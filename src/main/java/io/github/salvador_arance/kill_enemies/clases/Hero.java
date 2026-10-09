package io.github.salvador_arance.kill_enemies.clases;

import io.github.salvador_arance.kill_enemies.interfaces.Character;

public class Hero implements Character {
	private int killCount;
	private int defendCount;
	
	public Hero() {
		this.setKillCount(0);
		this.setDefendCount(0);
	}
	
	@Override
	public boolean isEnemy() {
		return false;
	}
	
	public void attack(Enemy enemy) {
		System.out.println("¡He atacado a un enemigo!");
		enemy.kill();
		this.setKillCount(this.getKillCount() + 1);
	}
	
	public void defend(Friend friend) {
		System.out.println("¡He defendido a un amigo!");
		friend.heal();
		this.setDefendCount(this.getDefendCount() + 1);
	}

	public int getKillCount() {
		return killCount;
	}

	private void setKillCount(int killCount) {
		this.killCount = killCount;
	}

	public int getDefendCount() {
		return defendCount;
	}

	private void setDefendCount(int defendCount) {
		this.defendCount = defendCount;
	}
}
