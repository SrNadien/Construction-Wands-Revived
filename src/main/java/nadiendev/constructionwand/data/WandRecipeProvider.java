package nadiendev.constructionwand.data;

import net.minecraft.advancements.critereon.InventoryChangeTrigger;
import net.minecraft.advancements.critereon.ItemPredicate;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.data.recipes.RecipeCategory;
import net.minecraft.data.recipes.RecipeOutput;
import net.minecraft.data.recipes.RecipeProvider;
import net.minecraft.data.recipes.ShapedRecipeBuilder;
import net.minecraft.data.recipes.SpecialRecipeBuilder;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.ItemTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.ItemLike;
import net.neoforged.neoforge.common.Tags;
import nadiendev.constructionwand.ConstructionWand;
import nadiendev.constructionwand.crafting.RecipeWandUpgrade;
import nadiendev.constructionwand.items.ModItems;

import java.util.concurrent.CompletableFuture;

public class WandRecipeProvider extends RecipeProvider {

    protected WandRecipeProvider(HolderLookup.Provider provider, RecipeOutput output) {
        super(provider, output);
    }

    @Override
    protected void buildRecipes() {
        wandRecipe(output, ModItems.WAND_STONE.get(),     ItemTags.STONE_TOOL_MATERIALS);
        wandRecipe(output, ModItems.WAND_IRON.get(),      Tags.Items.INGOTS_IRON);
        wandRecipe(output, ModItems.WAND_DIAMOND.get(),   Tags.Items.GEMS_DIAMOND);
        wandRecipe(output, ModItems.WAND_NETHERITE.get(), Tags.Items.INGOTS_NETHERITE);
        wandRecipe(output, ModItems.WAND_INFINITY.get(),  Tags.Items.NETHER_STARS);

        coreRecipe(output, ModItems.CORE_ANGEL.get(),       Tags.Items.FEATHERS,              Tags.Items.INGOTS_GOLD);
        coreRecipe(output, ModItems.CORE_DESTRUCTION.get(), Tags.Items.STORAGE_BLOCKS_DIAMOND, asTag(Items.DIAMOND_PICKAXE));

        ResourceLocation id = ConstructionWand.loc("dynamic/wand_upgrade");
        SpecialRecipeBuilder.special(RecipeWandUpgrade::new).save(output, id.toString());
    }

    private void wandRecipe(RecipeOutput output, ItemLike wand, TagKey<Item> material) {
        shaped(RecipeCategory.TOOLS, wand)
                .define('X', material)
                .define('#', Tags.Items.RODS_WOODEN)
                .pattern("  X")
                .pattern(" # ")
                .pattern("#  ")
                .unlockedBy("has_item", inventoryTrigger(
                        ItemPredicate.Builder.item().of(registries, material).build()))
                .save(output);
    }

    private void coreRecipe(RecipeOutput output, ItemLike core, TagKey<Item> item1, TagKey<Item> item2) {
        shaped(RecipeCategory.MISC, core)
                .define('O', Ingredient.of(item1))
                .define('X', Ingredient.of(item2))
                .define('#', Tags.Items.GLASS_PANES)
                .pattern(" #X")
                .pattern("#O#")
                .pattern("X# ")
                .unlockedBy("has_item", inventoryTrigger(
                        ItemPredicate.Builder.item().of(registries, item1).build()))
                .save(output);
    }

    private static TagKey<Item> asTag(ItemLike item) {
        return net.minecraft.core.registries.BuiltInRegistries.ITEM
                .getResourceKey(item.asItem())
                .map(key -> TagKey.create(net.minecraft.core.registries.Registries.ITEM,
                        key.location()))
                .orElseThrow();
    }

    public static class Runner extends RecipeProvider.Runner {
        public Runner(PackOutput output, CompletableFuture<HolderLookup.Provider> lookupProvider) {
            super(output, lookupProvider);
        }

        @Override
        protected RecipeProvider createRecipeProvider(HolderLookup.Provider provider, RecipeOutput output) {
            return new WandRecipeProvider(provider, output);
        }

        @Override
        public String getName() {
            return ConstructionWand.MODNAME + " recipes";
        }
    }
}