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
	GameEngine thisGameEngine = this;
	DicePair dicePair;
	
	@Override
	public void rollPlayer(Player player, int initialDelay1, int finalDelay1, int delayIncrement1,
			int initialDelay2, int finalDelay2, int delayIncrement2)
	{
		if (anyParameterInvalid(initialDelay1, finalDelay1, delayIncrement1,
				initialDelay2, finalDelay2, delayIncrement2))
		{
			throw new IllegalArgumentException();
		}

		while (initialDelay1 < finalDelay1)
		{
			dicePair = new DicePairImpl();
			
			delay(initialDelay1);
	        
	        for (GameEngineCallback callback : callbacks)
	        {
	        	callback.playerDieUpdate(player, dicePair.getDie1(), this);
	        	callback.playerDieUpdate(player, dicePair.getDie2(), this);
	        }
	        initialDelay1 += delayIncrement1;
		}
		
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

		while (initialDelay1 < finalDelay1)
		{
			dicePair = new DicePairImpl();
			
			delay(initialDelay1);
	        
	        for (GameEngineCallback callback : callbacks)
	        {
	        	callback.houseDieUpdate(dicePair.getDie1(), this);
	        	callback.houseDieUpdate(dicePair.getDie2(), this);
	        }
	        initialDelay1 += delayIncrement1;
		}
        
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
	
	private void delay(int milliseconds)
	{
        try
        {
        	Thread.sleep(milliseconds);
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
