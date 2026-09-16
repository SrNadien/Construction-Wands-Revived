package nadiendev.constructionwand.basics;

import net.minecraft.world.item.ItemStack;

import java.util.Objects;

public final class StackEntry
{
    private final ItemStack stack;

    public StackEntry(ItemStack stack) {
        this.stack = stack.copyWithCount(1);
    }

    public ItemStack copyStack() {
        return stack.copy();
    }

    @Override
    public boolean equals(Object other) {
        if(this == other) return true;
        return other instanceof StackEntry entry && ItemStack.isSameItemSameComponents(stack, entry.stack);
    }

    @Override
    public int hashCode() {
        return Objects.hash(stack.getItem(), stack.getComponents());
    }

    @Override
    public String toString() {
        return stack.toString();
    }
}
