package nadiendev.constructionwand.data;

import net.minecraft.client.data.models.ModelProvider;
import net.minecraft.data.advancements.AdvancementProvider;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.data.event.GatherDataEvent;
import nadiendev.constructionwand.ConstructionWand;

import java.util.List;

@EventBusSubscriber(modid = ConstructionWand.MODID)
public class ModData
{
    @SubscribeEvent
    public static void gatherData(GatherDataEvent.Server event) {
        event.createProvider((GatherDataEvent.DataProviderFromOutputLookup<AdvancementProvider>) (output, lookup) ->
                new AdvancementProvider(output, lookup, List.of(new WandAdvancementSubProvider())));

        event.createProvider((GatherDataEvent.DataProviderFromOutputLookup<WandRecipeProvider>) (output, lookup) ->
                new WandRecipeProvider(output, lookup));
    }

    @SubscribeEvent
    public static void gatherClientData(GatherDataEvent.Client event) {
        event.createProvider((GatherDataEvent.DataProviderFromOutput<ModelProvider>) output ->
                new WandModelProvider(output));
    }
}
