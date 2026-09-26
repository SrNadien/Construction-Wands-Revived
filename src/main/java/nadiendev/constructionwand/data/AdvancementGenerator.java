package nadiendev.constructionwand.data;

import net.minecraft.advancements.Advancement;
import net.minecraft.advancements.DisplayInfo;
import net.minecraft.advancements.FrameType;
import net.minecraft.advancements.critereon.InventoryChangeTrigger;
import net.minecraft.advancements.critereon.ItemPredicate;
import net.minecraft.data.PackOutput;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.ItemLike;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.common.data.ForgeAdvancementProvider;
import net.minecraftforge.registries.RegistryObject;
import nadiendev.constructionwand.ConstructionWand;
import nadiendev.constructionwand.items.ModItems;

import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.function.Consumer;

public class AdvancementGenerator extends ForgeAdvancementProvider
{
    public AdvancementGenerator(PackOutput output, CompletableFuture<net.minecraft.core.HolderLookup.Provider> registries, ExistingFileHelper existingFileHelper) {
        super(output, registries, existingFileHelper, List.of(new WandAdvancementGenerator()));
    }

    public static class WandAdvancementGenerator implements AdvancementGenerator
    {
        @Override
        public void generate(net.minecraft.core.HolderLookup.Provider registries, Consumer<Advancement> consumer, ExistingFileHelper existingFileHelper) {

            Advancement root = Advancement.Builder.advancement()
                    .display(rootDisplay(
                            ModItems.WAND_STONE.get(),
                            advancementPrefix("root.title"),
                            advancementPrefix("root.desc"),
                            mcLoc("textures/block/oak_planks.png")
                    ))
                    .addCriterion("wand", InventoryChangeTrigger.TriggerInstance.hasItems(
                            ItemPredicate.Builder.item().of(ModItems.WAND_STONE.get()).build()
                    ))
                    .save(consumer, rootID("root"), existingFileHelper);

            onHasItem(consumer, existingFileHelper, ModItems.WAND_WOOD,        FrameType.TASK, root);
            onHasItem(consumer, existingFileHelper, ModItems.WAND_STONE,       FrameType.TASK, root);
            onHasItem(consumer, existingFileHelper, ModItems.WAND_IRON,        FrameType.TASK, root);
            onHasItem(consumer, existingFileHelper, ModItems.WAND_GOLD,        FrameType.TASK, root);
            onHasItem(consumer, existingFileHelper, ModItems.WAND_DIAMOND,     FrameType.TASK, root);
            onHasItem(consumer, existingFileHelper, ModItems.WAND_NETHERITE,   FrameType.TASK, root);
            onHasItem(consumer, existingFileHelper, ModItems.WAND_INFINITY,    FrameType.GOAL, root);
            onHasItem(consumer, existingFileHelper, ModItems.CORE_ANGEL,       FrameType.TASK, root);
            onHasItem(consumer, existingFileHelper, ModItems.CORE_DESTRUCTION, FrameType.TASK, root);
            onHasItem(consumer, existingFileHelper, ModItems.CORE_EXCHANGE,    FrameType.TASK, root);
            onHasItem(consumer, existingFileHelper, ModItems.VOID_SACK,        FrameType.TASK, root);
        }

        private static void onHasItem(Consumer<Advancement> consumer, ExistingFileHelper fileHelper,
                                      RegistryObject<Item> item, FrameType type, Advancement parent) {
            String path = item.getId().getPath();
            Advancement.Builder.advancement()
                    .display(simpleDisplay(item.get(), path, type))
                    .parent(parent)
                    .addCriterion(path, InventoryChangeTrigger.TriggerInstance.hasItems(item.get()))
                    .save(consumer, rootID(path), fileHelper);
        }

        private static DisplayInfo rootDisplay(ItemLike icon, String titleKey, String descKey, ResourceLocation background) {
            return new DisplayInfo(
                    new ItemStack(icon),
                    Component.translatable(titleKey),
                    Component.translatable(descKey),
                    background, FrameType.TASK, false, false, false
            );
        }

        private static DisplayInfo simpleDisplay(ItemLike icon, String name, FrameType type) {
            return new DisplayInfo(
                    new ItemStack(icon),
                    Component.translatable(advancementPrefix(name + ".title")),
                    Component.translatable(advancementPrefix(name + ".desc")),
                    null, type, true, true, false
            );
        }

        private static String advancementPrefix(String name) {
            return "advancement." + ConstructionWand.MODID + "." + name;
        }

        private static ResourceLocation rootID(String name) {
            return ConstructionWand.loc(name);
        }

        private static ResourceLocation mcLoc(String path) {
            return new ResourceLocation("minecraft", path);
        }
    }
}
