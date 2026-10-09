package io.github.salvador_arance.kill_enemies;

import java.util.Scanner;

import io.github.salvador_arance.kill_enemies.clases.Hero;
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
			clearConsole();
			charactersRepo.showCharacters();

			gameMode = startGame();

			if (gameMode == ATTACK_MODE) {
				heroActsOnCharacter(true);
			}

			if (gameMode == DEFEND_MODE) {
				heroActsOnCharacter(false);
			}

			if (charactersRepo.friendCount() == 0) {
				System.out.println("Te has cargado a todos tus amigos. GAME OVER.");
				break;
			}

			if (charactersRepo.enemyCount() == 0) {
				System.out.println("No quedan enemigos que atacar. WIN.");
				break;
			}

			System.out.print("¿Quieres seguir jugando? (S/N): ");

			if (!scanner.nextLine().trim().toUpperCase().equals("S")) {
				break;
			}

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
				Character target = charactersRepo.getCharacters().get(index);

				if (attacking) {
					hero.attack(target);
					charactersRepo.delete(target);
				} else {
					hero.defend(target);
					if (target.isEnemy()) {
						charactersRepo.addEnemy();
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
