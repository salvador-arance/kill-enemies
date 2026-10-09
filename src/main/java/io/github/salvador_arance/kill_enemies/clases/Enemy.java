package io.github.salvador_arance.kill_enemies.clases;

public class Enemy implements Character {
	public String kill() {
		return "Ahhhggg, me mataste, bastardo!";
	}
	@Override
	public boolean isEnemy() {
		return true;
	}
}
