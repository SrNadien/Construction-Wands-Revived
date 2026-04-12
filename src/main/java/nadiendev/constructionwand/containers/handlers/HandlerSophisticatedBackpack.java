package nadiendev.constructionwand.containers.handlers;

import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.neoforged.neoforge.capabilities.Capabilities;
import net.neoforged.neoforge.items.IItemHandler;
import nadiendev.constructionwand.api.IContainerHandler;
import nadiendev.constructionwand.basics.WandUtil;
import nadiendev.constructionwand.containers.ContainerTrace;

public class HandlerSophisticatedBackpack implements IContainerHandler {

    @Override
    public boolean matches(Player player, ItemStack itemStack, ItemStack inventoryStack) {
        if (inventoryStack == null || inventoryStack.isEmpty() || inventoryStack.getCount() != 1) {
            return false;
        }
        String namespace = BuiltInRegistries.ITEM
                .getKey(inventoryStack.getItem())
                .getNamespace();
        if (!"sophisticatedbackpacks".equals(namespace)) {
            return false;
        }
        IItemHandler handler = inventoryStack.getCapability(Capabilities.ItemHandler.ITEM);
        return handler != null;
    }

    @Override
    public int countItems(Player player, ContainerTrace trace, ItemStack itemStack, ItemStack inventoryStack) {
        IItemHandler handler = inventoryStack.getCapability(Capabilities.ItemHandler.ITEM);
        if (handler == null) return 0;

        int count = 0;
        for (int i = 0; i < handler.getSlots(); i++) {
            ItemStack slotStack = handler.getStackInSlot(i);
            if (WandUtil.stackEquals(slotStack, itemStack)) {
                count += slotStack.getCount();
            }
        }
        return count;
    }

    @Override
    public int useItems(Player player, ContainerTrace trace, ItemStack itemStack, ItemStack inventoryStack, int count) {
        IItemHandler handler = inventoryStack.getCapability(Capabilities.ItemHandler.ITEM);
        if (handler == null) return count;

        boolean changed = false;

        for (int i = 0; i < handler.getSlots() && count > 0; i++) {
            ItemStack slotStack = handler.getStackInSlot(i);
            if (!WandUtil.stackEquals(slotStack, itemStack)) continue;

            int toTake = Math.min(count, slotStack.getCount());
            ItemStack extracted = handler.extractItem(i, toTake, false);
            if (!extracted.isEmpty()) {
                count -= extracted.getCount();
                changed = true;
            }
        }

        if (changed) {
            player.getInventory().setChanged();
        }

        return count;
    }
}