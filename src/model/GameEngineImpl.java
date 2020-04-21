package model;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import model.interfaces.DicePair;
import model.interfaces.Die;
import model.interfaces.DieRollHandler;
import model.interfaces.GameEngine;
import model.interfaces.Player;
import util.Rand;
import view.interfaces.GameEngineCallback;

public class GameEngineImpl implements GameEngine
{
	private Map<String, Player> players = new HashMap<>();
	private List<GameEngineCallback> callbacks = new ArrayList<>();
	
	@Override
	public void rollPlayer(Player player, int initialDelay1, int finalDelay1, int delayIncrement1,
			int initialDelay2, int finalDelay2, int delayIncrement2)
	{
		if (anyParameterInvalid(initialDelay1, finalDelay1, delayIncrement1,
				initialDelay2, finalDelay2, delayIncrement2))
		{
			throw new IllegalArgumentException();
		}
		
		DieRollHandler dieRollHandler = new PlayerDieRollHandler(player, callbacks, this);
		DicePair dicePair = roll(initialDelay1, finalDelay1, delayIncrement1, initialDelay2, finalDelay2, delayIncrement2, dieRollHandler);
		
        for (GameEngineCallback callback : callbacks)
        {
        	callback.playerResult(player, dicePair, this);
        }
        
        player.setResult(dicePair);
	}
	
	@Override
	public void rollHouse(int initialDelay1, int finalDelay1, int delayIncrement1, int initialDelay2, int finalDelay2,
			int delayIncrement2)
	{
		if (anyParameterInvalid(initialDelay1, finalDelay1, delayIncrement1,
				initialDelay2, finalDelay2, delayIncrement2))
		{
			throw new IllegalArgumentException();
		}
		
		DieRollHandler dieRollHandler = new HouseDieRollHandler(callbacks, this);
		DicePair dicePair = roll(initialDelay1, finalDelay1, delayIncrement1, initialDelay2, finalDelay2, delayIncrement2, dieRollHandler);
        
        for (Player player : players.values())
        {
        	applyWinLoss(player, dicePair);
        }
        
        for (GameEngineCallback callback : callbacks)
        {
        	callback.houseResult(dicePair, this);
        }
        
        for (Player player : players.values())
        {
        	player.resetBet();
        }
	}
	
	/* https://i.kym-cdn.com/entries/icons/original/000/022/524/tumblr_o16n2kBlpX1ta3qyvo1_1280.jpg
	 * 
	 * This method emulates concurrent rolling. This is long because as far as I know there is no way
	 * to separate the logic into helper methods, other classes, etc. I know we weren't required to do
	 * anything nearly as complicated as this, but I dropped Software Engineering Fundamentals for a
	 * semester and have lots of spare time.
	 * 
	 * The algorithm is as follows:
	 * 
	 * 	WHILE either die is still rolling
	 * 		IF Dice1 is due to roll AND is still rolling, OR Dice2 has stopped rolling
	 * 			Wait Die1's delay before rolling
	 * 			Increment its delay and rolling time
	 * 			If it's Die2's turn to roll next, decrease its delay by how long Die1 has been rolling for
	 * 		ELSE IF Dice2 is due to roll AND is still rolling, OR Dice1 has stopped rolling
	 * 			Wait Die2's delay before rolling
	 * 			Increment its delay and rolling time
	 * 			If it's Die1's turn to roll next, decrease its delay by how long Die2 has been rolling for
	 * 		ELSE IF Both dies are due to roll AND both dice are still rolling
	 * 			Wait the shorter delay (or either, if equal) before rolling
	 * 			Increment their delays and rolling time
	 * 		IF a dice's delay has exceeded its maximum delay, stop it from rolling again
	 * 
	 * */
	
