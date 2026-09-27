package nadiendev.constructionwand.containers.handlers;

import appeng.api.config.Actionable;
import appeng.api.networking.security.IActionSource;
import appeng.api.stacks.AEItemKey;
import appeng.api.storage.MEStorage;
import appeng.api.storage.StorageHelper;
import appeng.helpers.WirelessTerminalMenuHost;
import appeng.items.tools.powered.WirelessCraftingTerminalItem;
import appeng.items.tools.powered.WirelessTerminalItem;
import appeng.menu.locator.ItemMenuHostLocator;
import appeng.menu.locator.MenuLocators;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import nadiendev.constructionwand.api.IContainerHandler;
import nadiendev.constructionwand.containers.ContainerTrace;
import top.theillusivec4.curios.api.CuriosApi;

public class HandlerWirelessTerminal implements IContainerHandler {

    @Override
    public boolean matches(Player player, ItemStack itemStack, ItemStack inventoryStack) {
        return inventoryStack.getItem() instanceof WirelessTerminalItem
                || inventoryStack.getItem() instanceof WirelessCraftingTerminalItem;
    }

    @Override
    public int getSignature(Player player, ItemStack inventoryStack) {
        return inventoryStack.getComponents().hashCode();
    }

    @Override
    public int countItems(Player player, ContainerTrace trace, ItemStack target, ItemStack terminal) {
        if (!(player instanceof ServerPlayer serverPlayer)) return 0;

        MEStorage storage = getStorage(serverPlayer, terminal);
        if (storage == null) return 0;

        AEItemKey key = AEItemKey.of(target);
        if (key == null) return 0;

        long amount = 0;
        for (var entry : storage.getAvailableStacks()) {
            if (entry.getKey().equals(key)) {
                amount = entry.getLongValue();
                break;
            }
        }
        return amount > Integer.MAX_VALUE ? Integer.MAX_VALUE : (int) amount;
    }

    @Override
    public int useItems(Player player, ContainerTrace trace, ItemStack target, ItemStack terminal, int count) {
        if (!(player instanceof ServerPlayer serverPlayer)) return count;

        WirelessTerminalMenuHost<?> host = getHost(serverPlayer, terminal);
        if (host == null) return count;

        MEStorage storage = host.getInventory();
        if (storage == null) return count;

        AEItemKey key = AEItemKey.of(target);
        if (key == null) return count;

        var source = IActionSource.ofPlayer(serverPlayer);
        long extracted = StorageHelper.poweredExtraction(host, storage, key, count, source, Actionable.MODULATE);
        long remaining = count - extracted;
        return remaining > Integer.MAX_VALUE ? Integer.MAX_VALUE : (int) remaining;
    }

    private MEStorage getStorage(ServerPlayer player, ItemStack terminal) {
        var host = getHost(player, terminal);
        return host != null ? host.getInventory() : null;
    }

    private WirelessTerminalMenuHost<?> getHost(ServerPlayer player, ItemStack terminal) {
        try {
            ItemMenuHostLocator locator = resolveLocator(player, terminal);
            if (locator == null) return null;

            WirelessTerminalMenuHost<?> host = buildHost(player, terminal, locator);
            if (host == null) return null;

            if (!host.getLinkStatus().connected()) return null;

            return host;
        } catch (Exception e) {
            return null;
        }
    }

    private WirelessTerminalMenuHost<?> buildHost(ServerPlayer player, ItemStack terminal, ItemMenuHostLocator locator) {
        try {
            if (terminal.getItem() instanceof WirelessCraftingTerminalItem crafting) {
                return new WirelessTerminalMenuHost<>(crafting, player, locator, (p, menu) -> {});
            } else if (terminal.getItem() instanceof WirelessTerminalItem wireless) {
                return new WirelessTerminalMenuHost<>(wireless, player, locator, (p, menu) -> {});
            }
        } catch (Exception ignored) {}
        return null;
    }

    static ItemMenuHostLocator resolveLocator(ServerPlayer player, ItemStack item) {
        for (int i = 0; i < player.getInventory().getContainerSize(); i++) {
            ItemStack slotStack = player.getInventory().getItem(i);
            if (slotStack.getItem() == item.getItem()
                    && ItemStack.isSameItemSameComponents(slotStack, item)) {
                return MenuLocators.forInventorySlot(i);
            }
        }

        try {
            var curiosInventory = CuriosApi.getCuriosInventory(player).orElse(null);
            if (curiosInventory == null) return null;

            int globalSlot = 0;
            for (var entry : curiosInventory.getCurios().entrySet()) {
                var stacks = entry.getValue().getStacks();
                for (int i = 0; i < stacks.getSlots(); i++) {
                    ItemStack slotStack = stacks.getStackInSlot(i);
                    if (slotStack.getItem() == item.getItem()
                            && ItemStack.isSameItemSameComponents(slotStack, item)) {
                        return MenuLocators.forCurioSlot(globalSlot);
                    }
                    globalSlot++;
                }
            }
        } catch (Exception ignored) {}

        return null;
    }
}