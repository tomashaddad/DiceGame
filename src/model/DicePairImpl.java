package model;

import java.util.Random;

import model.interfaces.DicePair;
import model.interfaces.Die;

public class DicePairImpl implements DicePair
{
	private Die die1;
	private Die die2;

	public DicePairImpl()
	{
		die1 = new DieImpl(1, getRandomNumberInRange(1, Die.NUM_FACES), Die.NUM_FACES);
		die2 = new DieImpl(2, getRandomNumberInRange(1, Die.NUM_FACES), Die.NUM_FACES);
	}

	/**
	 * Taken from Fabio's Programming Techniques lectures (Week 8, slide 15)
	 */
	private int getRandomNumberInRange(int min, int max)
	{
		Random random = new Random();
		return random.nextInt((max - min) + 1) + min;
	}

	@Override
	public Die getDie1()
	{
		return die1;
	}

	@Override
	public Die getDie2()
	{
		return die2;
	}

	@Override
	public int getTotal()
	{
		return die1.getValue() + die2.getValue();
	}

	@Override
	public boolean equals(DicePair dicePair)
	{
		return this.die1.equals(dicePair.getDie1())
			&& this.die2.equals(dicePair.getDie2());
	}

	@Override
	public boolean equals(Object dicePair)
	{
		if (dicePair instanceof DicePair)
			return this.equals((DicePair) dicePair);
		
		return false;
	}
	
	@Override
	public int hashCode()
	{
		return 1;
	}
	
	@Override
	public String toString()
	{
		return "Dice 1: " + this.die1.getValue() + ", Dice 2: " + this.die2.getValue() + " .. Total: " + this.getTotal();
	}
	
	// Lecturer: You'd probably implement this using compareTo on the individual dice themselves,
	// that is use the underlying compareTo function to compare their dice values rather than code it yourself
	@Override
	public int compareTo(DicePair dicePair)
	{
		return this.getTotal() < dicePair.getTotal() ? -1 : this.getTotal() > dicePair.getTotal() ? 1 : 0;
	}
}
