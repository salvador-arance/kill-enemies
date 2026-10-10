package io.github.salvador_arance.kill_enemies.clases;

import io.github.salvador_arance.kill_enemies.interfaces.Character;
import io.github.salvador_arance.kill_enemies.interfaces.Targetable;

public class Hero implements Character {
	private int killCount;
	private int defendCount;
	private String name;
	
	public Hero(String name) {
		this.setName(name);
		this.setDefendCount(0);
		this.setKillCount(0);
	}

	public void attack(Targetable target) {
		
		if (target.isEnemy()) {
			System.out.println("¡He atacado a un enemigo!");
			this.setKillCount(this.getKillCount() + 1);
		} else {
			System.out.println("¡He atacado a un amigo!");
		}
		
		target.receiveAttack();
	}

	public void defend(Targetable target) {
		
		if (!target.isEnemy()) {
			System.out.println("¡He defendido a un amigo!");
			this.setDefendCount(this.getDefendCount() + 1);
		} else {
			System.out.println("He defendido a un enemigo");
		}
		
		target.receiveDefense();
	}
	
	@Override 
	public boolean isEnemy() {
		return false;
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

	public String getName() {
		return name;
	}

	private void setName(String name) {
		this.name = name;
	}
}
