# Changelog

## 4.0.13 — Minecraft 1.21.8 / NeoForge and Fabric

### Fixes

- **The Destruction core no longer hands out the drops in creative mode, and it can be turned off in survival.** The
  core gives the drops of the blocks it breaks to your inventory (or to an active Void Sack), but its description said
  that the blocks disappear into the void. In creative mode the blocks are now simply destroyed. The server config has
  a new option, DestructionGivesDrops (on by default), that destroys the blocks without dropping anything also in
  survival when it is off. The description of the core in the game now says what it really does.

## 4.0.13 — Minecraft 1.21.8 / NeoForge and Fabric (Español)

### Corregido

- **El nucleo de Destruccion ya no entrega los drops en creativo y se puede desactivar en supervivencia.** El nucleo
  da los drops de los bloques que rompe a tu inventario (o a una Bolsa del Vacio activa), pero su descripcion decia
  que los bloques desaparecen en el vacio. En creativo los bloques ahora simplemente se destruyen. La config del
  servidor tiene una opcion nueva, DestructionGivesDrops (activada por defecto), que al desactivarla destruye los
  bloques sin soltar nada tambien en supervivencia. La descripcion del nucleo en el juego ahora dice lo que hace de
  verdad.

## 4.0.12 — Minecraft 1.21.8 / NeoForge and Fabric

### Additions

- **Copper Wand.** A new wand between the stone and the iron one. It has the durability of the copper tools, is
  repaired with copper ingots, places up to 18 blocks and takes the Angel, Destruction and Exchange cores.
- **Energy Upgrade.** Put it on a wand in a crafting grid, like a core (it is crafted with 8 copper ingots around a
  block of redstone). A wand with the upgrade runs on energy instead of durability: it stores up to 100000 FE, every
  block costs 100 FE (both can be changed in the server config) and the item bar shows the charge. It can be charged
  with any charger or cable that speaks FE on the NeoForge and Forge builds, and Team Reborn Energy (built into the mod) on the Fabric builds. The Infinity Wand does not take it.
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

## 4.0.12 — Minecraft 1.21.8 / NeoForge and Fabric (Español)

### Anadido

- **Varita de Cobre.** Una varita nueva entre la de piedra y la de hierro. Tiene la durabilidad de las herramientas de
  cobre, se repara con lingotes de cobre, coloca hasta 18 bloques y admite los nucleos de Angel, Destruccion e
  Intercambio.
- **Mejora de Energia.** Se pone en una varita en una mesa de crafteo, como un nucleo (se fabrica con 8 lingotes de
  cobre alrededor de un bloque de redstone). Una varita con la mejora funciona con energia en vez de durabilidad:
  guarda hasta 100000 FE, cada bloque cuesta 100 FE (ambos se cambian en la config del servidor) y la barra del objeto
  muestra la carga. Se carga con cualquier cargador o cable de FE en las versiones de NeoForge y Forge, y con Team Reborn Energy (incluido en el mod) en las de Fabric. La Varita Infinita no la admite.
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

## 4.0.11 — Minecraft 1.21.8 / NeoForge and Fabric

### Fixes

- **Undoing a destruction no longer duplicates the blocks.** The Destruction core hands the drops of every block it
  breaks to your inventory, but undoing the destruction put the block back without taking them away, so every undo
  left a free copy of each block. Undo now takes back from your inventory the items the destruction handed out, and
  it can only be done while you still carry them. When the drops went to a Void Sack (or what the sack could not hold
  fell in the world), they cannot be taken back and that destruction can no longer be undone.

## 4.0.11 — Minecraft 1.21.8 / NeoForge and Fabric (Español)

### Corregido

- **Deshacer una destruccion ya no duplica los bloques.** El nucleo de Destruccion entrega a tu inventario los drops
  de cada bloque que rompe, pero al deshacer se devolvia el bloque sin quitar esos objetos, asi que cada deshacer
  dejaba una copia gratis de cada bloque. Ahora deshacer te quita del inventario lo que la destruccion habia entregado,
  y solo se puede hacer mientras lo sigas llevando encima. Cuando los drops fueron a una Bolsa del Vacio (o lo que la
  bolsa no pudo guardar cayo al suelo), no se pueden recuperar y esa destruccion ya no se puede deshacer.

## 4.0.10 — Minecraft 1.21.8 / NeoForge and Fabric

### Fixes

- **Shulker boxes placed with the wand kept no contents.** A shulker box that held items came out empty when the
  wand placed it, so its contents were lost. The wand now moves the data of the item (contents, custom name and
  similar) into the placed block, the same way placing the block by hand does.

## 4.0.10 — Minecraft 1.21.8 / NeoForge and Fabric (Español)

### Corregido

