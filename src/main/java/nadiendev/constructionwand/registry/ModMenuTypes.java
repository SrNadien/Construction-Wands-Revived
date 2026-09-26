package nadiendev.constructionwand.registry;

import net.minecraft.world.inventory.MenuType;
import net.minecraftforge.common.extensions.IForgeMenuType;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import nadiendev.constructionwand.ConstructionWand;
import nadiendev.constructionwand.items.containeritems.MenuVoidSack;

public class ModMenuTypes
{
    public static final DeferredRegister<MenuType<?>> MENU_TYPES =
            DeferredRegister.create(ForgeRegistries.MENU_TYPES, ConstructionWand.MODID);

    public static final RegistryObject<MenuType<MenuVoidSack>> VOID_SACK =
            MENU_TYPES.register("void_sack", () ->
                    IForgeMenuType.create((windowId, inv, data) -> {
                        net.minecraft.world.InteractionHand hand =
                                data.readBoolean()
                                        ? net.minecraft.world.InteractionHand.MAIN_HAND
                                        : net.minecraft.world.InteractionHand.OFF_HAND;
                        return new MenuVoidSack(windowId, inv, hand);
                    }));

    public static void register(IEventBus eventBus) {
        MENU_TYPES.register(eventBus);
    }
}
