package nadiendev.constructionwand.items.containeritems;

import net.minecraft.ChatFormatting;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.nbt.NbtOps;
import net.minecraft.nbt.Tag;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.neoforged.neoforge.capabilities.Capabilities;
import net.neoforged.neoforge.items.IItemHandler;
import org.jetbrains.annotations.Nullable;

import java.util.List;

/**
 * Void Sack — recibe los bloques destruidos por el Destruction Core.
 *
 *  • Almacena hasta 16 stacks internamente (4×4).
 *  • Right-click sobre un bloque-container: lo linkea como destino.
 *  • Right-click en el aire: abre la GUI (4×4 + inventario jugador).
 *  • Toggle en la GUI: enviar ítems al container linkeado o guardar internamente.
 *  • Tecla M (cliente): activa/desactiva el envío al container linkeado.
 *  • Pickup activo: cuando el sack está activo (TAG_ACTIVE = true), los ítems
 *    que el jugador recoge van al sack primero. Se activa con la tecla M.
 */
public class ItemVoidSack extends Item
{
    // ── Claves NBT ────────────────────────────────────────────────────────────
    public static final String TAG_ITEMS        = "Items";
    public static final String TAG_LINKED_POS   = "LinkedPos";
    public static final String TAG_LINKED_DIM   = "LinkedDim";
    public static final String TAG_SEND_TO_CONT = "SendToContainer";
    public static final String TAG_ACTIVE       = "Active";

    public static final int ROWS = 4;
    public static final int COLS = 4;
    public static final int SIZE = ROWS * COLS; // 16 slots

    private final int tier;

    public ItemVoidSack(Properties properties, int tier) {
        super(properties);
        this.tier = tier;
    }

    // ─────────────────────────────────────────────────────────────────────────
    //  Acceso al DataComponent
    // ─────────────────────────────────────────────────────────────────────────

    public static CompoundTag getData(ItemStack sack) {
        return sack.getOrDefault(
                nadiendev.constructionwand.component.ModDataComponents.VOID_SACK_DATA.get(),
                new CompoundTag());
    }

    public static void setData(ItemStack sack, CompoundTag tag) {
        sack.set(
                nadiendev.constructionwand.component.ModDataComponents.VOID_SACK_DATA.get(),
                tag);
    }

    // ─────────────────────────────────────────────────────────────────────────
    //  Inventario interno (4×4)
    // ─────────────────────────────────────────────────────────────────────────

    public static SimpleContainer loadInventory(ItemStack sack) {
        SimpleContainer inv = new SimpleContainer(SIZE);
        CompoundTag tag = getData(sack);
        if (!tag.contains(TAG_ITEMS)) return inv;

        ListTag list = tag.getList(TAG_ITEMS).orElse(null);
        if (list == null) return inv;

        for (int i = 0; i < list.size(); i++) {
            CompoundTag slot = list.getCompound(i).orElse(null);
            if (slot == null) continue;

            int slotIdx = slot.getByte("Slot").orElse((byte) 0) & 0xFF;
            if (slotIdx >= SIZE) continue;

            final int finalSlot = slotIdx;
            ItemStack.OPTIONAL_CODEC
                    .parse(NbtOps.INSTANCE, slot)
                    .result()
                    .ifPresent(s -> inv.setItem(finalSlot, s));
        }
        return inv;
    }

    public static void saveInventory(ItemStack sack, SimpleContainer inv) {
        CompoundTag tag = getData(sack).copy();
        ListTag list = new ListTag();

        for (int i = 0; i < inv.getContainerSize(); i++) {
            ItemStack s = inv.getItem(i);
            if (s.isEmpty()) continue;

            Tag encoded = ItemStack.CODEC
                    .encodeStart(NbtOps.INSTANCE, s)
                    .result()
                    .orElse(null);

            if (encoded instanceof CompoundTag slot) {
                slot.putByte("Slot", (byte) i);
                list.add(slot);
            }
        }

        tag.put(TAG_ITEMS, list);
        setData(sack, tag);
    }

    // ─────────────────────────────────────────────────────────────────────────
    //  Container linkeado
    // ─────────────────────────────────────────────────────────────────────────

    @Nullable
    public static BlockPos getLinkedPos(ItemStack sack) {
        CompoundTag tag = getData(sack);
        if (!tag.contains(TAG_LINKED_POS)) return null;
        return BlockPos.of(tag.getLong(TAG_LINKED_POS).orElse(0L));
    }

