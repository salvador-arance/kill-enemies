package io.github.salvador_arance.kill_enemies.clases;

import io.github.salvador_arance.kill_enemies.interfaces.Character;

public class Friend implements Character {
	@Override
	public boolean isEnemy() {
		return false;
	}
	
	@Override
	public String toString() {
		return "es amigo.";
	}
	
	public void heal() {
		System.out.println("¡Me han curado!");
	}
}
