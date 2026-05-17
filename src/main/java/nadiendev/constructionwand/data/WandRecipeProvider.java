package nadiendev.constructionwand.data;

import net.minecraft.advancements.Advancement;
import net.minecraft.advancements.AdvancementHolder;
import net.minecraft.advancements.critereon.InventoryChangeTrigger;
import net.minecraft.advancements.critereon.ItemPredicate;
import net.minecraft.core.HolderGetter;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.CachedOutput;
import net.minecraft.data.DataProvider;
import net.minecraft.data.PackOutput;
import net.minecraft.data.recipes.RecipeCategory;
import net.minecraft.data.recipes.RecipeOutput;
import net.minecraft.data.recipes.ShapedRecipeBuilder;
import net.minecraft.data.recipes.SpecialRecipeBuilder;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.ItemTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.Recipe;
import net.neoforged.neoforge.common.Tags;
import nadiendev.constructionwand.ConstructionWand;
import nadiendev.constructionwand.crafting.RecipeWandUpgrade;
import nadiendev.constructionwand.items.ModItems;

import java.nio.file.Path;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CompletableFuture;

public class WandRecipeProvider implements DataProvider
{
    private final PackOutput output;
    private final CompletableFuture<HolderLookup.Provider> registries;

    public WandRecipeProvider(PackOutput output, CompletableFuture<HolderLookup.Provider> registries) {
        this.output = output;
        this.registries = registries;
    }

    @Override
    public CompletableFuture<?> run(CachedOutput cache) {
        return registries.thenCompose(provider -> {
            List<CompletableFuture<?>> futures = new ArrayList<>();
            PackOutput.PathProvider pathProvider = output.createPathProvider(PackOutput.Target.DATA_PACK, "recipes");
            HolderGetter<Item> itemGetter = provider.lookupOrThrow(Registries.ITEM);

            CollectingRecipeOutput recipeOutput = new CollectingRecipeOutput();
            buildRecipes(recipeOutput, provider, itemGetter);

            for(Map.Entry<ResourceKey<Recipe<?>>, Recipe<?>> entry : recipeOutput.recipes.entrySet()) {
                Path path = pathProvider.json(entry.getKey().location());
                futures.add(DataProvider.saveStable(cache, Recipe.CODEC, entry.getValue(), path));
            }
            return CompletableFuture.allOf(futures.toArray(CompletableFuture[]::new));
        });
    }

    private void buildRecipes(CollectingRecipeOutput output, HolderLookup.Provider provider, HolderGetter<Item> itemGetter) {
        wandRecipe(output, itemGetter, ModItems.WAND_STONE.get(), ItemTags.STONE_TOOL_MATERIALS);
        wandRecipe(output, itemGetter, ModItems.WAND_IRON.get(), Tags.Items.INGOTS_IRON);
        wandRecipe(output, itemGetter, ModItems.WAND_DIAMOND.get(), Tags.Items.GEMS_DIAMOND);
        wandRecipe(output, itemGetter, ModItems.WAND_NETHERITE.get(), Tags.Items.INGOTS_NETHERITE);
        wandRecipe(output, itemGetter, ModItems.WAND_INFINITY.get(), Tags.Items.NETHER_STARS);

        coreRecipe(output, itemGetter, ModItems.CORE_ANGEL.get(), Tags.Items.FEATHERS, Tags.Items.INGOTS_GOLD);
        coreRecipe(output, itemGetter, ModItems.CORE_DESTRUCTION.get(), Tags.Items.STORAGE_BLOCKS_DIAMOND, asTag(Items.DIAMOND_PICKAXE));

        specialRecipe(output);
    }

    private void wandRecipe(CollectingRecipeOutput output, HolderGetter<Item> itemGetter, net.minecraft.world.level.ItemLike wand, TagKey<Item> material) {
        ShapedRecipeBuilder.shaped(itemGetter, RecipeCategory.TOOLS, wand)
                .define('X', material)
                .define('#', Tags.Items.RODS_WOODEN)
                .pattern("  X")
                .pattern(" # ")
                .pattern("#  ")
                .unlockedBy("has_item", InventoryChangeTrigger.TriggerInstance.hasItems(
                        ItemPredicate.Builder.item().of(itemGetter, material).build()))
                .save(output);
    }

    private void coreRecipe(CollectingRecipeOutput output, HolderGetter<Item> itemGetter, net.minecraft.world.level.ItemLike core, TagKey<Item> item1, TagKey<Item> item2) {
        ShapedRecipeBuilder.shaped(itemGetter, RecipeCategory.MISC, core)
                .define('O', item1)
                .define('X', item2)
                .define('#', Tags.Items.GLASS_PANES)
                .pattern(" #X")
                .pattern("#O#")
                .pattern("X# ")
                .unlockedBy("has_item", InventoryChangeTrigger.TriggerInstance.hasItems(
                        ItemPredicate.Builder.item().of(itemGetter, item1).build()))
                .save(output);
    }

    private void specialRecipe(CollectingRecipeOutput output) {
        ResourceLocation id = ConstructionWand.loc("dynamic/wand_upgrade");
        SpecialRecipeBuilder.special(RecipeWandUpgrade::new).save(output, id.toString());
    }

    private static TagKey<Item> asTag(net.minecraft.world.level.ItemLike item) {
        return TagKey.create(Registries.ITEM, net.minecraft.core.registries.BuiltInRegistries.ITEM.getKey(item.asItem()));
    }

    @Override
    public String getName() {
        return ConstructionWand.MODNAME + " recipes";
    }

    private static class CollectingRecipeOutput implements RecipeOutput
    {
        final Map<ResourceKey<Recipe<?>>, Recipe<?>> recipes = new HashMap<>();

        @Override
        public void accept(ResourceKey<Recipe<?>> key, Recipe<?> recipe, AdvancementHolder advancement) {
            recipes.put(key, recipe);
        }

        public void accept(ResourceKey<Recipe<?>> key, Recipe<?> recipe, AdvancementHolder advancement, net.neoforged.neoforge.common.conditions.ICondition... conditions) {
            recipes.put(key, recipe);
        }

        @Override
        public Advancement.Builder advancement() {
            return Advancement.Builder.advancement();
        }

        @Override
        public void includeRootAdvancement() {
        }
    }
}
