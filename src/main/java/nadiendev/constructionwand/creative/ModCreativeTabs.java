package nadiendev.constructionwand.creative;

import nadiendev.constructionwand.ConstructionWand;
import nadiendev.constructionwand.items.ModItems;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

public class ModCreativeTabs {

    public static final DeferredRegister<CreativeModeTab> CREATIVE_TABS =
            DeferredRegister.create(Registries.CREATIVE_MODE_TAB, ConstructionWand.MODID);

    public static final DeferredHolder<CreativeModeTab, CreativeModeTab> CONSTRUCTION_WAND_TAB =
            CREATIVE_TABS.register("construction_wand_tab", () -> CreativeModeTab.builder()
                    .title(Component.translatable(
                            "itemGroup." + ConstructionWand.MODID + ".construction_wand_tab"))
                    .icon(() -> new ItemStack(ModItems.WAND_INFINITY.get()))
                    .displayItems((parameters, output) -> {
                        output.accept(ModItems.WAND_STONE.get());
                        output.accept(ModItems.WAND_IRON.get());
                        output.accept(ModItems.WAND_DIAMOND.get());
                        output.accept(ModItems.WAND_NETHERITE.get());
                        output.accept(ModItems.WAND_INFINITY.get());
                        output.accept(ModItems.CORE_ANGEL.get());
                        output.accept(ModItems.CORE_DESTRUCTION.get());
                        output.accept(ModItems.VOID_SACK.get());
                    })
                    .build());

    public static void register(IEventBus eventBus) {
        CREATIVE_TABS.register(eventBus);
    }
}