package nadiendev.constructionwand.data;

import net.minecraft.core.HolderLookup;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.PackOutput;
import net.minecraft.data.advancements.AdvancementProvider;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.common.data.ExistingFileHelper;
import net.neoforged.neoforge.data.event.GatherDataEvent;

import java.util.List;
import java.util.concurrent.CompletableFuture;

public class ModData
{
    public static void register(IEventBus modEventBus) {
        modEventBus.addListener(ModData::gatherData);
    }

    public static void gatherData(GatherDataEvent event) {
        DataGenerator generator = event.getGenerator();
        PackOutput packOutput = generator.getPackOutput();
        ExistingFileHelper fileHelper = event.getExistingFileHelper();
        CompletableFuture<HolderLookup.Provider> lookupProvider = event.getLookupProvider();

        if(event.includeServer()) {
            generator.addProvider(true, new WandRecipeProvider.Runner(packOutput, lookupProvider));
            generator.addProvider(true, new AdvancementProvider(packOutput, lookupProvider,                    List.of(new WandAdvancementSubProvider())));
        }

        if(event.includeClient()) {
            generator.addProvider(true, new ItemModelGenerator(packOutput, fileHelper));
            generator.addProvider(true, new LanguageGenerator(packOutput));
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
}
