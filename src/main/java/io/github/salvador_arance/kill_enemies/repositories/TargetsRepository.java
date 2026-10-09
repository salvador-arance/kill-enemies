package io.github.salvador_arance.kill_enemies.repositories;

import java.util.ArrayList;
import java.util.Collections;

import io.github.salvador_arance.kill_enemies.clases.Enemy;
import io.github.salvador_arance.kill_enemies.clases.Friend;
import io.github.salvador_arance.kill_enemies.interfaces.Targetable;

public class TargetsRepository {
	private static final int ENEMY_COUNT = 5;
	private static final int FRIEND_COUNT = 5;
	private ArrayList<Targetable> targets;

	public TargetsRepository() {
		this.setTargets(new ArrayList<Targetable>());
		addEnemies();
		addFriends();
		shuffle();
	}

	private void addEnemies() {
		for (int i = 0; i < ENEMY_COUNT; i++) {
			this.getTargets().add(new Enemy());
		}
	}

	private void addFriends() {
		for (int i = 0; i < FRIEND_COUNT; i++) {
			this.getTargets().add(new Friend());
		}
	}

	private void shuffle() {
		Collections.shuffle(getTargets());
	}

	public void showTargets() {
		int targetIndex = 0;

		for (Targetable c : this.getTargets()) {
			++targetIndex;
			System.out.println("Personaje " + targetIndex + ": " + c.toString());
		}
	}

	public int enemyCount() {
		int enemyCount = 0;
		for (Targetable c : this.getTargets()) {
			if (c.isEnemy()) {
				enemyCount++;
			}
		}
		return enemyCount;
	}

	public int friendCount() {
		int friendCount = 0;
		for (Targetable c : this.getTargets()) {
			if (!c.isEnemy()) {
				friendCount++;
			}
		}
		return friendCount;
	}

	public void delete(Targetable character) {
		targets.remove(character);
	}

	public void addEnemy() {
		targets.add(new Enemy());
	}

	public ArrayList<Targetable> getTargets() {
		return targets;
	}

	private void setTargets(ArrayList<Targetable> characters) {
		this.targets = characters;
	}
}
