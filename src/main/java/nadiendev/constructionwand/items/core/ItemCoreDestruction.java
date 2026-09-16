package nadiendev.constructionwand.items.core;

import net.minecraft.resources.Identifier;
import nadiendev.constructionwand.ConstructionWand;
import nadiendev.constructionwand.api.IWandAction;
import nadiendev.constructionwand.wand.action.ActionDestruction;

public class ItemCoreDestruction extends ItemCore
{
    public ItemCoreDestruction(Properties properties) {
        super(properties);
    }

    @Override
    public int getColor() {
        return 0xFFFF0000;
    }

    @Override
    public IWandAction getWandAction() {
        return new ActionDestruction();
    }

    @Override
    public Identifier getRegistryName() {
        return ConstructionWand.loc("core_destruction");
    }
}