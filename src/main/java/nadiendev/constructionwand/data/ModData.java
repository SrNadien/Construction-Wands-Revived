package nadiendev.constructionwand.data;

import net.minecraft.data.DataGenerator;
import net.minecraft.data.PackOutput;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.data.event.GatherDataEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.MOD)
public class ModData
{
    @SubscribeEvent
    public static void gatherData(GatherDataEvent event) {
        DataGenerator generator = event.getGenerator();
        PackOutput packOutput = generator.getPackOutput();
        ExistingFileHelper fileHelper = event.getExistingFileHelper();

        if(event.includeServer()) {
            generator.addProvider(true, new RecipeGenerator(packOutput));
            generator.addProvider(true, new AdvancementGenerator(packOutput, event.getLookupProvider(), fileHelper));
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
