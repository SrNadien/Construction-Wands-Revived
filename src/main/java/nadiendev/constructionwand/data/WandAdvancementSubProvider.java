package nadiendev.constructionwand.data;

import net.minecraft.advancements.Advancement;
import net.minecraft.advancements.AdvancementHolder;
import net.minecraft.advancements.AdvancementType;
import net.minecraft.advancements.DisplayInfo;
import net.minecraft.advancements.critereon.InventoryChangeTrigger;
import net.minecraft.advancements.critereon.ItemPredicate;
import net.minecraft.core.ClientAsset;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.advancements.AdvancementSubProvider;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.ItemLike;
import net.neoforged.neoforge.registries.DeferredItem;
import nadiendev.constructionwand.ConstructionWand;
import nadiendev.constructionwand.items.ModItems;

import java.util.Optional;
import java.util.function.Consumer;

public class WandAdvancementSubProvider implements AdvancementSubProvider
{
    @Override
    public void generate(HolderLookup.Provider registries, Consumer<AdvancementHolder> consumer) {
        var itemGetter = registries.lookupOrThrow(Registries.ITEM);

        AdvancementHolder root = Advancement.Builder.advancement()
                .display(rootDisplay(
                        ModItems.WAND_STONE.get(),
                        advancementPrefix("root.title"),
                        advancementPrefix("root.desc"),
                        mcLoc("textures/block/oak_planks.png")
                ))
                .addCriterion("wand", InventoryChangeTrigger.TriggerInstance.hasItems(
                        ItemPredicate.Builder.item().of(itemGetter, ModItems.WAND_STONE.get()).build()
                ))
                .save(consumer, ConstructionWand.loc("root"));

        
        onHasItem(consumer, itemGetter, ModItems.WAND_STONE,       AdvancementType.TASK, root);
        onHasItem(consumer, itemGetter, ModItems.WAND_IRON,        AdvancementType.TASK, root);
        onHasItem(consumer, itemGetter, ModItems.WAND_DIAMOND,     AdvancementType.TASK, root);
        onHasItem(consumer, itemGetter, ModItems.WAND_NETHERITE,   AdvancementType.TASK, root);
        onHasItem(consumer, itemGetter, ModItems.WAND_INFINITY,    AdvancementType.GOAL, root);
        onHasItem(consumer, itemGetter, ModItems.CORE_ANGEL,       AdvancementType.TASK, root);
        onHasItem(consumer, itemGetter, ModItems.CORE_DESTRUCTION, AdvancementType.TASK, root);
        onHasItem(consumer, itemGetter, ModItems.VOID_SACK,        AdvancementType.TASK, root);
    }

    private static void onHasItem(Consumer<AdvancementHolder> consumer,
                                   HolderLookup.RegistryLookup<Item> itemGetter,
                                   DeferredItem<Item> item, AdvancementType type, AdvancementHolder parent) {
        String path = item.getId().getPath();
        Advancement.Builder.advancement()
                .display(simpleDisplay(item.get(), path, type))
                .parent(parent)
                .addCriterion(path, InventoryChangeTrigger.TriggerInstance.hasItems(item.get()))
                .save(consumer, ConstructionWand.loc(path));
    }

    private static DisplayInfo rootDisplay(ItemLike icon, String titleKey, String descKey, ResourceLocation background) {
        return new DisplayInfo(
                new ItemStack(icon),
                Component.translatable(titleKey),
                Component.translatable(descKey),
                Optional.of(new ClientAsset(background)),
                AdvancementType.TASK, false, false, false
        );
    }

    private static DisplayInfo simpleDisplay(ItemLike icon, String name, AdvancementType type) {
        return new DisplayInfo(
                new ItemStack(icon),
                Component.translatable(advancementPrefix(name + ".title")),
                Component.translatable(advancementPrefix(name + ".desc")),
                Optional.empty(), type, true, true, false
        );
    }

    private static String advancementPrefix(String name) {
        return "advancement." + ConstructionWand.MODID + "." + name;
    }

    private static ResourceLocation mcLoc(String path) {
        return ResourceLocation.withDefaultNamespace(path);
    }
}