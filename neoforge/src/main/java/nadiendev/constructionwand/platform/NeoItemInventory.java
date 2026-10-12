//? if neoforge {
package nadiendev.constructionwand.platform;

import net.minecraft.world.item.ItemStack;
//? if >=1.21.9 {
import net.neoforged.neoforge.transfer.ResourceHandler;
import net.neoforged.neoforge.transfer.item.ItemResource;
import net.neoforged.neoforge.transfer.transaction.Transaction;
//?} else {
/*import net.neoforged.neoforge.items.IItemHandler;
*///?}

//? if >=1.21.9 {
/** Exposes a NeoForge item {@link ResourceHandler} as a common inventory. */
public record NeoItemInventory(ResourceHandler<ItemResource> handler) implements IItemInventory {

    @Override
    public int getSlots() {
        return handler.size();
    }

    @Override
    public ItemStack getStack(int slot) {
        return handler.getResource(slot).toStack(handler.getAmountAsInt(slot));
    }

    @Override
    public ItemStack extract(int slot, int amount, boolean simulate) {
        ItemResource resource = handler.getResource(slot);
        if (resource.isEmpty() || amount <= 0) return ItemStack.EMPTY;
        try (Transaction transaction = Transaction.openRoot()) {
            int extracted = handler.extract(slot, resource, amount, transaction);
            if (extracted <= 0) return ItemStack.EMPTY;
            if (!simulate) transaction.commit();
            return resource.toStack(extracted);
        }
    }

    @Override
    public ItemStack insert(int slot, ItemStack stack, boolean simulate) {
        if (stack.isEmpty()) return stack;
        try (Transaction transaction = Transaction.openRoot()) {
            int inserted = handler.insert(slot, ItemResource.of(stack), stack.getCount(), transaction);
            if (inserted <= 0) return stack;
            if (!simulate) transaction.commit();
            return stack.copyWithCount(stack.getCount() - inserted);
        }
    }
}
//?} else {
/*/^* Exposes a NeoForge {@link IItemHandler} as a common inventory. ^/
public record NeoItemInventory(IItemHandler handler) implements IItemInventory {

    @Override
    public int getSlots() {
        return handler.getSlots();
    }

    @Override
    public ItemStack getStack(int slot) {
        return handler.getStackInSlot(slot);
    }

    @Override
    public ItemStack extract(int slot, int amount, boolean simulate) {
        return handler.extractItem(slot, amount, simulate);
    }

    @Override
    public ItemStack insert(int slot, ItemStack stack, boolean simulate) {
        return handler.insertItem(slot, stack, simulate);
    }
}
*///?}
//?}
