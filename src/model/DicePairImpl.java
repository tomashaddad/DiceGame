package model;

import model.interfaces.DicePair;
import model.interfaces.Die;
import util.Rand;

public class DicePairImpl implements DicePair
{
	private static final int MINIMUM_VALUE = 1;
	private Die die1;
	private Die die2;
	
	public DicePairImpl(Die die1, Die die2)
	{
		this.die1 = die1;
		this.die2 = die2;
	}
	
	public DicePairImpl()
	{
		this(new DieImpl(1, Rand.getRandomNumberInRange(MINIMUM_VALUE, Die.NUM_FACES), Die.NUM_FACES),
			 new DieImpl(2, Rand.getRandomNumberInRange(MINIMUM_VALUE, Die.NUM_FACES), Die.NUM_FACES));
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
	
	/* Implemented according to:
	 * https://stackoverflow.com/questions/11742593/what-is-the-hashcode-for-a-custom-class-having-just-two-int-properties */
	@Override
	public int hashCode()
	{
		int hash = 17;
		hash = hash * 31 + die1.hashCode();
		hash = hash * 31 + die2.hashCode();
		return hash;
	}
	
	@Override
	public String toString()
	{
		return String.format("Dice 1: %d, Dice 2: %d .. Total: ",
				die1.getValue(),
				die2.getValue(),
				getTotal()
		);
	}
	
	@Override
	public int compareTo(DicePair dicePair)
	{
		return this.getTotal() < dicePair.getTotal() ? -1 : this.getTotal() > dicePair.getTotal() ? 1 : 0;
	}
}
