package nadiendev.constructionwand.client.property;

import nadiendev.constructionwand.ConstructionWand;
import nadiendev.constructionwand.basics.option.WandOptions;
import nadiendev.constructionwand.items.ModItems;
import nadiendev.constructionwand.items.core.ItemCoreAngel;
import nadiendev.constructionwand.items.core.ItemCoreDestruction;
import nadiendev.constructionwand.items.wand.ItemWand;
import net.minecraft.client.renderer.item.ItemProperties;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.neoforged.fml.event.lifecycle.FMLClientSetupEvent;
import net.neoforged.neoforge.registries.DeferredItem;

public class SelectWandCore
{
    public static final ResourceLocation WAND_CORE = ResourceLocation.fromNamespaceAndPath(ConstructionWand.MODID, "wand_core");

    public static void onClientSetup(FMLClientSetupEvent event) {
        // ItemProperties#register is not thread-safe, must run inside enqueueWork
        event.enqueueWork(() -> {
            for (DeferredItem<Item> wand : ModItems.WANDS) {
                ItemProperties.register(wand.get(), WAND_CORE, (stack, level, entity, seed) -> {
                    if (!(stack.getItem() instanceof ItemWand)) return 0f;
                    var core = new WandOptions(stack).cores.get();
                    if (core instanceof ItemCoreAngel)       return 1f; // core_angel active
                    if (core instanceof ItemCoreDestruction) return 2f; // core_destruction active
                    return 0f;                                          // default (no core)
                });
            }
        });
    }
}