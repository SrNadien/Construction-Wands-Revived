package nadiendev.constructionwand.network;

import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.core.BlockPos;
import net.minecraft.core.SectionPos;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.client.event.ClientTickEvent;
import nadiendev.constructionwand.ConstructionWand;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@EventBusSubscriber(modid = ConstructionWand.MODID, value = Dist.CLIENT)
public final class ClientRefresh {

    private static final int MAX_ATTEMPTS = 20;

    private static final List<Pending> pending = new ArrayList<>();

    private ClientRefresh() {}

    private static final class Pending {
        final Set<BlockPos> positions;
        final Set<SectionPos> sections = new HashSet<>();
        int attempts;

        Pending(Set<BlockPos> positions) {
            this.positions = positions;
            this.attempts = 0;
        }
    }

    public static void refresh(Set<BlockPos> positions) {
        if(positions.isEmpty()) return;
        Pending entry = new Pending(new HashSet<>(positions));
        if(!attempt(entry)) {
            pending.add(entry);
        }
    }

    @SubscribeEvent
    public static void onClientTick(ClientTickEvent.Post event) {
        if(pending.isEmpty()) return;
        pending.removeIf(ClientRefresh::attempt);
    }

    private static boolean attempt(Pending entry) {
        Minecraft mc = Minecraft.getInstance();
        ClientLevel level = mc.level;
        if(level == null) return true;

        Set<SectionPos> dirty = new HashSet<>();

        entry.positions.removeIf(pos -> {
            if(!level.isLoaded(pos)) return false;

            BlockEntity blockEntity = level.getBlockEntity(pos);
            if(blockEntity == null) return false;

            blockEntity.requestModelDataUpdate();
            dirty.add(SectionPos.of(pos));
            return true;
        });

        entry.sections.addAll(dirty);
        for(SectionPos section : dirty) {
            level.setSectionDirtyWithNeighbors(section.x(), section.y(), section.z());
        }

        if(entry.positions.isEmpty()) return true;

        if(++entry.attempts >= MAX_ATTEMPTS) {
            for(SectionPos section : entry.sections) {
                level.setSectionDirtyWithNeighbors(section.x(), section.y(), section.z());
            }
            return true;
        }
        return false;
    }
}
