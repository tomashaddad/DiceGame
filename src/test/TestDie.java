package test;

import model.DieImpl;
import model.interfaces.Die;
import util.Wrap;

public class TestDie
{
	public void startTest()
	{
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

		Die die1 = new DieImpl(1, 5, 10);
		Die die2 = new DieImpl(1, 5, 10);
		Die die3 = new DieImpl(1, 6, 10);
		Die die4 = new DieImpl(1, 5, 11);
		
		Wrap.wrapper("Testing if two die with the same value and number of faces are equal:");
		
			System.out.println("(Die 1) Value: " + die1.getValue() + ", Number of faces: " + die1.getNumFaces());
			System.out.println("(Die 2) Value: " + die2.getValue() + ", Number of faces: " + die2.getNumFaces());
			System.out.println("Are they equal? .. " + die1.equals(die2));

		Wrap.wrapper("Testing if two die with different values but the same number of faces are not equal:");
		
			System.out.println("(Die 1) Value: " + die2.getValue() + ", Number of faces: " + die2.getNumFaces());
			System.out.println("(Die 2) Value: " + die3.getValue() + ", Number of faces: " + die3.getNumFaces());
			System.out.println("Are they equal? .. " + die2.equals(die3));

		Wrap.wrapper("Testing if two die with the same value but different number of faces are not equal:");
		
			System.out.println("(Die 1) Value: " + die2.getValue() + ", Number of faces: " + die2.getNumFaces());
			System.out.println("(Die 2) Value: " + die4.getValue() + ", Number of faces: " + die4.getNumFaces());
			System.out.println("Are they equal? .. " + die2.equals(die4));

		Wrap.wrapper("Testing if two die with different values and number of faces are not equal:");
		
			System.out.println("(Die 1) Value: " + die3.getValue() + ", Number of faces: " + die3.getNumFaces());
			System.out.println("(Die 2) Value: " + die4.getValue() + ", Number of faces: " + die4.getNumFaces());
			System.out.println("Are they equal? .. " + die3.equals(die4));
	}
}
