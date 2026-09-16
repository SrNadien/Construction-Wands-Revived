package nadiendev.constructionwand.client.screen;

import net.minecraft.client.gui.GuiGraphicsExtractor;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.client.renderer.RenderPipelines;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.Identifier;
import net.minecraft.world.entity.player.Inventory;
import nadiendev.constructionwand.ConstructionWand;
import nadiendev.constructionwand.items.containeritems.MenuVoidSack;

public class ScreenVoidSack extends AbstractContainerScreen<MenuVoidSack>
{
    private static final Identifier TEXTURE =
            Identifier.fromNamespaceAndPath(ConstructionWand.MODID, "textures/gui/sprites/basket_4x4.png");

    private static final int GUI_WIDTH  = 176;
    private static final int GUI_HEIGHT = 192;

    public ScreenVoidSack(MenuVoidSack menu, Inventory playerInv, Component title) {
        super(menu, playerInv, title, GUI_WIDTH, GUI_HEIGHT);
    }

    @Override
    public void extractBackground(GuiGraphicsExtractor gfx, int mouseX, int mouseY, float partialTick) {
        super.extractBackground(gfx, mouseX, mouseY, partialTick);

        gfx.blit(
                RenderPipelines.GUI_TEXTURED,
                TEXTURE,
                leftPos, topPos,
                0, 0,
                GUI_WIDTH, GUI_HEIGHT,
                256, 256
        );
    }

    @Override
    protected void extractLabels(GuiGraphicsExtractor gfx, int mouseX, int mouseY) {
        gfx.text(font, title,
                titleLabelX,
                titleLabelY,
                0xFF404040, false);

        gfx.text(font, playerInventoryTitle,
                8,
                100,
                0xFF404040, false);
    }
}