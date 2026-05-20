package com.teamsmartstreamlabs.smartbackpacks.client.screen;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import com.teamsmartstreamlabs.smartbackpacks.SmartBackpacks;
import com.teamsmartstreamlabs.smartbackpacks.inventory.BackpackSortMode;
import com.teamsmartstreamlabs.smartbackpacks.menu.BackpackMenu;
import com.teamsmartstreamlabs.smartbackpacks.network.SetBackpackScrollOffsetPayload;
import com.teamsmartstreamlabs.smartbackpacks.network.SortOpenBackpackPayload;
import com.teamsmartstreamlabs.smartbackpacks.network.ToggleUpgradeEnabledPayload;
import com.teamsmartstreamlabs.smartbackpacks.network.UseInstalledUpgradePayload;

import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.components.EditBox;
import net.minecraft.client.gui.components.Tooltip;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;
import net.neoforged.neoforge.network.PacketDistributor;

public class BackpackScreen extends LegacyContainerScreen<BackpackMenu> {
    private static final ResourceLocation TEXTURE = new ResourceLocation(SmartBackpacks.MOD_ID, "textures/gui/container/backpack.png");
    private static final float TITLE_SCALE = 0.8F;
    private static final int LABEL_COLOR = 0xFF404040;
    private static final int SEARCH_HINT_COLOR = 0xFF7A7268;
    private static final int UPGRADE_PANEL_WIDTH = 26;
    private static final int SLOT_SIZE = 18;
    private static final int SCROLLBAR_WIDTH = 6;
    private static final int SCROLLBAR_MIN_HEIGHT = 15;
    private static final int SCROLLBAR_MARGIN = 3;
    private static final int SCROLLBAR_TRACK_COLOR = 0xFF8E7760;
    private static final int SCROLLBAR_THUMB_COLOR = 0xFFD9C8B1;
    private static final int SCROLLBAR_BORDER_COLOR = 0xFF5E4935;
    private static final int STACK_TEXT_BACKGROUND = 0xFFD3C1A7;
    private static final NumberFormat STACK_COUNT_FORMAT = NumberFormat.getIntegerInstance(Locale.US);

    private BackpackSortMode sortMode;
    private Button sortButton;
    private Button[] upgradeToggleButtons = new Button[0];
    private EditBox searchBox;
    private boolean scrolling;

    public BackpackScreen(BackpackMenu menu, Inventory playerInventory, Component title) {
        super(menu, playerInventory, title, 176 + UPGRADE_PANEL_WIDTH, menu.getImageHeight());
        this.titleLabelX = UPGRADE_PANEL_WIDTH + 8;
        this.titleLabelY = 6;
        this.inventoryLabelX = UPGRADE_PANEL_WIDTH + 8;
        this.inventoryLabelY = this.imageHeight - 94;
        this.sortMode = menu.getSortMode();
    }

    @Override
    protected void init() {
        super.init();
        this.searchBox = this.addRenderableWidget(new EditBox(this.font, this.leftPos + UPGRADE_PANEL_WIDTH + 92, this.topPos + 3, 42, 12,
                Component.translatable("screen.smartbackpacks.search")));
        this.searchBox.setBordered(true);
        this.searchBox.setTextColor(LABEL_COLOR);
        this.searchBox.setTextColorUneditable(SEARCH_HINT_COLOR);
        this.searchBox.setMaxLength(50);
        this.searchBox.setResponder(value -> this.applySearchFilter());
        this.searchBox.setCanLoseFocus(true);

        this.sortButton = this.addRenderableWidget(Button.builder(this.getSortButtonLabel(), button -> this.cycleSortMode())
                .pos(this.leftPos + this.imageWidth - 24, this.topPos + 1)
                .size(16, 16)
                .tooltip(this.getSortButtonTooltip())
                .build());
        this.initUpgradeButtons();
        this.applySearchFilter();
    }

    public BackpackSortMode getSortMode() {
        return this.sortMode;
    }

