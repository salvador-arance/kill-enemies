package io.github.salvador_arance.kill_enemies.clases;

import java.util.ArrayList;
import java.util.Collections;

public class CharactersRepository {
	private static final int ENEMY_COUNT = 5;
	private static final int FRIEND_COUNT = 5;
	ArrayList<Character> character;
	
	public CharactersRepository() {
		this.character = new ArrayList<Character>();
		addEnemies();
		addFriends();
		shuffle();
	}
	
	private void addEnemies() {
		for (int i = 0; i < FRIEND_COUNT; i++) {
			this.character.add(new Friend());
		}
	}
	
	private void addFriends() {
		for (int i= 0; i < ENEMY_COUNT; i++) {
			this.character.add(new Enemy());
		}
	}
	
	private void shuffle() {
		Collections.shuffle(character);
	}
}
