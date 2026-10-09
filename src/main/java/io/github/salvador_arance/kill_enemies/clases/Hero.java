package io.github.salvador_arance.kill_enemies.clases;

import io.github.salvador_arance.kill_enemies.interfaces.Character;

public class Hero {
	private int killCount;
	private int defendCount;
	
	public Hero() {
		this.setKillCount(0);
		this.setDefendCount(0);
	}
	
	public void attack(Character character) {
		System.out.println("¡He atacado a alguien!");
		character.receiveAttack();
		if (character.isEnemy()) {
			this.setKillCount(this.getKillCount() + 1);
		}
		
	}
	
	public void defend(Character character) {
		System.out.println("¡He defendido a alguien!");
		character.receiveDefense();
		if (!character.isEnemy()) {
			this.setDefendCount(this.getDefendCount() + 1);
		}
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
