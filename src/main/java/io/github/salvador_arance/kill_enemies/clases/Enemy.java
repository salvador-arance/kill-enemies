package io.github.salvador_arance.kill_enemies.clases;

import io.github.salvador_arance.kill_enemies.interfaces.Targetable;

public class Enemy implements Targetable {
	@Override
	public void receiveAttack() {
		System.out.println("¡Ahhhggg, me mataste, bastardo!");
	}

	@Override
	public void receiveDefense() {
		System.out.println("Gracias, ahora me reproduzco!");
	}

	@Override
	public boolean isEnemy() {
		return true;
	}

	@Override
	public String toString() {
		return "es enemigo.";
	}
}
