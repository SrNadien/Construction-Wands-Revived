# Integraciones aparcadas (Minecraft 26.3)

Estas clases NO se compilan: la carpeta esta fuera de `sourceSets.main`.

| Clase | Mod requerido | Motivo |
|---|---|---|
| `containers/handlers/HandlerWirelessTerminal.java` | Applied Energistics 2 (`ae2`) | sin build para 26.3 |
| `containers/handlers/HandlerPortableCell.java` | Applied Energistics 2 (`ae2`) | sin build para 26.3 |
| `containers/handlers/HandlerWirelessTerminalAE2WTLib.java` | AE2 Wireless Terminal Library (`ae2wtlib`) | sin build para 26.3 |
| `containers/handlers/HandlerWirelessGrid.java` | Refined Storage (`refinedstorage`) | sin build para 26.3 |
| `integrations/curios/CuriosIntegration.java` | Applied Energistics 2 (`ae2`) + Curios | AE2 sin build para 26.3 (Curios ya esta activo) |
| `containers/handlers/HandlerSophisticatedBackpack.java` | Sophisticated Backpacks | sin build para 26.3 (las publicadas declaran "below 26.3") |

JEI (`integrations/jei/`) y Curios (`integrations/curios/CuriosCompat.java`,
`integrations/curios/CuriosHelper.java`) ya volvieron a `src/main/java`.

## Reactivar Sophisticated Backpacks

1. Descomentar `sophisticatedbackpacks_version` y `sophisticatedcore_version` en
   `gradle.properties`, y sus `compileOnly` en `build.gradle`.
2. Volver a anadir las dos entradas comentadas del mapa `replaceProperties` en `build.gradle`.
3. Mover `containers/handlers/HandlerSophisticatedBackpack.java` de vuelta a `src/main/java/...`.
4. Descomentar en `ContainerRegistrar` el import, el metodo `registerSophisticatedBackpacks()`
   y su llamada.

Nota: Refined Storage Curios Integration tampoco tiene build para 26.3, pero el mod
nunca tuvo codigo propio aqui: solo era una dependencia de runtime.

## Reactivar AE2 / AE2WTLib / Refined Storage

1. Descomentar las dependencias en `build.gradle` y los repos `modmaven.dev` /
   `maven.creeperhost.net`.
2. Rellenar los ids de version en `gradle.properties` (`ae2_version_id`,
   `guideme_version_id`, `refinedstorage_version_id`,
   `refinedstorage_curiosintegration_version_id`, `ae2wtlib_version`).
3. Mover las clases de vuelta a `src/main/java/...` respetando el mismo arbol de paquetes
   (incluido `integrations/curios/CuriosIntegration.java`).
4. Descomentar `registerAppliedEnergistics()` / `registerRefinedStorage()` en
   `ContainerRegistrar.java`, tanto los metodos como sus llamadas en `register()`.
5. Volver a anadir las dependencias opcionales `ae2wtlib` / `refinedstorage` en
   `src/main/resources/META-INF/neoforge.mods.toml`.

Ojo: estas clases estan escritas contra las APIs de 26.1. Al reactivarlas habra que
revisarlas contra los cambios de 26.2 y 26.3. En concreto, `CuriosIntegration` recorre los
slots con `getStackInSlot`, que desde Curios 17 devuelve una copia: si llega a modificar el
stack, tiene que devolverlo con `setStackInSlot` (ver `CuriosHelper.useCuriosStacks`).
