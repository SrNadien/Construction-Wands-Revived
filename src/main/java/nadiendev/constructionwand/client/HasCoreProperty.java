// package nadiendev.constructionwand.client;

// import nadiendev.constructionwand.ConstructionWand;
// import nadiendev.constructionwand.basics.option.WandOptions;
// import nadiendev.constructionwand.items.ModItems;
// import nadiendev.constructionwand.items.core.CoreDefault;
// import nadiendev.constructionwand.items.wand.ItemWand;
// import net.minecraft.resources.ResourceLocation;
// import net.minecraft.world.item.Item;
// import net.neoforged.bus.api.SubscribeEvent;
// import net.neoforged.fml.common.EventBusSubscriber;
// import net.neoforged.neoforge.client.event.RegisterItemDecorationsEvent;
// import net.neoforged.neoforge.registries.DeferredItem;
// import net.neoforged.neoforge.client.model.generators.ItemModelBuilder;

// // Registers the "using_core" float predicate used in item model overrides
// @EventBusSubscriber(modid = ConstructionWand.MODID, bus = EventBusSubscriber.Bus.MOD, value = net.minecraft.client.Minecraft.class)
// public class HasCoreProperty
// {
//     public static final ResourceLocation USING_CORE = ResourceLocation.fromNamespaceAndPath(ConstructionWand.MODID, "using_core");

//     @SubscribeEvent
//     public static void registerPredicates(net.neoforged.neoforge.client.event.RegisterClientReloadListenersEvent event) {
//     }

//     // Call this from your client events class on FMLClientSetupEvent
//     public static void register() {
//         for (DeferredItem<Item> wand : ModItems.WANDS) {
//             net.minecraft.client.renderer.item.ItemProperties.register(
//                     wand.get(),
//                     USING_CORE,
//                     (stack, level, entity, seed) -> {
//                         if (!(stack.getItem() instanceof ItemWand)) return 0f;
//                         return (new WandOptions(stack).cores.get() instanceof CoreDefault) ? 0f : 1f;
//                     }
//             );
//         }
//     }
// }