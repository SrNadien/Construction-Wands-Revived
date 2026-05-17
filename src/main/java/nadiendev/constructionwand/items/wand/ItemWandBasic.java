package nadiendev.constructionwand.items.wand;

import net.minecraft.core.component.DataComponents;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import nadiendev.constructionwand.basics.ConfigServer;

import javax.annotation.Nonnull;

public class ItemWandBasic extends ItemWand
{
    private final int tierUses;
    private final RepairProvider repairProvider;

    @FunctionalInterface
    public interface RepairProvider {
        boolean isValidRepairItem(@Nonnull ItemStack repair);
    }

    public ItemWandBasic(Properties properties, int tierUses, RepairProvider repairProvider) {
        super(properties.component(DataComponents.MAX_DAMAGE, tierUses));
        this.tierUses = tierUses;
        this.repairProvider = repairProvider;
    }

    @Override
    public int getMaxDamage(ItemStack stack) {
        return ConfigServer.getWandProperties(this).getDurability();
    }

    @Override
    public int remainingDurability(ItemStack stack) {
        return stack.getMaxDamage() - stack.getDamageValue();
    }

    @Override
    public boolean isValidRepairItem(@Nonnull ItemStack toRepair, @Nonnull ItemStack repair) {
        return this.repairProvider.isValidRepairItem(repair);
    }
}
