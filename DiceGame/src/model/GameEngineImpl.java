package model;

import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

import java.util.ArrayList;
import java.lang.Thread;
import model.interfaces.DicePair;
import model.interfaces.Die;
import model.interfaces.GameEngine;
import model.interfaces.Player;
import view.GameEngineCallbackImpl;
import view.interfaces.GameEngineCallback;


public class GameEngineImpl implements GameEngine{
	
	private Collection<Player> listOfPlayers = new ArrayList<Player>();
	private List<GameEngineCallback> callbacks = new ArrayList<GameEngineCallback>();
	
	static final int DIE_1 = 1;
	static final int DIE_2 = 2;
	static final int NUM_FACE = 6;
	
	private Die die1;
	private Die die2;
	
	Thread t1;
	Thread t2;
	
	
	//This method Roll the dice and 
	private void rollDice(Player player, int initialDelay1, int finalDelay1, int delayIncrement1, 
			int initialDelay2, int finalDelay2, int delayIncrement2, GameEngineCallback callback, GameEngine gameEngine) {
			
		t1 = new Thread() {
			
			int currentDelay1 = initialDelay1;
			
			@Override
			public void run() {
				try {
					Thread.sleep(initialDelay1);
					while(currentDelay1 < finalDelay1) {
						Thread.sleep(delayIncrement1);
						
						int randNum = ThreadLocalRandom.current().nextInt(1, NUM_FACE+1);
						die1 = new DieImpl(1, randNum, NUM_FACE);
						if(player!=null) {
							callback.playerDieUpdate((SimplePlayer)player, die1, gameEngine);
						}else {
							callback.houseDieUpdate(die1, gameEngine);
						}
						currentDelay1  += delayIncrement1;
					}
					}catch (InterruptedException e) {
						e.printStackTrace();
					}
			}
		};
		
		t2 = new Thread() {
			
			int currentDelay2 = initialDelay2;
			
			@Override
			public void run() {
				try {
					Thread.sleep(initialDelay2);
					while(currentDelay2 < finalDelay2) {
						Thread.sleep(delayIncrement2);
						int randNum = ThreadLocalRandom.current().nextInt(1, NUM_FACE+1);
						die2 = new DieImpl(2, randNum, NUM_FACE);
						if(player!=null) {
							callback.playerDieUpdate((SimplePlayer)player, die2, gameEngine);
						}else {
							callback.houseDieUpdate(die2, gameEngine);
						}
						currentDelay2  += delayIncrement2;
					}
					}catch (InterruptedException e) {
						e.printStackTrace();
					}
			}
		};
	}
	
	
	@Override
	public void rollPlayer(Player player, int initialDelay1, int finalDelay1, int delayIncrement1, int initialDelay2,
			int finalDelay2, int delayIncrement2) {
		
		//get the last added callback
		GameEngineCallback lastCallback = this.callbacks.get(callbacks.size()-1);
		rollDice(player, initialDelay1, finalDelay1, delayIncrement1, initialDelay2,finalDelay2, delayIncrement2, lastCallback, this);
		
		try {
			t1.start();
			t2.start();
			t1.join();
			t2.join();
			
			DicePair dicePair = new DicePairImpl(die1, die2);
			
			player.setResult(dicePair);
			lastCallback.playerResult(player, dicePair, this);
			
		}catch(InterruptedException e) {
			e.printStackTrace();
		}
		
	}
	
	@Override
	public void rollHouse(int initialDelay1, int finalDelay1, int delayIncrement1, int initialDelay2, int finalDelay2,
			int delayIncrement2) {
		
		GameEngineCallback lastCallback = this.callbacks.get(callbacks.size()-1);
		rollDice(null, initialDelay1, finalDelay1, delayIncrement1, initialDelay2,finalDelay2, delayIncrement2, lastCallback, this);
		
		try {
			t1.start();
			t2.start();
			t1.join();
			t2.join();
			
			DicePair dicePair = new DicePairImpl(die1, die2);
			lastCallback.houseResult(dicePair, this);
			
			for(Player player : this.listOfPlayers) {
				this.applyWinLoss(player, dicePair);
				player.resetBet();
			}
			
		}catch(InterruptedException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void applyWinLoss(Player player, DicePair houseResult) {
		int updatePoints = 0;
	   	if(player.getResult().getTotal() > houseResult.getTotal()) {
	   		updatePoints += player.getPoints() + player.getBet();
	   	}
	   	else if(player.getResult().getTotal() < houseResult.getTotal()) {
	   		updatePoints += player.getPoints() - player.getBet();
	   	}
	   	else{
	   		updatePoints = player.getPoints();
	   	}
	    player.setPoints(updatePoints);
	    
	}
	
	@Override
	public void addPlayer(Player player) {
		this.listOfPlayers.add(player);
	}

	@Override
	public Player getPlayer(String id) {
		
		for(Player p: this.listOfPlayers) {
			if(p.getPlayerId().equals(id)) {
				return p;
			}
		}
		 return null;
	}

	@Override
	public boolean removePlayer(Player player) {
		return this.listOfPlayers.remove(player);
	}

	@Override
	public boolean placeBet(Player player, int bet) {
		if(player.setBet(bet)==true) {
			player.setBet(bet);
			return true;
		}else {
			player.resetBet();
			return false;
		}
	}

	@Override
	public void addGameEngineCallback(GameEngineCallback gameEngineCallback) {
		this.callbacks.add(gameEngineCallback);
	}

	@Override
	public boolean removeGameEngineCallback(GameEngineCallback gameEngineCallback) {
		return this.callbacks.remove(gameEngineCallback);
	}

	@Override
	public Collection<Player> getAllPlayers() {
		return this.listOfPlayers;
	}
}

