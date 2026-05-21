package nadiendev.constructionwand.items;

import net.minecraft.world.item.Item;
import net.minecraft.world.item.ToolMaterial;
import net.neoforged.neoforge.registries.DeferredItem;
import net.neoforged.neoforge.registries.DeferredRegister;
import nadiendev.constructionwand.ConstructionWand;
import nadiendev.constructionwand.items.core.ItemCoreAngel;
import nadiendev.constructionwand.items.core.ItemCoreDestruction;
import nadiendev.constructionwand.items.wand.ItemWand;
import nadiendev.constructionwand.items.wand.ItemWandBasic;
import nadiendev.constructionwand.items.wand.ItemWandInfinity;
import nadiendev.constructionwand.items.containeritems.ItemVoidSack;

public class ModItems {

    public static final DeferredRegister.Items ITEMS =
            DeferredRegister.createItems(ConstructionWand.MODID);

    public static final DeferredItem<Item> WAND_STONE =
            ITEMS.registerItem("stone_wand", props -> new ItemWandBasic(props, ToolMaterial.STONE));
    public static final DeferredItem<Item> WAND_IRON =
            ITEMS.registerItem("iron_wand", props -> new ItemWandBasic(props, ToolMaterial.IRON));
    public static final DeferredItem<Item> WAND_DIAMOND =
            ITEMS.registerItem("diamond_wand", props -> new ItemWandBasic(props, ToolMaterial.DIAMOND));
    public static final DeferredItem<Item> WAND_NETHERITE =
            ITEMS.registerItem("netherite_wand", props -> new ItemWandBasic(props.fireResistant(), ToolMaterial.NETHERITE));
    public static final DeferredItem<Item> WAND_INFINITY =
            ITEMS.registerItem("infinity_wand", props -> new ItemWandInfinity(props));

    public static final DeferredItem<Item> CORE_ANGEL       = ITEMS.register("core_angel",       () -> new ItemCoreAngel(new Item.Properties().stacksTo(1)));
    public static final DeferredItem<Item> CORE_DESTRUCTION = ITEMS.register("core_destruction", () -> new ItemCoreDestruction(new Item.Properties().stacksTo(1)));

   public static final DeferredItem<Item> VOID_SACK =
            ITEMS.registerItem("void_sack", props -> new ItemVoidSack(props.stacksTo(1).fireResistant()));


    @SuppressWarnings("unchecked")
    public static final DeferredItem<Item>[] WANDS =
            new DeferredItem[]{WAND_STONE, WAND_IRON, WAND_DIAMOND, WAND_NETHERITE, WAND_INFINITY};
    @SuppressWarnings("unchecked")
    public static final DeferredItem<Item>[] CORES =
            new DeferredItem[]{CORE_ANGEL, CORE_DESTRUCTION};
    @SuppressWarnings("unchecked")
    public static final DeferredItem<Item>[] CONTAINERITEMS =
            new DeferredItem[]{VOID_SACK};
}