package nadiendev.constructionwand.network;

import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.Identifier;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.item.ItemStack;
import net.neoforged.neoforge.network.handling.IPayloadContext;
import nadiendev.constructionwand.ConstructionWand;
import nadiendev.constructionwand.items.containeritems.ItemVoidSack;

/**
 * Paquete C→S: el cliente presionó M → toggle del estado activo del Void Sack.
 * También puede usarse para toggle de SendToContainer desde la tecla M
 * (dependiendo del modo).
 *
 * By NadienDev
 */
public record PacketToggleVoidSackActive(InteractionHand hand) implements CustomPacketPayload
{
    public static final CustomPacketPayload.Type<PacketToggleVoidSackActive> TYPE =
            new CustomPacketPayload.Type<>(
                    Identifier.fromNamespaceAndPath(ConstructionWand.MODID, "toggle_void_sack_active"));

    public static final StreamCodec<FriendlyByteBuf, PacketToggleVoidSackActive> CODEC =
            StreamCodec.composite(
                    net.minecraft.network.codec.ByteBufCodecs.idMapper(
                            id -> id == 0 ? InteractionHand.MAIN_HAND : InteractionHand.OFF_HAND,
                            h  -> h  == InteractionHand.MAIN_HAND ? 0 : 1),
                    PacketToggleVoidSackActive::hand,
                    PacketToggleVoidSackActive::new);

    @Override
    public CustomPacketPayload.Type<? extends CustomPacketPayload> type() {
        return TYPE;
    }

    // ─────────────────────────────────────────────────────────────────────────
    //  Handler (hilo del servidor)
    //  Busca el sack en cualquiera de las dos manos y hace toggle de TAG_ACTIVE.
    //  Si el sack tiene container linkeado, también hace toggle de SendToContainer.
    // ─────────────────────────────────────────────────────────────────────────
    public static void handle(PacketToggleVoidSackActive packet, IPayloadContext ctx) {
        ctx.enqueueWork(() -> {
            if (!(ctx.player() instanceof ServerPlayer sp)) return;

            // Busca el sack en la mano indicada; si no, en la otra mano
            ItemStack sack = sp.getItemInHand(packet.hand());
            if (!(sack.getItem() instanceof ItemVoidSack)) {
                InteractionHand other = packet.hand() == InteractionHand.MAIN_HAND
                        ? InteractionHand.OFF_HAND : InteractionHand.MAIN_HAND;
                sack = sp.getItemInHand(other);
            }
            if (!(sack.getItem() instanceof ItemVoidSack)) return;

            boolean nowActive = !ItemVoidSack.isActive(sack);
            ItemVoidSack.setActive(sack, nowActive);

            // Si tiene container linkeado, sincroniza también SendToContainer
            if (ItemVoidSack.getLinkedPos(sack) != null) {
                ItemVoidSack.setSendToContainer(sack, nowActive);
            }

            // Feedback al jugador
            sp.sendSystemMessage(
                    net.minecraft.network.chat.Component.translatable(
                            nowActive
                                ? "item.constructionwand.void_sack.activated"
                                : "item.constructionwand.void_sack.deactivated")
                            .withStyle(nowActive
                                ? net.minecraft.ChatFormatting.GREEN
                                : net.minecraft.ChatFormatting.YELLOW),
                    true); // true = actionbar (no llena el chat)
        });
    }

    // ─────────────────────────────────────────────────────────────────────────
    //  Envío desde el cliente (KeybindHandler)
    // ─────────────────────────────────────────────────────────────────────────
    public static void send(InteractionHand hand) {
        net.neoforged.neoforge.client.network.ClientPacketDistributor.sendToServer(
                new PacketToggleVoidSackActive(hand));
    }
}