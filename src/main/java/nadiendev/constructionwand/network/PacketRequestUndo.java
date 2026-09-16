package nadiendev.constructionwand.network;

import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.Identifier;
import net.minecraft.world.entity.player.Player;
import net.neoforged.neoforge.network.handling.IPayloadContext;
import nadiendev.constructionwand.ConstructionWand;
import nadiendev.constructionwand.basics.WandUtil;

import static nadiendev.constructionwand.ConstructionWand.MODID;

public record PacketRequestUndo() implements CustomPacketPayload {

    public static final PacketRequestUndo INSTANCE = new PacketRequestUndo();

    public static final Type<PacketRequestUndo> TYPE =
            new Type<>(Identifier.fromNamespaceAndPath(MODID, "request_undo"));

    public static final StreamCodec<FriendlyByteBuf, PacketRequestUndo> CODEC =
            StreamCodec.unit(INSTANCE);

    @Override
    public Type<? extends CustomPacketPayload> type() {
        return TYPE;
    }

    public static void handle(final PacketRequestUndo msg, final IPayloadContext ctx) {
        ctx.enqueueWork(() -> {
            Player player = ctx.player();
            if (player == null) return;
            if (WandUtil.holdingWand(player) == null) return;

            ConstructionWand.undoHistory.forceUndo(player, player.level());
        });
    }
}