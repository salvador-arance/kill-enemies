package io.github.salvador_arance.kill_enemies.repositories;

import java.util.ArrayList;
import java.util.Collections;

import io.github.salvador_arance.kill_enemies.clases.Enemy;
import io.github.salvador_arance.kill_enemies.clases.Friend;
import io.github.salvador_arance.kill_enemies.interfaces.Character;

public class CharactersRepository {
	private static final int ENEMY_COUNT = 5;
	private static final int FRIEND_COUNT = 5;
	private ArrayList<Character> characters;
	
	public CharactersRepository() {
		this.setCharacters(new ArrayList<Character>());
		addEnemies();
		addFriends();
		shuffle();
	}
	
	private void addEnemies() {
		for (int i = 0; i < FRIEND_COUNT; i++) {
			this.getCharacters().add(new Friend());
		}
	}
	
	private void addFriends() {
		for (int i= 0; i < ENEMY_COUNT; i++) {
			this.getCharacters().add(new Enemy());
		}
	}
	
	private void shuffle() {
		Collections.shuffle(getCharacters());
	}

	public ArrayList<Character> getCharacters() {
		return characters;
	}

	private void setCharacters(ArrayList<Character> characters) {
		this.characters = characters;
	}
}
