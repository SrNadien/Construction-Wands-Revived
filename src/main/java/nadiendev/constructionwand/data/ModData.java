package nadiendev.constructionwand.data;

import net.minecraft.core.HolderLookup;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.PackOutput;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.common.data.ExistingFileHelper;
import net.neoforged.neoforge.data.event.GatherDataEvent;

import java.util.concurrent.CompletableFuture;

<<<<<<< Updated upstream
=======
@SuppressWarnings("removal")
>>>>>>> Stashed changes
@EventBusSubscriber(bus = EventBusSubscriber.Bus.MOD)
public class ModData
{
    @SubscribeEvent
    public static void gatherData(GatherDataEvent event) {
        DataGenerator generator = event.getGenerator();
        PackOutput packOutput = generator.getPackOutput();
        ExistingFileHelper fileHelper = event.getExistingFileHelper();
        CompletableFuture<HolderLookup.Provider> lookupProvider = event.getLookupProvider();

        if(event.includeServer()) {
            generator.addProvider(true, new RecipeGenerator(packOutput, lookupProvider));
            generator.addProvider(true, new AdvancementGenerator(packOutput, lookupProvider, fileHelper));
        }

        if(event.includeClient()) {
            generator.addProvider(true, new ItemModelGenerator(packOutput, fileHelper));
<<<<<<< Updated upstream
=======
            generator.addProvider(true, new LanguageGenerator(packOutput)); // Default (en_us)
            generator.addProvider(true, new LanguageGenerator.ESAR(packOutput)); // Argentine Spanish
            generator.addProvider(true, new LanguageGenerator.ESCL(packOutput)); // Chilean Spanish
            generator.addProvider(true, new LanguageGenerator.ESCO(packOutput)); // Colombian Spanish
            generator.addProvider(true, new LanguageGenerator.ESES(packOutput)); // Spain Spanish
            generator.addProvider(true, new LanguageGenerator.ESMX(packOutput)); // Mexican Spanish
            generator.addProvider(true, new LanguageGenerator.JAJP(packOutput)); // Japanese
            generator.addProvider(true, new LanguageGenerator.KOKR(packOutput)); // Korean
            generator.addProvider(true, new LanguageGenerator.PTBR(packOutput)); // Brazilian Portuguese
            generator.addProvider(true, new LanguageGenerator.RURU(packOutput)); // Russian
            generator.addProvider(true, new LanguageGenerator.SVSE(packOutput)); // Swedish
            generator.addProvider(true, new LanguageGenerator.TRTR(packOutput)); // Turkish
            generator.addProvider(true, new LanguageGenerator.ZHCN(packOutput)); // Chinese Simplified
            generator.addProvider(true, new LanguageGenerator.DEDE(packOutput)); // German
>>>>>>> Stashed changes
        }
    }
}