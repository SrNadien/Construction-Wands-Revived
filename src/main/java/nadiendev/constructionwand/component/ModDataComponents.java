package nadiendev.constructionwand.component;

import net.minecraft.core.component.DataComponentType;
import net.minecraft.core.registries.Registries;
import net.neoforged.neoforge.registries.DeferredRegister;
import nadiendev.constructionwand.ConstructionWand;

import java.util.function.Supplier;

public class ModDataComponents
{
    public static final DeferredRegister<DataComponentType<?>> DATA_COMPONENT_TYPES =
            DeferredRegister.create(Registries.DATA_COMPONENT_TYPE, ConstructionWand.MODID);

    // ── Void Sack ─────────────────────────────────────────────────────────────
    public static final Supplier<DataComponentType<VoidSackData>> VOID_SACK_DATA =
            DATA_COMPONENT_TYPES.register("void_sack_data", () ->
                    DataComponentType.<VoidSackData>builder()
                            .persistent(VoidSackData.CODEC)
                            .networkSynchronized(VoidSackData.STREAM_CODEC)
                            .build());
}