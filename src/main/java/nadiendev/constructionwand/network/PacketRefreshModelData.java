package nadiendev.constructionwand.network;

import net.minecraft.core.BlockPos;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.Identifier;
import net.neoforged.neoforge.network.handling.IPayloadContext;

import java.util.HashSet;
import java.util.Set;

import static nadiendev.constructionwand.ConstructionWand.MODID;

public record PacketRefreshModelData(HashSet<BlockPos> positions) implements CustomPacketPayload
{
    public static final StreamCodec<FriendlyByteBuf, PacketRefreshModelData> CODEC = CustomPacketPayload.codec(
            PacketRefreshModelData::encode,
            PacketRefreshModelData::new);
    public static final Type<PacketRefreshModelData> ID =
            new Type<>(Identifier.fromNamespaceAndPath(MODID, "refresh_model_data"));

    public PacketRefreshModelData(FriendlyByteBuf buffer) {
        this(readPositions(buffer));
    }

    public PacketRefreshModelData(Set<BlockPos> positions) {
        this(new HashSet<>(positions));
    }

    private static HashSet<BlockPos> readPositions(FriendlyByteBuf buffer) {
        HashSet<BlockPos> positions = new HashSet<>();
        while(buffer.isReadable()) {
            positions.add(buffer.readBlockPos());
        }
        return positions;
    }

    public void encode(FriendlyByteBuf buffer) {
        for(BlockPos pos : positions) {
            buffer.writeBlockPos(pos);
        }
    }

    @Override
    public Type<? extends CustomPacketPayload> type() {
        return ID;
    }

    public static class Handler
    {
        public static void handle(final PacketRefreshModelData msg, final IPayloadContext ctx) {
            ctx.enqueueWork(() -> ClientRefresh.refresh(msg.positions()));
        }
    }
}
