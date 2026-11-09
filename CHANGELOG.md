# Changelog

## 4.0.14 — Minecraft 26.3 / NeoForge and Fabric

### Fixes

- **The Destruction core no longer hands out the drops in creative mode, and it can be turned off in survival.** The
  core gives the drops of the blocks it breaks to your inventory (or to an active Void Sack), but its description said
  that the blocks disappear into the void. In creative mode the blocks are now simply destroyed. The server config has
  a new option, DestructionGivesDrops (on by default), that destroys the blocks without dropping anything also in
  survival when it is off. The description of the core in the game now says what it really does.

## 4.0.14 — Minecraft 26.3 / NeoForge and Fabric (Español)

### Corregido

- **El nucleo de Destruccion ya no entrega los drops en creativo y se puede desactivar en supervivencia.** El nucleo
  da los drops de los bloques que rompe a tu inventario (o a una Bolsa del Vacio activa), pero su descripcion decia
  que los bloques desaparecen en el vacio. En creativo los bloques ahora simplemente se destruyen. La config del
  servidor tiene una opcion nueva, DestructionGivesDrops (activada por defecto), que al desactivarla destruye los
  bloques sin soltar nada tambien en supervivencia. La descripcion del nucleo en el juego ahora dice lo que hace de
  verdad.

## 4.0.13 — Minecraft 26.3 / NeoForge and Fabric

### Fixes

- **The game no longer crashes on startup when Applied Energistics 2 is not installed.** Version 4.0.12 loaded a
  class that depends on AE2 while the mod was starting, so any modpack without AE2 failed with a missing class error
  (IAEItemPowerStorage). That class is now only loaded when AE2 is present.

## 4.0.13 — Minecraft 26.3 / NeoForge and Fabric (Español)

### Corregido

- **El juego ya no se cierra al iniciar cuando Applied Energistics 2 no esta instalado.** La version 4.0.12 cargaba al
  arrancar una clase que depende de AE2, asi que cualquier modpack sin AE2 fallaba con un error de clase no
  encontrada (IAEItemPowerStorage). Ahora esa clase solo se carga cuando AE2 esta presente.

## 4.0.12 — Minecraft 26.3 / NeoForge and Fabric

### Additions

- **Copper Wand.** A new wand between the stone and the iron one. It has the durability of the copper tools, is
  repaired with copper ingots, places up to 18 blocks and takes the Angel, Destruction and Exchange cores.
- **Energy Upgrade.** Put it on a wand in a crafting grid, like a core (it is crafted with 8 copper ingots around a
  block of redstone). A wand with the upgrade runs on energy instead of durability: it stores up to 100000 FE, every
  block costs 100 FE (both can be changed in the server config) and the item bar shows the charge. It can be charged
  with any charger or cable that speaks FE on the NeoForge and Forge builds, and Team Reborn Energy (built into the mod) on the Fabric builds. On NeoForge it can also be charged in the AE2 Charger. The Infinity Wand does not take it.
- **Silk Touch on the wands.** The wands can be enchanted with Silk Touch (enchanting table, anvil or creative). The
  Destruction core then breaks the blocks as a Silk Touch tool would, also when the drops go to a Void Sack. Fortune belongs to the same enchantment group, so it can be put on the wand too and the core respects it.
- **Each core has its own overlay** on the wand (a golden plus for Angel, a cross for Destruction, a double arrow for
  Exchange) instead of the same dot for all of them.

### Fixes

- **Crafting a core into a wand now consumes the core.** In any crafting table, in the inventory grid and in the AE2
  crafting terminal the core stayed in the grid after taking the upgraded wand. The recipe was also installing the core
  in the wand that was still in the grid, so the recipe stopped matching and the ingredients came back.
- **The Exchange core block can only be selected with the Exchange core active.** The selection key (Numpad 7) now
  shows a warning with any other core instead of selecting a block, and the "no block selected" warning of the block
  preview no longer covers the confirmation of the selection.

## 4.0.12 — Minecraft 26.3 / NeoForge and Fabric (Español)

### Anadido

- **Varita de Cobre.** Una varita nueva entre la de piedra y la de hierro. Tiene la durabilidad de las herramientas de
  cobre, se repara con lingotes de cobre, coloca hasta 18 bloques y admite los nucleos de Angel, Destruccion e
  Intercambio.
