package nadiendev.constructionwand.network;

import net.minecraft.ChatFormatting;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.chat.Component;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.item.ItemStack;
import net.neoforged.neoforge.network.PacketDistributor;
import net.neoforged.neoforge.network.handling.IPayloadContext;
import nadiendev.constructionwand.ConstructionWand;
import nadiendev.constructionwand.items.containeritems.ItemVoidSack;

public record PacketToggleVoidSackActive(InteractionHand hand) implements CustomPacketPayload
{
    public static final CustomPacketPayload.Type<PacketToggleVoidSackActive> TYPE =
            new CustomPacketPayload.Type<>(
                    ResourceLocation.fromNamespaceAndPath(ConstructionWand.MODID, "toggle_void_sack_active"));

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

    public static void handle(PacketToggleVoidSackActive packet, IPayloadContext ctx) {
        ctx.enqueueWork(() -> {
            if (!(ctx.player() instanceof ServerPlayer sp)) return;

            ItemStack sack = sp.getItemInHand(packet.hand());
            if (!(sack.getItem() instanceof ItemVoidSack)) {
                InteractionHand other = packet.hand() == InteractionHand.MAIN_HAND
                        ? InteractionHand.OFF_HAND : InteractionHand.MAIN_HAND;
                sack = sp.getItemInHand(other);
            }
            if (!(sack.getItem() instanceof ItemVoidSack)) return;

            boolean nowActive = !ItemVoidSack.isActive(sack);
            ItemVoidSack.setActive(sack, nowActive);

            if (ItemVoidSack.getLinkedPos(sack) != null) {
                ItemVoidSack.setSendToContainer(sack, nowActive);
            }

            sp.sendSystemMessage(
                    Component.translatable(
                            nowActive
                                ? "item.constructionwand.void_sack.activated"
                                : "item.constructionwand.void_sack.deactivated")
                            .withStyle(nowActive ? ChatFormatting.GREEN : ChatFormatting.YELLOW),
                    true);
        });
    }

    public static void send(InteractionHand hand) {
        PacketDistributor.sendToServer(new PacketToggleVoidSackActive(hand));
    }
}