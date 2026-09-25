package nadiendev.constructionwand.data;
import net.minecraft.advancements.Advancement;

import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.recipes.RecipeCategory;
import net.minecraft.data.worldgen.BootstrapContext;
import net.minecraft.data.recipes.RecipeOutput;
import net.minecraft.data.recipes.RecipeProvider;
import net.minecraft.data.recipes.SpecialRecipeBuilder;
import net.minecraft.resources.Identifier;
import net.minecraft.resources.ResourceKey;
import net.minecraft.tags.ItemTags;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.level.ItemLike;
import net.neoforged.neoforge.common.Tags;
import nadiendev.constructionwand.ConstructionWand;
import nadiendev.constructionwand.crafting.ModRecipes;
import nadiendev.constructionwand.crafting.RecipeWandUpgrade;
import nadiendev.constructionwand.items.ModItems;
import nadiendev.constructionwand.data.Inp;

import java.util.function.Supplier;

public class RecipeGenerator extends RecipeProvider
{
    public RecipeGenerator(BootstrapContext<Recipe<?>> recipeOutput, BootstrapContext<Advancement> advancementOutput) {
        super(recipeOutput, advancementOutput);
    }

    @Override
    protected void buildRecipes() {
        wandRecipe(ModItems.WAND_WOOD.get(),       Inp.fromItems(items, "wood_planks", Items.CHERRY_PLANKS, Items.BIRCH_PLANKS));
        wandRecipeAlt1(ModItems.WAND_WOOD.get(),   Inp.fromItems(items, "wood_planks", Items.CHERRY_PLANKS, Items.BIRCH_PLANKS));
        wandRecipeAlt2(ModItems.WAND_WOOD.get(),   Inp.fromItems(items, "wood_planks", Items.CHERRY_PLANKS, Items.BIRCH_PLANKS));
        wandRecipe(ModItems.WAND_STONE.get(),      Inp.fromTag(items, ItemTags.STONE_TOOL_MATERIALS));
        wandRecipeAlt1(ModItems.WAND_STONE.get(),  Inp.fromTag(items, ItemTags.STONE_TOOL_MATERIALS));
        wandRecipeAlt2(ModItems.WAND_STONE.get(),  Inp.fromTag(items, ItemTags.STONE_TOOL_MATERIALS));
        wandRecipe(ModItems.WAND_IRON.get(),       Inp.fromTag(items, Tags.Items.INGOTS_IRON));
        wandRecipeAlt1(ModItems.WAND_IRON.get(),   Inp.fromTag(items, Tags.Items.INGOTS_IRON));
        wandRecipeAlt2(ModItems.WAND_IRON.get(),   Inp.fromTag(items, Tags.Items.INGOTS_IRON));
        wandRecipe(ModItems.WAND_GOLD.get(),       Inp.fromTag(items, Tags.Items.INGOTS_GOLD));
        wandRecipeAlt1(ModItems.WAND_GOLD.get(),   Inp.fromTag(items, Tags.Items.INGOTS_GOLD));
        wandRecipeAlt2(ModItems.WAND_GOLD.get(),   Inp.fromTag(items, Tags.Items.INGOTS_GOLD));
        wandRecipe(ModItems.WAND_DIAMOND.get(),    Inp.fromTag(items, Tags.Items.GEMS_DIAMOND));
        wandRecipeAlt1(ModItems.WAND_DIAMOND.get(), Inp.fromTag(items, Tags.Items.GEMS_DIAMOND));
        wandRecipeAlt2(ModItems.WAND_DIAMOND.get(), Inp.fromTag(items, Tags.Items.GEMS_DIAMOND));
        wandRecipe(ModItems.WAND_NETHERITE.get(),  Inp.fromTag(items, Tags.Items.INGOTS_NETHERITE));
        wandRecipeAlt1(ModItems.WAND_NETHERITE.get(), Inp.fromTag(items, Tags.Items.INGOTS_NETHERITE));
        wandRecipeAlt2(ModItems.WAND_NETHERITE.get(), Inp.fromTag(items, Tags.Items.INGOTS_NETHERITE));
        wandRecipe(ModItems.WAND_INFINITY.get(),   Inp.fromTag(items, Tags.Items.NETHER_STARS));
        wandRecipeAlt1(ModItems.WAND_INFINITY.get(), Inp.fromTag(items, Tags.Items.NETHER_STARS));
        wandRecipeAlt2(ModItems.WAND_INFINITY.get(), Inp.fromTag(items, Tags.Items.NETHER_STARS));

        coreRecipe(ModItems.CORE_ANGEL.get(),
                Inp.fromTag(items, Tags.Items.FEATHERS),
                Inp.fromTag(items, Tags.Items.INGOTS_GOLD));
        coreRecipe(ModItems.CORE_DESTRUCTION.get(),
                Inp.fromTag(items, Tags.Items.STORAGE_BLOCKS_DIAMOND),
                Inp.fromItem(items, Items.DIAMOND_PICKAXE));
        coreRecipe(ModItems.CORE_EXCHANGE.get(),
                Inp.fromItem(items, Items.NETHER_STAR),
                Inp.fromTag(items, Tags.Items.STORAGE_BLOCKS_NETHERITE));

        voidSackRecipe();

        specialRecipe(RecipeWandUpgrade::new, ModRecipes.WAND_UPGRADE.get());
    }

