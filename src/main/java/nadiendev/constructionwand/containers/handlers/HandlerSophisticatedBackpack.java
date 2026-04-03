package nadiendev.constructionwand.containers.handlers;

import net.minecraft.core.NonNullList;
import net.minecraft.core.component.DataComponents;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.component.ItemContainerContents;
import nadiendev.constructionwand.api.IContainerHandler;
import nadiendev.constructionwand.basics.WandUtil;
import nadiendev.constructionwand.containers.ContainerTrace;

public class HandlerSophisticatedBackpack implements IContainerHandler {

    private static final int SLOTS = 108;

    @Override
    public boolean matches(Player player, ItemStack itemStack, ItemStack inventoryStack) {
        if (inventoryStack == null || inventoryStack.isEmpty() || inventoryStack.getCount() != 1) {
            return false;
        }
        String namespace = BuiltInRegistries.ITEM
                .getKey(inventoryStack.getItem())
                .getNamespace();
        return "sophisticatedbackpacks".equals(namespace);
    }

    @Override
    public int getSignature(Player player, ItemStack inventoryStack) {
        return inventoryStack.hashCode();
    }

    @Override
    public int countItems(Player player, ContainerTrace trace, ItemStack itemStack, ItemStack inventoryStack) {
        int count = 0;
        for (ItemStack stack : getItemList(inventoryStack)) {
            if (WandUtil.stackEquals(stack, itemStack)) {
                count += stack.getCount();
            }
        }
        return count;
    }

    @Override
    public int useItems(Player player, ContainerTrace trace, ItemStack itemStack, ItemStack inventoryStack, int count) {
        NonNullList<ItemStack> itemList = getItemList(inventoryStack);
        boolean changed = false;

        for (ItemStack stack : itemList) {
            if (!WandUtil.stackEquals(stack, itemStack)) continue;
            int toTake = Math.min(count, stack.getCount());
            stack.shrink(toTake);
            count -= toTake;
            changed = true;
            if (count == 0) break;
        }

        if (changed) {
            setItemList(inventoryStack, itemList);
            player.getInventory().setChanged();
        }

        return count;
    }

    private NonNullList<ItemStack> getItemList(ItemStack itemStack) {
        NonNullList<ItemStack> itemStacks = NonNullList.withSize(SLOTS, ItemStack.EMPTY);
        ItemContainerContents contents = itemStack.getOrDefault(DataComponents.CONTAINER, ItemContainerContents.EMPTY);
        contents.copyInto(itemStacks);
        return itemStacks;
    }

    private void setItemList(ItemStack itemStack, NonNullList<ItemStack> itemStacks) {
        itemStack.set(DataComponents.CONTAINER, ItemContainerContents.fromItems(itemStacks));
    }
}