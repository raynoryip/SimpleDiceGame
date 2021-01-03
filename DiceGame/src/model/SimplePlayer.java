package model;

import model.interfaces.DicePair;
import model.interfaces.Player;

public class SimplePlayer implements Player {
	
	private String playerName;
	private String playerId;
	private int points;
	private int bets;
	private DicePair diceResult;
	
	public SimplePlayer(String playerId, String playerName, int initialPoints) {
		this.playerName = playerName;
		this.playerId = playerId;
		this.points = initialPoints;
		this.bets = 0;
	}
	
	@Override
	public String getPlayerName() {
		return this.playerName;
	}

	@Override
	public void setPlayerName(String playerName) {
		this.playerName = playerName;
	}

	@Override
	public int getPoints() {
		return this.points;
	}

	@Override
	public void setPoints(int points) {
		this.points = points;
	}

	@Override
	public String getPlayerId() {
		return this.playerId;
	}

	@Override
	public boolean setBet(int bet) {
		//cond 1: true if bet is greater than 0 [set]
		//cond2: and player has sufficient points to place the bet [not yet] <- 18/04/2020
		
		if(bet >= 1 && bet <= this.points) {
			this.bets = bet;
			return true;
		}
		return false;
	}

	@Override
	public int getBet() {
		return this.bets;
	}

	@Override
	public void resetBet() {
		//reset the bet to 0 for next round (in case player does not bet again in next round)
		this.bets = 0;
	}

	@Override
	public DicePair getResult() {
		// a DicePair containing final dice values of a roll as set by setResult(DicePair)
		return this.diceResult;
	}

	@Override
	public void setResult(DicePair rollResult) {
		//a DicePair containing final dice values (updated from the GameEngine via 
        //GameEngine.rollPlayer(Player, int, int, int, int, int, int))
		this.diceResult = rollResult;
	}
	
	@Override
	public String toString() {
		//OP:  "Player: id=1, name=The Roller, bet=100, points=4900, RESULT .. Dice 1: One,  Dice 2: Three .. Total: 4" 
		String retStr = String.format("Player: id=%s, name=%s, bet=%d, points=%d, RESULT .. %s", 
				this.playerId, this.playerName, this.bets, this.points, this.diceResult.toString());
		return retStr;
	}
	
}
