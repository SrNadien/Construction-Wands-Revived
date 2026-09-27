# Porte Construction Wands Revived: 1.21.1 → 1.21.8

## Versiones objetivo

```
Minecraft:      1.21.8
NeoForge:       21.8.51
NeoGradle:      7.1.22
Gradle:         8.14
Java:           21
Pack format:    64
Parchment:      2025.07.20
```

---

## PENDIENTE

- [ ] **Overlay de core en wands** — BLOQUEADO, necesita más investigación
  - `CoreTintSource` registrado via `RegisterColorHandlersEvent.ItemTintSources` ✓
  - `HasCoreProperty` registrado via `RegisterConditionalItemModelPropertyEvent` ✓
  - `ItemTintSources.CODEC` usa `.dispatch()` con key `"type"` — tint JSONs requieren `{"type":"id"}`
  - `ConditionalItemModelProperties.MAP_CODEC` usa `.dispatchMap()` con key `"property"` — da error `Input does not contain a key [type]: MapLike[{}]`
  - Intentado: `minecraft:condition` con `minecraft:has_component`, `constructionwand:has_core`, y string property — todos fallan
  - Intentado: overlay siempre presente con `CoreTintSource` retornando alpha=0 sin core — tints con `{"type":"minecraft:constant","value":-1}` para layer0 falla igual
  - **Posible solución**: Encontrar un ejemplo vanilla de item def con tints o conditional en 1.21.8 y copiar el formato exacto
  - **Archivos relevantes**: `CoreTintSource.java`, `HasCoreProperty.java`, `items/*.json`, `models/item/*_overlay.json`

- [ ] **Testing completo**
  - Verificar placement de bloques funcional
  - Verificar undo system
  - Verificar cores (Angel, Destruction)
  - Verificar integración con Curios
  - Verificar integración con Sophisticated Backpacks (si está instalado)
  - Verificar keybinds (OPT key)
