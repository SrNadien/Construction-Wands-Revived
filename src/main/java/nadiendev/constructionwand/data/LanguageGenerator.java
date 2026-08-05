package nadiendev.constructionwand.data;

import nadiendev.constructionwand.ConstructionWand;
import nadiendev.constructionwand.items.ModItems;
import net.minecraft.data.PackOutput;
import net.neoforged.neoforge.common.data.LanguageProvider;

/*
*Language Provider
* By NadienDev
*/
public class LanguageGenerator extends LanguageProvider {

    public LanguageGenerator(PackOutput packOutput) {
        super(packOutput, ConstructionWand.MODID, "en_us");
    }

    @Override
    protected void addTranslations() {
        add("itemGroup.constructionwand", "Construction Wands Revived");
        add("itemGroup.constructionwand.construction_wand_tab", "Construction Wands Revived");

        addItem(ModItems.WAND_STONE, "Stone Wand");
        addItem(ModItems.WAND_IRON, "Iron Wand");
        addItem(ModItems.WAND_GOLD, "Gold Wand");
        addItem(ModItems.WAND_DIAMOND, "Diamond Wand");
        addItem(ModItems.WAND_NETHERITE, "Netherite Wand");
        addItem(ModItems.WAND_INFINITY, "Infinity Wand");
        addItem(ModItems.CORE_ANGEL, "Angel Wand Core");
        addItem(ModItems.CORE_DESTRUCTION, "Destruction Wand Core");
        addItem(ModItems.CORE_EXCHANGE, "Exchange Wand Core");
        add("advancement.constructionwand.gold_wand.title", "Gold Wand");
        add("advancement.constructionwand.gold_wand.desc", "Obtain a Gold Wand");
        add("advancement.constructionwand.core_exchange.title", "Exchange Core");
        add("advancement.constructionwand.core_exchange.desc", "Obtain an Exchange Wand Core");
        add("constructionwand.option.cores.constructionwand:core_exchange", "§bExchange Core");
        add("constructionwand.option.cores.constructionwand:core_exchange.desc", "Replaces blocks on the side facing you with the block in your offhand");
        add("constructionwand.description.core_exchange", "The exchange core replaces blocks on the side facing you (or a row of blocks) with the block held in your offhand. Maximum number of blocks depends on wand tier. Restrictions work just like with the Construction core.");
        add("constructionwand.message.exchange_selected", "Selected: %s");
        add("constructionwand.message.exchange_invalid", "That block can't be selected");
        add("constructionwand.message.exchange_none_selected", "No block selected — press Numpad 7 looking at a block");
        add("constructionwand.message.exchange_no_target", "Look at a block first");
        add("key.constructionwand.exchange_select", "Select Exchange Block");

 add("advancement.constructionwand.root.title", "Construction Wands Revived");
add("advancement.constructionwand.root.desc", "Get your first wand");
add("advancement.constructionwand.stone_wand.title", "Stone Wand");
add("advancement.constructionwand.stone_wand.desc", "Obtain a Stone Wand");
add("advancement.constructionwand.iron_wand.title", "Iron Wand");
add("advancement.constructionwand.iron_wand.desc", "Obtain an Iron Wand");
add("advancement.constructionwand.diamond_wand.title", "Diamond Wand");
add("advancement.constructionwand.diamond_wand.desc", "Obtain a Diamond Wand");
add("advancement.constructionwand.netherite_wand.title", "Netherite Wand");
add("advancement.constructionwand.netherite_wand.desc", "Obtain a Netherite Wand");
add("advancement.constructionwand.infinity_wand.title", "Infinity Wand");
add("advancement.constructionwand.infinity_wand.desc", "Obtain the Infinity Wand");
add("advancement.constructionwand.core_angel.title", "Angel Core");
add("advancement.constructionwand.core_angel.desc", "Obtain an Angel Wand Core");
add("advancement.constructionwand.core_destruction.title", "Destruction Core");
add("advancement.constructionwand.core_destruction.desc", "Obtain a Destruction Wand Core");
add("advancement.constructionwand.void_sack.desc", "Void Sack");
add("advancement.constructionwand.void_sack.title", "Store items from the Destruction Core inside the Void Sack or linked containers");


        add("constructionwand.tooltip.blocks", "Max. %d blocks");
        add("constructionwand.tooltip.shift", "Press [SHIFT]");
        add("constructionwand.tooltip.cores", "Wand cores:");
        add("constructionwand.tooltip.core_tip", "Combine the core with your wand in a crafting grid");

        add("constructionwand.option.cores", "");
        addCoreInfo("default", "Construction Core", "Extend your building on the side facing you");
        addCoreInfo("core_angel", "§6Angel Core", "Place behind blocks and in mid air");
        addCoreInfo("core_destruction", "§cDestruction Core", "Destroys blocks on the side facing you");

        add("constructionwand.option.lock", "Restriction: ");
        add("constructionwand.option.lock.horizontal", "§aLeft/Right");
        add("constructionwand.option.lock.horizontal.desc", "Build a horizontal column in front of the original block");
        add("constructionwand.option.lock.vertical", "§aUp/Down");
        add("constructionwand.option.lock.vertical.desc", "Build a vertical column in front of the original block");
        add("constructionwand.option.lock.northsouth", "§6North/South");
        add("constructionwand.option.lock.northsouth.desc", "Build a row in N/S direction on top of the original block");
        add("constructionwand.option.lock.eastwest", "§6East/West");
        add("constructionwand.option.lock.eastwest.desc", "Build a row in E/W direction on top of the original block");
        add("constructionwand.option.lock.nolock", "§cNone");
        add("constructionwand.option.lock.nolock.desc", "Extend from any side of the original block");

        add("constructionwand.option.direction", "Direction: ");
        add("constructionwand.option.direction.target", "§6Target");
        add("constructionwand.option.direction.target.desc", "Place blocks with same direction as target block");
        add("constructionwand.option.direction.player", "§aPlayer");
        add("constructionwand.option.direction.player.desc", "Place blocks facing the player");

        add("constructionwand.option.replace", "Replacement: ");
        add("constructionwand.option.replace.yes", "§aYes");
        add("constructionwand.option.replace.yes.desc", "Replace certain blocks like fluids, snow and tallgrass");
        add("constructionwand.option.replace.no", "§cNo");
        add("constructionwand.option.replace.no.desc", "Don't replace blocks");

        add("constructionwand.option.match", "Matching: ");
        add("constructionwand.option.match.exact", "§aExact");
        add("constructionwand.option.match.exact.desc", "Only extend blocks that are exactly the same");
        add("constructionwand.option.match.similar", "§6Similar");
        add("constructionwand.option.match.similar.desc", "Treat similar blocks (dirt/grass types) equally");
        add("constructionwand.option.match.any", "§cAny");
        add("constructionwand.option.match.any.desc", "Extend any block");

        add("constructionwand.option.random", "Random: ");
        add("constructionwand.option.random.yes", "§aYes");
        add("constructionwand.option.random.yes.desc", "Place random blocks present in your hotbar");
        add("constructionwand.option.random.no", "§cNo");
        add("constructionwand.option.random.no.desc", "Don't randomize placed blocks");

        add("constructionwand.description.wand", "The %1$s can place up to %2$d blocks at the side of a building facing you and lasts %3$s.\n\nHold down %5$s and scroll to change placement restriction (Horizontal, Vertical, North/South, East/West, No lock).\n\nOpen the option screen with %6$s§9+Right click§0.\n\n§5§nUNDO§0§r\nHolding down §9Sneak+§0%4$s while looking at a blocks will show you the last blocks you placed with a green border around them. §9Sneak+§0%4$s§9+Right clicking§0 any of them will undo the operation, giving you all the items back. If you used the Destruction core, it will restore the blocks.\n\n§5§nCONTAINERS§0§r\nShulker boxes, bundles and many containers from other mods can provide building blocks for the wand.\n\n§5§nOFFHAND PRIORITY§0§r\nHaving blocks in your offhand will place them instead of the block you're looking at.");
        add("constructionwand.description.durability.limited", "for %d blocks");
        add("constructionwand.description.durability.unlimited", "forever");
        add("constructionwand.description.key.sneak", "Sneak");
        add("constructionwand.description.key.sneak_opt", "Sneak+%s");
        add("constructionwand.description.core", "§5§nINSTALLATION§0§r\nPut your new core together with your wand in a crafting grid to install it. To switch between cores, hold down %s and left click empty space with your wand or use the option screen.");
        add("constructionwand.description.core_angel", "The angel core places a block on the opposite side of the block (or row of blocks) you are facing. Maximum distance depends on wand tier. Right click empty space to place a block in midair. To do that, you'll need to have the block you want to place in your offhand.");
        add("constructionwand.description.core_destruction", "The destruction core destroys blocks (no tile entities) on the side facing you. Maximum number of blocks depends on wand tier. Destroyed blocks disappear into the void, you can use the undo feature if you've made a mistake.");

        add("item.constructionwand.void_sack", "Void Sack");
        add("item.constructionwand.void_sack.active", "§aActive §7(press §6%1$s§7 to toggle)");
        add("item.constructionwand.void_sack.inactive", "§7Inactive §7(press §6%1$s§7 to toggle)");
        add("item.constructionwand.void_sack.linked", "Linked to: %d, %d, %d");
        add("item.constructionwand.void_sack.no_link", "Not linked to any container");
        add("item.constructionwand.void_sack.sending", "Sending to container");
        add("item.constructionwand.void_sack.storing", "Storing internally");
        add("item.constructionwand.void_sack.slots_used", "Slots used: %d / %d");
        add("item.constructionwand.void_sack.linked_msg", "Void Sack linked to %d, %d, %d");
        add("item.constructionwand.void_sack.activated", "Void Sack activated");
        add("item.constructionwand.void_sack.deactivated", "Void Sack deactivated");
        add("gui.constructionwand.void_sack.toggle_tooltip", "Toggle: send to container / store internally");
        add("gui.constructionwand.void_sack.sending", "Sending →");
        add("gui.constructionwand.void_sack.storing", "Storing");
        add("key.constructionwand.void_sack_toggle", "Toggle Void Sack");
        add("constructionwand.description.void_sack", "The Void Sack intercepts items you pick up and stores them in its 4×4 internal inventory.\n\n§5§nACTIVATION§0§r\nPress %1$s to activate or deactivate the sack. While inactive, items go to your normal inventory as usual.\n\n§5§nLINKING A CONTAINER§0§r\nRight-click any container (chest, barrel, shulker...) while holding the sack to link it. Once linked, items go to that container first when the sack is active. If the container is full, the overflow goes to the sack's internal storage.\n\n§5§nTOGGLE MODE§0§r\nPress %1$s to switch between §aSending§0 (items → container) and §eStoring§0 (items → internal slots). You can also click the toggle button inside the sack GUI.");
        add("stat.constructionwand.use_wand", "Blocks placed using Wand");
        add("key.category.constructionwand.category", "Construction Wands Revived");
        add("constructionwand.undo.success", "Wand action undone");
        add("constructionwand.undo.nothing", "Nothing to undo");
        add("constructionwand.networking.wand_undo.failed", "Failed to undo wand action");
        add("key.constructionwand.wand_option", "Wand Option");
        add("key.constructionwand.wand_undo", "Undo Wand");
    }

    private void addCoreInfo(String core, String name, String desc) {
        add("constructionwand.option.cores.constructionwand:" + core, name);
        add("constructionwand.option.cores.constructionwand:" + core + ".desc", desc);
    }

    public static class ESAR extends LanguageProvider {
        public ESAR(PackOutput packOutput) {
            super(packOutput, ConstructionWand.MODID, "es_ar");
        }
        @Override
        protected void addTranslations() {
        add("itemGroup.constructionwand", "Varitas de Construcción Revividas");
        add("itemGroup.constructionwand.construction_wand_tab", "Varitas de Construcción Revividas");

        addItem(ModItems.WAND_STONE, "Varita de Piedra");
        addItem(ModItems.WAND_IRON, "Varita de Hierro");
        addItem(ModItems.WAND_GOLD, "Varita de Oro");
        addItem(ModItems.WAND_DIAMOND, "Varita de Diamante");
        addItem(ModItems.WAND_NETHERITE, "Varita de netherita");
        addItem(ModItems.WAND_INFINITY, "Varita del Infinito");
        addItem(ModItems.CORE_ANGEL, "Núcleo de Varita Angelical");
        addItem(ModItems.CORE_DESTRUCTION, "Núcleo de Varita de Destrucción");
        addItem(ModItems.CORE_EXCHANGE, "Núcleo de Varita de Intercambio");
        add("advancement.constructionwand.gold_wand.title", "Varita de Oro");
        add("advancement.constructionwand.gold_wand.desc", "Obtené una Varita de Oro");
        add("advancement.constructionwand.core_exchange.title", "Núcleo de Intercambio");
        add("advancement.constructionwand.core_exchange.desc", "Obtené un Núcleo de Varita de Intercambio");
        add("constructionwand.option.cores.constructionwand:core_exchange", "§bNúcleo de Intercambio");
        add("constructionwand.option.cores.constructionwand:core_exchange.desc", "Reemplaza bloques en el lado que te mira por el bloque que tenés en la mano secundaria");
        add("constructionwand.description.core_exchange", "El núcleo de intercambio reemplaza los bloques en el lado que estás mirando (o una fila de bloques) por el bloque que tenés en la mano secundaria. El número máximo de bloques depende del nivel de la varita. Las restricciones funcionan igual que con el núcleo de Construcción.");
        add("constructionwand.message.exchange_selected", "Seleccionado: %s");
        add("constructionwand.message.exchange_invalid", "Ese bloque no se puede seleccionar");
        add("constructionwand.message.exchange_none_selected", "No hay bloque seleccionado — presioná Numpad 7 mirando un bloque");
        add("constructionwand.message.exchange_no_target", "Primero mirá un bloque");
        add("key.constructionwand.exchange_select", "Seleccionar bloque de Intercambio");
       
         add("advancement.constructionwand.root.title", "Varitas de Construcción Revividas");
add("advancement.constructionwand.root.desc", "Obtené tu primera varita");
add("advancement.constructionwand.stone_wand.title", "Varita de Piedra");
add("advancement.constructionwand.stone_wand.desc", "Obtené una Varita de Piedra");
add("advancement.constructionwand.iron_wand.title", "Varita de Hierro");
add("advancement.constructionwand.iron_wand.desc", "Obtené una Varita de Hierro");
add("advancement.constructionwand.diamond_wand.title", "Varita de Diamante");
add("advancement.constructionwand.diamond_wand.desc", "Obtené una Varita de Diamante");
add("advancement.constructionwand.netherite_wand.title", "Varita de Netherita");
add("advancement.constructionwand.netherite_wand.desc", "Obtené una Varita de Netherita");
add("advancement.constructionwand.infinity_wand.title", "Varita del Infinito");
add("advancement.constructionwand.infinity_wand.desc", "Obtené la Varita del Infinito");
add("advancement.constructionwand.core_angel.title", "Núcleo Angelical");
add("advancement.constructionwand.core_angel.desc", "Obtené un Núcleo de Varita Angelical");
add("advancement.constructionwand.core_destruction.title", "Núcleo de Destrucción");
add("advancement.constructionwand.core_destruction.desc", "Obtené un Núcleo de Varita de Destrucción");

        add("constructionwand.tooltip.blocks", "Máx. %d bloques");
        add("constructionwand.tooltip.shift", "Presiona [MAYÚS]");
        add("constructionwand.tooltip.cores", "Núcleos de varita:");
        add("constructionwand.tooltip.core_tip", "Combina el núcleo con tu varita en una rejilla de crafteo");
        add("constructionwand.option.cores", "");
        add("constructionwand.option.cores.constructionwand:default", "Núcleo de Construcción");
        add("constructionwand.option.cores.constructionwand:default.desc", "Extiende tu construcción hacia el lado que te mira");
        add("constructionwand.option.cores.constructionwand:core_angel", "§6Núcleo Angelical");
        add("constructionwand.option.cores.constructionwand:core_angel.desc", "Coloca bloques detrás, incluso en el aire");
        add("constructionwand.option.cores.constructionwand:core_destruction", "§cNúcleo de Destrucción");
        add("constructionwand.option.cores.constructionwand:core_destruction.desc", "Destruye bloques en el lado que te mira");
        add("constructionwand.option.lock", "Restricción: ");
        add("constructionwand.option.lock.horizontal", "§aIzquierda/Derecha");
        add("constructionwand.option.lock.horizontal.desc", "Construye una columna horizontal frente al bloque original");
        add("constructionwand.option.lock.vertical", "§aArriba/Abajo");
        add("constructionwand.option.lock.vertical.desc", "Construye una columna vertical frente al bloque original");
        add("constructionwand.option.lock.northsouth", "§6Norte/Sur");
        add("constructionwand.option.lock.northsouth.desc", "Construye una fila en dirección N/S sobre el bloque original");
        add("constructionwand.option.lock.eastwest", "§6Este/Oeste");
        add("constructionwand.option.lock.eastwest.desc", "Construye una fila en dirección E/O sobre el bloque original");
        add("constructionwand.option.lock.nolock", "§cNinguna");
        add("constructionwand.option.lock.nolock.desc", "Extender desde cualquier lado del bloque original");
        add("constructionwand.option.direction", "Dirección: ");
        add("constructionwand.option.direction.target", "§6Objetivo");
        add("constructionwand.option.direction.target.desc", "Coloca bloques con la misma orientación que el bloque objetivo");
        add("constructionwand.option.direction.player", "§aJugador");
        add("constructionwand.option.direction.player.desc", "Coloca bloques orientados hacia el jugador");
        add("constructionwand.option.replace", "Reemplazo: ");
        add("constructionwand.option.replace.yes", "§aSí");
        add("constructionwand.option.replace.yes.desc", "Reemplaza ciertos bloques como fluidos, nieve y hierba alta");
        add("constructionwand.option.replace.no", "§cNo");
        add("constructionwand.option.replace.no.desc", "No reemplazar bloques");
        add("constructionwand.option.match", "Coincidencia: ");
        add("constructionwand.option.match.exact", "§aExacta");
        add("constructionwand.option.match.exact.desc", "Extiende solo bloques que sean exactamente iguales");
        add("constructionwand.option.match.similar", "§6Similar");
        add("constructionwand.option.match.similar.desc", "Trata bloques similares (tierra/pasto) como iguales");
        add("constructionwand.option.match.any", "§cCualquiera");
        add("constructionwand.option.match.any.desc", "Extiende cualquier bloque");
        add("constructionwand.option.random", "Aleatorio: ");
        add("constructionwand.option.random.yes", "§aSí");
        add("constructionwand.option.random.yes.desc", "Coloca bloques al azar presentes en tu barra rápida");
        add("constructionwand.option.random.no", "§cNo");
        add("constructionwand.option.random.no.desc", "No aleatorizar los bloques colocados");
        add("constructionwand.description.wand", "La %1$s puede colocar hasta %2$d bloques al lado de una construcción que te mire y dura %3$s.\n\nMantén presionado %5$s y rota la rueda para cambiar la restricción de colocación (Horizontal, Vertical, Norte/Sur, Este/Oeste, Sin restricción).\n\nAbre la pantalla de opciones con %6$s§9+Clic derecho§0.\n\n§5§nDESHACER§0§r\nManteniendo presionado §9Agacharse+§0%4$s mientras mirás un bloque mostrará los últimos bloques que colocaste con un borde verde alrededor. §9Agacharse+§0%4$s§9+Clic derecho§0 sobre cualquiera de ellos deshará la operación, devolviéndote todos los objetos. Si usaste el núcleo de Destrucción, restaurará los bloques.\n\n§5§nCONTENEDORES§0§r\nShulker boxes, bundles y muchos contenedores de otros mods pueden proveer bloques de construcción para la varita.\n\n§5§nPRIORIDAD MAIN/SECUNDARIA§0§r\nTener bloques en la mano secundaria hará que se coloquen ellos en vez del bloque en el que estás mirando.");
        add("constructionwand.description.durability.limited", "por %d bloques");
        add("constructionwand.description.durability.unlimited", "para siempre");
        add("constructionwand.description.key.sneak", "Agacharse");
        add("constructionwand.description.key.sneak_opt", "Agacharse+%s");
        add("constructionwand.description.core", "§5§nINSTALACIÓN§0§r\nPon tu nuevo núcleo junto con tu varita en una rejilla de crafteo para instalarlo. Para cambiar entre núcleos, mantén presionado %s y haz clic izquierdo en el aire con tu varita o usa la pantalla de opciones.");
        add("constructionwand.description.core_angel", "El núcleo angelical coloca un bloque en el lado opuesto del bloque (o fila de bloques) al que estás mirando. La distancia máxima depende del nivel de la varita. Clic derecho en el aire para colocar un bloque en el aire. Para eso, necesitarás tener el bloque que quieras colocar en la mano secundaria.");
        add("constructionwand.description.core_destruction", "El núcleo de destrucción destruye bloques (no entidades de bloque) en el lado que estás mirando. El número máximo de bloques depende del nivel de la varita. Los bloques destruidos desaparecen en el vacío; podés usar la función deshacer si te equivocás.");
        add("item.constructionwand.void_sack", "Bolsa del Vacío");
        add("item.constructionwand.void_sack.active", "§aActiva §7(presioná §6%1$s§7 para cambiar)");
        add("item.constructionwand.void_sack.inactive", "§7Inactiva §7(presioná §6%1$s§7 para cambiar)");
        add("item.constructionwand.void_sack.linked", "Vinculada a: %d, %d, %d");
        add("item.constructionwand.void_sack.no_link", "Sin container vinculado");
        add("item.constructionwand.void_sack.sending", "Enviando al container");
        add("item.constructionwand.void_sack.storing", "Guardando internamente");
        add("item.constructionwand.void_sack.slots_used", "Espacios usados: %d / %d");
        add("item.constructionwand.void_sack.linked_msg", "Bolsa vinculada a %d, %d, %d");
        add("item.constructionwand.void_sack.activated", "Bolsa del Vacío activada");
        add("item.constructionwand.void_sack.deactivated", "Bolsa del Vacío desactivada");
        add("gui.constructionwand.void_sack.toggle_tooltip", "Cambiar: enviar al container / guardar internamente");
        add("gui.constructionwand.void_sack.sending", "Enviando →");
        add("gui.constructionwand.void_sack.storing", "Guardando");
        add("key.constructionwand.void_sack_toggle", "Alternar Bolsa del Vacío");
        add("constructionwand.description.void_sack", "La Bolsa del Vacío intercepta los ítems que recogés y los guarda en su inventario interno de 4×4.\n\n§5§nACTIVACIÓN§0§r\nPresioná %1$s para activar o desactivar la bolsa. Mientras está inactiva, los ítems van a tu inventario normal.\n\n§5§nVINCULAR UN CONTAINER§0§r\nHacé clic derecho sobre cualquier container (cofre, barril, shulker...) con la bolsa en mano para vincularlo. Una vez vinculada, los ítems van primero al container cuando la bolsa está activa. Si el container está lleno, el exceso va al almacenamiento interno de la bolsa.\n\n§5§nMODO§0§r\nPresioná %1$s para alternar entre §aEnviando§0 (ítems → container) y §eGuardando§0 (ítems → espacios internos). También podés usar el botón de cambio dentro de la GUI.");
        add("stat.constructionwand.use_wand", "Bloques colocados usando la Varita");
        add("advancement.constructionwand.void_sack.desc", "Bolsa del Vacío");
        add("advancement.constructionwand.void_sack.title", "Guarda items del Destruction Core dentro de la Bolsa del Vacío o en contenedores vinculados");
        add("key.category.constructionwand.category", "Construction Wands Revived");
        add("constructionwand.undo.success", "Acción de la varita deshecha");
        add("constructionwand.undo.nothing", "No hay nada para deshacer");
        add("constructionwand.networking.wand_undo.failed", "No se pudo deshacer la acción de la varita");
        add("key.constructionwand.wand_option", "Opción de varita");
        add("key.constructionwand.wand_undo", "Deshacer varita");
        }
    }