    private void wandRecipe(ItemLike wand, Inp material) {
        shaped(RecipeCategory.TOOLS, wand)
                .define('X', material.ingredient())
                .define('#', Tags.Items.RODS_WOODEN)
                .pattern("  X")
                .pattern(" # ")
                .pattern("#  ")
                .unlockedBy("has_item", inventoryTrigger(material.predicate()))
                .save(output);
    }

    private void wandRecipeAlt1(ItemLike wand, Inp material) {
        shaped(RecipeCategory.TOOLS, wand)
                .define('X', material.ingredient())
                .define('#', Tags.Items.RODS_WOODEN)
                .pattern("X  ")
                .pattern(" # ")
                .pattern("  #")
                .unlockedBy("has_item", inventoryTrigger(material.predicate()))
                .save(output, ResourceKey.create(Registries.RECIPE,
                        ConstructionWand.loc(wandSuffix(wand) + "_alt1")));
    }

    private void wandRecipeAlt2(ItemLike wand, Inp material) {
        shaped(RecipeCategory.TOOLS, wand)
                .define('X', material.ingredient())
                .define('#', Tags.Items.RODS_WOODEN)
                .pattern("#  ")
                .pattern(" # ")
                .pattern("  X")
                .unlockedBy("has_item", inventoryTrigger(material.predicate()))
                .save(output, ResourceKey.create(Registries.RECIPE,
                        ConstructionWand.loc(wandSuffix(wand) + "_alt2")));
    }

    private static String wandSuffix(ItemLike wand) {
        Identifier id = BuiltInRegistries.ITEM.getKey(wand.asItem());
        return id != null ? id.getPath() : wand.asItem().toString();
    }

    private void coreRecipe(ItemLike core, Inp item1, Inp item2) {
        shaped(RecipeCategory.MISC, core)
                .define('O', item1.ingredient())
                .define('X', item2.ingredient())
                .define('#', Tags.Items.GLASS_PANES)
                .pattern(" #X")
                .pattern("#O#")
                .pattern("X# ")
                .unlockedBy("has_item", inventoryTrigger(item1.predicate()))
                .save(output);
    }

    private void voidSackRecipe() {
        shaped(RecipeCategory.MISC, ModItems.VOID_SACK.get())
                .define('C', ModItems.CORE_DESTRUCTION.get())
                .define('E', Items.DIAMOND)
                .define('O', Items.NETHERITE_INGOT)
                .define('D', Items.EMERALD)
                .pattern("EDE")
                .pattern("OCO")
                .pattern("EDE")
                .unlockedBy("has_ender_chest",
                        inventoryTrigger(Inp.fromTag(items, Tags.Items.CHESTS_ENDER).predicate()))
                .save(output);
    }

    private void specialRecipe(Supplier<Recipe<?>> factory, RecipeSerializer<?> serializer) {
        Identifier name = BuiltInRegistries.RECIPE_SERIALIZER.getKey(serializer);
        if (name == null) return;
        SpecialRecipeBuilder.special(factory).save(output,
                ConstructionWand.loc("dynamic/" + name.getPath()).toString());
    }
}
