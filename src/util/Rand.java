package util;

import java.util.Random;

public class Rand
{
	public static int getRandomNumberInRange(int min, int max)
	{
		Random random = new Random();
		return random.nextInt((max - min) + 1) + min;
	}
}
