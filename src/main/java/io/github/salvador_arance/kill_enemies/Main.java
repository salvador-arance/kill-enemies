package io.github.salvador_arance.kill_enemies;
import io.github.salvador_arance.kill_enemies.clases.*;
import io.github.salvador_arance.kill_enemies.interfaces.Character;
import io.github.salvador_arance.kill_enemies.repositories.CharactersRepository;

public class Main {
	static CharactersRepository charactersRepo; 
  public static void main(String[] args) {
    charactersRepo = new CharactersRepository();
    
    int characterIndex = 0;
    for (Character c: charactersRepo.getCharacters()) {
    	++ characterIndex;
    	if (c.isEnemy()) {
    		System.out.println("El personaje " + characterIndex + " es un enemigo! ¡Mátalo!");
    		((Enemy) c).kill();
    	} else {
    		System.out.println("El personaje " + characterIndex + " es un amigo.");
    	}
    }
  }
}