- **Mejora de Energia.** Se pone en una varita en una mesa de crafteo, como un nucleo (se fabrica con 8 lingotes de
  cobre alrededor de un bloque de redstone). Una varita con la mejora funciona con energia en vez de durabilidad:
  guarda hasta 100000 FE, cada bloque cuesta 100 FE (ambos se cambian en la config del servidor) y la barra del objeto
  muestra la carga. Se carga con cualquier cargador o cable de FE en las versiones de NeoForge y Forge, y con Team Reborn Energy (incluido en el mod) en las de Fabric. En NeoForge tambien se puede cargar en el Cargador de AE2. La Varita Infinita no la admite.
- **Toque de seda en las varitas.** Las varitas se pueden encantar con Toque de seda (mesa de encantamientos, yunque o
  creativo). El nucleo de Destruccion rompe entonces los bloques como lo haria una herramienta con Toque de seda,
  tambien cuando los drops van a una Bolsa del Vacio. Fortuna es del mismo grupo de encantamientos, asi que tambien se puede poner en la varita y el nucleo la respeta.
- **Cada nucleo tiene su propio overlay** en la varita (un signo mas dorado para Angel, una cruz para Destruccion, una
  flecha doble para Intercambio) en vez del mismo punto para todos.

### Corregido

- **Craftear un nucleo en una varita ahora consume el nucleo.** En cualquier mesa de crafteo, en la cuadricula del
  inventario y en la terminal de crafteo de AE2 el nucleo se quedaba en la cuadricula al coger la varita mejorada. La
  receta tambien instalaba el nucleo en la varita que seguia en la cuadricula, asi que la receta dejaba de coincidir y
  los ingredientes volvian.
- **El bloque del nucleo de Intercambio solo se puede seleccionar con ese nucleo activo.** La tecla de seleccion
  (Numpad 7) muestra un aviso con cualquier otro nucleo en vez de seleccionar un bloque, y el aviso de "ningun bloque
  seleccionado" de la vista previa ya no tapa la confirmacion de la seleccion.

## 4.0.11 — Minecraft 26.3 / NeoForge and Fabric

### Fixes

- **Undoing a destruction no longer duplicates the blocks.** The Destruction core hands the drops of every block it
  breaks to your inventory, but undoing the destruction put the block back without taking them away, so every undo
  left a free copy of each block. Undo now takes back from your inventory the items the destruction handed out, and
  it can only be done while you still carry them. When the drops went to a Void Sack (or what the sack could not hold
  fell in the world), they cannot be taken back and that destruction can no longer be undone.

## 4.0.11 — Minecraft 26.3 / NeoForge and Fabric (Español)

### Corregido

- **Deshacer una destruccion ya no duplica los bloques.** El nucleo de Destruccion entrega a tu inventario los drops
  de cada bloque que rompe, pero al deshacer se devolvia el bloque sin quitar esos objetos, asi que cada deshacer
  dejaba una copia gratis de cada bloque. Ahora deshacer te quita del inventario lo que la destruccion habia entregado,
  y solo se puede hacer mientras lo sigas llevando encima. Cuando los drops fueron a una Bolsa del Vacio (o lo que la
  bolsa no pudo guardar cayo al suelo), no se pueden recuperar y esa destruccion ya no se puede deshacer.

## 4.0.10 — Minecraft 26.3 / NeoForge and Fabric

### Fixes

- **Shulker boxes placed with the wand kept no contents.** A shulker box that held items came out empty when the
  wand placed it, so its contents were lost. The wand now moves the data of the item (contents, custom name and
  similar) into the placed block, the same way placing the block by hand does.

## 4.0.10 — Minecraft 26.3 / NeoForge and Fabric (Español)

### Corregido

- **Las cajas de shulker colocadas con la varita perdian su contenido.** Una caja de shulker con objetos salia
  vacia al colocarla con la varita, y el contenido se perdia. Ahora la varita pasa los datos del objeto (contenido,
  nombre personalizado y similares) al bloque colocado, igual que al colocarlo a mano.

## 4.0.9 — Minecraft 26.3 / NeoForge and Fabric

### Fixes

- **The wand no longer places unlimited blocks while you carry a container.** Any carried item that exposes an item
  inventory (backpacks and similar) made the wand treat every block as available. The wand now counts what your
  inventory and the containers in it really hold, and stops when it runs out.

