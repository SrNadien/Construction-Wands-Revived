package nadiendev.constructionwand.items.containeritems;

import net.minecraft.ChatFormatting;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.Identifier;
import net.minecraft.resources.ResourceKey;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.Container;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import nadiendev.constructionwand.component.ModDataComponents;
import nadiendev.constructionwand.component.VoidSackData;
import org.jetbrains.annotations.Nullable;

import java.util.List;

/**
 * Void Sack
 *
 * By NadienDev
 */
public class ItemVoidSack extends Item
{
    public static final int ROWS = 4;
    public static final int COLS = 4;
    public static final int SIZE = ROWS * COLS; // 16 slots

    public ItemVoidSack(Properties properties) {
        super(properties);
    }

    // ─────────────────────────────────────────────────────────────────────────
    //  Acceso al DataComponent
    // ─────────────────────────────────────────────────────────────────────────

    public static VoidSackData getData(ItemStack sack) {
        return sack.getOrDefault(
                ModDataComponents.VOID_SACK_DATA.get(),
                VoidSackData.EMPTY);
    }

    public static void setData(ItemStack sack, VoidSackData data) {
        sack.set(ModDataComponents.VOID_SACK_DATA.get(), data);
    }

    // ─────────────────────────────────────────────────────────────────────────
    //  Inventario interno (4×4)
    // ─────────────────────────────────────────────────────────────────────────

    public static SimpleContainer loadInventory(ItemStack sack) {
        return getData(sack).loadInventory(SIZE);
    }

    public static void saveInventory(ItemStack sack, SimpleContainer inv) {
        setData(sack, getData(sack).saveInventory(inv));
    }

    // ─────────────────────────────────────────────────────────────────────────
    //  Container linkeado
    // ─────────────────────────────────────────────────────────────────────────

    @Nullable
    public static BlockPos getLinkedPos(ItemStack sack) {
        return getData(sack).getLinkedBlockPos();
    }

    @Nullable
    public static Identifier getLinkedDim(ItemStack sack) {
        String dim = getData(sack).linkedDim();
        if (dim == null || dim.isEmpty()) return null;
        return Identifier.tryParse(dim);
    }

    public static void setLinkedPos(ItemStack sack, @Nullable BlockPos pos,
                                    @Nullable ResourceKey<Level> dim) {
        VoidSackData data = getData(sack);
        if (pos == null) {
            setData(sack, data.withNoLink());
        } else {
            String dimStr = dim != null ? dimensionKeyToString(dim) : "minecraft:overworld";
            setData(sack, data.withLinkedPos(pos.asLong(), dimStr));
        }
    }

    private static String dimensionKeyToString(ResourceKey<Level> key) {
        String s = key.toString();
        int slash = s.lastIndexOf('/');
        int bracket = s.lastIndexOf(']');
        if (slash >= 0 && bracket > slash) {
            return s.substring(slash + 1, bracket).trim();
        }
        return s;
    }

    // ─────────────────────────────────────────────────────────────────────────
    //  Toggle "enviar al container"
    // ─────────────────────────────────────────────────────────────────────────

    public static boolean isSendToContainer(ItemStack sack) {
        return getData(sack).sendToContainer();
    }

    public static void setSendToContainer(ItemStack sack, boolean value) {
        setData(sack, getData(sack).withSendToContainer(value));
    }

    // ─────────────────────────────────────────────────────────────────────────
    //  Toggle "sack activo"
    // ─────────────────────────────────────────────────────────────────────────

    public static boolean isActive(ItemStack sack) {
        return getData(sack).active();
    }

    public static void setActive(ItemStack sack, boolean value) {
        setData(sack, getData(sack).withActive(value));
    }

    // ─────────────────────────────────────────────────────────────────────────
    //  Pickup interceptado
    // ─────────────────────────────────────────────────────────────────────────

    public static int receive(ServerLevel level, ItemStack sack, ItemStack toInsert) {
        if (sack.isEmpty() || toInsert.isEmpty()) return toInsert.getCount();

        int remaining = toInsert.getCount();

        if (isSendToContainer(sack) && getLinkedPos(sack) != null) {
            remaining = insertToLinkedContainer(level, sack, toInsert.copyWithCount(remaining));
            if (remaining == 0) return 0;
        }

        return insertToInternalInventory(sack, toInsert.copyWithCount(remaining));
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

        Identifier dim = getLinkedDim(sack);
        if (dim != null && !dim.toString().equals(dimensionKeyToString(level.dimension())))
            return toInsert.getCount();

        BlockEntity be = level.getBlockEntity(pos);
        if (!(be instanceof Container container)) return toInsert.getCount();

        ItemStack remaining = toInsert.copy();
        for (int i = 0; i < container.getContainerSize() && !remaining.isEmpty(); i++) {
            ItemStack inSlot = container.getItem(i);
            if (inSlot.isEmpty()) {
                int maxAccepted = Math.min(remaining.getCount(), container.getMaxStackSize(remaining));
                ItemStack toPlace = remaining.copyWithCount(maxAccepted);
                container.setItem(i, toPlace);
                container.setChanged();
                remaining.shrink(maxAccepted);
            } else if (ItemStack.isSameItemSameComponents(inSlot, remaining)) {
                int space = Math.min(inSlot.getMaxStackSize(), container.getMaxStackSize(inSlot)) - inSlot.getCount();
                int move = Math.min(space, remaining.getCount());
                if (move > 0) {
                    inSlot.grow(move);
                    container.setItem(i, inSlot);
                    container.setChanged();
                    remaining.shrink(move);
                }
            }
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

        if (!level.isClientSide()) {
            BlockEntity be = level.getBlockEntity(pos);
            if (be instanceof Container) {
                setLinkedPos(sack, pos, level.dimension());
                if (player instanceof ServerPlayer sp) {
                    sp.sendSystemMessage(Component.translatable(
                            "item.constructionwand.void_sack.linked_msg",
                            pos.getX(), pos.getY(), pos.getZ())
                            .withStyle(ChatFormatting.GREEN));
                }
                return InteractionResult.SUCCESS;
            }
        }
        return InteractionResult.PASS;
    }

    @Override
    public InteractionResult use(Level level, Player player, InteractionHand hand) {
        if (!level.isClientSide() && player instanceof ServerPlayer serverPlayer) {
            openGui(serverPlayer, hand);
        }
        return level.isClientSide() ? InteractionResult.SUCCESS : InteractionResult.CONSUME;
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