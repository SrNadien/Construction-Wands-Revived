package nadiendev.constructionwand.client.property;

import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.renderer.item.properties.select.SelectItemModelProperty;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.item.ItemStack;
import nadiendev.constructionwand.basics.option.WandOptions;
import nadiendev.constructionwand.items.core.CoreDefault;
import nadiendev.constructionwand.items.wand.ItemWand;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class SelectWandCore implements SelectItemModelProperty<String> {

    public static final SelectItemModelProperty.Type<SelectWandCore, String> TYPE =
            SelectItemModelProperty.Type.create(
                    MapCodec.unit(new SelectWandCore()),
                    Codec.STRING
            );

    @Nullable
    @Override
    public String get(@NotNull ItemStack stack, @Nullable ClientLevel level,
                      @Nullable LivingEntity entity, int seed,
                      @NotNull ItemDisplayContext displayContext) {
        if (!(stack.getItem() instanceof ItemWand)) return null;

        var core = new WandOptions(stack).cores.get();
        if (core instanceof CoreDefault) return null;

        // Retorna el path del registry name: "core_angel", "core_destruction"
        return core.getRegistryName().getPath();
    }

    @Override
    public Codec<String> valueCodec() {
        return Codec.STRING;
    }

    @Override
    public @NotNull Type<? extends SelectItemModelProperty<String>, String> type() {
        return TYPE;
    }
}