    @Override
    protected void renderBg(GuiGraphics guiGraphics, float partialTick, int mouseX, int mouseY) {
        int x = this.leftPos;
        int y = this.topPos;
        int visibleRows = this.menu.getVisibleBackpackRows();
        int upgradeRows = Math.max(visibleRows + 1, this.menu.getUpgradeSlotCount());
        int upgradeFooterHeight = Math.max(0, this.imageHeight - (17 + upgradeRows * 18));

        guiGraphics.blit(TEXTURE, x, y, 0.0F, 0.0F, UPGRADE_PANEL_WIDTH, 17, 176, 131);
        for (int row = 0; row < upgradeRows; row++) {
            guiGraphics.blit(TEXTURE, x, y + 17 + row * 18, 0.0F, 17.0F, UPGRADE_PANEL_WIDTH, 18, 176, 131);
        }
        if (upgradeFooterHeight > 0) {
            guiGraphics.blit(TEXTURE, x, y + 17 + upgradeRows * 18, 0.0F, 35.0F, UPGRADE_PANEL_WIDTH, upgradeFooterHeight, 176, 131);
        }

        guiGraphics.blit(TEXTURE, x + UPGRADE_PANEL_WIDTH, y, 0.0F, 0.0F, 176, 17, 176, 131);
        for (int row = 0; row < visibleRows; row++) {
            guiGraphics.blit(TEXTURE, x + UPGRADE_PANEL_WIDTH, y + 17 + row * 18, 0.0F, 17.0F, 176, 18, 176, 131);
        }
        guiGraphics.blit(TEXTURE, x + UPGRADE_PANEL_WIDTH, y + 17 + visibleRows * 18, 0.0F, 35.0F, 176, 18, 176, 131);
        guiGraphics.blit(TEXTURE, x + UPGRADE_PANEL_WIDTH, y + 35 + visibleRows * 18, 0.0F, 35.0F, 176, 96, 176, 131);
        this.renderScrollBar(guiGraphics);
    }

    @Override
    protected void renderForeground(GuiGraphics guiGraphics, int mouseX, int mouseY, float partialTick) {
        this.updateUpgradeButtons();
        this.renderCompactBackpackCounts(guiGraphics);
        if (this.searchBox.getValue().isEmpty() && !this.searchBox.isFocused()) {
            guiGraphics.drawString(this.font, Component.translatable("screen.smartbackpacks.search_hint"),
                    this.searchBox.getX() + 4, this.searchBox.getY() + 2, SEARCH_HINT_COLOR, false);
        }
    }

    @Override
    protected void renderLabels(GuiGraphics guiGraphics, int mouseX, int mouseY) {
        int maxTitleWidth = Math.round((this.searchBox.getX() - this.leftPos - this.titleLabelX - 6) / TITLE_SCALE);
        Component titleComponent = this.title;
        if (this.font.width(titleComponent) > maxTitleWidth) {
            String shortened = this.font.plainSubstrByWidth(titleComponent.getString(), Math.max(0, maxTitleWidth - this.font.width("...")));
            titleComponent = Component.literal(shortened + "...");
        }

        guiGraphics.pose().pushPose();
        guiGraphics.pose().scale(TITLE_SCALE, TITLE_SCALE, 1.0F);
        guiGraphics.drawString(this.font, titleComponent,
                Math.round(this.titleLabelX / TITLE_SCALE),
                Math.round(this.titleLabelY / TITLE_SCALE) + 1,
                LABEL_COLOR, false);
        guiGraphics.pose().popPose();

        guiGraphics.drawString(this.font, Component.translatable("screen.smartbackpacks.upgrades_short"),
                2, 6, LABEL_COLOR, false);
        guiGraphics.drawString(this.font, this.playerInventoryTitle,
                this.inventoryLabelX, this.inventoryLabelY, LABEL_COLOR, false);
    }

    @Override
    public boolean keyPressed(int keyCode, int scanCode, int modifiers) {
        if (this.searchBox.isFocused()) {
            if (this.searchBox.keyPressed(keyCode, scanCode, modifiers)) {
                return true;
            }

            if (this.minecraft != null && this.minecraft.options.keyInventory.matches(keyCode, scanCode)) {
                return true;
            }
        }
        return super.keyPressed(keyCode, scanCode, modifiers);
    }

