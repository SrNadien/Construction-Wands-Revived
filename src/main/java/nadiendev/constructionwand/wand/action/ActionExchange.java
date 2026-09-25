package nadiendev.constructionwand.wand.action;

import net.minecraft.ChatFormatting;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;
import nadiendev.constructionwand.ConstructionWand;
import nadiendev.constructionwand.api.IWandAction;
import nadiendev.constructionwand.api.IWandSupplier;
import nadiendev.constructionwand.basics.ConfigServer;
import nadiendev.constructionwand.basics.WandUtil;
import nadiendev.constructionwand.basics.option.WandOptions;
import nadiendev.constructionwand.wand.undo.ExchangeSnapshot;
import nadiendev.constructionwand.wand.undo.ISnapshot;

import javax.annotation.Nonnull;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;


public class ActionExchange implements IWandAction
{
    @Override
    public int getLimit(ItemStack wand) {
        return ConfigServer.getWandProperties(wand.getItem()).getExchange();
    }

    public static boolean selectReplacementBlock(Level world, Player player, BlockPos pos) {
        BlockState state = world.getBlockState(pos);
        Item item = state.getBlock().asItem();

        if(!(item instanceof BlockItem blockItem) || item == Items.AIR) {
            player.sendOverlayMessage(
                    Component.translatable(ConstructionWand.MODID + ".message.exchange_invalid")
                            .withStyle(ChatFormatting.RED));
            return false;
        }

        ConstructionWand.undoHistory.setExchangeSelection(player, blockItem);
        player.sendOverlayMessage(
                Component.translatable(ConstructionWand.MODID + ".message.exchange_selected", state.getBlock().getName())
                        .withStyle(ChatFormatting.GREEN));

        return true;
    }

    @Nonnull
    @Override
    public List<ISnapshot> getSnapshots(Level world, Player player, BlockHitResult rayTraceResult,
                                        ItemStack wand, WandOptions options, IWandSupplier supplier, int limit) {
        LinkedList<ISnapshot> exchangeSnapshots = new LinkedList<>();

        BlockItem selected = ConstructionWand.undoHistory.getExchangeSelection(player);
        if(selected == null) {
            player.sendOverlayMessage(
                    Component.translatable(ConstructionWand.MODID + ".message.exchange_none_selected")
                            .withStyle(ChatFormatting.RED));
            return exchangeSnapshots;
        }

        supplier.getSupply(selected);

        LinkedList<BlockPos> candidates = new LinkedList<>();
        HashSet<BlockPos> allCandidates = new HashSet<>();

        Direction targetFace = rayTraceResult.getDirection();
        BlockPos startingPoint = rayTraceResult.getBlockPos();
        BlockState targetBlock = world.getBlockState(rayTraceResult.getBlockPos());

        if(targetFace == Direction.UP || targetFace == Direction.DOWN) {
            if(options.testLock(WandOptions.LOCK.NORTHSOUTH) || options.testLock(WandOptions.LOCK.EASTWEST))
                candidates.add(startingPoint);
        }
        else if(options.testLock(WandOptions.LOCK.HORIZONTAL) || options.testLock(WandOptions.LOCK.VERTICAL))
            candidates.add(startingPoint);

        while(!candidates.isEmpty() && exchangeSnapshots.size() < limit) {
            BlockPos currentCandidate = candidates.removeFirst();

            if(!WandUtil.isBlockPermeable(world, currentCandidate.relative(targetFace))) continue;

            try {
                BlockState candidateBlock = world.getBlockState(currentCandidate);

                if(candidateBlock.hasBlockEntity()) continue;

                if(options.matchBlocks(targetBlock.getBlock(), candidateBlock.getBlock()) &&
                        allCandidates.add(currentCandidate)) {
                    ExchangeSnapshot snapshot = ExchangeSnapshot.get(world, player, currentCandidate, supplier);
                    if(snapshot == null) continue;
                    exchangeSnapshots.add(snapshot);

                    Direction[] axisA, axisB;
                    boolean lockA, lockB;
                    if(targetFace == Direction.UP || targetFace == Direction.DOWN) {
                        axisA = new Direction[]{Direction.NORTH, Direction.SOUTH};
                        axisB = new Direction[]{Direction.EAST, Direction.WEST};
                        lockA = options.testLock(WandOptions.LOCK.NORTHSOUTH);
                        lockB = options.testLock(WandOptions.LOCK.EASTWEST);
                    }
                    else {
                        axisA = (targetFace == Direction.NORTH || targetFace == Direction.SOUTH)
                                ? new Direction[]{Direction.EAST, Direction.WEST}
                                : new Direction[]{Direction.NORTH, Direction.SOUTH};
                        axisB = new Direction[]{Direction.UP, Direction.DOWN};
                        lockA = options.testLock(WandOptions.LOCK.HORIZONTAL);
                        lockB = options.testLock(WandOptions.LOCK.VERTICAL);
                    }

                    if(lockA) for(Direction a : axisA) candidates.add(currentCandidate.relative(a));
                    if(lockB) for(Direction b : axisB) candidates.add(currentCandidate.relative(b));

                    if(lockA && lockB) {
                        for(Direction a : axisA) {
                            BlockPos sideA = currentCandidate.relative(a);
                            if(!WandUtil.isBlockPermeable(world, sideA.relative(targetFace))) continue;
                            for(Direction b : axisB) {
                                BlockPos sideB = currentCandidate.relative(b);
                                if(!WandUtil.isBlockPermeable(world, sideB.relative(targetFace))) continue;
                                candidates.add(sideA.relative(b));
                            }
                        }
                    }
                }
            } catch(Exception e) {
            }
        }

        return exchangeSnapshots;
    }

    @Nonnull
    @Override
    public List<ISnapshot> getSnapshotsFromAir(Level world, Player player, BlockHitResult rayTraceResult,
                                               ItemStack wand, WandOptions options, IWandSupplier supplier, int limit) {
        return new ArrayList<>();
    }
}
