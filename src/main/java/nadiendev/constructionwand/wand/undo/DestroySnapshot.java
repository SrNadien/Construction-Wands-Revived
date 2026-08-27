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

        // En 26.1, ItemEnchantments se movió a net.minecraft.world.item.enchantment.ItemEnchantments
        // (ya no está en net.minecraft.world.item.component).
        // ItemEnchantments.EMPTY sigue existiendo; ItemEnchantments.Mutable también.
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

        // Nota: silkTool está creado pero destroyBlock usa la herramienta del jugador internamente.
        // Para forzar silk touch en el drop, se puede swapear temporalmente la herramienta
        // o usar el overload que acepta una herramienta custom si existe en 26.1.
        // En vanilla 26.1 destroyBlock no acepta una herramienta override, así que el bloque
        // se romperá con la herramienta del jugador. Si se necesita silk touch garantizado,
        // habría que reemplazar temporalmente el item en mano del jugador.
        ServerPlayer serverPlayer = player instanceof ServerPlayer sp ? sp : null;

        // Con Void Sack activo se mantiene el comportamiento normal: el bloque suelta sus
        // drops en el mundo y el sack los intercepta al recogerlos.
        if(hasActiveVoidSack(player)) {
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