    public static class ESCL extends LanguageProvider {
        public ESCL(PackOutput packOutput) {
            super(packOutput, ConstructionWand.MODID, "es_cl");
        }
        @Override
        protected void addTranslations() {
        add("itemGroup.constructionwand", "Varitas de Construcción Revividas");
        add("itemGroup.constructionwand.construction_wand_tab", "Varitas de Construcción Revividas");

        addItem(ModItems.WAND_STONE, "Varita de piedra");
        addItem(ModItems.WAND_IRON, "Varita de hierro");
        addItem(ModItems.WAND_GOLD, "Varita de Oro");
        addItem(ModItems.WAND_DIAMOND, "Varita de diamante");
        addItem(ModItems.WAND_NETHERITE, "Varita de netherita");
        addItem(ModItems.WAND_INFINITY, "Varita infinita");
        addItem(ModItems.CORE_ANGEL, "Núcleo de varita angelical");
        addItem(ModItems.CORE_DESTRUCTION, "Núcleo de varita de destrucción");
        addItem(ModItems.CORE_EXCHANGE, "Núcleo de Varita de Intercambio");
        add("advancement.constructionwand.gold_wand.title", "Varita de Oro");
        add("advancement.constructionwand.gold_wand.desc", "Obtén una Varita de Oro");
        add("advancement.constructionwand.core_exchange.title", "Núcleo de Intercambio");
        add("advancement.constructionwand.core_exchange.desc", "Obtén un Núcleo de Varita de Intercambio");
        add("constructionwand.option.cores.constructionwand:core_exchange", "§bNúcleo de Intercambio");
        add("constructionwand.option.cores.constructionwand:core_exchange.desc", "Reemplaza bloques en el lado que te mira por el bloque que tenés en la mano secundaria");
        add("constructionwand.description.core_exchange", "El núcleo de intercambio reemplaza los bloques en el lado que estás mirando (o una fila de bloques) por el bloque que tenés en la mano secundaria. El número máximo de bloques depende del nivel de la varita. Las restricciones funcionan igual que con el núcleo de Construcción.");
        add("constructionwand.message.exchange_selected", "Seleccionado: %s");
        add("constructionwand.message.exchange_invalid", "Ese bloque no se puede seleccionar");
        add("constructionwand.message.exchange_none_selected", "No hay bloque seleccionado — presioná Numpad 7 mirando un bloque");
        add("constructionwand.message.exchange_no_target", "Primero mirá un bloque");
        add("key.constructionwand.exchange_select", "Seleccionar bloque de Intercambio");

        add("constructionwand.tooltip.blocks", "%d bloques máx.");
        add("constructionwand.tooltip.shift", "Presiona [MAYÚS]");
        add("constructionwand.tooltip.cores", "Núcleos de varita:");
        add("constructionwand.tooltip.core_tip", "Combina el núcleo con tu varita en una cuadrícula de fabricación");
        add("constructionwand.option.cores", "");
        add("constructionwand.option.cores.constructionwand:default", "Núcleo de construcción");
        add("constructionwand.option.cores.constructionwand:default.desc", "Extiende tu construcción hacia el lado que estás mirando");
        add("constructionwand.option.cores.constructionwand:core_angel", "§6Núcleo angelical");
        add("constructionwand.option.cores.constructionwand:core_angel.desc", "Coloca bloques detrás, incluso en el aire");
        add("constructionwand.option.cores.constructionwand:core_destruction", "§cNúcleo de destrucción");
        add("constructionwand.option.cores.constructionwand:core_destruction.desc", "Destruye bloques en el lado que estás mirando");
        add("constructionwand.option.lock", "Restricción: ");
        add("constructionwand.option.lock.horizontal", "§aIzquierda/Derecha");
        add("constructionwand.option.lock.horizontal.desc", "Construye una columna horizontal frente al bloque original");
        add("constructionwand.option.lock.vertical", "§aArriba/Abajo");
        add("constructionwand.option.lock.vertical.desc", "Construye una columna vertical frente al bloque original");
        add("constructionwand.option.lock.northsouth", "§6Norte/Sur");
        add("constructionwand.option.lock.northsouth.desc", "Construye una fila en dirección N/S sobre el bloque original");
        add("constructionwand.option.lock.eastwest", "§6Este/Oeste");
        add("constructionwand.option.lock.eastwest.desc", "Construye una fila en dirección E/O sobre el bloque original");
        add("constructionwand.option.lock.nolock", "§cNinguna");
        add("constructionwand.option.lock.nolock.desc", "Extiende desde cualquier lado del bloque original");
        add("constructionwand.option.direction", "Dirección: ");
        add("constructionwand.option.direction.target", "§6Objetivo");
        add("constructionwand.option.direction.target.desc", "Coloca bloques con la misma orientación que el bloque objetivo");
        add("constructionwand.option.direction.player", "§aJugador");
        add("constructionwand.option.direction.player.desc", "Coloca bloques orientados hacia el jugador");
        add("constructionwand.option.replace", "Reemplazo: ");
        add("constructionwand.option.replace.yes", "§aSí");
        add("constructionwand.option.replace.yes.desc", "Reemplaza ciertos bloques como fluidos, nieve y hierba alta");
        add("constructionwand.option.replace.no", "§cNo");
        add("constructionwand.option.replace.no.desc", "No reemplazar bloques");
        add("constructionwand.option.match", "Coincidencia: ");
        add("constructionwand.option.match.exact", "§aExacta");
        add("constructionwand.option.match.exact.desc", "Extiende solo bloques que sean exactamente iguales");
        add("constructionwand.option.match.similar", "§6Similar");
        add("constructionwand.option.match.similar.desc", "Trata bloques similares (tierra/pasto) como iguales");
        add("constructionwand.option.match.any", "§cCualquiera");
        add("constructionwand.option.match.any.desc", "Extiende cualquier bloque");
        add("constructionwand.option.random", "Aleatorio: ");
        add("constructionwand.option.random.yes", "§aSí");
        add("constructionwand.option.random.yes.desc", "Coloca bloques al azar presentes en tu barra de acceso rápido");
        add("constructionwand.option.random.no", "§cNo");
        add("constructionwand.option.random.no.desc", "No aleatorizar los bloques colocados");
        add("constructionwand.description.wand", "La %1$s puede colocar hasta %2$d bloques al lado de una construcción que estés mirando y dura %3$s.\n\nMantén presionado %5$s y gira la rueda para cambiar la restricción de colocación (Horizontal, Vertical, Norte/Sur, Este/Oeste, Sin restricción).\n\nAbre la pantalla de opciones con %6$s§9 + Clic derecho§0.\n\n§5§nDESHACER§0§r\nManteniendo presionado §9Agacharse + §0%4$s mientras miras un bloque mostrará los últimos bloques que colocaste resaltados con un borde verde. §9Agacharse + §0%4$s§9 + Clic derecho§0 sobre cualquiera de ellos deshará la operación, devolviéndote todos los objetos. Si usaste el núcleo de destrucción, restaurará los bloques.\n\n§5§nCONTENEDORES§0§r\nLas cajas de shulker, sacos y muchos contenedores de otros mods pueden proveer bloques de construcción para la varita.\n\n§5§nPRIORIDAD DE MANO SECUNDARIA§0§r\nTener bloques en la mano secundaria hará que se coloquen ellos en vez del bloque al que estás mirando.");
        add("constructionwand.description.durability.limited", "para %d bloques");
        add("constructionwand.description.durability.unlimited", "para siempre");
        add("constructionwand.description.key.sneak", "Agacharse");
        add("constructionwand.description.key.sneak_opt", "Agacharse + %s");
        add("constructionwand.description.core", "§5§nINSTALACIÓN§0§r\nColoca tu nuevo núcleo junto con tu varita en una cuadrícula de fabricación para instalarlo. Para cambiar entre núcleos, mantén presionado %s y haz clic izquierdo en un espacio vacío con tu varita o usa la pantalla de opciones.");
        add("constructionwand.description.core_angel", "El núcleo angelical coloca un bloque en el lado opuesto del bloque (o fila de bloques) que estás mirando. La distancia máxima depende del nivel de la varita. Haz clic derecho en un espacio vacío para colocar un bloque en el aire. Para hacer eso, necesitarás tener el bloque que deseas colocar en tu mano secundaria.");
        add("constructionwand.description.core_destruction", "El núcleo de destrucción destruye bloques (no entidades de bloque) en el lado que estás mirando. El número máximo de bloques depende del nivel de la varita. Los bloques destruidos desaparecen en el vacío, puedes usar la función de deshacer si cometiste un error.");
        add("stat.constructionwand.use_wand", "Bloques colocados usando la varita");
        add("advancement.constructionwand.void_sack.desc", "Bolsa del Vacío");
        add("advancement.constructionwand.void_sack.title", "Guarda items del Destruction Core dentro de la Bolsa del Vacío o en contenedores vinculados");
        add("key.category.constructionwand.category", "Construction Wands Revived");
        add("constructionwand.undo.success", "Acción de la varita deshecha");
        add("constructionwand.undo.nothing", "No hay nada para deshacer");
        add("constructionwand.networking.wand_undo.failed", "No se pudo deshacer la acción de la varita");
        add("key.constructionwand.wand_option", "Opción de varita");
        add("key.constructionwand.wand_undo", "Deshacer varita");
        }
    }

    public static class ESCO extends LanguageProvider {
        public ESCO(PackOutput packOutput) {
            super(packOutput, ConstructionWand.MODID, "es_co");
        }
        @Override
        protected void addTranslations() {
        add("itemGroup.constructionwand", "Varitas de Construcción Revividas");
        add("itemGroup.constructionwand.construction_wand_tab", "Varitas de Construcción Revividas");

        addItem(ModItems.WAND_STONE, "Varita de piedra");
        addItem(ModItems.WAND_IRON, "Varita de hierro");
        addItem(ModItems.WAND_GOLD, "Varita de Oro");
        addItem(ModItems.WAND_DIAMOND, "Varita de diamante");
        addItem(ModItems.WAND_NETHERITE, "Varita de netherita");
        addItem(ModItems.WAND_INFINITY, "Varita infinita");
        addItem(ModItems.CORE_ANGEL, "Núcleo de varita angelical");
        addItem(ModItems.CORE_DESTRUCTION, "Núcleo de varita de destrucción");
        addItem(ModItems.CORE_EXCHANGE, "Núcleo de Varita de Intercambio");
        add("advancement.constructionwand.gold_wand.title", "Varita de Oro");
        add("advancement.constructionwand.gold_wand.desc", "Obtén una Varita de Oro");
        add("advancement.constructionwand.core_exchange.title", "Núcleo de Intercambio");
        add("advancement.constructionwand.core_exchange.desc", "Obtén un Núcleo de Varita de Intercambio");
        add("constructionwand.option.cores.constructionwand:core_exchange", "§bNúcleo de Intercambio");
        add("constructionwand.option.cores.constructionwand:core_exchange.desc", "Reemplaza bloques en el lado que te mira por el bloque que tenés en la mano secundaria");
        add("constructionwand.description.core_exchange", "El núcleo de intercambio reemplaza los bloques en el lado que estás mirando (o una fila de bloques) por el bloque que tenés en la mano secundaria. El número máximo de bloques depende del nivel de la varita. Las restricciones funcionan igual que con el núcleo de Construcción.");
        add("constructionwand.message.exchange_selected", "Seleccionado: %s");
        add("constructionwand.message.exchange_invalid", "Ese bloque no se puede seleccionar");
        add("constructionwand.message.exchange_none_selected", "No hay bloque seleccionado — presioná Numpad 7 mirando un bloque");
        add("constructionwand.message.exchange_no_target", "Primero mirá un bloque");
        add("key.constructionwand.exchange_select", "Seleccionar bloque de Intercambio");

        add("advancement.constructionwand.root.title", "Varitas de Construcción Revividas");
add("advancement.constructionwand.root.desc", "Obtené tu primera varita");
add("advancement.constructionwand.stone_wand.title", "Varita de Piedra");
add("advancement.constructionwand.stone_wand.desc", "Obtené una Varita de Piedra");
add("advancement.constructionwand.iron_wand.title", "Varita de Hierro");
add("advancement.constructionwand.iron_wand.desc", "Obtené una Varita de Hierro");
add("advancement.constructionwand.diamond_wand.title", "Varita de Diamante");
add("advancement.constructionwand.diamond_wand.desc", "Obtené una Varita de Diamante");
add("advancement.constructionwand.netherite_wand.title", "Varita de Netherita");
add("advancement.constructionwand.netherite_wand.desc", "Obtené una Varita de Netherita");
add("advancement.constructionwand.infinity_wand.title", "Varita del Infinito");
add("advancement.constructionwand.infinity_wand.desc", "Obtené la Varita del Infinito");
add("advancement.constructionwand.core_angel.title", "Núcleo Angelical");
add("advancement.constructionwand.core_angel.desc", "Obtené un Núcleo de Varita Angelical");
add("advancement.constructionwand.core_destruction.title", "Núcleo de Destrucción");
add("advancement.constructionwand.core_destruction.desc", "Obtené un Núcleo de Varita de Destrucción");

        add("constructionwand.tooltip.blocks", "%d bloques máx.");
        add("constructionwand.tooltip.shift", "Presiona [MAYÚS]");
        add("constructionwand.tooltip.cores", "Núcleos de varita:");
        add("constructionwand.tooltip.core_tip", "Combina el núcleo con tu varita en una cuadrícula de fabricación");
        add("constructionwand.option.cores", "");
        add("constructionwand.option.cores.constructionwand:default", "Núcleo de construcción");
        add("constructionwand.option.cores.constructionwand:default.desc", "Extiende tu construcción hacia el lado que estás mirando");
        add("constructionwand.option.cores.constructionwand:core_angel", "§6Núcleo angelical");
        add("constructionwand.option.cores.constructionwand:core_angel.desc", "Coloca bloques detrás, incluso en el aire");
        add("constructionwand.option.cores.constructionwand:core_destruction", "§cNúcleo de destrucción");
        add("constructionwand.option.cores.constructionwand:core_destruction.desc", "Destruye bloques en el lado que estás mirando");
        add("constructionwand.option.lock", "Restricción: ");
        add("constructionwand.option.lock.horizontal", "§aIzquierda/Derecha");
        add("constructionwand.option.lock.horizontal.desc", "Construye una columna horizontal frente al bloque original");
        add("constructionwand.option.lock.vertical", "§aArriba/Abajo");
        add("constructionwand.option.lock.vertical.desc", "Construye una columna vertical frente al bloque original");
        add("constructionwand.option.lock.northsouth", "§6Norte/Sur");
        add("constructionwand.option.lock.northsouth.desc", "Construye una fila en dirección N/S sobre el bloque original");
        add("constructionwand.option.lock.eastwest", "§6Este/Oeste");
        add("constructionwand.option.lock.eastwest.desc", "Construye una fila en dirección E/O sobre el bloque original");
        add("constructionwand.option.lock.nolock", "§cNinguna");
        add("constructionwand.option.lock.nolock.desc", "Extiende desde cualquier lado del bloque original");
        add("constructionwand.option.direction", "Dirección: ");
        add("constructionwand.option.direction.target", "§6Objetivo");
        add("constructionwand.option.direction.target.desc", "Coloca bloques con la misma orientación que el bloque objetivo");
        add("constructionwand.option.direction.player", "§aJugador");
        add("constructionwand.option.direction.player.desc", "Coloca bloques orientados hacia el jugador");
        add("constructionwand.option.replace", "Reemplazo: ");
        add("constructionwand.option.replace.yes", "§aSí");
        add("constructionwand.option.replace.yes.desc", "Reemplaza ciertos bloques como fluidos, nieve y hierba alta");
        add("constructionwand.option.replace.no", "§cNo");
        add("constructionwand.option.replace.no.desc", "No reemplazar bloques");
        add("constructionwand.option.match", "Coincidencia: ");
        add("constructionwand.option.match.exact", "§aExacta");
        add("constructionwand.option.match.exact.desc", "Extiende solo bloques que sean exactamente iguales");
        add("constructionwand.option.match.similar", "§6Similar");
        add("constructionwand.option.match.similar.desc", "Trata bloques similares (tierra/pasto) como iguales");
        add("constructionwand.option.match.any", "§cCualquiera");
        add("constructionwand.option.match.any.desc", "Extiende cualquier bloque");
        add("constructionwand.option.random", "Aleatorio: ");
        add("constructionwand.option.random.yes", "§aSí");
        add("constructionwand.option.random.yes.desc", "Coloca bloques al azar presentes en tu barra de acceso rápido");
        add("constructionwand.option.random.no", "§cNo");
        add("constructionwand.option.random.no.desc", "No aleatorizar los bloques colocados");
        add("constructionwand.description.wand", "La %1$s puede colocar hasta %2$d bloques al lado de una construcción que estés mirando y dura %3$s.\n\nMantén presionado %5$s y gira la rueda para cambiar la restricción de colocación (Horizontal, Vertical, Norte/Sur, Este/Oeste, Sin restricción).\n\nAbre la pantalla de opciones con %6$s§9 + Clic derecho§0.\n\n§5§nDESHACER§0§r\nManteniendo presionado §9Agacharse + §0%4$s mientras miras un bloque mostrará los últimos bloques que colocaste resaltados con un borde verde. §9Agacharse + §0%4$s§9 + Clic derecho§0 sobre cualquiera de ellos deshará la operación, devolviéndote todos los objetos. Si usaste el núcleo de destrucción, restaurará los bloques.\n\n§5§nCONTENEDORES§0§r\nLas cajas de shulker, sacos y muchos contenedores de otros mods pueden proveer bloques de construcción para la varita.\n\n§5§nPRIORIDAD DE MANO SECUNDARIA§0§r\nTener bloques en la mano secundaria hará que se coloquen ellos en vez del bloque al que estás mirando.");
        add("constructionwand.description.durability.limited", "para %d bloques");
        add("constructionwand.description.durability.unlimited", "para siempre");
        add("constructionwand.description.key.sneak", "Agacharse");
        add("constructionwand.description.key.sneak_opt", "Agacharse + %s");
        add("constructionwand.description.core", "§5§nINSTALACIÓN§0§r\nColoca tu nuevo núcleo junto con tu varita en una cuadrícula de fabricación para instalarlo. Para cambiar entre núcleos, mantén presionado %s y haz clic izquierdo en un espacio vacío con tu varita o usa la pantalla de opciones.");
        add("constructionwand.description.core_angel", "El núcleo angelical coloca un bloque en el lado opuesto del bloque (o fila de bloques) que estás mirando. La distancia máxima depende del nivel de la varita. Haz clic derecho en un espacio vacío para colocar un bloque en el aire. Para hacer eso, necesitarás tener el bloque que deseas colocar en tu mano secundaria.");
        add("constructionwand.description.core_destruction", "El núcleo de destrucción destruye bloques (no entidades de bloque) en el lado que estás mirando. El número máximo de bloques depende del nivel de la varita. Los bloques destruidos desaparecen en el vacío, puedes usar la función de deshacer si cometiste un error.");
        add("stat.constructionwand.use_wand", "Bloques colocados usando la varita");
        add("advancement.constructionwand.void_sack.desc", "Bolsa del Vacío");
        add("advancement.constructionwand.void_sack.title", "Guarda items del Destruction Core dentro de la Bolsa del Vacío o en contenedores vinculados");
        add("key.category.constructionwand.category", "Construction Wands Revived");
        add("constructionwand.undo.success", "Acción de la varita deshecha");
        add("constructionwand.undo.nothing", "No hay nada para deshacer");
        add("constructionwand.networking.wand_undo.failed", "No se pudo deshacer la acción de la varita");
        add("key.constructionwand.wand_option", "Opción de varita");
        add("key.constructionwand.wand_undo", "Deshacer varita");
        }
    }

