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
import nadiendev.constructionwand.containers.ContainerManager;
import nadiendev.constructionwand.containers.ContainerTrace;
import nadiendev.constructionwand.integrations.curios.CuriosIntegration;
import nadiendev.constructionwand.wand.undo.PlaceSnapshot;

import org.jetbrains.annotations.Nullable;
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

    @Override
    public void getSupply(@Nullable BlockItem target) {
        itemCounts = new LinkedHashMap<>();
        ItemStack offhandStack = player.getItemInHand(InteractionHand.OFF_HAND);

        itemPool = new OrderedPool<>();

        if (!offhandStack.isEmpty() && offhandStack.getItem() instanceof BlockItem blockItem) {
            addBlockItem(blockItem);
        } else if (target != null && target != Items.AIR) {
            addBlockItem(target);

            if (options.match.get() != WandOptions.MATCH.EXACT) {
                for (Item it : ReplacementRegistry.getMatchingSet(target)) {
                    if (it instanceof BlockItem blockItem)
                        addBlockItem(blockItem);
                }
            }
        }
    }

    protected void addBlockItem(BlockItem item) {
        int count = WandUtil.countItem(player, item);

        // Si hay algún container handler que reconoce este item, usamos MAX_VALUE
        // para no limitar por el stock real de la red (que se decrementaría incorrectamente
        // en getPlaceSnapshot). El verdadero consumo ocurre en takeItemStack().
        // Esto también corrige el bug en modo creativo con terminales wireless:
        // countItemInContainers devuelve el stock real de la red, pero getPlaceSnapshot
        // lo va decrementando y se queda "sin stock" aunque la red tenga miles.
        if (hasContainerWithItem(item)) {
            count = Integer.MAX_VALUE;
        } else {
            count += countItemInContainers(item);
        }

        if (count > 0) {
            itemCounts.put(item, count);
            itemPool.add(item);
        }
    }

    /**
     * Curios API Optional Dependency.
     * Six Seven -_-
     */
    private List<ItemStack> getCuriosInv(Player player) {
        return CuriosIntegration.getCurioStacks(player);
    }

    private boolean hasContainerWithItem(BlockItem item) {
        ContainerManager containerManager = ConstructionWand.instance.containerManager;
        ItemStack itemStack = new ItemStack(item);

        for (ItemStack inv : WandUtil.getHotbarWithOffhand(player)) {
            if (containerManager.hasHandler(player, itemStack, inv)) return true;
        }
        for (ItemStack inv : WandUtil.getMainInv(player)) {
            if (containerManager.hasHandler(player, itemStack, inv)) return true;
        }
        for (ItemStack inv : getCuriosInv(player)) {
            if (containerManager.hasHandler(player, itemStack, inv)) return true;
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
        }
        for (ItemStack inv : getCuriosInv(player)) {
            total += containerManager.countItems(player, trace, itemStack, inv);
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
            BlockItem item = itemPool.draw();
            if (item == null)
                return null;

            int count = itemCounts.get(item);
            if (count == 0)
                continue;

            PlaceSnapshot placeSnapshot = PlaceSnapshot.get(world, player, rayTraceResult, pos, item, supportingBlock, options);
            if (placeSnapshot != null) {
                int newCount = (count == Integer.MAX_VALUE) ? Integer.MAX_VALUE : count - 1;
                itemCounts.put(item, newCount);

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
        List<ItemStack> curios = getCuriosInv(player);

        count = takeItemsInvList(count, item, mainInv, false);
        count = takeItemsInvList(count, item, mainInv, true);
        count = takeItemsInvList(count, item, hotbar, true);
        count = takeItemsInvList(count, item, hotbar, false);
        count = takeItemsInvList(count, item, curios, true);

        return count;
    }

    private int takeItemsInvList(int count, Item item, List<ItemStack> inv, boolean container) {
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