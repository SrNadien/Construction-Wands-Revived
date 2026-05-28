package nadiendev.constructionwand.wand.action;

// ──────────────────────────────────────────────────────────────────────────────
// ActionDestruction.java — ported to Minecraft 26.1.2 / NeoForge 26.1.2.33-beta
// ──────────────────────────────────────────────────────────────────────────────
//
// PORTING NOTES (1.21.11 → 26.1.2)
// ─────────────────────────────────
// 1. JAVA 25
//    Minecraft 26.1 requires Java 25 (set `languageVersion = JavaLanguageVersion.of(25)`
//    in your build.gradle toolchain block).  Records, pattern-matching instanceof,
//    sealed classes, and text blocks are all available.  This file already used
//    records and pattern-matching instanceof — no changes required for those.
//
// 2. NO OBFUSCATION
//    Mojang removed obfuscation in 26.1 (see https://www.minecraft.net/en-us/article/removing-obfuscation-in-java-edition).
//    All vanilla class/method/field names now match Mojang's official sources
//    directly; SRG / MCP / Parchment mappings are no longer needed.
//    The classes used here (BlockPos, Direction, ServerLevel, ServerPlayer,
//    BlockState, BlockHitResult, Level, Player, ItemStack) keep their Mojang
//    names unchanged — no import changes required for them.
//
// 3. EVENT CLASSES
//    BlockEvent.BreakEvent and BlockDropsEvent both remain in
//    net.neoforged.neoforge.event.level throughout the 1.21.x → 26.1 line.
//    This file does NOT directly subscribe to those events; it only sets a
//    ThreadLocal context that is consumed by a *separate* @EventBusSubscriber
//    class (VoidSackDropHandler).  That handler must subscribe to
//    BlockDropsEvent on NeoForge.EVENT_BUS (the game bus), which is unchanged.
//
// 4. API STABILITY
//    All vanilla APIs called here — Player#getMainHandItem, Player#getOffhandItem,
//    Player#getInventory, Inventory#getContainerSize, Inventory#getItem,
//    Level#getBlockState, Level#isClientSide, BlockHitResult#getDirection,
//    BlockHitResult#getBlockPos — are stable and present in 26.1.
//
// 5. ITEMSTACK#isEmpty / ItemStack.EMPTY
//    Still present and unchanged in 26.1.
//
// 6. ServerPlayerGameMode#destroyBlock (called inside DestroySnapshot)
//    In 26.1 the method signature is unchanged: destroyBlock(BlockPos).
//    The internal break pipeline is:
//      → Player#blockActionRestricted (pre-check)
//      → BlockEvent.BreakEvent fired (NeoForge.EVENT_BUS, server-only)
//      → Block#playerWillDestroy
//      → IBlockExtension#canHarvestBlock / PlayerEvent.HarvestCheck
//      → IBlockExtension#onDestroyedByPlayer
//      → BlockDropsEvent fired (NeoForge.EVENT_BUS, server-only) ← VoidSackDropHandler hooks here
//      → Block#popExperience
//    The VoidSackCapturingSnapshot wrapper below sets ACTIVE_CONTEXT so that
//    VoidSackDropHandler can identify which block's drops to redirect.
//
// 7. ChunkPos construction changes (NOT used in this file, but note for other files):
//    new ChunkPos(blockPos)  →  ChunkPos.containing(blockPos)
//    ChunkPos.asLong(blockPos)  →  ChunkPos.pack(blockPos)
//    new ChunkPos(packedLong)  →  ChunkPos.unpack(packedLong)
//
// 8. GuiGraphics renamed to GuiGraphicsExtractor in 26.1 (NOT relevant here).
//
// 9. ItemStackTemplate — instantiating ItemStack before registries load now
//    requires ItemStackTemplate (NOT relevant here; we only hold live stacks).
//
// ──────────────────────────────────────────────────────────────────────────────

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
    //
    // HOW THIS WORKS IN 26.1
    // ───────────────────────
    // When execute() is called on a VoidSackCapturingSnapshot, it:
    //   1. Places a VoidSackDropContext into ACTIVE_CONTEXT (ThreadLocal).
    //   2. Calls delegate.execute() which internally calls
    //      ServerPlayerGameMode#destroyBlock(BlockPos).
    //   3. NeoForge fires BlockDropsEvent on NeoForge.EVENT_BUS (server thread).
    //   4. VoidSackDropHandler (a separate @EventBusSubscriber class) reads
    //      ACTIVE_CONTEXT in its @SubscribeEvent handler:
    //        - Cancels the event (suppresses item-entity spawning).
    //        - Inserts the drop stacks directly into the sack's inventory.
    //   5. ACTIVE_CONTEXT is cleared in the finally block.
    //
    // The ThreadLocal is safe here because BlockDropsEvent fires synchronously
    // on the same server thread that called destroyBlock.
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
            // WandUtil.isBlockPermeable is unchanged in 26.1.
            if (!WandUtil.isBlockPermeable(world, currentCandidate.relative(breakFace))) continue;

            try {
                BlockState candidateBlock = world.getBlockState(currentCandidate);

                // Match block type and record the candidate if not yet processed.
                if (options.matchBlocks(targetBlock.getBlock(), candidateBlock.getBlock())
                        && allCandidates.add(currentCandidate))
                {
                    // DestroySnapshot.get performs permission/tool checks internally
                    // using the 26.1 break pipeline (BlockEvent.BreakEvent etc.).
                    DestroySnapshot snapshot = DestroySnapshot.get(world, player, currentCandidate);
                    if (snapshot == null) continue;
                    destroySnapshots.add(snapshot);

                    // Spread to neighbours based on the break face and active locks.
                    // Java 25 allows switch expressions, but the traditional switch
                    // statement is retained here for clarity and zero migration risk.
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
        //
        // If a VoidSack is present in the player's inventory, wrap each
        // snapshot so that BlockDropsEvent is intercepted by VoidSackDropHandler
        // on the NeoForge.EVENT_BUS game bus.
        //
        // Level#isClientSide() is the 26.1 successor to world.isRemote;
        // it is unchanged from 1.20.x → 26.1.
        //
        // Pattern-matching instanceof (Java 16+, available under Java 25):
        //   `player instanceof ServerPlayer serverPlayer`
        // replaces the old cast:
        //   `(ServerPlayer) player`
        // ─────────────────────────────────────────────────────────────────────
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
     *
     * Search order:
     *   1. Main hand  (fastest path for normal use)
     *   2. Off hand
     *   3. Full inventory (hotbar + main grid + armour slots)
     *
     * Player#getMainHandItem, Player#getOffhandItem, Player#getInventory,
     * Inventory#getContainerSize, and Inventory#getItem are all stable
     * in Minecraft 26.1.
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
    //
    // Wraps any ISnapshot so that, just before block destruction, it installs
    // a VoidSackDropContext into ACTIVE_CONTEXT.  VoidSackDropHandler (a
    // separate @EventBusSubscriber) reads this context when BlockDropsEvent
    // fires and redirects all drops into the sack instead of the world.
    //
    // Concurrency note: BlockDropsEvent always fires on the server thread that
    // called ServerPlayerGameMode#destroyBlock, which is the same thread that
    // set the ThreadLocal — so this is safe without extra synchronization.
    //
    // 26.1 API used here:
    //   • ISnapshot — your mod interface, unchanged
    //   • BlockPos, BlockState, ItemStack, Level, Player — unchanged vanilla
    //   • BlockHitResult — unchanged vanilla
    //   • ServerLevel — unchanged, still in net.minecraft.server.level
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