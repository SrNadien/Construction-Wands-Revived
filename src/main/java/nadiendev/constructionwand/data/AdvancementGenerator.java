package nadiendev.constructionwand.data;

import nadiendev.constructionwand.ConstructionWand;
import nadiendev.constructionwand.items.ModItems;
import net.minecraft.advancements.Advancement;
import net.minecraft.advancements.AdvancementHolder;
import net.minecraft.advancements.AdvancementType;
import net.minecraft.advancements.DisplayInfo;
import net.minecraft.advancements.critereon.InventoryChangeTrigger;
import net.minecraft.advancements.critereon.ItemPredicate;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.ItemLike;
import net.neoforged.neoforge.common.data.AdvancementProvider;
import net.neoforged.neoforge.common.data.ExistingFileHelper;
import net.neoforged.neoforge.registries.DeferredItem;
import net.minecraft.world.item.Item;

import java.util.List;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;
import java.util.function.Consumer;

public class AdvancementGenerator extends AdvancementProvider {

    public AdvancementGenerator(PackOutput output, CompletableFuture<HolderLookup.Provider> registries, ExistingFileHelper existingFileHelper) {
        super(output, registries, existingFileHelper, List.of(new WandAdvancementGenerator()));
    }

    public static class WandAdvancementGenerator implements AdvancementGenerator {
        @Override
        public void generate(HolderLookup.Provider registries, Consumer<AdvancementHolder> consumer, ExistingFileHelper existingFileHelper) {

            AdvancementHolder root = Advancement.Builder.advancement()
                    .display(rootDisplay(
                            ModItems.WAND_STONE.get(),
                            advancementPrefix("root.title"),
                            advancementPrefix("root.desc"),
                            mcLoc("textures/block/oak_planks.png")
                    ))
                    .addCriterion("wand", InventoryChangeTrigger.TriggerInstance.hasItems(
                            ItemPredicate.Builder.item().of(ModItems.WAND_STONE.get()).build()
                    ))
                    .save(consumer, rootID("root"));

            onHasItem(consumer, ModItems.WAND_STONE,    AdvancementType.TASK, root);
            onHasItem(consumer, ModItems.WAND_IRON,     AdvancementType.TASK, root);
            onHasItem(consumer, ModItems.WAND_DIAMOND,  AdvancementType.TASK, root);
            onHasItem(consumer, ModItems.WAND_NETHERITE,AdvancementType.TASK, root);
            onHasItem(consumer, ModItems.WAND_INFINITY, AdvancementType.GOAL, root);
            onHasItem(consumer, ModItems.CORE_ANGEL,     AdvancementType.TASK, root);
            onHasItem(consumer, ModItems.CORE_DESTRUCTION, AdvancementType.TASK, root);
<<<<<<< Updated upstream
=======
            onHasItem(consumer, ModItems.VOID_SACK,        AdvancementType.TASK, root);
>>>>>>> Stashed changes
        }

        private static void onHasItem(Consumer<AdvancementHolder> consumer, DeferredItem<Item> item,
                                      AdvancementType type, AdvancementHolder parent) {
            String path = item.getId().getPath();
            Advancement.Builder.advancement()
                    .display(simpleDisplay(item.get(), path, type))
                    .parent(parent)
                    .addCriterion(path, InventoryChangeTrigger.TriggerInstance.hasItems(item.get()))
                    .save(consumer, rootID(path));
        }

        private static DisplayInfo rootDisplay(ItemLike icon, String titleKey, String descKey, ResourceLocation background) {
            return new DisplayInfo(
                    new ItemStack(icon),
                    Component.translatable(titleKey),
                    Component.translatable(descKey),
                    Optional.of(background), AdvancementType.TASK, false, false, false
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

        private static String rootID(String name) {
            return modLoc(name).toString();
        }

        private static ResourceLocation modLoc(String path) {
            return ConstructionWand.loc(path);
        }

        private static ResourceLocation mcLoc(String path) {
            return ResourceLocation.withDefaultNamespace(path);
        }
    }
}