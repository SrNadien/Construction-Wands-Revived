# Changelog

## 4.0.4 — Minecraft 1.21.1 / NeoForge

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

---

## 4.0.4 — Minecraft 1.21.1 / NeoForge (Español)

### Cambios

- **Núcleo de intercambio: el reemplazo ahora se corta debajo de un bloque tapado en vez de rodearlo.**
  Cuando el relleno del intercambio llegaba a un bloque que tenía algo encima, ese bloque se saltaba
  correctamente, pero el relleno seguía extendiéndose por los vecinos diagonales, rodeaba el
  obstáculo y continuaba reemplazando al otro lado. Ahora las diagonales solo se propagan si los dos
  vecinos ortogonales están a su vez descubiertos, así que el intercambio se corta justo debajo del
  bloque tapado y se detiene ahí. La propagación recta ya funcionaba así; esto solo cierra el atajo
  por diagonal.

- **Núcleo de destrucción: los bloques rotos van directos al inventario si no llevas la Bolsa del Vacío.**
  Antes los drops aparecían en el mundo y había que pasar por encima a recogerlos. Ahora, si no
  llevas una Bolsa del Vacío activa, los drops se meten directamente en tu inventario y, cuando el
  inventario está lleno, el resto se elimina en vez de quedar tirado por el suelo. Con la Bolsa del
  Vacío **activa** no cambia nada: el bloque suelta sus drops normalmente y la bolsa los intercepta
  al recogerlos, así que los contenedores enlazados y el almacenamiento interno funcionan igual que
  antes. Qué ítems suelta cada bloque no cambia.

---

