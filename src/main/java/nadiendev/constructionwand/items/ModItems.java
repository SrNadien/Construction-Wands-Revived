package nadiendev.constructionwand.items;

import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.neoforge.client.event.RegisterColorHandlersEvent;
import net.neoforged.neoforge.registries.DeferredItem;
import net.neoforged.neoforge.registries.DeferredRegister;
import nadiendev.constructionwand.ConstructionWand;
import nadiendev.constructionwand.basics.option.WandOptions;
import nadiendev.constructionwand.items.core.ItemCoreAngel;
import nadiendev.constructionwand.items.core.ItemCoreDestruction;
import nadiendev.constructionwand.items.wand.ItemWand;
import nadiendev.constructionwand.items.wand.ItemWandBasic;
import nadiendev.constructionwand.items.wand.ItemWandInfinity;

public class ModItems
{
    public static final DeferredRegister.Items ITEMS = DeferredRegister.createItems(ConstructionWand.MODID);

    // Wands
    public static final DeferredItem<Item> WAND_STONE     = ITEMS.register("stone_wand",    () -> new ItemWandBasic(propWand(), 131, r -> r.is(Items.COBBLED_DEEPSLATE) || r.is(Items.COBBLESTONE) || r.is(Items.BLACKSTONE)));
    public static final DeferredItem<Item> WAND_IRON      = ITEMS.register("iron_wand",     () -> new ItemWandBasic(propWand(), 250, r -> r.is(Items.IRON_INGOT)));
    public static final DeferredItem<Item> WAND_DIAMOND   = ITEMS.register("diamond_wand",  () -> new ItemWandBasic(propWand(), 1561, r -> r.is(Items.DIAMOND)));
    public static final DeferredItem<Item> WAND_NETHERITE = ITEMS.register("netherite_wand",() -> new ItemWandBasic(propWand().fireResistant(), 2031, r -> r.is(Items.NETHERITE_INGOT)));
    public static final DeferredItem<Item> WAND_INFINITY  = ITEMS.register("infinity_wand", () -> new ItemWandInfinity(propWand()));

    // Cores
    public static final DeferredItem<Item> CORE_ANGEL      = ITEMS.register("core_angel",      () -> new ItemCoreAngel(propUpgrade()));
    public static final DeferredItem<Item> CORE_DESTRUCTION = ITEMS.register("core_destruction", () -> new ItemCoreDestruction(propUpgrade()));

    // Collections
    @SuppressWarnings("unchecked")
    public static final DeferredItem<Item>[] WANDS = new DeferredItem[]{WAND_STONE, WAND_IRON, WAND_DIAMOND, WAND_NETHERITE, WAND_INFINITY};
    @SuppressWarnings("unchecked")
    public static final DeferredItem<Item>[] CORES = new DeferredItem[]{CORE_ANGEL, CORE_DESTRUCTION};

    public static Item.Properties propWand() {
        return new Item.Properties();
    }

    private static Item.Properties propUpgrade() {
        return new Item.Properties().stacksTo(1);
    }

    @OnlyIn(Dist.CLIENT)
    public static void registerModelProperties() {
        for (DeferredItem<Item> itemSupplier : WANDS) {
            Item item = itemSupplier.get();
            net.minecraft.client.renderer.item.ItemProperties.register(
                    item, ConstructionWand.loc("using_core"),
                    (stack, world, entity, n) -> entity == null || !(stack.getItem() instanceof ItemWand) ? 0 :
                            new WandOptions(stack).cores.get().getColor() > -1 ? 1 : 0
            );
        }
    }

    @OnlyIn(Dist.CLIENT)
    @SubscribeEvent
    public static void registerItemColors(RegisterColorHandlersEvent.Item event) {
        for (DeferredItem<Item> itemSupplier : WANDS) {
            Item item = itemSupplier.get();
            event.register((stack, layer) -> (layer == 1 && stack.getItem() instanceof ItemWand) ?
                    new WandOptions(stack).cores.get().getColor() : -1, item);
        }
    }
}