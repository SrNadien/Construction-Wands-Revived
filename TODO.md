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

## Dependencias

| Dependencia | Version 1.21.1 | Version 1.21.8 | Maven | Estado |
|---|---|---|---|---|
| NeoForge | 21.1.215 | 21.8.51 | `net.neoforged:neoforge:21.8.51` | OK |
| JEI | 19.27.0.340 | 24.2.0.6 | `mezz.jei:jei-1.21.8-neoforge:24.2.0.6` | OK |
| Curios | 9.5.1+1.21.1 | 12.0.0+1.21.8 | `top.theillusivec4.curios:curios-neoforge:12.0.0+1.21.8` | OK |
| Soph. Backpacks | curse maven | 1.21.8-3.25.46.1735 | CurseMaven `curse.maven:sophisticatedbackpacks-422301:8034893` | OK (compileOnly) |
| Soph. Core | curse maven | 1.21.8-1.3.103.1365 | CurseMaven `curse.maven:sophisticatedcore-618298:8039643` | OK (compileOnly) |
| GuideME | 21.1.15 | 21.8.4 | `org.appliedenergistics:guideme:21.8.4` | COMENTADO (dep de AE2) |
| AE2 | 19.2.17 | N/A | No disponible para 1.21.8 | EXCLUIDO |
| Refined Storage | 2.0.1 | N/A | No disponible para 1.21.8 | EXCLUIDO |
| ae2wtlib | 19.4.1 | N/A | No disponible para 1.21.8 | EXCLUIDO |

---

## Progreso

### COMPLETADO

- [x] **Build System** — gradle.properties, build.gradle (NeoGradle 7.1.22), gradle-wrapper (8.14), pack.mcmeta (64), neoforge.mods.toml
- [x] **NBT changes** — `WandUpgrades.java` tag.getList→getListOrEmpty, `WandUpgradesSelectable.java` tag.getByte→getByteOr
- [x] **ContainerRegistrar** — comentados handlers AE2, RS, ae2wtlib
- [x] **build.gradle runs** — `data`→eliminado (run type no existe en NG 7.1), solo client/server
- [x] **Excluir handler files AE2/RS/WTlib del source set** — excluidos en build.gradle
- [x] **Tiers/Tier eliminados** — durabilities hardcodeados (131/250/1561/2031), ItemWandBasic simplificado
- [x] **InteractionResultHolder→InteractionResult** — `ItemWand.use()` actualizado
- [x] **Inventory.items/offhand privados** — refactored a `getItem(slot)` y `getOffhandItem()` en WandUtil
- [x] **BuiltInRegistries.ITEM.get() Optional** — `.getOptional().orElse()` en ReplacementRegistry y WandUpgrades
- [x] **Direction.getNearest() 4 params** — `(int, int, int, Direction)` en WandUtil.fromVector()
- [x] **TooltipContext→Item.TooltipContext** — nueva firma `appendHoverText(ItemStack, TooltipContext, TooltipDisplay, Consumer, TooltipFlag)`
- [x] **SimpleCraftingRecipeSerializer→CustomRecipe.Serializer** — con Factory pattern
- [x] **Data Gen excluido del source set** — necesita rewrite completo para nueva API
- [x] **ClientPacketDistributor** — movido a `net.neoforged.neoforge.client.network.ClientPacketDistributor`
- [x] **RegisterColorHandlersEvent.Item** — stubbed temporalmente, necesita ItemTintSources
- [x] **Sophisticated Backpacks/Core via CurseMaven** — compileOnly con file IDs
- [x] **getNormal()→relative()** — ActionConstruction/ActionDestruction
- [x] **serverLevel() cast** — `(ServerLevel) player.level()` en ContainerTrace
- [x] **@OnlyIn eliminado** — warnings resueltos en ModItems
- [x] **DeferredRegister.Items.registerItem()** — nuevo patrón de registro con `Function<Item.Properties, I>`
- [x] **Ghost block preview render** — reimplementado con vertex emission manual (drawBox/drawLine)
- [x] **Compilación limpia** — BUILD SUCCESSFUL
- [x] **Runtime exitoso** — Minecraft 1.21.8 abre correctamente con el mod cargado

### PENDIENTE

- [x] **Data Gen rewrite para 1.21.8**
  - Archivos reescritos: `ModData.java`, `WandModelProvider.java`, `WandRecipeProvider.java`, `WandAdvancementSubProvider.java`
  - `GatherDataEvent.Server` y `GatherDataEvent.Client` como subclases
  - Item models creados manualmente en `assets/<modid>/items/` + `assets/<modid>/models/item/`
  - Texturas de items visibles correctamente en el juego

- [ ] **Item color registration**
  - `RegisterColorHandlersEvent.Item` reemplazado por `RegisterColorHandlersEvent.ItemTintSources`
  - `ModItems.registerItemColors()` actualmente vacío

- [ ] **Item property registration**
  - `ItemProperties.register` eliminado/reemplazado en 1.21.8
  - `ModItems.registerModelProperties()` actualmente vacío

- [ ] **Repair ingredients en ItemWandBasic**
  - `isValidRepairItem` fue removido al simplificar
  - Re-agregar lógica de reparación

- [ ] **Testing completo**
  - Verificar placement de bloques funcional
  - Verificar undo system
  - Verificar cores (Angel, Destruction)
  - Verificar integración con Curios
  - Verificar integración con Sophisticated Backpacks (si está instalado)
  - Verificar keybinds (OPT key)
