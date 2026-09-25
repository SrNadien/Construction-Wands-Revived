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
import nadiendev.constructionwand.containers.ContainerTrace;
import nadiendev.constructionwand.containers.ContainerManager;
import nadiendev.constructionwand.integrations.curios.CuriosCompat;
import nadiendev.constructionwand.wand.undo.PlaceSnapshot;

import javax.annotation.Nullable;
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

    public void getSupply(@Nullable BlockItem target) {
        itemCounts = new LinkedHashMap<>();
        ItemStack offhandStack = player.getItemInHand(InteractionHand.OFF_HAND);

        itemPool = new OrderedPool<>();

        if (!offhandStack.isEmpty() && offhandStack.getItem() instanceof BlockItem) {
            addStack(offhandStack);
        }
        else if (target != null && target != Items.AIR) {
            addBlockItem(target);

            if (options.match.get() != WandOptions.MATCH.EXACT) {
                for (Item it : ReplacementRegistry.getMatchingSet(target)) {
                    if (it instanceof BlockItem blockItem) addBlockItem(blockItem);
                }
            }
        }
    }

    protected void addBlockItem(BlockItem item) {
        boolean found = false;

        for (ItemStack stack : WandUtil.getFullInv(player)) {
            if (stack == null || stack.isEmpty()) continue;
            if (stack.getItem() != item) continue;
            found = true;
            addStack(stack);
        }

        if (!found) addStack(new ItemStack(item));
    }

    protected void addStack(ItemStack stack) {
        if (!(stack.getItem() instanceof BlockItem)) return;

        StackEntry entry = new StackEntry(stack);
        if (itemCounts.containsKey(entry)) return;

        int count = WandUtil.countItem(player, entry.copyStack());
        if (count > 0) {
            itemCounts.put(entry, count);
            itemPool.add(entry);
        }
    }

    @Override
    @Nullable
    public PlaceSnapshot getPlaceSnapshot(Level world, BlockPos pos, BlockHitResult rayTraceResult,
                                          @Nullable BlockState supportingBlock) {
        if (!WandUtil.isPositionPlaceable(world, player, pos, options.replace.get())) return null;
        itemPool.reset();

        while (true) {
            StackEntry entry = itemPool.draw();
            if (entry == null) return null;

            int count = itemCounts.get(entry);
            if (count == 0) continue;

            PlaceSnapshot placeSnapshot = PlaceSnapshot.get(world, player, rayTraceResult, pos, entry.copyStack(), supportingBlock, options);
            if (placeSnapshot != null) {
                int ncount = count - 1;
                itemCounts.put(entry, ncount);

                if (ncount == 0) itemPool.remove(entry);

                return placeSnapshot;
            }
        }
    }

    @Override
    public int takeItemStack(ItemStack stack) {
        int count = stack.getCount();

        if (player.getInventory().getNonEquipmentItems().isEmpty()) return count;
        if (player.isCreative()) return 0;

        List<ItemStack> hotbar  = WandUtil.getHotbarWithOffhand(player);
        List<ItemStack> mainInv = WandUtil.getMainInv(player);
        List<ItemStack> armor   = WandUtil.getArmor(player);

        count = takeItemsInvList(count, stack, mainInv, false);
        count = takeItemsInvList(count, stack, mainInv, true);

        count = takeItemsInvList(count, stack, hotbar, true);
        count = takeItemsInvList(count, stack, hotbar, false);

        count = takeItemsInvList(count, stack, armor, true);
        count = takeItemsInvList(count, stack, armor, false);

        final int remaining = count;
        count = CuriosCompat.useStacks(player,
                curios -> takeItemsInvList(remaining, stack, curios, true), remaining);

        return count;
    }

    private int takeItemsInvList(int count, ItemStack template, List<ItemStack> inv, boolean container) {
        if (count == 0) return 0;
        if (!(player instanceof ServerPlayer serverPlayer)) return count;

        ContainerManager containerManager = ConstructionWand.containerManager;
        ContainerTrace trace = new ContainerTrace(serverPlayer);

        for (ItemStack stack : inv) {
            if (count == 0) break;

            if (container) {
                count = containerManager.useItems(serverPlayer, trace, template.copyWithCount(1), stack, count);
            }

            if (!container && WandUtil.stackEquals(stack, template)) {
                int toTake = Math.min(count, stack.getCount());
                stack.shrink(toTake);
                count -= toTake;
                serverPlayer.getInventory().setChanged();
            }
        }
        return count;
    }
}