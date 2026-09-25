package nadiendev.constructionwand.data;

import nadiendev.constructionwand.ConstructionWand;
import nadiendev.constructionwand.items.ModItems;
import net.minecraft.advancements.Advancement;
import net.minecraft.advancements.AdvancementHolder;
import net.minecraft.advancements.AdvancementType;
import net.minecraft.advancements.triggers.Criterion;
import net.minecraft.advancements.DisplayInfo;
import net.minecraft.advancements.triggers.InventoryChangeTrigger;
import net.minecraft.core.ClientAsset;
import net.minecraft.data.advancements.AdvancementProvider;
import net.minecraft.data.advancements.AdvancementSubProvider;
import net.minecraft.data.worldgen.BootstrapContext;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.Identifier;
import net.minecraft.world.item.ItemStackTemplate;
import net.minecraft.world.level.ItemLike;
import net.neoforged.neoforge.registries.DeferredItem;
import net.minecraft.world.item.Item;

import java.util.List;
import java.util.Optional;

public class AdvancementGenerator extends AdvancementProvider {

    public AdvancementGenerator() {
        super(List.of(WandAdvancementGenerator::new));
    }

    public static class WandAdvancementGenerator extends AdvancementSubProvider {

        public WandAdvancementGenerator(BootstrapContext<Advancement> output) {
            super(output);
        }

        @Override
        public void generate() {
            AdvancementHolder root = Advancement.Builder.advancement()
                    .display(rootDisplay(
                            ModItems.WAND_STONE.get(),
                            advancementPrefix("root.title"),
                            advancementPrefix("root.desc"),
                            Identifier.withDefaultNamespace("textures/block/stone.png")))
                    .addCriterion("stone_wand", hasItemsTrigger(ModItems.WAND_STONE.get()))
                    .save(output, rootID("root"));

            AdvancementHolder woodWand    = onHasItem(output, ModItems.WAND_WOOD,       AdvancementType.TASK,      root);
            AdvancementHolder stoneWand   = onHasItem(output, ModItems.WAND_STONE,      AdvancementType.TASK,      woodWand);
            AdvancementHolder ironWand    = onHasItem(output, ModItems.WAND_IRON,       AdvancementType.TASK,      stoneWand);
            AdvancementHolder goldWand    = onHasItem(output, ModItems.WAND_GOLD,       AdvancementType.TASK,      ironWand);
            AdvancementHolder diamondWand = onHasItem(output, ModItems.WAND_DIAMOND,    AdvancementType.TASK,      goldWand);
            AdvancementHolder netherWand  = onHasItem(output, ModItems.WAND_NETHERITE,  AdvancementType.TASK,      diamondWand);
            AdvancementHolder infWand     = onHasItem(output, ModItems.WAND_INFINITY,   AdvancementType.CHALLENGE, netherWand);

            AdvancementHolder angelCore      = onHasItem(output, ModItems.CORE_ANGEL,       AdvancementType.TASK, stoneWand);
            AdvancementHolder destructionCore = onHasItem(output, ModItems.CORE_DESTRUCTION, AdvancementType.TASK, stoneWand);
            AdvancementHolder exchangeCore   = onHasItem(output, ModItems.CORE_EXCHANGE,    AdvancementType.TASK, stoneWand);

            AdvancementHolder voidSack        = onHasItem(output, ModItems.VOID_SACK,        AdvancementType.TASK, root);
        }

        protected static AdvancementHolder onHasItem(BootstrapContext<Advancement> output,
                                                     DeferredItem<Item> iconItem,
                                                     AdvancementType type,
                                                     AdvancementHolder parent) {
            String path = iconItem.getId().getPath();
            DisplayInfo info = simpleDisplay(iconItem.get(), path, type);
            return Advancement.Builder.advancement()
                    .display(info)
                    .parent(parent)
                    .addCriterion(path, hasItemsTrigger(iconItem.get()))
                    .save(output, rootID(path));
        }

        protected static Criterion<InventoryChangeTrigger.TriggerInstance> hasItemsTrigger(ItemLike... items) {
            return InventoryChangeTrigger.TriggerInstance.hasItems(items);
        }

        protected static DisplayInfo rootDisplay(ItemLike icon, String titleKey, String descKey, Identifier background) {
            return new DisplayInfo(
                    new ItemStackTemplate(icon.asItem()),
                    Component.translatable(titleKey),
                    Component.translatable(descKey),
                    Optional.of(new ClientAsset.ResourceTexture(background)),
                    AdvancementType.TASK, false, false, false);
        }

        protected static DisplayInfo simpleDisplay(ItemLike icon, String name, AdvancementType type) {
            return new DisplayInfo(
                    new ItemStackTemplate(icon.asItem()),
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
