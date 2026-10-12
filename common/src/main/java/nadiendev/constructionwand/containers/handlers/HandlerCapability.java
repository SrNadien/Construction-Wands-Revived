package nadiendev.constructionwand.containers.handlers;

import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import nadiendev.constructionwand.platform.IItemInventory;
import nadiendev.constructionwand.platform.Services;
import nadiendev.constructionwand.api.IContainerHandler;
import nadiendev.constructionwand.basics.WandUtil;
import nadiendev.constructionwand.containers.ContainerTrace;

public class HandlerCapability implements IContainerHandler
{
    @Override
    public boolean matches(Player player, ItemStack itemStack, ItemStack inventoryStack) {
        // Los slots vacios llegan hasta aqui; ItemAccess.forStack() lanza IllegalArgumentException con ellos.
        return inventoryStack != null
                && !inventoryStack.isEmpty()
                && Services.PLATFORM.getItemInventory(inventoryStack) != null;
    }

    @Override
    public int getSignature(Player player, ItemStack inventoryStack) {
        return inventoryStack.hashCode();
    }

    @Override
    public int countItems(Player player, ContainerTrace trace, ItemStack itemStack, ItemStack inventoryStack) {
        IItemInventory itemHandler = Services.PLATFORM.getItemInventory(inventoryStack);
        if(itemHandler == null) return 0;

        int total = 0;
        for(int i = 0; i < itemHandler.getSlots(); i++) {
            ItemStack containerStack = itemHandler.getStack(i);
            if(WandUtil.stackEquals(itemStack, containerStack)) {
                total += Math.max(0, containerStack.getCount());
            }
        }
        return total;
    }

    @Override
    public int useItems(Player player, ContainerTrace trace, ItemStack itemStack, ItemStack inventoryStack, int count) {
        IItemInventory itemHandler = Services.PLATFORM.getItemInventory(inventoryStack);
        if(itemHandler == null) return count;

        for(int i = 0; i < itemHandler.getSlots(); i++) {
            ItemStack handlerStack = itemHandler.getStack(i);
            if(WandUtil.stackEquals(itemStack, handlerStack)) {
                ItemStack extracted = itemHandler.extract(i, count, false);
                count -= extracted.getCount();
                if(count <= 0) break;
            }
        }
        return count;
    }
}