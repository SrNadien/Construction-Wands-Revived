package nadiendev.constructionwand.client;

import com.mojang.blaze3d.vertex.PoseStack;
import nadiendev.constructionwand.basics.WandUtil;
import nadiendev.constructionwand.network.PacketRequestPreview;
import net.minecraft.client.renderer.SubmitNodeCollector;
import net.minecraft.client.renderer.rendertype.RenderTypes;
import net.minecraft.client.renderer.state.level.BlockOutlineRenderState;
import net.minecraft.client.renderer.state.level.LevelRenderState;
import net.minecraft.core.BlockPos;
import net.minecraft.util.ARGB;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.Vec3;
import net.minecraft.world.phys.shapes.Shapes;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.neoforge.client.CustomBlockOutlineRenderer;
import net.neoforged.neoforge.client.event.ExtractBlockOutlineRenderStateEvent;
import net.neoforged.neoforge.client.network.ClientPacketDistributor;

import java.util.HashSet;
import java.util.List;
import java.util.Set;


public class RenderBlockPreview {
    private static final int COLOR_PREVIEW = ARGB.colorFromFloat(0.4f, 0f, 0f, 0f);
    private static final int COLOR_UNDO = ARGB.colorFromFloat(0.4f, 0f, 1f, 0f);
    private static final float LINE_WIDTH = 2f;

    private static BlockHitResult lastRayTraceResult = null;
    private static ItemStack lastWand = ItemStack.EMPTY;
    public static HashSet<BlockPos> undoBlocks = new HashSet<>();
    public static Set<BlockPos> previewBlocks;

    /**
     * 26.2: el render de outlines pasa por el sistema de "submit nodes". Toda la logica
     * (leer el jugador, la varita y pedir el preview al server) se resuelve aqui, en la
     * fase de extraccion, y el renderer solo recibe datos ya copiados: el javadoc de
     * {@link CustomBlockOutlineRenderer} prohibe capturar el nivel/estado mutable.
     */
    @SubscribeEvent
    public void onExtractBlockOutlineRenderState(ExtractBlockOutlineRenderStateEvent event) {
        Entity entity = event.getCamera().entity();
        if (!(entity instanceof Player player)) return;

        ItemStack wand = WandUtil.holdingWand(player);
        if (wand == null) return;

        BlockHitResult target = event.getHitResult();
        Set<BlockPos> blocks;
        int color;

        if (KeybindHandler.isOptKeyDown()) {
            blocks = undoBlocks;
            color = COLOR_UNDO;
        } else {
            // Modo preview normal: solicitar al server si cambio el objetivo o la varita
            if (lastRayTraceResult == null
                    || !compareRTR(lastRayTraceResult, target)
                    || !ItemStack.matches(lastWand, wand)
                    || previewBlocks == null
                    || previewBlocks.size() < 2) {
                lastRayTraceResult = target;
                lastWand = wand.copy();
                ClientPacketDistributor.sendToServer(new PacketRequestPreview(target, wand));
            }
            blocks = previewBlocks;
            color = COLOR_PREVIEW;
        }

        if (blocks == null || blocks.isEmpty()) return;

        // Copia defensiva: el set lo actualiza el hilo de red al llegar PacketPreviewResult.
        event.addCustomRenderer(new WandPreviewRenderer(List.copyOf(blocks), color));
    }

    private record WandPreviewRenderer(List<BlockPos> blocks, int color) implements CustomBlockOutlineRenderer {
        @Override
        public boolean render(BlockOutlineRenderState renderState, SubmitNodeCollector submitNodeCollector,
                              PoseStack poseStack, LevelRenderState levelRenderState) {
            // El poseStack llega en origen de camara (vanilla traslada despues de llamarnos).
            Vec3 cameraPos = levelRenderState.cameraRenderState.pos;

            for (BlockPos block : blocks) {
                AABB aabb = new AABB(block).move(-cameraPos.x, -cameraPos.y, -cameraPos.z);
                submitNodeCollector.submitShapeOutline(
                        poseStack,
                        Shapes.create(aabb),
                        RenderTypes.lines(),
                        color,
                        LINE_WIDTH,
                        renderState.isTranslucent());
            }

            return true;
        }
    }

    public static void reset() {
        lastRayTraceResult = null;
        lastWand = ItemStack.EMPTY;
        previewBlocks = null;
    }

    private static boolean compareRTR(BlockHitResult rtr1, BlockHitResult rtr2) {
        return rtr1.getBlockPos().equals(rtr2.getBlockPos())
                && rtr1.getDirection().equals(rtr2.getDirection());
    }
}
