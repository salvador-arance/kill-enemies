package io.github.salvador_arance.kill_enemies.persistence;

import java.io.Serializable;

import io.github.salvador_arance.kill_enemies.clases.Hero;
import io.github.salvador_arance.kill_enemies.repositories.TargetsRepository;

public class GameState implements Serializable {
	private static final long serialVersionUID = 1L;

	private final Hero hero;
	private final TargetsRepository targetsRepo;

	public GameState(Hero hero, TargetsRepository targetsRepo) {
		this.hero = hero;
		this.targetsRepo = targetsRepo;
	}

	public Hero getHero() {
		return hero;
	}

	public TargetsRepository getTargetsRepo() {
		return targetsRepo;
	}

}
