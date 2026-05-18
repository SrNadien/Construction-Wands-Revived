package nadiendev.constructionwand.data;

<<<<<<< Updated upstream
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
=======
import net.minecraft.advancements.critereon.InventoryChangeTrigger;
import net.minecraft.advancements.critereon.ItemPredicate;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.data.recipes.RecipeCategory;
import net.minecraft.data.recipes.RecipeOutput;
import net.minecraft.data.recipes.RecipeProvider;
import net.minecraft.data.recipes.ShapedRecipeBuilder;
import net.minecraft.data.recipes.SpecialRecipeBuilder;
>>>>>>> Stashed changes
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.ItemTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
<<<<<<< Updated upstream
import net.minecraft.world.item.crafting.Recipe;
=======
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.ItemLike;
>>>>>>> Stashed changes
import net.neoforged.neoforge.common.Tags;
import nadiendev.constructionwand.ConstructionWand;
import nadiendev.constructionwand.crafting.RecipeWandUpgrade;
import nadiendev.constructionwand.items.ModItems;

<<<<<<< Updated upstream
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
=======
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
>>>>>>> Stashed changes
                .define('X', material)
                .define('#', Tags.Items.RODS_WOODEN)
                .pattern("  X")
                .pattern(" # ")
                .pattern("#  ")
<<<<<<< Updated upstream
                .unlockedBy("has_item", InventoryChangeTrigger.TriggerInstance.hasItems(
                        ItemPredicate.Builder.item().of(itemGetter, material).build()))
                .save(output);
    }

    private void coreRecipe(CollectingRecipeOutput output, HolderGetter<Item> itemGetter, net.minecraft.world.level.ItemLike core, TagKey<Item> item1, TagKey<Item> item2) {
        ShapedRecipeBuilder.shaped(itemGetter, RecipeCategory.MISC, core)
                .define('O', item1)
                .define('X', item2)
=======
                .unlockedBy("has_item", inventoryTrigger(
                        ItemPredicate.Builder.item().of(registries, material).build()))
                .save(output);
    }

    private void coreRecipe(RecipeOutput output, ItemLike core, TagKey<Item> item1, TagKey<Item> item2) {
        shaped(RecipeCategory.MISC, core)
                .define('O', Ingredient.of(item1))
                .define('X', Ingredient.of(item2))
>>>>>>> Stashed changes
                .define('#', Tags.Items.GLASS_PANES)
                .pattern(" #X")
                .pattern("#O#")
                .pattern("X# ")
<<<<<<< Updated upstream
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
=======
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
>>>>>>> Stashed changes
