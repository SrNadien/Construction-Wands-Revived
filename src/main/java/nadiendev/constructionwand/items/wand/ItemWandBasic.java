package nadiendev.constructionwand.items.wand;

import net.minecraft.core.component.DataComponents;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Tier;
import nadiendev.constructionwand.basics.ConfigServer;

import javax.annotation.Nonnull;

public class ItemWandBasic extends ItemWand
{
    private final Tier tier;

    public ItemWandBasic(Properties properties, Tier tier) {
        super(properties.component(DataComponents.MAX_DAMAGE, tier.getUses()));
        this.tier = tier;
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
        return this.tier.getRepairIngredient().test(repair);
    }
}
