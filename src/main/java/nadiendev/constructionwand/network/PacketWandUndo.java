package nadiendev.constructionwand.network;

import net.minecraft.ChatFormatting;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.network.NetworkEvent;
import nadiendev.constructionwand.ConstructionWand;
import nadiendev.constructionwand.basics.WandUtil;

import java.util.function.Supplier;

public class PacketWandUndo
{
    public PacketWandUndo() {}

    public static void encode(PacketWandUndo msg, FriendlyByteBuf buffer) {}

    public static PacketWandUndo decode(FriendlyByteBuf buffer) {
        return new PacketWandUndo();
    }

    public static class Handler
    {
        public static void handle(final PacketWandUndo msg, final Supplier<NetworkEvent.Context> ctx) {
            ctx.get().enqueueWork(() -> {
                ServerPlayer player = ctx.get().getSender();
                if(player == null) return;

                ItemStack wand = WandUtil.holdingWand(player);
                if(wand == null) return;

                boolean success = ConstructionWand.instance.undoHistory.undoLast(player, player.level());

                player.displayClientMessage(
                        Component.translatable(success
                                        ? "constructionwand.undo.success"
                                        : "constructionwand.undo.nothing")
                                .withStyle(success ? ChatFormatting.GREEN : ChatFormatting.GRAY),
                        true);
            });
            ctx.get().setPacketHandled(true);
        }
    }
}
