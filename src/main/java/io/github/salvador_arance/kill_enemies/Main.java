package io.github.salvador_arance.kill_enemies;
import io.github.salvador_arance.kill_enemies.clases.*;
import io.github.salvador_arance.kill_enemies.clases.Character;

public class Main {
	static CharactersRepository personajesRepo; 
  public static void main(String[] args) {
    personajesRepo = new CharactersRepository();
    
    int characterIndex = 0;
    for (Character c: personajesRepo.getCharacters()) {
    	++ characterIndex;
    	if (c.isEnemy()) {
    		System.out.println("El personaje " + characterIndex + " es un enemigo! ¡Mátalo!");
    		System.out.println((((Enemy) c).kill()));
    	} else {
    		System.out.println("El personaje " + characterIndex + " es un amigo.");
    	}
    }
  }
}
