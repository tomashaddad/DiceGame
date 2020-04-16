package model;

import java.util.List;

import model.interfaces.Die;
import model.interfaces.DieRollHandler;
import model.interfaces.GameEngine;
import model.interfaces.Player;
import view.interfaces.GameEngineCallback;

public class PlayerDieRollHandler implements DieRollHandler
{
	private Player player;
	private List<GameEngineCallback> callbacks;
	private GameEngine gameEngine;
	
	public PlayerDieRollHandler(Player player, List<GameEngineCallback> callbacks, GameEngineImpl gameEngine)
	{
		this.player = player;
		this.callbacks = callbacks;
		this.gameEngine = gameEngine;
	}

	@Override
	public void handle(Die die)
	{
		for (GameEngineCallback callback : callbacks)
		{
			callback.playerDieUpdate(player, die, gameEngine);
		}
	}

}
