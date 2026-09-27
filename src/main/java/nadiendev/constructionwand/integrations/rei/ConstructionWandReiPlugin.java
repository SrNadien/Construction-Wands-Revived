package nadiendev.constructionwand.integrations.rei;

import me.shedaniel.rei.api.client.plugins.REIClientPlugin;
import me.shedaniel.rei.api.client.registry.display.DisplayRegistry;
import me.shedaniel.rei.api.common.util.EntryStacks;
import me.shedaniel.rei.forge.REIPluginClient;
import me.shedaniel.rei.plugin.common.displays.DefaultInformationDisplay;
import net.minecraft.ChatFormatting;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.neoforged.neoforge.registries.DeferredItem;
import nadiendev.constructionwand.ConstructionWand;
import nadiendev.constructionwand.basics.ConfigClient;
import nadiendev.constructionwand.basics.ConfigServer;
import nadiendev.constructionwand.client.KeybindHandler;
import nadiendev.constructionwand.items.ModItems;

@REIPluginClient
public class ConstructionWandReiPlugin implements REIClientPlugin
{
    private static final String baseKey = ConstructionWand.MODID + ".description.";
    private static final String baseKeyItem = "item." + ConstructionWand.MODID + ".";

    private Component keyComboComponent(boolean shiftOpt, Component optkeyComponent) {
        String key = shiftOpt ? "sneak_opt" : "opt";
        return Component.translatable(baseKey + "key." + key, optkeyComponent).withStyle(ChatFormatting.BLUE);
    }

    @Override
    public void registerDisplays(DisplayRegistry registry) {
        Component optkeyComponent = KeybindHandler.KEY_OPT.getTranslatedKeyMessage().copy().withStyle(ChatFormatting.BLUE);
        Component wandModeComponent = keyComboComponent(ConfigClient.SHIFTOPT_MODE.get(), optkeyComponent);
        Component wandGuiComponent = keyComboComponent(ConfigClient.SHIFTOPT_GUI.get(), optkeyComponent);

        for(DeferredItem<Item> wandSupplier : ModItems.WANDS) {
            Item wand = wandSupplier.get();
            ConfigServer.WandProperties wandProperties = ConfigServer.getWandProperties(wand);

            String durabilityKey = wand == ModItems.WAND_INFINITY.get() ? "unlimited" : "limited";
            Component durabilityComponent = Component.translatable(baseKey + "durability." + durabilityKey, wandProperties.getDurability());

            addInfo(registry, wand, Component.translatable(baseKey + "wand",
                    Component.translatable(baseKeyItem + BuiltInRegistries.ITEM.getKey(wand).getPath()),
                    wandProperties.getLimit(), durabilityComponent, optkeyComponent, wandModeComponent, wandGuiComponent));
        }

        for(DeferredItem<Item> coreSupplier : ModItems.CORES) {
            Item core = coreSupplier.get();
            addInfo(registry, core, Component.translatable(baseKey + BuiltInRegistries.ITEM.getKey(core).getPath())
                    .append("\n\n")
                    .append(Component.translatable(baseKey + "core", wandModeComponent)));
        }

        Component mKeyComponent = KeybindHandler.KEY_VOID_SACK_TOGGLE
                .getTranslatedKeyMessage()
                .copy().withStyle(ChatFormatting.GOLD);

        addInfo(registry, ModItems.VOID_SACK.get(), Component.translatable(baseKey + "void_sack", mKeyComponent));
    }

    private void addInfo(DisplayRegistry registry, Item item, Component text) {
        registry.add(DefaultInformationDisplay
                .createFromEntry(EntryStacks.of(item), new ItemStack(item).getHoverName())
                .line(text));
    }
}
