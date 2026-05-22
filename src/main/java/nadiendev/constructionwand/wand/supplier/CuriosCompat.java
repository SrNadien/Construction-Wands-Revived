package nadiendev.constructionwand.wand.supplier;

import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.neoforged.fml.ModList;

import java.util.Collections;
import java.util.List;


public final class CuriosCompat {

   
    private static final boolean CURIOS_LOADED =
            ModList.get().isLoaded("curios");

    private CuriosCompat() {}

    
    public static boolean isLoaded() {
        return CURIOS_LOADED;
    }

    /**
    
     * @param player el jugador cuyo inventario de Curios se quiere leer
     * @return lista (posiblemente vacía) de ItemStacks en slots de Curios
     */
    public static List<ItemStack> getStacks(Player player) {
        if (!CURIOS_LOADED) {
            return Collections.emptyList();
        }
        return CuriosHelper.getCuriosStacks(player);
    }
}