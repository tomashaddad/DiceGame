package test;

import model.SimplePlayer;
import model.interfaces.Player;
import util.Wrap;

public class TestPlayer
{
	public void startTest()
	{
		Player player = new SimplePlayer("1", "Tomas", 500);
		
		System.out.println("Instantiating a player: " + player.toString());
		
		Wrap.wrapper("Placing a bet less than 0:");
		System.out.println(player.setBet(-1));

		Wrap.wrapper("Placing a bet equal to 0:");
		System.out.println(player.setBet(0));

		Wrap.wrapper("Placing a bet equal to points: ");
		System.out.println(player.setBet(500));
		
		Wrap.wrapper("Placing a bet less than available points: ");
		System.out.println(player.setBet(600));
	}
}