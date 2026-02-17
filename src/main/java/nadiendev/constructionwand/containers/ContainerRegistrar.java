package nadiendev.constructionwand.containers;

import nadiendev.constructionwand.ConstructionWand;
import nadiendev.constructionwand.containers.handlers.HandlerBundle;
import nadiendev.constructionwand.containers.handlers.HandlerCapability;
import nadiendev.constructionwand.containers.handlers.HandlerShulkerbox;
import nadiendev.constructionwand.containers.handlers.HandlerSophisticatedBackpack;
import net.neoforged.fml.ModList;

public class ContainerRegistrar
{
    public static void register() {
        ConstructionWand.instance.containerManager.register(new HandlerCapability());
        ConstructionWand.instance.containerManager.register(new HandlerShulkerbox());
        ConstructionWand.instance.containerManager.register(new HandlerBundle());

//        if(ModList.get().isLoaded("botania")) {
//            ConstructionWand.instance.containerManager.register(new HandlerBotania());
//            ConstructionWand.LOGGER.info("Botania integration added");
//        }

        registerSophisticatedBackpacks();
    }

    private static void registerSophisticatedBackpacks() {
        try {
            boolean loaded = ModList.get().isLoaded("sophisticatedbackpacks");
            if (loaded) {
                ConstructionWand.instance.containerManager.register(new HandlerSophisticatedBackpack());
                ConstructionWand.LOGGER.info("Sophisticated BackPack integration added");
            }
        } catch (Exception e) {
            ConstructionWand.LOGGER.warn("Could not load Sophisticated Backpacks integration: " + e.getMessage());
        }
    }
}