package nadiendev.constructionwand.data;

import net.minecraft.core.HolderLookup;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.PackOutput;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.data.event.GatherDataEvent;
import nadiendev.constructionwand.ConstructionWand;

import java.util.concurrent.CompletableFuture;

@EventBusSubscriber(modid = ConstructionWand.MODID, bus = EventBusSubscriber.Bus.MOD)
public class ModData
{
    @SubscribeEvent
    public static void gatherData(GatherDataEvent event) {
        DataGenerator generator = event.getGenerator();
        PackOutput packOutput = generator.getPackOutput();
        ExistingFileHelper fileHelper = event.getExistingFileHelper();
        CompletableFuture<HolderLookup.Provider> lookupProvider = event.getLookupProvider();

        generator.addProvider(true, new RecipeGenerator(packOutput, lookupProvider));
        generator.addProvider(true, new AdvancementGenerator(packOutput, lookupProvider, fileHelper));
        generator.addProvider(true, new ItemModelGenerator(packOutput, fileHelper));
    }
}