- **Items taken from carried containers are now really consumed.** On NeoForge and Fabric the wand reported the blocks as taken but
  the backpack kept them, so they were never used up. The container is now updated when the wand takes blocks from it.

- **Crash on the block preview with an empty inventory slot.** On NeoForge, looking at a block with the wand while
  any slot of the inventory was empty could crash the game. Empty slots are now skipped.

## 4.0.9 — Minecraft 26.3 / NeoForge and Fabric (Español)

### Corregido

- **La varita ya no coloca bloques ilimitados mientras llevas un contenedor.** Cualquier objeto con inventario
  (mochilas y similares) hacia que la varita diera por disponibles todos los bloques. Ahora cuenta lo que de verdad
  hay en tu inventario y en los contenedores que llevas, y se detiene cuando se acaba.

- **Los objetos que se toman de los contenedores ahora se consumen de verdad.** En NeoForge y Fabric la varita daba los bloques
  por tomados pero la mochila los conservaba, asi que nunca se gastaban. Ahora el contenedor se actualiza cuando la
  varita toma bloques de el.

- **Fallo al previsualizar con un hueco vacio en el inventario.** En NeoForge, mirar un bloque con la varita
  mientras algun hueco del inventario estaba vacio podia cerrar el juego. Ahora los huecos vacios se ignoran.

## 4.0.8 — Minecraft 26.3 / NeoForge and Fabric

### Fixes

- **Joining a world failed with a network error.** The wand upgrade recipe could not be sent to the client: the
  recipe synchronization failed with `Can't encode 'RecipeWandUpgrade', expected 'RecipeWandUpgrade'` and every
  join was disconnected. The recipe has no state, so any two instances are now the same recipe and it is
  synchronized without errors.

- **Two warnings at startup are gone.** NeoForge no longer reports the deprecated `logoFile` property of the mod,
  and Minecraft no longer reports that the resource pack metadata lacks `min_format` and `max_format`.

## 4.0.8 — Minecraft 26.3 / NeoForge and Fabric (Español)

### Corregido

- **Entrar a un mundo fallaba con un error de red.** La receta de mejora de la varita no se podia enviar al
  cliente: la sincronizacion de recetas fallaba con `Can't encode 'RecipeWandUpgrade', expected 'RecipeWandUpgrade'`
  y cada intento de entrar terminaba desconectado. La receta no tiene estado, asi que ahora dos instancias
  cualesquiera son la misma receta y se sincroniza sin errores.

- **Desaparecen dos avisos al iniciar.** NeoForge ya no avisa de la propiedad `logoFile`, que esta obsoleta, y
  Minecraft ya no avisa de que al metadato del paquete de recursos le faltan `min_format` y `max_format`.

## 4.0.7 — Minecraft 26.3 / NeoForge and Fabric

### Additions

- **Fabric build for Minecraft 26.3.** This version now ships a NeoForge jar and a Fabric
  jar built from the same code. The Fabric build supports the recipe viewers (JEI, REI, EMI) that exist
  for this version. The integrations with other mods (storage mods, Curios, Botania) are NeoForge only.

- **Applied Energistics 2 support is back** (NeoForge): wireless terminals and portable cells, plus
  AE2 Wireless Terminals and Sophisticated Backpacks.

- **Botania Revived support is back** (NeoForge): the Black Hole Talisman and the Rod of the Lands and
  Depths supply blocks to the wand, consuming one block per placement.

- **The Exchange Core now has an advancement.** It was the only core without one.

### Fixes

- **The Destruction core captures drops with the Void Sack again.** The sack is found anywhere in the
  inventory, not only in your hand. An active sack stores the drops, or sends them to its linked
  container. An inactive sack that is linked to a container still sends to it. Without either, the
  drops go straight to your inventory.

- **The Void Sack advancement showed the wrong text.** Its title and description were swapped, so the
  toast showed the whole sentence as the title. It also left the Destruction Core name untranslated in
  every language. The title is now "Void Sack" and the sentence is the description.

- **Sophisticated Backpacks are read through the same inventory layer as the other containers**, which
  also works with the item transfer API of this version.

- **The Fabric mixin configuration was malformed.** The extra client mixins were joined with a literal
  `\n` instead of a line break, which is not valid JSON.

## 4.0.7 — Minecraft 26.3 / NeoForge and Fabric (Español)