    public static class ESES extends LanguageProvider {
        public ESES(PackOutput packOutput) {
            super(packOutput, ConstructionWand.MODID, "es_es");
        }
        @Override
        protected void addTranslations() {
        add("itemGroup.constructionwand", "Varitas de Construcción Revividas");
        add("itemGroup.constructionwand.construction_wand_tab", "Varitas de Construcción Revividas");

        addItem(ModItems.WAND_STONE, "Varita de piedra");
        addItem(ModItems.WAND_IRON, "Varita de hierro");
        addItem(ModItems.WAND_GOLD, "Varita de Oro");
        addItem(ModItems.WAND_DIAMOND, "Varita de diamante");
        addItem(ModItems.WAND_NETHERITE, "Varita de netherita");
        addItem(ModItems.WAND_INFINITY, "Varita infinita");
        addItem(ModItems.CORE_ANGEL, "Núcleo de varita angelical");
        addItem(ModItems.CORE_DESTRUCTION, "Núcleo de varita de destrucción");
        addItem(ModItems.CORE_EXCHANGE, "Núcleo de Varita de Intercambio");
        add("advancement.constructionwand.gold_wand.title", "Varita de Oro");
        add("advancement.constructionwand.gold_wand.desc", "Obtén una Varita de Oro");
        add("advancement.constructionwand.core_exchange.title", "Núcleo de Intercambio");
        add("advancement.constructionwand.core_exchange.desc", "Obtén un Núcleo de Varita de Intercambio");
        add("constructionwand.option.cores.constructionwand:core_exchange", "§bNúcleo de Intercambio");
        add("constructionwand.option.cores.constructionwand:core_exchange.desc", "Reemplaza bloques en el lado que te mira por el bloque que tenés en la mano secundaria");
        add("constructionwand.description.core_exchange", "El núcleo de intercambio reemplaza los bloques en el lado que estás mirando (o una fila de bloques) por el bloque que tenés en la mano secundaria. El número máximo de bloques depende del nivel de la varita. Las restricciones funcionan igual que con el núcleo de Construcción.");
        add("constructionwand.message.exchange_selected", "Seleccionado: %s");
        add("constructionwand.message.exchange_invalid", "Ese bloque no se puede seleccionar");
        add("constructionwand.message.exchange_none_selected", "No hay bloque seleccionado — presioná Numpad 7 mirando un bloque");
        add("constructionwand.message.exchange_no_target", "Primero mirá un bloque");
        add("key.constructionwand.exchange_select", "Seleccionar bloque de Intercambio");

         add("advancement.constructionwand.root.title", "Varitas de Construcción Revividas");
add("advancement.constructionwand.root.desc", "Obtené tu primera varita");
add("advancement.constructionwand.stone_wand.title", "Varita de Piedra");
add("advancement.constructionwand.stone_wand.desc", "Obtené una Varita de Piedra");
add("advancement.constructionwand.iron_wand.title", "Varita de Hierro");
add("advancement.constructionwand.iron_wand.desc", "Obtené una Varita de Hierro");
add("advancement.constructionwand.diamond_wand.title", "Varita de Diamante");
add("advancement.constructionwand.diamond_wand.desc", "Obtené una Varita de Diamante");
add("advancement.constructionwand.netherite_wand.title", "Varita de Netherita");
add("advancement.constructionwand.netherite_wand.desc", "Obtené una Varita de Netherita");
add("advancement.constructionwand.infinity_wand.title", "Varita del Infinito");
add("advancement.constructionwand.infinity_wand.desc", "Obtené la Varita del Infinito");
add("advancement.constructionwand.core_angel.title", "Núcleo Angelical");
add("advancement.constructionwand.core_angel.desc", "Obtené un Núcleo de Varita Angelical");
add("advancement.constructionwand.core_destruction.title", "Núcleo de Destrucción");
add("advancement.constructionwand.core_destruction.desc", "Obtené un Núcleo de Varita de Destrucción");

        add("constructionwand.tooltip.blocks", "%d bloques máx.");
        add("constructionwand.tooltip.shift", "Presiona [MAYÚS]");
        add("constructionwand.tooltip.cores", "Núcleos de varita:");
        add("constructionwand.tooltip.core_tip", "Combina el núcleo con tu varita en una cuadrícula de fabricación");
        add("constructionwand.option.cores", "");
        add("constructionwand.option.cores.constructionwand:default", "Núcleo de construcción");
        add("constructionwand.option.cores.constructionwand:default.desc", "Extiende tu construcción hacia el lado que estás mirando");
        add("constructionwand.option.cores.constructionwand:core_angel", "§6Núcleo angelical");
        add("constructionwand.option.cores.constructionwand:core_angel.desc", "Coloca bloques detrás, incluso en el aire");
        add("constructionwand.option.cores.constructionwand:core_destruction", "§cNúcleo de destrucción");
        add("constructionwand.option.cores.constructionwand:core_destruction.desc", "Destruye bloques en el lado que estás mirando");
        add("constructionwand.option.lock", "Restricción: ");
        add("constructionwand.option.lock.horizontal", "§aIzquierda/Derecha");
        add("constructionwand.option.lock.horizontal.desc", "Construye una columna horizontal frente al bloque original");
        add("constructionwand.option.lock.vertical", "§aArriba/Abajo");
        add("constructionwand.option.lock.vertical.desc", "Construye una columna vertical frente al bloque original");
        add("constructionwand.option.lock.northsouth", "§6Norte/Sur");
        add("constructionwand.option.lock.northsouth.desc", "Construye una fila en dirección N/S sobre el bloque original");
        add("constructionwand.option.lock.eastwest", "§6Este/Oeste");
        add("constructionwand.option.lock.eastwest.desc", "Construye una fila en dirección E/O sobre el bloque original");
        add("constructionwand.option.lock.nolock", "§cNinguna");
        add("constructionwand.option.lock.nolock.desc", "Extiende desde cualquier lado del bloque original");
        add("constructionwand.option.direction", "Dirección: ");
        add("constructionwand.option.direction.target", "§6Objetivo");
        add("constructionwand.option.direction.target.desc", "Coloca bloques con la misma orientación que el bloque objetivo");
        add("constructionwand.option.direction.player", "§aJugador");
        add("constructionwand.option.direction.player.desc", "Coloca bloques orientados hacia el jugador");
        add("constructionwand.option.replace", "Reemplazo: ");
        add("constructionwand.option.replace.yes", "§aSí");
        add("constructionwand.option.replace.yes.desc", "Reemplaza ciertos bloques como fluidos, nieve y hierba alta");
        add("constructionwand.option.replace.no", "§cNo");
        add("constructionwand.option.replace.no.desc", "No reemplazar bloques");
        add("constructionwand.option.match", "Coincidencia: ");
        add("constructionwand.option.match.exact", "§aExacta");
        add("constructionwand.option.match.exact.desc", "Extiende solo bloques que sean exactamente iguales");
        add("constructionwand.option.match.similar", "§6Similar");
        add("constructionwand.option.match.similar.desc", "Trata bloques similares (tierra/pasto) como iguales");
        add("constructionwand.option.match.any", "§cCualquiera");
        add("constructionwand.option.match.any.desc", "Extiende cualquier bloque");
        add("constructionwand.option.random", "Aleatorio: ");
        add("constructionwand.option.random.yes", "§aSí");
        add("constructionwand.option.random.yes.desc", "Coloca bloques al azar presentes en tu barra de acceso rápido");
        add("constructionwand.option.random.no", "§cNo");
        add("constructionwand.option.random.no.desc", "No aleatorizar los bloques colocados");
        add("constructionwand.description.wand", "La %1$s puede colocar hasta %2$d bloques al lado de una construcción que estés mirando y dura %3$s.\n\nMantén presionado %5$s y gira la rueda para cambiar la restricción de colocación (Horizontal, Vertical, Norte/Sur, Este/Oeste, Sin restricción).\n\nAbre la pantalla de opciones con %6$s§9 + Clic derecho§0.\n\n§5§nDESHACER§0§r\nManteniendo presionado §9Agacharse + §0%4$s mientras miras un bloque mostrará los últimos bloques que colocaste resaltados con un borde verde. §9Agacharse + §0%4$s§9 + Clic derecho§0 sobre cualquiera de ellos deshará la operación, devolviéndote todos los objetos. Si usaste el núcleo de destrucción, restaurará los bloques.\n\n§5§nCONTENEDORES§0§r\nLas cajas de shulker, sacos y muchos contenedores de otros mods pueden proveer bloques de construcción para la varita.\n\n§5§nPRIORIDAD DE MANO SECUNDARIA§0§r\nTener bloques en la mano secundaria hará que se coloquen ellos en vez del bloque al que estás mirando.");
        add("constructionwand.description.durability.limited", "para %d bloques");
        add("constructionwand.description.durability.unlimited", "para siempre");
        add("constructionwand.description.key.sneak", "Agacharse");
        add("constructionwand.description.key.sneak_opt", "Agacharse + %s");
        add("constructionwand.description.core", "§5§nINSTALACIÓN§0§r\nColoca tu nuevo núcleo junto con tu varita en una cuadrícula de fabricación para instalarlo. Para cambiar entre núcleos, mantén presionado %s y haz clic izquierdo en un espacio vacío con tu varita o usa la pantalla de opciones.");
        add("constructionwand.description.core_angel", "El núcleo angelical coloca un bloque en el lado opuesto del bloque (o fila de bloques) que estás mirando. La distancia máxima depende del nivel de la varita. Haz clic derecho en un espacio vacío para colocar un bloque en el aire. Para hacer eso, necesitarás tener el bloque que deseas colocar en tu mano secundaria.");
        add("constructionwand.description.core_destruction", "El núcleo de destrucción destruye bloques (no entidades de bloque) en el lado que estás mirando. El número máximo de bloques depende del nivel de la varita. Los bloques destruidos desaparecen en el vacío, puedes usar la función de deshacer si cometiste un error.");
        add("stat.constructionwand.use_wand", "Bloques colocados usando la varita");
        add("item.constructionwand.void_sack", "Bolsa del Vacío");
        add("item.constructionwand.void_sack.active", "§aActiva §7(presioná §6%1$s§7 para cambiar)");
        add("item.constructionwand.void_sack.inactive", "§7Inactiva §7(presioná §6%1$s§7 para cambiar)");
        add("item.constructionwand.void_sack.linked", "Vinculada a: %d, %d, %d");
        add("item.constructionwand.void_sack.no_link", "Sin container vinculado");
        add("item.constructionwand.void_sack.sending", "Enviando al container");
        add("item.constructionwand.void_sack.storing", "Guardando internamente");
        add("item.constructionwand.void_sack.slots_used", "Espacios usados: %d / %d");
        add("item.constructionwand.void_sack.linked_msg", "Bolsa vinculada a %d, %d, %d");
        add("item.constructionwand.void_sack.activated", "Bolsa del Vacío activada");
        add("item.constructionwand.void_sack.deactivated", "Bolsa del Vacío desactivada");
        add("gui.constructionwand.void_sack.toggle_tooltip", "Cambiar: enviar al container / guardar internamente");
        add("gui.constructionwand.void_sack.sending", "Enviando →");
        add("gui.constructionwand.void_sack.storing", "Guardando");
        add("key.constructionwand.void_sack_toggle", "Alternar Bolsa del Vacío");
        add("advancement.constructionwand.void_sack.desc", "Bolsa del Vacío");
        add("advancement.constructionwand.void_sack.title", "Guarda items del Destruction Core dentro de la Bolsa del Vacío o en contenedores vinculados");
        add("key.category.constructionwand.category", "Construction Wands Revived");
        add("constructionwand.undo.success", "Acción de la varita deshecha");
        add("constructionwand.undo.nothing", "No hay nada para deshacer");
        add("constructionwand.networking.wand_undo.failed", "No se pudo deshacer la acción de la varita");
        add("key.constructionwand.wand_option", "Opción de varita");
        add("key.constructionwand.wand_undo", "Deshacer varita");
        }
    }

