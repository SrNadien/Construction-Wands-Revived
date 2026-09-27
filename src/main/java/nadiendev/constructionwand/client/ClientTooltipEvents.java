package nadiendev.constructionwand.client;

import nadiendev.constructionwand.ConstructionWand;
import nadiendev.constructionwand.api.IWandCore;
import nadiendev.constructionwand.items.wand.ItemWand;
import nadiendev.constructionwand.items.core.ItemCore;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.entity.player.ItemTooltipEvent;

import java.util.function.Consumer;

@EventBusSubscriber(modid = ConstructionWand.MODID, value = Dist.CLIENT)
public class ClientTooltipEvents {
    private ClientTooltipEvents() {
    }

    @SubscribeEvent
    public static void onItemTooltip(ItemTooltipEvent event) {
        ItemStack stack = event.getItemStack();
        Item item = stack.getItem();

        Consumer<Component> tooltip = component -> event.getToolTip().add(component);

        if (item instanceof ItemWand) {
            ItemWand.appendWandTooltip(stack, tooltip, event.getFlags());
        } else if (item instanceof ItemCore && item instanceof IWandCore core) {
            ItemCore.appendCoreTooltip(core, tooltip);
        }
    }
}