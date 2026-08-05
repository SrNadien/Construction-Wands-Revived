package nadiendev.constructionwand.containers.handlers;

import appeng.api.config.Actionable;
import appeng.api.networking.security.IActionSource;
import appeng.api.stacks.AEItemKey;
import appeng.api.storage.MEStorage;
import appeng.api.storage.StorageHelper;
import appeng.helpers.WirelessTerminalMenuHost;
import appeng.items.tools.powered.WirelessTerminalItem;
import appeng.menu.locator.ItemMenuHostLocator;
import de.mari_023.ae2wtlib.api.registration.WTDefinition;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import nadiendev.constructionwand.api.IContainerHandler;
import nadiendev.constructionwand.containers.ContainerTrace;

public class HandlerWirelessTerminalAE2WTLib implements IContainerHandler {

    @Override
    public boolean matches(Player player, ItemStack itemStack, ItemStack inventoryStack) {
        return isWTLibTerminal(inventoryStack.getItem());
    }

    private static boolean isWTLibTerminal(Item item) {
        for (WTDefinition def : WTDefinition.wirelessTerminals()) {
            if (def.item() == item) return true;
        }
        return false;
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
        WirelessTerminalMenuHost<?> host = getHost(player, terminal);
        return host != null ? host.getInventory() : null;
    }

    private WirelessTerminalMenuHost<?> getHost(ServerPlayer player, ItemStack terminal) {
        try {
            WTDefinition definition = definitionOf(terminal);
            if (definition == null) return null;

            ItemMenuHostLocator locator = HandlerWirelessTerminal.resolveLocator(player, terminal);
            if (locator == null) return null;

            WirelessTerminalMenuHost<?> host = buildHost(definition, player, locator);
            if (host == null) return null;

            if (!host.getLinkStatus().connected()) return null;

            return host;
        } catch (Exception e) {
            return null;
        }
    }

    private WirelessTerminalMenuHost<?> buildHost(WTDefinition definition, ServerPlayer player, ItemMenuHostLocator locator) {
        try {
            var item = definition.item();
            if (item instanceof WirelessTerminalItem wireless) {
                return new WirelessTerminalMenuHost<>(wireless, player, locator, (p, menu) -> {});
            }
        } catch (Exception ignored) {}
        return null;
    }

    private static WTDefinition definitionOf(ItemStack terminal) {
        for (WTDefinition def : WTDefinition.wirelessTerminals()) {
            if (def.item() == terminal.getItem()) return def;
        }
        return null;
    }
}
