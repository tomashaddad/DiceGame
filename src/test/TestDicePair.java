package test;

import model.DicePairImpl;
import model.DieImpl;
import model.interfaces.DicePair;
import model.interfaces.Die;
import util.Wrap;

public class TestDicePair
{
	public void startTest()
	{
		Die die1 = new DieImpl(1, 3, 10);
		Die die2 = new DieImpl(1, 6, 10);
		Die die3 = new DieImpl(1, 5, 12);
		
		DicePair dicePair1 = new DicePairImpl(die1, die2); // 3,10 and 6,10 (total 9)
		DicePair dicePair2 = new DicePairImpl(die2, die1); // 6,10 and 3,10 (total 9)
		DicePair dicePair3 = new DicePairImpl(die2, die3); // 6,10 and 5,12 (total 11)
		
		Wrap.wrapper("Testing if order is enforced for dice pairs:");
		
			System.out.println("(Die 1) " + die1.toString());
			System.out.println("(Die 2) " + die2.toString() + "\n");		
			System.out.println("(Dice pair 1) " + dicePair1.toString());
			System.out.println("(Dice pair 2) " + dicePair2.toString() + "\n");
			System.out.println("Are the two pairs of dice equal? .. " + dicePair1.equals(dicePair2));

		Wrap.wrapper("compareTo() on Dice Pairs with equal totals (expecting 0):");
		
			System.out.println("(Dice pair 1) " + dicePair1.toString());
			System.out.println("(Dice pair 2) " + dicePair2.toString() + "\n");
			System.out.println("compareTo() returns: " + dicePair1.compareTo(dicePair2));
		
		Wrap.wrapper("compareTo(), dice pair 1 total is less than pair 2 (expecting negative integer):");
		
			System.out.println("(Dice pair 1) " + dicePair1.toString());
			System.out.println("(Dice pair 2) " + dicePair3.toString() + "\n");
			System.out.println("compareTo() returns: " + dicePair1.compareTo(dicePair3));	
		
		Wrap.wrapper("compareTo(), dice pair 1 total is more than pair 2 (expecting positive integer):");
		
			System.out.println("(Dice pair 1) " + dicePair3.toString());
			System.out.println("(Dice pair 2) " + dicePair1.toString() + "\n");
			System.out.println("compareTo() returns: " + dicePair3.compareTo(dicePair1));
	}
}
