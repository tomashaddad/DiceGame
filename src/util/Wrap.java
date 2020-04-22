package util;

public class Wrap
{
	public static void wrapper(String string)
	{
		StringBuilder wrapper = new StringBuilder();
		
		for (int i = 0; i < string.length(); i++)
		{
			wrapper.append("=");
		}
		
		System.out.println("\n" + wrapper + "\n" + string + "\n" + wrapper + "\n");
	}
}
