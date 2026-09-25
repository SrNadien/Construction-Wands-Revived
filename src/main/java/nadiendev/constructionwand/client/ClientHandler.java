package nadiendev.constructionwand.client;

import nadiendev.constructionwand.ConstructionWand;
import nadiendev.constructionwand.client.property.SelectWandCore;
import nadiendev.constructionwand.integrations.rrv.RrvIntegration;
import nadiendev.constructionwand.client.screen.ScreenVoidSack;
import nadiendev.constructionwand.registry.ModMenuTypes;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.ModList;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.fml.event.lifecycle.FMLClientSetupEvent;
import net.neoforged.neoforge.client.event.RegisterKeyMappingsEvent;
import net.neoforged.neoforge.client.event.RegisterSelectItemModelPropertyEvent;
import net.neoforged.neoforge.client.event.RegisterMenuScreensEvent;
import net.neoforged.neoforge.common.NeoForge;

@EventBusSubscriber(modid = ConstructionWand.MODID, value = Dist.CLIENT)
public class ClientHandler {
    public static RenderBlockPreview renderBlockPreview;

    @SubscribeEvent
    public static void onClientSetup(final FMLClientSetupEvent event) {
        renderBlockPreview = new RenderBlockPreview();
        NeoForge.EVENT_BUS.register(renderBlockPreview);
        NeoForge.EVENT_BUS.register(new KeybindHandler());

        // Descripciones de items en Reliable Recipe Viewer (JEI las recibe por su propio
        // plugin, ConstructionWandJeiPlugin). La guarda vive aqui a proposito:
        // RrvIntegration referencia clases de RRV y solo debe cargarse si el mod esta.
        if(ModList.get().isLoaded("rrv")) {
            RrvIntegration.init();
        }
    }

    // ─────────────────────────────────────────────────────────────────────────
    // RegisterKeyMappingsEvent
    // ─────────────────────────────────────────────────────────────────────────
    @SubscribeEvent
    public static void registerKeymapping(final RegisterKeyMappingsEvent event) {
        event.register(KeybindHandler.KEY_OPT);
        event.register(KeybindHandler.KEY_VOID_SACK_TOGGLE);
        event.register(KeybindHandler.KEY_UNDO);
        event.register(KeybindHandler.KEY_EXCHANGE_SELECT);
    }

    @SubscribeEvent
    public static void registerMenuScreens(RegisterMenuScreensEvent event) {
        event.register(ModMenuTypes.VOID_SACK.get(), ScreenVoidSack::new);
    }

    @SubscribeEvent
    public static void registerModelProperties(RegisterSelectItemModelPropertyEvent event) {
        event.register(ConstructionWand.loc("wand_core"), SelectWandCore.TYPE);
    }
}