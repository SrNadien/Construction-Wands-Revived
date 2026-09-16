package nadiendev.constructionwand.data;

import nadiendev.constructionwand.ConstructionWand;
import net.minecraft.core.RegistrySetBuilder;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.PackOutput;
import net.minecraft.data.recipes.RecipeProvider;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.common.data.DatapackBuiltinEntriesProvider;
import net.neoforged.neoforge.data.event.GatherDataEvent;

import java.util.Set;

@EventBusSubscriber(modid = ConstructionWand.MODID)
public class ModData {
    @SubscribeEvent
    public static void gatherData(GatherDataEvent.Client event) {
        DataGenerator generator = event.getGenerator();
        PackOutput packOutput = generator.getPackOutput();

        // En 26.3 recetas y avances son registros de datapack recargables: se generan
        // con un RegistrySetBuilder en vez de con providers sueltos.
        RegistrySetBuilder registrySet = new RegistrySetBuilder()
                .add(RecipeProvider.asBootstrap(RecipeGenerator::new))
                .add(Registries.ADVANCEMENT, new AdvancementGenerator());

        generator.addProvider(true, DatapackBuiltinEntriesProvider.forReloadableLayer(
                packOutput,
                "Construction Wand Recipes/Advancements",
                event.getWorldLookupProvider(),
                event.getReloadableLookupProvider(),
                registrySet,
                Set.of(ConstructionWand.MODID)));

        generator.addProvider(true, new ItemModelGenerator(packOutput));

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
