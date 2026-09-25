package nadiendev.constructionwand.client;

import net.minecraft.client.Minecraft;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.neoforged.neoforge.client.network.ClientPacketDistributor;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.neoforge.client.event.InputEvent;
import net.neoforged.bus.api.EventPriority;
import net.neoforged.neoforge.event.entity.player.PlayerInteractEvent;
import nadiendev.constructionwand.basics.ConfigClient;
import nadiendev.constructionwand.client.KeybindHandler;
import nadiendev.constructionwand.basics.WandUtil;
import nadiendev.constructionwand.basics.option.WandOptions;
import nadiendev.constructionwand.items.wand.ItemWand;
import nadiendev.constructionwand.network.PacketQueryUndo;
import nadiendev.constructionwand.network.PacketWandOption;

public class ClientEvents
{
    private boolean optPressed;

    public ClientEvents() {
        optPressed = false;
    }

    @SubscribeEvent
    public void KeyEvent(InputEvent.Key event) {
        Player player = Minecraft.getInstance().player;
        if(player == null) return;
        if(WandUtil.holdingWand(player) == null) return;

        boolean optState = isOptKeyDown();
        if(optPressed != optState) {
            optPressed = optState;
            ClientPacketDistributor.sendToServer(new PacketQueryUndo(optPressed, isLeftShiftKeyDown()));
        }
    }

    @SubscribeEvent(priority = EventPriority.HIGHEST)
    public void MouseScrollEvent(InputEvent.MouseScrollingEvent event) {
        Player player = Minecraft.getInstance().player;
        double scroll = event.getScrollDeltaY();

        if(player == null || !modeKeyCombDown(player) || scroll == 0) return;

        ItemStack wand = WandUtil.holdingWand(player);
        if(wand == null) return;

        WandOptions wandOptions = new WandOptions(wand);
        wandOptions.lock.next(scroll < 0);
        ClientPacketDistributor.sendToServer(new PacketWandOption(wandOptions.lock, true));
        event.setCanceled(true);
    }

    @SubscribeEvent
    public void onLeftClickEmpty(PlayerInteractEvent.LeftClickEmpty event) {
        Player player = event.getEntity();

        if(player == null || !modeKeyCombDown(player)) return;

        ItemStack wand = event.getItemStack();
        if(!(wand.getItem() instanceof ItemWand)) return;

        WandOptions wandOptions = new WandOptions(wand);
        wandOptions.cores.next();
        ClientPacketDistributor.sendToServer(new PacketWandOption(wandOptions.cores, true));
    }

    @SubscribeEvent
    public void onRightClickItem(PlayerInteractEvent.RightClickItem event) {
        if(event.getSide().isServer()) return;

        Player player = event.getEntity();
        if(player == null || !guiKeyCombDown(player)) return;

        ItemStack wand = event.getItemStack();
        if(!(wand.getItem() instanceof ItemWand)) return;

        Minecraft.getInstance().gui.setScreen(new ScreenWand(wand));
        event.setCanceled(true);
    }

    private static boolean isLeftShiftKeyDown() {
        return Minecraft.getInstance().options.keyShift.isDown();
    }

    public static boolean isOptKeyDown() {
        return KeybindHandler.isOptKeyDown();
    }

    public static boolean modeKeyCombDown(Player player) {
        return player.isCrouching() && (isOptKeyDown() || !ConfigClient.SHIFTOPT_MODE.get());
    }

    public static boolean guiKeyCombDown(Player player) {
        return player.isCrouching() && (isOptKeyDown() || !ConfigClient.SHIFTOPT_GUI.get());
    }
}
