# Changelog

## 4.0.13 — Minecraft 1.21.11 / NeoForge and Fabric

### Fixes

- **The Destruction core no longer hands out the drops in creative mode, and it can be turned off in survival.** The
  core gives the drops of the blocks it breaks to your inventory (or to an active Void Sack), but its description said
  that the blocks disappear into the void. In creative mode the blocks are now simply destroyed. The server config has
  a new option, DestructionGivesDrops (on by default), that destroys the blocks without dropping anything also in
  survival when it is off. The description of the core in the game now says what it really does.

## 4.0.13 — Minecraft 1.21.11 / NeoForge and Fabric (Español)

### Corregido

- **El nucleo de Destruccion ya no entrega los drops en creativo y se puede desactivar en supervivencia.** El nucleo
  da los drops de los bloques que rompe a tu inventario (o a una Bolsa del Vacio activa), pero su descripcion decia
  que los bloques desaparecen en el vacio. En creativo los bloques ahora simplemente se destruyen. La config del
  servidor tiene una opcion nueva, DestructionGivesDrops (activada por defecto), que al desactivarla destruye los
  bloques sin soltar nada tambien en supervivencia. La descripcion del nucleo en el juego ahora dice lo que hace de
  verdad.

## 4.0.12 — Minecraft 1.21.11 / NeoForge and Fabric

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

## 4.0.12 — Minecraft 1.21.11 / NeoForge and Fabric (Español)

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

## 4.0.11 — Minecraft 1.21.11 / NeoForge and Fabric

### Fixes

- **Undoing a destruction no longer duplicates the blocks.** The Destruction core hands the drops of every block it
  breaks to your inventory, but undoing the destruction put the block back without taking them away, so every undo
  left a free copy of each block. Undo now takes back from your inventory the items the destruction handed out, and
  it can only be done while you still carry them. When the drops went to a Void Sack (or what the sack could not hold
  fell in the world), they cannot be taken back and that destruction can no longer be undone.

## 4.0.11 — Minecraft 1.21.11 / NeoForge and Fabric (Español)

### Corregido

- **Deshacer una destruccion ya no duplica los bloques.** El nucleo de Destruccion entrega a tu inventario los drops
  de cada bloque que rompe, pero al deshacer se devolvia el bloque sin quitar esos objetos, asi que cada deshacer
  dejaba una copia gratis de cada bloque. Ahora deshacer te quita del inventario lo que la destruccion habia entregado,
  y solo se puede hacer mientras lo sigas llevando encima. Cuando los drops fueron a una Bolsa del Vacio (o lo que la
  bolsa no pudo guardar cayo al suelo), no se pueden recuperar y esa destruccion ya no se puede deshacer.

## 4.0.10 — Minecraft 1.21.11 / NeoForge and Fabric

### Fixes

- **Shulker boxes placed with the wand kept no contents.** A shulker box that held items came out empty when the
  wand placed it, so its contents were lost. The wand now moves the data of the item (contents, custom name and
  similar) into the placed block, the same way placing the block by hand does.

## 4.0.10 — Minecraft 1.21.11 / NeoForge and Fabric (Español)

### Corregido

- **Las cajas de shulker colocadas con la varita perdian su contenido.** Una caja de shulker con objetos salia
  vacia al colocarla con la varita, y el contenido se perdia. Ahora la varita pasa los datos del objeto (contenido,
  nombre personalizado y similares) al bloque colocado, igual que al colocarlo a mano.

## 4.0.9 — Minecraft 1.21.11 / NeoForge and Fabric

### Fixes

- **The wand no longer places unlimited blocks while you carry a container.** Any carried item that exposes an item
  inventory (backpacks and similar) made the wand treat every block as available. The wand now counts what your
  inventory and the containers in it really hold, and stops when it runs out.

- **Items taken from carried containers are now really consumed.** On NeoForge and Fabric the wand reported the blocks as taken but
  the backpack kept them, so they were never used up. The container is now updated when the wand takes blocks from it.

- **Crash on the block preview with an empty inventory slot.** On NeoForge, looking at a block with the wand while
  any slot of the inventory was empty could crash the game. Empty slots are now skipped.

## 4.0.9 — Minecraft 1.21.11 / NeoForge and Fabric (Español)

### Corregido

- **La varita ya no coloca bloques ilimitados mientras llevas un contenedor.** Cualquier objeto con inventario
  (mochilas y similares) hacia que la varita diera por disponibles todos los bloques. Ahora cuenta lo que de verdad
  hay en tu inventario y en los contenedores que llevas, y se detiene cuando se acaba.

- **Los objetos que se toman de los contenedores ahora se consumen de verdad.** En NeoForge y Fabric la varita daba los bloques
  por tomados pero la mochila los conservaba, asi que nunca se gastaban. Ahora el contenedor se actualiza cuando la
  varita toma bloques de el.

- **Fallo al previsualizar con un hueco vacio en el inventario.** En NeoForge, mirar un bloque con la varita
  mientras algun hueco del inventario estaba vacio podia cerrar el juego. Ahora los huecos vacios se ignoran.

## 4.0.7 — Minecraft 1.21.11 / NeoForge and Fabric

### Additions

- **Fabric build for Minecraft 1.21.11.** This version now ships a NeoForge jar and a Fabric
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

## 4.0.7 — Minecraft 1.21.11 / NeoForge and Fabric (Español)

### Anadido

- **Build de Fabric para Minecraft 1.21.11.** Esta version trae ahora un jar de NeoForge y
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

