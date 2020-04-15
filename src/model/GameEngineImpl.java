package model;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.TimeUnit;

import model.interfaces.DicePair;
import model.interfaces.Die;
import model.interfaces.GameEngine;
import model.interfaces.LogRoll;
import model.interfaces.Player;
import util.Rand;
import view.interfaces.GameEngineCallback;

public class GameEngineImpl implements GameEngine
{
	HashMap<String, Player> players = new HashMap<>();
	List<GameEngineCallback> callbacks = new ArrayList<>();
	
	@Override
	public void rollPlayer(Player player, int initialDelay1, int finalDelay1, int delayIncrement1,
			int initialDelay2, int finalDelay2, int delayIncrement2)
	{
		if (anyParameterInvalid(initialDelay1, finalDelay1, delayIncrement1,
				initialDelay2, finalDelay2, delayIncrement2))
		{
			throw new IllegalArgumentException();
		}
		
		// Read -> as "goes to"
		// This whole thing is a callback
		// In the roll method, player is unused. It is only relevant here.
		
        LogRoll myInterface = (die, gameEngine) ->
        { 
			for (GameEngineCallback callback : callbacks)
			{
				callback.playerDieUpdate(player, die, gameEngine);
			}
        };
        
        DicePair dicePair = roll(initialDelay1, finalDelay1, delayIncrement1, initialDelay2, finalDelay2, delayIncrement2, myInterface);
		
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

        LogRoll myInterface = (die, gameEngine) ->
        { 
			for (GameEngineCallback callback : callbacks)
			{
				callback.houseDieUpdate(die, gameEngine);
			}
        };
        
        DicePair dicePair = roll(initialDelay1, finalDelay1, delayIncrement1, initialDelay2, finalDelay2, delayIncrement2, myInterface);
        
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
	
	private DicePair roll(int initialDelay1, int finalDelay1, int delayIncrement1, int initialDelay2, int finalDelay2,
			int delayIncrement2, LogRoll myInterface)
	{
		Die die1 = new DieImpl(1, Rand.getRandomNumberInRange(1, Die.NUM_FACES), Die.NUM_FACES);
		Die die2 = new DieImpl(2, Rand.getRandomNumberInRange(1, Die.NUM_FACES), Die.NUM_FACES);
		
		// Tracks the roll delay by adding the respective delayIncrement to the previous delay
		int runningDelay1 = initialDelay1;
		int runningDelay2 = initialDelay2;
		
		/* Tracks the total time each dice has been rolling for to determine which dice is
		 * rolling next and if that dice should stop rolling */
		int runningTime1 = initialDelay1;
		int runningTime2 = initialDelay2;
		
		boolean die1Rolling = true;
		boolean die2Rolling = true;
		
		while (die1Rolling || die2Rolling)
		{
			// If it's Die1's turn to roll, or if only die 2 has stopped rolling
			if (((runningTime1 < runningTime2) && die1Rolling) || !die2Rolling)
			{
				die1 = new DieImpl(1, Rand.getRandomNumberInRange(1, Die.NUM_FACES), Die.NUM_FACES);
				
			    wait(runningDelay1);
			    
				myInterface.DieUpdate(die1, this);
				
				runningDelay1 += delayIncrement1;
				runningTime1 += runningDelay1;
			}
			
			// If it's Die2's turn to roll, or if only die 1 has stopped rolling
			else if (((runningTime1 > runningTime2) && die2Rolling) || !die1Rolling)
			{
				die2 = new DieImpl(2, Rand.getRandomNumberInRange(1, Die.NUM_FACES), Die.NUM_FACES);
				
				wait(runningDelay2);
				
				myInterface.DieUpdate(die2, this);
				
				runningDelay2 += delayIncrement2;
				runningTime2 += runningDelay2;
			}
			
			// If both die are about to roll
			else if ((runningTime1 == runningTime2) && die1Rolling && die2Rolling)
			{
				die1 = new DieImpl(1, Rand.getRandomNumberInRange(1, Die.NUM_FACES), Die.NUM_FACES);
				die2 = new DieImpl(2, Rand.getRandomNumberInRange(1, Die.NUM_FACES), Die.NUM_FACES);
				
				myInterface.DieUpdate(die1, this);
				myInterface.DieUpdate(die2, this);
				
				runningDelay1 += delayIncrement1;
				runningTime1 += runningDelay1;
				runningDelay2 += delayIncrement2;
				runningTime2 += runningDelay2;
			}
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
		players.put(player.getPlayerId(), player);
	}

	@Override
	public Player getPlayer(String id)
	{
		if (players.containsKey(id))
		{
			return players.get(id);
		}
		return null;
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
		if (player.setBet(bet))
			return true;
		else
			return false;
	}

	@Override
	public void addGameEngineCallback(GameEngineCallback gameEngineCallback)
	{
		callbacks.add(gameEngineCallback);
	}

	@Override
	public boolean removeGameEngineCallback(GameEngineCallback gameEngineCallback)
	{
		return callbacks.remove(gameEngineCallback) ? true : false;
	}

	@Override
	public Collection<Player> getAllPlayers()
	{
		return Collections.unmodifiableCollection(players.values());
	}
}
