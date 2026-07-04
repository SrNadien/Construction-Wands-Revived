package nadiendev.constructionwand.wand.action;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;
import nadiendev.constructionwand.api.IWandAction;
import nadiendev.constructionwand.api.IWandSupplier;
import nadiendev.constructionwand.basics.ConfigServer;
import nadiendev.constructionwand.basics.WandUtil;
import nadiendev.constructionwand.basics.option.WandOptions;
import nadiendev.constructionwand.items.containeritems.ItemVoidSack;
import nadiendev.constructionwand.wand.undo.DestroySnapshot;
import nadiendev.constructionwand.wand.undo.ISnapshot;

import javax.annotation.Nonnull;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;

public class ActionDestruction implements IWandAction
{
    // ─────────────────────────────────────────────────────────────────────────
    // VoidSack drop-interception context
    // ─────────────────────────────────────────────────────────────────────────

    /**
     * Active context for VoidSackDropHandler.
     * Set in VoidSackCapturingSnapshot#execute(), cleared in finally.
     */
    public static final ThreadLocal<VoidSackDropContext> ACTIVE_CONTEXT = new ThreadLocal<>();

    /**
     * Carries the information the drop handler needs to redirect drops.
     *
     * @param pos   The position of the block being broken.
     * @param level The server level in which the break occurs.
     * @param sack  The ItemVoidSack stack that will receive the drops.
     */
    // record is a Java 16+ feature — fully supported under Java 25.
    public record VoidSackDropContext(BlockPos pos, ServerLevel level, ItemStack sack) {}

    // ─────────────────────────────────────────────────────────────────────────
    // IWandAction implementation
    // ─────────────────────────────────────────────────────────────────────────

    @Override
    public int getLimit(ItemStack wand) {
        return ConfigServer.getWandProperties(wand.getItem()).getDestruction();
    }

    @Nonnull
    @Override
    public List<ISnapshot> getSnapshots(
            Level world,
            Player player,
            BlockHitResult rayTraceResult,
            ItemStack wand,
            WandOptions options,
            IWandSupplier supplier,
            int limit)
    {
        LinkedList<ISnapshot> destroySnapshots = new LinkedList<>();
        // Current list of block positions to process
        LinkedList<BlockPos> candidates = new LinkedList<>();
        // All positions that were already processed (prevent double-breaking)
        HashSet<BlockPos> allCandidates = new HashSet<>();

        // Block face the wand was aimed at
        Direction breakFace = rayTraceResult.getDirection();
        // Block the wand was aimed at
        BlockPos startingPoint = rayTraceResult.getBlockPos();
        BlockState targetBlock = world.getBlockState(rayTraceResult.getBlockPos());

        // Seed the candidate list based on which face was hit and which
        // axis locks are active.
        // Vertical face (UP/DOWN) → allow NS/EW spreading
        if (breakFace == Direction.UP || breakFace == Direction.DOWN) {
            if (options.testLock(WandOptions.LOCK.NORTHSOUTH) || options.testLock(WandOptions.LOCK.EASTWEST))
                candidates.add(startingPoint);
        }
        // Side face (N/S/E/W) → allow horizontal/vertical spreading
        else if (options.testLock(WandOptions.LOCK.HORIZONTAL) || options.testLock(WandOptions.LOCK.VERTICAL)) {
            candidates.add(startingPoint);
        }

        // BFS: process candidates until exhausted or the block limit is hit.
        while (!candidates.isEmpty() && destroySnapshots.size() < limit) {
            BlockPos currentCandidate = candidates.removeFirst();

            // Only break blocks whose face toward the player is unobstructed.
            if (!WandUtil.isBlockPermeable(world, currentCandidate.relative(breakFace))) continue;

            try {
                BlockState candidateBlock = world.getBlockState(currentCandidate);

                // Match block type and record the candidate if not yet processed.
                if (options.matchBlocks(targetBlock.getBlock(), candidateBlock.getBlock())
                        && allCandidates.add(currentCandidate))
                {
                    // DestroySnapshot.get 
                    DestroySnapshot snapshot = DestroySnapshot.get(world, player, currentCandidate);
                    if (snapshot == null) continue;
                    destroySnapshots.add(snapshot);

                 
                    switch (breakFace) {
                        case DOWN:
                        case UP:
                            if (options.testLock(WandOptions.LOCK.NORTHSOUTH)) {
                                candidates.add(currentCandidate.relative(Direction.NORTH));
                                candidates.add(currentCandidate.relative(Direction.SOUTH));
                            }
                            if (options.testLock(WandOptions.LOCK.EASTWEST)) {
                                candidates.add(currentCandidate.relative(Direction.EAST));
                                candidates.add(currentCandidate.relative(Direction.WEST));
                            }
                            if (options.testLock(WandOptions.LOCK.NORTHSOUTH) && options.testLock(WandOptions.LOCK.EASTWEST)) {
                                candidates.add(currentCandidate.relative(Direction.NORTH).relative(Direction.EAST));
                                candidates.add(currentCandidate.relative(Direction.NORTH).relative(Direction.WEST));
                                candidates.add(currentCandidate.relative(Direction.SOUTH).relative(Direction.EAST));
                                candidates.add(currentCandidate.relative(Direction.SOUTH).relative(Direction.WEST));
                            }
                            break;

                        case NORTH:
                        case SOUTH:
                            if (options.testLock(WandOptions.LOCK.HORIZONTAL)) {
                                candidates.add(currentCandidate.relative(Direction.EAST));
                                candidates.add(currentCandidate.relative(Direction.WEST));
                            }
                            if (options.testLock(WandOptions.LOCK.VERTICAL)) {
                                candidates.add(currentCandidate.relative(Direction.UP));
                                candidates.add(currentCandidate.relative(Direction.DOWN));
                            }
                            if (options.testLock(WandOptions.LOCK.HORIZONTAL) && options.testLock(WandOptions.LOCK.VERTICAL)) {
                                candidates.add(currentCandidate.relative(Direction.UP).relative(Direction.EAST));
                                candidates.add(currentCandidate.relative(Direction.UP).relative(Direction.WEST));
                                candidates.add(currentCandidate.relative(Direction.DOWN).relative(Direction.EAST));
                                candidates.add(currentCandidate.relative(Direction.DOWN).relative(Direction.WEST));
                            }
                            break;

                        case EAST:
                        case WEST:
                            if (options.testLock(WandOptions.LOCK.HORIZONTAL)) {
                                candidates.add(currentCandidate.relative(Direction.NORTH));
                                candidates.add(currentCandidate.relative(Direction.SOUTH));
                            }
                            if (options.testLock(WandOptions.LOCK.VERTICAL)) {
                                candidates.add(currentCandidate.relative(Direction.UP));
                                candidates.add(currentCandidate.relative(Direction.DOWN));
                            }
                            if (options.testLock(WandOptions.LOCK.HORIZONTAL) && options.testLock(WandOptions.LOCK.VERTICAL)) {
                                candidates.add(currentCandidate.relative(Direction.UP).relative(Direction.NORTH));
                                candidates.add(currentCandidate.relative(Direction.UP).relative(Direction.SOUTH));
                                candidates.add(currentCandidate.relative(Direction.DOWN).relative(Direction.NORTH));
                                candidates.add(currentCandidate.relative(Direction.DOWN).relative(Direction.SOUTH));
                            }
                            break;
                    }
                }
            } catch (Exception e) {
                // Defensive: skip any block that throws unexpectedly.
            }
        }

        // ─────────────────────────────────────────────────────────────────────
        // Void Sack wrapping (server-side only)
        //───────────────────────────────────────────
        if (!world.isClientSide() && player instanceof ServerPlayer serverPlayer) {
            ItemStack voidSack = findSack(serverPlayer);
            if (!voidSack.isEmpty()) {
                List<ISnapshot> wrapped = new ArrayList<>(destroySnapshots.size());
                for (ISnapshot snapshot : destroySnapshots) {
                    wrapped.add(new VoidSackCapturingSnapshot(snapshot, (ServerLevel) world, voidSack));
                }
                return wrapped;
            }
        }

        return destroySnapshots;
    }

