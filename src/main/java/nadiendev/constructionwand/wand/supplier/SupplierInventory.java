package nadiendev.constructionwand.wand.supplier;

import net.minecraft.core.BlockPos;
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
import nadiendev.constructionwand.containers.ContainerManager;
import nadiendev.constructionwand.containers.ContainerTrace;
import nadiendev.constructionwand.wand.undo.PlaceSnapshot;

import org.jetbrains.annotations.Nullable;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;

<<<<<<< Updated upstream
/**
 * Default WandSupplier. Takes items from player inventory in order.
 */
=======
>>>>>>> Stashed changes
public class SupplierInventory implements IWandSupplier {
    protected final Player player;
    protected final WandOptions options;

    protected HashMap<BlockItem, Integer> itemCounts;
    protected IPool<BlockItem> itemPool;

    public SupplierInventory(Player player, WandOptions options) {
        this.player = player;
        this.options = options;
    }

    @Override
    public void getSupply(@Nullable BlockItem target) {
        itemCounts = new LinkedHashMap<>();
        ItemStack offhandStack = player.getItemInHand(InteractionHand.OFF_HAND);

        itemPool = new OrderedPool<>();

<<<<<<< Updated upstream
        // Bloque en la mano izquierda -> tiene prioridad
        if (!offhandStack.isEmpty() && offhandStack.getItem() instanceof BlockItem blockItem) {
            addBlockItem(blockItem);
        }
        // Si no, usar el bloque objetivo
        else if (target != null && target != Items.AIR) {
            addBlockItem(target);

            // Agregar items de reemplazo
=======
        if (!offhandStack.isEmpty() && offhandStack.getItem() instanceof BlockItem blockItem) {
            addBlockItem(blockItem);
        } else if (target != null && target != Items.AIR) {
            addBlockItem(target);

>>>>>>> Stashed changes
            if (options.match.get() != WandOptions.MATCH.EXACT) {
                for (Item it : ReplacementRegistry.getMatchingSet(target)) {
                    if (it instanceof BlockItem blockItem)
                        addBlockItem(blockItem);
                }
            }
        }
    }

    protected void addBlockItem(BlockItem item) {
        // Contar items sueltos en el inventario
        int count = WandUtil.countItem(player, item);
<<<<<<< Updated upstream

        // BUGFIX: También contar items dentro de contenedores (mochilas, shulkers, etc.)
        // Sin esto, si los bloques SOLO están en la mochila, count=0 y el wand
        // nunca los usa, resultando en bloques "infinitos" que no se consumen.
        count += countItemInContainers(item);

=======
        count += countItemInContainers(item);

        // En cliente (preview render), countItemInContainers siempre devuelve 0
        // porque player no es ServerPlayer. Si hay algún handler que reconozca
        // un ítem del inventario como container, asumimos disponibilidad para
        // mostrar el preview correctamente. El servidor valida al ejecutar.
        if (count == 0 && hasContainerWithItem(item)) {
            count = Integer.MAX_VALUE;
        }

>>>>>>> Stashed changes
        if (count > 0) {
            itemCounts.put(item, count);
            itemPool.add(item);
        }
    }

    /**
<<<<<<< Updated upstream
     * Cuenta cuántos items del tipo dado hay dentro de todos los contenedores
     * del inventario del jugador (hotbar + inventario principal).
     */
    private int countItemInContainers(BlockItem item) {
        ContainerManager containerManager = ConstructionWand.instance.containerManager;
        ItemStack itemStack = new ItemStack(item);
        int total = 0;

        for (ItemStack inv : WandUtil.getHotbarWithOffhand(player)) {
            total += containerManager.countItems(player, itemStack, inv);
        }
        for (ItemStack inv : WandUtil.getMainInv(player)) {
            total += containerManager.countItems(player, itemStack, inv);
=======
     * Verifica si algún ítem del inventario es reconocido por un handler del
     * ContainerManager. Funciona tanto en cliente como en servidor.
     */
    private boolean hasContainerWithItem(BlockItem item) {
        ContainerManager containerManager = ConstructionWand.instance.containerManager;
        ItemStack itemStack = new ItemStack(item);

        for (ItemStack inv : WandUtil.getHotbarWithOffhand(player)) {
            if (containerManager.hasHandler(player, itemStack, inv)) {
                return true;
            }
        }
        for (ItemStack inv : WandUtil.getMainInv(player)) {
            if (containerManager.hasHandler(player, itemStack, inv)) {
                return true;
            }
        }
        return false;
    }

    private int countItemInContainers(BlockItem item) {
        if (!(player instanceof ServerPlayer sp)) return 0;

        ContainerManager containerManager = ConstructionWand.instance.containerManager;
        ContainerTrace trace = new ContainerTrace(sp);
        ItemStack itemStack = new ItemStack(item);
        int total = 0;

        for (ItemStack inv : WandUtil.getHotbarWithOffhand(player)) {
            total += containerManager.countItems(player, trace, itemStack, inv);
        }
        for (ItemStack inv : WandUtil.getMainInv(player)) {
            total += containerManager.countItems(player, trace, itemStack, inv);
>>>>>>> Stashed changes
        }
        return total;
    }

    @Override
    @Nullable
    public PlaceSnapshot getPlaceSnapshot(Level world, BlockPos pos, BlockHitResult rayTraceResult,
            @Nullable BlockState supportingBlock) {
        if (!WandUtil.isPositionPlaceable(world, player, pos, options.replace.get()))
            return null;
        itemPool.reset();

        while (true) {
<<<<<<< Updated upstream
            // Sacar item del pool (retorna null si no queda ninguno)
=======
>>>>>>> Stashed changes
            BlockItem item = itemPool.draw();
            if (item == null)
                return null;

            int count = itemCounts.get(item);
            if (count == 0)
                continue;

<<<<<<< Updated upstream
            PlaceSnapshot placeSnapshot = PlaceSnapshot.get(world, player, rayTraceResult, pos, item, supportingBlock,
                    options);
            if (placeSnapshot != null) {
                int newCount = count - 1;
                itemCounts.put(item, newCount);

                // Remover del pool si ya no queda ninguno de ese item
=======
            PlaceSnapshot placeSnapshot = PlaceSnapshot.get(world, player, rayTraceResult, pos, item, supportingBlock, options);
            if (placeSnapshot != null) {
                // Evitar underflow si count era Integer.MAX_VALUE (cliente/preview)
                int newCount = (count == Integer.MAX_VALUE) ? Integer.MAX_VALUE : count - 1;
                itemCounts.put(item, newCount);

>>>>>>> Stashed changes
                if (newCount == 0)
                    itemPool.remove(item);

                return placeSnapshot;
            }
        }
    }

    @Override
    public int takeItemStack(ItemStack stack) {
        int count = stack.getCount();
        Item item = stack.getItem();

        if (player.getInventory().items == null)
            return count;
        if (player.isCreative())
            return 0;

        List<ItemStack> hotbar = WandUtil.getHotbarWithOffhand(player);
        List<ItemStack> mainInv = WandUtil.getMainInv(player);

<<<<<<< Updated upstream
        // Consumir de inventario principal, items sueltos primero
        count = takeItemsInvList(count, item, mainInv, false);
        count = takeItemsInvList(count, item, mainInv, true);

        // Consumir de hotbar, contenedores primero
=======
        count = takeItemsInvList(count, item, mainInv, false);
        count = takeItemsInvList(count, item, mainInv, true);
>>>>>>> Stashed changes
        count = takeItemsInvList(count, item, hotbar, true);
        count = takeItemsInvList(count, item, hotbar, false);

        return count;
    }

    private int takeItemsInvList(int count, Item item, List<ItemStack> inv, boolean container) {
<<<<<<< Updated upstream
        ContainerManager containerManager = ConstructionWand.instance.containerManager;

        for (ItemStack stack : inv) {
            if (count == 0)
                break;

            // Intentar consumir desde contenedores (ej: Sophisticated Backpacks, Shulkers)
            if (container) {
                int prevCount = count;
                count = containerManager.useItems(player, new ItemStack(item), stack, count);
                if (count < prevCount)
                    player.getInventory().setChanged();
            }

            // Intentar consumir desde items sueltos directamente
=======
        if (!(player instanceof ServerPlayer sp)) return count;

        ContainerManager containerManager = ConstructionWand.instance.containerManager;
        ContainerTrace trace = new ContainerTrace(sp);

        for (ItemStack stack : inv) {
            if (count == 0) break;

            if (container) {
                int prevCount = count;
                count = containerManager.useItems(player, trace, new ItemStack(item), stack, count);
                if (count < prevCount)
                    player.getInventory().setChanged();
            }

>>>>>>> Stashed changes
            if (!container && WandUtil.stackEquals(stack, item)) {
                int toTake = Math.min(count, stack.getCount());
                stack.shrink(toTake);
                count -= toTake;
                player.getInventory().setChanged();
            }
        }
        return count;
    }
}