### Anadido

- **Build de Fabric para Minecraft 26.3.** Esta version trae ahora un jar de NeoForge y
  otro de Fabric hechos con el mismo codigo. El build de Fabric soporta los visores de recetas (JEI, REI,
  EMI) que existen para esta version. Las integraciones con otros mods (mods de almacenamiento, Curios,
  Botania) son solo de NeoForge.

- **Vuelve el soporte de Applied Energistics 2** (NeoForge): terminales inalambricas y celdas portatiles,
  ademas de AE2 Wireless Terminals y Sophisticated Backpacks.

- **Vuelve el soporte de Botania Revived** (NeoForge): el Black Hole Talisman y la Rod of the Lands y
  Depths suministran bloques a la varita, consumiendo un bloque por colocacion.

- **El Nucleo de Intercambio ahora tiene avance.** Era el unico nucleo que no tenia.

### Corregido

- **El nucleo de Destruccion vuelve a capturar los drops con la Bolsa del Vacio.** La bolsa se encuentra
  en cualquier parte del inventario, no solo en la mano. Una bolsa activa guarda los drops, o los envia a
  su contenedor vinculado. Una bolsa inactiva vinculada a un contenedor sigue enviando a el. Sin ninguna
  de las dos cosas, los drops van directo al inventario.

- **El avance de la Bolsa del Vacio mostraba el texto equivocado.** El titulo y la descripcion estaban
  intercambiados, asi que el aviso mostraba la frase completa como titulo. Ademas dejaba sin traducir el
  nombre del Destruction Core en todos los idiomas. Ahora el titulo es "Bolsa del Vacio" y la frase es la
  descripcion.

- **Las Sophisticated Backpacks se leen con la misma capa de inventarios que el resto de contenedores**,
  lo que tambien funciona con la API de transferencia de items de esta version.

- **La configuracion de mixins de Fabric estaba mal formada.** Los mixins extra del cliente se unian con
  un `\n` literal en vez de un salto de linea, lo cual no es JSON valido.

## 4.0.1 — Minecraft 26.3 / NeoForge

### Additions

- **JEI is back.** JEI now ships for 26.3, so the item descriptions (wand stats, core
  descriptions and the Void Sack entry) show up in JEI again. Reliable Recipe Viewer stays
  supported: both register the same texts and can be installed together.
- **Curios is back.** Wands pull blocks again from containers equipped in Curios slots.

---

## 4.0.1 — Minecraft 26.3 / NeoForge (Español)

### Añadido

- **Vuelve JEI.** JEI ya tiene build para 26.3, así que las descripciones de los ítems
  (estadísticas de las varitas, descripción de los núcleos y la entrada de la Bolsa del Vacío)
  vuelven a salir en JEI. Reliable Recipe Viewer sigue soportado: los dos registran los mismos
  textos y se pueden tener instalados a la vez.
- **Vuelve Curios.** Las varitas vuelven a sacar bloques de los contenedores equipados en slots
  de Curios.

---

## 4.0.0 — Minecraft 26.3 / NeoForge

This release is a port of the 1.21 line, brought over for parity across versions: the fixes and
changes below were developed on 1.21.1 and 26.1 and carried here so every supported version
behaves the same.

Port to Minecraft 26.3, branched from the 26.2 tree. Carries the same fixes as 4.0.3 on
26.2 (modded blocks that store data on the item, block entity data preserved on placement,
redrawn golden wand).

### Additions

- **Wooden wand.** A new entry-tier wand, below stone: 59 uses, 5 blocks per placement, and no
  core slots at all. It is crafted from **cherry or birch planks** only, and its texture uses the
  same silhouette and shading as the rest of the set, in cherry tones.

### Build

- Minecraft `26.3`, NeoForge `26.3.0.0-beta` (declared range: `[26.3.0.0-beta,26.4.0)`).
- **Build system migrated from NeoGradle to ModDevGradle `2.0.147`.** NeoGradle `7.1.38`,
  the newest release, cannot build 26.3: its `neoFormRecompile` step fails while recompiling
  decompiled *vanilla* source (`net/minecraft/core/HolderSet.java`). ModDevGradle uses
  NeoForm Runtime and the artifacts NeoForge already publishes, so that step never runs.
  The previous build script is kept as `build.gradle.neogradle.bak`.

### Ported

