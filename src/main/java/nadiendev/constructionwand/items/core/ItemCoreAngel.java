package nadiendev.constructionwand.items.core;

import net.minecraft.resources.Identifier;
import nadiendev.constructionwand.ConstructionWand;
import nadiendev.constructionwand.api.IWandAction;
import nadiendev.constructionwand.wand.action.ActionAngel;

public class ItemCoreAngel extends ItemCore
{
    public ItemCoreAngel(Properties properties) {
        super(properties);
    }

    @Override
    public int getColor() {
        return 0xFFE9B115;
    }

    @Override
    public IWandAction getWandAction() {
        return new ActionAngel();
    }

    @Override
    public Identifier getRegistryName() {
        return ConstructionWand.loc("core_angel");
    }
}