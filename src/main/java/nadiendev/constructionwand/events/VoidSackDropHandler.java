package nadiendev.constructionwand.events;

import nadiendev.constructionwand.ConstructionWand;
import nadiendev.constructionwand.items.containeritems.ItemVoidSack;
import nadiendev.constructionwand.wand.action.ActionDestruction;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.item.ItemStack;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.level.BlockDropsEvent;

import java.util.ArrayList;
import java.util.List;

@EventBusSubscriber(modid = ConstructionWand.MODID)
public class VoidSackDropHandler
{
    @SubscribeEvent
    public static void onBlockDrops(BlockDropsEvent event) {
        ActionDestruction.VoidSackDropContext ctx = ActionDestruction.ACTIVE_CONTEXT.get();
        if (ctx == null) return;
        if (!event.getPos().equals(ctx.pos())) return;
        if (!event.getLevel().equals(ctx.level())) return;

        ServerLevel level = ctx.level();
        ItemStack sack = ctx.sack();
        if (sack.isEmpty()) return;

        BlockPos pos = ctx.pos();

        // Los drops ya son silk touch porque DestroySnapshot usa destroyBlock con silk tool
        List<ItemEntity> drops = new ArrayList<>(event.getDrops());
        event.getDrops().clear();

        for (ItemEntity entity : drops) {
            ItemStack drop = entity.getItem().copy();
            if (drop.isEmpty()) continue;

            int leftover = ItemVoidSack.receive(level, sack, drop);
            if (leftover > 0) {
                // Sack lleno: devolver el sobrante al mundo
                event.getDrops().add(new ItemEntity(level,
                        pos.getX() + 0.5, pos.getY() + 0.5, pos.getZ() + 0.5,
                        drop.copyWithCount(leftover)));
            }
        }
    }
}