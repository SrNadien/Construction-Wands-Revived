package nadiendev.constructionwand.containers.handlers;

import appeng.api.config.Actionable;
import appeng.api.networking.security.IActionSource;
import appeng.api.stacks.AEItemKey;
import appeng.api.storage.MEStorage;
import appeng.api.storage.StorageHelper;
import appeng.core.localization.PlayerMessages;
import appeng.helpers.WirelessTerminalMenuHost;
import appeng.items.tools.powered.WirelessCraftingTerminalItem;
import appeng.items.tools.powered.WirelessTerminalItem;
import appeng.menu.locator.MenuLocators;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import nadiendev.constructionwand.api.IContainerHandler;
import nadiendev.constructionwand.containers.ContainerTrace;

public class HandlerWirelessTerminal implements IContainerHandler {

    @Override
    public boolean matches(Player player, ItemStack itemStack, ItemStack inventoryStack) {
        return inventoryStack.getItem() instanceof WirelessTerminalItem
                || inventoryStack.getItem() instanceof WirelessCraftingTerminalItem;
    }

    @Override
    public int getSignature(Player player, ItemStack inventoryStack) {
        if (player instanceof ServerPlayer serverPlayer) {
            MEStorage storage = getStorage(serverPlayer, inventoryStack);
            if (storage != null) {
                return storage.hashCode();
            }
        }
        return -1;
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
            WirelessTerminalMenuHost<?> host;

            if (terminal.getItem() instanceof WirelessCraftingTerminalItem crafting) {
                host = new WirelessTerminalMenuHost<>(crafting, player, MenuLocators.forStack(terminal), (p, menu) -> {});
            } else if (terminal.getItem() instanceof WirelessTerminalItem wireless) {
                host = new WirelessTerminalMenuHost<>(wireless, player, MenuLocators.forStack(terminal), (p, menu) -> {});
            } else {
                return null;
            }

            if (host.getActionableNode() == null) {
                player.displayClientMessage(PlayerMessages.OutOfRange.text(), true);
                return null;
            }

            double power = terminal.getItem() instanceof WirelessTerminalItem w ? w.getAECurrentPower(terminal) : 0;
            if (power <= 0) {
                player.displayClientMessage(PlayerMessages.DeviceNotPowered.text(), true);
                return null;
            }

            return host;
        } catch (Exception e) {
            return null;
        }
    }
}