package nadiendev.constructionwand.client;

import com.mojang.blaze3d.platform.InputConstants;
import net.minecraft.client.KeyMapping;
import net.minecraft.client.Minecraft;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.InputEvent;
import net.minecraftforge.client.event.RegisterKeyMappingsEvent;
import net.minecraftforge.client.settings.KeyConflictContext;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import nadiendev.constructionwand.ConstructionWand;
import nadiendev.constructionwand.basics.WandUtil;
import nadiendev.constructionwand.items.containeritems.ItemVoidSack;
import nadiendev.constructionwand.network.ModMessages;
import nadiendev.constructionwand.network.PacketExchangeSelect;
import nadiendev.constructionwand.network.PacketToggleVoidSackActive;
import nadiendev.constructionwand.network.PacketWandUndo;

@Mod.EventBusSubscriber(modid = ConstructionWand.MODID, value = Dist.CLIENT)
public class KeybindHandler
{
    public static final KeyMapping KEY_VOID_SACK_TOGGLE = new KeyMapping(
            "key." + ConstructionWand.MODID + ".void_sack_toggle",
            KeyConflictContext.IN_GAME,
            InputConstants.Type.KEYSYM,
            InputConstants.KEY_M,
            "key.categories." + ConstructionWand.MODID);

    public static final KeyMapping KEY_EXCHANGE_SELECT = new KeyMapping(
            "key." + ConstructionWand.MODID + ".exchange_select",
            KeyConflictContext.IN_GAME,
            InputConstants.Type.KEYSYM,
            InputConstants.KEY_NUMPAD7,
            "key.categories." + ConstructionWand.MODID);

    public static final KeyMapping KEY_WAND_UNDO = new KeyMapping(
            "key." + ConstructionWand.MODID + ".wand_undo",
            KeyConflictContext.IN_GAME,
            InputConstants.Type.KEYSYM,
            InputConstants.KEY_K,
            "key.categories." + ConstructionWand.MODID);

    @Mod.EventBusSubscriber(modid = ConstructionWand.MODID, bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
    public static class Registration
    {
        @SubscribeEvent
        public static void registerKeyMappings(RegisterKeyMappingsEvent event) {
            event.register(KEY_VOID_SACK_TOGGLE);
            event.register(KEY_EXCHANGE_SELECT);
            event.register(KEY_WAND_UNDO);
        }
    }

    @SubscribeEvent
    public static void onKey(InputEvent.Key event) {
        Player player = Minecraft.getInstance().player;
        if(player == null) return;

        if(KEY_VOID_SACK_TOGGLE.consumeClick()) {
            for(InteractionHand hand : InteractionHand.values()) {
                ItemStack stack = player.getItemInHand(hand);
                if(stack.getItem() instanceof ItemVoidSack) {
                    PacketToggleVoidSackActive.send(hand);
                    break;
                }
            }
        }

        if(KEY_EXCHANGE_SELECT.consumeClick()) {
            if(WandUtil.holdingWand(player) != null) {
                ModMessages.sendToServer(new PacketExchangeSelect());
            }
        }

        if(KEY_WAND_UNDO.consumeClick()) {
            if(WandUtil.holdingWand(player) != null) {
                ModMessages.sendToServer(new PacketWandUndo());
            }
        }
    }
}
