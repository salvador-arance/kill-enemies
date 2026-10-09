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
		for (int i = 0; i < ENEMY_COUNT; i++) {
			this.getCharacters().add(new Enemy());
		}
	}
	
	private void addFriends() {
		for (int i= 0; i < FRIEND_COUNT; i++) {
			this.getCharacters().add(new Friend());
		}
	}
	
	private void shuffle() {
		Collections.shuffle(getCharacters());
	}
	
	public void showCharacters() {
		int characterIndex = 0;
		
		for (Character c: this.getCharacters()) {
			++ characterIndex;
			System.out.println("Personaje " + characterIndex + ": " + c.toString());
		}
	}
	
	public int enemyCount() {
		int enemyCount = 0;
		for (Character c: this.getCharacters()) {
			if (c.isEnemy()) {
				enemyCount ++;
			}
		}
		return enemyCount;
	}
	
	public int friendCount() {
		int friendCount = 0;
		for (Character c: this.getCharacters()) {
			if (!c.isEnemy()) {
				friendCount ++;
			}
		}
		return friendCount;
	}
	
	public void delete(Character character) {
	    characters.remove(character);
	}
	
	public void addEnemy() {
		characters.add(new Enemy());
	};
	
	public ArrayList<Character> getCharacters() {
		return characters;
	}

	private void setCharacters(ArrayList<Character> characters) {
		this.characters = characters;
	}
}
