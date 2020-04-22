package test;

import model.DieImpl;
import model.interfaces.Die;
import util.Wrap;

public class TestDie
{
	public void startTest()
	{
		System.out.println("NOTE: dice are compared in the order shown, i.e. if\n"
				+ "the first dice is D1, and the D2, then the equals method reads\n"
				+ "D1.equals(D2), while their hashcodes are compared using\n"
				+ "D1.hashcode() == D2.hashcode.");
		
		Wrap.wrapper("Number less than 1, all other arguments valid:");
		
		try
		{
			Die die = new DieImpl(0, 3, 6);
		}
		catch (IllegalArgumentException e)
		{
			System.out.println(e.toString());
		}
		
		Wrap.wrapper("Number greater than 2, all other arguments valid:");
		
		try
		{
			Die die = new DieImpl(3, 3, 6);
		}
		catch (IllegalArgumentException e)
		{
			System.out.println(e.toString());
		}

		Wrap.wrapper("Value less than 1, all other arguments valid:");
		
		try
		{
			Die die = new DieImpl(1, 0, 6);
		}
		catch (IllegalArgumentException e)
		{
			System.out.println(e.toString());
		}

		Wrap.wrapper("Value greater than number of faces, all other arguments valid:");
		
		try
		{
			Die die = new DieImpl(1, 10, 6);
		}
		catch (IllegalArgumentException e)
		{
			System.out.println(e.toString());
		}

		Wrap.wrapper("Number of faces less than 1, all other arguments valid:");
		
		try
		{
			Die die = new DieImpl(1, 5, 0);
		}
		catch (IllegalArgumentException e)
		{
			System.out.println(e.toString());
		}
		
		Wrap.wrapper("Testing toString() outputs the correct die word:");
		
			System.out.println("Value:  1. Expected: One ..... Result: " + new DieImpl(1, 1, 10));
			System.out.println("Value:  2. Expected: Two ..... Result: " + new DieImpl(1, 2, 10));
			System.out.println("Value:  3. Expected: Three ... Result: " + new DieImpl(1, 3, 10));
			System.out.println("Value:  4. Expected: Four..... Result: " + new DieImpl(1, 4, 10));
			System.out.println("Value:  5. Expected: Five .... Result: " + new DieImpl(1, 5, 10));
			System.out.println("Value:  6. Expected: Six ..... Result: " + new DieImpl(1, 6, 10));
			System.out.println("Value:  7. Expected: Seven ... Result: " + new DieImpl(1, 7, 10));
			System.out.println("Value:  8. Expected: Eight ... Result: " + new DieImpl(1, 8, 10));
			System.out.println("Value:  9. Expected: Nine .... Result: " + new DieImpl(1, 9, 10));
			System.out.println("Value: 10. Expected: > Nine .. Result: " + new DieImpl(1, 10, 10));
						
		Die die1 = new DieImpl(1, 5, 10);
		Die die2 = new DieImpl(1, 5, 10);
		Die die3 = new DieImpl(1, 9, 10);
		Die die4 = new DieImpl(1, 3, 12);
	
		Wrap.wrapper("Testing if two die with the same value and number of faces are equal:");
		
			System.out.println("(Die 1) Value: " + die1.getValue() + ", Number of faces: " + die1.getNumFaces());
			System.out.println("(Die 2) Value: " + die2.getValue() + ", Number of faces: " + die2.getNumFaces());
			System.out.println("Are they equal? ... " + die1.equals(die2));
			System.out.println("Hashcodes equal? .. " + (die1.hashCode() == die2.hashCode()));

		Wrap.wrapper("Testing if two die with different values but the same number of faces are not equal:");
		
			System.out.println("(Die 1) Value: " + die2.getValue() + ", Number of faces: " + die2.getNumFaces());
			System.out.println("(Die 2) Value: " + die3.getValue() + ", Number of faces: " + die3.getNumFaces());
			System.out.println("Are they equal? ... " + die2.equals(die3));
			System.out.println("Hashcodes equal? .. " + (die2.hashCode() == die3.hashCode()));

		Wrap.wrapper("Testing if two die with the same value but different number of faces are not equal:");
		
			System.out.println("(Die 1) Value: " + die2.getValue() + ", Number of faces: " + die2.getNumFaces());
			System.out.println("(Die 2) Value: " + die4.getValue() + ", Number of faces: " + die4.getNumFaces());
			System.out.println("Are they equal? ... " + die2.equals(die4));
			System.out.println("Hashcodes equal? .. " + (die2.hashCode() == die4.hashCode()));

		Wrap.wrapper("Testing if two die with different values and number of faces are not equal:");
		
			System.out.println("(Die 1) Value: " + die3.getValue() + ", Number of faces: " + die3.getNumFaces());
			System.out.println("(Die 2) Value: " + die4.getValue() + ", Number of faces: " + die4.getNumFaces());
			System.out.println("Are they equal? ... " + die3.equals(die4));
			System.out.println("Hashcodes equal? .. " + (die3.hashCode() == die4.hashCode()));
	}
}
