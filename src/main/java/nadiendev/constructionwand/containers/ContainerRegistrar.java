package nadiendev.constructionwand.containers;

import net.neoforged.fml.ModList;
import nadiendev.constructionwand.ConstructionWand;
import nadiendev.constructionwand.containers.handlers.HandlerBundle;
import nadiendev.constructionwand.containers.handlers.HandlerCapability;
import nadiendev.constructionwand.containers.handlers.HandlerShulkerbox;
// import nadiendev.constructionwand.containers.handlers.HandlerSophisticatedBackpack;

public class ContainerRegistrar
{
    public static void register() {
        ConstructionWand.containerManager.register(new HandlerShulkerbox());
        ConstructionWand.containerManager.register(new HandlerBundle());
        ConstructionWand.containerManager.register(new HandlerCapability());

        // registerSophisticatedBackpacks();
    }

    // private static void registerSophisticatedBackpacks() {
    //     try {
    //         boolean loaded = ModList.get().isLoaded("sophisticatedbackpacks");
    //         if (loaded) {
    //             ConstructionWand.containerManager.register(new HandlerSophisticatedBackpack());
    //             ConstructionWand.LOGGER.info("Sophisticated BackPack integration added");
    //         }
    //     } catch (Exception e) {
    //         ConstructionWand.LOGGER.warn("Could not load Sophisticated Backpacks integration: " + e.getMessage());
    //     }
    // }
}