package nadiendev.constructionwand.data;

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
    public WandModelProvider(PackOutput output) {
        super(output, ConstructionWand.MODID);
    }

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
