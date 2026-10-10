package io.github.salvador_arance.kill_enemies;

import java.util.Scanner;

import io.github.salvador_arance.kill_enemies.clases.Hero;
import io.github.salvador_arance.kill_enemies.interfaces.Targetable;
import io.github.salvador_arance.kill_enemies.repositories.TargetsRepository;

public class Main {
	private static final int ATTACK_MODE = 1;
	private static final int DEFEND_MODE = 2;

	static Hero hero;
	static TargetsRepository targetsRepo;
	static Scanner scanner;

	public static void main(String[] args) {
		targetsRepo = new TargetsRepository();
		scanner = new Scanner(System.in);
		int gameMode;
		String heroName; 
		
		while (true) {
			System.out.print("Elige un nombre para el héroe: ");
			heroName = scanner.nextLine().trim();
			
			
			if (heroName.isEmpty()) {
				System.out.println("Introduce un nombre, por favor.");
			} else {
				break;
			}
		}
		
		hero = new Hero(heroName);
		
		do {
			System.out.println("Número de amigos: " + targetsRepo.friendCount()
					+ "\n" + "Número de enemigos: " + targetsRepo.enemyCount() + "\n");
			targetsRepo.showTargets();

			gameMode = startGame();

			if (gameMode == ATTACK_MODE) {
				heroActsOnCharacter(true);
			}

			if (gameMode == DEFEND_MODE) {
				heroActsOnCharacter(false);
			}

			if (targetsRepo.friendCount() == 0) {
				System.out.println("Te has cargado a todos tus amigos. GAME OVER, " + hero.getName());
				break;
			}

			if (targetsRepo.enemyCount() == 0) {
				System.out.println("No quedan enemigos que atacar. WIN, " + hero.getName());
				break;
			}

			System.out.print("¿Quieres seguir jugando? (S/N): ");

			if (!scanner.nextLine().trim().toUpperCase().equals("S")) {
				break;
			}
			
			clearConsole();
		} while (true);

		showFinalScore();

		scanner.close();
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

	private static void heroActsOnCharacter(boolean attacking) {
		while (true) {
			System.out.print("Introduce el índice del personaje: ");
			try {
				int index = Integer.parseInt(scanner.nextLine()) - 1;
				Targetable target = targetsRepo.getTargets().get(index);

				if (attacking) {
					hero.attack(target);
					targetsRepo.delete(target);
				} else {
					hero.defend(target);
					if (target.isEnemy()) {
						targetsRepo.addEnemy();
					}
				}
				return;
			} catch (NumberFormatException e) {
				System.out.println("ERROR: Deberías introducir un número válido.");
			} catch (IndexOutOfBoundsException e) {
				System.out.println("ERROR: Deberías introducir un índice dentro de los márgenes.");
			}
		}
	}

	private static void clearConsole() {
		for (int i = 0; i < 100; i++) {
			System.out.println();
		}
	}

	private static void showFinalScore() {
		System.out.println("Has matado " + hero.getKillCount() + " enemigos.");
		System.out.println("Has defendido " + hero.getDefendCount() + " amigos.");
	}
}
