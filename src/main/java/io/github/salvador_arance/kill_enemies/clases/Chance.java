package io.github.salvador_arance.kill_enemies.clases;

import java.util.Random;

public class Chance {
	private static final Random RANDOM = new Random();
	
	public static boolean roll (int percent) {
		return RANDOM.nextInt(100) < percent;
	}
}
