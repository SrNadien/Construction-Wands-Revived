package nadiendev.constructionwand.client;

import org.lwjgl.glfw.GLFW;

import net.minecraft.client.KeyMapping;
import net.minecraft.client.Minecraft;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.InteractionHand;
import net.neoforged.neoforge.client.network.ClientPacketDistributor;
import net.neoforged.neoforge.client.event.InputEvent;
import net.neoforged.neoforge.event.entity.player.PlayerInteractEvent;
import net.neoforged.bus.api.EventPriority;
import net.neoforged.bus.api.SubscribeEvent;
import nadiendev.constructionwand.ConstructionWand;
import nadiendev.constructionwand.basics.ConfigClient;
import nadiendev.constructionwand.basics.WandUtil;
import nadiendev.constructionwand.basics.option.WandOptions;
import nadiendev.constructionwand.items.wand.ItemWand;
import nadiendev.constructionwand.network.PacketQueryUndo;
import nadiendev.constructionwand.network.PacketWandOption;
import nadiendev.constructionwand.items.containeritems.ItemVoidSack;
import nadiendev.constructionwand.network.PacketToggleVoidSackActive;

public class KeybindHandler {
    // TODO: Need a lang
    public static final KeyMapping.Category CATEGORY = new KeyMapping.Category(ConstructionWand.loc("category"));
    public static final KeyMapping KEY_OPT = new KeyMapping(getKey("wand_option"), GLFW.GLFW_KEY_LEFT_CONTROL, CATEGORY);

     public static final KeyMapping KEY_VOID_SACK_TOGGLE = new KeyMapping(
            getKey("void_sack_toggle"),
            GLFW.GLFW_KEY_M,
            CATEGORY
    );

    private static String getKey(String name) {
		return String.join(".", "key", ConstructionWand.MODID, name);
	}

	public KeybindHandler() {
        leftShiftPressed = false;
        optPressed = false;
	}

    private boolean leftShiftPressed;
    private boolean optPressed;

    @SubscribeEvent
    public void KeyEvent(InputEvent.Key event) {
        Player player = Minecraft.getInstance().player;
        if(player == null) return;
        
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
        if(WandUtil.holdingWand(player) == null) return;

        boolean optState = isOptKeyDown();
        boolean leftShiftState = isLeftShiftKeyDown();
        if(optPressed != optState || leftShiftPressed != leftShiftState) {
            optPressed = optState;
            leftShiftPressed = leftShiftState;
            ClientPacketDistributor.sendToServer(new PacketQueryUndo(optPressed, leftShiftPressed));
            //ConstructionWand.LOGGER.debug("OPT key update: " + optPressed);
        }
    }

    // (Sneak)+OPT+Scroll to change direction lock
    @SubscribeEvent(priority = EventPriority.HIGHEST)
    public void MouseScrollEvent(InputEvent.MouseScrollingEvent event) {
        Player player = Minecraft.getInstance().player;
        double scroll = event.getScrollDeltaY();
        if (scroll == 0.0D) {
            scroll = event.getScrollDeltaX();
        }

        if(!modeKeyCombDown() || scroll == 0) return;

        ItemStack wand = WandUtil.holdingWand(player);
        if(wand == null) return;

        WandOptions wandOptions = new WandOptions(wand);
        wandOptions.lock.next(scroll < 0);
        ClientPacketDistributor.sendToServer(new PacketWandOption(wandOptions.lock, true));
        event.setCanceled(true);
    }

    // (Sneak)+OPT+Left click wand to change core
    @SubscribeEvent
    public void onLeftClickEmpty(PlayerInteractEvent.LeftClickEmpty event) {
        Player player = event.getEntity();

        if(!modeKeyCombDown()) return;

        ItemStack wand = event.getItemStack();
        if(!(wand.getItem() instanceof ItemWand)) return;

        WandOptions wandOptions = new WandOptions(wand);
        wandOptions.cores.next();
        ClientPacketDistributor.sendToServer(new PacketWandOption(wandOptions.cores, true));
    }

    // (Sneak)+OPT+Right click wand to open GUI
    @SubscribeEvent
    public void onRightClickItem(PlayerInteractEvent.RightClickItem event) {
        if(event.getSide().isServer()) return;

        if(!guiKeyCombDown()) return;

        ItemStack wand = event.getItemStack();
        if(!(wand.getItem() instanceof ItemWand)) return;

        Minecraft.getInstance().setScreen(new ScreenWand(wand));
        event.setCanceled(true);
    }

    public static boolean isLeftShiftKeyDown() {
        return Minecraft.getInstance().options.keyShift.isDown();
    }

    public static boolean isOptKeyDown() {
        return KEY_OPT.isDown();
    }

    public static boolean modeKeyCombDown() {
        return isOptKeyDown() && (isLeftShiftKeyDown() || !ConfigClient.SHIFTOPT_MODE.get());
    }

    public static boolean guiKeyCombDown() {
        return isOptKeyDown() && (isLeftShiftKeyDown() || !ConfigClient.SHIFTOPT_GUI.get());
    }
<<<<<<< Updated upstream
}
=======
}
>>>>>>> Stashed changes
