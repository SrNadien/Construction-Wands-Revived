package nadiendev.constructionwand.data;

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
    }
}