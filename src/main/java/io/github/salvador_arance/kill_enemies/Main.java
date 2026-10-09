package io.github.salvador_arance.kill_enemies;
import java.util.Scanner;

import io.github.salvador_arance.kill_enemies.clases.*;
import io.github.salvador_arance.kill_enemies.interfaces.Character;
import io.github.salvador_arance.kill_enemies.repositories.CharactersRepository;

public class Main {
  private static final int ATTACK_MODE = 1;
  private static final int DEFEND_MODE = 2;
  
  static Hero hero;
  static CharactersRepository charactersRepo;
  static Scanner scanner;
	
  public static void main(String[] args) {
    charactersRepo = new CharactersRepository();
    hero = new Hero();
    scanner = new Scanner(System.in);
    int gameMode;
    
    do {
    	charactersRepo.showCharacters();
    	if (charactersRepo.enemyCount() == 0) {
    		System.out.println("No quedan enemigos que atacar");
    		return;
    	}
    	
    	gameMode = startGame();
    	
    	if (gameMode == ATTACK_MODE) {
    		heroAttackEnemy();
    	} 
    	
    	if (gameMode == DEFEND_MODE){
    		heroDefendsFriend();
    	}
    	
    	System.out.print("¿Quieres seguir jugando? (S/N)");
    	
    	if (!scanner.nextLine().trim().toUpperCase().equals("S")) {
    		return;
    	}
    	
    }while(true);
    
  }
  
  private static int startGame() {
	  String answer;

	    while (true) {
	        System.out.println("¿Atacar a un enemigo o defender a un amigo?\n"
	                + "(Atacar: introducir '1'. Defender: introducir '2'.)");
	        System.out.print("Tu respuesta: ");

	        answer = scanner.nextLine().trim();

	        if (answer.equals("1") || answer.equals("2")) {
	            return Integer.parseInt(answer);
	        }

	        System.out.println("ERROR: Tu respuesta debe ser 1 o 2.");
	    }
  }
  
  private static void heroAttackEnemy() {
	  int enemyIndex;
	  Enemy enemyAttacked;
	  
	  while (true) {
		  System.out.print("Introduce el índice del enemigo al que quieres atacar: ");
		  try {
			  enemyIndex = Integer.parseInt(scanner.nextLine());
			  enemyIndex --;
			  if (charactersRepo.getCharacters().toArray()[enemyIndex] instanceof Friend) {
				  charactersRepo.deleteFriend(((Friend)charactersRepo.getCharacters().toArray()[enemyIndex]));
				  System.out.println("¡HAS MATADO A UN AMIGO, INSENSATO!");
				  return;
			  } else {
				  enemyAttacked = ((Enemy) charactersRepo.getCharacters().toArray()[enemyIndex]);
				  
				  hero.attack(enemyAttacked);
				  
				  charactersRepo.deleteEnemy(enemyAttacked);
				  
				  System.out.println("Has matado a " + hero.getKillCount() + " enemigos.");
				  return;
			  }
			  
		  } catch (NumberFormatException e) {
			  System.out.println("ERROR: Deberías introducir un número válido.");
		  } catch (ArrayIndexOutOfBoundsException e2) {
			  System.out.println("ERROR: Deberías introducir un número de índice dentro de los márgenes.");
		  }
	  }
  }

  private static void heroDefendsFriend() {
	  int friendIndex;
	  Friend friendDefended;
	  
	  while (true) {
		  System.out.print("Introduce el índice del amigo al que quieres defender: ");
		  
		  try {
			  friendIndex = Integer.parseInt(scanner.nextLine());
			  friendIndex --;
			  
			  if (charactersRepo.getCharacters().toArray()[friendIndex] instanceof Enemy) {
				  charactersRepo.addEnemy();
				  System.out.println("¡Has ayudado a un enemigo y se ha reproducido!");
				  return;
			  } else {
				  friendDefended = ((Friend) charactersRepo.getCharacters().toArray()[friendIndex]);
				  
				  hero.defend(friendDefended);;
				  
				  System.out.println("Has defendido a " + hero.getDefendCount() + " amigos.");
				  return;
			  }
			  
		  } catch (NumberFormatException e) {
			  System.out.println("ERROR: Deberías introducir un número válido.");
		  } catch (ArrayIndexOutOfBoundsException e2) {
			  System.out.println("ERROR: Deberías introducir un número de índice dentro de los márgenes.");
		  }
	  }
  }
}

