package nadiendev.constructionwand.containers.handlers;

<<<<<<< Updated upstream
=======
import net.minecraft.core.registries.BuiltInRegistries;
>>>>>>> Stashed changes
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.neoforged.neoforge.capabilities.Capabilities;
import net.neoforged.neoforge.items.IItemHandler;
import nadiendev.constructionwand.api.IContainerHandler;
import nadiendev.constructionwand.basics.WandUtil;

/**
 * Sophisticated Backpack handler.
 * By NadienDev
 */
public class HandlerSophisticatedBackpack implements IContainerHandler {

<<<<<<< Updated upstream
    // Comprueba si el item del inventario es una mochila de Sophisticated.

=======
>>>>>>> Stashed changes
    @Override
    public boolean matches(Player player, ItemStack itemStack, ItemStack inventoryStack) {
        if (inventoryStack == null || inventoryStack.isEmpty() || inventoryStack.getCount() != 1) {
            return false;
        }
<<<<<<< Updated upstream
        // Comprueba que el item expone IItemHandler como capability de item
        // Esto detecta mochilas de Sophisticated sin depender directamente de sus clases
        IItemHandler handler = inventoryStack.getCapability(Capabilities.ItemHandler.ITEM);
        return handler != null;
    }

  
      //Cuenta cuántos items coincidentes hay dentro de la mochila.

    @Override
    public int countItems(Player player, ItemStack itemStack, ItemStack inventoryStack) {
=======
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
>>>>>>> Stashed changes
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

    /**
     * Extrae items de la mochila.
     * Usa extractItem() de IItemHandler para que Sophisticated pueda
     *
     * @return items restantes por consumir (0 = todos consumidos)
     */
    @Override
<<<<<<< Updated upstream
    public int useItems(Player player, ItemStack itemStack, ItemStack inventoryStack, int count) {
=======
    public int useItems(Player player, ContainerTrace trace, ItemStack itemStack, ItemStack inventoryStack, int count) {
>>>>>>> Stashed changes
        IItemHandler handler = inventoryStack.getCapability(Capabilities.ItemHandler.ITEM);
        if (handler == null) return count;

        boolean changed = false;

        for (int i = 0; i < handler.getSlots() && count > 0; i++) {
            ItemStack slotStack = handler.getStackInSlot(i);
            if (!WandUtil.stackEquals(slotStack, itemStack)) continue;

            int toTake = Math.min(count, slotStack.getCount());
<<<<<<< Updated upstream

            // simulate=false para extraer de verdad
=======
>>>>>>> Stashed changes
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