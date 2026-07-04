package nadiendev.constructionwand.events;

import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.item.ItemStack;
import net.neoforged.bus.api.EventPriority;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.neoforge.event.entity.EntityJoinLevelEvent;
import nadiendev.constructionwand.items.containeritems.ItemVoidSack;
import nadiendev.constructionwand.wand.action.ActionDestruction;

public class VoidSackDropHandler
{
    @SubscribeEvent(priority = EventPriority.HIGH)
    public void onEntityJoinLevel(EntityJoinLevelEvent event) {
        if (event.getLevel().isClientSide()) return;
        if (!(event.getEntity() instanceof ItemEntity itemEntity)) return;

        ActionDestruction.VoidSackDropContext ctx = ActionDestruction.ACTIVE_CONTEXT.get();
        if (ctx == null) return;

        ServerLevel level = ctx.level();
        ItemStack sack = ctx.sack();

        if (sack.isEmpty() || !(sack.getItem() instanceof ItemVoidSack)) return;

        ItemStack drop = itemEntity.getItem().copy();
        if (drop.isEmpty()) return;

        int remaining = ItemVoidSack.receive(level, sack, drop);

        if (remaining <= 0) {
            // El sack absorbió todo — cancelar la entidad
            event.setCanceled(true);
        } else if (remaining < drop.getCount()) {
            // Absorbió parcialmente — reducir el stack que cae
            itemEntity.getItem().setCount(remaining);
        }
    }
}