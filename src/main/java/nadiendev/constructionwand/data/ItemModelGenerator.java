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
import net.minecraft.client.resources.model.sprite.Material;
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

        // Void Sack
        itemModels.itemModelOutput.accept(
                ModItems.VOID_SACK.get(),
                ItemModelUtils.plainModel(
                        ModelTemplates.FLAT_ITEM.create(
                                ModelLocationUtils.getModelLocation(ModItems.VOID_SACK.get()),
                                TextureMapping.layer0(
                                        new Material(
                                                Identifier.fromNamespaceAndPath(ConstructionWand.MODID, "item/backpack_void")
                                        )
                                ),
                                itemModels.modelOutput
                        )
                )
        );
    }

    private void generateWandModel(ItemModelGenerators itemModels, DeferredItem<Item> wandItem) {
        Item wand = wandItem.get();
        Identifier location = ModelLocationUtils.getModelLocation(wand);

        // Modelo base: sin core (handheld normal)
        ItemModel.Unbaked base = ItemModelUtils.plainModel(
                itemModels.createFlatItemModel(wand, ModelTemplates.FLAT_HANDHELD_ITEM)
        );

        Material wandTexture = TextureMapping.getItemTexture(wand);

        // Overlay: con core "destruction"
        Material overlayMaterial = new Material(
                Identifier.fromNamespaceAndPath(ConstructionWand.MODID, "item/overlay_core")
        );

        // Modelo con core "angel": layer0 = textura del wand + layer1 = overlay_core
        ItemModel.Unbaked angelModel = ItemModelUtils.plainModel(
                generateLayeredItem(
                        itemModels,
                        location.withSuffix("_angel"),
                        wandTexture,
                        overlayMaterial
                )
        );

        // Modelo con core "destruction": layer0 = textura del wand + layer1 = overlay_core
        ItemModel.Unbaked destructionModel = ItemModelUtils.plainModel(
                generateLayeredItem(
                        itemModels,
                        location.withSuffix("_destruction"),
                        wandTexture,
                        overlayMaterial
                )
        );

        // Modelo con core "exchange": layer0 = textura del wand + layer1 = overlay_core
        ItemModel.Unbaked exchangeModel = ItemModelUtils.plainModel(
                generateLayeredItem(
                        itemModels,
                        location.withSuffix("_exchange"),
                        wandTexture,
                        overlayMaterial
                )
        );

        // Select por string
        List<SelectItemModel.SwitchCase<String>> cases = new ArrayList<>();
        cases.add(ItemModelUtils.when("angel", angelModel));
        cases.add(ItemModelUtils.when("destruction", destructionModel));
        cases.add(ItemModelUtils.when("exchange", exchangeModel));

        itemModels.itemModelOutput.accept(
                wand,
                ItemModelUtils.select(new SelectWandCore(), base, cases)
        );
    }

    public Identifier generateLayeredItem(ItemModelGenerators itemModels,
                                           Identifier modelLocation,
                                           Material layer0,
                                           Material layer1) {
        return TWO_LAYERED_HANDHELD.create(
                modelLocation,
                TextureMapping.layered(layer0, layer1),
                itemModels.modelOutput
        );
    }
}