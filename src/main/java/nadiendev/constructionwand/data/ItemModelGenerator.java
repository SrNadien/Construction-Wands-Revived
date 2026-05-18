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
import net.minecraft.resources.Identifier;
import net.minecraft.world.item.Item;
import net.neoforged.neoforge.registries.DeferredItem;

import java.util.ArrayList;
import java.util.List;

public class ItemModelGenerator extends ModelProvider {

    public ItemModelGenerator(PackOutput output) {
        super(output, ConstructionWand.MODID);
    }

    // Template handheld de 2 capas
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
    }

    private void generateWandModel(ItemModelGenerators itemModels, DeferredItem<Item> wandItem) {
        Item wand = wandItem.get();
        Identifier location = ModelLocationUtils.getModelLocation(wand);

        // Modelo base: sin core (handheld normal)
        ItemModel.Unbaked base = ItemModelUtils.plainModel(
                itemModels.createFlatItemModel(wand, ModelTemplates.FLAT_HANDHELD_ITEM)
        );

        // Modelo con core "angel"
        ItemModel.Unbaked angelModel = ItemModelUtils.plainModel(
                generateLayeredItem(
                        itemModels,
                        location.withSuffix("_angel"),
                        location,
                        Identifier.fromNamespaceAndPath(ConstructionWand.MODID, "item/overlay_core")
                )
        );

        // Modelo con core "destruction"
        ItemModel.Unbaked destructionModel = ItemModelUtils.plainModel(
                generateLayeredItem(
                        itemModels,
                        location.withSuffix("_destruction"),
                        location,
                        Identifier.fromNamespaceAndPath(ConstructionWand.MODID, "item/overlay_core")
                )
        );

        // Select por string
        List<SelectItemModel.SwitchCase<String>> cases = new ArrayList<>();
        cases.add(ItemModelUtils.when("angel", angelModel));
        cases.add(ItemModelUtils.when("destruction", destructionModel));

        itemModels.itemModelOutput.accept(
                wand,
                ItemModelUtils.select(new SelectWandCore(), base, cases)
        );
    }

    /**
     * Crea un modelo handheld de 2 capas dado layer0 y layer1.
     */
    public Identifier generateLayeredItem(ItemModelGenerators itemModels,
                                                Identifier modelLocation,
                                                Identifier layer0,
                                                Identifier layer1) {
        return TWO_LAYERED_HANDHELD.create(
                modelLocation,
                TextureMapping.layered(layer0, layer1),
                itemModels.modelOutput
        );
    }
}