package nadiendev.constructionwand.data;

import nadiendev.constructionwand.ConstructionWand;
import nadiendev.constructionwand.client.property.SelectWandCore;
import nadiendev.constructionwand.items.ModItems;
import net.minecraft.client.data.models.BlockModelGenerators;
import net.minecraft.client.data.models.ItemModelGenerators;
import net.minecraft.client.data.models.ModelProvider;
import net.minecraft.client.data.models.model.*;
import net.minecraft.client.renderer.item.ItemModel;
import net.minecraft.client.renderer.item.SelectItemModel;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.neoforged.neoforge.registries.DeferredItem;

import java.util.ArrayList;
import java.util.List;

public class WandModelProvider extends ModelProvider {

    public WandModelProvider(PackOutput output) {
        super(output, ConstructionWand.MODID);
    }

    public static final ModelTemplate TWO_LAYERED_HANDHELD =
            ModelTemplates.createItem("handheld", TextureSlot.LAYER0, TextureSlot.LAYER1);

    @Override
    protected void registerModels(BlockModelGenerators blockModels, ItemModelGenerators itemModels) {
        for (DeferredItem<Item> wand : ModItems.WANDS) {
            generateWandModel(itemModels, wand);
        }
        for (DeferredItem<Item> core : ModItems.CORES) {
            itemModels.generateFlatItem(core.get(), ModelTemplates.FLAT_ITEM);
        }

        // Void Sack — textura: textures/item/backpack_void.png
        itemModels.itemModelOutput.accept(
                ModItems.VOID_SACK.get(),
                ItemModelUtils.plainModel(
                        ModelTemplates.FLAT_ITEM.create(
                                ModelLocationUtils.getModelLocation(ModItems.VOID_SACK.get()),
                                new TextureMapping().put(TextureSlot.LAYER0,
                                        ResourceLocation.fromNamespaceAndPath(ConstructionWand.MODID, "item/backpack_void")),
                                itemModels.modelOutput
                        )
                )
        );
    }

    private void generateWandModel(ItemModelGenerators itemModels, DeferredItem<Item> wandItem) {
        Item wand = wandItem.get();
        ResourceLocation location = ModelLocationUtils.getModelLocation(wand);

        ItemModel.Unbaked base = ItemModelUtils.plainModel(
                itemModels.createFlatItemModel(wand, ModelTemplates.FLAT_HANDHELD_ITEM)
        );

        ItemModel.Unbaked angelModel = ItemModelUtils.plainModel(
                generateLayeredItem(
                        itemModels,
                        location.withSuffix("_core_angel"),
                        location,
                        ResourceLocation.fromNamespaceAndPath(ConstructionWand.MODID, "item/overlay_core")
                )
        );

        ItemModel.Unbaked destructionModel = ItemModelUtils.plainModel(
                generateLayeredItem(
                        itemModels,
                        location.withSuffix("_core_destruction"),
                        location,
                        ResourceLocation.fromNamespaceAndPath(ConstructionWand.MODID, "item/overlay_core")
                )
        );

        List<SelectItemModel.SwitchCase<String>> cases = new ArrayList<>();
        cases.add(ItemModelUtils.when("core_angel", angelModel));
        cases.add(ItemModelUtils.when("core_destruction", destructionModel));

        itemModels.itemModelOutput.accept(
                wand,
                ItemModelUtils.select(new SelectWandCore(), base, cases)
        );
    }

    public ResourceLocation generateLayeredItem(ItemModelGenerators itemModels,
                                                ResourceLocation modelLocation,
                                                ResourceLocation layer0,
                                                ResourceLocation layer1) {
        return TWO_LAYERED_HANDHELD.create(
                modelLocation,
                TextureMapping.layered(layer0, layer1),
                itemModels.modelOutput
        );
    }
}