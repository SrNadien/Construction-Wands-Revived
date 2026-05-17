package nadiendev.constructionwand.crafting;

import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.SimpleRecipeSerializer;
import net.neoforged.neoforge.registries.DeferredRegister;
import nadiendev.constructionwand.ConstructionWand;

import java.util.function.Supplier;

public class ModRecipes
{

    public static final DeferredRegister<RecipeSerializer<?>> RECIPE_SERIALIZERS = DeferredRegister.create(BuiltInRegistries.RECIPE_SERIALIZER, ConstructionWand.MODID);

    public static final Supplier<SimpleRecipeSerializer<RecipeWandUpgrade>> WAND_UPGRADE = RECIPE_SERIALIZERS.register("wand_upgrade", () -> new SimpleRecipeSerializer<>(RecipeWandUpgrade::new));
}
