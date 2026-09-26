package nadiendev.constructionwand.creative;

import net.minecraft.network.chat.Component;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraft.core.registries.Registries;
import net.minecraftforge.registries.RegistryObject;
import nadiendev.constructionwand.ConstructionWand;
import nadiendev.constructionwand.items.ModItems;

public class ModCreativeTabs
{
    public static final DeferredRegister<CreativeModeTab> CREATIVE_TABS =
            DeferredRegister.create(Registries.CREATIVE_MODE_TAB, ConstructionWand.MODID);

    public static final RegistryObject<CreativeModeTab> CONSTRUCTION_WAND_TAB =
            CREATIVE_TABS.register("construction_wand_tab", () -> CreativeModeTab.builder()
                    .title(Component.translatable("itemGroup." + ConstructionWand.MODID))
                    .icon(() -> new ItemStack(ModItems.WAND_INFINITY.get()))
                    .displayItems((parameters, output) -> {
                        for(RegistryObject<Item> entry : ModItems.ITEMS.getEntries()) {
                            output.accept(entry.get());
                        }
                    })
                    .build());

    public static void register(IEventBus eventBus) {
        CREATIVE_TABS.register(eventBus);
    }
}