- **Minecraft 26.3 replaced GLFW with SDL.** `org.lwjgl.glfw` is no longer on the classpath
  (`lwjgl-sdl` is), so the keybinds now use Mojang's own `InputConstants` key codes instead
  of raw `GLFW.GLFW_KEY_*` constants. Same default keys.
- **Recipes and advancements are now reloadable datapack registries.** `RecipeProvider` takes
  a `BootstrapContext` pair instead of a `PackOutput`, `RecipeProvider.Runner` is gone, and
  `AdvancementProvider` is a `SingleRegistryBootstrap<Advancement>` built from
  `AdvancementSubProvider.Factory` list. Data generation is wired through a
  `RegistrySetBuilder` handed to `DatapackBuiltinEntriesProvider.forReloadableLayer`.
- `RecipeProvider` exposes a `HolderGetter<Item>` (`items`) instead of the full
  `HolderLookup.Provider`, and `ItemPredicate.Builder#of` takes that getter.
- `Player#drop` now requires a `Prediction`; the undo history drops server-side.
- `BundleContents#itemCopyStream` was renamed to `itemCopies`.
- `FriendlyByteBuf`'s `read/writeBlockHitResult` helpers are gone; the preview packet uses
  `BlockHitResult.STREAM_CODEC`.

### Recipe viewer

JEI has no 26.3 build (`jei-26.3-neoforge` is not on the maven), so the item descriptions
are provided through **Reliable Recipe Viewer** instead, which does ship for 26.3.
`RrvIntegration` registers exactly the same texts the JEI plugin did — wand stats, core
descriptions and the Void Sack entry — translated from JEI's `addIngredientInfo` to RRV's
info recipes, rebuilt on every client reload so they follow keybind and config changes.

It is an optional client-side dependency: `ClientHandler` only touches the integration
behind `ModList.isLoaded("rrv")`, so nothing is loaded when RRV is absent.

The JEI plugin is **not deleted** — it stays in `src/disabled-integrations/`, and its
dependency lines in `build.gradle` and `neoforge.mods.toml` are commented out rather than
removed, ready to switch back on when JEI ships for 26.3. Both viewers can coexist then.

RRV is pulled from the Modrinth maven (`maven.modrinth:rrv:8.10.5+26.3-neoforge`) because
`maven.cassian.cc` has not published 8.10.5 yet, and 8.10.5 is the first release with 26.3
support.

### Removed (temporarily)

| Integration | Mod | Reason |
|---|---|---|
| Recipe descriptions | JEI | no 26.3 build; covered by Reliable Recipe Viewer in the meantime |

Sophisticated Backpacks and Sophisticated Core moved from `implementation` to `compileOnly`.
Their published builds declare `minecraft 26.2 or above, and below 26.3`, so FML refuses to
start a dev run with them on the runtime classpath. The integration still compiles and the
published jar is unchanged — those mods were never bundled, and `ContainerRegistrar` already
guards the handler behind `ModList.isLoaded("sophisticatedbackpacks")`. Switch them back to
`implementation` once a 26.3 build ships.

Everything 26.2 already had parked in `src/disabled-integrations/` stays parked. The JEI
plugin joins it there, and its dependency lines in `build.gradle` and `neoforge.mods.toml`
are commented out, ready to switch back on when JEI ships for 26.3.

### Fixes

- **Blocks that draw themselves from model data now render correctly when placed by a wand.**
  Functional Storage drawers were the visible case: a framed drawer placed by the wand appeared
  without its frame until something else forced the chunk to redraw, such as placing a block next
  to it. The wand places blocks server-side only, so the client never runs the placement
  prediction that makes those mods refresh their model data. The wand now tells the client which
  positions to refresh, retrying for the block entities the client has not created yet.

- **The golden wand had no translation.** Its name showed as `item.constructionwand.gold_wand`
  and it had no advancement, in every language.

- **The Void Sack tooltip showed a raw `%1$s`** instead of the toggle key, because the
  translation argument was never passed.

- **Mod icon added**, so the mod shows its own icon in the mods list instead of the default block.

### Documentation

- The README wand table was missing wood, gold and, in some versions, netherite, and several of its
  values did not match the config. It now lists all seven wands with the real values and adds the
  Exchange column. Image links point at this repository instead of the upstream one.
- New images: the wooden and golden wand recipes, and a stats table.

---
