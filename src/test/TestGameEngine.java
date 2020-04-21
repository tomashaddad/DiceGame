package test;

import java.util.concurrent.TimeUnit;

import model.GameEngineImpl;
import model.SimplePlayer;
import model.interfaces.GameEngine;
import model.interfaces.Player;
import util.Wrap;
import view.GameEngineCallbackImpl;
import view.interfaces.GameEngineCallback;

public class TestGameEngine
{
	public void startStateTest()
	{
		System.out.println("This test is instantiated with a GameEngine, two callbacks in the\n"
				+ " GameEngine, and three players:\n");
		
	    Player[] players = new Player[] {
    		new SimplePlayer("1", "The Roller", 5000),
    		new SimplePlayer("2", "The Loser", 500),
    		new SimplePlayer("3", "The Card Counter", 10000)};
		
		System.out.println(players[0].toString());
		System.out.println(players[1].toString());
		System.out.println(players[2].toString());

		GameEngine gameEngine = new GameEngineImpl();
		GameEngineCallback callback1 = new GameEngineCallbackImpl();
		GameEngineCallback callback2 = new GameEngineCallbackImpl();	
		gameEngine.addGameEngineCallback(callback1);
		gameEngine.addGameEngineCallback(callback2);
		
		for (Player player : players)
		{
			gameEngine.addPlayer(player);
		}
		
		Wrap.wrapper("Removing a GameEngineCallback:");
		
			System.out.println("Attempting to remove the second callback returns .. "
								+ gameEngine.removeGameEngineCallback(callback2));
		
		Wrap.wrapper("Removing a player: ");
		
			printPlayers(gameEngine);
			
			System.out.println("\nAttempting to remove Player 3. Returns: " + gameEngine.removePlayer(players[2]) + "\n");
			printPlayers(gameEngine);
			
			System.out.println("\nAttempting to remove Player 3 again. Returns: " + gameEngine.removePlayer(players[2]) + "\n");
			printPlayers(gameEngine);
		
		Wrap.wrapper("Adding a player (re-adding Player 3): ");
		
			printPlayers(gameEngine);
			
			System.out.println("\nAdding Player 3 again ...\n");
			gameEngine.addPlayer(players[2]);
			printPlayers(gameEngine);

		Wrap.wrapper("Adding a player with the same ID: ");
		
			printPlayers(gameEngine);
			Player newPlayer = new SimplePlayer("3", "The Poor", 100);
			System.out.println("\nAdding a new player with the same ID as Player #3:\n" + newPlayer.toString() + "\n");
			
			gameEngine.addPlayer(newPlayer);
			printPlayers(gameEngine);

		Wrap.wrapper("Getting a player that exists:");
		
			printPlayers(gameEngine);
			System.out.println("\nAttempting to get the player with ID 1 ...\nReturns: " + gameEngine.getPlayer("1"));
		
		Wrap.wrapper("Getting a player that does not exist:");
		
			printPlayers(gameEngine);
			System.out.println("\nAttempting to get the player with ID 4 ...\nReturns: " + gameEngine.getPlayer("4"));
	}

	public void startGameTest()
	{
		GameEngine gameEngine = new GameEngineImpl();
		gameEngine.addGameEngineCallback(new GameEngineCallbackImpl());
		
	    Player[] players = new Player[] {
	    		new SimplePlayer("1", "The Roller", 5000),
	    		new SimplePlayer("2", "The Loser", 500)};
		
		for (Player player : players)
		{
			gameEngine.addPlayer(player);
		}
		
		for (Player player : gameEngine.getAllPlayers())
		{
			gameEngine.placeBet(player, 100);
			gameEngine.rollPlayer(player, 50, 250, 50, 50, 250, 50);
		}
		
		gameEngine.rollHouse(100, 1000, 200, 50, 500, 25);
		
		try
		{
			TimeUnit.MILLISECONDS.sleep(1000);
		}
		catch (InterruptedException e)
		{
			e.printStackTrace();
		}
	}
	
	private void printPlayers(GameEngine gameEngine)
	{
		for (Player player : gameEngine.getAllPlayers())
		{
			System.out.println(player.toString());
		}
	}
}
