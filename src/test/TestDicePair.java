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
		DicePair dicePair1Copy = new DicePairImpl(die1, die2);
		DicePair dicePair2 = new DicePairImpl(die2, die1); // 6,10 and 3,10 (total 9)
		DicePair dicePair3 = new DicePairImpl(die2, die3); // 6,10 and 5,12 (total 11)
		
		System.out.println("NOTE: DicePairs are compared in the order shown, i.e.\n"
				+ "if the first dice pair is DP1, and the second DP2, then\n"
				+ "the equals method reads DP1.equals(DP2), likewise for\n"
				+ "compareTo(), while their hashcodes are compared using\n"
				+ "DP1.hashcode() == DP2.hashcode.");
		
		Wrap.wrapper("Testing if two separate, identical dice pairs are equal:");
			System.out.println(dicePair1.toString());
			System.out.println(dicePair1Copy.toString() + "\n");
			
			System.out.println("Are they equal? ... " + dicePair1.equals(dicePair1Copy));
			System.out.println("Hashcodes equal? .. " + (dicePair1.hashCode() == dicePair1Copy.hashCode()));
			
		Wrap.wrapper("Testing if two different dice pairs are not equal:");
			System.out.println(dicePair1.toString());
			System.out.println(dicePair3.toString() + "\n");
			
			System.out.println("Are they equal? ... " + dicePair1.equals(dicePair3));
			System.out.println("Hashcodes equal? .. " + (dicePair1.hashCode() == dicePair3.hashCode()));
		
		Wrap.wrapper("Testing if order is enforced for dice pairs:");
		
			System.out.println(dicePair1.toString());
			System.out.println(dicePair2.toString() + "\n");
			
			System.out.println("Are they equal? ... " + dicePair1.equals(dicePair2));
			System.out.println("Hashcodes equal? .. " + (dicePair1.hashCode() == dicePair2.hashCode()));

		Wrap.wrapper("Comparing totals: pairs are equal (expecting 0):");
		
			System.out.println(dicePair1.toString());
			System.out.println(dicePair2.toString() + "\n");
			System.out.println("compareTo() returns: " + dicePair1.compareTo(dicePair2));
		
		Wrap.wrapper("Comparing totals: pair 1 < pair 3 (expecting int < 0):");
		
			System.out.println(dicePair1.toString());
			System.out.println(dicePair3.toString() + "\n");
			System.out.println("compareTo() returns: " + dicePair1.compareTo(dicePair3));
		
		Wrap.wrapper("Comparing totals: pair 3 > pair 1 (expecting int > 0):");
		
			System.out.println(dicePair3.toString());
			System.out.println(dicePair1.toString() + "\n");
			System.out.println("compareTo() returns: " + dicePair3.compareTo(dicePair1));
	}
}
