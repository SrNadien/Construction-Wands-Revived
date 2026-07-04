package nadiendev.constructionwand.items.containeritems;

import net.minecraft.ChatFormatting;
import net.minecraft.core.BlockPos;
import net.minecraft.core.component.DataComponents;
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
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.component.CustomData;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.neoforged.neoforge.capabilities.Capabilities;
import net.neoforged.neoforge.items.IItemHandler;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class ItemVoidSack extends Item
{
    public static final String TAG_ROOT         = "VoidSack";
    public static final String TAG_ITEMS        = "Items";
    public static final String TAG_LINKED_POS   = "LinkedPos";
    public static final String TAG_LINKED_DIM   = "LinkedDim";
    public static final String TAG_SEND_TO_CONT = "SendToContainer";
    public static final String TAG_ACTIVE       = "Active";

    public static final int ROWS = 4;
    public static final int COLS = 4;
    public static final int SIZE = ROWS * COLS;

    private final int tier;

    public ItemVoidSack(Properties properties, int tier) {
        super(properties);
        this.tier = tier;
    }

    // ─── NBT via CustomData component (1.21.1) ────────────────────────────────

    public static CompoundTag getData(ItemStack sack) {
        CustomData cd = sack.getOrDefault(DataComponents.CUSTOM_DATA, CustomData.EMPTY);
        CompoundTag root = cd.copyTag();
        if (!root.contains(TAG_ROOT, Tag.TAG_COMPOUND)) {
            root.put(TAG_ROOT, new CompoundTag());
        }
        return root.getCompound(TAG_ROOT);
    }

    public static void setData(ItemStack sack, CompoundTag tag) {
        CustomData cd = sack.getOrDefault(DataComponents.CUSTOM_DATA, CustomData.EMPTY);
        CompoundTag root = cd.copyTag();
        root.put(TAG_ROOT, tag);
        sack.set(DataComponents.CUSTOM_DATA, CustomData.of(root));
    }

    // ─── Inventario interno 4×4 ───────────────────────────────────────────────

    public static SimpleContainer loadInventory(ItemStack sack) {
        SimpleContainer inv = new SimpleContainer(SIZE);
        CompoundTag tag = getData(sack);
        if (!tag.contains(TAG_ITEMS, Tag.TAG_LIST)) return inv;

        ListTag list = tag.getList(TAG_ITEMS, Tag.TAG_COMPOUND);
        for (int i = 0; i < list.size(); i++) {
            CompoundTag slot = list.getCompound(i);
            int slotIdx = slot.getByte("Slot") & 0xFF;
            if (slotIdx >= SIZE) continue;
            ItemStack s = ItemStack.CODEC
                    .parse(NbtOps.INSTANCE, slot)
                    .result()
                    .orElse(ItemStack.EMPTY);
            if (!s.isEmpty()) inv.setItem(slotIdx, s);
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
            if (!(encoded instanceof CompoundTag slot)) continue;
            slot.putByte("Slot", (byte) i);
            list.add(slot);
        }

        tag.put(TAG_ITEMS, list);
        setData(sack, tag);
    }

    // ─── Container linkeado ───────────────────────────────────────────────────

    @Nullable
    public static BlockPos getLinkedPos(ItemStack sack) {
        CompoundTag tag = getData(sack);
        if (!tag.contains(TAG_LINKED_POS, Tag.TAG_LONG)) return null;
        return BlockPos.of(tag.getLong(TAG_LINKED_POS));
    }

    @Nullable
    public static ResourceLocation getLinkedDim(ItemStack sack) {
        CompoundTag tag = getData(sack);
        if (!tag.contains(TAG_LINKED_DIM, Tag.TAG_STRING)) return null;
        return ResourceLocation.tryParse(tag.getString(TAG_LINKED_DIM));
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

    // ─── Toggle send to container ─────────────────────────────────────────────

    public static boolean isSendToContainer(ItemStack sack) {
        return getData(sack).getBoolean(TAG_SEND_TO_CONT);
    }

    public static void setSendToContainer(ItemStack sack, boolean value) {
        CompoundTag tag = getData(sack).copy();
        tag.putBoolean(TAG_SEND_TO_CONT, value);
        setData(sack, tag);
    }

    // ─── Toggle activo ────────────────────────────────────────────────────────

    public static boolean isActive(ItemStack sack) {
        return getData(sack).getBoolean(TAG_ACTIVE);
    }

    public static void setActive(ItemStack sack, boolean value) {
        CompoundTag tag = getData(sack).copy();
        tag.putBoolean(TAG_ACTIVE, value);
        setData(sack, tag);
    }

    // ─── Recepción de ítems ───────────────────────────────────────────────────

    public static int receive(ServerLevel level, ItemStack sack, ItemStack toInsert) {
        if (sack.isEmpty() || toInsert.isEmpty()) return toInsert.getCount();

        int remaining = toInsert.getCount();

        if (isSendToContainer(sack) && getLinkedPos(sack) != null) {
            remaining = insertToLinkedContainer(level, sack, toInsert.copy());
            if (remaining == 0) return 0;
        }

        ItemStack leftover = toInsert.copy();
        leftover.setCount(remaining);
        return insertToInternalInventory(sack, leftover);
    }

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

    // ─── Use ──────────────────────────────────────────────────────────────────

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
    public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand hand) {
        ItemStack sack = player.getItemInHand(hand);
        if (!level.isClientSide && player instanceof ServerPlayer serverPlayer) {
            openGui(serverPlayer, hand);
        }
        return InteractionResultHolder.sidedSuccess(sack, level.isClientSide);
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

    // ─── Tooltip ──────────────────────────────────────────────────────────────

    @Override
    public void appendHoverText(ItemStack stack, TooltipContext context,
                                List<Component> tooltip, TooltipFlag flag) {
        super.appendHoverText(stack, context, tooltip, flag);

        boolean active = isActive(stack);
        tooltip.add(Component.translatable(
                active ? "item.constructionwand.void_sack.active"
                       : "item.constructionwand.void_sack.inactive")
                .withStyle(active ? ChatFormatting.GREEN : ChatFormatting.DARK_GRAY));

        BlockPos linked = getLinkedPos(stack);
        if (linked != null) {
            tooltip.add(Component.translatable("item.constructionwand.void_sack.linked",
                    linked.getX(), linked.getY(), linked.getZ())
                    .withStyle(ChatFormatting.GRAY));

            boolean send = isSendToContainer(stack);
            tooltip.add(Component.translatable(
                    send ? "item.constructionwand.void_sack.sending"
                         : "item.constructionwand.void_sack.storing")
                    .withStyle(send ? ChatFormatting.GREEN : ChatFormatting.YELLOW));
        } else {
            tooltip.add(Component.translatable("item.constructionwand.void_sack.no_link")
                    .withStyle(ChatFormatting.DARK_GRAY));
        }

        SimpleContainer inv = loadInventory(stack);
        int used = 0;
        for (int i = 0; i < inv.getContainerSize(); i++)
            if (!inv.getItem(i).isEmpty()) used++;

        tooltip.add(Component.translatable(
                "item.constructionwand.void_sack.slots_used", used, SIZE)
                .withStyle(ChatFormatting.AQUA));
    }
}