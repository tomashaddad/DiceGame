package client;

import java.util.Scanner;

import test.TestDicePair;
import test.TestDie;
import test.TestGameEngine;
import test.TestPlayer;

public class Menu
{
	private Scanner scanner = new Scanner(System.in);
	
	private TestPlayer testPlayer = new TestPlayer();
	private TestDie testDie = new TestDie();
	private TestDicePair testDicePair = new TestDicePair();
	private TestGameEngine testGameEngine = new TestGameEngine();
	
	public void start()
	{
		String matchNotFoundMessage = "Menu item does not exist.\n"
				+ "Please ensure that your input matches one of the menu\n"
				+ "labels to the right of the option you wish to access.";

		System.out.println("Please select from one of the following menu items\n"
				+ "by typing in the letters shown on the right (case insensitive).");
		System.out.println();

		while (true)
		{
			displayMenu();
			String selection = scanner.nextLine().toUpperCase();
			System.out.println();

			switch (selection)
			{
			case "PL":
				testPlayer.startTest();
				break;
			case "DI":
				testDie.startTest();
				break;
			case "DP":
				testDicePair.startTest();
				break;
			case "G1":
				testGameEngine.startStateTest();
				break;
			case "G2":
				testGameEngine.startGameExceptions();
				break;
			case "G3":
				testGameEngine.startGameTest();
				break;
			case "EX":
				System.out.println("Program is exiting");
				scanner.close();
				System.exit(0);
			default:
				System.out.println(matchNotFoundMessage);
			}

			System.out.println("\n####################################################\n");
			System.out.println("Please select another option:\n");
		}
	}
	
	private void displayMenu()
	{
		System.out.println("*** DiceGame Testing Menu ******\n");
		
		System.out.println(
			String.format("%-30s%s\n%-30s%s\n%-30s%s\n%-30s%s\n%-30s%s\n%-30s%s\n%-30s%s",
					
					"Test SimplePlayer", 			"PL",
					"Test Die", 					"DI",
					"Test DicePair", 				"DP",
					"Test GameEngine state", 		"G1",
					"Test GameEngine exceptions", 	"G2",
					"Test GameEngine games", 		"G3",
					"Exit program", 				"EX"
			)
		);
		
		System.out.print("\nEnter selection:\n> ");
	}
}
