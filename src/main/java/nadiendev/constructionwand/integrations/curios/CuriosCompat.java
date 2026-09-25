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

    /**
     * Indica si Curios está disponible en esta instancia del juego.
     * Llama a ModList solo una vez (campo estático).
     */
    public static boolean isLoaded() {
        return CURIOS_LOADED;
    }

    /**
     * Devuelve copias de los stacks del inventario de Curios del jugador, solo para leer.
     * Si Curios no está instalado, devuelve una lista vacía inmutable
     * sin tocar ninguna clase de Curios.
     *
     * @param player el jugador cuyo inventario de Curios se quiere leer
     * @return lista (posiblemente vacía) de ItemStacks en slots de Curios
     */
    public static List<ItemStack> getStacks(Player player) {
        if (!CURIOS_LOADED) {
            return Collections.emptyList();
        }
        // CuriosHelper se carga aquí por primera vez en runtime,
        // solo si Curios está presente.
        return CuriosHelper.getCuriosStacks(player);
    }

    /**
     * Pasa los stacks de Curios del jugador a {@code action} y guarda en su slot los que
     * haya modificado. Si Curios no está instalado, devuelve {@code fallback} sin llamar
     * a {@code action}.
     *
     * @param player   el jugador cuyo inventario de Curios se quiere modificar
     * @param action   operación sobre los stacks; puede modificarlos en sitio
     * @param fallback resultado si Curios no está instalado
     */
    public static <T> T useStacks(Player player, Function<List<ItemStack>, T> action, T fallback) {
        if (!CURIOS_LOADED) {
            return fallback;
        }
        return CuriosHelper.useCuriosStacks(player, action);
    }
}
