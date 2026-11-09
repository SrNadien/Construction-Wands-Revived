package nadiendev.constructionwand.wand.undo;

import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.Fluids;
import net.minecraft.world.phys.BlockHitResult;
import nadiendev.constructionwand.basics.ConfigServer;
import nadiendev.constructionwand.basics.WandUtil;
import nadiendev.constructionwand.wand.action.ActionDestruction;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.List;

public class DestroySnapshot implements ISnapshot
{
    private final BlockState block;
    private final BlockPos pos;

    /** What the player was given for this block, taken back from the inventory when the destruction is undone. */
    private final List<ItemStack> given = new ArrayList<>();
    /** The drops ended up somewhere they cannot be taken back from: a Void Sack, or the world. */
    private boolean dropsKept = false;

    public DestroySnapshot(BlockState block, BlockPos pos) {
        this.pos = pos;
        this.block = block;
    }

    @Nullable
    public static DestroySnapshot get(Level world, Player player, BlockPos pos) {
        if(!WandUtil.isBlockRemovable(world, player, pos)) return null;

        return new DestroySnapshot(world.getBlockState(pos), pos);
    }

    @Override
    public BlockPos getPos() {
        return pos;
    }

    @Override
    public BlockState getBlockState() {
        return block;
    }

    @Override
    public ItemStack getRequiredItems() {
        return ItemStack.EMPTY;
    }

    @Override
    public boolean execute(Level world, Player player, BlockHitResult rayTraceResult) {
        if(!(world instanceof ServerLevel serverLevel)) {
            return WandUtil.removeBlock(world, player, block, pos);
        }

        if(!WandUtil.isBlockRemovable(world, player, pos)) return false;

        ServerPlayer serverPlayer = player instanceof ServerPlayer sp ? sp : null;

        // A wand enchanted with Silk Touch breaks the blocks as that tool would: the loot context gets the wand
        ItemStack tool = silkTouchWand(player);

        // With a Void Sack in the inventory the drops are dropped in the world and the sack intercepts them
        // (see ActionDestruction.VoidSackCapturingSnapshot / VoidSackDropHandler).
        if(!ActionDestruction.findActiveSack(player).isEmpty()) {
            boolean destroyed;
            if(tool.isEmpty()) destroyed = serverLevel.destroyBlock(pos, true, serverPlayer, 512);
            else {
                // destroyBlock always drops as if broken by hand, so the drops of the enchanted wand are spawned here
                List<ItemStack> silkDrops = Block.getDrops(world.getBlockState(pos), serverLevel, pos,
                        world.getBlockEntity(pos), player, tool);
                destroyed = serverLevel.destroyBlock(pos, false, serverPlayer, 512);
                if(destroyed) for(ItemStack drop : silkDrops) Block.popResource(serverLevel, pos, drop);
            }
            // The sack keeps what it caught, and what it could not hold falls in the world: undoing this
            // destruction would hand the blocks out a second time
            if(destroyed) dropsKept = true;
            return destroyed;
        }

        // Creative players never need the drops, and the server config can turn the pickup off: the blocks are
        // destroyed and undoing it puts them back for free
        if(player.isCreative() || !ConfigServer.DESTRUCTION_GIVES_DROPS.get()) {
            given.clear();
            return serverLevel.destroyBlock(pos, false, serverPlayer, 512);
        }

        BlockState broken = world.getBlockState(pos);
        BlockEntity blockEntity = world.getBlockEntity(pos);
        List<ItemStack> drops = Block.getDrops(broken, serverLevel, pos, blockEntity, player, tool);

        if(!serverLevel.destroyBlock(pos, false, serverPlayer, 512)) return false;

        given.clear();
        for(ItemStack drop : drops) {
            if(drop.isEmpty()) continue;
            ItemStack offered = drop.copy();
            int before = drop.getCount();
            player.getInventory().add(drop);
            offered.setCount(before - drop.getCount());
            if(offered.getCount() > 0) remember(offered);
        }

        return true;
    }

    /** The wand in the player's hands when it is enchanted with Silk Touch, otherwise an empty stack. */
    private static ItemStack silkTouchWand(Player player) {
        ItemStack wand = WandUtil.holdingWand(player);
        if(wand == null) return ItemStack.EMPTY;
        return EnchantmentHelper.getItemEnchantmentLevel(Enchantments.SILK_TOUCH, wand) > 0 ? wand : ItemStack.EMPTY;
    }

    @Override
    public boolean canRestore(Level world, Player player) {
        // Is position out of world?
        if(!world.isInWorldBounds(pos)) return false;

        // Is block modifiable?
        if(!world.mayInteract(player, pos)) return false;

        // Ignore blocks and entities when in creative
        if(player.isCreative()) return true;
        // The blocks that were handed out have to be taken back, so they must still be in the inventory
        if(dropsKept || !carriesGivenItems(player)) return false;

        // Is block empty or fluid?
        if(!world.isEmptyBlock(pos) && !world.getBlockState(pos).canBeReplaced(Fluids.EMPTY)) return false;

        return !WandUtil.entitiesCollidingWithBlock(world, block, pos);
    }

    @Override
    public boolean restore(Level world, Player player) {
        boolean charge = !player.isCreative() && !given.isEmpty();
        if(charge && !takeBackGivenItems(player)) return false;

        if(WandUtil.placeBlock(world, player, block, pos, null)) return true;

        if(charge) {
            for(ItemStack stack : given) player.getInventory().add(stack.copy());
            player.getInventory().setChanged();
        }
        return false;
    }

    private void remember(ItemStack offered) {
        for(ItemStack stack : given) {
            if(WandUtil.stackEquals(stack, offered)) {
                stack.grow(offered.getCount());
                return;
            }
        }
        given.add(offered);
    }

    private static int carried(Player player, ItemStack template) {
        int total = 0;
        for(ItemStack stack : WandUtil.getFullInv(player)) {
            if(!stack.isEmpty() && WandUtil.stackEquals(stack, template)) total += stack.getCount();
        }
        return total;
    }

    private boolean carriesGivenItems(Player player) {
        for(ItemStack stack : given) {
            if(carried(player, stack) < stack.getCount()) return false;
        }
        return true;
    }

    private boolean takeBackGivenItems(Player player) {
        if(!carriesGivenItems(player)) return false;

        for(ItemStack wanted : given) {
            int remaining = wanted.getCount();
            for(ItemStack stack : WandUtil.getFullInv(player)) {
                if(remaining <= 0) break;
                if(stack.isEmpty() || !WandUtil.stackEquals(stack, wanted)) continue;
                int taken = Math.min(remaining, stack.getCount());
                stack.shrink(taken);
                remaining -= taken;
            }
        }
        player.getInventory().setChanged();
        return true;
    }

    @Override
    public void forceRestore(Level world) {
        world.setBlockAndUpdate(pos, block);
    }
}
