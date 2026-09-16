package nadiendev.constructionwand.network;

import net.minecraft.core.BlockPos;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.chat.Component;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.Identifier;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.HitResult;
import net.neoforged.neoforge.network.handling.IPayloadContext;
import nadiendev.constructionwand.basics.WandUtil;
import nadiendev.constructionwand.wand.action.ActionExchange;

import static nadiendev.constructionwand.ConstructionWand.MODID;

public record PacketExchangeSelect() implements CustomPacketPayload
{
    public static final StreamCodec<FriendlyByteBuf, PacketExchangeSelect> CODEC = CustomPacketPayload.codec(
            PacketExchangeSelect::encode,
            PacketExchangeSelect::new);

    public static final Type<PacketExchangeSelect> ID = new Type<>(
            Identifier.fromNamespaceAndPath(MODID, "exchange_select"));

    /** Constructor de decodificación — no hay datos que leer. */
    public PacketExchangeSelect(FriendlyByteBuf buffer) {
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
        public static void handle(final PacketExchangeSelect msg, final IPayloadContext ctx) {
            ctx.enqueueWork(() -> {
                if(!(ctx.player() instanceof ServerPlayer player)) return;

                // Solo si tiene la vara en mano
                ItemStack wand = WandUtil.holdingWand(player);
                if(wand == null) return;

                // Raytrace del servidor hacia donde está mirando el jugador
                double reach = player.blockInteractionRange();
                HitResult hit = player.pick(reach, 1.0F, false);

                if(!(hit instanceof BlockHitResult blockHit) || hit.getType() != HitResult.Type.BLOCK) {
                    player.displayClientMessage(
                            Component.translatable(MODID + ".message.exchange_no_target")
                                    .withStyle(net.minecraft.ChatFormatting.RED), true);
                    return;
                }

                BlockPos pos = blockHit.getBlockPos();
                ActionExchange.selectReplacementBlock(player.level(), player, pos);
            })
            .exceptionally(e -> {
                ctx.disconnect(Component.translatable(
                        MODID + ".networking.exchange_select.failed", e.getMessage()));
                return null;
            });
        }
    }
}