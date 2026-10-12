package io.github.salvador_arance.kill_enemies.clases;

import io.github.salvador_arance.kill_enemies.interfaces.Character;
import io.github.salvador_arance.kill_enemies.interfaces.Targetable;

public class Hero implements Character {
	private static final int MAX_HITS = 3;
	private static final long serialVersionUID = 1L;
	private static final int ATTACK_FAIL_CHANCE = 30;
	
	private int killCount;
	private int defendCount;
	private String name;
	private int hits;
	private int roundsWithoutKilling;
	private int roundsWithoutDefending;

	public Hero(String name) {
		this.setName(name);
		this.setDefendCount(0);
		this.setKillCount(0);
		this.setHits(0);
		this.setRoundsWithoutKilling(0);
		this.setRoundsWithoutDefending(0);
	}
	
	
	public void registerRound (boolean killed, boolean defended) {
		if (!killed) {
			this.setRoundsWithoutKilling(this.getRoundsWithoutKilling() + 1);
		} else {
			this.setRoundsWithoutKilling(0);
		}
		
		if (!defended) {
			this.setRoundsWithoutDefending(this.getRoundsWithoutDefending() + 1);
		} else {
			this.setRoundsWithoutDefending(0);
		}
	}

	public boolean attack(Targetable target) {
		
		if (target.isEnemy() && Chance.roll(ATTACK_FAIL_CHANCE)) {
			System.out.println("¡He fallado el ataque!");
			return false;
		}

		if (target.isEnemy()) {
			System.out.println("¡He atacado a un enemigo!");
			this.setKillCount(this.getKillCount() + 1);
		} else {
			System.out.println("¡He atacado a un amigo!");
		}

		target.receiveAttack();
		
		return true;
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

	public void receiveHit() {
		hits++;
		System.out.println("¡Te han herido! Impactos: " + hits + " / " + MAX_HITS);
	}

	public void heal() {
		this.setHits(0);
		System.out.println("Te has curado. Impactos: " + hits + " / " + MAX_HITS);
	}

	public boolean isDead() {
		return hits >= MAX_HITS;
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

	public int getHits() {
		return hits;
	}

	private void setHits(int hits) {
		this.hits = hits;
	}

	public int getRoundsWithoutKilling() {
		return roundsWithoutKilling;
	}

	private void setRoundsWithoutKilling(int roundsWithoutKilling) {
		this.roundsWithoutKilling = roundsWithoutKilling;
	}

	public int getRoundsWithoutDefending() {
		return roundsWithoutDefending;
	}

	private void setRoundsWithoutDefending(int roundsWithoutDefending) {
		this.roundsWithoutDefending = roundsWithoutDefending;
	}
}
