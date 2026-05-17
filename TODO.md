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
| Soph. Backpacks | curse maven | 1.21.8-3.25.46.1735 | `net.p3pp3rf1y:sophisticatedbackpacks:1.21.8-3.25.46.1735` | PENDIENTE REPO |
| Soph. Core | curse maven | 1.21.8-1.3.103.1365 | `sophisticatedcore:sophisticatedcore:1.21.8-1.3.103.1365` | PENDIENTE REPO |
| GuideME | 21.1.15 | 21.8.4 | `org.appliedenergistics:guideme:21.8.4` | COMENTADO (dep de AE2) |
| AE2 | 19.2.17 | N/A | No disponible para 1.21.8 | COMENTADO |
| Refined Storage | 2.0.1 | N/A | No disponible para 1.21.8 | COMENTADO |
| ae2wtlib | 19.4.1 | N/A | No disponible para 1.21.8 | COMENTADO |

---

## Progreso

### COMPLETADO

- [x] **Build System** — gradle.properties, build.gradle (NeoGradle 7.1.22), gradle-wrapper (8.14), pack.mcmeta (64), neoforge.mods.toml
- [x] **NBT changes** — `WandUpgrades.java` tag.getList→getListOrEmpty, `WandUpgradesSelectable.java` tag.getByte→getByteOr
- [x] **ContainerRegistrar** — comentados handlers AE2, RS, ae2wtlib
- [x] **build.gradle runs** — `data`→eliminado (run type no existe en NG 7.1), solo client/server

### PENDIENTE

- [ ] **Eliminar handler files AE2/RS/WTlib del source set**
  - `HandlerPortableCell.java` — usa `appeng.*` (AE2 no disponible)
  - `HandlerWirelessTerminal.java` — usa `appeng.*` (AE2 no disponible)
  - `HandlerWirelessTerminalAE2WTLib.java` — usa `appeng.*` + `de.mari_023.*` (AE2+WTlib no disponibles)
  - `HandlerWirelessGrid.java` — usa `com.refinedmods.*` (RS no disponible)
  - Solución: excluir del source set en build.gradle o renombrar a `.java.disabled`
  - **Paquetes afectados:** `appeng.api.config`, `appeng.api.stacks`, `appeng.api.storage`, `appeng.items.tools.powered`, `com.refinedmods.refinedstorage.*`

- [ ] **Tiers/Tier eliminados en 1.21.5+**
  - `net.minecraft.world.item.Tiers` → NO EXISTE
  - `net.minecraft.world.item.Tier` → NO EXISTE
  - Archivos: `ConfigServer.java`, `ItemWandBasic.java`, `ModItems.java`
  - Solución: hardcodear durabilities (Stone=131, Iron=250, Diamond=1561, Netherite=2031)
  - `ItemWandBasic` quitar referencia a Tier, hardcodear `isValidRepairItem` o simplificar
  - **Paquetes eliminados:** `net.minecraft.world.item.Tier`, `net.minecraft.world.item.Tiers`

- [ ] **InteractionResultHolder eliminado en 1.21.2+**
  - `net.minecraft.world.InteractionResultHolder` → NO EXISTE
  - Unificado en `net.minecraft.world.InteractionResult`
  - Archivo: `ItemWand.java` método `use()`
  - Firma vieja: `InteractionResultHolder<ItemStack> use(Level, Player, InteractionHand)`
  - Firma nueva: `InteractionResult use(Level, Player, InteractionHand)` (o similar)
  - **Paquete eliminado:** `net.minecraft.world.InteractionResultHolder`

- [ ] **Inventory.items/offhand ahora privados en 1.21.5+**
  - `Inventory.items` → privado, usar `Inventory.getItems()` o `Inventory.getSlot()`
  - `Inventory.offhand` → NO EXISTE, usar `Inventory.getItem(36+hotbarSize)` o similar
  - Archivos: `WandUtil.java` (getHotbar, getHotbarWithOffhand, getMainInv, getFullInv, countItem), `SupplierInventory.java`
  - **Campos privados:** `net.minecraft.world.entity.player.Inventory.items`, `net.minecraft.world.entity.player.Inventory.offhand`

- [ ] **BuiltInRegistries.ITEM.get() retorna Optional en 1.21.2+**
  - `BuiltInRegistries.ITEM.get(ResourceLocation)` → retorna `Optional<Reference<Item>>`
  - Archivos: `ReplacementRegistry.java:26`, `WandUpgrades.java:39`
  - Solución: usar `.orElse(Items.AIR)` o `.orElseThrow()`
  - **Paquete:** `net.minecraft.core.registries.BuiltInRegistries`

- [ ] **Direction.getNearest(double,double,double) eliminado**
  - Firma vieja: `Direction.getNearest(double x, double y, double z)` → 3 doubles
  - Firma nueva: `Direction.getNearest(Vec3i, Direction)` o `Direction.getNearest(int, int, int, Direction)`
  - Archivo: `WandUtil.java:225` (fromVector)
  - Solución: usar `Direction.getNearest(new Vec3(vector.x, vector.y, vector.z))` o similar
  - **Paquete:** `net.minecraft.core.Direction`

- [ ] **TooltipContext eliminado/movido en 1.21.8**
  - `net.minecraft.world.item.TooltipContext` → NO EXISTE en 1.21.8
  - Verificar nueva firma de `appendHoverText` en `Item`
  - Archivos: `ItemCore.java`, `ItemWand.java`
  - **Paquete eliminado:** `net.minecraft.world.item.TooltipContext`

