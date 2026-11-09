package nadiendev.constructionwand.basics;

import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import nadiendev.constructionwand.items.wand.WandTier;
import nadiendev.constructionwand.platform.IConfigBuilder;
import nadiendev.constructionwand.platform.Services;
import nadiendev.constructionwand.registry.RegistryObject;
import nadiendev.constructionwand.items.ModItems;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.function.Supplier;

public class ConfigServer
{
    /** Loader specific spec object, registered by each loader. */
    public static final Object SPEC;

    public static final Supplier<Integer> LIMIT_CREATIVE;
    public static final Supplier<Integer> MAX_RANGE;
    public static final Supplier<Integer> UNDO_HISTORY;
    public static final Supplier<Boolean> ANGEL_FALLING;
    public static final Supplier<Integer> ENERGY_CAPACITY;
    public static final Supplier<Integer> ENERGY_PER_BLOCK;
    public static final Supplier<Boolean> DESTRUCTION_GIVES_DROPS;

    public static final Supplier<List<?>> SIMILAR_BLOCKS;
    private static final String[] SIMILAR_BLOCKS_DEFAULT = {
            "minecraft:dirt;minecraft:grass_block;minecraft:coarse_dirt;minecraft:podzol;minecraft:mycelium;minecraft:farmland;minecraft:dirt_path;minecraft:rooted_dirt"
    };

    public static final Supplier<Boolean> TE_WHITELIST;
    public static final Supplier<List<?>> TE_LIST;
    private static final String[] TE_LIST_DEFAULT = {"chiselsandbits"};

    private static final HashMap<ResourceLocation, WandProperties> wandProperties = new HashMap<>();

    public static WandProperties getWandProperties(Item wand) {
        return wandProperties.getOrDefault(BuiltInRegistries.ITEM.getKey(wand), WandProperties.DEFAULT);
    }

    public static class WandProperties
    {
        public static final WandProperties DEFAULT = new WandProperties(null, null, null, null, null, null);

        private final Supplier<Integer> durability;
        private final Supplier<Integer> limit;
        private final Supplier<Integer> angel;
        private final Supplier<Integer> destruction;
        private final Supplier<Integer> exchange;
        private final Supplier<Boolean> upgradeable;

        private WandProperties(Supplier<Integer> durability, Supplier<Integer> limit,
                               Supplier<Integer> angel, Supplier<Integer> destruction,
                               Supplier<Integer> exchange, Supplier<Boolean> upgradeable) {
            this.durability = durability;
            this.limit = limit;
            this.angel = angel;
            this.destruction = destruction;
            this.exchange = exchange;
            this.upgradeable = upgradeable;
        }

        public WandProperties(IConfigBuilder builder, RegistryObject<? extends Item> wandSupplier, int defDurability, int defLimit,
                              int defAngel, int defDestruction, int defExchange, boolean defUpgradeable) {
            ResourceLocation registryName = wandSupplier.getId();
            builder.push(registryName.getPath());

            if(defDurability > 0) {
                builder.comment("Wand durability");
                durability = builder.defineInRange("durability", defDurability, 1, Integer.MAX_VALUE);
            }
            else durability = null;
            builder.comment("Wand block limit");
            limit = builder.defineInRange("limit", defLimit, 1, Integer.MAX_VALUE);
            builder.comment("Max placement distance with angel core (0 to disable angel core)");
            angel = builder.defineInRange("angel", defAngel, 0, Integer.MAX_VALUE);
            builder.comment("Wand destruction block limit (0 to disable destruction core)");
            destruction = builder.defineInRange("destruction", defDestruction, 0, Integer.MAX_VALUE);
            builder.comment("Wand exchange block limit (0 to disable exchange core)");
            exchange = builder.defineInRange("exchange", defExchange, 0, Integer.MAX_VALUE);
            builder.comment("Allow wand upgrading by putting the wand together with a wand core in a crafting grid.");
            upgradeable = builder.define("upgradeable", defUpgradeable);
            builder.pop();

            wandProperties.put(registryName, this);
        }

        public int getDurability() {
            return durability == null ? -1 : durability.get();
        }

        public int getLimit() {
            return limit == null ? 0 : limit.get();
        }

        public int getAngel() {
            return angel == null ? 0 : angel.get();
        }

        public int getDestruction() {
            return destruction == null ? 0 : destruction.get();
        }

        public int getExchange() {
            return exchange == null ? 0 : exchange.get();
        }

