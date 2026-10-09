package io.github.salvador_arance.kill_enemies.clases;

import io.github.salvador_arance.kill_enemies.interfaces.Character;

public class Friend implements Character {
	@Override
	public boolean isEnemy() {
		return false;
	}

	@Override
	public void receiveAttack() {
		System.out.println("¡ME HAS MATADO, A UN AMIGO, INSENSATO!");
	}

	@Override
	public void receiveDefense() {
		System.out.println("¡Me han curado!");
	}

	@Override
	public String toString() {
		return "es amigo.";
	}
}
