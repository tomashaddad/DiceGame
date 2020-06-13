package model;

import model.interfaces.DicePair;
import model.interfaces.Player;

public class SimplePlayer implements Player
{
	private String playerId;
	private String playerName;
	private int points;
	private int bet;
	private DicePair result;
	
	public static int playerIDCounter = 0;
	
	public SimplePlayer(String playerId, String playerName, int initialPoints)
	{
		this.playerId = playerId;
		this.playerName = playerName;
		this.points = initialPoints;
	}
	
	@Override
	public String getPlayerName()
	{
		return playerName;
	}

	@Override
	public void setPlayerName(String playerName)
	{
		this.playerName = playerName;	
	}

	@Override
	public int getPoints()
	{
		return points;
	}

	@Override
	public void setPoints(int points)
	{
		this.points = points;
	}

	@Override
	public String getPlayerId()
	{
		return playerId;
	}

	@Override
	public boolean setBet(int bet)
	{
		if (bet > 0 && points >= bet)
		{
			this.bet = bet;
			return true;
		}
		return false;
	}

	@Override
	public int getBet()
	{
		return bet;
	}

	@Override
	public void resetBet()
	{
		bet = 0;
	}

	@Override
	public DicePair getResult()
	{
		return result;
	}

	@Override
	public void setResult(DicePair rollResult)
	{
		result = rollResult;
	}
	
	public String toString()
	{
		return String.format(
				"Player: id=%s, name=%s, bet=%d, points=%d, RESULT .. %s",
				playerId,
				playerName,
				bet,
				points,
				result == null ? "None" : result.toString());
	}
}