package nadiendev.constructionwand.network;

import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.InteractionHand;
import net.minecraftforge.network.NetworkEvent;
import nadiendev.constructionwand.items.containeritems.MenuVoidSack;

import java.util.function.Supplier;

public class PacketToggleVoidSack
{
    public final InteractionHand hand;

    public PacketToggleVoidSack(InteractionHand hand) {
        this.hand = hand;
    }

    public static void encode(PacketToggleVoidSack msg, FriendlyByteBuf buffer) {
        buffer.writeBoolean(msg.hand == InteractionHand.MAIN_HAND);
    }

    public static PacketToggleVoidSack decode(FriendlyByteBuf buffer) {
        return new PacketToggleVoidSack(buffer.readBoolean() ? InteractionHand.MAIN_HAND : InteractionHand.OFF_HAND);
    }

    public static void send(InteractionHand hand) {
        ModMessages.sendToServer(new PacketToggleVoidSack(hand));
    }

    public static class Handler
    {
        public static void handle(final PacketToggleVoidSack msg, final Supplier<NetworkEvent.Context> ctx) {
            ctx.get().enqueueWork(() -> {
                ServerPlayer player = ctx.get().getSender();
                if(player == null) return;

                if(player.containerMenu instanceof MenuVoidSack menu) {
                    menu.toggleSendToContainer();
                }
            });
            ctx.get().setPacketHandled(true);
        }
    }
}