    public static class ESMX extends LanguageProvider {
        public ESMX(PackOutput packOutput) {
            super(packOutput, ConstructionWand.MODID, "es_mx");
        }
        @Override
        protected void addTranslations() {
        add("itemGroup.constructionwand", "Varitas de Construcción Revividas");
        add("itemGroup.constructionwand.construction_wand_tab", "Varitas de Construcción Revividas");

        addItem(ModItems.WAND_STONE, "Varita de piedra");
        addItem(ModItems.WAND_IRON, "Varita de hierro");
        addItem(ModItems.WAND_GOLD, "Varita de Oro");
        addItem(ModItems.WAND_DIAMOND, "Varita de diamante");
        addItem(ModItems.WAND_NETHERITE, "Varita de netherita");
        addItem(ModItems.WAND_INFINITY, "Varita infinita");
        addItem(ModItems.CORE_ANGEL, "Núcleo de varita angelical");
        addItem(ModItems.CORE_DESTRUCTION, "Núcleo de varita de destrucción");
        addItem(ModItems.CORE_EXCHANGE, "Núcleo de Varita de Intercambio");
        add("advancement.constructionwand.gold_wand.title", "Varita de Oro");
        add("advancement.constructionwand.gold_wand.desc", "Obtén una Varita de Oro");
        add("advancement.constructionwand.core_exchange.title", "Núcleo de Intercambio");
        add("advancement.constructionwand.core_exchange.desc", "Obtén un Núcleo de Varita de Intercambio");
        add("constructionwand.option.cores.constructionwand:core_exchange", "§bNúcleo de Intercambio");
        add("constructionwand.option.cores.constructionwand:core_exchange.desc", "Reemplaza bloques en el lado que te mira por el bloque que tenés en la mano secundaria");
        add("constructionwand.description.core_exchange", "El núcleo de intercambio reemplaza los bloques en el lado que estás mirando (o una fila de bloques) por el bloque que tenés en la mano secundaria. El número máximo de bloques depende del nivel de la varita. Las restricciones funcionan igual que con el núcleo de Construcción.");
        add("constructionwand.message.exchange_selected", "Seleccionado: %s");
        add("constructionwand.message.exchange_invalid", "Ese bloque no se puede seleccionar");
        add("constructionwand.message.exchange_none_selected", "No hay bloque seleccionado — presioná Numpad 7 mirando un bloque");
        add("constructionwand.message.exchange_no_target", "Primero mirá un bloque");
        add("key.constructionwand.exchange_select", "Seleccionar bloque de Intercambio");

         add("advancement.constructionwand.root.title", "Varitas de Construcción Revividas");
add("advancement.constructionwand.root.desc", "Obtené tu primera varita");
add("advancement.constructionwand.stone_wand.title", "Varita de Piedra");
add("advancement.constructionwand.stone_wand.desc", "Obtené una Varita de Piedra");
add("advancement.constructionwand.iron_wand.title", "Varita de Hierro");
add("advancement.constructionwand.iron_wand.desc", "Obtené una Varita de Hierro");
add("advancement.constructionwand.diamond_wand.title", "Varita de Diamante");
add("advancement.constructionwand.diamond_wand.desc", "Obtené una Varita de Diamante");
add("advancement.constructionwand.netherite_wand.title", "Varita de Netherita");
add("advancement.constructionwand.netherite_wand.desc", "Obtené una Varita de Netherita");
add("advancement.constructionwand.infinity_wand.title", "Varita del Infinito");
add("advancement.constructionwand.infinity_wand.desc", "Obtené la Varita del Infinito");
add("advancement.constructionwand.core_angel.title", "Núcleo Angelical");
add("advancement.constructionwand.core_angel.desc", "Obtené un Núcleo de Varita Angelical");
add("advancement.constructionwand.core_destruction.title", "Núcleo de Destrucción");
add("advancement.constructionwand.core_destruction.desc", "Obtené un Núcleo de Varita de Destrucción");

        add("constructionwand.tooltip.blocks", "%d bloques máx.");
        add("constructionwand.tooltip.shift", "Presiona [MAYÚS]");
        add("constructionwand.tooltip.cores", "Núcleos de varita:");
        add("constructionwand.tooltip.core_tip", "Combina el núcleo con tu varita en una cuadrícula de fabricación");
        add("constructionwand.option.cores", "");
        add("constructionwand.option.cores.constructionwand:default", "Núcleo de construcción");
        add("constructionwand.option.cores.constructionwand:default.desc", "Extiende tu construcción hacia el lado que estás mirando");
        add("constructionwand.option.cores.constructionwand:core_angel", "§6Núcleo angelical");
        add("constructionwand.option.cores.constructionwand:core_angel.desc", "Coloca bloques detrás, incluso en el aire");
        add("constructionwand.option.cores.constructionwand:core_destruction", "§cNúcleo de destrucción");
        add("constructionwand.option.cores.constructionwand:core_destruction.desc", "Destruye bloques en el lado que estás mirando");
        add("constructionwand.option.lock", "Restricción: ");
        add("constructionwand.option.lock.horizontal", "§aIzquierda/Derecha");
        add("constructionwand.option.lock.horizontal.desc", "Construye una columna horizontal frente al bloque original");
        add("constructionwand.option.lock.vertical", "§aArriba/Abajo");
        add("constructionwand.option.lock.vertical.desc", "Construye una columna vertical frente al bloque original");
        add("constructionwand.option.lock.northsouth", "§6Norte/Sur");
        add("constructionwand.option.lock.northsouth.desc", "Construye una fila en dirección N/S sobre el bloque original");
        add("constructionwand.option.lock.eastwest", "§6Este/Oeste");
        add("constructionwand.option.lock.eastwest.desc", "Construye una fila en dirección E/O sobre el bloque original");
        add("constructionwand.option.lock.nolock", "§cNinguna");
        add("constructionwand.option.lock.nolock.desc", "Extiende desde cualquier lado del bloque original");
        add("constructionwand.option.direction", "Dirección: ");
        add("constructionwand.option.direction.target", "§6Objetivo");
        add("constructionwand.option.direction.target.desc", "Coloca bloques con la misma orientación que el bloque objetivo");
        add("constructionwand.option.direction.player", "§aJugador");
        add("constructionwand.option.direction.player.desc", "Coloca bloques orientados hacia el jugador");
        add("constructionwand.option.replace", "Reemplazo: ");
        add("constructionwand.option.replace.yes", "§aSí");
        add("constructionwand.option.replace.yes.desc", "Reemplaza ciertos bloques como fluidos, nieve y hierba alta");
        add("constructionwand.option.replace.no", "§cNo");
        add("constructionwand.option.replace.no.desc", "No reemplazar bloques");
        add("constructionwand.option.match", "Coincidencia: ");
        add("constructionwand.option.match.exact", "§aExacta");
        add("constructionwand.option.match.exact.desc", "Extiende solo bloques que sean exactamente iguales");
        add("constructionwand.option.match.similar", "§6Similar");
        add("constructionwand.option.match.similar.desc", "Trata bloques similares (tierra/pasto) como iguales");
        add("constructionwand.option.match.any", "§cCualquiera");
        add("constructionwand.option.match.any.desc", "Extiende cualquier bloque");
        add("constructionwand.option.random", "Aleatorio: ");
        add("constructionwand.option.random.yes", "§aSí");
        add("constructionwand.option.random.yes.desc", "Coloca bloques al azar presentes en tu barra de acceso rápido");
        add("constructionwand.option.random.no", "§cNo");
        add("constructionwand.option.random.no.desc", "No aleatorizar los bloques colocados");
        add("constructionwand.description.wand", "La %1$s puede colocar hasta %2$d bloques al lado de una construcción que estés mirando y dura %3$s.\n\nMantén presionado %5$s y gira la rueda para cambiar la restricción de colocación (Horizontal, Vertical, Norte/Sur, Este/Oeste, Sin restricción).\n\nAbre la pantalla de opciones con %6$s§9 + Clic derecho§0.\n\n§5§nDESHACER§0§r\nManteniendo presionado §9Agacharse + §0%4$s mientras miras un bloque mostrará los últimos bloques que colocaste resaltados con un borde verde. §9Agacharse + §0%4$s§9 + Clic derecho§0 sobre cualquiera de ellos deshará la operación, devolviéndote todos los objetos. Si usaste el núcleo de destrucción, restaurará los bloques.\n\n§5§nCONTENEDORES§0§r\nLas cajas de shulker, sacos y muchos contenedores de otros mods pueden proveer bloques de construcción para la varita.\n\n§5§nPRIORIDAD DE MANO SECUNDARIA§0§r\nTener bloques en la mano secundaria hará que se coloquen ellos en vez del bloque al que estás mirando.");
        add("constructionwand.description.durability.limited", "para %d bloques");
        add("constructionwand.description.durability.unlimited", "para siempre");
        add("constructionwand.description.key.sneak", "Agacharse");
        add("constructionwand.description.key.sneak_opt", "Agacharse + %s");
        add("constructionwand.description.core", "§5§nINSTALACIÓN§0§r\nColoca tu nuevo núcleo junto con tu varita en una cuadrícula de fabricación para instalarlo. Para cambiar entre núcleos, mantén presionado %s y haz clic izquierdo en un espacio vacío con tu varita o usa la pantalla de opciones.");
        add("constructionwand.description.core_angel", "El núcleo angelical coloca un bloque en el lado opuesto del bloque (o fila de bloques) que estás mirando. La distancia máxima depende del nivel de la varita. Haz clic derecho en un espacio vacío para colocar un bloque en el aire. Para hacer eso, necesitarás tener el bloque que deseas colocar en tu mano secundaria.");
        add("constructionwand.description.core_destruction", "El núcleo de destrucción destruye bloques (no entidades de bloque) en el lado que estás mirando. El número máximo de bloques depende del nivel de la varita. Los bloques destruidos desaparecen en el vacío, puedes usar la función de deshacer si cometiste un error.");
        add("stat.constructionwand.use_wand", "Bloques colocados usando la varita");
        add("item.constructionwand.void_sack", "Bolsa del Vacío");
        add("item.constructionwand.void_sack.active", "§aActiva §7(presioná §6%1$s§7 para cambiar)");
        add("item.constructionwand.void_sack.inactive", "§7Inactiva §7(presioná §6%1$s§7 para cambiar)");
        add("item.constructionwand.void_sack.linked", "Vinculada a: %d, %d, %d");
        add("item.constructionwand.void_sack.no_link", "Sin container vinculado");
        add("item.constructionwand.void_sack.sending", "Enviando al container");
        add("item.constructionwand.void_sack.storing", "Guardando internamente");
        add("item.constructionwand.void_sack.slots_used", "Espacios usados: %d / %d");
        add("item.constructionwand.void_sack.linked_msg", "Bolsa vinculada a %d, %d, %d");
        add("item.constructionwand.void_sack.activated", "Bolsa del Vacío activada");
        add("item.constructionwand.void_sack.deactivated", "Bolsa del Vacío desactivada");
        add("gui.constructionwand.void_sack.toggle_tooltip", "Cambiar: enviar al container / guardar internamente");
        add("gui.constructionwand.void_sack.sending", "Enviando →");
        add("gui.constructionwand.void_sack.storing", "Guardando");
        add("key.constructionwand.void_sack_toggle", "Alternar Bolsa del Vacío");
        add("advancement.constructionwand.void_sack.desc", "Bolsa del Vacío");
        add("advancement.constructionwand.void_sack.title", "Guarda items del Destruction Core dentro de la Bolsa del Vacío o en contenedores vinculados");
        add("key.category.constructionwand.category", "Construction Wands Revived");
        add("constructionwand.undo.success", "Acción de la varita deshecha");
        add("constructionwand.undo.nothing", "No hay nada para deshacer");
        add("constructionwand.networking.wand_undo.failed", "No se pudo deshacer la acción de la varita");
        add("key.constructionwand.wand_option", "Opción de varita");
        add("key.constructionwand.wand_undo", "Deshacer varita");
        }
    }

    public static class JAJP extends LanguageProvider {
        public JAJP(PackOutput packOutput) {
            super(packOutput, ConstructionWand.MODID, "ja_jp");
        }
        @Override
        protected void addTranslations() {
        add("itemGroup.constructionwand", "建築の杖リバイバル");
        add("itemGroup.constructionwand.construction_wand_tab", "建築の杖リバイバル");

        addItem(ModItems.WAND_STONE, "石の杖");
        addItem(ModItems.WAND_IRON, "鉄の杖");
        addItem(ModItems.WAND_GOLD, "金の杖");
        addItem(ModItems.WAND_DIAMOND, "ダイヤモンドの杖");
        addItem(ModItems.WAND_NETHERITE, "ネザライトの杖");
        addItem(ModItems.WAND_INFINITY, "無限の杖");
        addItem(ModItems.CORE_ANGEL, "天使の杖のコア");
        addItem(ModItems.CORE_DESTRUCTION, "破壊の杖のコア");
        addItem(ModItems.CORE_EXCHANGE, "交換の杖のコア");
        add("advancement.constructionwand.gold_wand.title", "金の杖");
        add("advancement.constructionwand.gold_wand.desc", "金の杖を入手する");
        add("advancement.constructionwand.core_exchange.title", "交換のコア");
        add("advancement.constructionwand.core_exchange.desc", "交換の杖のコアを入手する");
        add("constructionwand.option.cores.constructionwand:core_exchange", "§b交換のコア");
        add("constructionwand.option.cores.constructionwand:core_exchange.desc", "正面方向のブロックをオフハンドのブロックに置き換えます。");
        add("constructionwand.description.core_exchange", "交換のコアは、正面方向のブロック（またはブロックの列）を、オフハンドに持っているブロックと入れ替えます。置き換えられる最大ブロック数は杖の段階に依存します。制限の効果は建築のコアと同様です。");
        add("constructionwand.message.exchange_selected", "選択済み: %s");
        add("constructionwand.message.exchange_invalid", "そのブロックは選択できません");
        add("constructionwand.message.exchange_none_selected", "ブロックが選択されていません — ブロックを見ながらテンキー7を押してください");
        add("constructionwand.message.exchange_no_target", "まずブロックを見てください");
        add("key.constructionwand.exchange_select", "交換ブロックを選択");

        add("advancement.constructionwand.root.title", "建築の杖リバイバル");
add("advancement.constructionwand.root.desc", "最初の杖を手に入れよう");
add("advancement.constructionwand.stone_wand.title", "石の杖");
add("advancement.constructionwand.stone_wand.desc", "石の杖を入手する");
add("advancement.constructionwand.iron_wand.title", "鉄の杖");
add("advancement.constructionwand.iron_wand.desc", "鉄の杖を入手する");
add("advancement.constructionwand.diamond_wand.title", "ダイヤモンドの杖");
add("advancement.constructionwand.diamond_wand.desc", "ダイヤモンドの杖を入手する");
add("advancement.constructionwand.netherite_wand.title", "ネザライトの杖");
add("advancement.constructionwand.netherite_wand.desc", "ネザライトの杖を入手する");
add("advancement.constructionwand.infinity_wand.title", "無限の杖");
add("advancement.constructionwand.infinity_wand.desc", "無限の杖を入手する");
add("advancement.constructionwand.core_angel.title", "天使のコア");
add("advancement.constructionwand.core_angel.desc", "天使の杖のコアを入手する");
add("advancement.constructionwand.core_destruction.title", "破壊のコア");
add("advancement.constructionwand.core_destruction.desc", "破壊の杖のコアを入手する");

        add("constructionwand.tooltip.blocks", "最大%dブロック");
        add("constructionwand.tooltip.shift", "Shiftを押す");
        add("constructionwand.tooltip.cores", "杖のコア:");
        add("constructionwand.tooltip.core_tip", "クラフト画面上で杖とコアを組み合わせてください。");
        add("constructionwand.option.cores", "");
        add("constructionwand.option.cores.constructionwand:default", "建築のコア");
        add("constructionwand.option.cores.constructionwand:default.desc", "建築の効果を正面方向に拡大します。");
        add("constructionwand.option.cores.constructionwand:core_angel", "§6天使のコア");
        add("constructionwand.option.cores.constructionwand:core_angel.desc", "ブロックの後ろや中空にブロックを設置します。");
        add("constructionwand.option.cores.constructionwand:core_destruction", "§c破壊のコア");
        add("constructionwand.option.cores.constructionwand:core_destruction.desc", "正面方向のブロックを破壊します。");
        add("constructionwand.option.lock", "設置の制限: ");
        add("constructionwand.option.lock.horizontal", "§a水平方向");
        add("constructionwand.option.lock.horizontal.desc", "目標ブロックの前に水平方向に設置します。");
        add("constructionwand.option.lock.vertical", "§a垂直方向");
        add("constructionwand.option.lock.vertical.desc", "目標ブロックの前に垂直方向に設置します。");
        add("constructionwand.option.lock.northsouth", "§6南北方向");
        add("constructionwand.option.lock.northsouth.desc", "目標ブロックの上に南北方向に設置します。");
        add("constructionwand.option.lock.eastwest", "§6東西方向");
        add("constructionwand.option.lock.eastwest.desc", "目標ブロックの上に東西方向に設置します。");
        add("constructionwand.option.lock.nolock", "§cなし");
        add("constructionwand.option.lock.nolock.desc", "目標ブロックからどんな方向にも拡張できます。");
        add("constructionwand.option.direction", "設置する向き: ");
        add("constructionwand.option.direction.target", "§6目標ブロック基準");
        add("constructionwand.option.direction.target.desc", "目標ブロックと同じ向きに設置します。");
        add("constructionwand.option.direction.player", "§aプレイヤー基準");
        add("constructionwand.option.direction.player.desc", "プレイヤーに向かって設置します。");
        add("constructionwand.option.replace", "ブロックの置換: ");
        add("constructionwand.option.replace.yes", "§a置換する");
        add("constructionwand.option.replace.yes.desc", "液体、雪、背の高い草など特定のブロックを置換します。");
        add("constructionwand.option.replace.no", "§c置換しない");
        add("constructionwand.option.replace.no.desc", "ブロックを置換しません。");
        add("constructionwand.option.match", "適合条件: ");
        add("constructionwand.option.match.exact", "§a完全一致");
        add("constructionwand.option.match.exact.desc", "厳密に一致するブロックのみが拡張できます。");
        add("constructionwand.option.match.similar", "§6類似");
        add("constructionwand.option.match.similar.desc", "土ブロックと草ブロックなど、似たブロックを同じものとして扱います。");
        add("constructionwand.option.match.any", "§c全て");
        add("constructionwand.option.match.any.desc", "どんなブロックも拡張できます。");
        add("constructionwand.option.random", "ランダム設置: ");
        add("constructionwand.option.random.yes", "§aランダム設置する");
        add("constructionwand.option.random.yes.desc", "ホットバー内にあるブロックをランダムに設置します。");
        add("constructionwand.option.random.no", "§cランダム設置しない");
        add("constructionwand.option.random.no.desc", "ランダム設置を行いません。");
        add("constructionwand.description.wand", "%1$sは、向かっている面に%2$dブロック設置でき、%3$s使用できます。\n\n%5$を押しながらホイールスクロールすると設置方向(水平、垂直、南北、東西、無制限)を変更できます。\n\n%6$s§9を押しながら使用§0でオプション画面を開きます。\n\n§5§n取消§0§r\nブロックの方を向きながら§9スニークしながら§0%4$sを押し続けると、最後に設置したブロックが緑の境界線ととともに表示されます。§9スニークしながら§0%4$s§9を押して使用ボタンを押す§0と、処理を取り消すことができ、すべてのブロックはインベントリに戻ります。破壊の杖を使用していた場合もブロックはもとに戻ります。\n\n§5§コンテナ§0§r\nシュルカーボックス、バンドル、その他のModで追加されたコンテナは杖で建築を行った際に資材を供給できます。\n\n§5§nオフハンド優先§0§r\nオフハンドにブロックを持っている場合、設置されているブロックよりそちらが優先されます。");
        add("constructionwand.description.durability.limited", "残り%dブロック");
        add("constructionwand.description.durability.unlimited", "無制限に");
        add("constructionwand.description.key.sneak", "スニーク");
        add("constructionwand.description.key.sneak_opt", "スニーク+%s");
        add("constructionwand.description.core", "§5§nセッティング§0§r\n新しいコアと杖を一緒にクラフトが麺に設置することでセッティングできます。コアを交換するためには、杖を持って何もない空間を%sを押しながら攻撃するか、オプション画面を使用してください。");
        add("constructionwand.description.core_angel", "天使のコアはブロックの反対側にブロックを設置します。最大距離は杖の段階に依存します。オフハンドにブロックを持った状態で、何もない空間に使用すると空中にそのブロックを設置できます。");
        add("constructionwand.description.core_destruction", "破壊のコアは正面のブロック(タイルエンティティは不可)を破壊できます。破壊できる最大ブロック数は杖の段階に依存します。破壊されたブロックは消滅しますが、間違えたときは取り消すことができます。");
        add("stat.constructionwand.use_wand", "杖を用いてブロックを設置");
        add("advancement.constructionwand.void_sack.desc", "ボイドサック");
add("advancement.constructionwand.void_sack.title", "Destruction Coreのアイテムをボイドサックまたはリンクされたコンテナに保存する");

add("item.constructionwand.void_sack", "ボイドサック");
add("item.constructionwand.void_sack.active", "§a有効 §7(§6%1$s§7で切り替え)");
add("item.constructionwand.void_sack.inactive", "§7無効 §7(§6%1$s§7で切り替え)");
add("item.constructionwand.void_sack.linked", "リンク先: %d, %d, %d");
add("item.constructionwand.void_sack.no_link", "リンクされたコンテナなし");
add("item.constructionwand.void_sack.sending", "コンテナへ送信中");
add("item.constructionwand.void_sack.storing", "内部保存中");
add("item.constructionwand.void_sack.slots_used", "使用スロット: %d / %d");
add("item.constructionwand.void_sack.linked_msg", "サックを %d, %d, %d にリンクしました");
add("item.constructionwand.void_sack.activated", "ボイドサックを有効化しました");
add("item.constructionwand.void_sack.deactivated", "ボイドサックを無効化しました");
add("gui.constructionwand.void_sack.toggle_tooltip", "切り替え: コンテナへ送信 / 内部保存");
add("gui.constructionwand.void_sack.sending", "送信中 →");
add("gui.constructionwand.void_sack.storing", "保存中");
add("key.constructionwand.void_sack_toggle", "ボイドサック切り替え");
add("key.category.constructionwand.category", "Construction Wands Revived");
add("constructionwand.undo.success", "ワンドの操作を元に戻しました");
add("constructionwand.undo.nothing", "元に戻す操作がありません");
add("constructionwand.networking.wand_undo.failed", "ワンドの操作を元に戻せませんでした");
add("key.constructionwand.wand_option", "ワンドオプション");
add("key.constructionwand.wand_undo", "ワンドを元に戻す");
        }
    }

