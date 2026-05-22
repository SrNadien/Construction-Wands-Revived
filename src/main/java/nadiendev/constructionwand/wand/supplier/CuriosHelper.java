package nadiendev.constructionwand.wand.supplier;

import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import top.theillusivec4.curios.api.CuriosApi;

import java.util.ArrayList;
import java.util.List;


final class CuriosHelper {

    private CuriosHelper() {}

    
    static List<ItemStack> getCuriosStacks(Player player) {
        List<ItemStack> result = new ArrayList<>();

        CuriosApi.getCuriosInventory(player).ifPresent(inv ->
            inv.getCurios().forEach((slotId, handler) -> {
                var stacks = handler.getStacks();
                for (int i = 0; i < stacks.getSlots(); i++) {
                    ItemStack stack = stacks.getStackInSlot(i);
                    if (!stack.isEmpty()) {
                        result.add(stack);
                    }
                }
            })
        );

        return result;
    }
}