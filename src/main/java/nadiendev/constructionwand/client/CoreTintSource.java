package nadiendev.constructionwand.client;

import com.mojang.serialization.MapCodec;
import net.minecraft.client.color.item.ItemTintSource;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.client.multiplayer.ClientLevel;
import nadiendev.constructionwand.basics.option.WandOptions;
import nadiendev.constructionwand.items.wand.ItemWand;

public class CoreTintSource implements ItemTintSource
{
    public static final MapCodec<CoreTintSource> CODEC = MapCodec.unit(new CoreTintSource());

    @Override
    public int calculate(ItemStack stack, ClientLevel level, LivingEntity entity) {
        if(stack.getItem() instanceof ItemWand) {
            int color = new WandOptions(stack).cores.get().getColor();
            if(color == -1) return 0x00000000;
            return color;
        }
        return 0x00000000;
    }

    @Override
    public MapCodec<? extends ItemTintSource> type() {
        return CODEC;
    }
}
