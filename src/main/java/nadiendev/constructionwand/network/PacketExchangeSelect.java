package nadiendev.constructionwand.network;

import net.minecraft.ChatFormatting;
import net.minecraft.core.BlockPos;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.HitResult;
import net.minecraftforge.network.NetworkEvent;
import nadiendev.constructionwand.basics.WandUtil;
import nadiendev.constructionwand.wand.action.ActionExchange;

import java.util.function.Supplier;

import static nadiendev.constructionwand.ConstructionWand.MODID;

public class PacketExchangeSelect
{
    public PacketExchangeSelect() {}

    public static void encode(PacketExchangeSelect msg, FriendlyByteBuf buffer) {}

    public static PacketExchangeSelect decode(FriendlyByteBuf buffer) {
        return new PacketExchangeSelect();
    }

    public static class Handler
    {
        public static void handle(final PacketExchangeSelect msg, final Supplier<NetworkEvent.Context> ctx) {
            ctx.get().enqueueWork(() -> {
                ServerPlayer player = ctx.get().getSender();
                if(player == null) return;

                ItemStack wand = WandUtil.holdingWand(player);
                if(wand == null) return;

                HitResult hit = player.pick(player.getBlockReach(), 1.0F, false);

                if(!(hit instanceof BlockHitResult blockHit) || hit.getType() != HitResult.Type.BLOCK) {
                    player.displayClientMessage(
                            Component.translatable(MODID + ".message.exchange_no_target")
                                    .withStyle(ChatFormatting.RED), true);
                    return;
                }

                BlockPos pos = blockHit.getBlockPos();
                ActionExchange.selectReplacementBlock(player.level(), player, pos);
            });
            ctx.get().setPacketHandled(true);
        }
    }
}
