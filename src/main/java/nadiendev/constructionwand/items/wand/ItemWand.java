package nadiendev.constructionwand.items.wand;

import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.component.TooltipDisplay;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;

import nadiendev.constructionwand.ConstructionWand;
import nadiendev.constructionwand.api.IWandCore;
import nadiendev.constructionwand.basics.WandUtil;
import nadiendev.constructionwand.basics.option.IOption;
import nadiendev.constructionwand.basics.option.WandOptions;
import nadiendev.constructionwand.items.core.ItemCoreAngel;
import nadiendev.constructionwand.items.core.ItemCoreDestruction;
import nadiendev.constructionwand.items.core.ItemCoreExchange;
import nadiendev.constructionwand.wand.WandJob;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.function.Consumer;

public abstract class ItemWand extends Item
{
    public ItemWand(Properties properties) {
        super(properties);
    }

    @Nonnull
    @Override
    public InteractionResult useOn(UseOnContext context) {
        Player player = context.getPlayer();
        InteractionHand hand = context.getHand();
        Level world = context.getLevel();

        if(world.isClientSide() || player == null) return InteractionResult.FAIL;

        ItemStack stack = player.getItemInHand(hand);

        if(ConstructionWand.undoHistory.isUndoActive(player)) {
            return ConstructionWand.undoHistory.undo(player, world, context.getClickedPos()) ? InteractionResult.SUCCESS : InteractionResult.FAIL;
        }
        else {
            WandJob job = getWandJob(player, world, new BlockHitResult(context.getClickLocation(), context.getClickedFace(), context.getClickedPos(), false), stack);
            return job.doIt() ? InteractionResult.SUCCESS : InteractionResult.FAIL;
        }
    }

    @Nonnull
    @Override
    public InteractionResult use(@Nonnull Level world, Player player, @Nonnull InteractionHand hand) {
        ItemStack stack = player.getItemInHand(hand);

        if(!ConstructionWand.undoHistory.isUndoActive(player)) {
            if(world.isClientSide()) return InteractionResult.FAIL;

            WandJob job = getWandJob(player, world, BlockHitResult.miss(player.getLookAngle(),
                    WandUtil.fromVector(player.getLookAngle()), player.blockPosition()), stack);
            return job.doIt() ? InteractionResult.SUCCESS : InteractionResult.FAIL;
        }
        return InteractionResult.FAIL;
    }

    public static WandJob getWandJob(Player player, Level world, @Nullable BlockHitResult rayTraceResult, ItemStack wand) {
        WandJob wandJob = new WandJob(player, world, rayTraceResult, wand);
        wandJob.getSnapshots();
        return wandJob;
    }

    @Override
    public boolean isCorrectToolForDrops(ItemStack stack, BlockState blockIn) {
        return false;
    }

    public boolean isValidRepairItem(@Nonnull ItemStack toRepair, @Nonnull ItemStack repair) {
        return false;
    }

    public int remainingDurability(ItemStack stack) {
        return Integer.MAX_VALUE;
    }

    @Override
    public void appendHoverText(ItemStack itemstack, TooltipContext context,
                                TooltipDisplay tooltipDisplay,
                                Consumer<Component> components, TooltipFlag extraInfo) {
        appendWandTooltip(itemstack, components, extraInfo);
    }

    public static void appendWandTooltip(ItemStack stack, Consumer<Component> lines, TooltipFlag flags) {
        WandOptions options = new WandOptions(stack);
        int limit = options.cores.get().getWandAction().getLimit(stack);
        String langTooltip = ConstructionWand.MODID + ".tooltip.";

        if(flags.hasShiftDown()) {
            for(int i = 1; i < options.allOptions.length; i++) {
                IOption<?> opt = options.allOptions[i];
                lines.accept(Component.translatable(opt.getKeyTranslation()).withStyle(ChatFormatting.AQUA)
                        .append(Component.translatable(opt.getValueTranslation()).withStyle(ChatFormatting.GRAY)));
            }
            if(!options.cores.getUpgrades().isEmpty()) {
                lines.accept(Component.literal(""));
                lines.accept(Component.translatable(langTooltip + "cores").withStyle(ChatFormatting.GRAY));
                for(IWandCore core : options.cores.getUpgrades()) {
                    ChatFormatting coreColor = getCoreColor(core);
                    lines.accept(Component.translatable(options.cores.getKeyTranslation() + "." + core.getClass().getSimpleName())
                            .withStyle(coreColor));
                }
            }
        } else {
            IOption<?> opt = options.allOptions[0];
            lines.accept(Component.translatable(langTooltip + "blocks", limit).withStyle(ChatFormatting.GRAY));

            IWandCore activeCore = options.cores.get();
            ChatFormatting coreColor = getCoreColor(activeCore);
            lines.accept(Component.translatable(opt.getKeyTranslation()).withStyle(ChatFormatting.AQUA)
                    .append(Component.translatable(opt.getValueTranslation()).withStyle(coreColor)));
            lines.accept(Component.translatable(langTooltip + "shift").withStyle(ChatFormatting.AQUA));
        }
    }

    private static ChatFormatting getCoreColor(IWandCore core) {
        if (core instanceof ItemCoreAngel)       return ChatFormatting.YELLOW;
        if (core instanceof ItemCoreDestruction) return ChatFormatting.RED;
        if (core instanceof ItemCoreExchange)    return ChatFormatting.AQUA;
        return ChatFormatting.WHITE;
    }

    public static void optionMessage(Player player, IOption<?> option) {
        player.sendOverlayMessage(
                Component.translatable(option.getKeyTranslation()).withStyle(ChatFormatting.AQUA)
                        .append(Component.translatable(option.getValueTranslation()).withStyle(ChatFormatting.WHITE))
                        .append(Component.literal(" - ").withStyle(ChatFormatting.GRAY))
                        .append(Component.translatable(option.getDescTranslation()).withStyle(ChatFormatting.WHITE)));
    }
}