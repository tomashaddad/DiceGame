package model;
import java.util.Objects;

import model.interfaces.Die;

public class DieImpl implements Die
{
	private int number;
	private int value;
	private int numFaces;
	
	public DieImpl(int number, int value, int numFaces) throws IllegalArgumentException
	{
		 if(number < 1 || number > 2 || value < 1 || value > numFaces || numFaces < 1)
		 {
			 throw new IllegalArgumentException();
		 }
		 
		 this.number = number;
		 this.value = value;
		 this.numFaces = numFaces;
	}
	
	@Override
	public int getNumber()
	{
		return number;
	}

	@Override
	public int getValue()
	{
		return value;
	}

	@Override
	public int getNumFaces()
	{
		return numFaces;
	}

	@Override
	public boolean equals(Die die)
	{
		return die.getValue() == this.value && die.getNumFaces() == this.numFaces;
	}
	
	@Override
	public boolean equals(Object die)
	{
		if (die instanceof Die)
		{
			return this.equals((Die) die);
		}
		
		return false;
	}

	@Override
	public int hashCode()
	{
		return Objects.hash(value, numFaces);
	}

	@Override
	public String toString()
	{
		return null;
	}
}