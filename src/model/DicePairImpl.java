package model;

import model.interfaces.DicePair;
import model.interfaces.Die;
import util.Rand;

public class DicePairImpl implements DicePair
{
	private Die die1;
	private Die die2;

	public DicePairImpl()
	{
		die1 = new DieImpl(1, Rand.getRandomNumberInRange(1, Die.NUM_FACES), Die.NUM_FACES);
		die2 = new DieImpl(2, Rand.getRandomNumberInRange(1, Die.NUM_FACES), Die.NUM_FACES);
	}
	
	public DicePairImpl(Die die1, Die die2)
	{
		this.die1 = die1;
		this.die2 = die2;
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
		return (dicePair == null) ? false : this.die1.equals(dicePair.getDie1())
										 && this.die2.equals(dicePair.getDie2());
	}

	@Override
	public boolean equals(Object dicePair)
	{
		return (dicePair instanceof DicePair) ? this.equals((DicePair) dicePair) : false;
	}
	
	@Override
	public int hashCode()
	{
		return 1;
	}
	
	@Override
	public String toString()
	{
		return "Dice 1: " + this.die1.getValue() + ", Dice 2: " + this.die2.getValue()
			 + " .. Total: " + this.getTotal();
	}
	
	@Override
	public int compareTo(DicePair dicePair)
	{
		return this.getTotal() < dicePair.getTotal() ? -1 : this.getTotal() > dicePair.getTotal() ? 1 : 0;
	}
}
