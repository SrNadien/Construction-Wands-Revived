package nadiendev.constructionwand.integrations.curios;

import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import top.theillusivec4.curios.api.CuriosApi;
import top.theillusivec4.curios.api.type.inventory.IDynamicStackHandler;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;


final class CuriosHelper {

    private CuriosHelper() {}

    private record SlotRef(IDynamicStackHandler handler, int slot, ItemStack stack, ItemStack original) {}

    private static List<SlotRef> collectSlots(Player player) {
        List<SlotRef> result = new ArrayList<>();

        CuriosApi.getCuriosInventory(player).ifPresent(inv ->
            inv.getCurios().forEach((slotId, handler) -> {
                IDynamicStackHandler stacks = handler.getStacks();
                for (int i = 0; i < stacks.getSlots(); i++) {
                    ItemStack stack = stacks.getStackInSlot(i);
                    if (!stack.isEmpty()) {
                        result.add(new SlotRef(stacks, i, stack, stack.copy()));
                    }
                }
            })
        );

        return result;
    }

    static List<ItemStack> getCuriosStacks(Player player) {
        List<ItemStack> result = new ArrayList<>();
        for (SlotRef ref : collectSlots(player)) result.add(ref.stack());
        return result;
    }

    static <T> T useCuriosStacks(Player player, Function<List<ItemStack>, T> action) {
        List<SlotRef> refs = collectSlots(player);
        List<ItemStack> stacks = new ArrayList<>(refs.size());
        for (SlotRef ref : refs) stacks.add(ref.stack());

        T result = action.apply(stacks);

        for (SlotRef ref : refs) {
            if (!ItemStack.matches(ref.stack(), ref.original())) {
                ref.handler().setStackInSlot(ref.slot(), ref.stack());
            }
        }
        return result;
    }
}
