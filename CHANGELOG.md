# Changelog

## 4.0.2 — Minecraft 26.2 / NeoForge

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

## 4.1.0 — Minecraft 26.2 / NeoForge

Port to Minecraft 26.2. See the [NeoForge 26.2 primer](https://docs.neoforged.net/primer/docs/26.2/).

### Ported

- **Block preview rendering rewritten for the new feature/submit-node system.** 26.2 deleted
  `MultiBufferSource` and `ShapeRenderer` outright, so the wand preview no longer grabs a
  `VertexConsumer` and draws into it. It now submits a `ShapeOutline` node per block through
  `SubmitNodeCollector#submitShapeOutline`, matching how vanilla draws the block highlight.
  `CustomBlockOutlineRenderer#render` also changed signature (no more buffer source, no more
  translucent-pass flag), so all the per-frame work — reading the held wand, asking the server for
  the preview — moved into the `ExtractBlockOutlineRenderStateEvent` handler, which is where 26.2
  wants it: the renderer now only receives data already copied out of the level.
- **Advancement classes moved packages.** `Criterion` and `InventoryChangeTrigger` are now in
  `net.minecraft.advancements.triggers`, and `ItemPredicate` in `net.minecraft.advancements.predicates`.
- **`Minecraft#setScreen` is gone.** Screens are opened through `Minecraft#gui.setScreen` now
  (the whole screen/HUD split of 26.2).
- Recipe JSONs regenerated with the 26.2 data generators.

### Removed (temporarily)

These integrations do not ship in this version:

| Integration | Mod | Reason |
|---|---|---|
| Wireless Terminal, Portable Cell | Applied Energistics 2 | no 26.2 build |
| Wireless terminals (all variants) | AE2 Wireless Terminal Library | no 26.2 build |
| Wireless Grid | Refined Storage | no 26.2 build |
| — | Refined Storage Curios Integration | no 26.2 build |
| Pulling blocks from Curios slots | Curios | 26.2 build exists but is broken on this NeoForge |

The code is not deleted: it sits in `src/disabled-integrations/`, outside the source set, together
with a README explaining how to switch each one back on. Shulker boxes, bundles, capability
inventories and Sophisticated Backpacks are unaffected.

### Build

- Minecraft `26.2`, NeoForge `26.2.0.53-beta` (declared range: `[26.2.0.40-beta,26.3.0)`).
  The compile target is `.53-beta` and not `.40-beta` only because Sophisticated Core/Backpacks
  for 26.2 refuse to load below it.
- JEI `30.24.0.173`, Sophisticated Core `1.4.101`, Sophisticated Backpacks `3.25.90`.
- NeoGradle `7.1.38`.
- Parchment mappings disabled: there is no 26.2 export yet (latest is 1.21.11 / 26.1). The lines are
  commented in `gradle.properties`, ready to re-enable.
- `neoforge.mods.toml` now declares real version ranges instead of bare versions, which previously
  meant no constraint at all, and uses `bannerFile` instead of the deprecated `logoFile`
  (26.2 split it into `bannerFile` for wide banners and `iconFile` for square icons).

### Changes

- **Exchange core: replacement now stops under a covered block instead of going around it.**
  When the exchange flood-fill reached a block with something sitting on top of it, that block was
  correctly skipped — but the fill kept spreading through the diagonal neighbours, wrapped around
  the obstruction and carried on replacing on the far side. Diagonals now only propagate when both
  orthogonal neighbours are themselves uncovered, so the exchange cuts off right below the covered
  block and stops there. Straight propagation already behaved this way; this only closes the
  diagonal bypass.

---

## 4.0.2 — Minecraft 26.2 / NeoForge (Español)

Port a Minecraft 26.2. Ver el [primer de NeoForge 26.2](https://docs.neoforged.net/primer/docs/26.2/).

### Portado

- **El render del preview de bloques reescrito al nuevo sistema de submit nodes.** 26.2 eliminó
  `MultiBufferSource` y `ShapeRenderer`, así que el preview de la varita ya no pide un
  `VertexConsumer` para dibujar en él. Ahora envía un nodo `ShapeOutline` por bloque mediante
  `SubmitNodeCollector#submitShapeOutline`, igual que hace el juego con el contorno del bloque
  apuntado. `CustomBlockOutlineRenderer#render` también cambió de firma (sin buffer source y sin
  el flag de pasada translúcida), así que todo el trabajo por frame — leer la varita en mano y
  pedir el preview al servidor — se movió al handler de `ExtractBlockOutlineRenderStateEvent`,
  que es donde 26.2 lo quiere: el renderer solo recibe datos ya copiados fuera del nivel.
- **Las clases de logros cambiaron de paquete.** `Criterion` e `InventoryChangeTrigger` están ahora
  en `net.minecraft.advancements.triggers`, e `ItemPredicate` en `net.minecraft.advancements.predicates`.
- **`Minecraft#setScreen` ya no existe.** Las pantallas se abren con `Minecraft#gui.setScreen`
  (la separación pantalla/HUD de 26.2).
- JSONs de recetas regenerados con los data generators de 26.2.

### Quitado (temporalmente)

Estas integraciones no van en esta versión:

| Integración | Mod | Motivo |
|---|---|---|
| Terminal inalámbrica, celda portátil | Applied Energistics 2 | sin build para 26.2 |
| Terminales inalámbricas (todas) | AE2 Wireless Terminal Library | sin build para 26.2 |
| Wireless Grid | Refined Storage | sin build para 26.2 |
| — | Refined Storage Curios Integration | sin build para 26.2 |
| Sacar bloques de los slots de Curios | Curios | hay build 26.2 pero está roto en este NeoForge |

El código no se borró: está en `src/disabled-integrations/`, fuera del source set, con un README que
explica cómo reactivar cada uno. Shulker boxes, bundles, inventarios por capability y Sophisticated
Backpacks no cambian.

### Build

- Minecraft `26.2`, NeoForge `26.2.0.53-beta` (rango declarado: `[26.2.0.40-beta,26.3.0)`).
  Se compila contra `.53-beta` y no `.40-beta` solo porque Sophisticated Core/Backpacks para 26.2
  se niegan a cargar por debajo.
- JEI `30.24.0.173`, Sophisticated Core `1.4.101`, Sophisticated Backpacks `3.25.90`.
- NeoGradle `7.1.38`.
- Parchment desactivado: todavía no hay export para 26.2 (el último es 1.21.11 / 26.1). Las líneas
  quedan comentadas en `gradle.properties`, listas para reactivar.
- `neoforge.mods.toml` declara ahora rangos de versión reales en vez de versiones sueltas, que en la
  práctica no restringían nada, y usa `bannerFile` en vez del deprecado `logoFile`
  (26.2 lo partió en `bannerFile` para banners apaisados e `iconFile` para iconos cuadrados).

### Cambios

- **Núcleo de intercambio: el reemplazo ahora se corta debajo de un bloque tapado en vez de rodearlo.**
  Cuando el relleno del intercambio llegaba a un bloque que tenía algo encima, ese bloque se saltaba
  correctamente, pero el relleno seguía extendiéndose por los vecinos diagonales, rodeaba el
  obstáculo y continuaba reemplazando al otro lado. Ahora las diagonales solo se propagan si los dos
  vecinos ortogonales están a su vez descubiertos, así que el intercambio se corta justo debajo del
  bloque tapado y se detiene ahí. La propagación recta ya funcionaba así; esto solo cierra el atajo
  por diagonal.

---

## 4.0.3 — Minecraft 26.1 / NeoForge

### Additions

- **AE2 Wireless Terminal Library support.** Wands can now pull blocks straight from the terminals added by AE2WTLib (wireless terminal, crafting terminal, pattern terminal, universal terminal...), the same way they already worked with the vanilla AE2 wireless terminal. The integration only loads when `ae2wtlib` is present, so nothing changes if you don't have the mod.
- The AE2WTLib API is now a proper build dependency (`de.mari_023:ae2wtlib_api`), which is what makes the above integration actually ship.

### Fixes

- **Wand cores showed raw translation keys in the tooltip.** With Shift held, the core list read `constructionwand.option.cores.CoreDefault`, `...ItemCoreDestruction`, `...ItemCoreExchange` instead of their names. The tooltip was building the key from the Java class name rather than the core's registry name, so it never matched any language file.
- **The wand tooltip was printed twice.** The whole block (Restriction, Direction, Replacement, Matching, Random, plus the core list) was added both by the item itself and by a client tooltip event, so it appeared a second time below the item ID line.

### Translations

All 14 languages are now at parity — same 116 keys everywhere. Filled in what was missing:

| Language | Added |
|---|---|
| Spanish (CL) | 16 advancements + the full Void Sack set (32 keys) |
| Spanish (CO) | the full Void Sack set (16 keys) |
| Portuguese (BR) | 16 advancements + Void Sack guide entry (17 keys) |
| Spanish (ES, MX), Japanese, Korean, Russian | Void Sack guide entry |

Spanish (AR), German, Swedish, Turkish and Chinese (Simplified) were already complete.

---

## 4.0.3 — Minecraft 26.1 / NeoForge (Español)

### Añadido

- **Compatibilidad con AE2 Wireless Terminal Library.** Las varitas ya pueden sacar bloques directamente de las terminales que añade AE2WTLib (terminal inalámbrica, de fabricación, de patrones, universal...), igual que ya funcionaba con la terminal inalámbrica de AE2. La integración solo se carga si `ae2wtlib` está instalado, así que nada cambia si no tienes el mod.
- La API de AE2WTLib pasa a ser una dependencia real de compilación (`de.mari_023:ae2wtlib_api`), que es lo que hace que la integración anterior llegue realmente al jar.

### Corregido

- **Los núcleos mostraban la clave de traducción en cruda en el tooltip.** Con Shift pulsado, la lista de núcleos ponía `constructionwand.option.cores.CoreDefault`, `...ItemCoreDestruction`, `...ItemCoreExchange` en vez de sus nombres. El tooltip construía la clave a partir del nombre de la clase Java y no del nombre de registro del núcleo, así que nunca coincidía con ningún archivo de idioma.
- **El tooltip de la varita salía duplicado.** El bloque entero (Restricción, Dirección, Reemplazo, Coincidencia, Aleatorio y la lista de núcleos) lo añadían tanto el propio ítem como un evento de tooltip del cliente, y aparecía otra vez debajo de la línea del ID del ítem.

### Traducciones

Los 14 idiomas quedan igualados: las mismas 116 claves en todos. Se completó lo que faltaba:

| Idioma | Añadido |
|---|---|
| Español (CL) | 16 logros + todo el bloque de la Bolsa del Vacío (32 claves) |
| Español (CO) | todo el bloque de la Bolsa del Vacío (16 claves) |
| Portugués (BR) | 16 logros + entrada de guía de la Bolsa del Vacío (17 claves) |
| Español (ES, MX), japonés, coreano, ruso | entrada de guía de la Bolsa del Vacío |

Español (AR), alemán, sueco, turco y chino simplificado ya estaban completos.