    @Override
    public boolean charTyped(char codePoint, int modifiers) {
        if (this.searchBox.isFocused() && this.searchBox.charTyped(codePoint, modifiers)) {
            return true;
        }
        return super.charTyped(codePoint, modifiers);
    }

    @Override
    public boolean mouseClicked(double mouseX, double mouseY, int button) {
        if (this.searchBox.mouseClicked(mouseX, mouseY, button)) {
            this.setFocused(this.searchBox);
            return true;
        }

        if (button == 1 && this.hoveredSlot != null) {
            int backpackSlots = this.menu.getBackpackSlotCount();
            int slotIndex = this.menu.slots.indexOf(this.hoveredSlot);
            if (slotIndex >= backpackSlots && slotIndex < backpackSlots + this.menu.getUpgradeSlotCount()) {
                Slot slot = this.hoveredSlot;
                if (slot.hasItem()) {
                    PacketDistributor.sendToServer(new UseInstalledUpgradePayload(slotIndex - backpackSlots));
                    return true;
                }
            }
        }

        if (button == 0 && this.isMouseOverScrollBar(mouseX, mouseY)) {
            this.scrolling = true;
            this.scrollTo(mouseY);
            return true;
        }
        return super.mouseClicked(mouseX, mouseY, button);
    }

    @Override
    public boolean mouseScrolled(double mouseX, double mouseY, double scrollDelta) {
        if (!this.menu.canScroll() || scrollDelta == 0 || !this.isMouseOverBackpackArea(mouseX, mouseY)) {
            return super.mouseScrolled(mouseX, mouseY, scrollDelta);
        }

        this.menu.scrollRows(scrollDelta < 0 ? 1 : -1);
        this.syncScrollOffset();
        this.applySearchFilter();
        return true;
    }

    @Override
    public boolean mouseDragged(double mouseX, double mouseY, int button, double dragX, double dragY) {
        if (this.scrolling) {
            this.scrollTo(mouseY);
            return true;
        }
        return super.mouseDragged(mouseX, mouseY, button, dragX, dragY);
    }

    @Override
    public boolean mouseReleased(double mouseX, double mouseY, int button) {
        this.scrolling = false;
        return super.mouseReleased(mouseX, mouseY, button);
    }

    private void cycleSortMode() {
        this.sortMode = this.sortMode.next();
        this.sortButton.setMessage(this.getSortButtonLabel());
        this.sortButton.setTooltip(this.getSortButtonTooltip());
        PacketDistributor.sendToServer(new SortOpenBackpackPayload(this.sortMode));
    }

    private void applySearchFilter() {
        String query = this.searchBox.getValue().trim().toLowerCase();
        this.menu.applyClientSearchFilter(stack -> query.isEmpty() || this.matchesSearch(stack, query));
    }

    private boolean matchesSearch(ItemStack stack, String query) {
        if (stack.isEmpty()) {
            return false;
        }

        String itemName = stack.getHoverName().getString().toLowerCase();
        String itemId = BuiltInRegistries.ITEM.getKey(stack.getItem()).toString().toLowerCase();
        String modId = BuiltInRegistries.ITEM.getKey(stack.getItem()).getNamespace().toLowerCase();

        if (query.startsWith("@")) {
            String modQuery = query.substring(1).trim();
            return !modQuery.isEmpty() && modId.contains(modQuery);
        }

        return itemName.contains(query) || itemId.contains(query);
    }

    private Component getSortButtonLabel() {
        return Component.literal(this.sortMode.getShortLabel());
    }

    private Tooltip getSortButtonTooltip() {
        return Tooltip.create(Component.translatable("tooltip.smartbackpacks.sort_button",
                Component.translatable(this.sortMode.getTranslationKey())));
    }

