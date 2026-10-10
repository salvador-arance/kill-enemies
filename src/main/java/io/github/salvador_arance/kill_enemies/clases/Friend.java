package io.github.salvador_arance.kill_enemies.clases;

import io.github.salvador_arance.kill_enemies.interfaces.Targetable;

public class Friend implements Targetable {
	private static final long serialVersionUID = -2844186359710904214L;

	@Override
	public boolean isEnemy() {
		return false;
	}

	@Override
	public void receiveAttack() {
		System.out.println("¡ME HAS MATADO INSENSATO!");
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
