package io.github.salvador_arance.kill_enemies;

import java.nio.file.Path;
import java.util.Optional;
import java.util.Scanner;

import io.github.salvador_arance.kill_enemies.clases.Chance;
import io.github.salvador_arance.kill_enemies.clases.Hero;
import io.github.salvador_arance.kill_enemies.interfaces.Targetable;
import io.github.salvador_arance.kill_enemies.persistence.GameState;
import io.github.salvador_arance.kill_enemies.persistence.GameStorage;
import io.github.salvador_arance.kill_enemies.repositories.TargetsRepository;

public class Main {
	private static final int ATTACK_MODE = 1;
	private static final int SAVE_OPTION = 2;
	private static final int DELETE_OPTION = 3;

	private static final int HEAL_MODE = 3;

	private static final int ENEMY_ATTACK_CHANCE = 50;
	private static final int ENEMY_HIT_CHANCE = 75;
	private static final int FRIEND_DEATH_CHANCE = 60;
	private static final int ENEMY_SPAWN_CHANCE = 75;

	private static final int ROUNDS_WITHOUT_DEFENDING_LIMIT = 2;
	private static final int ROUNDS_WITHOUT_KILLING_LIMIT = 3;

	private static final String FILE_PATH = "savegame.dat";

	static GameStorage storage;
	static Hero hero;
	static TargetsRepository targetsRepo;
	static Scanner scanner;

	public static void main(String[] args) {
		storage = new GameStorage(Path.of(FILE_PATH));
		Optional<GameState> saved = storage.load();
		scanner = new Scanner(System.in);

		if (saved.isPresent()) {
			hero = saved.get().getHero();
			targetsRepo = saved.get().getTargetsRepo();
			System.out.println("Partida cargada. Bienvenido de nuevo, " + hero.getName());
		} else {
			targetsRepo = new TargetsRepository();
			hero = new Hero(askHeroName());
		}

		int gameMode;

		do {
			System.out.println("Número de amigos: " + targetsRepo.friendCount() + "\n" + "Número de enemigos: "
					+ targetsRepo.enemyCount() + "\n" + "\nImpactos: " + hero.getHits() + "/3\n");
			targetsRepo.showTargets();

			gameMode = startGame();

			int killsBefore = hero.getKillCount();
			int defendsBefore = hero.getDefendCount();

			if (gameMode == HEAL_MODE) {
				hero.heal();
			} else {
				heroActsOnCharacter(gameMode == ATTACK_MODE);
			}

			hero.registerRound(hero.getKillCount() > killsBefore, hero.getDefendCount() > defendsBefore);

			enemyAttackPhase();

			if (hero.isDead()) {
				System.out.println("Has recibido demasiados impactos. GAME OVER, " + hero.getName());
				storage.delete();
				break;
			}
			
			if (targetsRepo.enemyCount() > 0) {
				applyRoundRules();
			}

			if (targetsRepo.friendCount() == 0) {
				System.out.println("Te has cargado a todos tus amigos. GAME OVER, " + hero.getName());
				storage.delete();
				break;
			}

			if (targetsRepo.enemyCount() == 0) {
				System.out.println("No quedan enemigos que atacar. WIN, " + hero.getName());
				storage.delete();
				break;
			}

			int finalOption = askEndOfRoundOption();

			if (finalOption == SAVE_OPTION) {

				if (storage.save(new GameState(hero, targetsRepo))) {
					System.out.println("Partida guardada con éxito.");
				}

				break;
			}

			if (finalOption == DELETE_OPTION) {

				storage.delete();

				System.out.println("Partida eliminada con éxito.");
				break;
			}
			clearConsole();
		} while (true);

		showFinalScore();

		scanner.close();
	}

	private static String askHeroName() {
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

		return heroName;
	}

	private static int startGame() {
		String answer;

		while (true) {
			System.out.println("¿Qué quieres hacer?\n" + "1. Atacar a un enemigo.\n" + "2. Defender a un amigo.\n"
					+ "3. Curarte.");

			System.out.print("Tu respuesta: ");

			answer = scanner.nextLine().trim();

			if (answer.equals("1") || answer.equals("2") || answer.equals("3")) {
				return Integer.parseInt(answer);
			}
			System.out.println("ERROR: Tu respuesta debe ser 1, 2 o 3.");
		}
	}

	private static void heroActsOnCharacter(boolean attacking) {
		while (true) {
			System.out.print("Introduce el índice del personaje: ");
			try {
				int index = Integer.parseInt(scanner.nextLine()) - 1;
				Targetable target = targetsRepo.getTargets().get(index);

				if (attacking) {
					if (hero.attack(target)) {
						targetsRepo.delete(target);
					}
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

	private static void enemyAttackPhase() {
		if (targetsRepo.enemyCount() == 0 || !Chance.roll(ENEMY_ATTACK_CHANCE)) {
			return;
		}

		System.out.println("¡Un enemigo te ataca!");

		if (Chance.roll(ENEMY_HIT_CHANCE)) {
			hero.receiveHit();
		} else {
			System.out.println("...pero falla.");
		}
	}

	private static int askEndOfRoundOption() {
		while (true) {
			System.out.print("Final de ronda.\n" + "1. Seguir jugando.\n" + "2. Guardar partida y dejar de jugar.\n"
					+ "3. Eliminar partida guardada y terminar.\n" + "Tu respuesta: ");

			String answer = scanner.nextLine().trim();

			if (answer.equals("1") || answer.equals("2") || answer.equals("3")) {
				return Integer.parseInt(answer);
			}

			System.out.println("ERROR: Escoge una opción válida.");
		}
	}

	private static void applyRoundRules() {
		if (hero.getRoundsWithoutDefending() > ROUNDS_WITHOUT_DEFENDING_LIMIT && Chance.roll(FRIEND_DEATH_CHANCE)) {
			targetsRepo.removeRandomFriend();
			System.out.println("Por no defender a tus amigos, uno de ellos ha muerto.");
		}

		if (hero.getRoundsWithoutKilling() > ROUNDS_WITHOUT_KILLING_LIMIT && Chance.roll(ENEMY_SPAWN_CHANCE)) {
			targetsRepo.addEnemy();
			System.out.println("Por no matar enemigos, ha aparecido uno nuevo.");
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
