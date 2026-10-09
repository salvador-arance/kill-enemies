package io.github.salvador_arance.kill_enemies;
import java.util.Scanner;

import io.github.salvador_arance.kill_enemies.clases.*;
import io.github.salvador_arance.kill_enemies.interfaces.Character;
import io.github.salvador_arance.kill_enemies.repositories.CharactersRepository;

public class Main {
  private static final int ATTACK_MODE = 1;
  private static final int DEFEND_MODE = 2;
  private static final String NUMBERS = "0987654321";
  static CharactersRepository charactersRepo;
  static Scanner scanner;
	
  public static void main(String[] args) {
    charactersRepo = new CharactersRepository();
    Hero hero = new Hero();
    scanner = new Scanner(System.in);
    int gameMode;
    
    charactersRepo.showCharacters();
    
    do {
    	gameMode = startGame();
    	
    	if (gameMode == ATTACK_MODE) {
    		System.out.print("¿Enemigo a eliminar? Introduce su índice: ");
    		
    		String enemyKillIndex = scanner.nextLine();
    		
    		
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
}
