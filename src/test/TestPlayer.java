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
		System.out.println("player.setBet(-1) returns: " + player.setBet(-1));

		Wrap.wrapper("Placing a bet equal to 0:");
		System.out.println("player.setBet(0) returns: " + player.setBet(0));

		Wrap.wrapper("Placing a bet equal to points: ");
		System.out.println("player.setBet(500) returns: " + player.setBet(500));
		
		Wrap.wrapper("Placing a bet larger than available points: ");
		System.out.println("player.setBet(600) returns: " + player.setBet(600));
		
		Wrap.wrapper("Placing a valid bet: ");
		System.out.println("player.setBet(250) returns: " + player.setBet(250));
		
		System.out.println("\nFinal player toString(): " + player.toString());
	}
}