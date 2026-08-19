package nadiendev.constructionwand.containers;

import net.neoforged.fml.ModList;
import nadiendev.constructionwand.ConstructionWand;
import nadiendev.constructionwand.containers.handlers.HandlerBundle;
import nadiendev.constructionwand.containers.handlers.HandlerCapability;
import nadiendev.constructionwand.containers.handlers.HandlerShulkerbox;
import nadiendev.constructionwand.containers.handlers.HandlerSophisticatedBackpack;


public class ContainerRegistrar {

    public static void register() {
        ConstructionWand.containerManager.register(new HandlerShulkerbox());
        ConstructionWand.containerManager.register(new HandlerBundle());
        ConstructionWand.containerManager.register(new HandlerCapability());

        // Mod Integrations
        registerSophisticatedBackpacks();
        // registerAppliedEnergistics();  // ver src/disabled-integrations
        // registerRefinedStorage();      // ver src/disabled-integrations
    }

    // -------------------------------------------------------------------------
    // Integraciones opcionales
    // -------------------------------------------------------------------------

    private static void registerSophisticatedBackpacks() {
        try {
            if (ModList.get().isLoaded("sophisticatedbackpacks")) {
                ConstructionWand.containerManager.register(new HandlerSophisticatedBackpack());
                ConstructionWand.LOGGER.info("Sophisticated Backpacks integration added");
            }
        } catch (Exception e) {
            ConstructionWand.LOGGER.warn("Could not load Sophisticated Backpacks integration: " + e.getMessage());
        }
    }

    // -------------------------------------------------------------------------
    // Applied Energistics 2 / AE2WTLib / Refined Storage
    //
    // Sin build para Minecraft 26.2. Los handlers estan aparcados en
    // src/disabled-integrations/ (fuera del sourceSet). Ver el README de esa
    // carpeta para reactivarlos.
    // -------------------------------------------------------------------------

    // private static void registerAppliedEnergistics() {
    //     try {
    //         if (ModList.get().isLoaded("ae2")) {
    //             ConstructionWand.containerManager.register(new HandlerWirelessTerminal());
    //             ConstructionWand.containerManager.register(new HandlerPortableCell());
    //             ConstructionWand.LOGGER.info("Applied Energistics 2 integration added");
    //         }
    //     } catch (Exception e) {
    //         ConstructionWand.LOGGER.warn("Could not load Applied Energistics 2 integration: " + e.getMessage());
    //     }
    //
    //     try {
    //         if (ModList.get().isLoaded("ae2wtlib")) {
    //             ConstructionWand.containerManager.register(new HandlerWirelessTerminalAE2WTLib());
    //             ConstructionWand.LOGGER.info("AE2 Wireless Terminals integration added");
    //         }
    //     } catch (Exception e) {
    //         ConstructionWand.LOGGER.warn("Could not load AE2 Wireless Terminals integration: " + e.getMessage());
    //     }
    // }

    // private static void registerRefinedStorage() {
    //     try {
    //         if (ModList.get().isLoaded("refinedstorage")) {
    //             ConstructionWand.containerManager.register(new HandlerWirelessGrid());
    //             ConstructionWand.LOGGER.info("Refined Storage integration added (Wireless Grid)");
    //         }
    //     } catch (Exception e) {
    //         ConstructionWand.LOGGER.warn("Could not load Refined Storage integration: " + e.getMessage());
    //     }
    // }
}
