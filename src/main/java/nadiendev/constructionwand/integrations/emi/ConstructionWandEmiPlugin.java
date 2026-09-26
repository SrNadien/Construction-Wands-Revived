package nadiendev.constructionwand.integrations.emi;

import com.mojang.blaze3d.platform.InputConstants;
import dev.emi.emi.api.EmiEntrypoint;
import dev.emi.emi.api.EmiPlugin;
import dev.emi.emi.api.EmiRegistry;
import dev.emi.emi.api.recipe.EmiInfoRecipe;
import dev.emi.emi.api.stack.EmiIngredient;
import dev.emi.emi.api.stack.EmiStack;
import net.minecraft.ChatFormatting;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.neoforged.neoforge.registries.DeferredItem;
import nadiendev.constructionwand.ConstructionWand;
import nadiendev.constructionwand.basics.ConfigClient;
import nadiendev.constructionwand.basics.ConfigServer;
import nadiendev.constructionwand.client.KeybindHandler;
import nadiendev.constructionwand.items.ModItems;

import java.util.List;

@EmiEntrypoint
public class ConstructionWandEmiPlugin implements EmiPlugin
{
    private static final String baseKey = ConstructionWand.MODID + ".description.";
    private static final String baseKeyItem = "item." + ConstructionWand.MODID + ".";

    private Component keyComboComponent(boolean shiftOpt, Component optkeyComponent) {
        String key = shiftOpt ? "sneak_opt" : "sneak";
        return Component.translatable(baseKey + "key." + key, optkeyComponent).withStyle(ChatFormatting.BLUE);
    }

    @Override
    public void register(EmiRegistry registry) {
        Component optkeyComponent = Component.translatable(InputConstants.getKey(ConfigClient.OPT_KEY.get(), -1).getName())
                .withStyle(ChatFormatting.BLUE);
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

    private void addInfo(EmiRegistry registry, Item item, Component text) {
        ResourceLocation itemId = BuiltInRegistries.ITEM.getKey(item);
        if(itemId == null) return;

        registry.addRecipe(new EmiInfoRecipe(
                List.<EmiIngredient>of(EmiStack.of(item)),
                List.of(text),
                ConstructionWand.loc("info/" + itemId.getPath())));
    }
}
