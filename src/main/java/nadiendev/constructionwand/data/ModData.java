package nadiendev.constructionwand.data;

import nadiendev.constructionwand.ConstructionWand;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.PackOutput;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.data.event.GatherDataEvent;

import java.util.concurrent.CompletableFuture;

@EventBusSubscriber(modid = ConstructionWand.MODID)
public class ModData {
    @SubscribeEvent
    public static void gatherData(GatherDataEvent.Client event) {
        DataGenerator generator = event.getGenerator();
        PackOutput packOutput = generator.getPackOutput();
        CompletableFuture<HolderLookup.Provider> lookupProvider = event.getLookupProvider();

        generator.addProvider(true, new RecipeGenerator.Runner(packOutput, lookupProvider));
        generator.addProvider(true, new ItemModelGenerator(packOutput));
        generator.addProvider(true, new AdvancementGenerator(packOutput, lookupProvider));

        // en_us
        generator.addProvider(true, new LanguageGenerator(packOutput));
        // otros idiomas
        generator.addProvider(true, new LanguageGenerator.ESAR(packOutput));
        generator.addProvider(true, new LanguageGenerator.ESCL(packOutput));
        generator.addProvider(true, new LanguageGenerator.ESCO(packOutput));
        generator.addProvider(true, new LanguageGenerator.ESES(packOutput));
        generator.addProvider(true, new LanguageGenerator.ESMX(packOutput));
        generator.addProvider(true, new LanguageGenerator.JAJP(packOutput));
        generator.addProvider(true, new LanguageGenerator.KOKR(packOutput));
        generator.addProvider(true, new LanguageGenerator.PTBR(packOutput));
        generator.addProvider(true, new LanguageGenerator.RURU(packOutput));
        generator.addProvider(true, new LanguageGenerator.SVSE(packOutput));
        generator.addProvider(true, new LanguageGenerator.TRTR(packOutput));
        generator.addProvider(true, new LanguageGenerator.ZHCN(packOutput));
        generator.addProvider(true, new LanguageGenerator.DEDE(packOutput));
    }
}