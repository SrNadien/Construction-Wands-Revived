package nadiendev.constructionwand.network;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraftforge.network.NetworkRegistry;
import net.minecraftforge.network.PacketDistributor;
import net.minecraftforge.network.simple.SimpleChannel;

import static nadiendev.constructionwand.ConstructionWand.MODID;

public class ModMessages
{
    private static final String PROTOCOL_VERSION = "1";

    public static SimpleChannel CHANNEL;

    public static void register() {
        CHANNEL = NetworkRegistry.newSimpleChannel(
                new ResourceLocation(MODID, "main"),
                () -> PROTOCOL_VERSION,
                PROTOCOL_VERSION::equals,
                PROTOCOL_VERSION::equals);

        int index = 0;
        CHANNEL.registerMessage(index++, PacketUndoBlocks.class,
                PacketUndoBlocks::encode, PacketUndoBlocks::decode, PacketUndoBlocks.Handler::handle);
        CHANNEL.registerMessage(index++, PacketQueryUndo.class,
                PacketQueryUndo::encode, PacketQueryUndo::decode, PacketQueryUndo.Handler::handle);
        CHANNEL.registerMessage(index++, PacketWandOption.class,
                PacketWandOption::encode, PacketWandOption::decode, PacketWandOption.Handler::handle);
        CHANNEL.registerMessage(index++, PacketExchangeSelect.class,
                PacketExchangeSelect::encode, PacketExchangeSelect::decode, PacketExchangeSelect.Handler::handle);
        CHANNEL.registerMessage(index++, PacketToggleVoidSack.class,
                PacketToggleVoidSack::encode, PacketToggleVoidSack::decode, PacketToggleVoidSack.Handler::handle);
        CHANNEL.registerMessage(index++, PacketToggleVoidSackActive.class,
                PacketToggleVoidSackActive::encode, PacketToggleVoidSackActive::decode, PacketToggleVoidSackActive.Handler::handle);
        CHANNEL.registerMessage(index, PacketWandUndo.class,
                PacketWandUndo::encode, PacketWandUndo::decode, PacketWandUndo.Handler::handle);
    }

    public static void sendToServer(Object message) {
        CHANNEL.sendToServer(message);
    }

    public static void sendToPlayer(Object message, ServerPlayer player) {
        CHANNEL.send(PacketDistributor.PLAYER.with(() -> player), message);
    }
}
