package model;

import java.util.Collection;

import model.interfaces.DicePair;
import model.interfaces.GameEngine;
import model.interfaces.Player;
import view.interfaces.GameEngineCallback;

public class GameEngineImpl implements GameEngine
{
	
	Player players[] = new Player[2];

	@Override
	public void rollPlayer(Player player, int initialDelay1, int finalDelay1, int delayIncrement1, int initialDelay2,
			int finalDelay2, int delayIncrement2)
	{
		// TODO Auto-generated method stub

	}

	@Override
	public void rollHouse(int initialDelay1, int finalDelay1, int delayIncrement1, int initialDelay2, int finalDelay2,
			int delayIncrement2)
	{
		// TODO Auto-generated method stub

	}

	@Override
	public void applyWinLoss(Player player, DicePair houseResult)
	{
		// TODO Auto-generated method stub

	}

	@Override
	public void addPlayer(Player player)
	{
		// TODO Auto-generated method stub

	}

	@Override
	public Player getPlayer(String id)
	{
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean removePlayer(Player player)
	{
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean placeBet(Player player, int bet)
	{
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void addGameEngineCallback(GameEngineCallback gameEngineCallback)
	{
		// TODO Auto-generated method stub

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
		// TODO Auto-generated method stub
		return null;
	}

}
