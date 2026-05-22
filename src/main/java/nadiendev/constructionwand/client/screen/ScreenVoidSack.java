package nadiendev.constructionwand.client.screen;

import net.minecraft.client.gui.GuiGraphicsExtractor;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.client.renderer.RenderPipelines;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.Identifier;
import net.minecraft.world.entity.player.Inventory;
import nadiendev.constructionwand.ConstructionWand;
import nadiendev.constructionwand.items.containeritems.MenuVoidSack;
import nadiendev.constructionwand.network.PacketToggleVoidSack;

public class ScreenVoidSack extends AbstractContainerScreen<MenuVoidSack>
{
    private static final Identifier TEXTURE =
            Identifier.fromNamespaceAndPath(ConstructionWand.MODID, "textures/gui/sprites/basket_4x4.png");

    private static final int GUI_WIDTH  = 176;
    private static final int GUI_HEIGHT = 192;

    private static final int TOGGLE_BTN_X = 152;
    private static final int TOGGLE_BTN_Y = 5;
    private static final int TOGGLE_BTN_W = 20;
    private static final int TOGGLE_BTN_H = 10;

    private boolean sendToContainer;
    private boolean hasLinked;

    public ScreenVoidSack(MenuVoidSack menu, Inventory playerInv, Component title) {
        super(menu, playerInv, title, GUI_WIDTH, GUI_HEIGHT);
        this.sendToContainer = menu.isSendToContainer();
        this.hasLinked       = menu.hasLinkedContainer();
    }

    // extractBackground es public en Screen (26.1), el override debe mantener public
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

    // extractLabels mantiene el acceso del padre (protected en AbstractContainerScreen)
    @Override
    protected void extractLabels(GuiGraphicsExtractor gfx, int mouseX, int mouseY) {
        super.extractLabels(gfx, mouseX, mouseY);
        // text() reemplaza a drawString() en 26.1; color en ARGB
        gfx.text(font, title, titleLabelX, titleLabelY, 0xFF404040, false);
        gfx.text(font, playerInventoryTitle, 8, 100, 0xFF404040, false);

        if (hasLinked) {
            Component statusLabel = sendToContainer
                    ? Component.translatable("gui.constructionwand.void_sack.sending")
                    : Component.translatable("gui.constructionwand.void_sack.storing");
            gfx.text(font, statusLabel, 8, GUI_HEIGHT - 6, 0xFF404040, false);
        }
    }

    // extractRenderState reemplaza render() para contenido dinámico en 26.1
    @Override
    public void extractRenderState(GuiGraphicsExtractor gfx, int mouseX, int mouseY, float partialTick) {
        super.extractRenderState(gfx, mouseX, mouseY, partialTick);

        if (hasLinked && isOverButton(mouseX, mouseY)) {
            // En 26.1, setTooltipForNextFrame acepta Component (no List<MutableComponent>)
            // Usar la sobrecarga que acepta Component directamente
            gfx.setTooltipForNextFrame(
                    font,
                    Component.translatable("gui.constructionwand.void_sack.toggle_tooltip"),
                    mouseX, mouseY
            );
        }
    }

    // En 26.1, AbstractContainerScreen sobrescribe mouseClicked con firma distinta (MouseButtonEvent).
    // Para no colisionar, interceptamos el click desde la interfaz Screen base usando
    // el método que NO está sobrescrito en AbstractContainerScreen: mousePressed no existe,
    // pero Screen hereda mouseClicked(double,double,int) de GuiEventListener.
    // La solución correcta es NO hacer @Override de mouseClicked y registrar un widget
    // invisible, o usar el hook de slotClicked. Sin embargo, la forma más simple y compatible
    // en 26.1 es definir nuestro propio método sin @Override para capturar antes que el super.
    //
    // Alternativa limpia: escuchar ScreenEvent.MouseButtonPressed en el mod event bus,
    // pero para mantener el código autocontenido usamos init() + addRenderableWidget con un
    // Button invisible posicionado sobre el área del toggle.
    @Override
    protected void init() {
        super.init();
        // Registramos un widget invisible sobre el botón para capturar el click
        // sin colisionar con la firma cambiada de mouseClicked en AbstractContainerScreen.
        net.minecraft.client.gui.components.Button toggleBtn = net.minecraft.client.gui.components.Button
                .builder(Component.empty(), btn -> {
                    if (hasLinked) {
                        PacketToggleVoidSack.send(menu.getHand());
                        sendToContainer = !sendToContainer;
                    }
                })
                .bounds(
                    leftPos + TOGGLE_BTN_X,
                    topPos  + TOGGLE_BTN_Y,
                    TOGGLE_BTN_W,
                    TOGGLE_BTN_H
                )
                .build();
        // Solo añadimos el widget si hay contenedor vinculado
        // (si hasLinked es false, el botón existe pero no hace nada al presionar)
        addRenderableWidget(toggleBtn);
    }

    private boolean isOverButton(int mouseX, int mouseY) {
        int bx = leftPos + TOGGLE_BTN_X;
        int by = topPos  + TOGGLE_BTN_Y;
        return mouseX >= bx && mouseX < bx + TOGGLE_BTN_W
                && mouseY >= by && mouseY < by + TOGGLE_BTN_H;
    }
}