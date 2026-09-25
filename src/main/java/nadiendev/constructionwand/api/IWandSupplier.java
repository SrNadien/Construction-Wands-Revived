package nadiendev.constructionwand.api;

import net.minecraft.core.BlockPos;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;
import nadiendev.constructionwand.wand.undo.PlaceSnapshot;

import javax.annotation.Nullable;

public interface IWandSupplier
{
    void getSupply(@Nullable BlockItem target);

    @Nullable
    PlaceSnapshot getPlaceSnapshot(Level world, BlockPos pos, BlockHitResult rayTraceResult,
                                   @Nullable BlockState supportingBlock);

    int takeItemStack(ItemStack stack);
}