    private void initUpgradeButtons() {
        this.upgradeToggleButtons = new Button[this.menu.getUpgradeSlotCount()];
        for (int slot = 0; slot < this.upgradeToggleButtons.length; slot++) {
            int upgradeSlot = slot;
            Button button = this.addRenderableWidget(Button.builder(Component.literal("\u25cf"), ignored -> this.toggleUpgrade(upgradeSlot))
                    .pos(this.leftPos + 1, this.topPos + 23 + slot * 18)
                    .size(8, 8)
                    .build());
            this.upgradeToggleButtons[slot] = button;
        }
        this.updateUpgradeButtons();
    }

    private void updateUpgradeButtons() {
        for (int slot = 0; slot < this.upgradeToggleButtons.length; slot++) {
            Button button = this.upgradeToggleButtons[slot];
            ItemStack stack = this.menu.getUpgradeStack(slot);
            boolean hasUpgrade = !stack.isEmpty();
            boolean enabled = this.menu.isUpgradeEnabled(slot);
            button.visible = hasUpgrade;
            button.active = hasUpgrade;
            button.setMessage(Component.literal(enabled ? "\u25cf" : "\u25c1"));
            button.setTooltip(Tooltip.create(Component.translatable(
                    enabled ? "tooltip.smartbackpacks.upgrade_enabled" : "tooltip.smartbackpacks.upgrade_disabled")));
        }
    }

    private void toggleUpgrade(int upgradeSlot) {
        PacketDistributor.sendToServer(new ToggleUpgradeEnabledPayload(upgradeSlot));
    }

    private void renderScrollBar(GuiGraphics guiGraphics) {
        if (!this.menu.canScroll()) {
            return;
        }

        int trackX = this.getScrollBarX();
        int trackY = this.getScrollBarY();
        int trackHeight = this.getScrollBarTrackHeight();
        int thumbHeight = this.getScrollBarThumbHeight();
        int thumbY = this.getScrollBarThumbY();

        guiGraphics.fill(trackX - 1, trackY - 1, trackX + SCROLLBAR_WIDTH + 1, trackY + trackHeight + 1, SCROLLBAR_BORDER_COLOR);
        guiGraphics.fill(trackX, trackY, trackX + SCROLLBAR_WIDTH, trackY + trackHeight, SCROLLBAR_TRACK_COLOR);
        guiGraphics.fill(trackX, thumbY, trackX + SCROLLBAR_WIDTH, thumbY + thumbHeight, SCROLLBAR_THUMB_COLOR);
    }

    private boolean isMouseOverBackpackArea(double mouseX, double mouseY) {
        int backpackX = this.leftPos + UPGRADE_PANEL_WIDTH + 8;
        int backpackY = this.topPos + 18;
        int backpackWidth = 9 * SLOT_SIZE + 16;
        int backpackHeight = this.menu.getVisibleBackpackRows() * SLOT_SIZE;
        return mouseX >= backpackX
                && mouseX < backpackX + backpackWidth
                && mouseY >= backpackY
                && mouseY < backpackY + backpackHeight;
    }

    private boolean isMouseOverScrollBar(double mouseX, double mouseY) {
        if (!this.menu.canScroll()) {
            return false;
        }

        int trackX = this.getScrollBarX();
        int trackY = this.getScrollBarY();
        int trackHeight = this.getScrollBarTrackHeight();
        return mouseX >= trackX
                && mouseX < trackX + SCROLLBAR_WIDTH
                && mouseY >= trackY
                && mouseY < trackY + trackHeight;
    }

    private void scrollTo(double mouseY) {
        int maxOffset = this.menu.getMaxScrollRowOffset();
        if (maxOffset <= 0) {
            this.menu.setFirstVisibleRow(0);
            this.syncScrollOffset();
            return;
        }

        int trackY = this.getScrollBarY();
        int trackHeight = this.getScrollBarTrackHeight();
        int thumbHeight = this.getScrollBarThumbHeight();
        int maxThumbTravel = Math.max(1, trackHeight - thumbHeight);
        double thumbCenterOffset = thumbHeight / 2.0D;
        double relative = Mth.clamp(mouseY - trackY - thumbCenterOffset, 0.0D, maxThumbTravel);
        int rowOffset = Mth.floor((relative / maxThumbTravel) * maxOffset + 0.5D);
        this.menu.setFirstVisibleRow(rowOffset);
        this.syncScrollOffset();
        this.applySearchFilter();
    }

