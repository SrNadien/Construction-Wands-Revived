package nadiendev.constructionwand.client;

import org.lwjgl.glfw.GLFW;

import net.minecraft.client.KeyMapping;
import net.minecraft.client.Minecraft;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.neoforge.client.event.InputEvent;
import nadiendev.constructionwand.ConstructionWand;
import nadiendev.constructionwand.items.containeritems.ItemVoidSack;
import nadiendev.constructionwand.network.PacketToggleVoidSackActive;

public class KeybindHandler {

    public static final KeyMapping KEY_VOID_SACK_TOGGLE = new KeyMapping(
            getKey("void_sack_toggle"),
            GLFW.GLFW_KEY_M,
            getCategory("constructionwand")
    );

    public static final KeyMapping KEY_WAND_UNDO = new KeyMapping(
            getKey("wand_undo"),
            GLFW.GLFW_KEY_K,
            getCategory("constructionwand")
    );

    private static String getKey(String name) {
        return String.join(".", "key", ConstructionWand.MODID, name);
    }

    private static String getCategory(String modid) {
        return "key.categories." + modid;
    }

    @SubscribeEvent
    public void KeyEvent(InputEvent.Key event) {
        Player player = Minecraft.getInstance().player;
        if (player == null) return;

        // Toggle Void Sack con tecla M
        if (KEY_VOID_SACK_TOGGLE.consumeClick()) {
            for (InteractionHand hand : InteractionHand.values()) {
                ItemStack stack = player.getItemInHand(hand);
                if (stack.getItem() instanceof ItemVoidSack) {
                    PacketToggleVoidSackActive.send(hand);
                    break;
                }
            }
        }

        // Undo de varita con tecla K
        if (KEY_WAND_UNDO.consumeClick()) {
            if (nadiendev.constructionwand.basics.WandUtil.holdingWand(player) != null) {
                nadiendev.constructionwand.network.ModMessages.sendToServer(
                        new nadiendev.constructionwand.network.PacketWandUndo());
            }
        }
    }
}
