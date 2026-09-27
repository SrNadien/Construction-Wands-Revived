package nadiendev.constructionwand.items.wand;

import net.minecraft.core.component.DataComponents;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.ToolMaterial;
import nadiendev.constructionwand.basics.ConfigServer;

public class ItemWandBasic extends ItemWand
{
    private final ToolMaterial tier;

    public ItemWandBasic(Properties properties, ToolMaterial tier) {
        super(properties.durability(tier.durability()).repairable(tier.repairItems()));
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
}
