package model;

import java.util.List;

import model.interfaces.Die;
import model.interfaces.DieRollHandler;
import model.interfaces.GameEngine;
import view.interfaces.GameEngineCallback;

public class HouseDieRollHandler implements DieRollHandler
{
	private GameEngine gameEngine;
	private List<GameEngineCallback> callbacks;

	public HouseDieRollHandler(List<GameEngineCallback> callbacks, GameEngine gameEngine)
	{
		this.callbacks = callbacks;
		this.gameEngine = gameEngine;
	}

	@Override
	public void handle(Die die)
	{
		for (GameEngineCallback callback : callbacks)
		{
			callback.houseDieUpdate(die, gameEngine);
		}
	}

}
