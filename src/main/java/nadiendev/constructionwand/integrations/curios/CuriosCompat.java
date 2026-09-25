package nadiendev.constructionwand.integrations.curios;

import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.neoforged.fml.ModList;

import java.util.Collections;
import java.util.List;
import java.util.function.Function;


public final class CuriosCompat {


    private static final boolean CURIOS_LOADED =
            ModList.get().isLoaded("curios");

    private CuriosCompat() {}

    public static boolean isLoaded() {
        return CURIOS_LOADED;
    }

    public static List<ItemStack> getStacks(Player player) {
        if (!CURIOS_LOADED) {
            return Collections.emptyList();
        }
        return CuriosHelper.getCuriosStacks(player);
    }

    public static <T> T useStacks(Player player, Function<List<ItemStack>, T> action, T fallback) {
        if (!CURIOS_LOADED) {
            return fallback;
        }
        return CuriosHelper.useCuriosStacks(player, action);
    }
}