    public static class KOKR extends LanguageProvider {
        public KOKR(PackOutput packOutput) {
            super(packOutput, ConstructionWand.MODID, "ko_kr");
        }
        @Override
        protected void addTranslations() {
        add("itemGroup.constructionwand", "건축 완드 리바이벌");
        add("itemGroup.constructionwand.construction_wand_tab", "건축 완드 리바이벌");

        addItem(ModItems.WAND_STONE, "돌 완드");
        addItem(ModItems.WAND_IRON, "철 완드");
        addItem(ModItems.WAND_GOLD, "황금 지팡이");
        addItem(ModItems.WAND_DIAMOND, "다이아몬드 완드");
        addItem(ModItems.WAND_NETHERITE, "네더라이트 지팡이");
        addItem(ModItems.WAND_INFINITY, "무한의 완드");
        addItem(ModItems.CORE_ANGEL, "천사 완드 코어");
        addItem(ModItems.CORE_DESTRUCTION, "파괴 완드 코어");
        addItem(ModItems.CORE_EXCHANGE, "교환 완드 코어");
        add("advancement.constructionwand.gold_wand.title", "황금 지팡이");
        add("advancement.constructionwand.gold_wand.desc", "황금 지팡이 획득하기");
        add("advancement.constructionwand.core_exchange.title", "교환 코어");
        add("advancement.constructionwand.core_exchange.desc", "교환 지팡이 코어 획득하기");
        add("constructionwand.option.cores.constructionwand:core_exchange", "§b교환 코어");
        add("constructionwand.option.cores.constructionwand:core_exchange.desc", "당신 쪽의 블록을 보조 손의 블록으로 교체합니다.");
        add("constructionwand.description.core_exchange", "교환 코어는 당신이 마주보고 있는 블록(또는 블록 행)을 보조 손에 들고 있는 블록으로 교체합니다. 최대 블록 수는 완드의 티어에 따라 다릅니다. 제한 옵션은 건축 코어와 동일하게 작동합니다.");
        add("constructionwand.message.exchange_selected", "선택됨: %s");
        add("constructionwand.message.exchange_invalid", "그 블록은 선택할 수 없습니다");
        add("constructionwand.message.exchange_none_selected", "선택된 블록이 없습니다 — 블록을 보면서 넘버패드 7을 누르세요");
        add("constructionwand.message.exchange_no_target", "먼저 블록을 보세요");
        add("key.constructionwand.exchange_select", "교환 블록 선택");

        add("advancement.constructionwand.root.title", "건설 지팡이 부활");
add("advancement.constructionwand.root.desc", "첫 번째 지팡이를 획득하세요");
add("advancement.constructionwand.stone_wand.title", "돌 지팡이");
add("advancement.constructionwand.stone_wand.desc", "돌 지팡이를 획득하세요");
add("advancement.constructionwand.iron_wand.title", "철 지팡이");
add("advancement.constructionwand.iron_wand.desc", "철 지팡이를 획득하세요");
add("advancement.constructionwand.diamond_wand.title", "다이아몬드 지팡이");
add("advancement.constructionwand.diamond_wand.desc", "다이아몬드 지팡이를 획득하세요");
add("advancement.constructionwand.netherite_wand.title", "네더라이트 지팡이");
add("advancement.constructionwand.netherite_wand.desc", "네더라이트 지팡이를 획득하세요");
add("advancement.constructionwand.infinity_wand.title", "무한 지팡이");
add("advancement.constructionwand.infinity_wand.desc", "무한 지팡이를 획득하세요");
add("advancement.constructionwand.core_angel.title", "천사 코어");
add("advancement.constructionwand.core_angel.desc", "천사 지팡이 코어를 획득하세요");
add("advancement.constructionwand.core_destruction.title", "파괴 코어");
add("advancement.constructionwand.core_destruction.desc", "파괴 지팡이 코어를 획득하세요");

        add("constructionwand.tooltip.blocks", "최대. %d 블록");
        add("constructionwand.tooltip.shift", "[SHIFT]를 누르세요.");
        add("constructionwand.tooltip.cores", "완드 코어:");
        add("constructionwand.tooltip.core_tip", "조합창에서 코어와 완드를 합치세요.");
        add("constructionwand.option.cores", "");
        add("constructionwand.option.cores.constructionwand:default", "생성 코어");
        add("constructionwand.option.cores.constructionwand:default.desc", "당신 쪽으로 건물을 확장합니다.");
        add("constructionwand.option.cores.constructionwand:core_angel", "§6천사 코어");
        add("constructionwand.option.cores.constructionwand:core_angel.desc", "블록 뒤와 공중에 배치합니다.");
        add("constructionwand.option.cores.constructionwand:core_destruction", "§c파괴 코어");
        add("constructionwand.option.cores.constructionwand:core_destruction.desc", "당신 쪽의 블록을 파괴합니다.");
        add("constructionwand.option.lock", "제한: ");
        add("constructionwand.option.lock.horizontal", "§a오른쪽/왼쪽");
        add("constructionwand.option.lock.horizontal.desc", "원래 블록의 앞에 수평한 열을 만듭니다.");
        add("constructionwand.option.lock.vertical", "§a위/아래");
        add("constructionwand.option.lock.vertical.desc", "원래 블록의 앞에 수직한 열을 만듭니다.");
        add("constructionwand.option.lock.northsouth", "§6북쪽/남쪽");
        add("constructionwand.option.lock.northsouth.desc", "원래 블록의 위에 북/남 방향으로 행을 만듭니다.");
        add("constructionwand.option.lock.eastwest", "§6동쪽/서쪽");
        add("constructionwand.option.lock.eastwest.desc", "원래 블록의 위에 동/서 방향으로 행을 만듭니다.");
        add("constructionwand.option.lock.nolock", "§c없음");
        add("constructionwand.option.lock.nolock.desc", "원래 블록의 어느 방향으로도 확장합니다.");
        add("constructionwand.option.direction", "방향: ");
        add("constructionwand.option.direction.target", "§6대상");
        add("constructionwand.option.direction.target.desc", "대상 블록과 같은 방향으로 블록을 배치합니다.");
        add("constructionwand.option.direction.player", "§a플레이어");
        add("constructionwand.option.direction.player.desc", "플레이어를 향해 블록을 배치합니다.");
        add("constructionwand.option.replace", "재배치: ");
        add("constructionwand.option.replace.yes", "§a예");
        add("constructionwand.option.replace.yes.desc", "유체, 눈, 키 큰 잔디와 같은 특정 블록을 교체합니다.");
        add("constructionwand.option.replace.no", "§c아니오");
        add("constructionwand.option.replace.no.desc", "블록을 재배치하지 않습니다.");
        add("constructionwand.option.match", "비교: ");
        add("constructionwand.option.match.exact", "§a정확");
        add("constructionwand.option.match.exact.desc", "완전히 같은 블록만 확장합니다.");
        add("constructionwand.option.match.similar", "§6유사");
        add("constructionwand.option.match.similar.desc", "비슷한 블록(흙/잔디)을 똑같이 취급합니다.");
        add("constructionwand.option.match.any", "§c아무거나");
        add("constructionwand.option.match.any.desc", "아무 블록이나 확장합니다.");
        add("constructionwand.option.random", "무작위: ");
        add("constructionwand.option.random.yes", "§a예");
        add("constructionwand.option.random.yes.desc", "핫바에 있는 블록 중 무작위적으로 배치합니다.");
        add("constructionwand.option.random.no", "§c아니오");
        add("constructionwand.option.random.no.desc", "배치할 블록을 무작위적으로 하지 않습니다.");
        add("constructionwand.description.wand", "%1$s는 당신 쪽으로 최대 %2$d 블록까지 배치할 수 있고, %3$s 지속됩니다.\n\n%5$s을(를) 누르고 스크롤 하여 배치 제한을 바꾸세요 (수평, 수직, 북쪽/남쪽, 동쪽/서쪽, 제한 없음).\n\n%6$s§9+우클릭§0으로 옵션 스크린을 여세요.\n\n§5§n실행 취소§0§r\n블록을 보면서 §9웅크리기+§0%4$s를 누르고 있으면 마지막으로 배치했던 블록들이 녹색 테두리로 표시됩니다. 그 중 아무거나 §9S웅크리기+§0%4$s§9+우클릭§0 하면 그 작업을 실행 취소하고, 모든 아이템을 돌려줍니다. 파괴 코어를 사용했다면, 블록들을 복원합니다.\n\n§5§n컨테이너§0§r\n셜커 상자, 꾸러미, 그리고 다른 모드의 컨테이너들은 완드에 건설 블록을 제공할 수 있습니다.\n\n§5§n보조손 우선도§0§r\n보조 손에 블록을 가지고 있으면 보고 있는 블록을 배치하는 대신에 보조 손의 블록을 배치할 것입니다.");
        add("constructionwand.description.durability.limited", "%d 블록 만큼");
        add("constructionwand.description.durability.unlimited", "영원히");
        add("constructionwand.description.key.sneak", "웅크리기");
        add("constructionwand.description.key.sneak_opt", "웅크리기+%s");
        add("constructionwand.description.core", "§5§n설치§0§r\n새 코어를 완드와 함께 조합창에 넣어 설치하세요. 코어 간에 전환하려면 %s 키를 누른 상태에서 완드로 빈 공간을 좌클릭하거나 옵션 화면을 사용하십시오.");
        add("constructionwand.description.core_angel", "엔젤 코어는 마주보고 있는 블록(또는 블록 행)의 반대쪽에 블록을 배치합니다. 최대 거리는 완드의 티어에 따라 다릅니다. 빈 공간을 우클릭하면 공중에 블록을 배치할 수 있습니다. 그렇게 하려면 보조 손에 배치하려는 블록이 있어야 합니다.");
        add("constructionwand.description.core_destruction", "파괴 코어는 당신 쪽의 (타일 엔티티가 없는)블록을 파괴합니다. 최대 블록 수는 완드의 티어에 따라 다릅니다. 파괴된 블록은 공허로 사라지며 실수를 했다면 실행 취소 기능을 사용할 수 있습니다.");
        add("stat.constructionwand.use_wand", "완드로 배치한 블록 수");
        add("advancement.constructionwand.void_sack.desc", "공허 자루");
add("advancement.constructionwand.void_sack.title", "Destruction Core의 아이템을 공허 자루 또는 연결된 컨테이너에 저장하세요");

add("item.constructionwand.void_sack", "공허 자루");
add("item.constructionwand.void_sack.active", "§a활성화됨 §7(§6%1$s§7 키로 전환)");
add("item.constructionwand.void_sack.inactive", "§7비활성화됨 §7(§6%1$s§7 키로 전환)");
add("item.constructionwand.void_sack.linked", "연결됨: %d, %d, %d");
add("item.constructionwand.void_sack.no_link", "연결된 컨테이너 없음");
add("item.constructionwand.void_sack.sending", "컨테이너로 전송 중");
add("item.constructionwand.void_sack.storing", "내부 저장 중");
add("item.constructionwand.void_sack.slots_used", "사용된 슬롯: %d / %d");
add("item.constructionwand.void_sack.linked_msg", "자루가 %d, %d, %d 에 연결됨");
add("item.constructionwand.void_sack.activated", "공허 자루 활성화됨");
add("item.constructionwand.void_sack.deactivated", "공허 자루 비활성화됨");
add("gui.constructionwand.void_sack.toggle_tooltip", "전환: 컨테이너로 전송 / 내부 저장");
add("gui.constructionwand.void_sack.sending", "전송 중 →");
add("gui.constructionwand.void_sack.storing", "저장 중");
add("key.constructionwand.void_sack_toggle", "공허 자루 전환");
add("key.category.constructionwand.category", "Construction Wands Revived");
add("constructionwand.undo.success", "지팡이 동작을 실행 취소했습니다");
add("constructionwand.undo.nothing", "실행 취소할 내용이 없습니다");
add("constructionwand.networking.wand_undo.failed", "지팡이 동작 실행 취소에 실패했습니다");
add("key.constructionwand.wand_option", "지팡이 옵션");
add("key.constructionwand.wand_undo", "지팡이 실행 취소");
        }
    }

    public static class PTBR extends LanguageProvider {
        public PTBR(PackOutput packOutput) {
            super(packOutput, ConstructionWand.MODID, "pt_br");
        }
        @Override
        protected void addTranslations() {
        add("itemGroup.constructionwand", "Varinhas de Construção Revividas");
        add("itemGroup.constructionwand.construction_wand_tab", "Varinhas de Construção Revividas");

        addItem(ModItems.WAND_STONE, "Varinha de pedra");
        addItem(ModItems.WAND_IRON, "Varinha de ferro");
        addItem(ModItems.WAND_GOLD, "Varinha de Ouro");
        addItem(ModItems.WAND_DIAMOND, "Varinha de Diamante");
        addItem(ModItems.WAND_NETHERITE, "Varinha de Netjerita");
        addItem(ModItems.WAND_INFINITY, "Varinha infinita");
        addItem(ModItems.CORE_ANGEL, "Núcleo de Varita Angelical");
        addItem(ModItems.CORE_DESTRUCTION, "Núcleo de Varita de Destruição");
        addItem(ModItems.CORE_EXCHANGE, "Núcleo de Varita de Troca");
        add("advancement.constructionwand.gold_wand.title", "Varinha de Ouro");
        add("advancement.constructionwand.gold_wand.desc", "Obtenha uma Varinha de Ouro");
        add("advancement.constructionwand.core_exchange.title", "Núcleo de Troca");
        add("advancement.constructionwand.core_exchange.desc", "Obtenha um Núcleo de Troca");
        add("constructionwand.option.cores.constructionwand:core_exchange", "§bNúcleo de Troca");
        add("constructionwand.option.cores.constructionwand:core_exchange.desc", "Substitui os blocos do lado de frente para você pelo bloco na sua mão secundária");
        add("constructionwand.description.core_exchange", "O núcleo de troca substitui os blocos do lado de frente para você (ou uma fileira de blocos) pelo bloco que você está segurando na mão secundária. O número máximo de blocos depende da camada de varinha. As restrições funcionam da mesma forma que com o núcleo de construção.");
        add("constructionwand.message.exchange_selected", "Selecionado: %s");
        add("constructionwand.message.exchange_invalid", "Esse bloco não pode ser selecionado");
        add("constructionwand.message.exchange_none_selected", "Nenhum bloco selecionado — pressione Numpad 7 olhando para um bloco");
        add("constructionwand.message.exchange_no_target", "Olhe para um bloco primeiro");
        add("key.constructionwand.exchange_select", "Selecionar Bloco de Troca");

        add("constructionwand.tooltip.blocks", "Max. %d blocos");
        add("constructionwand.tooltip.shift", "Pressione Shift]");
        add("constructionwand.tooltip.cores", "Núcleos de varinhas:");
        add("constructionwand.tooltip.core_tip", "Combine o núcleo com sua varinha em uma grade de criação");
        add("constructionwand.option.cores", "");
        add("constructionwand.option.cores.constructionwand:default", "Núcleo de construção");
        add("constructionwand.option.cores.constructionwand:default.desc", "Estender seu prédio do lado de frente para você");
        add("constructionwand.option.cores.constructionwand:core_angel", "§6angelCore");
        add("constructionwand.option.cores.constructionwand:core_angel.desc", "Coloque atrás dos quarteirões e no meio do ar");
        add("constructionwand.option.cores.constructionwand:core_destruction", "§cNúcleo de destruição");
        add("constructionwand.option.cores.constructionwand:core_destruction.desc", "Destrói blocos do lado de frente para você");
        add("constructionwand.option.lock", "Restrição: ");
        add("constructionwand.option.lock.horizontal", "§aEsquerda direita");
        add("constructionwand.option.lock.horizontal.desc", "Construa uma coluna horizontal em frente ao bloco original");
        add("constructionwand.option.lock.vertical", "§aCima baixo");
        add("constructionwand.option.lock.vertical.desc", "Construa uma coluna vertical em frente ao bloco original");
        add("constructionwand.option.lock.northsouth", "§6Norte Sul");
        add("constructionwand.option.lock.northsouth.desc", "Construa uma linha na direção N/s no topo do bloco original");
        add("constructionwand.option.lock.eastwest", "§6Leste Oeste");
        add("constructionwand.option.lock.eastwest.desc", "Construa uma linha na direção E/W no topo do bloco original");
        add("constructionwand.option.lock.nolock", "§cNenhum");
        add("constructionwand.option.lock.nolock.desc", "Estender de qualquer lado do bloco original");
        add("constructionwand.option.direction", "Direção: ");
        add("constructionwand.option.direction.target", "§6Alvo");
        add("constructionwand.option.direction.target.desc", "Coloque blocos com a mesma direção que o bloco de destino");
        add("constructionwand.option.direction.player", "§aJogadora");
        add("constructionwand.option.direction.player.desc", "Coloque blocos de frente para o jogador");
        add("constructionwand.option.replace", "Substituição: ");
        add("constructionwand.option.replace.yes", "§aSim");
        add("constructionwand.option.replace.yes.desc", "Substitua certos blocos como fluidos, neve e capim alto");
        add("constructionwand.option.replace.no", "§cNão");
        add("constructionwand.option.replace.no.desc", "Não substitua blocos");
        add("constructionwand.option.match", "Coincidindo: ");
        add("constructionwand.option.match.exact", "§aExata");
        add("constructionwand.option.match.exact.desc", "Estender apenas blocos que são exatamente iguais");
        add("constructionwand.option.match.similar", "§6Semelhante");
        add("constructionwand.option.match.similar.desc", "Tratar blocos semelhantes (tipos de sujeira/grama) igualmente");
        add("constructionwand.option.match.any", "§cAlguma");
        add("constructionwand.option.match.any.desc", "Estender qualquer bloco");
        add("constructionwand.option.random", "Aleatório: ");
        add("constructionwand.option.random.yes", "§aSim");
        add("constructionwand.option.random.yes.desc", "Coloque blocos aleatórios presentes em seu hotbar");
        add("constructionwand.option.random.no", "§cNão");
        add("constructionwand.option.random.no.desc", "Não randomize blocos colocados");
        add("constructionwand.description.wand", "o %1$s pode colocar até %2$d bloqueios ao lado de um prédio de frente para você e dura %3$s.\n\nCalma %5$s e role para alterar a restrição de posicionamento (horizontal, vertical, norte/sul, leste/oeste, sem fechadura).\n\nAbra a tela de opção com %6$s§9+Clique com o botão direito do mouse§0.\n\n§5§nDESFAZER§0§r\nMantendo pressionada §9Esgueirar-se+§0%4$s Enquanto olha para um bloco, mostrará os últimos blocos que você colocou com uma borda verde ao redor deles. §9Esgueirar-se+§0%4$s§9+Certa clicando§0 Qualquer um deles desfazerá a operação, oferecendo todos os itens de volta.Se você usou o núcleo de destruição, ele restaurará os blocos.\n\n§5§nRECIPIENTES§0§r\nCaixas Shulker, pacotes e muitos contêineres de outros mods podem fornecer blocos de construção para a varinha.\n\n§5§nPrioridade imediata§0§r\nTer blocos em sua mão os colocará em vez do bloco que você está olhando.");
        add("constructionwand.description.durability.limited", "por %d blocos");
        add("constructionwand.description.durability.unlimited", "para todo sempre");
        add("constructionwand.description.key.sneak", "Esgueirar-se");
        add("constructionwand.description.key.sneak_opt", "Esgueirar-se+%s");
        add("constructionwand.description.core", "§5§nINSTALAÇÃO§0§r\nColoque seu novo núcleo junto com sua varinha em uma grade de criação para instalá -la.Para alternar entre núcleos, mantenha pressionado %s e o clique esquerdo, esvazie o espaço com sua varinha ou use a tela de opção.");
        add("constructionwand.description.core_angel", "O núcleo do anjo coloca um bloco no lado oposto do bloco (ou fileira de blocos) que você está enfrentando.A distância máxima depende da camada de varinha.Clique com o botão direito do mouse em espaço vazio para colocar um bloco no ar.Para fazer isso, você precisará ter o bloco que deseja colocar em sua mão.");
        add("constructionwand.description.core_destruction", "O núcleo de destruição destrói blocos (sem entidades de ladrilhos) do lado de frente para você.O número máximo de blocos depende da camada de varinha.Blocos destruídos desaparecem no vazio, você pode usar o recurso de desfazer se cometer um erro.");
        add("stat.constructionwand.use_wand", "Blocos colocados usando varinha");

add("advancement.constructionwand.void_sack.desc", "Saco do Vazio");
add("advancement.constructionwand.void_sack.title", "Armazene itens do Destruction Core dentro do Saco do Vazio ou em contêineres vinculados");

add("item.constructionwand.void_sack", "Saco do Vazio");
add("item.constructionwand.void_sack.active", "§aAtivo §7(pressione §6%1$s§7 para alternar)");
add("item.constructionwand.void_sack.inactive", "§7Inativo §7(pressione §6%1$s§7 para alternar)");
add("item.constructionwand.void_sack.linked", "Vinculado a: %d, %d, %d");
add("item.constructionwand.void_sack.no_link", "Nenhum contêiner vinculado");
add("item.constructionwand.void_sack.sending", "Enviando para o contêiner");
add("item.constructionwand.void_sack.storing", "Armazenando internamente");
add("item.constructionwand.void_sack.slots_used", "Espaços usados: %d / %d");
add("item.constructionwand.void_sack.linked_msg", "Saco vinculado a %d, %d, %d");
add("item.constructionwand.void_sack.activated", "Saco do Vazio ativado");
add("item.constructionwand.void_sack.deactivated", "Saco do Vazio desativado");
add("gui.constructionwand.void_sack.toggle_tooltip", "Alternar: enviar para o contêiner / armazenar internamente");
add("gui.constructionwand.void_sack.sending", "Enviando →");
add("gui.constructionwand.void_sack.storing", "Armazenando");
add("key.constructionwand.void_sack_toggle", "Alternar Saco do Vazio");
add("key.category.constructionwand.category", "Construction Wands Revived");
add("constructionwand.undo.success", "Ação da varinha desfeita");
add("constructionwand.undo.nothing", "Nada para desfazer");
add("constructionwand.networking.wand_undo.failed", "Falha ao desfazer a ação da varinha");
add("key.constructionwand.wand_option", "Opção da varinha");
add("key.constructionwand.wand_undo", "Desfazer varinha");
        }
    }

