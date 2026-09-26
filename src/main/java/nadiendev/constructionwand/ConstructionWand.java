package nadiendev.constructionwand;

import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.config.ModConfig;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import nadiendev.constructionwand.basics.ConfigClient;
import nadiendev.constructionwand.basics.ConfigServer;
import nadiendev.constructionwand.basics.ModStats;
import nadiendev.constructionwand.client.ClientEvents;
import nadiendev.constructionwand.client.RenderBlockPreview;
import nadiendev.constructionwand.containers.ContainerManager;
import nadiendev.constructionwand.containers.ContainerRegistrar;
import nadiendev.constructionwand.items.ModItems;
import nadiendev.constructionwand.network.ModMessages;
import nadiendev.constructionwand.registry.ModMenuTypes;
import nadiendev.constructionwand.creative.ModCreativeTabs;
import nadiendev.constructionwand.events.VoidSackDropHandler;
import nadiendev.constructionwand.events.VoidSackPickupHandler;
import nadiendev.constructionwand.wand.undo.UndoHistory;


@Mod(ConstructionWand.MODID)
public class ConstructionWand
{
    public static final String MODID = "constructionwand";
    public static final String MODNAME = "ConstructionWand";

    public static ConstructionWand instance;
    public static final Logger LOGGER = LogManager.getLogger();

    public ContainerManager containerManager;
    public UndoHistory undoHistory;
    public RenderBlockPreview renderBlockPreview;

    public ConstructionWand() {
        instance = this;

        containerManager = new ContainerManager();
        undoHistory = new UndoHistory();

        // Register setup methods for modloading
        FMLJavaModLoadingContext.get().getModEventBus().addListener(this::commonSetup);
        FMLJavaModLoadingContext.get().getModEventBus().addListener(this::clientSetup);
        MinecraftForge.EVENT_BUS.register(this);
        MinecraftForge.EVENT_BUS.register(new VoidSackDropHandler());
        MinecraftForge.EVENT_BUS.register(new VoidSackPickupHandler());

        // Register Item DeferredRegister
        ModItems.ITEMS.register(FMLJavaModLoadingContext.get().getModEventBus());
        ModMenuTypes.register(FMLJavaModLoadingContext.get().getModEventBus());
        ModCreativeTabs.register(FMLJavaModLoadingContext.get().getModEventBus());

        // Config setup
        ModLoadingContext.get().registerConfig(ModConfig.Type.SERVER, ConfigServer.SPEC);
        ModLoadingContext.get().registerConfig(ModConfig.Type.CLIENT, ConfigClient.SPEC);
    }

    private void commonSetup(final FMLCommonSetupEvent event) {
        LOGGER.info("ConstructionWand says hello - may the odds be ever in your favor.");

        // Register packets
        ModMessages.register();

        // Container registry
        ContainerRegistrar.register();

        // Stats
        ModStats.register();
    }

    private void clientSetup(final FMLClientSetupEvent event) {
        renderBlockPreview = new RenderBlockPreview();
        MinecraftForge.EVENT_BUS.register(renderBlockPreview);
        MinecraftForge.EVENT_BUS.register(new ClientEvents());

        event.enqueueWork(ModItems::registerModelProperties);
        event.enqueueWork(() -> net.minecraft.client.gui.screens.MenuScreens.register(
                nadiendev.constructionwand.registry.ModMenuTypes.VOID_SACK.get(),
                nadiendev.constructionwand.client.screen.ScreenVoidSack::new));
    }

    public static ResourceLocation loc(String name) {
        return new ResourceLocation(MODID, name);
    }
}
