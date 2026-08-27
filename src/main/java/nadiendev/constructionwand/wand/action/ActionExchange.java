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

    /**
     * Selecciona (o rechaza) el bloque en {@code pos} como reemplazo del Exchange core.
     * La usa PacketExchangeSelect (tecla Numpad 7).
     */
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

        // Bloque seleccionado con la tecla de selección. Sin selección no hay nada para intercambiar.
        BlockItem selected = ConstructionWand.undoHistory.getExchangeSelection(player);
        if(selected == null) {
            player.sendOverlayMessage(
                    Component.translatable(ConstructionWand.MODID + ".message.exchange_none_selected")
                            .withStyle(ChatFormatting.RED));
            return exchangeSnapshots;
        }

        // Reinicializa el supplier con el bloque seleccionado (no el bloque objetivo,
        // que es lo que el WandJob le pasó por defecto al construirlo).
        supplier.getSupply(selected);

        // Current list of block positions to process
        LinkedList<BlockPos> candidates = new LinkedList<>();
        // All positions that were processed (dont process blocks multiple times)
        HashSet<BlockPos> allCandidates = new HashSet<>();

        // Block face the wand was pointed at
        Direction targetFace = rayTraceResult.getDirection();
        // Block the wand was pointed at
        BlockPos startingPoint = rayTraceResult.getBlockPos();
        BlockState targetBlock = world.getBlockState(rayTraceResult.getBlockPos());

        // Is exchange direction allowed by lock?
        if(targetFace == Direction.UP || targetFace == Direction.DOWN) {
            if(options.testLock(WandOptions.LOCK.NORTHSOUTH) || options.testLock(WandOptions.LOCK.EASTWEST))
                candidates.add(startingPoint);
        }
        else if(options.testLock(WandOptions.LOCK.HORIZONTAL) || options.testLock(WandOptions.LOCK.VERTICAL))
            candidates.add(startingPoint);

        // Process current candidates, stop when none are avaiable or block limit is reached
        while(!candidates.isEmpty() && exchangeSnapshots.size() < limit) {
            BlockPos currentCandidate = candidates.removeFirst();

            // Only exchange blocks facing the player, with no collidable blocks in between
            if(!WandUtil.isBlockPermeable(world, currentCandidate.relative(targetFace))) continue;

            try {
                BlockState candidateBlock = world.getBlockState(currentCandidate);

                // Never touch blocks with a BlockEntity (chests, furnaces, machines, etc.)
                if(candidateBlock.hasBlockEntity()) continue;

                // If target and candidate blocks match and the current candidate has not been processed
                if(options.matchBlocks(targetBlock.getBlock(), candidateBlock.getBlock()) &&
                        allCandidates.add(currentCandidate)) {
                    ExchangeSnapshot snapshot = ExchangeSnapshot.get(world, player, currentCandidate, supplier);
                    if(snapshot == null) continue;
                    exchangeSnapshots.add(snapshot);

                    switch(targetFace) {
                        case DOWN:
                        case UP:
                            addNeighbours(world, candidates, currentCandidate, targetFace,
                                    options.testLock(WandOptions.LOCK.NORTHSOUTH), Direction.NORTH, Direction.SOUTH,
                                    options.testLock(WandOptions.LOCK.EASTWEST), Direction.EAST, Direction.WEST);
                            break;
                        case NORTH:
                        case SOUTH:
                            addNeighbours(world, candidates, currentCandidate, targetFace,
                                    options.testLock(WandOptions.LOCK.HORIZONTAL), Direction.EAST, Direction.WEST,
                                    options.testLock(WandOptions.LOCK.VERTICAL), Direction.UP, Direction.DOWN);
                            break;
                        case EAST:
                        case WEST:
                            addNeighbours(world, candidates, currentCandidate, targetFace,
                                    options.testLock(WandOptions.LOCK.HORIZONTAL), Direction.NORTH, Direction.SOUTH,
                                    options.testLock(WandOptions.LOCK.VERTICAL), Direction.UP, Direction.DOWN);
                            break;
                    }
                }
            } catch(Exception e) {
            }
        }

        return exchangeSnapshots;
    }

    /**
     * Encola los vecinos del bloque procesado sobre los dos ejes habilitados por el lock.
     * Los ortogonales se propagan siempre; las diagonales solo cuando ambos ortogonales que las
     * componen estan descubiertos, para que el intercambio se corte debajo de un bloque tapado
     * en vez de rodearlo.
     */
    private void addNeighbours(Level world, LinkedList<BlockPos> candidates, BlockPos pos, Direction targetFace,
                               boolean axisA, Direction a1, Direction a2,
                               boolean axisB, Direction b1, Direction b2) {
        if(axisA) {
            candidates.add(pos.relative(a1));
            candidates.add(pos.relative(a2));
        }
        if(axisB) {
            candidates.add(pos.relative(b1));
            candidates.add(pos.relative(b2));
        }
        if(axisA && axisB) {
            for(Direction a : new Direction[] {a1, a2}) {
                BlockPos orthoA = pos.relative(a);
                if(!WandUtil.isBlockPermeable(world, orthoA.relative(targetFace))) continue;

                for(Direction b : new Direction[] {b1, b2}) {
                    BlockPos orthoB = pos.relative(b);
                    if(!WandUtil.isBlockPermeable(world, orthoB.relative(targetFace))) continue;

                    candidates.add(orthoA.relative(b));
                }
            }
        }
    }

    @Nonnull
    @Override
    public List<ISnapshot> getSnapshotsFromAir(Level world, Player player, BlockHitResult rayTraceResult,
                                               ItemStack wand, WandOptions options, IWandSupplier supplier, int limit) {
        return new ArrayList<>();
    }
}