- [ ] **SimpleCraftingRecipeSerializer eliminado en 1.21.8**
  - `net.minecraft.world.item.crafting.SimpleCraftingRecipeSerializer` → NO EXISTE
  - Archivos: `ModRecipes.java`, `RecipeGenerator.java`
  - Migrar a nuevo sistema de recetas custom (posiblemente `SpecialRecipeSerializer` o similar)
  - **Paquete eliminado:** `net.minecraft.world.item.crafting.SimpleCraftingRecipeSerializer`

- [ ] **Data Gen refactorizado en 1.21.5+**
  - `ExistingFileHelper` → movido/eliminado, buscar en `net.neoforged.neoforge.data.*`
  - `AdvancementProvider` → movido/eliminado
  - `ItemModelProvider` → movido de `net.neoforged.neoforge.client.model.generators`
  - `EventBusSubscriber(bus = EventBusSubscriber.Bus.MOD)` → sintaxis cambió
  - `GatherDataEvent` → posiblemente renombrado o firma diferente
  - Archivos: `ModData.java`, `AdvancementGenerator.java`, `ItemModelGenerator.java`, `RecipeGenerator.java`
  - **Paquetes afectados:** `net.neoforged.neoforge.common.data`, `net.neoforged.neoforge.client.model.generators`

- [ ] **ClientPacketDistributor paquete incorrecto**
  - `net.neoforged.neoforge.network.client.ClientPacketDistributor` → NO EXISTE en ese path
  - Verificar paquete correcto en NeoForge 21.8.x
  - Alternativa: puede ser `net.neoforged.neoforge.network.PacketDistributor` con otro método
  - Archivo: `ModMessages.java`
  - **Paquete afectado:** `net.neoforged.neoforge.network.client`

- [ ] **RegisterColorHandlersEvent.Item firma cambiada**
  - `RegisterColorHandlersEvent.Item` → la inner class `Item` cambió o se movió
  - Archivo: `ModItems.java:63`
  - **Paquete:** `net.neoforged.neoforge.client.event.RegisterColorHandlersEvent`

- [ ] **Configurar Maven repo para Sophisticated Backpacks/Core**
  - Coordenadas conocidas:
    - `net.p3pp3rf1y:sophisticatedbackpacks:1.21.8-3.25.46.1735`
    - `sophisticatedcore:sophisticatedcore:1.21.8-1.3.103.1365`
  - Falta: URL del repositorio Maven público
  - Opciones: GitHub Packages (requiere auth), Modrinth Maven, CurseForge Maven (deshabilitado 3rd party)
  - Pendiente: investigar repo público sin autenticación

- [ ] **Compilar y debuggear errores restantes**
  - Ejecutar `./gradlew compileJava` después de cada fix
  - Iterar hasta compilación limpia
  - Luego verificar data gen (`./gradlew runData`)
  - Luego verificar runtime (`./gradlew runClient`)

---

## Notas tecnicas

### NBT (CompoundTag) — cambio en 1.21.5+
```java
// 1.21.1 (viejo):
ListTag list = tag.getList("key", Tag.TAG_STRING);
byte val = tag.getByte("key");

// 1.21.5+ (nuevo):
ListTag list = tag.getListOrEmpty("key");
byte val = tag.getByteOr("key", (byte) 0);
```

### Networking — cambio en 1.21.6+
```java
// 1.21.1 (viejo):
PacketDistributor.sendToServer(message);

// 1.21.6+ (nuevo) — paquete exacto por verificar:
ClientPacketDistributor.sendToServer(message);
// O puede seguir siendo PacketDistributor pero con diferente API
```

### Tier/Durability — cambio en 1.21.5+
```java
// 1.21.1 (viejo):
new ItemWandBasic(propWand(), Tiers.STONE);
// Dentro: super(properties.durability(tier.getUses()));

// 1.21.5+ (nuevo):
new ItemWandBasic(propWand(), 131); // hardcodear durability
// Dentro: super(properties.component(DataComponents.MAX_DAMAGE, uses));
```

### Inventory — cambio en 1.21.5+
```java
// 1.21.1 (viejo):
player.getInventory().items       // NonNullList<ItemStack>
player.getInventory().offhand     // NonNullList<ItemStack>

// 1.21.5+ (nuevo):
player.getInventory().getItems()  // o similar — verificar API
```

### BuiltInRegistries — cambio en 1.21.2+
```java
// 1.21.1 (viejo):
Item item = BuiltInRegistries.ITEM.get(resourceLocation);

// 1.21.2+ (nuevo):
Item item = BuiltInRegistries.ITEM.getOptional(resourceLocation).orElse(Items.AIR);
// O: BuiltInRegistries.ITEM.getValue(resourceLocation);
```

### InteractionResult — cambio en 1.21.2+
```java
// 1.21.1 (viejo):
public InteractionResultHolder<ItemStack> use(Level world, Player player, InteractionHand hand) {
    return InteractionResultHolder.success(stack);
}

// 1.21.2+ (nuevo):
public InteractionResult use(Level world, Player player, InteractionHand hand) {
    return InteractionResult.SUCCESS;
}
```

### Direction — cambio en 1.21.5+
```java
// 1.21.1 (viejo):
Direction.getNearest(vector.x, vector.y, vector.z);

// 1.21.5+ (nuevo):
Direction.getNearest((int)vector.x, (int)vector.y, (int)vector.z, Direction.UP);
// O usar Vec3i
```

### Data Generation — cambio en 1.21.5+
- NeoForge 1.21.5+ separó client y server data gen
- `GatherDataEvent` puede haberse dividido en `GatherClientDataEvent` + `GatherServerDataEvent`
- `ExistingFileHelper` puede haberse movido a otro paquete
- Verificar: `net.neoforged.neoforge.data.event.*` o similar
