package nadiendev.constructionwand.integrations.rrv;

import cc.cassian.rrv.api.recipe.ItemView;
import cc.cassian.rrv.common.builtin.info.InfoClientRecipe;
import cc.cassian.rrv.common.recipe.inventory.SlotContent;
import net.minecraft.ChatFormatting;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.Identifier;
import net.minecraft.world.item.Item;
import net.neoforged.neoforge.registries.DeferredHolder;
import nadiendev.constructionwand.ConstructionWand;
import nadiendev.constructionwand.basics.ConfigClient;
import nadiendev.constructionwand.basics.ConfigServer;
import nadiendev.constructionwand.client.KeybindHandler;
import nadiendev.constructionwand.items.ModItems;

public final class RrvIntegration {
    private static final String baseKey = ConstructionWand.MODID + ".description.";
    private static final String baseKeyItem = "item." + ConstructionWand.MODID + ".";

    private RrvIntegration() {}

    public static void init() {
        // Los textos dependen de los keybinds y de la config, que pueden cambiar en
        // caliente, asi que se reconstruyen en cada recarga del cliente.
        ItemView.addClientReloadCallback(RrvIntegration::registerInfo);
    }

    private static Component keyComboComponent(boolean shiftOpt, Component optkeyComponent) {
        String key = shiftOpt ? "sneak_opt" : "opt";
        return Component.translatable(baseKey + "key." + key, optkeyComponent).withStyle(ChatFormatting.BLUE);
    }

    private static void registerInfo() {
        Component optkeyComponent = KeybindHandler.KEY_OPT.getTranslatedKeyMessage().copy().withStyle(ChatFormatting.BLUE);
        Component wandModeComponent = keyComboComponent(ConfigClient.SHIFTOPT_MODE.get(), optkeyComponent);
        Component wandGuiComponent = keyComboComponent(ConfigClient.SHIFTOPT_GUI.get(), optkeyComponent);

        for(DeferredHolder<Item, Item> wandSupplier : ModItems.WANDS) {
            Item wand = wandSupplier.get();
            ConfigServer.WandProperties wandProperties = ConfigServer.getWandProperties(wand);

            String durabilityKey = wand == ModItems.WAND_INFINITY.get() ? "unlimited" : "limited";
            Component durabilityComponent = Component.translatable(baseKey + "durability." + durabilityKey, wandProperties.getDurability());

            addInfo(wand, Component.translatable(baseKey + "wand",
                    Component.translatable(baseKeyItem + BuiltInRegistries.ITEM.getKey(wand).getPath()),
                    wandProperties.getLimit(), durabilityComponent, optkeyComponent, wandModeComponent, wandGuiComponent));
        }

        for(DeferredHolder<Item, Item> coreSupplier : ModItems.CORES) {
            Item core = coreSupplier.get();
            addInfo(core, Component.translatable(baseKey + BuiltInRegistries.ITEM.getKey(core).getPath())
                    .append("\n\n")
                    .append(Component.translatable(baseKey + "core", wandModeComponent)));
        }

        Component mKeyComponent = KeybindHandler.KEY_VOID_SACK_TOGGLE
                .getTranslatedKeyMessage()
                .copy().withStyle(ChatFormatting.GOLD);

        addInfo(ModItems.VOID_SACK.get(), Component.translatable(baseKey + "void_sack", mKeyComponent));
    }

    private static void addInfo(Item item, Component text) {
        Identifier itemId = BuiltInRegistries.ITEM.getKey(item);
        if(itemId == null) return;

        ItemView.addInfoRecipe(new InfoClientRecipe(
                ConstructionWand.loc("info/" + itemId.getPath()),
                SlotContent.of(item),
                text));
    }
}