    public static class RURU extends LanguageProvider {
        public RURU(PackOutput packOutput) {
            super(packOutput, ConstructionWand.MODID, "ru_ru");
        }
        @Override
        protected void addTranslations() {
        add("itemGroup.constructionwand", "Волшебные жезлы для строительства - Возрождение");
        add("itemGroup.constructionwand.construction_wand_tab", "Волшебные жезлы для строительства - Возрождение");

        addItem(ModItems.WAND_STONE, "Каменный жезл");
        addItem(ModItems.WAND_IRON, "Железный жезл");
        addItem(ModItems.WAND_GOLD, "Золотая палочка");
        addItem(ModItems.WAND_DIAMOND, "Алмазный жезл");
        addItem(ModItems.WAND_NETHERITE, "Незеритовый жезл");
        addItem(ModItems.WAND_INFINITY, "Бесконечный жезл");
        addItem(ModItems.CORE_ANGEL, "Ангельское ядро для жезла");
        addItem(ModItems.CORE_DESTRUCTION, "Ядро разрушения для жезла");
        addItem(ModItems.CORE_EXCHANGE, "Обменное ядро для жезла");
        add("advancement.constructionwand.gold_wand.title", "Золотая палочка");
        add("advancement.constructionwand.gold_wand.desc", "Получите золотую палочку");
        add("advancement.constructionwand.core_exchange.title", "Ядро обмена");
        add("advancement.constructionwand.core_exchange.desc", "Получите ядро обмена палочки");
        add("constructionwand.option.cores.constructionwand:core_exchange", "§bОбменное ядро");
        add("constructionwand.option.cores.constructionwand:core_exchange.desc", "Заменяет блоки на стороне, обращённой к Вам, на блок из левой руки.");
        add("constructionwand.description.core_exchange", "Обменное ядро заменяет блоки на стороне, обращённой к Вам (или ряд блоков), на блок, который Вы держите в левой руке. Максимальное количество блоков зависит от уровня жезла. Ограничения работают так же, как и с ядром строительства.");
        add("constructionwand.message.exchange_selected", "Выбрано: %s");
        add("constructionwand.message.exchange_invalid", "Этот блок нельзя выбрать");
        add("constructionwand.message.exchange_none_selected", "Блок не выбран — нажмите Numpad 7, глядя на блок");
        add("constructionwand.message.exchange_no_target", "Сначала посмотрите на блок");
        add("key.constructionwand.exchange_select", "Выбрать блок для обмена");

          add("advancement.constructionwand.root.title", "Строительные Жезлы Возрождены");
add("advancement.constructionwand.root.desc", "Получите свой первый жезл");
add("advancement.constructionwand.stone_wand.title", "Каменный жезл");
add("advancement.constructionwand.stone_wand.desc", "Получите каменный жезл");
add("advancement.constructionwand.iron_wand.title", "Железный жезл");
add("advancement.constructionwand.iron_wand.desc", "Получите железный жезл");
add("advancement.constructionwand.diamond_wand.title", "Алмазный жезл");
add("advancement.constructionwand.diamond_wand.desc", "Получите алмазный жезл");
add("advancement.constructionwand.netherite_wand.title", "Незеритовый жезл");
add("advancement.constructionwand.netherite_wand.desc", "Получите незеритовый жезл");
add("advancement.constructionwand.infinity_wand.title", "Жезл бесконечности");
add("advancement.constructionwand.infinity_wand.desc", "Получите жезл бесконечности");
add("advancement.constructionwand.core_angel.title", "Ангельское ядро");
add("advancement.constructionwand.core_angel.desc", "Получите ангельское ядро жезла");
add("advancement.constructionwand.core_destruction.title", "Ядро разрушения");
add("advancement.constructionwand.core_destruction.desc", "Получите ядро разрушения жезла");

        add("constructionwand.tooltip.blocks", "Максимум %d блоков");
        add("constructionwand.tooltip.shift", "Нажмите [SHIFT]");
        add("constructionwand.tooltip.cores", "Ядер жезла:");
        add("constructionwand.tooltip.core_tip", "Объедините ядро со своим жезлом в сетке создания.");
        add("constructionwand.option.cores", "");
        add("constructionwand.option.cores.constructionwand:default", "Ядро строительства");
        add("constructionwand.option.cores.constructionwand:default.desc", "Расширяйте свои строения на стороне, обращённой к Вам.");
        add("constructionwand.option.cores.constructionwand:core_angel", "§6Ангельское ядро");
        add("constructionwand.option.cores.constructionwand:core_angel.desc", "Размещает за блоками и в воздухе.");
        add("constructionwand.option.cores.constructionwand:core_destruction", "§cЯдро разрушения");
        add("constructionwand.option.cores.constructionwand:core_destruction.desc", "Уничтожает блоки на стороне, обращённой к Вам.");
        add("constructionwand.option.lock", "Ограничение: ");
        add("constructionwand.option.lock.horizontal", "§aВлево/Вправо");
        add("constructionwand.option.lock.horizontal.desc", "Строить горизонтальную колонну перед основным блоком.");
        add("constructionwand.option.lock.vertical", "§aВверх/Вниз");
        add("constructionwand.option.lock.vertical.desc", "Строить вертикальную колонну перед основным блоком.");
        add("constructionwand.option.lock.northsouth", "§6Север/Юг");
        add("constructionwand.option.lock.northsouth.desc", "Строить ряд в С/Ю направлении непосредственно за основным блоком.");
        add("constructionwand.option.lock.eastwest", "§6Восток/Запад");
        add("constructionwand.option.lock.eastwest.desc", "Строить ряд в В/З направлении непосредственно за основным блоком.");
        add("constructionwand.option.lock.nolock", "§cНичего");
        add("constructionwand.option.lock.nolock.desc", "Расширять с любой стороны основного блока.");
        add("constructionwand.option.direction", "Направление: ");
        add("constructionwand.option.direction.target", "§6Цель");
        add("constructionwand.option.direction.target.desc", "Размещать блоки с таким же направлением как целевой блок.");
        add("constructionwand.option.direction.player", "§aИгрок");
        add("constructionwand.option.direction.player.desc", "Размещать блоки, обращённые к игроку.");
        add("constructionwand.option.replace", "Замена: ");
        add("constructionwand.option.replace.yes", "§aДа");
        add("constructionwand.option.replace.yes.desc", "Заменять некоторые блоки как жидкости, снег и высокорослая трава.");
        add("constructionwand.option.replace.no", "§cНет");
        add("constructionwand.option.replace.no.desc", "Не заменять блоки.");
        add("constructionwand.option.match", "Совпадение: ");
        add("constructionwand.option.match.exact", "§aТочное");
        add("constructionwand.option.match.exact.desc", "Расширять только абсолютно одинаковые блоки.");
        add("constructionwand.option.match.similar", "§6Похожее");
        add("constructionwand.option.match.similar.desc", "Подносить аналогичные блоки (пример: земля/трава) поровну.");
        add("constructionwand.option.match.any", "§cНикакое");
        add("constructionwand.option.match.any.desc", "Расширять любой блок.");
        add("constructionwand.option.random", "Случайно: ");
        add("constructionwand.option.random.yes", "§aДа");
        add("constructionwand.option.random.yes.desc", "Размещать случайные блоки, имеющиеся в Вашей горячей панели.");
        add("constructionwand.option.random.no", "§cНет");
        add("constructionwand.option.random.no.desc", "Не располагать блоки в случайном порядке.");
        add("constructionwand.description.wand", "%1$s может размещать до %2$d блоков сбоку от строения, обращённое к Вам и его хватит на %3$s блоков.\n\nУдерживайте %5$s и прокрутите колёсиком для изменения ограничения по размещении (Горизонтально, Вертикально, Север/Юг, Восток/Запад, Без ограничивания).\n\nОткройте экран настроек при помощи %6$s§9+щелчок правой кнопкой мыши§0.\n\n§5§nОТМЕНА§0§r\nУдерживайте §9Приседание+§0%4$s пока смотрите на блоки, установленные Вами, они будут выделены зелёным контуром. §9Приседание+§0%4$s§9+щелчок правой кнопкой мыши§0 на любой из них отменит операцию, вернув Вам все предметы обратно. Если использовать Ядро разрушения, то он вернёт блоки.\n\n§5§nКОНТЕЙНЕР§0§r\nШалкеровые ящики, мешки и множество контейнеров из других модов могут предоставлять строительные блоки в жезл.\n\n§5§nПРИОРИТЕТ ЛЕВОЙ РУКИ§0§r\nЕсли у Вас в левой руке находятся блоки, то они будут размещаться вместо блока, на который Вы смотрите.");
        add("constructionwand.description.durability.limited", "на %d блоков");
        add("constructionwand.description.durability.unlimited", "вечно");
        add("constructionwand.description.key.sneak", "Приседание");
        add("constructionwand.description.key.sneak_opt", "Приседание+%s");
        add("constructionwand.description.core", "§5§nУСТАНОВКА§0§r\nПоложите своё новое ядро вместе со своим жезлом в сетку создания для его установки. Для того, чтобы переключаться между ядрами, удерживайте %s и нажмите левую кнопку мыши по пустому пространству с жезлом в руке или используйте экран настроек.");
        add("constructionwand.description.core_angel", "Ангельское ядро размещает блоки на противоположной стороне блока (или ряда блоков), обращённые к Вам. Максимальное расстояние зависит от уровня жезла. Щелчок правой кнопкой мыши по пустому воздуху разместит блок в воздухе. Чтобы это сделать, Вам нужно иметь необходимые блоки в левой руке, чтобы разместить их.");
        add("constructionwand.description.core_destruction", "Ядро разрушения разрушает блоки (не функциональные блоки), обращённые к Вам. Максимально количество блоков зависит от уровня жезла. Разрушенные блоки исчезают в пустоту, можно использовать функцию отмены в случае допущенной ошибки.");
        add("stat.constructionwand.use_wand", "Блоки, размещённые при помощи Жезла");
       
add("advancement.constructionwand.void_sack.desc", "Мешок Пустоты");
add("advancement.constructionwand.void_sack.title", "Сохраняйте предметы из Destruction Core в Мешке Пустоты или связанных контейнерах");

add("item.constructionwand.void_sack", "Мешок Пустоты");
add("item.constructionwand.void_sack.active", "§aАктивен §7(нажмите §6%1$s§7 для переключения)");
add("item.constructionwand.void_sack.inactive", "§7Неактивен §7(нажмите §6%1$s§7 для переключения)");
add("item.constructionwand.void_sack.linked", "Связан с: %d, %d, %d");
add("item.constructionwand.void_sack.no_link", "Нет связанного контейнера");
add("item.constructionwand.void_sack.sending", "Отправка в контейнер");
add("item.constructionwand.void_sack.storing", "Внутреннее хранение");
add("item.constructionwand.void_sack.slots_used", "Использовано слотов: %d / %d");
add("item.constructionwand.void_sack.linked_msg", "Мешок связан с %d, %d, %d");
add("item.constructionwand.void_sack.activated", "Мешок Пустоты активирован");
add("item.constructionwand.void_sack.deactivated", "Мешок Пустоты деактивирован");
add("gui.constructionwand.void_sack.toggle_tooltip", "Переключить: отправка в контейнер / внутреннее хранение");
add("gui.constructionwand.void_sack.sending", "Отправка →");
add("gui.constructionwand.void_sack.storing", "Хранение");
add("key.constructionwand.void_sack_toggle", "Переключить Мешок Пустоты");
add("key.category.constructionwand.category", "Construction Wands Revived");
add("constructionwand.undo.success", "Действие жезла отменено");
add("constructionwand.undo.nothing", "Нечего отменять");
add("constructionwand.networking.wand_undo.failed", "Не удалось отменить действие жезла");
add("key.constructionwand.wand_option", "Опция жезла");
add("key.constructionwand.wand_undo", "Отменить действие жезла");
        }
    }

    public static class SVSE extends LanguageProvider {
        public SVSE(PackOutput packOutput) {
            super(packOutput, ConstructionWand.MODID, "sv_se");
        }
        @Override
        protected void addTranslations() {
        add("itemGroup.constructionwand", "Byggstavar Återupplivade");
        add("itemGroup.constructionwand.construction_wand_tab", "Byggstavar Återupplivade");

        addItem(ModItems.WAND_STONE, "Stenstav");
        addItem(ModItems.WAND_IRON, "Järnstav");
        addItem(ModItems.WAND_GOLD, "Guldstav");
        addItem(ModItems.WAND_DIAMOND, "Diamantstav");
        addItem(ModItems.WAND_NETHERITE, "Netheritstav");
        addItem(ModItems.WAND_INFINITY, "Oändlighetsstav");
        addItem(ModItems.CORE_ANGEL, "Änglastavskärna");
        addItem(ModItems.CORE_DESTRUCTION, "Rivningsstavskärna");
        addItem(ModItems.CORE_EXCHANGE, "Utbytesstavskärna");
        add("advancement.constructionwand.gold_wand.title", "Guldstav");
        add("advancement.constructionwand.gold_wand.desc", "Skaffa en Guldstav");
        add("advancement.constructionwand.core_exchange.title", "Utbyteskärna");
        add("advancement.constructionwand.core_exchange.desc", "Skaffa en Utbyteskärna");
        add("constructionwand.option.cores.constructionwand:core_exchange", "§bUtbyteskärna");
        add("constructionwand.option.cores.constructionwand:core_exchange.desc", "Ersätter block på sidan som är riktad mot dig med blocket i din sekundära hand");
        add("constructionwand.description.core_exchange", "Utbyteskärnan ersätter block på sidan som är riktad mot dig (eller en rad block) med blocket du håller i din sekundära hand. Det maximala antalet block beror på stavens nivå. Begränsningar fungerar precis som med byggkärnan.");
        add("constructionwand.message.exchange_selected", "Vald: %s");
        add("constructionwand.message.exchange_invalid", "Det blocket kan inte väljas");
        add("constructionwand.message.exchange_none_selected", "Inget block valt — tryck Numpad 7 medan du tittar på ett block");
        add("constructionwand.message.exchange_no_target", "Titta på ett block först");
        add("key.constructionwand.exchange_select", "Välj utbytesblock");

           add("advancement.constructionwand.root.title", "Byggstavar Återupplivade");
add("advancement.constructionwand.root.desc", "Skaffa din första stav");
add("advancement.constructionwand.stone_wand.title", "Stenstav");
add("advancement.constructionwand.stone_wand.desc", "Skaffa en stenstav");
add("advancement.constructionwand.iron_wand.title", "Järnstav");
add("advancement.constructionwand.iron_wand.desc", "Skaffa en järnstav");
add("advancement.constructionwand.diamond_wand.title", "Diamantstav");
add("advancement.constructionwand.diamond_wand.desc", "Skaffa en diamantstav");
add("advancement.constructionwand.netherite_wand.title", "Netheritstav");
add("advancement.constructionwand.netherite_wand.desc", "Skaffa en netheritstav");
add("advancement.constructionwand.infinity_wand.title", "Oändlighetsstav");
add("advancement.constructionwand.infinity_wand.desc", "Skaffa oändlighetsstaven");
add("advancement.constructionwand.core_angel.title", "Änglakärna");
add("advancement.constructionwand.core_angel.desc", "Skaffa en änglastavskärna");
add("advancement.constructionwand.core_destruction.title", "Rivningskärna");
add("advancement.constructionwand.core_destruction.desc", "Skaffa en rivningsstavskärna");

        add("constructionwand.tooltip.blocks", "Max. %d block");
        add("constructionwand.tooltip.shift", "Håll ned [SHIFT]");
        add("constructionwand.tooltip.cores", "Stavkärnor:");
        add("constructionwand.tooltip.core_tip", "Kombinera kärnan med din stav i ett tillverkningsrutnät");
        add("constructionwand.option.cores", "");
        add("constructionwand.option.cores.constructionwand:default", "Byggkärna");
        add("constructionwand.option.cores.constructionwand:default.desc", "Utvidga din byggnad åt sidan som är riktad mot dig");
        add("constructionwand.option.cores.constructionwand:core_angel", "§6Änglakärna");
        add("constructionwand.option.cores.constructionwand:core_angel.desc", "Placera block bakom befintliga block och i luften");
        add("constructionwand.option.cores.constructionwand:core_destruction", "§cRivningskärna");
        add("constructionwand.option.cores.constructionwand:core_destruction.desc", "Förstör block på sidan som är riktad mot dig");
        add("constructionwand.option.lock", "Begränsning: ");
        add("constructionwand.option.lock.horizontal", "§aVänster/Höger");
        add("constructionwand.option.lock.horizontal.desc", "Bygg en horisontal kolumn framför originalblocket");
        add("constructionwand.option.lock.vertical", "§aUppåt/Nedåt");
        add("constructionwand.option.lock.vertical.desc", "Bygg en vertikal kolumn framför originalblocket");
        add("constructionwand.option.lock.northsouth", "§6Nord/Syd");
        add("constructionwand.option.lock.northsouth.desc", "Bygg en nord-/sydriktad rad ovanpå originalblocket");
        add("constructionwand.option.lock.eastwest", "§6Öst/Väst");
        add("constructionwand.option.lock.eastwest.desc", "Bygg en öst-/västriktad rad ovanpå originalblocket");
        add("constructionwand.option.lock.nolock", "§cIngen");
        add("constructionwand.option.lock.nolock.desc", "Utvidga från en valfri sida av originalblocket");
        add("constructionwand.option.direction", "Riktning: ");
        add("constructionwand.option.direction.target", "§6Mål");
        add("constructionwand.option.direction.target.desc", "Placera block i samma riktning som målblocket");
        add("constructionwand.option.direction.player", "§aSpelare");
        add("constructionwand.option.direction.player.desc", "Placera block i samma riktning som spelaren tittar åt");
        add("constructionwand.option.replace", "Ersättning: ");
        add("constructionwand.option.replace.yes", "§aJa");
        add("constructionwand.option.replace.yes.desc", "Ersätt vissa block, t.ex. vätskor, snö och högt gräs");
        add("constructionwand.option.replace.no", "§cNej");
        add("constructionwand.option.replace.no.desc", "Ersätt inte något block");
        add("constructionwand.option.match", "Matchning: ");
        add("constructionwand.option.match.exact", "§aExakt");
        add("constructionwand.option.match.exact.desc", "Utvidga endast block som är exakt likadana");
        add("constructionwand.option.match.similar", "§6Liknande");
        add("constructionwand.option.match.similar.desc", "Behandla liknande block (jord-/grästyper) likadant");
        add("constructionwand.option.match.any", "§cAllting");
        add("constructionwand.option.match.any.desc", "Utvidga alla block");
        add("constructionwand.option.random", "Slumpa: ");
        add("constructionwand.option.random.yes", "§aJa");
        add("constructionwand.option.random.yes.desc", "Placera slumpartade block från din föremålsmeny");
        add("constructionwand.option.random.no", "§cNej");
        add("constructionwand.option.random.no.desc", "Slumpa inte block som ska placeras ut");
        add("constructionwand.description.wand", "En %1$s kan placera upp till %2$d block på sidan av en byggnad som är riktad mot dig och räcker %3$s.\n\nHåll ned %5$s och rulla med mushjulet för att ändra placeringsbegränsningen (horisontal, vertikal, nord/syd, öst/väst, ingen låsning).\n\nÖppna alternativmenyn med %6$s§9+Högerklick§0.\n\n§5§nÅNGRA§0§r\nNär du håller ned §9Smyga+§0%4$s medan du tittar på ett block kommer du se de senaste blocken du placerade omgivna av en grön ram. §9Smyg+§0%4$s§9+högerklicka§0 på något av dem för att ångra handlingen och få tillbaka alla föremål. Om du har använt rivningskärnan kommer blocken att återställas.\n\n§5§nBEHÅLLARE§0§r\nShulkerlådor, påsar och många behållare från andra moddar kan tillhandahålla byggblock för staven.\n\n§5§nPRIO FÖR SEKUNDÄR HAND§0§r\nBlocken i din sekundära hand placeras i stället för blocket du tittar på.");
        add("constructionwand.description.durability.limited", "för %d block");
        add("constructionwand.description.durability.unlimited", "för alltid");
        add("constructionwand.description.key.sneak", "Smyg");
        add("constructionwand.description.key.sneak_opt", "Smyg+%s");
        add("constructionwand.description.core", "§5§nINSTALLATION§0§r\nLägg din nya kärna tillsammans med din stav i ett tillverkningsrutnät för att installera den. Håll ned %s och vänsterklicka i luften med din stav eller använd alternativmenyn för att byta kärna.");
        add("constructionwand.description.core_angel", "Änglakärnan placerar ett block på den motsatta sidan av blocket (eller blockraden) som är riktad mot dig. Det maximala avståndet beror på stavens nivå. Högerklicka i luften för att placera ett block i luften. För att göra detta behöver du hålla blocket du vill placera i din sekundära hand.");
        add("constructionwand.description.core_destruction", "Rivningskärnan förstör block (inte blockentiteter) på sidan som är riktad mot dig. Det maximala antalet block beror på stavens nivå. Förstörda block försvinner helt och hållet, men du kan använda ångrafunktionen om du har gjort ett misstag.");
        add("stat.constructionwand.use_wand", "Block placerade med stavar");
        add("advancement.constructionwand.void_sack.desc", "Tomhetspåse");
add("advancement.constructionwand.void_sack.title", "Förvara föremål från Destruction Core i Tomhetspåsen eller länkade behållare");

add("item.constructionwand.void_sack", "Tomhetspåse");
add("item.constructionwand.void_sack.active", "§aAktiv §7(tryck §6%1$s§7 för att växla)");
add("item.constructionwand.void_sack.inactive", "§7Inaktiv §7(tryck §6%1$s§7 för att växla)");
add("item.constructionwand.void_sack.linked", "Länkad till: %d, %d, %d");
add("item.constructionwand.void_sack.no_link", "Ingen behållare länkad");
add("item.constructionwand.void_sack.sending", "Skickar till behållare");
add("item.constructionwand.void_sack.storing", "Intern lagring");
add("item.constructionwand.void_sack.slots_used", "Använda platser: %d / %d");
add("item.constructionwand.void_sack.linked_msg", "Påsen länkades till %d, %d, %d");
add("item.constructionwand.void_sack.activated", "Tomhetspåsen aktiverades");
add("item.constructionwand.void_sack.deactivated", "Tomhetspåsen avaktiverades");
add("gui.constructionwand.void_sack.toggle_tooltip", "Växla: skicka till behållare / intern lagring");
add("gui.constructionwand.void_sack.sending", "Skickar →");
add("gui.constructionwand.void_sack.storing", "Lagring");
add("key.constructionwand.void_sack_toggle", "Växla Tomhetspåse");
add("constructionwand.description.void_sack", "Tomhetspåsen fångar upp föremål du plockar upp och lagrar dem i sitt interna 4×4-inventarie.\n\n§5§nAKTIVERING§0§r\nTryck på %1$s för att aktivera eller avaktivera påsen. När den är avaktiverad hamnar föremål i ditt vanliga inventarie.\n\n§5§nLÄNKA EN BEHÅLLARE§0§r\nHögerklicka på valfri behållare (kista, tunna, shulkerlåda...) medan du håller påsen för att länka den. När den är länkad skickas föremål först till behållaren när påsen är aktiv. Om behållaren är full lagras överskottet i påsens interna lagring.\n\n§5§nLÄGE§0§r\nTryck på %1$s för att växla mellan §aSkickar§0 (föremål → behållare) och §eLagrar§0 (föremål → interna platser). Du kan även använda växlingsknappen i GUI:t.");
add("key.category.constructionwand.category", "Construction Wands Revived");
add("constructionwand.undo.success", "Trollstavsåtgärd ångrad");
add("constructionwand.undo.nothing", "Inget att ångra");
add("constructionwand.networking.wand_undo.failed", "Det gick inte att ångra trollstavsåtgärden");
add("key.constructionwand.wand_option", "Trollstavsalternativ");
add("key.constructionwand.wand_undo", "Ångra trollstav");        
        }
    }

