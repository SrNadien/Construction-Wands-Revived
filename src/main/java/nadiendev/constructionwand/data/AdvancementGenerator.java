package nadiendev.constructionwand.data;

import nadiendev.constructionwand.ConstructionWand;
import nadiendev.constructionwand.items.ModItems;
import net.minecraft.advancements.Advancement;
import net.minecraft.advancements.AdvancementHolder;
import net.minecraft.advancements.AdvancementType;
import net.minecraft.advancements.Criterion;
import net.minecraft.advancements.DisplayInfo;
import net.minecraft.advancements.criterion.InventoryChangeTrigger;
import net.minecraft.core.ClientAsset;
import net.minecraft.core.HolderLookup.Provider;
import net.minecraft.data.PackOutput;
import net.minecraft.data.advancements.AdvancementProvider;
import net.minecraft.data.advancements.AdvancementSubProvider;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.Identifier;
import net.minecraft.world.item.ItemStack; 
import net.minecraft.world.level.ItemLike;
import net.neoforged.neoforge.registries.DeferredItem;
import net.minecraft.world.item.Item;

import java.util.List;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;
import java.util.function.Consumer;

public class AdvancementGenerator extends AdvancementProvider {

    public AdvancementGenerator(PackOutput output, CompletableFuture<Provider> registries) {
        super(output, registries, List.of(new WandAdvancementGenerator()));
    }

    public static class WandAdvancementGenerator implements AdvancementSubProvider {

        @Override
        public void generate(Provider registries, Consumer<AdvancementHolder> consumer) {
            AdvancementHolder root = Advancement.Builder.advancement()
                    .display(rootDisplay(
                            ModItems.WAND_STONE.get(),
                            advancementPrefix("root.title"),
                            advancementPrefix("root.desc"),
                            Identifier.withDefaultNamespace("textures/block/stone.png")))
                    .addCriterion("stone_wand", hasItemsTrigger(ModItems.WAND_STONE.get()))
                    .save(consumer, rootID("root"));

            AdvancementHolder stoneWand    = onHasItem(consumer, ModItems.WAND_STONE,       AdvancementType.TASK,      root);
            AdvancementHolder ironWand     = onHasItem(consumer, ModItems.WAND_IRON,        AdvancementType.TASK,      stoneWand);
            AdvancementHolder diamondWand  = onHasItem(consumer, ModItems.WAND_DIAMOND,     AdvancementType.TASK,      ironWand);
            AdvancementHolder netherWand   = onHasItem(consumer, ModItems.WAND_NETHERITE,   AdvancementType.TASK,      diamondWand);
            AdvancementHolder infWand      = onHasItem(consumer, ModItems.WAND_INFINITY,    AdvancementType.CHALLENGE, netherWand);

            AdvancementHolder angelCore       = onHasItem(consumer, ModItems.CORE_ANGEL,       AdvancementType.TASK, stoneWand);
            AdvancementHolder destructionCore = onHasItem(consumer, ModItems.CORE_DESTRUCTION, AdvancementType.TASK, stoneWand);
            AdvancementHolder voidSack        = onHasItem(consumer, ModItems.VOID_SACK,        AdvancementType.TASK, root);
        }

        protected static AdvancementHolder onHasItem(Consumer<AdvancementHolder> consumer,
                                                     DeferredItem<Item> iconItem,
                                                     AdvancementType type,
                                                     AdvancementHolder parent) {
            String path = iconItem.getId().getPath();
            DisplayInfo info = simpleDisplay(iconItem.get(), path, type);
            return Advancement.Builder.advancement()
                    .display(info)
                    .parent(parent)
                    .addCriterion(path, hasItemsTrigger(iconItem.get()))
                    .save(consumer, rootID(path));
        }

        protected static Criterion<InventoryChangeTrigger.TriggerInstance> hasItemsTrigger(ItemLike... items) {
            return InventoryChangeTrigger.TriggerInstance.hasItems(items);
        }

        protected static DisplayInfo rootDisplay(ItemLike icon, String titleKey, String descKey, Identifier background) {
            return new DisplayInfo(
                    new ItemStack(icon.asItem()),
                    Component.translatable(titleKey),
                    Component.translatable(descKey),
                    Optional.of(new ClientAsset.ResourceTexture(background)),
                    AdvancementType.TASK, false, false, false);
        }

        protected static DisplayInfo simpleDisplay(ItemLike icon, String name, AdvancementType type) {
            return new DisplayInfo(
                    new ItemStack(icon.asItem()),
                    Component.translatable(advancementPrefix(name + ".title")),
                    Component.translatable(advancementPrefix(name + ".desc")),
                    Optional.empty(),
                    type, true, true, false);
        }

        private static String advancementPrefix(String name) {
            return "advancement." + ConstructionWand.MODID + "." + name;
        }

        private static String rootID(String name) {
            return Identifier.fromNamespaceAndPath(ConstructionWand.MODID, name).toString();
        }
    }
}