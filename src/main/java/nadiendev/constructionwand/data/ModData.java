package nadiendev.constructionwand.data;

import nadiendev.constructionwand.ConstructionWand;
import nadiendev.constructionwand.data.AdvancementGenerator;
import nadiendev.constructionwand.data.ItemModelGenerator;
import net.minecraft.data.advancements.AdvancementProvider;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.data.event.GatherDataEvent;

import java.util.List;

public class ModData {

    public static void register(IEventBus modEventBus) {
        modEventBus.addListener(ModData::gatherData);
    }

    public static void gatherData(GatherDataEvent.Client event) {
        // Advancements
        event.createProvider((output, lookup) ->
                new AdvancementProvider(output, lookup, List.of(new AdvancementGenerator.WandAdvancementGenerator())));

        // Recetas
        event.createProvider((output, lookup) ->
                new RecipeGenerator.Runner(output, lookup));

        // Modelos 
         event.createProvider(output -> new ItemModelGenerator(output));

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
    }
}