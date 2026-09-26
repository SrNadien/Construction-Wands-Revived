package nadiendev.constructionwand.data;

import net.minecraft.data.PackOutput;
import net.minecraft.data.recipes.FinishedRecipe;
import net.minecraft.data.recipes.RecipeCategory;
import net.minecraft.data.recipes.RecipeProvider;
import net.minecraft.data.recipes.ShapedRecipeBuilder;
import net.minecraft.data.recipes.SpecialRecipeBuilder;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.ItemTags;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.SimpleCraftingRecipeSerializer;
import net.minecraft.world.level.ItemLike;
import net.minecraftforge.common.Tags;
import net.minecraftforge.registries.ForgeRegistries;
import nadiendev.constructionwand.ConstructionWand;
import nadiendev.constructionwand.crafting.RecipeWandUpgrade;
import nadiendev.constructionwand.items.ModItems;

import javax.annotation.Nonnull;
import java.util.function.Consumer;

public class RecipeGenerator extends RecipeProvider
{
    public RecipeGenerator(PackOutput packOutput) {
        super(packOutput);
    }

    @Override
    protected void buildRecipes(@Nonnull Consumer<FinishedRecipe> consumer) {
        wandRecipe(consumer, ModItems.WAND_WOOD.get(), Inp.fromItems("wood_planks", Items.CHERRY_PLANKS, Items.BIRCH_PLANKS));
        wandRecipe(consumer, ModItems.WAND_STONE.get(), Inp.fromTag(ItemTags.STONE_TOOL_MATERIALS));
        wandRecipe(consumer, ModItems.WAND_IRON.get(), Inp.fromTag(Tags.Items.INGOTS_IRON));
        wandRecipe(consumer, ModItems.WAND_GOLD.get(), Inp.fromTag(Tags.Items.INGOTS_GOLD));
        wandRecipe(consumer, ModItems.WAND_DIAMOND.get(), Inp.fromTag(Tags.Items.GEMS_DIAMOND));
        wandRecipe(consumer, ModItems.WAND_NETHERITE.get(), Inp.fromTag(Tags.Items.INGOTS_NETHERITE));
        wandRecipe(consumer, ModItems.WAND_INFINITY.get(), Inp.fromTag(Tags.Items.NETHER_STARS));

        coreRecipe(consumer, ModItems.CORE_ANGEL.get(), Inp.fromTag(Tags.Items.FEATHERS), Inp.fromTag(Tags.Items.INGOTS_GOLD));
        coreRecipe(consumer, ModItems.CORE_DESTRUCTION.get(), Inp.fromTag(Tags.Items.STORAGE_BLOCKS_DIAMOND), Inp.fromItem(Items.DIAMOND_PICKAXE));
        coreRecipe(consumer, ModItems.CORE_EXCHANGE.get(), Inp.fromTag(Tags.Items.STORAGE_BLOCKS_EMERALD), Inp.fromItem(Items.DIAMOND_SHOVEL));

        voidSackRecipe(consumer);

        specialRecipe(consumer, RecipeWandUpgrade.SERIALIZER);
    }

    private void voidSackRecipe(Consumer<FinishedRecipe> consumer) {
        Inp coreDestruction = Inp.fromItem(ModItems.CORE_DESTRUCTION.get());
        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, ModItems.VOID_SACK.get())
                .define('C', coreDestruction.ingredient)
                .define('E', Items.DIAMOND)
                .define('O', Items.NETHERITE_INGOT)
                .define('D', Items.EMERALD)
                .pattern("EDE")
                .pattern("OCO")
                .pattern("EDE")
                .unlockedBy("has_core_destruction", inventoryTrigger(coreDestruction.predicate))
                .save(consumer);
    }

    private void wandRecipe(Consumer<FinishedRecipe> consumer, ItemLike wand, Inp material) {
        ShapedRecipeBuilder.shaped(RecipeCategory.TOOLS, wand)
                .define('X', material.ingredient)
                .define('#', Tags.Items.RODS_WOODEN)
                .pattern("  X")
                .pattern(" # ")
                .pattern("#  ")
                .unlockedBy("has_item", inventoryTrigger(material.predicate))
                .save(consumer);
    }

    private void coreRecipe(Consumer<FinishedRecipe> consumer, ItemLike core, Inp item1, Inp item2) {
        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, core)
                .define('O', item1.ingredient)
                .define('X', item2.ingredient)
                .define('#', Tags.Items.GLASS_PANES)
                .pattern(" #X")
                .pattern("#O#")
                .pattern("X# ")
                .unlockedBy("has_item", inventoryTrigger(item1.predicate))
                .save(consumer);
    }

    private void specialRecipe(Consumer<FinishedRecipe> consumer, SimpleCraftingRecipeSerializer<?> serializer) {
        ResourceLocation name = ForgeRegistries.RECIPE_SERIALIZERS.getKey(serializer);
        SpecialRecipeBuilder.special(serializer).save(consumer, ConstructionWand.loc("dynamic/" + name.getPath()).toString());
    }
}