package nadiendev.constructionwand.data;

<<<<<<< Updated upstream
import net.minecraft.client.data.models.ModelProvider;
import net.minecraft.data.advancements.AdvancementProvider;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
=======
import net.minecraft.data.advancements.AdvancementProvider;
import net.neoforged.bus.api.IEventBus;
>>>>>>> Stashed changes
import net.neoforged.neoforge.data.event.GatherDataEvent;
import nadiendev.constructionwand.ConstructionWand;

import java.util.List;

<<<<<<< Updated upstream
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
=======
public class ModData {

    public static void register(IEventBus modEventBus) {
        modEventBus.addListener(ModData::gatherData);
    }

    public static void gatherData(GatherDataEvent.Client event) {
        // Advancements
        event.createProvider((output, lookup) ->
                new AdvancementProvider(output, lookup, List.of(new WandAdvancementSubProvider())));

        // Recetas
        event.createProvider(WandRecipeProvider.Runner::new);

        // Modelos
        event.createProvider(output -> new WandModelProvider(output));

        // Lenguajes
        event.createProvider((output, lookup) -> new LanguageGenerator(output));
        event.createProvider((output, lookup) -> new LanguageGenerator.ESAR(output));
        event.createProvider((output, lookup) -> new LanguageGenerator.ESCL(output));
        event.createProvider((output, lookup) -> new LanguageGenerator.ESCO(output));
        event.createProvider((output, lookup) -> new LanguageGenerator.ESES(output));
        event.createProvider((output, lookup) -> new LanguageGenerator.ESMX(output));
        event.createProvider((output, lookup) -> new LanguageGenerator.JAJP(output));
        event.createProvider((output, lookup) -> new LanguageGenerator.KOKR(output));
        event.createProvider((output, lookup) -> new LanguageGenerator.PTBR(output));
        event.createProvider((output, lookup) -> new LanguageGenerator.RURU(output));
        event.createProvider((output, lookup) -> new LanguageGenerator.SVSE(output));
        event.createProvider((output, lookup) -> new LanguageGenerator.TRTR(output));
        event.createProvider((output, lookup) -> new LanguageGenerator.ZHCN(output));
        event.createProvider((output, lookup) -> new LanguageGenerator.DEDE(output));
>>>>>>> Stashed changes
    }
}
