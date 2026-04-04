package nadiendev.constructionwand.containers;

import nadiendev.constructionwand.ConstructionWand;
import nadiendev.constructionwand.containers.handlers.HandlerBundle;
import nadiendev.constructionwand.containers.handlers.HandlerCapability;
import nadiendev.constructionwand.containers.handlers.HandlerShulkerbox;
import nadiendev.constructionwand.containers.handlers.HandlerSophisticatedBackpack;
<<<<<<< Updated upstream
=======
import nadiendev.constructionwand.containers.handlers.HandlerPortableCell;
import nadiendev.constructionwand.containers.handlers.HandlerWirelessGrid;
import nadiendev.constructionwand.containers.handlers.HandlerWirelessTerminal;
>>>>>>> Stashed changes
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
        registerAppliedEnergistics();
        registerRefinedStorage();
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

    private static void registerAppliedEnergistics() {
        try {
            boolean loaded = ModList.get().isLoaded("ae2");
            if (loaded) {
                ConstructionWand.instance.containerManager.register(new HandlerPortableCell());
                ConstructionWand.instance.containerManager.register(new HandlerWirelessTerminal());
                ConstructionWand.LOGGER.info("Applied Energistics 2 integration added");
            }
        } catch (Exception e) {
            ConstructionWand.LOGGER.warn("Could not load Applied Energistics 2 integration: " + e.getMessage());
        }
    }

    private static void registerRefinedStorage() {
        try {
            boolean loaded = ModList.get().isLoaded("refinedstorage");
            if (loaded) {
                ConstructionWand.instance.containerManager.register(new HandlerWirelessGrid());
                ConstructionWand.LOGGER.info("Refined Storage integration added");
            }
        } catch (Exception e) {
            ConstructionWand.LOGGER.warn("Could not load Refined Storage integration: " + e.getMessage());
        }
    }
}