    @Nullable
    public static ResourceLocation getLinkedDim(ItemStack sack) {
        CompoundTag tag = getData(sack);
        if (!tag.contains(TAG_LINKED_DIM)) return null;
        return ResourceLocation.tryParse(tag.getString(TAG_LINKED_DIM).orElse(""));
    }

    public static void setLinkedPos(ItemStack sack, @Nullable BlockPos pos,
                                    @Nullable ResourceLocation dim) {
        CompoundTag tag = getData(sack).copy();
        if (pos == null) {
            tag.remove(TAG_LINKED_POS);
            tag.remove(TAG_LINKED_DIM);
        } else {
            tag.putLong(TAG_LINKED_POS, pos.asLong());
            tag.putString(TAG_LINKED_DIM,
                    dim != null ? dim.toString() : "minecraft:overworld");
        }
        setData(sack, tag);
    }

    // ─────────────────────────────────────────────────────────────────────────
    //  Toggle "enviar al container"
    // ─────────────────────────────────────────────────────────────────────────

    public static boolean isSendToContainer(ItemStack sack) {
        return getData(sack).getBoolean(TAG_SEND_TO_CONT).orElse(false);
    }

    public static void setSendToContainer(ItemStack sack, boolean value) {
        CompoundTag tag = getData(sack).copy();
        tag.putBoolean(TAG_SEND_TO_CONT, value);
        setData(sack, tag);
    }

    // ─────────────────────────────────────────────────────────────────────────
    //  Toggle "sack activo" (intercepta pickups con tecla M)
    // ─────────────────────────────────────────────────────────────────────────

    public static boolean isActive(ItemStack sack) {
        return getData(sack).getBoolean(TAG_ACTIVE).orElse(false);
    }

    public static void setActive(ItemStack sack, boolean value) {
        CompoundTag tag = getData(sack).copy();
        tag.putBoolean(TAG_ACTIVE, value);
        setData(sack, tag);
    }

    // ─────────────────────────────────────────────────────────────────────────
    //  Pickup interceptado (llamado desde el evento en el servidor)
    //
    //  Flujo:
    //    1. Si SendToContainer está activo y hay container linkeado:
    //       → intenta insertar en el container externo.
    //    2. Si queda resto (container lleno o no activo):
    //       → inserta en el inventario interno del sack.
    //    3. Si el sack interno también está lleno:
    //       → devuelve la cantidad restante para que vaya al inventario normal.
    // ─────────────────────────────────────────────────────────────────────────

    /**
     * Intenta insertar {@code toInsert} en el Void Sack.
     * @return cantidad de ítems que NO se pudieron insertar (0 = éxito total).
     */
    public static int receive(ServerLevel level, ItemStack sack, ItemStack toInsert) {
        if (sack.isEmpty() || toInsert.isEmpty()) return toInsert.getCount();

        int remaining = toInsert.getCount();

        // 1. Container externo (si toggle activo)
        if (isSendToContainer(sack) && getLinkedPos(sack) != null) {
            remaining = insertToLinkedContainer(level, sack, toInsert.copyWithCount(remaining));
            if (remaining == 0) return 0;
        }

        // 2. Inventario interno
        return insertToInternalInventory(sack, toInsert.copyWithCount(remaining));
    }

    /**
     * Intercepta un pickup del jugador. Solo actúa si el sack está activo (TAG_ACTIVE).
     * Llama a {@link #receive} y devuelve cuántos ítems sobraron sin poder insertar.
     */
    public static int interceptPickup(ServerLevel level, ItemStack sack, ItemStack pickedUp) {
        if (!isActive(sack)) return pickedUp.getCount();
        return receive(level, sack, pickedUp);
    }

    private static int insertToInternalInventory(ItemStack sack, ItemStack toInsert) {
        SimpleContainer inv = loadInventory(sack);
        ItemStack remainder = inv.addItem(toInsert.copy());
        saveInventory(sack, inv);
        return remainder.getCount();
    }

