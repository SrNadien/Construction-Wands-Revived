package nadiendev.constructionwand.items.core;

import net.minecraft.resources.Identifier;
import nadiendev.constructionwand.ConstructionWand;
import nadiendev.constructionwand.api.IWandAction;
import nadiendev.constructionwand.wand.action.ActionExchange;

public class ItemCoreExchange extends ItemCore
{
    public ItemCoreExchange(Properties properties) {
        super(properties);
    }

    @Override
    public int getColor() {
        return 0x2ECC71;
    }

    @Override
    public IWandAction getWandAction() {
        return new ActionExchange();
    }

    @Override
    public Identifier getRegistryName() {
        return ConstructionWand.loc("core_exchange");
    }
}