- **Las cajas de shulker colocadas con la varita perdian su contenido.** Una caja de shulker con objetos salia
  vacia al colocarla con la varita, y el contenido se perdia. Ahora la varita pasa los datos del objeto (contenido,
  nombre personalizado y similares) al bloque colocado, igual que al colocarlo a mano.

## 4.0.9 — Minecraft 1.21.8 / NeoForge and Fabric

### Fixes

- **The wand no longer places unlimited blocks while you carry a container.** Any carried item that exposes an item
  inventory (backpacks and similar) made the wand treat every block as available. The wand now counts what your
  inventory and the containers in it really hold, and stops when it runs out.

- **Items taken from carried containers are now really consumed.** On Fabric the wand reported the blocks as taken but
  the backpack kept them, so they were never used up. The container is now updated when the wand takes blocks from it.

## 4.0.9 — Minecraft 1.21.8 / NeoForge and Fabric (Español)

### Corregido

- **La varita ya no coloca bloques ilimitados mientras llevas un contenedor.** Cualquier objeto con inventario
  (mochilas y similares) hacia que la varita diera por disponibles todos los bloques. Ahora cuenta lo que de verdad
  hay en tu inventario y en los contenedores que llevas, y se detiene cuando se acaba.

- **Los objetos que se toman de los contenedores ahora se consumen de verdad.** En Fabric la varita daba los bloques
  por tomados pero la mochila los conservaba, asi que nunca se gastaban. Ahora el contenedor se actualiza cuando la
  varita toma bloques de el.

## 4.0.7 — Minecraft 1.21.8 / NeoForge and Fabric

### Additions

- **Fabric build for Minecraft 1.21.8.** This version now ships a NeoForge jar and a Fabric
  jar built from the same code. The Fabric build supports the recipe viewers (JEI, REI, EMI) that exist
  for this version. The integrations with other mods (storage mods, Curios, Botania) are NeoForge only.

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

- **The Fabric mixin configuration was malformed.** The extra client mixins were joined with a literal
  `\n` instead of a line break, which is not valid JSON.

## 4.0.7 — Minecraft 1.21.8 / NeoForge and Fabric (Español)

### Anadido

- **Build de Fabric para Minecraft 1.21.8.** Esta version trae ahora un jar de NeoForge y
  otro de Fabric hechos con el mismo codigo. El build de Fabric soporta los visores de recetas (JEI, REI,
  EMI) que existen para esta version. Las integraciones con otros mods (mods de almacenamiento, Curios,
  Botania) son solo de NeoForge.

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

- **La configuracion de mixins de Fabric estaba mal formada.** Los mixins extra del cliente se unian con
  un `\n` literal en vez de un salto de linea, lo cual no es JSON valido.

## 4.0.0 — Minecraft 1.21.8 / NeoForge

This release is a port of the 1.21 line, brought over for parity across versions: the fixes and
changes below were developed on 1.21.1 and 26.1 and carried here so every supported version
behaves the same.

### Additions

- **Wooden wand.** A new entry-tier wand, below stone: 59 uses, 5 blocks per placement, and no
  core slots at all. It is crafted from **cherry or birch planks** only, and its texture uses the
  same silhouette and shading as the rest of the set, in cherry tones.

- **Golden wand.** It did not exist in this version. It has been added in full - item, config
  entry, texture, recipe, advancement and translations - with the same values as the other
  versions: 32 uses, 60 blocks per placement, angel range 5, destruction limit 15, upgradeable.

### Fixes

- **Blocks from other mods that store data in the item now work with the wands.**
  Functional Storage framed drawers, decorated or not, were the clearest case: the wand
  behaved as if you had none in your inventory and refused to place anything. The supply
  lookup compared what you were carrying against a freshly built, component-less stack, so
  any block carrying data on the item (a frame material, a stored inventory, a mod's own
  configuration) never matched and was counted as zero. The wand now tracks the real
  ItemStack end to end: each variant of an item is supplied, placed and consumed
  separately, keeping its data intact.

- **Placement no longer strips the item's block entity data.** Blocks are now placed in the
  same order vanilla uses in `BlockItem#place`: the block entity saved on the item is
  restored first, then the block is notified with the real stack. Previously both steps
  received a blank stack, so even a block that did get placed came out in its default state.

- **Blocks that draw themselves from model data now render correctly when placed by a wand.**
  Functional Storage drawers were the visible case: a framed drawer placed by the wand appeared
  without its frame until something else forced the chunk to redraw, such as placing a block next
  to it. The wand places blocks server-side only, so the client never runs the placement
  prediction that makes those mods refresh their model data. The wand now tells the client which
  positions to refresh, retrying for the block entities the client has not created yet.

