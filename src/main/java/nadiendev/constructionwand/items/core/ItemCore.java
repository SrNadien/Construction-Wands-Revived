package nadiendev.constructionwand.items.core;

import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.component.TooltipDisplay;
import nadiendev.constructionwand.ConstructionWand;
import nadiendev.constructionwand.api.IWandCore;

import java.util.function.Consumer;

public abstract class ItemCore extends Item implements IWandCore
{
    public ItemCore(Properties properties) {
        super(properties);
    }

    @Override
    public void appendHoverText(ItemStack stack, Item.TooltipContext context, TooltipDisplay display, Consumer<Component> lines, TooltipFlag flag) {
        lines.accept(
                Component.translatable(ConstructionWand.MODID + ".option.cores." + getRegistryName().toString() + ".desc")
                        .withStyle(ChatFormatting.GRAY)
        );
        lines.accept(
                Component.translatable(ConstructionWand.MODID + ".tooltip.core_tip").withStyle(ChatFormatting.AQUA)
        );
    }
}
