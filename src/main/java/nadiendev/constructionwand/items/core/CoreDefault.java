package nadiendev.constructionwand.items.core;

import net.minecraft.resources.Identifier;
import nadiendev.constructionwand.ConstructionWand;
import nadiendev.constructionwand.api.IWandAction;
import nadiendev.constructionwand.api.IWandCore;
import nadiendev.constructionwand.wand.action.ActionConstruction;

public class CoreDefault implements IWandCore
{
    @Override
    public int getColor() {
        return -1;
    }

    @Override
    public IWandAction getWandAction() {
        return new ActionConstruction();
    }

    @Override
    public Identifier getRegistryName() {
        return ConstructionWand.loc("default");
    }
}