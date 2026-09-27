package nadiendev.constructionwand.items.wand;

import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.ToolMaterial;
import nadiendev.constructionwand.basics.ConfigServer;
import nadiendev.constructionwand.basics.option.WandOptions;
import nadiendev.constructionwand.items.ModItems;
import nadiendev.constructionwand.items.core.ItemCoreExchange;

import javax.annotation.Nonnull;

public class ItemWandBasic extends ItemWand
{
    private final ToolMaterial tier;

    public ItemWandBasic(Properties properties, ToolMaterial tier) {
        super(properties.durability(tier.durability()).repairable(tier.repairItems()));
        this.tier = tier;
    }

    @Override
    public int getMaxDamage(ItemStack stack) {
        // Special case: Gold Construction Wand + Exchange Core must have exactly
        // half the durability of the Diamond Construction Wand + Exchange Core.
        // Diamond's own durability isn't affected by which core is installed, so this
        // simply reads Diamond's configured durability and halves it, staying in sync
        // if the server admin changes Diamond's config value.
        if(this.tier == ToolMaterial.GOLD && new WandOptions(stack).cores.get() instanceof ItemCoreExchange) {
            int diamondDurability = ConfigServer.getWandProperties(ModItems.WAND_DIAMOND.get()).getDurability();
            return diamondDurability / 2;
        }
        return ConfigServer.getWandProperties(this).getDurability();
    }

    @Override
    public int remainingDurability(ItemStack stack) {
        return stack.getMaxDamage() - stack.getDamageValue();
    }

    @Override
    public boolean isValidRepairItem(@Nonnull ItemStack toRepair, @Nonnull ItemStack repair) {
        return repair.is(this.tier.repairItems());
    }
}