- **The wand preview did not appear when pointing at a drawer.** Titanium, the library behind
  Functional Storage, cancels the block highlight event on its own blocks to draw its per-slot
  outline, and the preview handler was not receiving canceled events, so it never ran.

- **The Void Sack tooltip showed a raw `%1$s`** instead of the toggle key, because the
  translation argument was never passed.

### Changes

- **Golden wand texture redrawn to match the rest of the set.** It was a flat 4-colour
  head while every other wand had a fully shaded one; it now uses the same silhouette and
  the same shading detail as the iron, stone, diamond and netherite wands, in gold.

- **JEI updated to the latest build for this Minecraft version.**

### Documentation

- The README wand table was missing wood, gold and, in some versions, netherite, and several of its
  values did not match the config. It now lists all seven wands with the real values and adds the
  Exchange column. Image links point at this repository instead of the upstream one.
- New images: the wooden and golden wand recipes, and a stats table.

---

## 4.0.0 — Minecraft 1.21.8 / NeoForge (Español)

Esta version es un port de la linea 1.21, traido para mantener la paridad entre versiones: las
correcciones y cambios de abajo se desarrollaron en 1.21.1 y 26.1 y se trasladaron aqui para que
todas las versiones soportadas se comporten igual.

### Anadido

- **Varita de madera.** Nueva varita de entrada, por debajo de la de piedra: 59 usos, 5 bloques
  por colocacion y sin ranuras para nucleos. Se fabrica solo con **tablas de cerezo o abedul**, y su
  textura usa la misma silueta y sombreado que el resto de la familia, en tonos de cerezo.

- **Varita dorada.** No existia en esta version. Se agrego entera - item, entrada de config,
  textura, receta, avance y traducciones - con los mismos valores que en las demas versiones:
  32 usos, 60 bloques por colocacion, alcance de angel 5, limite de destruccion 15, mejorable.

### Corregido

- **Los bloques de otros mods que guardan datos en el item ya funcionan con las varitas.**
  Los cajones enmarcados de Functional Storage, decorados o sin decorar, eran el caso mas
  claro: la varita se comportaba como si no tuvieras ninguno en el inventario y no colocaba
  nada. La busqueda de suministro comparaba lo que llevabas encima contra un stack recien
  construido y sin componentes, asi que cualquier bloque con datos en el item (el material
  del marco, un inventario guardado, la configuracion propia de un mod) nunca coincidia y
  contaba como cero. Ahora la varita lleva el ItemStack real de punta a punta: cada variante
  de un item se suministra, se coloca y se consume por separado, con sus datos intactos.

- **Al colocar ya no se pierden los datos del block entity del item.** Los bloques se
  colocan en el mismo orden que usa vanilla en `BlockItem#place`: primero se restaura el
  block entity guardado en el item y despues se avisa al bloque con el stack real. Antes los
  dos pasos recibian un stack vacio, asi que incluso un bloque que si llegaba a colocarse
  salia en su estado por defecto.

- **Los bloques que se dibujan segun model data ya se ven bien al colocarlos con la varita.**
  El caso visible eran los cajones de Functional Storage: un cajon enmarcado colocado con la varita
  salia sin su marco hasta que algo forzaba a redibujar el chunk, como poner un bloque al lado. La
  varita coloca solo en el servidor, asi que el cliente nunca ejecuta la prediccion de colocacion
  que hace que esos mods refresquen su model data. Ahora la varita le indica al cliente que
  posiciones refrescar, reintentando con los block entities que el cliente todavia no creo.

- **El preview de la varita no aparecia al apuntar a un cajon.** Titanium, la libreria sobre la que
  esta hecho Functional Storage, cancela el evento de resaltado en sus bloques para dibujar su
  propio recuadro de slots, y nuestro handler no recibia eventos cancelados, asi que nunca llegaba
  a ejecutarse.

- **El tooltip de la Void Sack mostraba un `%1$s` crudo** en vez de la tecla, porque nunca se
  pasaba el argumento de la traduccion.

### Cambios

- **Textura de la varita dorada redibujada al estilo del resto.** Tenia un cabezal plano de
  4 colores mientras que las demas lo tenian completamente sombreado; ahora usa la misma
  silueta y el mismo nivel de detalle que las varitas de hierro, piedra, diamante y
  netherita, en dorado.

- **JEI actualizado a la ultima build para esta version de Minecraft.**

### Documentacion

- La tabla de varitas del README no incluia madera, oro ni, en algunas versiones, netherita, y
  varios valores no coincidian con la config. Ahora lista las siete con los valores reales y suma
  la columna de intercambio. Los enlaces de imagenes apuntan a este repositorio y no al original.
- Imagenes nuevas: las recetas de la varita de madera y la dorada, y un cuadro de estadisticas.

---
