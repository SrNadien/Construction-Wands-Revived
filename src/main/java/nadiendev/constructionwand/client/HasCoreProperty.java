package nadiendev.constructionwand.client;

import com.mojang.serialization.MapCodec;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.renderer.item.properties.conditional.ConditionalItemModelProperty;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.item.ItemStack;
import nadiendev.constructionwand.basics.option.WandOptions;
import nadiendev.constructionwand.items.core.CoreDefault;
import nadiendev.constructionwand.items.wand.ItemWand;

import javax.annotation.Nullable;

public class HasCoreProperty implements ConditionalItemModelProperty
{
    public static final MapCodec<HasCoreProperty> MAP_CODEC = MapCodec.unit(new HasCoreProperty());

    @Override
    public boolean get(ItemStack stack, @Nullable ClientLevel level, @Nullable LivingEntity entity, int seed, ItemDisplayContext displayContext) {
        if(!(stack.getItem() instanceof ItemWand)) return false;
        return !(new WandOptions(stack).cores.get() instanceof CoreDefault);
    }

    @Override
    public MapCodec<? extends ConditionalItemModelProperty> type() {
        return MAP_CODEC;
    }
}
