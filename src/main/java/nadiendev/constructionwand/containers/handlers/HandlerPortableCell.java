package nadiendev.constructionwand.containers.handlers;

import appeng.api.config.Actionable;
import appeng.api.networking.security.IActionSource;
import appeng.api.stacks.AEItemKey;
import appeng.api.storage.MEStorage;
import appeng.api.storage.StorageHelper;
import appeng.items.contents.PortableCellMenuHost;
import appeng.items.tools.powered.PortableCellItem;
import appeng.menu.locator.ItemMenuHostLocator;
import appeng.menu.locator.MenuLocators;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import nadiendev.constructionwand.api.IContainerHandler;
import nadiendev.constructionwand.containers.ContainerTrace;
import nadiendev.constructionwand.integrations.curios.CuriosIntegration;

public class HandlerPortableCell implements IContainerHandler {

    @Override
    public boolean matches(Player player, ItemStack itemStack, ItemStack inventoryStack) {
        return inventoryStack.getItem() instanceof PortableCellItem;
    }

    @Override
    public int getSignature(Player player, ItemStack inventoryStack) {
        return inventoryStack.hashCode();
    }

    @Override
    public int countItems(Player player, ContainerTrace trace, ItemStack target, ItemStack cell) {
        if (!(player instanceof ServerPlayer serverPlayer)) return 0;

        MEStorage storage = getStorage(serverPlayer, cell);
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
    public int useItems(Player player, ContainerTrace trace, ItemStack target, ItemStack cell, int count) {
        if (!(player instanceof ServerPlayer serverPlayer)) return count;

        PortableCellMenuHost<?> host = getHost(serverPlayer, cell);
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

    private MEStorage getStorage(ServerPlayer player, ItemStack cell) {
        var host = getHost(player, cell);
        return host != null ? host.getInventory() : null;
    }

    private PortableCellMenuHost<?> getHost(ServerPlayer player, ItemStack cell) {
        if (!(cell.getItem() instanceof PortableCellItem c)) return null;
        try {
            ItemMenuHostLocator locator = resolveLocator(player, cell);
            if (locator == null) return null;

            PortableCellMenuHost<?> host = new PortableCellMenuHost<>(c, player, locator, (p, menu) -> {});

            if (!host.getLinkStatus().connected()) return null;

            return host;
        } catch (Exception e) {
            return null;
        }
    }

    private static ItemMenuHostLocator resolveLocator(ServerPlayer player, ItemStack cell) {
        for (int i = 0; i < player.getInventory().getContainerSize(); i++) {
            ItemStack slotStack = player.getInventory().getItem(i);
            if (slotStack.getItem() == cell.getItem()
                    && ItemStack.isSameItemSameComponents(slotStack, cell)) {
                return MenuLocators.forInventorySlot(i);
            }
        }

        // Curios
        return CuriosIntegration.resolveCurioSlot(player, cell);
    }
}