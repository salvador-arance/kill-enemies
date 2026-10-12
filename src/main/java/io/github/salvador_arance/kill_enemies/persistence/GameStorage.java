package io.github.salvador_arance.kill_enemies.persistence;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Optional;

public class GameStorage {
    private final Path file;

    public GameStorage(Path file) {
        this.file = file;
    }

    public boolean exists() {
        return Files.exists(file);
    }

    public boolean save(GameState state) {
        try (ObjectOutputStream out = new ObjectOutputStream(Files.newOutputStream(file))) {
            out.writeObject(state);
            return true;
        } catch (IOException e) {
            System.out.println("ERROR: no se ha podido guardar la partida: " + e.getMessage());
            return false;
        }
    }

    public Optional<GameState> load() {
        if (!exists()) {
            return Optional.empty();
        }
        try (ObjectInputStream in = new ObjectInputStream(Files.newInputStream(file))) {
            Object read = in.readObject();
            if (read instanceof GameState state) {
                return Optional.of(state);
            }
            System.out.println("ERROR: el fichero guardado no contiene una partida válida.");
        } catch (IOException | ClassNotFoundException e) {
            System.out.println("ERROR: no se ha podido cargar la partida: " + e.getMessage());
        }
        return Optional.empty();
    }

    public void delete() {
        try {
            Files.deleteIfExists(file);
        } catch (IOException e) {
            System.out.println("ERROR: no se ha podido borrar la partida guardada: " + e.getMessage());
        }
    }
}
