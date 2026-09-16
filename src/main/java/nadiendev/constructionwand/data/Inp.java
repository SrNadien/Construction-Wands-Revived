package nadiendev.constructionwand.data;

import net.minecraft.advancements.predicates.ItemPredicate;
import net.minecraft.core.HolderGetter;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.ItemLike;

/**
 * En 26.3 el RecipeProvider expone un HolderGetter<Item> (campo `items`) en vez del
 * HolderLookup.Provider completo, y ItemPredicate.Builder#of pasa a recibir ese getter.
 */
public record Inp(String name, Ingredient ingredient, ItemPredicate predicate) {
    public static Inp fromItem(HolderGetter<Item> items, ItemLike in) {
        return new Inp(BuiltInRegistries.ITEM.getKey(in.asItem()).getPath(), Ingredient.of(in),
                ItemPredicate.Builder.item().of(items, in).build());
    }

    public static Inp fromItems(HolderGetter<Item> items, String name, ItemLike... in) {
        return new Inp(name, Ingredient.of(in),
                ItemPredicate.Builder.item().of(items, in).build());
    }

    public static Inp fromTag(HolderGetter<Item> items, TagKey<Item> in) {
        return new Inp(in.location().getPath(), Ingredient.of(items.getOrThrow(in)),
                ItemPredicate.Builder.item().of(items, in).build());
    }
}