    private int getScrollBarX() {
        return this.leftPos + UPGRADE_PANEL_WIDTH + 176 - SCROLLBAR_WIDTH - SCROLLBAR_MARGIN;
    }

    private int getScrollBarY() {
        return this.topPos + 18;
    }

    private int getScrollBarTrackHeight() {
        return this.menu.getVisibleBackpackRows() * SLOT_SIZE;
    }

    private int getScrollBarThumbHeight() {
        int totalRows = this.menu.getTotalBackpackRows();
        int visibleRows = this.menu.getVisibleBackpackRows();
        int trackHeight = this.getScrollBarTrackHeight();
        if (totalRows <= 0) {
            return trackHeight;
        }

        return Math.max(SCROLLBAR_MIN_HEIGHT, Math.round((visibleRows / (float) totalRows) * trackHeight));
    }

    private int getScrollBarThumbY() {
        int trackY = this.getScrollBarY();
        int trackHeight = this.getScrollBarTrackHeight();
        int thumbHeight = this.getScrollBarThumbHeight();
        int maxTravel = Math.max(0, trackHeight - thumbHeight);
        return trackY + Math.round(this.menu.getScrollProgress() * maxTravel);
    }

    private void syncScrollOffset() {
        PacketDistributor.sendToServer(new SetBackpackScrollOffsetPayload(this.menu.getFirstVisibleRow()));
    }

    private void renderCompactBackpackCounts(GuiGraphics guiGraphics) {
        int backpackSlots = this.menu.getBackpackSlotCount();
        for (int index = 0; index < backpackSlots; index++) {
            Slot slot = this.menu.slots.get(index);
            if (!slot.isActive() || !slot.hasItem()) {
                continue;
            }

            int count = slot.getItem().getCount();
            if (count < 1000) {
                continue;
            }

            String label = this.formatCompactCount(count);
            int slotX = this.leftPos + slot.x;
            int slotY = this.topPos + slot.y;
            int textWidth = this.font.width(label);
            int textX = slotX + 17 - textWidth;
            int textY = slotY + 9;
            guiGraphics.fill(textX - 1, textY - 1, slotX + 17, slotY + 17, STACK_TEXT_BACKGROUND);
            guiGraphics.drawString(this.font, label, textX + 1, textY + 1, 0x3F3F3F, false);
            guiGraphics.drawString(this.font, label, textX, textY, 0xFFFFFF, false);
        }
    }

    private String formatCompactCount(int count) {
        if (count < 10_000) {
            return STACK_COUNT_FORMAT.format(count);
        }
        if (count >= 1_000_000_000) {
            return this.formatWithSuffix(count, 1_000_000_000, "b");
        }
        if (count >= 1_000_000) {
            return this.formatWithSuffix(count, 1_000_000, "m");
        }
        return this.formatWithSuffix(count, 1_000, "k");
    }

    private String formatWithSuffix(int count, int divisor, String suffix) {
        int whole = count / divisor;
        int tenth = (count % divisor) * 10 / divisor;
        if (whole >= 100) {
            return whole + suffix;
        }
        if (whole >= 10 && tenth == 0) {
            return whole + suffix;
        }
        return whole + "." + tenth + suffix;
    }

    @Override
    protected List<Component> getTooltipFromContainerItem(ItemStack itemStack) {
        List<Component> tooltip = new ArrayList<>(super.getTooltipFromContainerItem(itemStack));
        if (this.hoveredSlot != null
                && this.hoveredSlot.index < this.menu.getBackpackSlotCount()
                && itemStack.getCount() >= 1000) {
            tooltip.add(Component.literal("Count: " + STACK_COUNT_FORMAT.format(itemStack.getCount())));
        }
        return tooltip;
    }
}

