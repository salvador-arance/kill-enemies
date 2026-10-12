# kill-enemies

Juego de consola donde un héroe ataca enemigos y defiende amigos.

## Diseño

- **`Character`:** como pide el enunciado, solo declara `isEnemy()`.
  Lo implementan `Hero`, `Friend` y `Enemy`.
- **`Targetable extends Character`:** añade `receiveAttack()` y `receiveDefense()`.
  Lo implementan `Friend` y `Enemy`, los personajes que pueden recibir acciones del héroe.
- **`TargetsRepository`:** gestiona la lista de `Targetable` (crear, barajar, contar,
  mostrar, añadir y borrar) para que `Main` solo se ocupe del flujo del juego.
- **`GameState` y `GameStorage`** (paquete `persistence`): el primero agrupa lo que se
  guarda y el segundo lo escribe y lo lee del fichero. Ver [Persistencia](#persistencia).

## Persistencia

El estado de la partida se guarda en el fichero `savegame.dat` (en el directorio
desde el que se ejecuta el juego) mediante serialización de objetos
(`ObjectOutputStream` y `ObjectInputStream`). El fichero es binario y no se sube
al repositorio (`*.dat` está en el `.gitignore`).

- **Qué se guarda:** el héroe (nombre y contadores de enemigos matados y amigos
  defendidos) y la lista de personajes en su orden actual.
- **Cómo:** `Character` extiende `Serializable`, así que `Hero`, `Friend` y `Enemy`
  lo son. `GameState` agrupa el héroe y el `TargetsRepository` en un único objeto, y
  `GameStorage` se encarga de guardarlo, cargarlo y borrarlo.
- **Al iniciar:** si existe `savegame.dat` y se puede leer, se carga la partida y no
  se pide el nombre. Si no existe o está dañado, se avisa y se empieza una partida nueva.
- **Cuándo se guarda:** al final de cada ronda hay un menú con tres opciones:
  seguir jugando, guardar la partida y salir, o eliminar la partida guardada y terminar.
- **Cuándo se borra:** al elegir la opción de eliminar y al terminar la partida,
  ya sea ganando o perdiendo, para no cargar una partida acabada.

## Decisiones que se apartan del enunciado

### El héroe actúa sobre cualquier `Targetable`
El enunciado pide `attack(Enemy)` y `defend(Friend)`, pero también que atacar
a un amigo lo elimine y que defender a un enemigo lo duplique. Con esas firmas
no es posible, así que `attack` y `defend` reciben un `Targetable`.

En lugar de usar `instanceof` en `Main`, cada personaje implementa cómo reacciona:
- Enemigo atacado: muere. Enemigo defendido: se reproduce.
- Amigo atacado: muere. Amigo defendido: se cura.

Por eso `Targetable` se separa de `Character`: el héroe es un `Character`
pero nadie lo ataca ni lo defiende, y no tiene sentido que implemente esas acciones.

### Se sustituyen `kill()` y `heal()`
Su comportamiento está en `receiveAttack()` y `receiveDefense()`. Los mensajes
se han adaptado al contexto, por ejemplo "¡Me han curado!" en vez de "¡Te he curado!".

### Contadores y listado en el repositorio
El recuento de amigos y enemigos y `showTargets()` están en `TargetsRepository`
y no en `Main`, porque operan sobre la lista, no sobre el flujo del juego.
