package nadiendev.constructionwand.data;

<<<<<<< Updated upstream
import net.minecraft.client.data.models.ItemModelGenerators;
import net.minecraft.client.data.models.ModelProvider;
import net.minecraft.client.data.models.model.ModelTemplates;
import net.minecraft.client.data.models.model.TextureMapping;
import net.minecraft.data.PackOutput;
import net.minecraft.world.item.Item;
import net.neoforged.neoforge.registries.DeferredHolder;
import nadiendev.constructionwand.ConstructionWand;
import nadiendev.constructionwand.items.ModItems;

public class WandModelProvider extends ModelProvider
{
=======
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

>>>>>>> Stashed changes
    public WandModelProvider(PackOutput output) {
        super(output, ConstructionWand.MODID);
    }

<<<<<<< Updated upstream
    @Override
    protected void registerModels(net.minecraft.client.data.models.BlockModelGenerators blockModels, ItemModelGenerators itemModels) {
        for(DeferredHolder<Item, ? extends Item> entry : ModItems.ITEMS.getEntries()) {
            Item item = entry.get();
            String name = entry.getId().getPath();
            itemModels.generateFlatItem(item, ModelTemplates.FLAT_HANDHELD_ITEM);
        }
    }

    @Override
    public String getName() {
        return ConstructionWand.MODNAME + " item models";
    }
}
=======
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
        ResourceLocation location = ModelLocationUtils.getModelLocation(wand);

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
                        ResourceLocation.fromNamespaceAndPath(ConstructionWand.MODID, "item/overlay_core")
                )
        );

        // Modelo con core "destruction"
        ItemModel.Unbaked destructionModel = ItemModelUtils.plainModel(
                generateLayeredItem(
                        itemModels,
                        location.withSuffix("_destruction"),
                        location,
                        ResourceLocation.fromNamespaceAndPath(ConstructionWand.MODID, "item/overlay_core")
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
>>>>>>> Stashed changes
