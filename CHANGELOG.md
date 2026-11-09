# Changelog

## 4.0.3 — Minecraft 1.20.1 / Forge and Fabric

### Fixes

- **The Destruction core no longer hands out the drops in creative mode, and it can be turned off in survival.** The
  core gives the drops of the blocks it breaks to your inventory (or to an active Void Sack), but its description said
  that the blocks disappear into the void. In creative mode the blocks are now simply destroyed. The server config has
  a new option, DestructionGivesDrops (on by default), that destroys the blocks without dropping anything also in
  survival when it is off. The description of the core in the game now says what it really does.

## 4.0.3 — Minecraft 1.20.1 / Forge and Fabric (Español)

### Corregido

- **El nucleo de Destruccion ya no entrega los drops en creativo y se puede desactivar en supervivencia.** El nucleo
  da los drops de los bloques que rompe a tu inventario (o a una Bolsa del Vacio activa), pero su descripcion decia
  que los bloques desaparecen en el vacio. En creativo los bloques ahora simplemente se destruyen. La config del
  servidor tiene una opcion nueva, DestructionGivesDrops (activada por defecto), que al desactivarla destruye los
  bloques sin soltar nada tambien en supervivencia. La descripcion del nucleo en el juego ahora dice lo que hace de
  verdad.

## 4.0.2 — Minecraft 1.20.1 / Forge and Fabric

### Additions

- **Copper Wand.** A new wand between the stone and the iron one. It has the durability of the copper tools, is
  repaired with copper ingots, places up to 18 blocks and takes the Angel, Destruction and Exchange cores.
- **Energy Upgrade.** Put it on a wand in a crafting grid, like a core (it is crafted with 8 copper ingots around a
  block of redstone). A wand with the upgrade runs on energy instead of durability: it stores up to 100000 FE, every
  block costs 100 FE (both can be changed in the server config) and the item bar shows the charge. It can be charged
  with any charger or cable that speaks FE on the NeoForge and Forge builds, and Team Reborn Energy (built into the mod) on the Fabric builds. The Infinity Wand does not take it.
- **Silk Touch on the wands.** The wands can be enchanted with Silk Touch (enchanting table, anvil or creative). The
  Destruction core then breaks the blocks as a Silk Touch tool would, also when the drops go to a Void Sack.
- **Each core has its own overlay** on the wand (a golden plus for Angel, a cross for Destruction, a double arrow for
  Exchange) instead of the same dot for all of them.

### Fixes

- **Crafting a core into a wand now consumes the core.** In any crafting table, in the inventory grid and in the AE2
  crafting terminal the core stayed in the grid after taking the upgraded wand. The recipe was also installing the core
  in the wand that was still in the grid, so the recipe stopped matching and the ingredients came back.
- **The Exchange core block can only be selected with the Exchange core active.** The selection key (Numpad 7) now
  shows a warning with any other core instead of selecting a block, and the "no block selected" warning of the block
  preview no longer covers the confirmation of the selection.

## 4.0.2 — Minecraft 1.20.1 / Forge and Fabric (Español)

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
  tambien cuando los drops van a una Bolsa del Vacio.
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

## 4.0.1 — Minecraft 1.20.1 / Forge and Fabric

### Fixes

- **Undoing a destruction no longer duplicates the blocks.** The Destruction core hands the drops of every block it
  breaks to your inventory, but undoing the destruction put the block back without taking them away, so every undo
  left a free copy of each block. Undo now takes back from your inventory the items the destruction handed out, and
  it can only be done while you still carry them. When the drops went to a Void Sack (or what the sack could not hold
  fell in the world), they cannot be taken back and that destruction can no longer be undone.

## 4.0.1 — Minecraft 1.20.1 / Forge and Fabric (Español)

### Corregido

- **Deshacer una destruccion ya no duplica los bloques.** El nucleo de Destruccion entrega a tu inventario los drops
  de cada bloque que rompe, pero al deshacer se devolvia el bloque sin quitar esos objetos, asi que cada deshacer
  dejaba una copia gratis de cada bloque. Ahora deshacer te quita del inventario lo que la destruccion habia entregado,
  y solo se puede hacer mientras lo sigas llevando encima. Cuando los drops fueron a una Bolsa del Vacio (o lo que la
  bolsa no pudo guardar cayo al suelo), no se pueden recuperar y esa destruccion ya no se puede deshacer.

## 4.0.0 — Minecraft 1.20.1 / Forge and Fabric

### Additions

- **Fabric port for 1.20.1.** The same gameplay as the Forge build: all the wands and cores, the options
  screen, the keybinds, undo, the block preview, the Void Sack, recipes, advancements and the creative
  tab. JEI, REI and EMI are supported. Shulker boxes, bundles and block inventories work as supply
  containers through Fabric's transfer API.

  Limits of the Fabric build: containers that live inside an item, such as backpacks, are not supported.
  The server configuration is not sent to the clients, so the preview and the durability bar use the
  client's own configuration file. The integrations with Botania, Curios, Applied Energistics 2,
  Sophisticated Backpacks and Refined Storage are Forge only.

### Fixes

- **The Destruction core captures drops with the Void Sack again.** The sack is found anywhere in the
  inventory, not only in your hand. An active sack stores the drops, or sends them to its linked
  container. An inactive sack that is linked to a container still sends to it. Without either, the
  drops go straight to your inventory.


## 4.0.0 — Minecraft 1.20.1 / Forge and Fabric (Español)

### Anadido

- **Port a Fabric para 1.20.1.** La misma jugabilidad que el build de Forge: todas las varitas y nucleos,
  la pantalla de opciones, las teclas, deshacer, el preview de bloques, la Bolsa del Vacio, las recetas,
  los avances y la pestana creativa. Se soportan JEI, REI y EMI. Los shulker box, los bundles y los
  inventarios de bloques funcionan como contenedores de suministro mediante la API de transferencia de
  Fabric.

  Limites del build de Fabric: no se soportan los contenedores que viven dentro de un item, como las
  mochilas. La configuracion del servidor no se envia a los clientes, asi que el preview y la barra de
  durabilidad usan el archivo de configuracion del propio cliente. Las integraciones con Botania, Curios,
  Applied Energistics 2, Sophisticated Backpacks y Refined Storage son solo de Forge.

### Corregido

- **El nucleo de Destruccion vuelve a capturar los drops con la Bolsa del Vacio.** La bolsa se encuentra en
  cualquier parte del inventario, no solo en la mano. Una bolsa activa guarda los drops, o los envia a su
  contenedor vinculado. Una bolsa inactiva vinculada a un contenedor sigue enviando a el. Sin ninguna de las
  dos cosas, los drops van directo al inventario.
