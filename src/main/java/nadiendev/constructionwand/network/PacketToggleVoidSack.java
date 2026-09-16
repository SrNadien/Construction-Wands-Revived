package nadiendev.constructionwand.network;

import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.Identifier;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.InteractionHand;
import net.neoforged.neoforge.network.handling.IPayloadContext;
import nadiendev.constructionwand.ConstructionWand;
import nadiendev.constructionwand.items.containeritems.MenuVoidSack;

/**
 * Paquete (Void Sack).
 *
 * By NadienDev
 * 
 */
public record PacketToggleVoidSack(InteractionHand hand) implements CustomPacketPayload
{
    public static final CustomPacketPayload.Type<PacketToggleVoidSack> TYPE =
            new CustomPacketPayload.Type<>(
                    Identifier.fromNamespaceAndPath(ConstructionWand.MODID, "toggle_void_sack"));

    public static final StreamCodec<FriendlyByteBuf, PacketToggleVoidSack> CODEC =
            StreamCodec.composite(
                    net.minecraft.network.codec.ByteBufCodecs.idMapper(
                            id -> id == 0 ? InteractionHand.MAIN_HAND : InteractionHand.OFF_HAND,
                            hand -> hand == InteractionHand.MAIN_HAND ? 0 : 1),
                    PacketToggleVoidSack::hand,
                    PacketToggleVoidSack::new);

    @Override
    public CustomPacketPayload.Type<? extends CustomPacketPayload> type() {
        return TYPE;
    }

    // ─────────────────────────────────────────────────────────────────────────
    //  Handler (hilo del servidor)
    // ─────────────────────────────────────────────────────────────────────────
    public static void handle(PacketToggleVoidSack packet, IPayloadContext ctx) {
        ctx.enqueueWork(() -> {
            if (ctx.player() instanceof ServerPlayer serverPlayer) {
                if (serverPlayer.containerMenu instanceof MenuVoidSack menu) {
                    menu.toggleSendToContainer();
                }
            }
        });
    }

    // ─────────────────────────────────────────────────────────────────────────
    //  Envío desde el cliente
    // ─────────────────────────────────────────────────────────────────────────
    public static void send(InteractionHand hand) {
        net.neoforged.neoforge.client.network.ClientPacketDistributor.sendToServer(
                new PacketToggleVoidSack(hand));
    }
}