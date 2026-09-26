package nadiendev.constructionwand.items.containeritems;

import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.ClickType;
import net.minecraft.world.inventory.MenuType;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;
import nadiendev.constructionwand.registry.ModMenuTypes;

public class MenuVoidSack extends AbstractContainerMenu
{
    private final SimpleContainer sackInventory;
    private final InteractionHand hand;
    private final Player player;

    private static final int SACK_ROWS = 4;
    private static final int SACK_COLS = 4;
    private static final int SACK_SIZE = SACK_ROWS * SACK_COLS; // 16

    private static final int SACK_START_X   = 52;
    private static final int SACK_START_Y   = 18;
    private static final int PLAYER_START_X = 8;
    private static final int PLAYER_START_Y = 110;
    private static final int HOTBAR_START_Y = 168;

    // Constructor normal (servidor)
    public MenuVoidSack(int id, Inventory playerInv, InteractionHand hand) {
        super(ModMenuTypes.VOID_SACK.get(), id);
        this.hand   = hand;
        this.player = playerInv.player;

        ItemStack sack = player.getItemInHand(hand);
        this.sackInventory = ItemVoidSack.loadInventory(sack);
        buildSlots(playerInv);
    }

    // Constructor desde FriendlyByteBuf (cliente, requerido por MenuType factory en 1.21.1)
    public MenuVoidSack(int id, Inventory playerInv, FriendlyByteBuf buf) {
        super(ModMenuTypes.VOID_SACK.get(), id);
        this.hand   = buf.readBoolean() ? InteractionHand.MAIN_HAND : InteractionHand.OFF_HAND;
        this.player = playerInv.player;

        ItemStack sack = player.getItemInHand(hand);
        this.sackInventory = ItemVoidSack.loadInventory(sack);
        buildSlots(playerInv);
    }

    private void buildSlots(Inventory playerInv) {
        // ── Slots del Void Sack (4×4) ─────────────────────────────────────
        for (int row = 0; row < SACK_ROWS; row++) {
            for (int col = 0; col < SACK_COLS; col++) {
                int index = row * SACK_COLS + col;
                addSlot(new Slot(sackInventory, index,
                        SACK_START_X + col * 18,
                        SACK_START_Y + row * 18));
            }
        }

        // ── Inventario jugador (3×9) ──────────────────────────────────────
        for (int row = 0; row < 3; row++) {
            for (int col = 0; col < 9; col++) {
                addSlot(new Slot(playerInv,
                        col + row * 9 + 9,
                        PLAYER_START_X + col * 18,
                        PLAYER_START_Y + row * 18));
            }
        }

        // ── Hotbar (1×9) ──────────────────────────────────────────────────
        for (int col = 0; col < 9; col++) {
            addSlot(new Slot(playerInv, col,
                    PLAYER_START_X + col * 18,
                    HOTBAR_START_Y));
        }
    }

    @Override
    public ItemStack quickMoveStack(Player player, int index) {
        ItemStack result = ItemStack.EMPTY;
        Slot slot = slots.get(index);

        if (slot.hasItem()) {
            ItemStack stack = slot.getItem();
            result = stack.copy();

            if (index < SACK_SIZE) {
                if (!moveItemStackTo(stack, SACK_SIZE, slots.size(), true))
                    return ItemStack.EMPTY;
            } else {
                if (!moveItemStackTo(stack, 0, SACK_SIZE, false))
                    return ItemStack.EMPTY;
            }

            if (stack.isEmpty()) slot.set(ItemStack.EMPTY);
            else slot.setChanged();
        }
        return result;
    }

    @Override
    public boolean stillValid(Player player) {
        ItemStack sack = player.getItemInHand(hand);
        return sack.getItem() instanceof ItemVoidSack;
    }

    @Override
    public void removed(Player player) {
        super.removed(player);
        ItemStack sack = player.getItemInHand(hand);
        if (sack.getItem() instanceof ItemVoidSack) {
            ItemVoidSack.saveInventory(sack, sackInventory);
        }
    }

    public void toggleSendToContainer() {
        ItemStack sack = player.getItemInHand(hand);
        if (sack.getItem() instanceof ItemVoidSack) {
            boolean current = ItemVoidSack.isSendToContainer(sack);
            ItemVoidSack.setSendToContainer(sack, !current);
        }
    }

    public boolean isSendToContainer() {
        ItemStack sack = player.getItemInHand(hand);
        return ItemVoidSack.isSendToContainer(sack);
    }

    public boolean hasLinkedContainer() {
        ItemStack sack = player.getItemInHand(hand);
        return ItemVoidSack.getLinkedPos(sack) != null;
    }

    public InteractionHand getHand() {
        return hand;
    }
}
