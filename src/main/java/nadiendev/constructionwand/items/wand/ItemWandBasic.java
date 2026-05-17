package nadiendev.constructionwand.items.wand;

import net.minecraft.core.component.DataComponents;
import net.minecraft.world.item.ItemStack;
import nadiendev.constructionwand.basics.ConfigServer;

public class ItemWandBasic extends ItemWand
{
    private final int tierUses;

    public ItemWandBasic(Properties properties, int tierUses) {
        super(properties.component(DataComponents.MAX_DAMAGE, tierUses));
        this.tierUses = tierUses;
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
