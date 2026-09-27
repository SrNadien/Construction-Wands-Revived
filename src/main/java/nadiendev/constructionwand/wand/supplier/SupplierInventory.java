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
import nadiendev.constructionwand.basics.StackEntry;
import nadiendev.constructionwand.basics.WandUtil;
import nadiendev.constructionwand.basics.option.WandOptions;
import nadiendev.constructionwand.basics.pool.IPool;
import nadiendev.constructionwand.basics.pool.OrderedPool;
import nadiendev.constructionwand.containers.ContainerManager;
import nadiendev.constructionwand.containers.ContainerTrace;
import nadiendev.constructionwand.wand.undo.PlaceSnapshot;
import top.theillusivec4.curios.api.CuriosApi;

import org.jetbrains.annotations.Nullable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;

public class SupplierInventory implements IWandSupplier {
    protected final Player player;
    protected final WandOptions options;

    protected HashMap<StackEntry, Integer> itemCounts;
    protected IPool<StackEntry> itemPool;

    public SupplierInventory(Player player, WandOptions options) {
        this.player = player;
        this.options = options;
    }

    @Override
    public void getSupply(@Nullable BlockItem target) {
        itemCounts = new LinkedHashMap<>();
        ItemStack offhandStack = player.getItemInHand(InteractionHand.OFF_HAND);

        itemPool = new OrderedPool<>();

        if (!offhandStack.isEmpty() && offhandStack.getItem() instanceof BlockItem) {
            addStack(offhandStack);
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

    /**
     * Da de alta todas las variantes de un item que el jugador lleve encima. Un mismo
     * BlockItem puede existir con componentes distintos (cajones enmarcados con distinto
     * material, shulkers con contenido, bloques configurados por mods) y cada variante
     * entra por separado para que se coloque con sus datos intactos.
     */
    protected void addBlockItem(BlockItem item) {
        boolean found = false;

        for (ItemStack stack : WandUtil.getFullInv(player)) {
            if (stack == null || stack.isEmpty()) continue;
            if (stack.getItem() != item) continue;
            found = true;
            addStack(stack);
        }

        // Si no lo lleva encima todavia puede estar disponible via contenedores, o el
        // jugador estar en creativo: en ese caso se usa la variante limpia.
        if (!found) addStack(new ItemStack(item));
    }

    protected void addStack(ItemStack stack) {
        if (!(stack.getItem() instanceof BlockItem)) return;

        StackEntry entry = new StackEntry(stack);
        if (itemCounts.containsKey(entry)) return;

        ItemStack template = entry.copyStack();
        int count = WandUtil.countItem(player, template);
        count += countItemInContainers(template);

        if (count == 0 && hasContainerWithItem(template)) {
            count = Integer.MAX_VALUE;
        }

        if (count > 0) {
            itemCounts.put(entry, count);
            itemPool.add(entry);
        }
    }

    private List<ItemStack> getCuriosInv(Player player) {
        List<ItemStack> result = new ArrayList<>();
        try {
            CuriosApi.getCuriosInventory(player).ifPresent(inv -> {
                inv.getCurios().forEach((id, handler) -> {
                    var stacks = handler.getStacks();
                    for (int i = 0; i < stacks.getSlots(); i++) {
                        ItemStack stack = stacks.getStackInSlot(i);
                        if (!stack.isEmpty()) result.add(stack);
                    }
                });
            });
        } catch (Exception ignored) {}
        return result;
    }

    private boolean hasContainerWithItem(ItemStack itemStack) {
        ContainerManager containerManager = ConstructionWand.instance.containerManager;

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

    private int countItemInContainers(ItemStack itemStack) {
        if (!(player instanceof ServerPlayer sp)) return 0;

        ContainerManager containerManager = ConstructionWand.instance.containerManager;
        ContainerTrace trace = new ContainerTrace(sp);
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
            StackEntry entry = itemPool.draw();
            if (entry == null)
                return null;

            int count = itemCounts.get(entry);
            if (count == 0)
                continue;

            PlaceSnapshot placeSnapshot = PlaceSnapshot.get(world, player, rayTraceResult, pos, entry.copyStack(), supportingBlock, options);
            if (placeSnapshot != null) {
                int newCount = (count == Integer.MAX_VALUE) ? Integer.MAX_VALUE : count - 1;
                itemCounts.put(entry, newCount);

                if (newCount == 0)
                    itemPool.remove(entry);

                return placeSnapshot;
            }
        }
    }

    @Override
    public int takeItemStack(ItemStack stack) {
        int count = stack.getCount();

        if (player.isCreative())
            return 0;

        List<ItemStack> hotbar = WandUtil.getHotbarWithOffhand(player);
        List<ItemStack> mainInv = WandUtil.getMainInv(player);
        List<ItemStack> curios = getCuriosInv(player);

        count = takeItemsInvList(count, stack, mainInv, false);
        count = takeItemsInvList(count, stack, mainInv, true);
        count = takeItemsInvList(count, stack, hotbar, true);
        count = takeItemsInvList(count, stack, hotbar, false);
        count = takeItemsInvList(count, stack, curios, true);

        return count;
    }

    /**
     * Consume contra la plantilla completa (item + componentes) para no gastar por error
     * una variante distinta de la que se coloco.
     */
    private int takeItemsInvList(int count, ItemStack template, List<ItemStack> inv, boolean container) {
        if (!(player instanceof ServerPlayer sp)) return count;

        ContainerManager containerManager = ConstructionWand.instance.containerManager;
        ContainerTrace trace = new ContainerTrace(sp);

        for (ItemStack stack : inv) {
            if (count == 0) break;

            if (container) {
                int prevCount = count;
                count = containerManager.useItems(player, trace, template.copyWithCount(1), stack, count);
                if (count < prevCount)
                    player.getInventory().setChanged();
            }

            if (!container && WandUtil.stackEquals(stack, template)) {
                int toTake = Math.min(count, stack.getCount());
                stack.shrink(toTake);
                count -= toTake;
                player.getInventory().setChanged();
            }
        }
        return count;
    }
}