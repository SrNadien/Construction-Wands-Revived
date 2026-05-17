package nadiendev.constructionwand.client;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.HitResult;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.neoforge.client.event.RenderHighlightEvent;
import nadiendev.constructionwand.basics.WandUtil;
import nadiendev.constructionwand.items.wand.ItemWand;
import nadiendev.constructionwand.wand.WandJob;

import java.util.Set;

public class RenderBlockPreview
{
    private WandJob wandJob;
    public Set<BlockPos> undoBlocks;

    @SubscribeEvent
    public void renderBlockHighlight(RenderHighlightEvent.Block event) {
        if(event.getTarget().getType() != HitResult.Type.BLOCK) return;

        BlockHitResult rtr = event.getTarget();
        Entity entity = event.getCamera().getEntity();
        if(!(entity instanceof Player player)) return;
        Set<BlockPos> blocks;
        float colorR = 0, colorG = 0, colorB = 0;

        ItemStack wand = WandUtil.holdingWand(player);
        if(wand == null) return;

        if(!(player.isCrouching() && ClientEvents.isOptKeyDown())) {
            if(wandJob == null || !compareRTR(wandJob.rayTraceResult, rtr) || !(wandJob.wand.equals(wand))
                || wandJob.blockCount() < 2) {
                wandJob = ItemWand.getWandJob(player, player.level(), rtr, wand);
            }
            blocks = wandJob.getBlockPositions();
        }
        else {
            blocks = undoBlocks;
            colorG = 1;
        }

        if(blocks == null || blocks.isEmpty()) return;

        PoseStack ms = event.getPoseStack();
        MultiBufferSource buffer = event.getMultiBufferSource();
        VertexConsumer lineBuilder = buffer.getBuffer(RenderType.LINES);

        double partialTicks = event.getDeltaTracker().getGameTimeDeltaPartialTick(false);
        double d0 = player.xOld + (player.getX() - player.xOld) * partialTicks;
        double d1 = player.yOld + (player.getY() - player.yOld) * partialTicks + player.getEyeHeight();
        double d2 = player.zOld + (player.getZ() - player.zOld) * partialTicks;

        PoseStack.Pose pose = ms.last();
        float alpha = 0.4F;

        for(BlockPos block : blocks) {
            float minX = (float)(block.getX() - d0);
            float minY = (float)(block.getY() - d1);
            float minZ = (float)(block.getZ() - d2);
            float maxX = (float)(block.getX() + 1 - d0);
            float maxY = (float)(block.getY() + 1 - d1);
            float maxZ = (float)(block.getZ() + 1 - d2);
            drawBox(lineBuilder, pose, minX, minY, minZ, maxX, maxY, maxZ, colorR, colorG, colorB, alpha);
        }

        event.setCanceled(true);
    }

    private void drawBox(VertexConsumer consumer, PoseStack.Pose pose,
                         float minX, float minY, float minZ, float maxX, float maxY, float maxZ,
                         float red, float green, float blue, float alpha) {
        drawLine(consumer, pose, minX, minY, minZ, maxX, minY, minZ, red, green, blue, alpha);
        drawLine(consumer, pose, maxX, minY, minZ, maxX, minY, maxZ, red, green, blue, alpha);
        drawLine(consumer, pose, maxX, minY, maxZ, minX, minY, maxZ, red, green, blue, alpha);
        drawLine(consumer, pose, minX, minY, maxZ, minX, minY, minZ, red, green, blue, alpha);

        drawLine(consumer, pose, minX, maxY, minZ, maxX, maxY, minZ, red, green, blue, alpha);
        drawLine(consumer, pose, maxX, maxY, minZ, maxX, maxY, maxZ, red, green, blue, alpha);
        drawLine(consumer, pose, maxX, maxY, maxZ, minX, maxY, maxZ, red, green, blue, alpha);
        drawLine(consumer, pose, minX, maxY, maxZ, minX, maxY, minZ, red, green, blue, alpha);

        drawLine(consumer, pose, minX, minY, minZ, minX, maxY, minZ, red, green, blue, alpha);
        drawLine(consumer, pose, maxX, minY, minZ, maxX, maxY, minZ, red, green, blue, alpha);
        drawLine(consumer, pose, maxX, minY, maxZ, maxX, maxY, maxZ, red, green, blue, alpha);
        drawLine(consumer, pose, minX, minY, maxZ, minX, maxY, maxZ, red, green, blue, alpha);
    }

    private void drawLine(VertexConsumer consumer, PoseStack.Pose pose,
                          float x1, float y1, float z1, float x2, float y2, float z2,
                          float red, float green, float blue, float alpha) {
        float dx = x2 - x1;
        float dy = y2 - y1;
        float dz = z2 - z1;
        float len = (float)Math.sqrt(dx * dx + dy * dy + dz * dz);
        float nx = len > 0 ? dx / len : 0;
        float ny = len > 0 ? dy / len : 0;
        float nz = len > 0 ? dz / len : 0;

        consumer.addVertex(pose, x1, y1, z1).setColor(red, green, blue, alpha).setNormal(pose, nx, ny, nz);
        consumer.addVertex(pose, x2, y2, z2).setColor(red, green, blue, alpha).setNormal(pose, nx, ny, nz);
    }

    public void reset() {
        wandJob = null;
    }

    private static boolean compareRTR(BlockHitResult rtr1, BlockHitResult rtr2) {
        return rtr1.getBlockPos().equals(rtr2.getBlockPos()) && rtr1.getDirection().equals(rtr2.getDirection());
    }
}
