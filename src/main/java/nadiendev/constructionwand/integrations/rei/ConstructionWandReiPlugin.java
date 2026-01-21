// package nadiendev.constructionwand.integrations.rei;

// import com.mojang.blaze3d.platform.InputConstants;
// import me.shedaniel.rei.api.client.plugins.REIClientPlugin;
// import me.shedaniel.rei.api.common.util.EntryStacks;
// import net.minecraft.ChatFormatting;
// import net.minecraft.core.registries.BuiltInRegistries;
// import net.minecraft.network.chat.Component;
// import net.minecraft.world.item.Item;
// import net.neoforged.neoforge.registries.DeferredItem;
// import nadiendev.constructionwand.ConstructionWand;
// import nadiendev.constructionwand.basics.ConfigClient;
// import nadiendev.constructionwand.basics.ConfigServer;
// import nadiendev.constructionwand.items.ModItems;

// public class ConstructionWandReiPlugin implements REIClientPlugin {
//     private static final String baseKey = ConstructionWand.MODID + ".description.";
//     private static final String baseKeyItem = "item." + ConstructionWand.MODID + ".";

//     private Component keyComboComponent(boolean shiftOpt, Component optkeyComponent) {
//         String key = shiftOpt ? "sneak_opt" : "sneak";
//         return Component.translatable(baseKey + "key." + key, optkeyComponent).withStyle(ChatFormatting.BLUE);
//     }

    
// }