# Changelog

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
