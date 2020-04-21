package util;

public class Wrap
{
	public static void wrapper(String string)
	{
		String wrapper = "";
		
		for (int i = 0; i < string.length(); i++)
		{
			wrapper += "=";
		}
		
		System.out.println("\n" + wrapper + "\n" + string + "\n" + wrapper + "\n");
	}
}