    /**
     * Searches the player's full inventory for the first VoidSack.
     */
    public static ItemStack findSack(Player player) {
        ItemStack main = player.getMainHandItem();
        if (main.getItem() instanceof ItemVoidSack) return main;

        ItemStack off = player.getOffhandItem();
        if (off.getItem() instanceof ItemVoidSack) return off;

        for (int i = 0; i < player.getInventory().getContainerSize(); i++) {
            ItemStack stack = player.getInventory().getItem(i);
            if (stack.getItem() instanceof ItemVoidSack) return stack;
        }

        return ItemStack.EMPTY;
    }

    // ─────────────────────────────────────────────────────────────────────────
    // VoidSackCapturingSnapshot
    // ─────────────────────────────────────────────────────────────────────────
    private static class VoidSackCapturingSnapshot implements ISnapshot
    {
        private final ISnapshot delegate;
        private final ServerLevel level;
        private final ItemStack sack;

        VoidSackCapturingSnapshot(ISnapshot delegate, ServerLevel level, ItemStack sack) {
            this.delegate = delegate;
            this.level    = level;
            this.sack     = sack;
        }

        // Delegation — pass through all read-only queries to the wrapped snapshot.
        @Override public BlockPos   getPos()                              { return delegate.getPos(); }
        @Override public BlockState getBlockState()                       { return delegate.getBlockState(); }
        @Override public ItemStack  getRequiredItems()                    { return delegate.getRequiredItems(); }
        @Override public boolean    canRestore(Level w, Player p)        { return delegate.canRestore(w, p); }
        @Override public void       forceRestore(Level w)                { delegate.forceRestore(w); }
        @Override public boolean    restore(Level w, Player p)           { return delegate.restore(w, p); }

        /**
         * Sets ACTIVE_CONTEXT before delegating to the real execute() so that
         * VoidSackDropHandler can see the context when BlockDropsEvent fires.
         *
         * The try/finally guarantees ACTIVE_CONTEXT is always cleared even if
         * the delegate throws.
         */
        @Override
        public boolean execute(Level world, Player player, BlockHitResult ray) {
            ACTIVE_CONTEXT.set(new VoidSackDropContext(delegate.getPos(), level, sack));
            try {
                return delegate.execute(world, player, ray);
            } finally {
                ACTIVE_CONTEXT.remove();
            }
        }
    }

    // ─────────────────────────────────────────────────────────────────────────
    // getSnapshotsFromAir
    //
    // Destruction mode has no "build from air" operation — return empty list.
    // Unchanged from the original; no API touched here.
    // ─────────────────────────────────────────────────────────────────────────
    @Nonnull
    @Override
    public List<ISnapshot> getSnapshotsFromAir(
            Level world,
            Player player,
            BlockHitResult rayTraceResult,
            ItemStack wand,
            WandOptions options,
            IWandSupplier supplier,
            int limit)
    {
        return new ArrayList<>();
    }
}