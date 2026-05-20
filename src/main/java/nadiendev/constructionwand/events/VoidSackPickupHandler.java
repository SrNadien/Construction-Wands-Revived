package nadiendev.constructionwand.events;

import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.neoforge.event.entity.player.ItemEntityPickupEvent;
import nadiendev.constructionwand.items.containeritems.ItemVoidSack;

public class VoidSackPickupHandler
{
    @SubscribeEvent
    public void onItemPickup(ItemEntityPickupEvent.Pre event) {
        Player player = event.getPlayer();
        if (!(player instanceof ServerPlayer sp)) return;
        if (!(player.level() instanceof ServerLevel level)) return;

        ItemStack sack = findActiveSack(sp);
        if (sack == null) return;

        ItemStack picked = event.getItemEntity().getItem().copy();
        if (picked.isEmpty()) return;

        int originalCount = picked.getCount();
        int remaining = ItemVoidSack.interceptPickup(level, sack, picked);

        if (remaining >= originalCount) return;

        int absorbed = originalCount - remaining;

        ItemStack entityStack = event.getItemEntity().getItem();
        entityStack.shrink(absorbed);

        if (remaining == 0) {
            event.setCanPickup(net.neoforged.neoforge.common.util.TriState.FALSE);
            if (event.getItemEntity().getItem().isEmpty()) {
                event.getItemEntity().discard();
            }
        }
    }

    private static ItemStack findActiveSack(ServerPlayer player) {
        for (InteractionHand hand : InteractionHand.values()) {
            ItemStack stack = player.getItemInHand(hand);
            if (stack.getItem() instanceof ItemVoidSack && ItemVoidSack.isActive(stack)) {
                return stack;
            }
        }
        return null;
    }
}