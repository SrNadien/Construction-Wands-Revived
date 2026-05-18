package nadiendev.constructionwand.client;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import nadiendev.constructionwand.basics.WandUtil;
import nadiendev.constructionwand.network.PacketRequestPreview;
import net.minecraft.client.Camera;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.ShapeRenderer;
import net.minecraft.client.renderer.rendertype.RenderTypes;
import net.minecraft.client.renderer.state.BlockOutlineRenderState;
import net.minecraft.client.renderer.state.LevelRenderState;
import net.minecraft.core.BlockPos;
import net.minecraft.util.ARGB;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.Shapes;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.client.CustomBlockOutlineRenderer;
import net.neoforged.neoforge.client.event.ExtractBlockOutlineRenderStateEvent;
import net.neoforged.neoforge.client.network.ClientPacketDistributor;

import java.util.HashSet;
import java.util.Set;

@EventBusSubscriber(modid = "constructionwand", value = Dist.CLIENT)
public class RenderBlockPreview {
    private static BlockHitResult lastRayTraceResult = null;
    private static ItemStack lastWand = ItemStack.EMPTY;
    public static HashSet<BlockPos> undoBlocks = new HashSet<>();
    public static Set<BlockPos> previewBlocks;

    @SubscribeEvent
    public static void onExtractBlockOutlineRenderState(ExtractBlockOutlineRenderStateEvent event) {
        event.addCustomRenderer(new WandPreviewRenderer(event.getHitResult(), event.getCamera()));
    }

    private static class WandPreviewRenderer implements CustomBlockOutlineRenderer {
        private final BlockHitResult target;
        private final Camera camera;

        public WandPreviewRenderer(BlockHitResult hitResult, Camera camera) {
            this.target = hitResult;
            this.camera = camera;
        }

        @Override
        public boolean render(BlockOutlineRenderState renderState, MultiBufferSource.BufferSource buffer,
                              PoseStack poseStack, boolean translucentPass, LevelRenderState levelRenderState) {
            Entity entity = camera.entity();
            if (!(entity instanceof Player player)) return false;

            ItemStack wand = WandUtil.holdingWand(player);
            if (wand == null) return false;

            Set<BlockPos> blocks;
            float colorR = 0, colorG = 0, colorB = 0;

            if (KeybindHandler.isOptKeyDown()) {
                blocks = undoBlocks;
                colorG = 1;
            } else {
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
            }

            if (blocks == null || blocks.isEmpty()) return false;


            VertexConsumer lineBuilder = buffer.getBuffer(RenderTypes.lines());

            double d0 = camera.position().x();
            double d1 = camera.position().y();
            double d2 = camera.position().z();

            for (BlockPos block : blocks) {
                AABB aabb = new AABB(block).move(-d0, -d1, -d2);
                ShapeRenderer.renderShape(poseStack, lineBuilder, Shapes.create(aabb), 0, 0, 0,
                        ARGB.colorFromFloat(0.4f, colorR, colorG, colorB), 2f);
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