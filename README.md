# kill-enemies


## Decisiones de diseño que rompen con el enunciado

    - He ampliado la interfaz: en la última versión del juego, el héroe puede atacar y defender tanto a enemigos como amigos. Como tanto amigos como enemigos reciben la acción, he decidido ampliar la interfaz para qué se decida en la clase cómo reaccionan.
    - Debido a la anterior decisión, me he visto obligado a hacer que el héroe no implemente la interfaz. En primer lugar nunca llegamos a usar isEnemy() en él, y ahora que la interfaz contempla las acciones que los personajes reciben del hero, creo que no tiene ningún sentido que hero lo implemente.
    - He creado un repositorio para manejar lo que pasa con la lista de personajes.
