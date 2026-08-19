# Integraciones aparcadas (Minecraft 26.2)

Estas clases NO se compilan: la carpeta esta fuera de `sourceSets.main`.

| Clase | Mod requerido | Motivo |
|---|---|---|
| `containers/handlers/HandlerWirelessTerminal.java` | Applied Energistics 2 (`ae2`) | sin build para 26.2 |
| `containers/handlers/HandlerPortableCell.java` | Applied Energistics 2 (`ae2`) | sin build para 26.2 |
| `containers/handlers/HandlerWirelessTerminalAE2WTLib.java` | AE2 Wireless Terminal Library (`ae2wtlib`) | sin build para 26.2 |
| `containers/handlers/HandlerWirelessGrid.java` | Refined Storage (`refinedstorage`) | sin build para 26.2 |
| `integrations/curios/CuriosIntegration.java` | Applied Energistics 2 (`ae2`) + Curios | sin build para 26.2 |
| `wand/supplier/CuriosCompat.java` | Curios (`curios`) | hay build 26.2 pero esta roto |
| `wand/supplier/CuriosHelper.java` | Curios (`curios`) | hay build 26.2 pero esta roto |

Nota: Refined Storage Curios Integration tampoco tiene build para 26.2, pero el mod
nunca tuvo codigo propio aqui: solo era una dependencia de runtime.

## Reactivar AE2 / AE2WTLib / Refined Storage

1. Descomentar las dependencias en `build.gradle` y los repos `modmaven.dev` /
   `maven.creeperhost.net`.
2. Rellenar los ids de version en `gradle.properties` (`ae2_version_id`,
   `guideme_version_id`, `refinedstorage_version_id`,
   `refinedstorage_curiosintegration_version_id`, `ae2wtlib_version`).
3. Mover las clases de vuelta a `src/main/java/...` respetando el mismo arbol de paquetes.
4. Descomentar `registerAppliedEnergistics()` / `registerRefinedStorage()` en
   `ContainerRegistrar.java`, tanto los metodos como sus llamadas en `register()`.
5. Volver a anadir las dependencias opcionales `ae2wtlib` / `refinedstorage` en
   `src/main/resources/META-INF/neoforge.mods.toml`.

## Reactivar Curios

1. Descomentar `curios_version` en `gradle.properties`, y en `build.gradle` el repo
   `maven.theillusivec4.top` y las dos lineas `compileOnly`/`runtimeOnly` de Curios.
2. Mover `wand/supplier/CuriosCompat.java` y `wand/supplier/CuriosHelper.java` de vuelta a
   `src/main/java/nadiendev/constructionwand/wand/supplier/`.
3. En `SupplierInventory.java`, descomentar el metodo `getCuriosInv()`, la variable local
   `curios` y la llamada `takeItemsInvList(count, item, curios, true)`.

Ojo: estas clases estan escritas contra las APIs de 26.1. Al reactivarlas habra que
revisarlas contra los cambios de 26.2.
