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
    /**
     * Contexto activo para VoidSackDropHandler.
     * Se setea en VoidSackCapturingSnapshot.execute() y se limpia en finally.
     */
    public static final ThreadLocal<VoidSackDropContext> ACTIVE_CONTEXT = new ThreadLocal<>();

    public record VoidSackDropContext(BlockPos pos, ServerLevel level, ItemStack sack) {}

    @Override
    public int getLimit(ItemStack wand) {
        return ConfigServer.getWandProperties(wand.getItem()).getDestruction();
    }

    @Nonnull
    @Override
    public List<ISnapshot> getSnapshots(Level world, Player player, BlockHitResult rayTraceResult,
                                        ItemStack wand, WandOptions options, IWandSupplier supplier, int limit) {
        LinkedList<ISnapshot> destroySnapshots = new LinkedList<>();
        LinkedList<BlockPos> candidates = new LinkedList<>();
        HashSet<BlockPos> allCandidates = new HashSet<>();

        Direction breakFace = rayTraceResult.getDirection();
        BlockPos startingPoint = rayTraceResult.getBlockPos();
        BlockState targetBlock = world.getBlockState(rayTraceResult.getBlockPos());

        if (breakFace == Direction.UP || breakFace == Direction.DOWN) {
            if (options.testLock(WandOptions.LOCK.NORTHSOUTH) || options.testLock(WandOptions.LOCK.EASTWEST))
                candidates.add(startingPoint);
        } else if (options.testLock(WandOptions.LOCK.HORIZONTAL) || options.testLock(WandOptions.LOCK.VERTICAL))
            candidates.add(startingPoint);

        while (!candidates.isEmpty() && destroySnapshots.size() < limit) {
            BlockPos currentCandidate = candidates.removeFirst();
            if (!WandUtil.isBlockPermeable(world, currentCandidate.relative(breakFace))) continue;

            try {
                BlockState candidateBlock = world.getBlockState(currentCandidate);
                if (options.matchBlocks(targetBlock.getBlock(), candidateBlock.getBlock()) &&
                        allCandidates.add(currentCandidate)) {
                    DestroySnapshot snapshot = DestroySnapshot.get(world, player, currentCandidate);
                    if (snapshot == null) continue;
                    destroySnapshots.add(snapshot);

                    switch (breakFace) {
                        case DOWN, UP -> {
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
                        }
                        case NORTH, SOUTH -> {
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
                        }
                        case EAST, WEST -> {
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
                        }
                    }
                }
            } catch (Exception ignored) {}
        }

        // Envolver snapshots con el contexto del Void Sack si hay uno en el inventario
        if (!world.isClientSide && player instanceof ServerPlayer serverPlayer) {
            ItemStack voidSack = findSack(serverPlayer);
            if (!voidSack.isEmpty()) {
                List<ISnapshot> wrapped = new ArrayList<>(destroySnapshots.size());
                for (ISnapshot s : destroySnapshots)
                    wrapped.add(new VoidSackCapturingSnapshot(s, (ServerLevel) world, voidSack));
                return wrapped;
            }
        }

        return destroySnapshots;
    }

    /** Busca el primer Void Sack en el inventario completo del jugador. */
    public static ItemStack findSack(Player player) {
        ItemStack main = player.getMainHandItem();
        if (main.getItem() instanceof ItemVoidSack) return main;

        ItemStack off = player.getOffhandItem();
        if (off.getItem() instanceof ItemVoidSack) return off;

        for (int i = 0; i < player.getInventory().getContainerSize(); i++) {
            ItemStack s = player.getInventory().getItem(i);
            if (s.getItem() instanceof ItemVoidSack) return s;
        }
        return ItemStack.EMPTY;
    }

    /**
     * Wrapper de ISnapshot que setea ACTIVE_CONTEXT antes de ejecutar,
     * para que VoidSackDropHandler pueda interceptar los drops del destroyBlock.
     */
    private static class VoidSackCapturingSnapshot implements ISnapshot
    {
        private final ISnapshot delegate;
        private final ServerLevel level;
        private final ItemStack sack;

        VoidSackCapturingSnapshot(ISnapshot delegate, ServerLevel level, ItemStack sack) {
            this.delegate = delegate;
            this.level = level;
            this.sack = sack;
        }

        @Override public BlockPos getPos()                          { return delegate.getPos(); }
        @Override public BlockState getBlockState()                 { return delegate.getBlockState(); }
        @Override public ItemStack getRequiredItems()               { return delegate.getRequiredItems(); }
        @Override public boolean canRestore(Level w, Player p)     { return delegate.canRestore(w, p); }
        @Override public void forceRestore(Level w)                 { delegate.forceRestore(w); }
        @Override public boolean restore(Level w, Player p)         { return delegate.restore(w, p); }

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

    @Nonnull
    @Override
    public List<ISnapshot> getSnapshotsFromAir(Level world, Player player, BlockHitResult rayTraceResult,
                                               ItemStack wand, WandOptions options, IWandSupplier supplier, int limit) {
        return new ArrayList<>();
    }
}