    public static class TRTR extends LanguageProvider {
        public TRTR(PackOutput packOutput) {
            super(packOutput, ConstructionWand.MODID, "tr_tr");
        }
        @Override
        protected void addTranslations() {
        add("itemGroup.constructionwand", "Yeniden Canlandırılmış İnşaat Asaları");
        add("itemGroup.constructionwand.construction_wand_tab", "Yeniden Canlandırılmış İnşaat Asaları");

        addItem(ModItems.WAND_STONE, "Taş Asa");
        addItem(ModItems.WAND_IRON, "Demir Asa");
        addItem(ModItems.WAND_GOLD, "Altın Asa");
        addItem(ModItems.WAND_DIAMOND, "Elmas Asa");
        addItem(ModItems.WAND_NETHERITE, "Netherite Asa");
        addItem(ModItems.WAND_INFINITY, "Sonsuzluk Asası");
        addItem(ModItems.CORE_ANGEL, "Melek Asa Çekirdeği");
        addItem(ModItems.CORE_DESTRUCTION, "Yıkım Asa Çekirdeği");
        addItem(ModItems.CORE_EXCHANGE, "Değişim Asa Çekirdeği");
        add("advancement.constructionwand.gold_wand.title", "Altın Asa");
        add("advancement.constructionwand.gold_wand.desc", "Bir Altın Asa edin");
        add("advancement.constructionwand.core_exchange.title", "Değişim Çekirdeği");
        add("advancement.constructionwand.core_exchange.desc", "Bir Değişim Asa Çekirdeği edin");
        add("constructionwand.option.cores.constructionwand:core_exchange", "§bDeğişim Çekirdeği");
        add("constructionwand.option.cores.constructionwand:core_exchange.desc", "Size bakan taraftaki blokları, boştaki elinizdeki blokla değiştirir");
        add("constructionwand.description.core_exchange", "Değişim çekirdeği, karşı karşıya olduğunuz bloğu (veya blok sırasını) boştaki elinizde tuttuğunuz blokla değiştirir. Maksimum blok sayısı asa seviyesine bağlıdır. Kısıtlamalar inşa çekirdeğiyle aynı şekilde çalışır.");
        add("constructionwand.message.exchange_selected", "Seçildi: %s");
        add("constructionwand.message.exchange_invalid", "O blok seçilemez");
        add("constructionwand.message.exchange_none_selected", "Seçili blok yok — bir bloğa bakarken Numpad 7'ye basın");
        add("constructionwand.message.exchange_no_target", "Önce bir bloğa bakın");
        add("key.constructionwand.exchange_select", "Değişim Bloğu Seç");
  
add("advancement.constructionwand.root.title", "Yeniden Canlandırılmış İnşaat Asaları");
add("advancement.constructionwand.root.desc", "İlk asanı edin");
add("advancement.constructionwand.stone_wand.title", "Taş Asa");
add("advancement.constructionwand.stone_wand.desc", "Bir Taş Asa edin");
add("advancement.constructionwand.iron_wand.title", "Demir Asa");
add("advancement.constructionwand.iron_wand.desc", "Bir Demir Asa edin");
add("advancement.constructionwand.diamond_wand.title", "Elmas Asa");
add("advancement.constructionwand.diamond_wand.desc", "Bir Elmas Asa edin");
add("advancement.constructionwand.netherite_wand.title", "Netherite Asa");
add("advancement.constructionwand.netherite_wand.desc", "Bir Netherite Asa edin");
add("advancement.constructionwand.infinity_wand.title", "Sonsuzluk Asası");
add("advancement.constructionwand.infinity_wand.desc", "Sonsuzluk Asasını edin");
add("advancement.constructionwand.core_angel.title", "Melek Çekirdeği");
add("advancement.constructionwand.core_angel.desc", "Bir Melek Asa Çekirdeği edin");
add("advancement.constructionwand.core_destruction.title", "Yıkım Çekirdeği");
add("advancement.constructionwand.core_destruction.desc", "Bir Yıkım Asa Çekirdeği edin");

        add("constructionwand.tooltip.blocks", "Maks. %d blok");
        add("constructionwand.tooltip.shift", "[SHIFT] bas");
        add("constructionwand.tooltip.cores", "Asa çekirdekleri:");
        add("constructionwand.tooltip.core_tip", "Çekirdeği asanızla birlikte üretim ızgarasında birleştirin");
        add("constructionwand.option.cores", "");
        add("constructionwand.option.cores.constructionwand:default", "İnşa Çekirdeği");
        add("constructionwand.option.cores.constructionwand:default.desc", "Yapınızın size bakan tarafını uzatır");
        add("constructionwand.option.cores.constructionwand:core_angel", "§6Melek Çekirdeği");
        add("constructionwand.option.cores.constructionwand:core_angel.desc", "Blokların arkasına ve havaya yerleştirir");
        add("constructionwand.option.cores.constructionwand:core_destruction", "§cYıkım Çekirdeği");
        add("constructionwand.option.cores.constructionwand:core_destruction.desc", "Size bakan taraftaki blokları yok eder");
        add("constructionwand.option.lock", "Sınırlama: ");
        add("constructionwand.option.lock.horizontal", "§aSol/Sağ");
        add("constructionwand.option.lock.horizontal.desc", "Orijinal bloğun önüne yatay bir sütun oluşturur");
        add("constructionwand.option.lock.vertical", "§aYukarı/Aşağı");
        add("constructionwand.option.lock.vertical.desc", "Orijinal bloğun önünde dikey bir sütun oluşturur");
        add("constructionwand.option.lock.northsouth", "§6Kuzey/Güney");
        add("constructionwand.option.lock.northsouth.desc", "Orijinal bloğun üstüne K/G yönünde bir sıra oluşturun");
        add("constructionwand.option.lock.eastwest", "§6Doğu/Batı");
        add("constructionwand.option.lock.eastwest.desc", "Orijinal bloğun üstüne D/B yönünde bir sıra oluşturur");
        add("constructionwand.option.lock.nolock", "§cYok");
        add("constructionwand.option.lock.nolock.desc", "Orijinal bloğun herhangi bir tarafından uzatır");
        add("constructionwand.option.direction", "Yön: ");
        add("constructionwand.option.direction.target", "§6Hedef");
        add("constructionwand.option.direction.target.desc", "Blokları hedef blokla aynı yönde yerleştirir");
        add("constructionwand.option.direction.player", "§aOyuncu");
        add("constructionwand.option.direction.player.desc", "Blokları oyuncuya bakacak şekilde yerleştirir");
        add("constructionwand.option.replace", "Değiştirme: ");
        add("constructionwand.option.replace.yes", "§aEvet");
        add("constructionwand.option.replace.yes.desc", "Sıvılar, kar ve uzun otlar gibi belirli blokları değiştirir");
        add("constructionwand.option.replace.no", "§cHayır");
        add("constructionwand.option.replace.no.desc", "Blokları değiştirmez");
        add("constructionwand.option.match", "Eşleşen: ");
        add("constructionwand.option.match.exact", "§aAynı");
        add("constructionwand.option.match.exact.desc", "Yalnızca tamamen aynı olan blokları uzatır");
        add("constructionwand.option.match.similar", "§6Benzer");
        add("constructionwand.option.match.similar.desc", "Benzer bloklara (toprak/çimen türleri) eşit davranır");
        add("constructionwand.option.match.any", "§cHerhangi");
        add("constructionwand.option.match.any.desc", "Herhangi bir bloğu uzatır");
        add("constructionwand.option.random", "Rastgele: ");
        add("constructionwand.option.random.yes", "§aEvet");
        add("constructionwand.option.random.yes.desc", "Hotbar'ınızdan rastgele bloklar yerleştirir");
        add("constructionwand.option.random.no", "§cHayır");
        add("constructionwand.option.random.no.desc", "Yerleştirilen blokları rastgeleleştirmez");
        add("constructionwand.description.wand", "%1$s, bir yapının size bakan tarafına en fazla %2$d blok yerleştirebilir ve %3$s dayanıklılığı vardır.\n\n%5$s tuşunu basılı tutun ve yerleştirme sınırlamasını değiştirmek için kaydırın (Yatay, Dikey, Kuzey/Güney, Doğu/Batı, Kilitsiz).\n\n%6$s§9+Sağ tıklama ile seçenek ekranını açın§0.\n\n§5§nGERİ ALMA§0§r\nBir bloğa bakarken §9Eğil+§0%4$s tuşunu basılı tuttuğunuzda, yerleştirdiğiniz son bloklar, çevresinde yeşil bir çerçeveyle gösterilecektir. §9Eğil+§0%4$s§9+Bunlardan herhangi birine sağ tıklama§0 işlemi geri alacak ve tüm öğeleri size geri verecektir. Yıkım çekirdeğini kullandıysanız blokları geri koyacaktır.\n\n§5§nKONTEYNERLER§0§r\nShulker kutuları, paketler ve diğer modlardan birçok konteyner, asa için yapı taşları sağlar.\n\n§5§nBOŞTAKİ EL ÖNCELİĞİ§0§r\nBoştaki elinizde blok olduğunda, baktığınız blok yerine boştaki elinizdekini yerleştirirsiniz.");
        add("constructionwand.description.durability.limited", "%d blok için");
        add("constructionwand.description.durability.unlimited", "sonsuza kadar");
        add("constructionwand.description.key.sneak", "Eğil");
        add("constructionwand.description.key.sneak_opt", "Eğil+%s");
        add("constructionwand.description.core", "§5§nKURULUM§0§r\nTakmak için yeni çekirdeğinizi asanızla birlikte bir üretim ızgarasına koyun. Çekirdekler arasında geçiş yapmak için %s tuşunu basılı tutun ve asanızla boş alana sol tıklayın veya seçenek ekranını kullanın");
        add("constructionwand.description.core_angel", "Melek çekirdeği, karşı karşıya olduğunuz bloğun (veya blok sırasının) karşı tarafına bir blok yerleştirir. Maksimum mesafe asa seviyesine bağlıdır. Havada bir blok yerleştirmek için boş alana sağ tıklayın. Bunu yapmak için, yerleştirmek istediğiniz bloğu boştaki elinize almalısınız.");
        add("constructionwand.description.core_destruction", "Yıkım çekirdeği, size bakan taraftaki blokları (tile entities haricinde) yok eder. Maksimum blok sayısı asa seviyesine bağlıdır. Yok edilen bloklar boşluğa kaybolur, hata yaptıysanız geri alma özelliğini kullanabilirsiniz.");
        add("stat.constructionwand.use_wand", "Asa kullanılarak yerleştirilen bloklar");
        add("advancement.constructionwand.void_sack.desc", "Boşluk Çuvalı");
        add("advancement.constructionwand.void_sack.title", "Destruction Core öğelerini Boşluk Çuvalında veya bağlı konteynerlerde sakla");
add("item.constructionwand.void_sack", "Boşluk Çantası");
add("item.constructionwand.void_sack.active", "§aEtkin §7(geçiş yapmak için §6%1$s§7 tuşuna basın)");
add("item.constructionwand.void_sack.inactive", "§7Devre Dışı §7(geçiş yapmak için §6%1$s§7 tuşuna basın)");
add("item.constructionwand.void_sack.linked", "Bağlı: %d, %d, %d");
add("item.constructionwand.void_sack.no_link", "Bağlı konteyner yok");
add("item.constructionwand.void_sack.sending", "Konteynere gönderiliyor");
add("item.constructionwand.void_sack.storing", "Dahili depolama");
add("item.constructionwand.void_sack.slots_used", "Kullanılan yuva: %d / %d");
add("item.constructionwand.void_sack.linked_msg", "Çanta %d, %d, %d konumuna bağlandı");
add("item.constructionwand.void_sack.activated", "Boşluk Çantası etkinleştirildi");
add("item.constructionwand.void_sack.deactivated", "Boşluk Çantası devre dışı bırakıldı");
add("gui.constructionwand.void_sack.toggle_tooltip", "Geçiş yap: konteynere gönder / dahili depolama");
add("gui.constructionwand.void_sack.sending", "Gönderiliyor →");
add("gui.constructionwand.void_sack.storing", "Depolama");
add("key.constructionwand.void_sack_toggle", "Boşluk Çantasını Değiştir");
add("constructionwand.description.void_sack", "Boşluk Çantası topladığınız eşyaları yakalar ve dahili 4×4 envanterinde saklar.\n\n§5§nETKİNLEŞTİRME§0§r\nÇantayı etkinleştirmek veya devre dışı bırakmak için %1$s tuşuna basın. Devre dışıyken eşyalar normal envanterinize gider.\n\n§5§nKONTEYNER BAĞLAMA§0§r\nÇantayı elinizde tutarken herhangi bir konteynere (sandık, varil, shulker kutusu...) sağ tıklayarak bağlayın. Bağlandıktan sonra, çanta etkin durumdayken eşyalar önce konteynere gönderilir. Konteyner doluysa fazla eşyalar çantanın dahili deposuna aktarılır.\n\n§5§nMOD§0§r\n§aGönderme§0 (eşyalar → konteyner) ve §eDepolama§0 (eşyalar → dahili yuvalar) modları arasında geçiş yapmak için %1$s tuşuna basın. Ayrıca GUI içindeki geçiş düğmesini de kullanabilirsiniz.");
add("key.category.constructionwand.category", "Construction Wands Revived");
add("constructionwand.undo.success", "Değnek işlemi geri alındı");
add("constructionwand.undo.nothing", "Geri alınacak bir şey yok");
add("constructionwand.networking.wand_undo.failed", "Değnek işlemi geri alınamadı");
add("key.constructionwand.wand_option", "Değnek seçeneği");
add("key.constructionwand.wand_undo", "Değneği geri al");
        }
    }

