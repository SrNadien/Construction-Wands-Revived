package nadiendev.constructionwand.client.screen;

import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.FormattedCharSequence;
import net.minecraft.world.entity.player.Inventory;
import nadiendev.constructionwand.ConstructionWand;
import nadiendev.constructionwand.items.containeritems.MenuVoidSack;
import nadiendev.constructionwand.network.PacketToggleVoidSack;

import java.util.List;

public class ScreenVoidSack extends AbstractContainerScreen<MenuVoidSack>
{
    private static final ResourceLocation TEXTURE =
            ResourceLocation.fromNamespaceAndPath(ConstructionWand.MODID, "textures/gui/sprites/basket_4x4.png");

    private static final int GUI_WIDTH  = 176;
    private static final int GUI_HEIGHT = 192;

    private static final int TOGGLE_BTN_X = 152;
    private static final int TOGGLE_BTN_Y = 5;
    private static final int TOGGLE_BTN_W = 20;
    private static final int TOGGLE_BTN_H = 10;

    private boolean sendToContainer;
    private boolean hasLinked;

    public ScreenVoidSack(MenuVoidSack menu, Inventory playerInv, Component title) {
        super(menu, playerInv, title);
        this.imageWidth      = GUI_WIDTH;
        this.imageHeight     = GUI_HEIGHT;
        this.sendToContainer = menu.isSendToContainer();
        this.hasLinked       = menu.hasLinkedContainer();
    }

    @Override
    public void render(GuiGraphics gfx, int mouseX, int mouseY, float partialTick) {
        renderBackground(gfx, mouseX, mouseY, partialTick);
        super.render(gfx, mouseX, mouseY, partialTick);
        renderTooltip(gfx, mouseX, mouseY);

        if (hasLinked && isOverButton(mouseX, mouseY)) {
            Component tooltipText = Component.translatable("gui.constructionwand.void_sack.toggle_tooltip");
            List<FormattedCharSequence> lines = font.split(tooltipText, 150);
            gfx.renderTooltip(font, lines, mouseX, mouseY);
        }
    }

    @Override
    protected void renderBg(GuiGraphics gfx, float partialTick, int mouseX, int mouseY) {
        gfx.blit(TEXTURE, leftPos, topPos, 0, 0, GUI_WIDTH, GUI_HEIGHT);
    }

    @Override
    protected void renderLabels(GuiGraphics gfx, int mouseX, int mouseY) {
        gfx.drawString(font, title, titleLabelX, titleLabelY, 0x404040, false);
        gfx.drawString(font, playerInventoryTitle, 8, 100, 0x404040, false);

        if (hasLinked) {
            Component statusLabel = sendToContainer
                    ? Component.translatable("gui.constructionwand.void_sack.sending")
                    : Component.translatable("gui.constructionwand.void_sack.storing");
            gfx.drawString(font, statusLabel, 8, GUI_HEIGHT - 6, 0x404040, false);
        }
    }

    @Override
    public boolean mouseClicked(double mouseX, double mouseY, int button) {
        if (button == 0 && hasLinked && isOverButton((int) mouseX, (int) mouseY)) {
            PacketToggleVoidSack.send(menu.getHand());
            sendToContainer = !sendToContainer;
            return true;
        }
        return super.mouseClicked(mouseX, mouseY, button);
    }

    private boolean isOverButton(int mouseX, int mouseY) {
        int bx = leftPos + TOGGLE_BTN_X;
        int by = topPos  + TOGGLE_BTN_Y;
        return mouseX >= bx && mouseX < bx + TOGGLE_BTN_W
                && mouseY >= by && mouseY < by + TOGGLE_BTN_H;
    }

    @Override
    public boolean keyPressed(int keyCode, int scanCode, int modifiers) {
        return super.keyPressed(keyCode, scanCode, modifiers);
    }
}