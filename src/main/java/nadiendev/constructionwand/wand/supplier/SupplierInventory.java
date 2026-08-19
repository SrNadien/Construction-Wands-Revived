package nadiendev.constructionwand.wand.supplier;

import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;
import nadiendev.constructionwand.ConstructionWand;
import nadiendev.constructionwand.api.IWandSupplier;
import nadiendev.constructionwand.basics.ReplacementRegistry;
import nadiendev.constructionwand.basics.WandUtil;
import nadiendev.constructionwand.basics.option.WandOptions;
import nadiendev.constructionwand.basics.pool.IPool;
import nadiendev.constructionwand.basics.pool.OrderedPool;
import nadiendev.constructionwand.containers.ContainerTrace;
import nadiendev.constructionwand.containers.ContainerManager;
import nadiendev.constructionwand.wand.undo.PlaceSnapshot;

import javax.annotation.Nullable;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;


public class SupplierInventory implements IWandSupplier {

    protected final Player player;
    protected final WandOptions options;

    protected HashMap<BlockItem, Integer> itemCounts;
    protected IPool<BlockItem> itemPool;

    public SupplierInventory(Player player, WandOptions options) {
        this.player = player;
        this.options = options;
    }

    public void getSupply(@Nullable BlockItem target) {
        itemCounts = new LinkedHashMap<>();
        ItemStack offhandStack = player.getItemInHand(InteractionHand.OFF_HAND);

        itemPool = new OrderedPool<>();

        // Block in offhand -> override
        if (!offhandStack.isEmpty() && offhandStack.getItem() instanceof BlockItem blockItem) {
            addBlockItem(blockItem);
        }
        // Otherwise use target block
        else if (target != null && target != Items.AIR) {
            addBlockItem(target);

            // Add replacement items
            if (options.match.get() != WandOptions.MATCH.EXACT) {
                for (Item it : ReplacementRegistry.getMatchingSet(target)) {
                    if (it instanceof BlockItem blockItem) addBlockItem(blockItem);
                }
            }
        }
    }

    protected void addBlockItem(BlockItem item) {
        int count = WandUtil.countItem(player, item);
        if (count > 0) {
            itemCounts.put(item, count);
            itemPool.add(item);
        }
    }

    // -------------------------------------------------------------------------
    // Helpers de inventario de Curios -- DESACTIVADO en 26.2
    //
    // Curios esta roto en esta version de NeoForge, asi que la dependencia no se
    // compila. CuriosCompat/CuriosHelper estan aparcados en src/disabled-integrations/
    // (ver el README de esa carpeta para reactivarlos).
    // -------------------------------------------------------------------------

    // /**
    //  * Devuelve los stacks del inventario de Curios del jugador.
    //  * Si Curios no está instalado, retorna una lista vacía sin coste alguno.
    //  */
    // private List<ItemStack> getCuriosInv() {
    //     return CuriosCompat.getStacks(player);
    // }

    // -------------------------------------------------------------------------
    // IWandSupplier
    // -------------------------------------------------------------------------

    @Override
    @Nullable
    public PlaceSnapshot getPlaceSnapshot(Level world, BlockPos pos, BlockHitResult rayTraceResult,
                                          @Nullable BlockState supportingBlock) {
        if (!WandUtil.isPositionPlaceable(world, player, pos, options.replace.get())) return null;
        itemPool.reset();

        while (true) {
            // Draw item from pool (returns null if none are left)
            BlockItem item = itemPool.draw();
            if (item == null) return null;

            int count = itemCounts.get(item);
            if (count == 0) continue;

            PlaceSnapshot placeSnapshot = PlaceSnapshot.get(world, player, rayTraceResult, pos, item, supportingBlock, options);
            if (placeSnapshot != null) {
                int ncount = count - 1;
                itemCounts.put(item, ncount);

                // Remove item from pool if there are no items left
                if (ncount == 0) itemPool.remove(item);

                return placeSnapshot;
            }
        }
    }

    @Override
    public int takeItemStack(ItemStack stack) {
        int count = stack.getCount();
        Item item = stack.getItem();

        if (player.getInventory().getNonEquipmentItems().isEmpty()) return count;
        if (player.isCreative()) return 0;

        List<ItemStack> hotbar  = WandUtil.getHotbarWithOffhand(player);
        List<ItemStack> mainInv = WandUtil.getMainInv(player);
        List<ItemStack> armor   = WandUtil.getArmor(player);
        // List<ItemStack> curios  = getCuriosInv();  // Curios desactivado en 26.2

        // Take items from main inv, loose items first
        count = takeItemsInvList(count, item, mainInv, false);
        count = takeItemsInvList(count, item, mainInv, true);

        // Take items from hotbar, containers first
        count = takeItemsInvList(count, item, hotbar, true);
        count = takeItemsInvList(count, item, hotbar, false);

        count = takeItemsInvList(count, item, armor, true);
        count = takeItemsInvList(count, item, armor, false);

        // Take items from Curios slots (containers only; los curio slots) -- desactivado en 26.2
        // count = takeItemsInvList(count, item, curios, true);

        return count;
    }

    // -------------------------------------------------------------------------
    // Helpers privados
    // -------------------------------------------------------------------------

    private int takeItemsInvList(int count, Item item, List<ItemStack> inv, boolean container) {
        if (count == 0) return 0;
        if (!(player instanceof ServerPlayer serverPlayer)) return count;

        ContainerManager containerManager = ConstructionWand.containerManager;
        // ContainerTrace is just a placeholder for tracking purposes
        ContainerTrace trace = new ContainerTrace(serverPlayer);

        for (ItemStack stack : inv) {
            if (count == 0) break;

            if (container) {
                count = containerManager.useItems(serverPlayer, trace, new ItemStack(item), stack, count);
            }

            if (!container && WandUtil.stackEquals(stack, item)) {
                int toTake = Math.min(count, stack.getCount());
                stack.shrink(toTake);
                count -= toTake;
                serverPlayer.getInventory().setChanged();
            }
        }
        return count;
    }
}