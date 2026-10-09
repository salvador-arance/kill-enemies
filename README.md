# kill-enemies

Juego de consola donde un héroe ataca enemigos y defiende amigos.
Enunciado completo en [ENUNCIADO.md](./ENUNCIADO.md).

## Diseño

- **`Character`:** como pide el enunciado, solo declara `isEnemy()`.
  Lo implementan `Hero`, `Friend` y `Enemy`.
- **`Targetable extends Character`:** añade `receiveAttack()` y `receiveDefense()`.
  Lo implementan `Friend` y `Enemy`, los personajes que pueden recibir acciones del héroe.
- **`TargetsRepository`:** gestiona la lista de `Targetable` (crear, barajar, contar,
  mostrar, añadir y borrar) para que `Main` solo se ocupe del flujo del juego.

## Decisiones que se apartan del enunciado

### El héroe actúa sobre cualquier `Targetable`
El enunciado pide `attack(Enemy)` y `defend(Friend)`, pero también que atacar
a un amigo lo elimine y que defender a un enemigo lo duplique. Con esas firmas
no es posible, así que `attack` y `defend` reciben un `Targetable`.

### La reacción la decide cada clase
En lugar de usar `instanceof` en `Main`, cada personaje implementa cómo reacciona:
- Enemigo atacado: muere. Enemigo defendido: se reproduce.
- Amigo atacado: muere. Amigo defendido: se cura.

Por eso `Targetable` se separa de `Character`: el héroe es un `Character`
pero nadie lo ataca ni lo defiende, y no tiene sentido que implemente esas acciones.

### Se sustituyen `kill()` y `heal()`
Su comportamiento está en `receiveAttack()` y `receiveDefense()`. Los mensajes
se han adaptado al contexto, por ejemplo "¡Me han curado!" en vez de "¡Te he curado!".

### Contadores y listado en el repositorio
El recuento de amigos y enemigos y `showCharacters()` están en `TargetsRepository`
y no en `Main`, porque operan sobre la lista, no sobre el flujo del juego.