        public boolean isUpgradeable() {
            return upgradeable != null && upgradeable.get();
        }
    }

    static {
        final IConfigBuilder builder = Services.PLATFORM.createConfigBuilder();

        builder.comment("This is the Server config for ConstructionWand.",
                "If you're not familiar with Forge's new split client/server config, let me explain:",
                "Client config is stored in the /config folder and only contains client specific settings like graphics and keybinds.",
                "Mod behavior is configured in the Server config, which is world-specific and thus located",
                "in the /saves/myworld/serverconfig folder. If you want to change the serverconfig for all",
                "new worlds, copy the config files in the /defaultconfigs folder.");

        // NOTE: WAND_GOLD previously had no entry here at all, which made ConfigServer.getWandProperties()
        // fall back to WandProperties.DEFAULT (upgradeable = false). That silently prevented Gold Wand from
        // ever accepting ANY core (Angel/Destruction included), not just Exchange. Added below.
        new WandProperties(builder, ModItems.WAND_WOOD, WandTier.WOOD.durability(), 5, 0, 0, 0, false);
        new WandProperties(builder, ModItems.WAND_STONE, WandTier.STONE.durability(), 9, 0, 0, 0, false);
        new WandProperties(builder, ModItems.WAND_COPPER, WandTier.COPPER.durability(), 18, 1, 5, 5, true);
        new WandProperties(builder, ModItems.WAND_IRON, WandTier.IRON.durability(), 27, 2, 9, 9, true);
        new WandProperties(builder, ModItems.WAND_GOLD, WandTier.GOLD.durability(), 60, 5, 15, 15, true);
        new WandProperties(builder, ModItems.WAND_DIAMOND, WandTier.DIAMOND.durability(), 128, 8, 25, 25, true);
        new WandProperties(builder, ModItems.WAND_NETHERITE, WandTier.NETHERITE.durability(), 512, 15, 75, 75, true);
        new WandProperties(builder, ModItems.WAND_INFINITY, 0, 1024, 16, 81, 81, true);

        builder.push("misc");
        builder.comment("Block limit for Infinity Wand used in creative mode");
        LIMIT_CREATIVE = builder.defineInRange("InfinityWandCreative", 2048, 1, Integer.MAX_VALUE);
        builder.comment("Maximum placement range (0: unlimited). Affects all wands and is meant for lag prevention, not game balancing.");
        MAX_RANGE = builder.defineInRange("MaxRange", 100, 0, Integer.MAX_VALUE);
        builder.comment("Number of operations that can be undone");
        UNDO_HISTORY = builder.defineInRange("UndoHistory", 3, 0, Integer.MAX_VALUE);
        builder.comment("Place blocks below you while falling > 10 blocks with angel core (Can be used to save you from drops/the void)");
        ANGEL_FALLING = builder.define("AngelFalling", false);
        builder.comment("Energy a wand with the Energy Upgrade can store (FE)");
        ENERGY_CAPACITY = builder.defineInRange("EnergyCapacity", 100000, 1, Integer.MAX_VALUE);
        builder.comment("Energy a wand with the Energy Upgrade uses for every block it places or exchanges (FE)");
        ENERGY_PER_BLOCK = builder.defineInRange("EnergyPerBlock", 100, 0, Integer.MAX_VALUE);
        builder.comment("Items dropped by the blocks the Destruction core breaks go to the inventory (or to an active Void Sack).",
                "When false the blocks are destroyed without dropping anything. Creative players never get the drops.");
        DESTRUCTION_GIVES_DROPS = builder.define("DestructionGivesDrops", true);
        builder.comment("Blocks to treat equally when in Similar mode. Enter block IDs seperated by ;");
        SIMILAR_BLOCKS = builder.defineList("SimilarBlocks", Arrays.asList(SIMILAR_BLOCKS_DEFAULT));
        builder.pop();

        builder.push("tileentity");
        builder.comment("White/Blacklist for Tile Entities. Allow/Prevent blocks with TEs from being placed by wand.",
                "You can either add block ids like minecraft:chest or mod ids like minecraft");
        TE_LIST = builder.defineList("TEList", Arrays.asList(TE_LIST_DEFAULT));
        builder.comment("If set to TRUE, treat TEList as a whitelist, otherwise blacklist");
        TE_WHITELIST = builder.define("TEWhitelist", false);
        builder.pop();

        SPEC = builder.build();
    }
}