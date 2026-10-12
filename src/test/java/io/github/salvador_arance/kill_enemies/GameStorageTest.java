package io.github.salvador_arance.kill_enemies;

import static org.junit.jupiter.api.Assertions.*;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import io.github.salvador_arance.kill_enemies.clases.Hero;
import io.github.salvador_arance.kill_enemies.persistence.GameState;
import io.github.salvador_arance.kill_enemies.persistence.GameStorage;
import io.github.salvador_arance.kill_enemies.repositories.TargetsRepository;

class GameStorageTest {
	final String NOMBRE_EJEMPLO = "Salvador El Dios Del Inframundo";
	
	@Test
	void saveAndLoadKeepsTheState(@TempDir Path dir) {
		GameStorage storage = new GameStorage(dir.resolve("test.dat"));
		TargetsRepository repo = new TargetsRepository();
		Hero hero = new Hero(NOMBRE_EJEMPLO);

		storage.save(new GameState(hero, repo));
		GameState loaded = storage.load().orElseThrow();

		assertEquals(NOMBRE_EJEMPLO, loaded.getHero().getName());
		assertEquals(repo.getTargets().size(), loaded.getTargetsRepo().getTargets().size());
		assertEquals(repo.enemyCount(), loaded.getTargetsRepo().enemyCount());
	}

	@Test
	void loadReturnsEmptyWhenThereIsNoFile(@TempDir Path dir) {
		GameStorage storage = new GameStorage(dir.resolve("no-existe.dat"));
		assertTrue(storage.load().isEmpty());
	}

	@Test
	void loadReturnsEmptyWhenTheFileIsCorrupt(@TempDir Path dir) throws IOException {
		Path file = dir.resolve("corrupto.dat");
		Files.writeString(file, "esto no es una partida");
		assertTrue(new GameStorage(file).load().isEmpty());
	}
}
