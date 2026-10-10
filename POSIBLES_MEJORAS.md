# POSIBLES MEJORAS TRAS LA PERSISTENCIA

Estas mejoras se harán **después** de implementar la persistencia.

## Conceptos básicos

- **Ronda:** cada vez que el héroe elige una acción en el modo normal.
- **Impacto:** golpe que recibe el héroe. El héroe lleva un **contador de impactos** (de 0 a 3) y ese contador vive en la clase `Hero`.
- **Muerte del héroe:** al llegar a 3 impactos la partida termina en ese mismo momento (GAME OVER), sin esperar a que acabe la ronda.
- **Curarse:** pone el contador de impactos a 0. El héroe puede curarse en cualquier modo de juego (normal o bossfight).

## Reglas del modo normal

El menú de cada ronda tiene tres acciones: **1. Atacar**, **2. Defender**, **3. Curarse**.

### Orden de una ronda
1. El héroe elige una acción y la ejecuta.
2. Fase de ataque enemigo.
3. Se actualizan los contadores de rondas y se aplican las reglas de "rondas sin...".
4. Se comprueban las condiciones de fin de partida.
5. Menú de fin de ronda (ver "Guardado y salida").

### Atacar a un enemigo puede fallar
- [ ] Al atacar a un enemigo hay un **25% de fallar**. Si falla, el enemigo sigue en la lista y no suma al contador de enemigos matados.

### Los enemigos atacan al héroe
- [ ] En la fase de ataque enemigo, cada ronda hay un **50% de que se lance un ataque** (un solo ataque por ronda, sin importar cuántos enemigos queden).
- [ ] Si se lanza, tiene un **75% de impactar**, igual que los ataques del boss. Si impacta, el contador de impactos del héroe sube en 1.

### Si no defiendes, mueren amigos
- [ ] Si el héroe pasa **más de dos rondas seguidas sin defender a un amigo** (es decir, a partir de la tercera), al final de la ronda hay un **60% de que muera un amigo aleatorio**.
- El contador de rondas sin defender se reinicia a 0 cada vez que el héroe defiende a un amigo.

### Si no matas, aparecen enemigos
- [ ] Si el héroe pasa **más de tres rondas seguidas sin matar a un enemigo** (es decir, a partir de la cuarta), al final de la ronda hay un **75% de que aparezca un enemigo nuevo**.
- El contador de rondas sin matar se reinicia a 0 cada vez que el héroe mata a un enemigo.

## Bossfight

- [ ] Hay un **boss** con **4 puntos de vida**. No se puede curar, y solo se le mata dentro de la bossfight.

### Cuándo aparece
- Aparece en una **ronda aleatoria posterior a las 3 primeras**.
- Si todos los enemigos mueren antes de que haya aparecido, **aparece en ese momento** en vez de ganar la partida.
- **La partida no se puede ganar sin derrotar al boss.**

### Durante la bossfight
- El **modo normal se suspende por completo**: no se ataca ni se defiende a amigos y enemigos, no aparecen enemigos nuevos, no mueren amigos y los contadores de rondas no avanzan.
- El héroe solo tiene dos acciones: **1. Atacar al boss**, **2. Curarse**.
- Atacar al boss tiene un **75% de impactar**. Cuando el boss recibe 4 impactos, muere y el héroe **gana la partida**.
- En su turno, el boss tiene un **50% de lanzar un ataque**, y el ataque tiene un **75% de impactar** al héroe.
- Curarse **no se anula** aunque el boss ataque ese mismo turno: el contador se pone a 0 y, si el ataque del boss impacta después, queda en 1.
- Con 3 impactos en el héroe, la partida termina al instante.

### Orden de los turnos
1. Aparece el boss con un mensaje en la terminal.
2. **Turno del boss:** 50% de lanzar un ataque (si no lo lanza, es solo un amago).
3. **Turno del héroe:** atacar o curarse.
4. **Turno del boss:** igual que el punto 2.
5. Se repiten los turnos 3 y 4 hasta que muera uno de los dos.

## Guardado y salida

Este apartado forma parte de la persistencia, no de las mejoras.

- Al final de cada ronda normal el jugador elige:
  1. **Seguir jugando.**
  2. **Guardar la partida y dejar de jugar.**
  3. **Eliminar la partida guardada y terminar.**
- **Durante la bossfight no se puede guardar.** Si el jugador sale, se borra el progreso.
- Mientras no exista la persistencia, salir reinicia todo.

## Checklist de implementación (en este orden)

- [ ] Menú de tres acciones y curarse (contador de impactos en `Hero`)
- [ ] Fallo del 25% al atacar a un enemigo
- [ ] Ataques enemigos cada ronda
- [ ] Muerte de un amigo si no se defiende
- [ ] Aparición de un enemigo si no se mata
- [ ] Bossfight

## Decisiones que he tomado por ti (corrígelas si no son lo que quieres)

- Un solo ataque enemigo por ronda con un 50%, no uno por cada enemigo (con cinco enemigos, el héroe moriría casi seguro).
- El 25% de fallo solo se aplica a atacar enemigos, no a amigos.
- Una ronda en la que te curas cuenta como ronda sin defender y sin matar.
- Los contadores de rondas no se reinician cuando se activa su efecto, solo cuando haces la acción que los reinicia.
- El boss ataca con un 50% en **todos** sus turnos, no solo el primero.
- Durante la bossfight el héroe necesita una opción para salir (y borrar el progreso), además de atacar y curarse.
- Los personajes que quedaban en la lista cuando aparece el boss se quedan congelados y ya no importan.
- Falta fijar un límite superior para la ronda aleatoria del boss (propuesta: entre la ronda 4 y la 10).
