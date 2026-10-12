//? if neoforge {
package nadiendev.constructionwand.platform;

import net.minecraft.core.BlockPos;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.Identifier;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.MenuType;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.ChunkPos;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.neoforged.fml.ModList;
import net.neoforged.neoforge.capabilities.Capabilities;
import net.neoforged.neoforge.common.NeoForge;
import net.neoforged.neoforge.common.extensions.IMenuTypeExtension;
import net.neoforged.neoforge.common.util.BlockSnapshot;
import net.neoforged.neoforge.event.level.BlockEvent;
//? if <1.21.9
//import net.neoforged.neoforge.items.IItemHandler;
import net.neoforged.neoforge.network.PacketDistributor;
//? if curios
import nadiendev.constructionwand.integrations.curios.CuriosIntegration;
import nadiendev.constructionwand.platform.services.IPlatformHelper;

import javax.annotation.Nullable;
import java.util.List;
import java.util.function.Consumer;

public class NeoForgePlatformHelper implements IPlatformHelper {

    @Override
    public String getPlatformName() {
        return "NeoForge";
    }

    @Override
    public boolean isModLoaded(String modId) {
        return ModList.get().isLoaded(modId);
    }

    @Override
    public List<ItemStack> getCurioStacks(Player player) {
        //? if curios {
        return CuriosIntegration.getCurioStacks(player);
        //?} else
        //return List.of();
    }

    //? if <1.21.4 {
    /*@Override
    public void registerItemProperty(Item item, Identifier name, ItemPropertyFunction function) {
        NeoForgeClientHooks.registerItemProperty(item, name, function);
    }
    *///?}

    @Override
    public void requestModelDataUpdate(BlockEntity blockEntity) {
        blockEntity.requestModelDataUpdate();
    }

    @Override
    public CreativeModeTab.Builder newCreativeTabBuilder() {
        return CreativeModeTab.builder();
    }

    @Override
    public IConfigBuilder createConfigBuilder() {
        return new NeoConfigBuilder();
    }

    @Override
    public boolean firePlaceEvent(Level level, Player player, BlockState placed, BlockPos pos) {
        BlockSnapshot snapshot = BlockSnapshot.create(level.dimension(), level, pos);
        BlockEvent.EntityPlaceEvent event = new BlockEvent.EntityPlaceEvent(snapshot, placed, player);
        NeoForge.EVENT_BUS.post(event);
        return event.isCanceled();
    }

    @Override
    public boolean fireBreakEvent(Level level, Player player, BlockState state, BlockPos pos) {
        //? if >=26.1.2 {
        net.neoforged.neoforge.event.level.block.BreakBlockEvent event = new net.neoforged.neoforge.event.level.block.BreakBlockEvent(level, pos, state, player);
        //?} else {
        /*BlockEvent.BreakEvent event = new BlockEvent.BreakEvent(level, pos, state, player);
        *///?}
        NeoForge.EVENT_BUS.post(event);
        return event.isCanceled();
    }

    @Nullable
    @Override
    public IItemInventory getItemInventory(ItemStack stack) {
        if (stack == null || stack.isEmpty()) return null;
        //? if >=1.21.9 {
        var handler = net.neoforged.neoforge.transfer.access.ItemAccess.forStack(stack).getCapability(Capabilities.Item.ITEM);
        return handler == null ? null : new NeoItemInventory(handler);
        //?} else {
        /*IItemHandler handler = stack.getCapability(Capabilities.ItemHandler.ITEM);
        return handler == null ? null : new NeoItemInventory(handler);
        *///?}
    }

    @Nullable
    @Override
    public IItemInventory getBlockInventory(Level level, BlockPos pos, BlockEntity blockEntity) {
        //? if >=1.21.9 {
        var handler = level.getCapability(Capabilities.Item.BLOCK, pos, blockEntity.getBlockState(), blockEntity, null);
        return handler == null ? null : new NeoItemInventory(handler);
        //?} else {
        /*IItemHandler handler = level.getCapability(Capabilities.ItemHandler.BLOCK, pos, blockEntity.getBlockState(), blockEntity, null);
        return handler == null ? null : new NeoItemInventory(handler);
        *///?}
    }

    @Override
    public <T extends AbstractContainerMenu> MenuType<T> createMenuType(MenuFactory<T> factory) {
        return IMenuTypeExtension.create(factory::create);
    }

    @Override
    public void openMenu(ServerPlayer player, MenuProvider provider, Consumer<FriendlyByteBuf> extraData) {
        player.openMenu(provider, extraData::accept);
    }

    @Override
    public void sendToServer(CustomPacketPayload payload) {
        NeoForgeClientHooks.sendToServer(payload);
    }

    @Override
    public void sendToTrackingChunk(ServerLevel level, ChunkPos chunk, CustomPacketPayload payload) {
        PacketDistributor.sendToPlayersTrackingChunk(level, chunk, payload);
    }
}
//?}
