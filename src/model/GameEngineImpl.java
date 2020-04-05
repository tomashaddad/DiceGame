package model;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

import model.interfaces.DicePair;
import model.interfaces.GameEngine;
import model.interfaces.Player;
import view.interfaces.GameEngineCallback;

public class GameEngineImpl implements GameEngine
{
	HashMap<String, Player> players = new HashMap<>();
	List<GameEngineCallback> callbacks = new ArrayList<>(); 

	@Override
	public void rollPlayer(Player player, int initialDelay1, int finalDelay1, int delayIncrement1, int initialDelay2,
			int finalDelay2, int delayIncrement2)
	{
		if (anyParamZeroOrLess(initialDelay1, finalDelay1, delayIncrement1, initialDelay2, finalDelay2, delayIncrement2) ||
			finalDelayLessThanInitialDelay(initialDelay1, finalDelay1, initialDelay2, finalDelay2) ||
			delayIncrementsTooLarge(initialDelay1, finalDelay1, delayIncrement1, initialDelay2, finalDelay2, delayIncrement2))
		{
			throw new IllegalArgumentException();
		}

		DicePair playerDicePair = new DicePairImpl();
		
		while (initialDelay1 < finalDelay1)
		{
			playerDicePair = new DicePairImpl();
			
	        try
	        {
	        	Thread.sleep(initialDelay1);
	        }
	        catch (InterruptedException e)
	        {
	        	e.printStackTrace();
	        }
	        
	        for (GameEngineCallback callback : callbacks)
	        {
	        	callback.playerDieUpdate(player, playerDicePair.getDie1(), this);
	        	callback.playerDieUpdate(player, playerDicePair.getDie2(), this);
	        }
	        initialDelay1 += delayIncrement1;
		}
		
        for (GameEngineCallback callback : callbacks)
        {
        	callback.playerResult(player, playerDicePair, this);
        }
	}

	@Override
	public void rollHouse(int initialDelay1, int finalDelay1, int delayIncrement1, int initialDelay2, int finalDelay2,
			int delayIncrement2)
	{
		// TODO: Finish this method to the specifications of the interface
		
		DicePair houseDicePair = new DicePairImpl();
		
		while (initialDelay1 < finalDelay1)
		{
			houseDicePair = new DicePairImpl();
			
	        try
	        {
	        	Thread.sleep(initialDelay1);
	        }
	        catch (InterruptedException e)
	        {
	        	e.printStackTrace();
	        }
	        
	        for (GameEngineCallback callback : callbacks)
	        {
	        	callback.houseDieUpdate(houseDicePair.getDie1(), this);
	        	callback.houseDieUpdate(houseDicePair.getDie2(), this);
	        }
	        initialDelay1 += delayIncrement1;
		}
		
        for (GameEngineCallback callback : callbacks)
        {
        	callback.houseResult(houseDicePair, this);
        }
	}
	

	// Helper method to check if any parameters are less than 0
	private boolean anyParamZeroOrLess(int initialDelay1, int finalDelay1, int delayIncrement1, int initialDelay2, int finalDelay2,
			int delayIncrement2)
	{
		if (initialDelay1 <= 0 || finalDelay1 <= 0 || delayIncrement1 <= 0 || initialDelay2 <= 0 || finalDelay2 <= 0 || delayIncrement2 <= 0)
		{
			return true;
		}
		return false;
	}
	
	// Helper method to check if either final delay is less than the initial delay
 
	private boolean finalDelayLessThanInitialDelay(int initialDelay1, int finalDelay1, int initialDelay2, int finalDelay2)
	{
		if (finalDelay1 < initialDelay1 || finalDelay2 < initialDelay2)
		{
			return true;
		}
		return false;
	}
	
	// Helper method to check if either delay increment is longer than the difference between the initial and final delays
	private boolean delayIncrementsTooLarge(int initialDelay1, int finalDelay1, int delayIncrement1, int initialDelay2, int finalDelay2,
			int delayIncrement2)
	{
		if (delayIncrement1 > finalDelay1 - initialDelay1 || delayIncrement2 > finalDelay2 - initialDelay2)
		{
			return true;
		}
		return false;
	}

	@Override
	public void applyWinLoss(Player player, DicePair houseResult)
	{
		// TODO Auto-generated method stub

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
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public Collection<Player> getAllPlayers()
	{
		return Collections.unmodifiableCollection(players.values());
	}
}
