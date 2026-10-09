package io.github.salvador_arance.kill_enemies.interfaces;

public interface Character {
	boolean isEnemy();

	void receiveAttack();

	void receiveDefense();
}