package nadiendev.constructionwand.component;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.nbt.NbtOps;
import net.minecraft.nbt.Tag;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.SimpleContainer;
import org.jetbrains.annotations.Nullable;

import java.util.Objects;

public record VoidSackData(
        CompoundTag items,           // inventario interno serializado
        long linkedPos,              // BlockPos.asLong(), 0 = no linkeado
        String linkedDim,            // "namespace:path" de la dimensión, "" = none
        boolean sendToContainer,
        boolean active
) {

    // ── Instancia vacía (default) ──────────────────────────────────────────
    public static final VoidSackData EMPTY = new VoidSackData(
            new CompoundTag(), 0L, "", false, false
    );

    // ── Codec (persistencia en disco) ─────────────────────────────────────
    public static final Codec<VoidSackData> CODEC = RecordCodecBuilder.create(instance ->
            instance.group(
                    CompoundTag.CODEC
                            .optionalFieldOf("items", new CompoundTag())
                            .forGetter(VoidSackData::items),
                    Codec.LONG
                            .optionalFieldOf("linked_pos", 0L)
                            .forGetter(VoidSackData::linkedPos),
                    Codec.STRING
                            .optionalFieldOf("linked_dim", "")
                            .forGetter(VoidSackData::linkedDim),
                    Codec.BOOL
                            .optionalFieldOf("send_to_container", false)
                            .forGetter(VoidSackData::sendToContainer),
                    Codec.BOOL
                            .optionalFieldOf("active", false)
                            .forGetter(VoidSackData::active)
            ).apply(instance, VoidSackData::new)
    );

    // ── StreamCodec (sincronización por red) ──────────────────────────────
    public static final StreamCodec<FriendlyByteBuf, VoidSackData> STREAM_CODEC =
            StreamCodec.composite(
                    ByteBufCodecs.COMPOUND_TAG,  VoidSackData::items,
                    ByteBufCodecs.VAR_LONG,       VoidSackData::linkedPos,
                    ByteBufCodecs.STRING_UTF8,    VoidSackData::linkedDim,
                    ByteBufCodecs.BOOL,           VoidSackData::sendToContainer,
                    ByteBufCodecs.BOOL,           VoidSackData::active,
                    VoidSackData::new
            );

    // ── Helpers de "modificación" (devuelven nueva instancia) ─────────────

    public VoidSackData withItems(CompoundTag newItems) {
        return new VoidSackData(newItems, linkedPos, linkedDim, sendToContainer, active);
    }

    public VoidSackData withLinkedPos(long pos, String dim) {
        return new VoidSackData(items, pos, dim, sendToContainer, active);
    }

    public VoidSackData withNoLink() {
        return new VoidSackData(items, 0L, "", sendToContainer, active);
    }

    public VoidSackData withSendToContainer(boolean value) {
        return new VoidSackData(items, linkedPos, linkedDim, value, active);
    }

    public VoidSackData withActive(boolean value) {
        return new VoidSackData(items, linkedPos, linkedDim, sendToContainer, value);
    }

    // ── Acceso a linkedPos como BlockPos ──────────────────────────────────

    public boolean hasLinkedPos() {
        return linkedPos != 0L;
    }

    @Nullable
    public BlockPos getLinkedBlockPos() {
        return linkedPos != 0L ? BlockPos.of(linkedPos) : null;
    }

    // ── Helpers para el inventario interno ────────────────────────────────
    public static final String TAG_ITEMS = "Items";

    public SimpleContainer loadInventory(int size) {
        SimpleContainer inv = new SimpleContainer(size);
        if (!items.contains(TAG_ITEMS)) return inv;

        ListTag list = items.getList(TAG_ITEMS).orElse(null);
        if (list == null) return inv;

        for (int i = 0; i < list.size(); i++) {
            CompoundTag slot = list.getCompound(i).orElse(null);
            if (slot == null) continue;

            int slotIdx = slot.getByte("Slot").orElse((byte) 0) & 0xFF;
            if (slotIdx >= size) continue;

            final int finalSlot = slotIdx;
            ItemStack.OPTIONAL_CODEC
                    .parse(NbtOps.INSTANCE, slot)
                    .result()
                    .ifPresent(s -> inv.setItem(finalSlot, s));
        }
        return inv;
    }

    public VoidSackData saveInventory(SimpleContainer inv) {
        CompoundTag newItems = items.copy();
        ListTag list = new ListTag();

        for (int i = 0; i < inv.getContainerSize(); i++) {
            ItemStack s = inv.getItem(i);
            if (s.isEmpty()) continue;

            Tag encoded = ItemStack.CODEC
                    .encodeStart(NbtOps.INSTANCE, s)
                    .result()
                    .orElse(null);

            if (encoded instanceof CompoundTag slot) {
                slot.putByte("Slot", (byte) i);
                list.add(slot);
            }
        }

        newItems.put(TAG_ITEMS, list);
        return withItems(newItems);
    }

    
    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (!(obj instanceof VoidSackData other)) return false;
        return linkedPos == other.linkedPos
                && sendToContainer == other.sendToContainer
                && active == other.active
                && Objects.equals(linkedDim, other.linkedDim)
                && Objects.equals(items.toString(), other.items.toString());
    }

    @Override
    public int hashCode() {
        return Objects.hash(items.toString(), linkedPos, linkedDim, sendToContainer, active);
    }
}