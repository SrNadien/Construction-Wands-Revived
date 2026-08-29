package nadiendev.constructionwand.wand.undo;

import net.minecraft.core.BlockPos;
import net.minecraft.core.component.DataComponents;
import net.minecraft.core.registries.Registries;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.item.enchantment.ItemEnchantments;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.Fluids;
import net.minecraft.world.phys.BlockHitResult;
import nadiendev.constructionwand.basics.WandUtil;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntity;
import nadiendev.constructionwand.items.containeritems.ItemVoidSack;

import javax.annotation.Nullable;
import java.util.List;

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
    public BlockPos getPos() {
        return pos;
    }

    @Override
    public BlockState getBlockState() {
        return block;
    }

    @Override
    public ItemStack getRequiredItems() {
        return ItemStack.EMPTY;
    }

    @Override
    public boolean execute(Level world, Player player, BlockHitResult rayTraceResult) {
        if (!(world instanceof ServerLevel serverLevel)) {
            return WandUtil.removeBlock(world, player, block, pos);
        }

        if (!WandUtil.isBlockRemovable(world, player, pos)) return false;

        
        var silkTouchHolder = serverLevel.registryAccess()
                .lookupOrThrow(Registries.ENCHANTMENT)
                .getOrThrow(Enchantments.SILK_TOUCH);

        // Construir ItemEnchantments con Silk Touch nivel 1
        ItemEnchantments.Mutable mutableEnchantments =
                new ItemEnchantments.Mutable(ItemEnchantments.EMPTY);
        mutableEnchantments.set(silkTouchHolder, 1);

        // Crear la herramienta con el componente de enchantments
        ItemStack silkTool = new ItemStack(Items.NETHERITE_PICKAXE);
        silkTool.set(DataComponents.ENCHANTMENTS, mutableEnchantments.toImmutable());

        ServerPlayer serverPlayer = player instanceof ServerPlayer sp ? sp : null;

        // Con Void Sack activo se mantiene el comportamiento normal: el bloque suelta sus
        // drops en el mundo y el sack los intercepta al recogerlos.
        if(hasActiveVoidSack(player)) {
            return serverLevel.destroyBlock(pos, true, serverPlayer, 512);
        }

        // Sin Void Sack: los drops van directos al inventario del jugador y, cuando el
        // inventario esta lleno, el resto simplemente se descarta.
        BlockState broken = world.getBlockState(pos);
        BlockEntity blockEntity = world.getBlockEntity(pos);
        List<ItemStack> drops = Block.getDrops(broken, serverLevel, pos, blockEntity, player, ItemStack.EMPTY);

        if(!serverLevel.destroyBlock(pos, false, serverPlayer, 512)) return false;

        for(ItemStack drop : drops) {
            if(drop.isEmpty()) continue;
            // add() mete lo que quepa; lo que sobra se pierde (inventario lleno).
            player.getInventory().add(drop);
        }

        return true;
    }

    /** True si el jugador lleva un Void Sack activo en alguna mano. */
    private static boolean hasActiveVoidSack(Player player) {
        for(InteractionHand hand : InteractionHand.values()) {
            ItemStack stack = player.getItemInHand(hand);
            if(stack.getItem() instanceof ItemVoidSack && ItemVoidSack.isActive(stack)) return true;
        }
        return false;
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