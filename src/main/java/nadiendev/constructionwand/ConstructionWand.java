package nadiendev.constructionwand;

import net.minecraft.resources.ResourceLocation;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.config.ModConfig;
import net.neoforged.fml.event.lifecycle.FMLClientSetupEvent;
import net.neoforged.fml.event.lifecycle.FMLCommonSetupEvent;
import net.neoforged.neoforge.common.NeoForge;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import nadiendev.constructionwand.basics.ConfigClient;
import nadiendev.constructionwand.basics.ConfigServer;
import nadiendev.constructionwand.basics.ModStats;
import nadiendev.constructionwand.client.ClientEvents;
import nadiendev.constructionwand.client.RenderBlockPreview;
<<<<<<< Updated upstream
=======
import nadiendev.constructionwand.client.property.SelectWandCore;
>>>>>>> Stashed changes
import nadiendev.constructionwand.component.ModDataComponents;
import nadiendev.constructionwand.containers.ContainerManager;
import nadiendev.constructionwand.containers.ContainerRegistrar;
import nadiendev.constructionwand.crafting.ModRecipes;
import nadiendev.constructionwand.creative.ModCreativeTabs;
<<<<<<< Updated upstream
=======
import nadiendev.constructionwand.events.VoidSackDropHandler;
import nadiendev.constructionwand.events.VoidSackPickupHandler;
>>>>>>> Stashed changes
import nadiendev.constructionwand.items.ModItems;
import nadiendev.constructionwand.network.ModMessages;
import nadiendev.constructionwand.wand.undo.UndoHistory;

@Mod(ConstructionWand.MODID)
public class ConstructionWand {
    public static final String MODID = "constructionwand";
    public static final String MODNAME = "ConstructionWandRevived";

    public static ConstructionWand instance;
    public static final Logger LOGGER = LogManager.getLogger();

    public ContainerManager containerManager;
    public UndoHistory undoHistory;
    public RenderBlockPreview renderBlockPreview;

    public ConstructionWand(IEventBus eventBus, ModContainer container, Dist dist) {
        instance = this;

        containerManager = new ContainerManager();
        undoHistory = new UndoHistory();

        eventBus.addListener(this::commonSetup);
        eventBus.addListener(this::clientSetup);
        eventBus.addListener(ModMessages::registerPayloads);

        ModDataComponents.DATA_COMPONENT_TYPES.register(eventBus);
        ModItems.ITEMS.register(eventBus);
        ModRecipes.RECIPE_SERIALIZERS.register(eventBus);
        ModCreativeTabs.register(eventBus);
<<<<<<< Updated upstream
=======
        ModMenuTypes.register(eventBus);
>>>>>>> Stashed changes
        ModStats.CUSTOM_STATS.register(eventBus);

        container.registerConfig(ModConfig.Type.SERVER, ConfigServer.SPEC);
        container.registerConfig(ModConfig.Type.CLIENT, ConfigClient.SPEC);

        NeoForge.EVENT_BUS.register(new VoidSackDropHandler());
        NeoForge.EVENT_BUS.register(new VoidSackPickupHandler());
    }

    private void commonSetup(final FMLCommonSetupEvent event) {
        LOGGER.info("ConstructionWand says hello - may the odds be ever in your favor.");
        ContainerRegistrar.register();
    }

    private void clientSetup(final FMLClientSetupEvent event) {
        renderBlockPreview = new RenderBlockPreview();
        NeoForge.EVENT_BUS.register(renderBlockPreview);
        NeoForge.EVENT_BUS.register(new ClientEvents());
<<<<<<< Updated upstream
        event.enqueueWork(ModItems::registerModelProperties);
=======

        // Register wand core item property predicate
        SelectWandCore.onClientSetup(event);
>>>>>>> Stashed changes
    }

    public static ResourceLocation loc(String name) {
        return ResourceLocation.fromNamespaceAndPath(MODID, name);
    }
}