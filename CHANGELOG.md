# Changelog

## 4.0.2 — Minecraft 1.21.11 / NeoForge

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

- **The Void Sack tooltip showed a raw `%1$s`** instead of the toggle key, because the
  translation argument was never passed.


### Changes

- **Golden wand texture redrawn to match the rest of the set.** It was a flat 4-colour
  head while every other wand had a fully shaded one; it now uses the same silhouette and
  the same shading detail as the iron, stone, diamond and netherite wands, in gold.

- **JEI updated to the latest build for this Minecraft version.**

- **Minimum NeoForge raised to what the bundled JEI requires:** NeoForge `21.11.44` / JEI `27.38.0.97`.

### Documentation

- The README wand table was missing wood, gold and, in some versions, netherite, and several of its
  values did not match the config. It now lists all seven wands with the real values and adds the
  Exchange column. Image links point at this repository instead of the upstream one.
- New images: the wooden and golden wand recipes, and a stats table.

---

## 4.0.2 — Minecraft 1.21.11 / NeoForge (Español)

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

- **El tooltip de la Void Sack mostraba un `%1$s` crudo** en vez de la tecla, porque nunca se
  pasaba el argumento de la traduccion.


### Cambios

- **Textura de la varita dorada redibujada al estilo del resto.** Tenia un cabezal plano de
  4 colores mientras que las demas lo tenian completamente sombreado; ahora usa la misma
  silueta y el mismo nivel de detalle que las varitas de hierro, piedra, diamante y
  netherita, en dorado.

- **JEI actualizado a la ultima build para esta version de Minecraft.**

- **NeoForge minimo subido al que exige el JEI que acompana esta version:** NeoForge `21.11.44` / JEI `27.38.0.97`.

### Documentacion

- La tabla de varitas del README no incluia madera, oro ni, en algunas versiones, netherita, y
  varios valores no coincidian con la config. Ahora lista las siete con los valores reales y suma
  la columna de intercambio. Los enlaces de imagenes apuntan a este repositorio y no al original.
- Imagenes nuevas: las recetas de la varita de madera y la dorada, y un cuadro de estadisticas.

---

