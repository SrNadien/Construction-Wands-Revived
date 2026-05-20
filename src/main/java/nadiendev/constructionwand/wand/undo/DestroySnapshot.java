package nadiendev.constructionwand.wand.undo;

import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.Fluids;
import net.minecraft.world.phys.BlockHitResult;
import nadiendev.constructionwand.basics.WandUtil;

import javax.annotation.Nullable;

public class DestroySnapshot implements ISnapshot
{
    private final BlockState block;
    private final BlockPos pos;

    public DestroySnapshot(BlockState block, BlockPos pos) {
        this.pos = pos;
        this.block = block;
    }

    @Nullable
    public static DestroySnapshot get(Level world, Player player, BlockPos pos) {
        if (!WandUtil.isBlockRemovable(world, player, pos)) return null;
        return new DestroySnapshot(world.getBlockState(pos), pos);
    }

    @Override
    public BlockPos getPos() { return pos; }

    @Override
    public BlockState getBlockState() { return block; }

    @Override
    public ItemStack getRequiredItems() { return ItemStack.EMPTY; }

    @Override
    public boolean execute(Level world, Player player, BlockHitResult rayTraceResult) {
        if (!(world instanceof ServerLevel serverLevel)) {
            // Fallback client-side (no debería ocurrir)
            return WandUtil.removeBlock(world, player, block, pos);
        }

        if (!WandUtil.isBlockRemovable(world, player, pos)) return false;

        // Herramienta con Silk Touch para obtener el bloque exacto (grass block, etc.)
        ItemStack silkTool = new ItemStack(Items.NETHERITE_PICKAXE);
        silkTool.enchant(
            serverLevel.registryAccess()
                .lookupOrThrow(net.minecraft.core.registries.Registries.ENCHANTMENT)
                .getOrThrow(Enchantments.SILK_TOUCH),
            1
        );

        // destroyBlock 
        if (player instanceof ServerPlayer serverPlayer) {
            return serverLevel.destroyBlock(pos, true, serverPlayer, 512);
        } else {
            return serverLevel.destroyBlock(pos, true, null, 512);
        }
    }

    @Override
    public boolean canRestore(Level world, Player player) {
        if (!world.isInWorldBounds(pos)) return false;
        if (!world.mayInteract(player, pos)) return false;
        if (player.isCreative()) return true;
        if (!world.isEmptyBlock(pos) && !world.getBlockState(pos).canBeReplaced(Fluids.EMPTY)) return false;
        return !WandUtil.entitiesCollidingWithBlock(world, block, pos);
    }

    @Override
    public boolean restore(Level world, Player player) {
        return WandUtil.placeBlock(world, player, block, pos, null);
    }

    @Override
    public void forceRestore(Level world) {
        world.setBlockAndUpdate(pos, block);
    }
}