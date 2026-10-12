# kill-enemies

Juego de consola donde un héroe ataca enemigos y defiende amigos. Enunciado completo en [ENUNCIADO.md](./ENUNCIADO.md).

## Cómo ejecutarlo

Requiere Java 21 y Maven.

```bash
mvn compile
java -cp target/classes io.github.salvador_arance.kill_enemies.Main
```

Para pasar los tests: `mvn test`.

## Cómo se juega

### Objetivo
Eliminar a todos los enemigos sin que mueran todos tus amigos y sin que te maten a ti.

### Inicio de partida
- Si existe una partida guardada (`savegame.dat`), se carga y no se pide el nombre.
- Si no, se te pide el nombre del héroe y se crea una lista de **10 personajes** barajados: **5 amigos** y **5 enemigos**.

### Cada ronda
Al principio de cada ronda se muestra el número de amigos, el número de enemigos, tus **impactos** (de 0 a 3) y la lista de personajes numerada. Después eliges una acción:

| Opción | Acción | Qué pasa |
|---|---|---|
| **1** | Atacar | Eliges el número de un personaje. Si es un enemigo, tienes un **25% de fallar**; si aciertas, muere y suma a tu marcador. Si es un amigo, **muere siempre** (¡insensato!). |
| **2** | Defender | Eliges el número de un personaje. Si es un amigo, lo defiendes y suma a tu marcador. Si es un enemigo, **se reproduce**: aparece otro enemigo. |
| **3** | Curarte | Tus impactos vuelven a 0. No necesitas elegir ningún personaje. |

### Peligros: lo que pasa después de tu acción
Tras actuar, el juego resuelve estas tres cosas, por este orden:

1. **Ataque enemigo.** Si quedan enemigos, hay un **50%** de que uno te ataque, y el ataque tiene un **75%** de impactar. Cada impacto suma 1 a tus impactos. Solo hay **un ataque como máximo por ronda**, sin importar cuántos enemigos queden.
2. **Si no defiendes, mueren amigos.** Si pasas **más de dos rondas seguidas sin defender a un amigo** (a partir de la tercera), hay un **60%** de que un amigo aleatorio muera al final de la ronda.
3. **Si no matas, aparecen enemigos.** Si pasas **más de tres rondas seguidas sin matar a un enemigo** (a partir de la cuarta), hay un **75%** de que aparezca un enemigo nuevo.

Cómo cuentan las rondas:
- Defender a un amigo reinicia el contador de rondas sin defender. Matar a un enemigo reinicia el de rondas sin matar.
- **Curarte cuenta como ronda sin defender y sin matar**, al igual que atacar y fallar.
- Los contadores **no se reinician cuando la regla salta**, solo cuando haces la acción correspondiente. Una vez superado el límite, la regla puede volver a activarse en cada ronda siguiente.
- Estas dos reglas no se aplican en la ronda en la que matas al último enemigo.

### Final de la partida
| Resultado | Cuándo |
|---|---|
| **WIN** | No queda ningún enemigo. |
| **GAME OVER** | Llegas a **3 impactos**, o no queda ningún amigo. |

Al terminar se muestra tu marcador: enemigos matados y amigos defendidos. Cuando la partida acaba, se borra la partida guardada.

### Fin de ronda: guardar o salir
Si la partida sigue, al final de cada ronda eliges:

1. **Seguir jugando.**
2. **Guardar la partida y dejar de jugar.**
3. **Eliminar la partida guardada y terminar.**

### Resumen de probabilidades

| Qué | Probabilidad |
|---|---|
| Fallar al atacar a un enemigo | 25% |
| Un enemigo te ataca en una ronda | 50% |
| El ataque enemigo te impacta | 75% |
| Muere un amigo (tras más de 2 rondas sin defender) | 60% |
| Aparece un enemigo (tras más de 3 rondas sin matar) | 75% |

## Extensiones sobre el enunciado

El enunciado deja libertad para añadir características. Las mecánicas de **impactos y curación**, el **fallo al atacar**, los **ataques enemigos** y las **reglas por rondas** (amigos que mueren y enemigos que aparecen) son extensiones propias.

La bossfight está diseñada pero **no implementada** en esta versión. Los detalles están en [BOSSFIGHT.md](./BOSSFIGHT.md).

## Diseño

- **`Character`:** como pide el enunciado, solo declara `isEnemy()`.
  Lo implementan `Hero`, `Friend` y `Enemy`.
- **`Targetable extends Character`:** añade `receiveAttack()` y `receiveDefense()`.
  Lo implementan `Friend` y `Enemy`, los personajes que pueden recibir acciones del héroe.
- **`Hero`:** además del nombre y los marcadores, lleva sus impactos y los contadores de
  rondas sin matar y sin defender, de modo que todo se guarda con él.
- **`TargetsRepository`:** gestiona la lista de `Targetable` (crear, barajar, contar,
  mostrar, añadir y borrar) para que `Main` solo se ocupe del flujo del juego.
- **`Chance`:** clase de utilidad para toda la aleatoriedad (`roll(percent)` para
  tiradas y `pick(bound)` para elegir al azar). Los porcentajes son constantes en las
  clases a las que pertenecen cada regla.
- **`GameState` y `GameStorage`** (paquete `persistence`): el primero agrupa lo que se
  guarda y el segundo lo escribe y lo lee del fichero. Ver [Persistencia](#persistencia).

## Persistencia

El estado de la partida se guarda en el fichero `savegame.dat` (en el directorio
desde el que se ejecuta el juego) mediante serialización de objetos
(`ObjectOutputStream` y `ObjectInputStream`). El fichero es binario y no se sube
al repositorio (`*.dat` está en el `.gitignore`).

- **Qué se guarda:** el héroe (nombre, enemigos matados, amigos defendidos, impactos y
  contadores de rondas) y la lista de personajes en su orden actual.
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
