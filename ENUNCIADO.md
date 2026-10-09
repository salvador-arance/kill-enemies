Crea un proyecto llamado KillEnemies. Debe crear lo siguiente en su interior.

- Interface Character: Con un método llamado isEnemy() que devuelve un booleano
- Clase Friend: Implementa Character. IsEnemy() devuelve false.
- Clase Enemy: Implementa Character. IsEnemy() devuelve true. También implementa le método kill() que muestra este mensaje "Ahhhggg, me mataste, bastardo!".
- Clase Main: que contiene el método principal. Debes crear un arrayList de 10 Characters (5 amigos y 5 enemigos), luego, usando Collections.shuffle(List), aleatoriza el orden de los objetos. A continuación tienes que desplazarte a través de todos los personajes verificando si son enemigos, y si lo son, los matas (llamando al método kill()).

Ahora vamos a crear otra versión de nuestro juego donde nosotros somos un Hero, nos pedirá por pantalla el nombre de nuestro heroe

Implementar una Clase Hero:

- Crea una nueva clase llamada Hero que también implemente la interfaz Character.
- El método isEnemy() debe devolver false.
- Añade un método attack(Enemy enemy) que imprima el mensaje: "¡He atacado a un enemigo!". Este método debe recibir como parámetro un objeto de la clase Enemy y, si se ataca a un enemigo, este debe morir (llamando a su método kill()).
- Añade un método defend(Friend friend) que imprima el mensaje: "¡He defendido a un amigo!"
- El heroe debe de tener un contador de los enemigos matados y de los amigos defendidos.

Contar Amigos y Enemigos:

- En la clase Main, implementa un contador que te permita contar cuántos amigos y enemigos hay en el ArrayList y muéstralo en la consola.
Crear un Método showCharacters():

- Añade un método en la clase Main que muestre todos los personajes en el ArrayList, indicando si son amigos o enemigos. Indica también el indice de la lista para que nuestro heroe pueda decidir a quien mata o a quien defiende.
Añadir Interacción:

- Modifica la clase Main para que, después de barajar los personajes, ofrezca al heroe la opción de atacar a un enemigo (si hay alguno presente) o defender a un amigo (creando un método heal() en la clase Friend que imprima "¡Te he curado!").
- Al matar a un enemigo, este deberá ser eliminado de la lista (simulando que ha salido del juego).
- Si se intenta matar a un amigo debemos quitarlo de la lista también.
- Si se intenta defender a un Enemigo se duplica en la lista.
 
Persistencia de Datos:

- Implementa la capacidad de guardar el estado del juego en un archivo (por ejemplo, utilizando ObjectOutputStream) y cargarlo posteriormente (utilizando ObjectInputStream).
- El formato del fichero lo dejo a vuestra elección.
- Al iniciar el juego, verifica si existe un archivo de estado guardado y, si es así, cárgalo en lugar de crear nuevos personajes.

Extensión del Juego:

- Si te sientes creativo, añade nuevas características al juego, como la posibilidad de añadir nuevos personajes con diferentes habilidades o atributos (por ejemplo, un personaje que pueda curar o un personaje que pueda hacer daño adicional).