    private static int insertToLinkedContainer(ServerLevel level, ItemStack sack,
                                               ItemStack toInsert) {
        BlockPos pos = getLinkedPos(sack);
        if (pos == null) return toInsert.getCount();

        ResourceLocation dim = getLinkedDim(sack);
        if (dim != null && !dim.equals(level.dimension().location()))
            return toInsert.getCount();

        BlockEntity be = level.getBlockEntity(pos);
        if (be == null) return toInsert.getCount();

        IItemHandler handler = level.getCapability(
                Capabilities.ItemHandler.BLOCK, pos, be.getBlockState(), be, null);
        if (handler == null) return toInsert.getCount();

        ItemStack remaining = toInsert.copy();
        for (int i = 0; i < handler.getSlots() && !remaining.isEmpty(); i++) {
            remaining = handler.insertItem(i, remaining, false);
        }
        return remaining.getCount();
    }

    // ─────────────────────────────────────────────────────────────────────────
    //  Use
    // ─────────────────────────────────────────────────────────────────────────

    @Override
    public InteractionResult useOn(UseOnContext ctx) {
        Level level = ctx.getLevel();
        BlockPos pos = ctx.getClickedPos();
        Player player = ctx.getPlayer();
        ItemStack sack = ctx.getItemInHand();

        if (player == null) return InteractionResult.PASS;

        if (!level.isClientSide) {
            BlockEntity be = level.getBlockEntity(pos);
            if (be != null) {
                IItemHandler handler = level.getCapability(
                        Capabilities.ItemHandler.BLOCK, pos,
                        be.getBlockState(), be, null);
                if (handler != null) {
                    ResourceLocation dim = level.dimension().location();
                    setLinkedPos(sack, pos, dim);
                    if (player instanceof ServerPlayer sp) {
                        sp.sendSystemMessage(Component.translatable(
                                "item.constructionwand.void_sack.linked_msg",
                                pos.getX(), pos.getY(), pos.getZ())
                                .withStyle(ChatFormatting.GREEN));
                    }
                    return InteractionResult.SUCCESS;
                }
            }
        }
        return InteractionResult.PASS;
    }

    @Override
    public InteractionResult use(Level level, Player player, InteractionHand hand) {
        if (!level.isClientSide && player instanceof ServerPlayer serverPlayer) {
            openGui(serverPlayer, hand);
        }
        return level.isClientSide ? InteractionResult.SUCCESS : InteractionResult.CONSUME;
    }

    public static void openGui(ServerPlayer player, InteractionHand hand) {
        player.openMenu(new MenuProvider() {
            @Override
            public Component getDisplayName() {
                return Component.translatable("item.constructionwand.void_sack");
            }

            @Override
            public AbstractContainerMenu createMenu(int id, Inventory inv, Player p) {
                return new MenuVoidSack(id, inv, hand);
            }
        }, buf -> buf.writeBoolean(hand == InteractionHand.MAIN_HAND));
    }

    // ─────────────────────────────────────────────────────────────────────────
    //  Tooltip
    // ─────────────────────────────────────────────────────────────────────────

    @Override
    public void appendHoverText(ItemStack stack, TooltipContext context,
                                net.minecraft.world.item.component.TooltipDisplay tooltipDisplay,
                                java.util.function.Consumer<net.minecraft.network.chat.Component> tooltip,
                                TooltipFlag flag) {
        super.appendHoverText(stack, context, tooltipDisplay, tooltip, flag);

        // Estado activo
        boolean active = isActive(stack);
        tooltip.accept(Component.translatable(
                active ? "item.constructionwand.void_sack.active"
                       : "item.constructionwand.void_sack.inactive")
                .withStyle(active ? ChatFormatting.GREEN : ChatFormatting.DARK_GRAY));

        BlockPos linked = getLinkedPos(stack);
        if (linked != null) {
            tooltip.accept(Component.translatable("item.constructionwand.void_sack.linked",
                    linked.getX(), linked.getY(), linked.getZ())
                    .withStyle(ChatFormatting.GRAY));

            boolean send = isSendToContainer(stack);
            tooltip.accept(Component.translatable(
                    send ? "item.constructionwand.void_sack.sending"
                         : "item.constructionwand.void_sack.storing")
                    .withStyle(send ? ChatFormatting.GREEN : ChatFormatting.YELLOW));
        } else {
            tooltip.accept(Component.translatable("item.constructionwand.void_sack.no_link")
                    .withStyle(ChatFormatting.DARK_GRAY));
        }

        SimpleContainer inv = loadInventory(stack);
        int used = 0;
        for (int i = 0; i < inv.getContainerSize(); i++)
            if (!inv.getItem(i).isEmpty()) used++;

        tooltip.accept(Component.translatable(
                "item.constructionwand.void_sack.slots_used", used, SIZE)
                .withStyle(ChatFormatting.AQUA));
    }
}