    public static class ZHCN extends LanguageProvider {
        public ZHCN(PackOutput packOutput) {
            super(packOutput, ConstructionWand.MODID, "zh_cn");
        }
        @Override
        protected void addTranslations() {
        add("itemGroup.constructionwand", "建筑手杖复兴版");
        add("itemGroup.constructionwand.construction_wand_tab", "建筑手杖复兴版");

        addItem(ModItems.WAND_STONE, "石制手杖");
        addItem(ModItems.WAND_IRON, "铁制手杖");
        addItem(ModItems.WAND_GOLD, "金杖");
        addItem(ModItems.WAND_DIAMOND, "钻石手杖");
        addItem(ModItems.WAND_NETHERITE, "下界合金建筑杖");
        addItem(ModItems.WAND_INFINITY, "无尽手杖");
        addItem(ModItems.CORE_ANGEL, "天使手杖核心");
        addItem(ModItems.CORE_DESTRUCTION, "破坏手杖核心");
        addItem(ModItems.CORE_EXCHANGE, "交换手杖核心");
        add("advancement.constructionwand.gold_wand.title", "金杖");
        add("advancement.constructionwand.gold_wand.desc", "获得一根金杖");
        add("advancement.constructionwand.core_exchange.title", "置换核心");
        add("advancement.constructionwand.core_exchange.desc", "获得一个置换法杖核心");
        add("constructionwand.option.cores.constructionwand:core_exchange", "§b交换核心");
        add("constructionwand.option.cores.constructionwand:core_exchange.desc", "将面向你一侧的方块替换为副手中的方块");
        add("constructionwand.description.core_exchange", "交换核心会将你所面对的方块（或一排方块）替换为你副手中持有的方块。最大替换方块数取决于手杖材质。限制选项的效果与建筑核心相同。");
        add("constructionwand.message.exchange_selected", "已选择：%s");
        add("constructionwand.message.exchange_invalid", "该方块无法被选择");
        add("constructionwand.message.exchange_none_selected", "未选择方块 — 看着一个方块并按小键盘7");
        add("constructionwand.message.exchange_no_target", "请先看向一个方块");
        add("key.constructionwand.exchange_select", "选择交换方块");

add("advancement.constructionwand.root.title", "建筑手杖复兴版");
add("advancement.constructionwand.root.desc", "获得你的第一根手杖");
add("advancement.constructionwand.stone_wand.title", "石制手杖");
add("advancement.constructionwand.stone_wand.desc", "获得一根石制手杖");
add("advancement.constructionwand.iron_wand.title", "铁制手杖");
add("advancement.constructionwand.iron_wand.desc", "获得一根铁制手杖");
add("advancement.constructionwand.diamond_wand.title", "钻石手杖");
add("advancement.constructionwand.diamond_wand.desc", "获得一根钻石手杖");
add("advancement.constructionwand.netherite_wand.title", "下界合金建筑杖");
add("advancement.constructionwand.netherite_wand.desc", "获得一根下界合金建筑杖");
add("advancement.constructionwand.infinity_wand.title", "无尽手杖");
add("advancement.constructionwand.infinity_wand.desc", "获得无尽手杖");
add("advancement.constructionwand.core_angel.title", "天使核心");
add("advancement.constructionwand.core_angel.desc", "获得一个天使手杖核心");
add("advancement.constructionwand.core_destruction.title", "破坏核心");
add("advancement.constructionwand.core_destruction.desc", "获得一个破坏手杖核心");

        add("constructionwand.tooltip.blocks", "最多放置%d个方块");
        add("constructionwand.tooltip.shift", "按 [SHIFT]");
        add("constructionwand.tooltip.cores", "手杖核心:");
        add("constructionwand.tooltip.core_tip", "将手杖核心与手杖组合在一起");
        add("constructionwand.option.cores", "");
        add("constructionwand.option.cores.constructionwand:default", "建筑核心");
        add("constructionwand.option.cores.constructionwand:default.desc", "在面向你的一侧放置方块");
        add("constructionwand.option.cores.constructionwand:core_angel", "§6天使核心");
        add("constructionwand.option.cores.constructionwand:core_angel.desc", "在面向你的方块的背面放置方块，还可以悬空放置方块");
        add("constructionwand.option.cores.constructionwand:core_destruction", "§c毁灭核心");
        add("constructionwand.option.cores.constructionwand:core_destruction.desc", "破坏面向你一侧的方块");
        add("constructionwand.option.lock", "锁定: ");
        add("constructionwand.option.lock.horizontal", "§a左 / 右");
        add("constructionwand.option.lock.horizontal.desc", "在起始方块的前面延伸一行水平方块");
        add("constructionwand.option.lock.vertical", "§a上 / 下");
        add("constructionwand.option.lock.vertical.desc", "在起始方块的前面延伸一列竖直方块");
        add("constructionwand.option.lock.northsouth", "§6南 / 北");
        add("constructionwand.option.lock.northsouth.desc", "在起始方块的上面，向南 / 北方向延伸一行");
        add("constructionwand.option.lock.eastwest", "§6东 / 西");
        add("constructionwand.option.lock.eastwest.desc", "在起始方块的上面，向东 / 西方向延伸一行");
        add("constructionwand.option.lock.nolock", "§c无");
        add("constructionwand.option.lock.nolock.desc", "从原始块的任意一面延伸");
        add("constructionwand.option.direction", "方向: ");
        add("constructionwand.option.direction.target", "§6目标");
        add("constructionwand.option.direction.target.desc", "放置与的方块方向与目标方块的方向相同");
        add("constructionwand.option.direction.player", "§a玩家");
        add("constructionwand.option.direction.player.desc", "放置的方块面向玩家");
        add("constructionwand.option.replace", "替换: ");
        add("constructionwand.option.replace.yes", "§a是");
        add("constructionwand.option.replace.yes.desc", "替换某些方块，如液体、雪、高草丛");
        add("constructionwand.option.replace.no", "§c否");
        add("constructionwand.option.replace.no.desc", "不替换方块");
        add("constructionwand.option.match", "匹配: ");
        add("constructionwand.option.match.exact", "§a精确");
        add("constructionwand.option.match.exact.desc", "仅放置完全相同的方块");
        add("constructionwand.option.match.similar", "§6模糊");
        add("constructionwand.option.match.similar.desc", "相似的方块被认为是相同的(草方块 / 泥土类型)");
        add("constructionwand.option.match.any", "§c任意");
        add("constructionwand.option.match.any.desc", "放置任何方块");
        add("constructionwand.option.random", "随机: ");
        add("constructionwand.option.random.yes", "§a是");
        add("constructionwand.option.random.yes.desc", "随机放置快捷栏中的方块");
        add("constructionwand.option.random.no", "§c否");
        add("constructionwand.option.random.no.desc", "不会随机放置方块");
        add("constructionwand.description.wand", "%1$s可以在建筑物面向你的一侧放置最多%2$d个方块，持续时间为%3$s。\n\n按住%5$s并滚动以更改放置限制（水平、垂直、北/南、东/西、无锁定）。\n\n在选项配置GUI上打开%6$s§9+右键单击§0。\n\n§5§nUNDO§0§r\n在查看方块时向下折叠§9Sneak+§0%4$s将显示你放置的最后一个方块,并在其周围加上绿色边框。§9潜行+§0%4$s§9+右键单击其中任何一个方块将撤消操作,并将所有以此法放置的方块重返至玩家背包。如果你使用了破坏核心,它将恢复方块。\n\n§5§n容器§0§r\n潜影盒、收纳袋和许多其它模组存在于玩家背包内的容器都可以为建筑手杖提供构建所需的方块。\n\n§5§非即时优先级§0§r\n如果玩家在使用手杖时副手栏持有所需方块将被放置,而不是只是在你的手里放着。");
        add("constructionwand.description.durability.limited", "需要%d方块");
        add("constructionwand.description.durability.unlimited", "无限");
        add("constructionwand.description.key.sneak", "潜行");
        add("constructionwand.description.key.sneak_opt", "潜行+%s");
        add("constructionwand.description.core", "§5§n安装§0§r\n将新的手杖核心与你的手杖一起放入工作台中进行组装。如果你想要在核心功能之间切换,请按住%s并用手杖左键单击空地或使用手杖的选项配置GUI。");
        add("constructionwand.description.core_angel", "天使核心可将一个方块放置在你所面对的方块(或一排方块)的对面。最大距离取决于手杖材质。在空地上手持手杖并单击鼠标右键即可在空中放置方块。要做到这一点。你需要将想要被在空中放置的方块放在你的副手栏中。");
        add("constructionwand.description.core_destruction", "毁灭核心会破坏面向你一侧的方块(破坏时被破坏的方块不可存在实体)。最大破坏方块数取决于手杖材质。被使用毁灭核心破坏的方块会消失。如果你只是不小心使用了毁灭核心。可以使用“撤消”功能以撤回被破坏并消失的物品返回原处。");
        add("stat.constructionwand.use_wand", "使用建筑手杖所放置的方块");
add("advancement.constructionwand.void_sack.desc", "虚空袋");
add("advancement.constructionwand.void_sack.title", "将 Destruction Core 产生的物品存入虚空袋或已绑定的容器中");

add("item.constructionwand.void_sack", "虚空袋");
add("item.constructionwand.void_sack.active", "§a已启用 §7(按 §6%1$s§7 切换)");
add("item.constructionwand.void_sack.inactive", "§7已禁用 §7(按 §6%1$s§7 切换)");
add("item.constructionwand.void_sack.linked", "已绑定到：%d, %d, %d");
add("item.constructionwand.void_sack.no_link", "未绑定容器");
add("item.constructionwand.void_sack.sending", "发送到容器");
add("item.constructionwand.void_sack.storing", "内部存储");
add("item.constructionwand.void_sack.slots_used", "已使用槽位：%d / %d");
add("item.constructionwand.void_sack.linked_msg", "虚空袋已绑定到 %d, %d, %d");
add("item.constructionwand.void_sack.activated", "虚空袋已启用");
add("item.constructionwand.void_sack.deactivated", "虚空袋已禁用");
add("gui.constructionwand.void_sack.toggle_tooltip", "切换：发送到容器 / 内部存储");
add("gui.constructionwand.void_sack.sending", "发送 →");
add("gui.constructionwand.void_sack.storing", "存储");
add("key.constructionwand.void_sack_toggle", "切换虚空袋");
add("constructionwand.description.void_sack", "虚空袋会拦截你拾取的物品，并将其存储在内部的 4×4 背包空间中。\n\n§5§n启用§0§r\n按下 %1$s 以启用或禁用虚空袋。禁用时，物品会进入你的普通背包。\n\n§5§n绑定容器§0§r\n手持虚空袋右键点击任意容器（箱子、木桶、潜影盒等）即可进行绑定。绑定后，当虚空袋处于启用状态时，物品会优先存入该容器。如果容器已满，多余的物品将存入虚空袋的内部存储空间。\n\n§5§n模式§0§r\n按下 %1$s 可在 §a发送模式§0（物品 → 容器）和 §e存储模式§0（物品 → 内部槽位）之间切换。你也可以在 GUI 中使用切换按钮进行更改。");
add("key.category.constructionwand.category", "Construction Wands Revived");
add("constructionwand.undo.success", "法杖操作已撤销");
add("constructionwand.undo.nothing", "没有可撤销的操作");
add("constructionwand.networking.wand_undo.failed", "撤销法杖操作失败");   
add("key.constructionwand.wand_option", "法杖选项");
add("key.constructionwand.wand_undo", "撤销法杖操作");     
        }
    }

    public static class DEDE extends LanguageProvider {
        public DEDE(PackOutput packOutput) {
            super(packOutput, ConstructionWand.MODID, "de_de");
        }
        @Override
        protected void addTranslations() {
        add("itemGroup.constructionwand", "Baustäbe Wiederbelebt");
        add("itemGroup.constructionwand.construction_wand_tab", "Baustäbe Wiederbelebt");

        addItem(ModItems.WAND_STONE, "Steinerner Stab");
        addItem(ModItems.WAND_IRON, "Eiserner Stab");
        addItem(ModItems.WAND_GOLD, "Goldstab");
        addItem(ModItems.WAND_DIAMOND, "Diamantener Stab");
        addItem(ModItems.WAND_NETHERITE, "Netherit Stab");
        addItem(ModItems.WAND_INFINITY, "Stab der Unendlichkeit");
        addItem(ModItems.CORE_ANGEL, "Kristall der Engel");
        addItem(ModItems.CORE_DESTRUCTION, "Kristall der Zerstörung");
        addItem(ModItems.CORE_EXCHANGE, "Kristall des Austauschs");
        add("advancement.constructionwand.gold_wand.title", "Goldstab");
        add("advancement.constructionwand.gold_wand.desc", "Erhalte einen Goldstab");
        add("advancement.constructionwand.core_exchange.title", "Austauschkern");
        add("advancement.constructionwand.core_exchange.desc", "Erhalte einen Austausch-Stabkern");
        add("constructionwand.option.cores.constructionwand:core_exchange", "§bKristall des Austauschs");
        add("constructionwand.option.cores.constructionwand:core_exchange.desc", "Ersetzt Blöcke auf der dir zugewandten Seite durch den Block in deiner linken Hand");
        add("constructionwand.description.core_exchange", "Der Kristall des Austauschs ersetzt Blöcke auf der dir zugewandten Seite (oder eine Reihe von Blöcken) durch den Block, den du in deiner linken Hand hältst. Die maximale Anzahl Blöcke hängt vom Material des Stabes ab. Beschränkungen funktionieren genauso wie beim Kristall der Konstruktion.");
        add("constructionwand.message.exchange_selected", "Ausgewählt: %s");
        add("constructionwand.message.exchange_invalid", "Dieser Block kann nicht ausgewählt werden");
        add("constructionwand.message.exchange_none_selected", "Kein Block ausgewählt — drücke Numpad 7, während du einen Block anschaust");
        add("constructionwand.message.exchange_no_target", "Schau zuerst auf einen Block");
        add("key.constructionwand.exchange_select", "Austauschblock auswählen");

add("advancement.constructionwand.root.title", "Baustäbe Wiederbelebt");
add("advancement.constructionwand.root.desc", "Erhalte deinen ersten Stab");
add("advancement.constructionwand.stone_wand.title", "Steinerner Stab");
add("advancement.constructionwand.stone_wand.desc", "Erhalte einen steinernen Stab");
add("advancement.constructionwand.iron_wand.title", "Eiserner Stab");
add("advancement.constructionwand.iron_wand.desc", "Erhalte einen eisernen Stab");
add("advancement.constructionwand.diamond_wand.title", "Diamantener Stab");
add("advancement.constructionwand.diamond_wand.desc", "Erhalte einen diamantenen Stab");
add("advancement.constructionwand.netherite_wand.title", "Netherit Stab");
add("advancement.constructionwand.netherite_wand.desc", "Erhalte einen Netherit Stab");
add("advancement.constructionwand.infinity_wand.title", "Stab der Unendlichkeit");
add("advancement.constructionwand.infinity_wand.desc", "Erhalte den Stab der Unendlichkeit");
add("advancement.constructionwand.core_angel.title", "Kristall der Engel");
add("advancement.constructionwand.core_angel.desc", "Erhalte einen Kristall der Engel");
add("advancement.constructionwand.core_destruction.title", "Kristall der Zerstörung");
add("advancement.constructionwand.core_destruction.desc", "Erhalte einen Kristall der Zerstörung");

        add("constructionwand.tooltip.blocks", "Max. %d Blöcke");
        add("constructionwand.tooltip.shift", "Drücke [SHIFT]");
        add("constructionwand.tooltip.cores", "Kristalle im Stab:");
        add("constructionwand.tooltip.core_tip", "Kombiniere den Kristall mit deinem Stab im Craftingfeld");
        add("constructionwand.option.cores", "");
        add("constructionwand.option.cores.constructionwand:default", "Kristall der Konstruktion");
        add("constructionwand.option.cores.constructionwand:default.desc", "Platziere Blöcke, wohin du zeigst");
        add("constructionwand.option.cores.constructionwand:core_angel", "§6Kristall der Engel");
        add("constructionwand.option.cores.constructionwand:core_angel.desc", "Platziere hinter Blöcken sowie in der Luft");
        add("constructionwand.option.cores.constructionwand:core_destruction", "§cKristall der Zerstörung");
        add("constructionwand.option.cores.constructionwand:core_destruction.desc", "Zerstöre Blöcke, wohin du zeigst");
        add("constructionwand.option.lock", "Beschränkung: ");
        add("constructionwand.option.lock.horizontal", "§aHorizontal");
        add("constructionwand.option.lock.horizontal.desc", "Baut eine horizontale Säule vor dem Originalblock");
        add("constructionwand.option.lock.vertical", "§aVertikal");
        add("constructionwand.option.lock.vertical.desc", "Baut eine vertikale Säule vor dem Originalblock");
        add("constructionwand.option.lock.northsouth", "§6Nord/Süd");
        add("constructionwand.option.lock.northsouth.desc", "Baut eine Reihe in NS-Richtung auf dem Originalblock");
        add("constructionwand.option.lock.eastwest", "§6Ost/West");
        add("constructionwand.option.lock.eastwest.desc", "Baut eine Reihe in OW-Richtung auf dem Originalblock");
        add("constructionwand.option.lock.nolock", "§cKeine");
        add("constructionwand.option.lock.nolock.desc", "Erweitert in jede Richtung");
        add("constructionwand.option.direction", "Ausrichtung: ");
        add("constructionwand.option.direction.target", "§6Zielblock");
        add("constructionwand.option.direction.target.desc", "Platziert Blöcke mit der selben Ausrichtung wie der Zielblock");
        add("constructionwand.option.direction.player", "§aSpieler");
        add("constructionwand.option.direction.player.desc", "Platziert Blöcke in der Richtung, auf die der Spieler zeigt");
        add("constructionwand.option.replace", "Ersetzen: ");
        add("constructionwand.option.replace.yes", "§aJa");
        add("constructionwand.option.replace.yes.desc", "Ersetzt bestimmte Blöcke wie Flüssigkeiten, Schnee und hohes Gras");
        add("constructionwand.option.replace.no", "§cNein");
        add("constructionwand.option.replace.no.desc", "Ersetzt keine Blöcke");
        add("constructionwand.option.match", "Vergleich: ");
        add("constructionwand.option.match.exact", "§aExakt");
        add("constructionwand.option.match.exact.desc", "Erweitert nur Blöcke, die gleich dem Startblock sind");
        add("constructionwand.option.match.similar", "§6Ähnlich");
        add("constructionwand.option.match.similar.desc", "Behandle ähnliche Blöcke (Erde/Gras) gleich");
        add("constructionwand.option.match.any", "§cAlle");
        add("constructionwand.option.match.any.desc", "Erweitert alle Blöcke");
        add("constructionwand.option.random", "Zufallsmodus: ");
        add("constructionwand.option.random.yes", "§aEin");
        add("constructionwand.option.random.yes.desc", "Platziere zufällige Blöcke aus der Hotbar");
        add("constructionwand.option.random.no", "§cAus");
        add("constructionwand.option.random.no.desc", "Platziere Blöcke normal");
        add("constructionwand.description.wand", "Ein %1$s kann maximal %2$d Blöcke auf der dir zugewandten Seite eines Bauwerks platzieren und hält %3$s.\n\nHalte %5$s gedrückt und scrolle, um die Platzierung zu beschränken (Horizontal, Vertikal, Nord/Süd, Ost/West, Keine).\n\nÖffne den Optionsbildschirm mit %6$s§9+Rechtsklick§0.\n\n§5§nRÜCKGÄNGIG§0§r\nHalte §9Schleichen+§0%4$s während du einen Block fokussierst. Die letzten platzierten Blöcke werden mit einem grünen Rahmen markiert. §9Schleichen+§0%4$s§9+Rechtsklick§0 auf einen dieser Blöcke macht diese Operation rückgängig und gibt dir alle Items zurück. Wenn du den Kristall der Zerstörung benutzt hast, werden die zerstörten Blöcke wiederhergestellt.\n\n§5§nCONTAINER§0§r\nShulkerkisten, Bündel und viele Container von anderen Mods können Baumaterial für deinen Stab bereitstellen.\n\n§5§nLINKE-HAND-PRIORITÄT§0§r\nWenn du einen Block in der linken Hand hältst, wird der Stab diesen anstatt des Blocks, den du anschaust, platzieren.");
        add("constructionwand.description.durability.limited", "für %d Blöcke");
        add("constructionwand.description.durability.unlimited", "unendlich lang");
        add("constructionwand.description.key.sneak", "Schleichen");
        add("constructionwand.description.key.sneak_opt", "Schleichen+%s");
        add("constructionwand.description.core", "§5§nINSTALLATION§0§r\nLege deinen neuen Kristall zusammen mit dem Stab auf eine Werkbank, um ihn einzusetzen. Um zwischen den Kristallen zu wechseln, halte %s gedrückt und klicke mit der linken Maustaste ins Leere. Alternativ kannst du den Kristall auch im Optionsbildschirm auswählen.");
        add("constructionwand.description.core_angel", "Der Kristall der Engel platziert einen Block auf der gegenüberliegenden Seites des Blocks (oder der Blockreihe) den du anschaust. Die maximale Entfernung hängt vom Material des Stabes ab. Ein Rechtsklick ins Leere platziert einen Block mitten in der Luft. Hierfür musst du den Block, den du platzieren willst, in der linken Hand halten.");
        add("constructionwand.description.core_destruction", "Der Kristall der Zerstörung zerstört Blöcke (keine Tile Entities) auf der dir zugewandten Seite. Die maximale Anzahl Blöcke hängt vonm Material des Stabes ab. Zerstörte Blöcke verschwinden im Nichts, du kannst Fehler jedoch rückgängig machen.");
        add("stat.constructionwand.use_wand", "Blöcke mithilfe des Stabs platziert");
       add("advancement.constructionwand.void_sack.desc", "Leerenbeutel");
add("advancement.constructionwand.void_sack.title", "Bewahre Gegenstände aus dem Destruction Core im Leerenbeutel oder in verknüpften Behältern auf");

add("item.constructionwand.void_sack", "Leerenbeutel");
add("item.constructionwand.void_sack.active", "§aAktiv §7(drücke §6%1$s§7 zum Umschalten)");
add("item.constructionwand.void_sack.inactive", "§7Inaktiv §7(drücke §6%1$s§7 zum Umschalten)");
add("item.constructionwand.void_sack.linked", "Verknüpft mit: %d, %d, %d");
add("item.constructionwand.void_sack.no_link", "Kein Behälter verknüpft");
add("item.constructionwand.void_sack.sending", "An Behälter senden");
add("item.constructionwand.void_sack.storing", "Interne Lagerung");
add("item.constructionwand.void_sack.slots_used", "Belegte Plätze: %d / %d");
add("item.constructionwand.void_sack.linked_msg", "Beutel mit %d, %d, %d verknüpft");
add("item.constructionwand.void_sack.activated", "Leerenbeutel aktiviert");
add("item.constructionwand.void_sack.deactivated", "Leerenbeutel deaktiviert");
add("gui.constructionwand.void_sack.toggle_tooltip", "Umschalten: an Behälter senden / intern lagern");
add("gui.constructionwand.void_sack.sending", "Senden →");
add("gui.constructionwand.void_sack.storing", "Lagerung");
add("key.constructionwand.void_sack_toggle", "Leerenbeutel umschalten");
add("constructionwand.description.void_sack", "Der Leerenbeutel fängt aufgesammelte Gegenstände ab und speichert sie in seinem internen 4×4-Inventar.\n\n§5§nAKTIVIERUNG§0§r\nDrücke %1$s, um den Beutel zu aktivieren oder zu deaktivieren. Wenn er deaktiviert ist, werden Gegenstände in dein normales Inventar gelegt.\n\n§5§nBEHÄLTER VERKNÜPFEN§0§r\nKlicke mit der rechten Maustaste auf einen beliebigen Behälter (Truhe, Fass, Shulkerkiste usw.), während du den Beutel hältst, um ihn zu verknüpfen. Nach der Verknüpfung werden Gegenstände bei aktiviertem Beutel zuerst in den Behälter gesendet. Ist der Behälter voll, werden überschüssige Gegenstände im internen Speicher des Beutels abgelegt.\n\n§5§nMODUS§0§r\nDrücke %1$s, um zwischen §aSenden§0 (Gegenstände → Behälter) und §eLagern§0 (Gegenstände → interne Plätze) umzuschalten. Alternativ kannst du auch die Umschalttaste in der GUI verwenden.");
add("key.category.constructionwand.category", "Construction Wands Revived");
add("constructionwand.undo.success", "Zauberstab-Aktion rückgängig gemacht");
add("constructionwand.undo.nothing", "Nichts zum Rückgängigmachen");
add("constructionwand.networking.wand_undo.failed", "Rückgängigmachen der Zauberstab-Aktion fehlgeschlagen");       
add("key.constructionwand.wand_option", "Zauberstab-Option");
add("key.constructionwand.wand_undo", "Zauberstab rückgängig machen");       
        }
    }

}