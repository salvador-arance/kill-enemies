package io.github.salvador_arance.kill_enemies.clases;

import io.github.salvador_arance.kill_enemies.interfaces.Character;

public class Enemy implements Character {
	public void kill() {
		System.out.println("Ahhhggg, me mataste, bastardo!");
	}
	@Override
	public boolean isEnemy() {
		return true;
	}
}
