package nadiendev.constructionwand.network;

import net.minecraft.core.BlockPos;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.chat.Component;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.Identifier;
import net.minecraft.server.level.ServerPlayer;
import net.neoforged.neoforge.network.handling.IPayloadContext;
import nadiendev.constructionwand.ConstructionWand;
import nadiendev.constructionwand.basics.WandUtil;
import net.minecraft.world.item.ItemStack;

import java.util.Set;

import static nadiendev.constructionwand.ConstructionWand.MODID;


public record PacketWandUndo() implements CustomPacketPayload
{
    public static final StreamCodec<FriendlyByteBuf, PacketWandUndo> CODEC = CustomPacketPayload.codec(
            PacketWandUndo::encode,
            PacketWandUndo::new);

    public static final Type<PacketWandUndo> ID = new Type<>(
            Identifier.fromNamespaceAndPath(MODID, "wand_undo"));

    /** Constructor de decodificación — no hay datos que leer. */
    public PacketWandUndo(FriendlyByteBuf buffer) {
        this();
    }

    public void encode(FriendlyByteBuf buffer) {
        // Sin datos
    }

    @Override
    public Type<? extends CustomPacketPayload> type() {
        return ID;
    }

    // ── Handler ───────────────────────────────────────────────────────────────

    public static class Handler
    {
        public static void handle(final PacketWandUndo msg, final IPayloadContext ctx) {
            ctx.enqueueWork(() -> {
                if (!(ctx.player() instanceof ServerPlayer player)) return;

                // Solo si tiene varita en mano
                ItemStack wand = WandUtil.holdingWand(player);
                if (wand == null) return;

                
                boolean success = ConstructionWand.undoHistory.undoLast(player, player.level());

                if (success) {
                    player.displayClientMessage(
                            Component.translatable("constructionwand.undo.success")
                                    .withStyle(net.minecraft.ChatFormatting.GREEN),
                            true);
                } else {
                    player.displayClientMessage(
                            Component.translatable("constructionwand.undo.nothing")
                                    .withStyle(net.minecraft.ChatFormatting.GRAY),
                            true);
                }
            })
            .exceptionally(e -> {
                ctx.disconnect(Component.translatable(
                        "constructionwand.networking.wand_undo.failed", e.getMessage()));
                return null;
            });
        }
    }
}