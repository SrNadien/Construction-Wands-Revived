package nadiendev.constructionwand;

import nadiendev.constructionwand.basics.ConfigClient;
import nadiendev.constructionwand.basics.ConfigServer;
import nadiendev.constructionwand.basics.ModStats;
import nadiendev.constructionwand.containers.ContainerManager;
import nadiendev.constructionwand.containers.ContainerRegistrar;
import nadiendev.constructionwand.crafting.ModRecipes;
import nadiendev.constructionwand.items.ModItems;
import nadiendev.constructionwand.network.ModMessages;
import nadiendev.constructionwand.wand.undo.UndoHistory;
import nadiendev.constructionwand.creative.ModCreativeTabs;
import net.minecraft.resources.Identifier;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.config.ModConfig;
import net.neoforged.fml.event.lifecycle.FMLCommonSetupEvent;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@Mod(ConstructionWand.MODID)
public class ConstructionWand {
    public static final String MODID = "constructionwand";
    public static final String MODNAME = "ConstructionWand";

    public static final Logger LOGGER = LogManager.getLogger();

    public static ContainerManager containerManager;
    public static UndoHistory undoHistory;

    public ConstructionWand(IEventBus eventBus, ModContainer container, Dist dist) {
        containerManager = new ContainerManager();
        undoHistory = new UndoHistory();

        eventBus.addListener(this::commonSetup);
        eventBus.addListener(ModMessages::registerPayloads);

        ModItems.ITEMS.register(eventBus);
        ModCreativeTabs.CREATIVE_TABS.register(eventBus);
        ModRecipes.RECIPE_SERIALIZERS.register(eventBus);
        ModStats.CUSTOM_STATS.register(eventBus);

        container.registerConfig(ModConfig.Type.SERVER, ConfigServer.SPEC);
        container.registerConfig(ModConfig.Type.CLIENT, ConfigClient.SPEC);

        
    }

    private void commonSetup(final FMLCommonSetupEvent event) {
        LOGGER.info("ConstructionWand says hello - may the odds be ever in your favor.");
        ContainerRegistrar.register();
    }

    public static Identifier loc(String name) {
        return Identifier.fromNamespaceAndPath(MODID, name);
    }
}