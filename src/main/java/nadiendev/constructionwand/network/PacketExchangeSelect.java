package nadiendev.constructionwand.network;

import net.minecraft.ChatFormatting;
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

/**
 * Pide al servidor que seleccione el bloque al que apunta el jugador como
 * bloque de reemplazo del Exchange core. Sin datos: el servidor hace su propio
 * raytrace para no confiar en el cliente.
 */
public record PacketExchangeSelect() implements CustomPacketPayload
{
    public static final PacketExchangeSelect INSTANCE = new PacketExchangeSelect();

    public static final Type<PacketExchangeSelect> TYPE =
            new Type<>(Identifier.fromNamespaceAndPath(MODID, "exchange_select"));

    public static final StreamCodec<FriendlyByteBuf, PacketExchangeSelect> CODEC =
            StreamCodec.unit(INSTANCE);

    @Override
    public Type<? extends CustomPacketPayload> type() {
        return TYPE;
    }

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
                player.sendOverlayMessage(
                        Component.translatable(MODID + ".message.exchange_no_target")
                                .withStyle(ChatFormatting.RED));
                return;
            }

            BlockPos pos = blockHit.getBlockPos();
            ActionExchange.selectReplacementBlock(player.level(), player, pos);
        });
    }
}
