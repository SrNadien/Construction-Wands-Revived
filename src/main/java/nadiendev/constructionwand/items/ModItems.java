package nadiendev.constructionwand.items;

import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
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

    public static final DeferredItem<Item> WAND_STONE     = ITEMS.registerItem("stone_wand",     props -> new ItemWandBasic(props, 131), new Item.Properties());
    public static final DeferredItem<Item> WAND_IRON      = ITEMS.registerItem("iron_wand",      props -> new ItemWandBasic(props, 250), new Item.Properties());
    public static final DeferredItem<Item> WAND_DIAMOND   = ITEMS.registerItem("diamond_wand",   props -> new ItemWandBasic(props, 1561), new Item.Properties());
    public static final DeferredItem<Item> WAND_NETHERITE = ITEMS.registerItem("netherite_wand", props -> new ItemWandBasic(props, 2031), new Item.Properties().fireResistant());
    public static final DeferredItem<Item> WAND_INFINITY  = ITEMS.registerItem("infinity_wand",  ItemWandInfinity::new, new Item.Properties());

    public static final DeferredItem<Item> CORE_ANGEL      = ITEMS.registerItem("core_angel",      ItemCoreAngel::new, new Item.Properties().stacksTo(1));
    public static final DeferredItem<Item> CORE_DESTRUCTION = ITEMS.registerItem("core_destruction", ItemCoreDestruction::new, new Item.Properties().stacksTo(1));

    @SuppressWarnings("unchecked")
    public static final DeferredItem<Item>[] WANDS = new DeferredItem[]{WAND_STONE, WAND_IRON, WAND_DIAMOND, WAND_NETHERITE, WAND_INFINITY};
    @SuppressWarnings("unchecked")
    public static final DeferredItem<Item>[] CORES = new DeferredItem[]{CORE_ANGEL, CORE_DESTRUCTION};

    public static void registerModelProperties() {
    }

    public static void registerItemColors() {
    }
}
