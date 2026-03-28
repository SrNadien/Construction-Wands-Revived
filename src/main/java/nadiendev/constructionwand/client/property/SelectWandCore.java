package nadiendev.constructionwand.client.property;

import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.renderer.item.properties.select.SelectItemModelProperty;
import net.minecraft.core.component.DataComponents;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.component.CustomModelData;
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
        CustomModelData data = stack.get(DataComponents.CUSTOM_MODEL_DATA);
        if (data != null && !data.strings().isEmpty()) {
            String coreId = data.strings().get(0);
            if (!coreId.isEmpty()) {
                return coreId;
            }
        }
        return null;
    }

    @Override
    public Codec<String> valueCodec() {
        return Codec.STRING;
    }

    @NotNull
    @Override
    public Type<? extends SelectItemModelProperty<String>, String> type() {
        return TYPE;
    }
}