package nadiendev.constructionwand.integrations.curios;

import appeng.menu.locator.ItemMenuHostLocator;
import appeng.menu.locator.MenuLocators;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.neoforged.fml.ModList;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;


public final class CuriosIntegration {

    private static final boolean LOADED = ModList.get().isLoaded("curios");

    private CuriosIntegration() {
    }

    /**
     * @return true si el mod Curios API está instalado y cargado.
     */
    public static boolean isLoaded() {
        return LOADED;
    }

    public static List<ItemStack> getCurioStacks(Player player) {
        if (!LOADED) return Collections.emptyList();
        try {
            return Impl.getStacks(player);
        } catch (LinkageError | RuntimeException e) {
            return Collections.emptyList();
        }
    }

    
    public static ItemMenuHostLocator resolveCurioSlot(ServerPlayer player, ItemStack item) {
        if (!LOADED) return null;
        try {
            return Impl.resolveLocator(player, item);
        } catch (LinkageError | RuntimeException e) {
            return null;
        }
    }

    
    private static final class Impl {

        static List<ItemStack> getStacks(Player player) {
            List<ItemStack> result = new ArrayList<>();
            top.theillusivec4.curios.api.CuriosApi.getCuriosInventory(player).ifPresent(inv -> {
                inv.getCurios().forEach((id, handler) -> {
                    var stacks = handler.getStacks();
                    for (int i = 0; i < stacks.getSlots(); i++) {
                        ItemStack stack = stacks.getStackInSlot(i);
                        if (!stack.isEmpty()) result.add(stack);
                    }
                });
            });
            return result;
        }

        static ItemMenuHostLocator resolveLocator(ServerPlayer player, ItemStack item) {
            var curiosInventory = top.theillusivec4.curios.api.CuriosApi.getCuriosInventory(player).orElse(null);
            if (curiosInventory == null) return null;

            int globalSlot = 0;
            for (var entry : curiosInventory.getCurios().entrySet()) {
                var stacks = entry.getValue().getStacks();
                for (int i = 0; i < stacks.getSlots(); i++) {
                    ItemStack slotStack = stacks.getStackInSlot(i);
                    if (slotStack.getItem() == item.getItem()
                            && ItemStack.isSameItemSameComponents(slotStack, item)) {
                        return MenuLocators.forCurioSlot(globalSlot);
                    }
                    globalSlot++;
                }
            }
            return null;
        }
    }
}