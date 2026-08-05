package nadiendev.constructionwand.wand.undo;

import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;
import nadiendev.constructionwand.api.IWandSupplier;
import nadiendev.constructionwand.basics.WandUtil;

import javax.annotation.Nullable;


public class ExchangeSnapshot implements ISnapshot
{
    private final BlockState oldBlock;
    private final BlockPos pos;
    private final IWandSupplier supplier;

    private BlockState resultState;
    private PlaceSnapshot placeResult;

    private ExchangeSnapshot(BlockState oldBlock, BlockPos pos, IWandSupplier supplier) {
        this.oldBlock = oldBlock;
        this.pos = pos;
        this.supplier = supplier;
        this.resultState = oldBlock;
    }

    @Nullable
    public static ExchangeSnapshot get(Level world, Player player, BlockPos pos, IWandSupplier supplier) {
        if(!WandUtil.isBlockRemovable(world, player, pos)) return null;

        BlockState state = world.getBlockState(pos);
        if(state.hasBlockEntity()) return null;

        return new ExchangeSnapshot(state, pos, supplier);
    }

    @Override
    public BlockPos getPos() {
        return pos;
    }

    @Override
    public BlockState getBlockState() {
        return resultState;
    }

    @Override
    public ItemStack getRequiredItems() {
        return placeResult != null ? placeResult.getRequiredItems() : ItemStack.EMPTY;
    }

    @Override
    public boolean execute(Level world, Player player, BlockHitResult rayTraceResult) {
        // Recalculate current state, the block may have changed since this candidate was queued
        BlockState currentState = world.getBlockState(pos);
        if(currentState.hasBlockEntity()) return false;
        if(!WandUtil.isBlockRemovable(world, player, pos)) return false;

        // Step 1: remove the existing block. No item parameter -> no drop.
        if(!WandUtil.removeBlock(world, player, currentState, pos)) return false;

        // Step 2: position is now empty, ask the real supplier for a replacement,
        // exactly like Construction Core does.
        PlaceSnapshot newPlacement = supplier.getPlaceSnapshot(world, pos, rayTraceResult, null);
        if(newPlacement == null) {
            // No usable replacement block available: rollback the removal.
            world.setBlockAndUpdate(pos, currentState);
            return false;
        }

        if(!newPlacement.execute(world, player, rayTraceResult)) {
            world.setBlockAndUpdate(pos, currentState);
            return false;
        }

        placeResult = newPlacement;
        resultState = newPlacement.getBlockState();
        return true;
    }

    @Override
    public boolean canRestore(Level world, Player player) {
        if(!world.isInWorldBounds(pos)) return false;
        if(!world.mayInteract(player, pos)) return false;
        if(player.isCreative()) return true;
        return !WandUtil.entitiesCollidingWithBlock(world, oldBlock, pos);
    }

    @Override
    public boolean restore(Level world, Player player) {
        return WandUtil.placeBlock(world, player, oldBlock, pos, null);
    }

    @Override
    public void forceRestore(Level world) {
        world.setBlockAndUpdate(pos, oldBlock);
    }
}
