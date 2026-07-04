package nadiendev.constructionwand.component;

import net.minecraft.core.component.DataComponentType;
import net.minecraft.core.registries.Registries;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredRegister;
import nadiendev.constructionwand.ConstructionWand;

import java.util.function.Supplier;

public class ModDataComponents
{
    public static final DeferredRegister.DataComponents DATA_COMPONENT_TYPES =
            DeferredRegister.createDataComponents(Registries.DATA_COMPONENT_TYPE, ConstructionWand.MODID);

    // ── Void Sack ─────────────────────────────────────────────────────────────
    public static final Supplier<DataComponentType<VoidSackData>> VOID_SACK_DATA =
            DATA_COMPONENT_TYPES.registerComponentType(
                    "void_sack_data",
                    builder -> builder
                            .persistent(VoidSackData.CODEC)
                            .networkSynchronized(VoidSackData.STREAM_CODEC)
            );

    public static void register(IEventBus modBus) {
        DATA_COMPONENT_TYPES.register(modBus);
    }
}