package nadiendev.constructionwand.events;

import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.event.entity.player.EntityItemPickupEvent;
import nadiendev.constructionwand.items.containeritems.ItemVoidSack;
import nadiendev.constructionwand.wand.action.ActionDestruction;

public class VoidSackPickupHandler
{
    @SubscribeEvent
    public void onItemPickup(EntityItemPickupEvent event) {
        Player player = event.getEntity();
        if (!(player instanceof ServerPlayer sp)) return;
        if (!(player.level() instanceof ServerLevel level)) return;

        ItemStack sack = findActiveSack(sp);
        if (sack == null) return;

        ItemStack picked = event.getItem().getItem().copy();
        if (picked.isEmpty()) return;

        int originalCount = picked.getCount();
        int remaining = ItemVoidSack.interceptPickup(level, sack, picked);

        if (remaining >= originalCount) return;

        int absorbed = originalCount - remaining;

        ItemStack entityStack = event.getItem().getItem();
        entityStack.shrink(absorbed);

        if (remaining == 0) {
            event.setCanceled(true);
            if (event.getItem().getItem().isEmpty()) {
                event.getItem().discard();
            }
        }
    }

    private static ItemStack findActiveSack(ServerPlayer player) {
        ItemStack sack = ActionDestruction.findSack(player);
        if(!sack.isEmpty() && ItemVoidSack.isActive(sack)) return sack;
        return null;
    }
}