## 4.0.5 — Minecraft 1.21.11 / NeoForge

This release is a port of the 1.21 line, brought over for parity across versions: the fixes and
changes below were developed on 1.21.1 and 26.1 and carried here so every supported version
behaves the same.

### Additions

- **Wooden wand.** A new entry-tier wand, below stone: 59 uses, 5 blocks per placement, and no
  core slots at all. It is crafted from **cherry or birch planks** only, and its texture uses the
  same silhouette and shading as the rest of the set, in cherry tones.

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

- **The Void Sack tooltip showed a raw `%1$s`** instead of the toggle key, because the
  translation argument was never passed.


### Changes

- **JEI updated to the latest build for this Minecraft version.**

- **Minimum NeoForge raised to what the bundled JEI requires:** NeoForge `21.11.44` / JEI `27.38.0.97`.

### Documentation

- The README wand table was missing wood, gold and, in some versions, netherite, and several of its
  values did not match the config. It now lists all seven wands with the real values and adds the
  Exchange column. Image links point at this repository instead of the upstream one.
- New images: the wooden and golden wand recipes, and a stats table.

---

## 4.0.5 — Minecraft 1.21.11 / NeoForge (Español)

Esta version es un port de la linea 1.21, traido para mantener la paridad entre versiones: las
correcciones y cambios de abajo se desarrollaron en 1.21.1 y 26.1 y se trasladaron aqui para que
todas las versiones soportadas se comporten igual.

### Anadido

- **Varita de madera.** Nueva varita de entrada, por debajo de la de piedra: 59 usos, 5 bloques
  por colocacion y sin ranuras para nucleos. Se fabrica solo con **tablas de cerezo o abedul**, y su
  textura usa la misma silueta y sombreado que el resto de la familia, en tonos de cerezo.

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

- **El tooltip de la Void Sack mostraba un `%1$s` crudo** en vez de la tecla, porque nunca se
  pasaba el argumento de la traduccion.


### Cambios

- **JEI actualizado a la ultima build para esta version de Minecraft.**

- **NeoForge minimo subido al que exige el JEI que acompana esta version:** NeoForge `21.11.44` / JEI `27.38.0.97`.

### Documentacion

- La tabla de varitas del README no incluia madera, oro ni, en algunas versiones, netherita, y
  varios valores no coincidian con la config. Ahora lista las siete con los valores reales y suma
  la columna de intercambio. Los enlaces de imagenes apuntan a este repositorio y no al original.
- Imagenes nuevas: las recetas de la varita de madera y la dorada, y un cuadro de estadisticas.

---

## 4.0.4 — Minecraft 1.21.11 / NeoForge

### Changes

- **Exchange core: replacement now stops under a covered block instead of going around it.**
  When the exchange flood-fill reached a block with something sitting on top of it, that block was
  correctly skipped — but the fill kept spreading through the diagonal neighbours, wrapped around
  the obstruction and carried on replacing on the far side. Diagonals now only propagate when both
  orthogonal neighbours are themselves uncovered, so the exchange cuts off right below the covered
  block and stops there. Straight propagation already behaved this way; this only closes the
  diagonal bypass.

- **Destruction core: broken blocks go straight to your inventory when you have no Void Sack.**
  Previously the drops were spawned in the world and you had to walk over them. Now, if you are not
  holding an active Void Sack, the drops are inserted directly into your inventory, and once the
  inventory is full the remainder is discarded instead of littering the ground. Nothing changes when
  a Void Sack **is** active: the block still drops normally and the sack intercepts the pickup, so
  linked containers and internal storage work exactly as before. Which items a block drops is
  unchanged.

- **Golden wand added.** The item existed in name only: it had no config entry, no recipe, no
  advancement and no translations, and its texture was a flat 4-colour head. It is now complete
  and drawn in the same style as the rest of the set.

---

## 4.0.4 — Minecraft 1.21.11 / NeoForge (Español)

### Cambios

- **Núcleo de intercambio: el reemplazo ahora se corta debajo de un bloque tapado en vez de rodearlo.**
  Cuando el relleno del intercambio llegaba a un bloque que tenía algo encima, ese bloque se saltaba
  correctamente, pero el relleno seguía extendiéndose por los vecinos diagonales, rodeaba el
  obstáculo y continuaba reemplazando al otro lado. Ahora las diagonales solo se propagan si los dos
  vecinos ortogonales están a su vez descubiertos, así que el intercambio se corta justo debajo del
  bloque tapado y se detiene ahí. La propagación recta ya funcionaba así; esto solo cierra el atajo
  por diagonal.

- **Núcleo de destrucción: los bloques rotos van directos al inventario si no llevas la Bolsa del Vacío.**
  Antes los drops aparecían en el mundo y había que pasar por encima a recogerlos. Ahora, si no
  llevas una Bolsa del Vacío activa, los drops se meten directamente en tu inventario y, cuando el
  inventario está lleno, el resto se elimina en vez de quedar tirado por el suelo. Con la Bolsa del
  Vacío **activa** no cambia nada: el bloque suelta sus drops normalmente y la bolsa los intercepta
  al recogerlos, así que los contenedores enlazados y el almacenamiento interno funcionan igual que
  antes. Qué ítems suelta cada bloque no cambia.

- **Varita dorada agregada.** El item existia solo de nombre: no tenia entrada de config, ni
  receta, ni avance, ni traducciones, y su textura era un cabezal plano de 4 colores. Ahora esta
  completa y dibujada con el mismo estilo que el resto de la familia.

---