	private DicePair roll(int initialDelay1, int finalDelay1, int delayIncrement1,
			int initialDelay2, int finalDelay2, int delayIncrement2, DieRollHandler diceRollHandler)
	{
		Die die1 = new DieImpl(1, Rand.getRandomNumberInRange(DicePairImpl.MINIMUM_VALUE, Die.NUM_FACES), Die.NUM_FACES);
		Die die2 = new DieImpl(2, Rand.getRandomNumberInRange(DicePairImpl.MINIMUM_VALUE, Die.NUM_FACES), Die.NUM_FACES);
		
		/* Tracks the current delay sum after adding the delay increment, starting at the initial delay */
		int runningDelay1 = initialDelay1;
		int runningDelay2 = initialDelay2;
		
		/* Tracks the total time each dice has been rolling */
		int runningTime1 = initialDelay1;
		int runningTime2 = initialDelay2;
		
		/* Tracks if a die has stopped rolling, so the other die can roll regardless */
		boolean die1Rolling = true;
		boolean die2Rolling = true;
		
		/* Tracks the actual time each die waits for, factoring how long the other die has rolled before it */
		boolean switching = false;
		int adjustedDelay1 = runningDelay1;
		int adjustedDelay2 = runningDelay2;
		
		while (die1Rolling || die2Rolling)
		{
			// If it's Die1's turn to roll, or if only die 2 has stopped rolling
			if (((runningTime1 < runningTime2) && die1Rolling) || !die2Rolling)
			{
				// if one of the dice has stopped rolling there's no need to use adjusted delays anymore
			    wait(die2Rolling ? adjustedDelay1 : runningDelay1);
			    
				die1 = new DieImpl(1, Rand.getRandomNumberInRange(1, Die.NUM_FACES), Die.NUM_FACES);
			    diceRollHandler.handle(die1);
				
				runningDelay1 += delayIncrement1;
				runningTime1 += runningDelay1;
				
				if (switching)
				{
					adjustedDelay1 = runningDelay1;
					switching = false;
				}
				
				if ((runningTime1 > runningTime2) && die2Rolling)
				{
					switching = true;
					adjustedDelay2 = runningTime2 - (runningTime1 - runningDelay1);
				}
			}
			
			// If it's Die2's turn to roll, or if only die 1 has stopped rolling
			else if (((runningTime1 > runningTime2) && die2Rolling) || !die1Rolling)
			{
				wait(die1Rolling ? adjustedDelay2 : runningDelay2);
				
				die2 = new DieImpl(2, Rand.getRandomNumberInRange(1, Die.NUM_FACES), Die.NUM_FACES);
			    diceRollHandler.handle(die2);
			    
				runningDelay2 += delayIncrement2;
				runningTime2 += runningDelay2;
				
				if (switching)
				{
					adjustedDelay2 = runningDelay2;
					switching = false;
				}
				
				if ((runningTime2 > runningTime1) && die1Rolling)
				{
					switching = true;
					adjustedDelay1 = runningTime1 - (runningTime2 - runningDelay2);
				}
			}

			// If both die are about to roll
			else if ((runningTime1 == runningTime2) && die1Rolling && die2Rolling)
			{
				// wait whatever the shorter delay is (or either delay if equal)
				wait(adjustedDelay1 <= adjustedDelay2 ? adjustedDelay1 : adjustedDelay2);
				
				die1 = new DieImpl(1, Rand.getRandomNumberInRange(1, Die.NUM_FACES), Die.NUM_FACES);
				die2 = new DieImpl(2, Rand.getRandomNumberInRange(1, Die.NUM_FACES), Die.NUM_FACES);
			    diceRollHandler.handle(die1);
			    diceRollHandler.handle(die2);
			    
				runningDelay1 += delayIncrement1;
				adjustedDelay1 = runningDelay1;
				runningTime1 += runningDelay1;
				
				runningDelay2 += delayIncrement2;
				adjustedDelay2 = runningDelay2;
				runningTime2 += runningDelay2;
			}	
			// Keep rolling the dice until their delays are greater than their final delay
			die1Rolling = runningDelay1 < finalDelay1;
			die2Rolling = runningDelay2 < finalDelay2;
		}
		return new DicePairImpl(die1, die2);
	}
	
	private void wait(int milliseconds)
	{
		try
		{
			TimeUnit.MILLISECONDS.sleep(milliseconds);
		}
		catch (InterruptedException e)
		{
			e.printStackTrace();
		}
	}
	
	private boolean anyParameterInvalid(int initialDelay1, int finalDelay1, int delayIncrement1,
			int initialDelay2, int finalDelay2, int delayIncrement2)
	{
		return anyParamZeroOrLess(initialDelay1, finalDelay1, delayIncrement1)
				|| anyParamZeroOrLess(initialDelay2, finalDelay2, delayIncrement2)
				|| finalDelayLessThanInitialDelay(initialDelay1, finalDelay1)
				|| finalDelayLessThanInitialDelay(initialDelay2, finalDelay2)
				|| delayIncrementsTooLarge(initialDelay1, finalDelay1, delayIncrement1)
				|| delayIncrementsTooLarge(initialDelay2, finalDelay2, delayIncrement2);
	}
	
	private boolean anyParamZeroOrLess(int initialDelay, int finalDelay, int delayIncrement)
	{
		return initialDelay <= 0 || finalDelay <= 0 || delayIncrement <= 0;
	}
	
	private boolean finalDelayLessThanInitialDelay(int initialDelay, int finalDelay)
	{
		return finalDelay < initialDelay;
	}
	
	private boolean delayIncrementsTooLarge(int initialDelay, int finalDelay, int delayIncrement)
	{
		return delayIncrement > finalDelay - initialDelay;
	}

	@Override
	public void applyWinLoss(Player player, DicePair houseResult)
	{
		int compareToResult = player.getResult().compareTo(houseResult);
		
    	if (compareToResult < 0)
		{
    		player.setPoints(player.getPoints() - player.getBet());
		}
    	
    	else if (compareToResult > 0)
    	{
    		player.setPoints(player.getPoints() + player.getBet());
    	}
	}

	@Override
	public void addPlayer(Player player)
	{
		// HashMaps replace additions with the same key, so the specification is followed
		players.put(player.getPlayerId(), player);
	}

	@Override
	public Player getPlayer(String id)
	{
		return players.containsKey(id) ? players.get(id) : null;
	}

	@Override
	public boolean removePlayer(Player player)
	{
		if (players.containsKey(player.getPlayerId()))
		{
			players.remove(player.getPlayerId());
			return true;
		}
		return false;
	}

	@Override
	public boolean placeBet(Player player, int bet)
	{
		return player.setBet(bet);
	}

	@Override
	public void addGameEngineCallback(GameEngineCallback gameEngineCallback)
	{
		callbacks.add(gameEngineCallback);
	}

	@Override
	public boolean removeGameEngineCallback(GameEngineCallback gameEngineCallback)
	{
		return callbacks.remove(gameEngineCallback);
	}

	@Override
	public Collection<Player> getAllPlayers()
	{
		return Collections.unmodifiableCollection(players.values());
	}
}
