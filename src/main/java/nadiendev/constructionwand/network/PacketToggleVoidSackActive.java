package nadiendev.constructionwand.network;

import net.minecraft.ChatFormatting;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.network.NetworkEvent;
import nadiendev.constructionwand.items.containeritems.ItemVoidSack;

import java.util.function.Supplier;

public class PacketToggleVoidSackActive
{
    public final InteractionHand hand;

    public PacketToggleVoidSackActive(InteractionHand hand) {
        this.hand = hand;
    }

    public static void encode(PacketToggleVoidSackActive msg, FriendlyByteBuf buffer) {
        buffer.writeBoolean(msg.hand == InteractionHand.MAIN_HAND);
    }

    public static PacketToggleVoidSackActive decode(FriendlyByteBuf buffer) {
        return new PacketToggleVoidSackActive(buffer.readBoolean() ? InteractionHand.MAIN_HAND : InteractionHand.OFF_HAND);
    }

    public static void send(InteractionHand hand) {
        ModMessages.sendToServer(new PacketToggleVoidSackActive(hand));
    }

    public static class Handler
    {
        public static void handle(final PacketToggleVoidSackActive msg, final Supplier<NetworkEvent.Context> ctx) {
            ctx.get().enqueueWork(() -> {
                ServerPlayer sp = ctx.get().getSender();
                if(sp == null) return;

                ItemStack sack = sp.getItemInHand(msg.hand);
                if(!(sack.getItem() instanceof ItemVoidSack)) {
                    InteractionHand other = msg.hand == InteractionHand.MAIN_HAND
                            ? InteractionHand.OFF_HAND : InteractionHand.MAIN_HAND;
                    sack = sp.getItemInHand(other);
                }
                if(!(sack.getItem() instanceof ItemVoidSack)) return;

                boolean nowActive = !ItemVoidSack.isActive(sack);
                ItemVoidSack.setActive(sack, nowActive);

                if(ItemVoidSack.getLinkedPos(sack) != null) {
                    ItemVoidSack.setSendToContainer(sack, nowActive);
                }

                sp.displayClientMessage(
                        Component.translatable(
                                nowActive
                                    ? "item.constructionwand.void_sack.activated"
                                    : "item.constructionwand.void_sack.deactivated")
                                .withStyle(nowActive ? ChatFormatting.GREEN : ChatFormatting.YELLOW),
                        true);
            });
            ctx.get().setPacketHandled(true);
        }
    }
}
