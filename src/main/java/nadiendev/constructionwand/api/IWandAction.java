package nadiendev.constructionwand.api;

import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.BlockHitResult;
import nadiendev.constructionwand.basics.option.WandOptions;
import nadiendev.constructionwand.wand.undo.ISnapshot;

import javax.annotation.Nonnull;
import java.util.List;

public interface IWandAction
{
    int getLimit(ItemStack wand);

    @Nonnull
    List<ISnapshot> getSnapshots(Level world, Player player, BlockHitResult rayTraceResult,
                                 ItemStack wand, WandOptions options, IWandSupplier supplier, int limit);

    @Nonnull
    List<ISnapshot> getSnapshotsFromAir(Level world, Player player, BlockHitResult rayTraceResult,
                                        ItemStack wand, WandOptions options, IWandSupplier supplier, int limit);

   
    default boolean handleShiftClick(Level world, Player player, BlockHitResult rayTraceResult,
                                     ItemStack wand, WandOptions options) {
        return false;
    }
}