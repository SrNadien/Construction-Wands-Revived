package nadiendev.constructionwand.containers;

import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import nadiendev.constructionwand.api.IContainerHandler;
import nadiendev.constructionwand.containers.handlers.HandlerBundle;
import nadiendev.constructionwand.containers.handlers.HandlerCapability;
import nadiendev.constructionwand.containers.handlers.HandlerShulkerbox;

import java.util.ArrayList;

public class ContainerManager
{
    private final ArrayList<IContainerHandler> handlers;

    public ContainerManager() {
        handlers = new ArrayList<IContainerHandler>();
    }

    /** Handlers that only depend on vanilla or on the loader's item handler system. */
    public void registerDefaults() {
        register(new HandlerCapability());
        register(new HandlerShulkerbox());
        register(new HandlerBundle());
    }

    public boolean register(IContainerHandler handler) {
        return handlers.add(handler);
    }

    public int countItems(Player player, ContainerTrace trace, ItemStack itemStack, ItemStack inventoryStack) {
        for(IContainerHandler handler : handlers) {
            if(handler.matches(player, itemStack, inventoryStack)) {
                int sig = handler.getSignature(player, inventoryStack);
                if (trace.push(sig)) {
                    return handler.countItems(player, trace, itemStack, inventoryStack);
                } else {
                    return 0;
                }
            }
        }
        return 0;
    }

    public int useItems(Player player, ContainerTrace trace, ItemStack itemStack, ItemStack inventoryStack, int count) {
        for(IContainerHandler handler : handlers) {
            if(handler.matches(player, itemStack, inventoryStack)) {
                int sig = handler.getSignature(player, inventoryStack);
                if (trace.push(sig)) {
                    return handler.useItems(player, trace, itemStack, inventoryStack, count);
                } else {
                    return count;
                }
            }
        }
        return count;
    }
}
