# Integraciones aparcadas (Minecraft 26.3)

Estas clases NO se compilan: la carpeta esta fuera de `sourceSets.main`.

| Clase | Mod requerido | Motivo |
|---|---|---|
| `containers/handlers/HandlerWirelessTerminal.java` | Applied Energistics 2 (`ae2`) | sin build para 26.3 |
| `containers/handlers/HandlerPortableCell.java` | Applied Energistics 2 (`ae2`) | sin build para 26.3 |
| `containers/handlers/HandlerWirelessTerminalAE2WTLib.java` | AE2 Wireless Terminal Library (`ae2wtlib`) | sin build para 26.3 |
| `containers/handlers/HandlerWirelessGrid.java` | Refined Storage (`refinedstorage`) | sin build para 26.3 |
| `integrations/curios/CuriosIntegration.java` | Applied Energistics 2 (`ae2`) + Curios | sin build para 26.3 |
| `wand/supplier/CuriosCompat.java` | Curios (`curios`) | hay build 26.2 pero esta roto; sin build 26.3 |
| `wand/supplier/CuriosHelper.java` | Curios (`curios`) | hay build 26.2 pero esta roto; sin build 26.3 |
| `integrations/jei/ConstructionWandJeiPlugin.java` | Just Enough Items (`jei`) | sin build para 26.3 (`jei-26.3-neoforge` no existe en el maven) |
| `containers/handlers/HandlerSophisticatedBackpack.java` | Sophisticated Backpacks | sin build para 26.3 (las publicadas declaran "below 26.3") |

Mientras JEI no publique para 26.3, las descripciones de items las da **Reliable Recipe
Viewer** (`integrations/rrv/RrvIntegration.java`, en `src/main/java`), que registra
exactamente los mismos textos. Nada de JEI se ha borrado: la clase sigue aqui y sus lineas
en `build.gradle` y `neoforge.mods.toml` estan comentadas, no eliminadas.

## Reactivar Sophisticated Backpacks

1. Descomentar `sophisticatedbackpacks_version` y `sophisticatedcore_version` en
   `gradle.properties`, y sus `compileOnly` en `build.gradle`.
2. Volver a anadir las tres entradas comentadas del mapa `replaceProperties` en `build.gradle`.
3. Mover `containers/handlers/HandlerSophisticatedBackpack.java` de vuelta a `src/main/java/...`.
4. Descomentar en `ContainerRegistrar` el import, el metodo `registerSophisticatedBackpacks()`
   y su llamada.

## Reactivar JEI

1. Descomentar la linea `implementation "mezz.jei:jei-${mcversion}-neoforge:${jei_version}"`
   en `build.gradle` y el bloque `#jei dependency` en `neoforge.mods.toml`.
2. Poner en `gradle.properties` el `jei_version` que corresponda a 26.3.
3. Mover `integrations/jei/` de vuelta a `src/main/java/nadiendev/constructionwand/`.

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
