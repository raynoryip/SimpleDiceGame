package model;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.ListIterator;
import java.util.concurrent.ThreadLocalRandom;

import javax.swing.JOptionPane;

import model.interfaces.DicePair;
import model.interfaces.Die;
import model.interfaces.GameEngine;
import model.interfaces.Player;
import view.interfaces.GameEngineCallback;
import view.GameEngineCallbackGUI;

public class DiceAppModel {
	
	public class Events{
		public static final String DIE_VAL_CHANGE = "Die Value Change";
		public static final String CONSOLE_CHANGE = "Console Change";
		public static final String PLAYER_ROLL_BUTTON_DISABLE = "Player Roll Button Disable";
		public static final String PLAYER_ROLL_BUTTON_ENABLE = "Player Roll Button Enable";
		public static final String TRY_ROLL_BUTTON_DISABLE = "Try Roll Button Disable";
		public static final String TRY_ROLL_BUTTON_ENABLE = "Try Roll Button Enable";
		public static final String TRY_ROLL = "Try Roll";
		public static final String DIE_CONSOLE_UPDATE = "Die Console Update";
		public static final String DIE_1_STATUS_CHANGE = "Die 1 Status Change";
		public static final String DIE_2_STATUS_CHANGE = "Die 2 Status Change";
		public static final String ADD_PLAYER_DISABLE = "Add Player Disable";
		public static final String ADD_PLAYER_ENABLE = "Add Player Enable";
		public static final String REMOVE_PLAYER_DISABLE = "Remove Player Disable";
		public static final String REMOVE_PLAYER_ENABLE = "Remove Player Enable";
		public static final String ADD_BET_DISABLE = "Add Bet Disable";
		public static final String ADD_BET_ENABLE = "Add Bet Enable";
		public static final String REMOVE_BET_DISABLE = "Remove Bet Disable";
		public static final String REMOVE_BET_ENABLE = "Remove Bet Enable";
		public static final String COMBO_BOX_UPDATE = "Combo Box Update";
		public static final String UPDATE_SUMMARY_PANEL = "Update Summary Panel";
		public static final String CLEAR_SUMMARY_PANEL = "Clear Summary Panel";
		public static final String FINAL_SUMMARY_PANEL = "Final Summary Panel";
		
	}
	
	public final int initialDelay1 = 100;
	public final int finalDelay1 = 1000;
	public final int delayIncrement1 = 100;
	public final int initialDelay2 = 50;
	public final int finalDelay2 = 500;
	public final int delayIncrement2 = 50;
	
	
	public static final int NUM_FACE = 6;
	private GameEngine gameEngine;
	private final PropertyChangeSupport pcs = new PropertyChangeSupport(this);
	private Collection<Player> playerList = new ArrayList<Player>();
	private Collection<Player> playerHasntPlaceBet= new ArrayList<Player>();
	private Collection<Player> playerPlacedBet = new ArrayList<Player>();
	private Collection<Player> playerCouldRemove = new ArrayList<Player>();
	
	private Collection<Player> playerRolled = new ArrayList<Player>();
	private HashMap<Player, String> winLose = new HashMap<Player, String>();
	
	private DicePair houseResult;
	private Player currentSelectedPlayer;
	private Die die1;
	private Die die2;
	private int nextPlayerID;
	
	
	/*
	 * Constructor of the DiceAppModel
	 * Instantiate Objects
	 */
	public DiceAppModel() {
		super();
		gameEngine = new GameEngineImpl();
		die1 = new DieImpl(1,1,NUM_FACE);
		die2 = new DieImpl(2,1,NUM_FACE);
		this.nextPlayerID = 1;
	}
	
	public void updateDicePanel(Die die) {
		if(die.getNumber()==1) {
			this.die1 = die;
		}
		if(die.getNumber()==2) {
			this.die2 = die;
		}
		pcs.firePropertyChange(Events.DIE_VAL_CHANGE, 0, die);
	}
	
	
	/*
	 * This method roll the dice of the player.
	 * First, creates a callback and then adds to the engine
	 * Second disable both roll player and try roll button
	 * and then add that player into the playerRolled List and couldn't remove this player anymore (in this round)
	 * After that, the program generates dice results and display to the GUI
	 * and then enable the try roll button again
	 */
	
	public void rollDie() {
		GameEngineCallback currentCallback = new GameEngineCallbackGUI(this);
		gameEngine.addGameEngineCallback(currentCallback);
		
		pcs.firePropertyChange(Events.TRY_ROLL_BUTTON_DISABLE, 0, false);
		pcs.firePropertyChange(Events.PLAYER_ROLL_BUTTON_DISABLE, 0, false);
		
		this.playerRolled.add(currentSelectedPlayer);
		this.playerCouldRemove.remove(currentSelectedPlayer);

		new Thread() {
			
			@Override
			public void run() {
				gameEngine.rollPlayer(currentSelectedPlayer, initialDelay1, finalDelay1, delayIncrement1, initialDelay2, finalDelay2, delayIncrement2);
			}
		}.start();
		
		if(this.playerRolled.size()==this.playerList.size()) {
			
			pcs.firePropertyChange(Events.ADD_PLAYER_DISABLE, 0, false);
			pcs.firePropertyChange(Events.REMOVE_PLAYER_DISABLE, 0, false);
			pcs.firePropertyChange(Events.ADD_BET_DISABLE, 0, false);
			pcs.firePropertyChange(Events.REMOVE_BET_DISABLE, 0, false);
			
			JOptionPane.showMessageDialog(null, "All Players has rolled, now The house will roll");
			new Thread() {
				
				@Override
				public void run() {
					gameEngine.rollHouse(initialDelay1, finalDelay1, delayIncrement1, initialDelay2, finalDelay2, delayIncrement2);
				}
			}.start();
			
			JOptionPane.showMessageDialog(null, "Display Final Result");
			ApplyWinLose();
		}
	}
	/*
	 * This method do the applyWinLose and also updating the console Panel and reset the game when all done
	 */
	
	private void ApplyWinLose() {
		
		this.playerList = gameEngine.getAllPlayers(); 
		
		int houseTotal = this.houseResult.getTotal();
		for(Player p: playerList) {
			if(p.getResult().getTotal() < houseTotal) {
				winLose.put(p, "Lose");
			}else if(p.getResult().getTotal() > houseTotal) {
				winLose.put(p, "Win");
			}else {
				winLose.put(p, "Draw");
			}
		}
		
		String output = "House Pair -> " + houseResult.getDie1().getValue() + " and " + houseResult.getDie2().getValue() + "\n";
		output += "House Total -> " + houseResult.getTotal() + "\n";
		output += "----------------\n";
		output += "Player's Result -> \n";
		for(HashMap.Entry<Player, String> entry : this.winLose.entrySet()) {
			Player player = entry.getKey();
			int die1Val = player.getResult().getDie1().getValue();
			int die2Val = player.getResult().getDie2().getValue();
			output += "Player " + player.getPlayerName() + " has a dice Pair -> " + die1Val + " and " + die2Val + ", Total -> " + player.getResult().getTotal() + "\n";
			output += "WinLose -> " + entry.getValue() + "\n";
		}
		pcs.firePropertyChange(Events.CONSOLE_CHANGE, 0, output);
		pcs.firePropertyChange(Events.FINAL_SUMMARY_PANEL, 0, winLose);
		JOptionPane.showMessageDialog(null, "Reset all bets, starting next round");
		resetTheGame();
		
	}
	
	/*
	 *  This method reset the list for next game
	 *  
	 *  -> Automatically remove player with 0 points
	 *  
	 *  1) Reset playerCouldRemove List to default all players
	 *  2) Reset PlayerHasn't Place Bet to default all players
	 *  3) Reset PlayerPlacedBet to empty
	 *  4) Clear Player Rolled List
	 *  5) Clear the winLose state 
	 *  6) Reset the dice Panel
	 */
	private void resetTheGame() {
		
		//Using iterator to remove
		ListIterator<Player> iter = ((ArrayList<Player>) this.playerList).listIterator();
		while(iter.hasNext()) {
			if(iter.next().getPoints() <= 0) {
				iter.remove();
			}
		}
		
		pcs.firePropertyChange(Events.COMBO_BOX_UPDATE, 0, 1);
		
		this.playerCouldRemove.clear();
		
		for(Player p: this.playerList) {
			playerCouldRemove.add(p);
			playerHasntPlaceBet.add(p);
		}
		playerPlacedBet.clear();
		playerRolled.clear();
		winLose.clear();
		die1 = new DieImpl(1,1,NUM_FACE);
		die2 = new DieImpl(2,1,NUM_FACE);
		updateDie1Panel();
		updateDie2Panel();
		
		pcs.firePropertyChange(Events.ADD_PLAYER_ENABLE, 0, true);
		if(this.playerList.size()>0) {
			pcs.firePropertyChange(Events.REMOVE_PLAYER_ENABLE, 0, true);
			pcs.firePropertyChange(Events.ADD_BET_ENABLE, 0, true);
		}	
	}
	
	/*
	 * This method is for testing the Dice Roll without passing any data to the player or house
	 */
	public void rollDieTest() {
	
		String textOutput = "Dice Roll testing with initialDelay, finalDelay, DelayIncrement\n" +
										"Die 1 -> 100, 1000, 100\n" +
										"Die 2 -> 50, 500, 50\n";
		
		pcs.firePropertyChange(Events.DIE_CONSOLE_UPDATE, 0, textOutput);
			
		new Thread() {
			
				int currentDelay1 = initialDelay1;
				
				@Override
				public void run() {
					try {
						Thread.sleep(initialDelay1);
						while(currentDelay1 < finalDelay1) {
							Thread.sleep(delayIncrement1);
							int randNum = ThreadLocalRandom.current().nextInt(1, NUM_FACE+1);
							die1 = new DieImpl(1, randNum, NUM_FACE);
							updateDie1Panel();
							currentDelay1  += delayIncrement1;
						}
						}catch (InterruptedException e) {
							e.printStackTrace();
						}
				}
			}.start();
			
			new Thread() {
				
				int currentDelay2 = initialDelay2;
				
				@Override
				public void run() {
					try {
						Thread.sleep(initialDelay2);
						while(currentDelay2 < finalDelay2) {
							Thread.sleep(delayIncrement2);
							int randNum = ThreadLocalRandom.current().nextInt(1, NUM_FACE+1);
							die2 = new DieImpl(2, randNum, NUM_FACE);
							updateDie2Panel();
							currentDelay2  += delayIncrement2;
						}
						}catch (InterruptedException e) {
							e.printStackTrace();
						}
				}
			}.start();	
	}
	
	public Die getDie1() {
		return this.die1;
	}
	
	public Die getDie2() {
		return this.die2;
	}
	
	/*
	 * This method returns a Object array for all players 
	 */
	
	public Object[] getPlayersName(){
		Collection<String> playersName = new ArrayList<String>();
		for(Player p: this.playerList) {
			playersName.add(p.getPlayerId() + " " + p.getPlayerName());
		}
		return playersName.toArray();
	}
	
	/*
	 * This method returns a Object array if player hasn't placed bet 
	 */
	
	public Object[] getPlayersHasntPlaceBet(){
		Collection<String> playersName = new ArrayList<String>();
		for(Player p: this.playerHasntPlaceBet) {
			playersName.add(p.getPlayerId() + " " + p.getPlayerName());
		}
		return playersName.toArray();
	}
	
	/*
	 * This method returns a Object array if player has placed bet 
	 * and this player hasn't rolled
	 */
	
	public Object[] getPlayersPlacedBet(){
		Collection<String> playersName = new ArrayList<String>();
		for(Player p: this.playerPlacedBet) {
			if(!this.playerRolled.contains(p)) {
				playersName.add(p.getPlayerId() + " " + p.getPlayerName());
			}
		}
		return playersName.toArray();
	}
	
	public Object[] getPlayersRemoveName() {
		Collection<String> playersName = new ArrayList<String>();
		for(Player p: this.playerCouldRemove) {
			playersName.add(p.getPlayerId() + " " + p.getPlayerName());
		}
		return playersName.toArray();
	}
	
	
	/*
	 * This method remove player by their name,
	 * and remove them from all the existing collections
	 */
	
	public void removePlayer(String playerID) {
		Player playerToRemove = null;
		for(Player p: this.playerCouldRemove) {
			if(p.getPlayerId().equals(playerID)) {
				playerToRemove = p;
				break;
			}
		}
		//Remove if they exist
		String output = "Player " + playerToRemove.getPlayerName() + " has been removed.";
		gameEngine.removePlayer(playerToRemove);
		this.playerList.remove(playerToRemove);
		this.playerHasntPlaceBet.remove(playerToRemove);
		this.playerPlacedBet.remove(playerToRemove);
		this.playerCouldRemove.remove(playerToRemove);
		
		pcs.firePropertyChange(Events.CONSOLE_CHANGE, 0, output);
		pcs.firePropertyChange(Events.COMBO_BOX_UPDATE, 0, 1);
		pcs.firePropertyChange(Events.UPDATE_SUMMARY_PANEL, 0, this.currentSelectedPlayer);
		
		if(this.playerList.size()==0) {
			pcs.firePropertyChange(Events.REMOVE_PLAYER_DISABLE, 0, false);
			pcs.firePropertyChange(Events.ADD_BET_DISABLE, 0, false);
		}
	}
	

	public void updatePlayerResult(Player player, DicePair result, String output) {
		die1 = result.getDie1();
		die2 = result.getDie2();
		
		this.currentSelectedPlayer.setResult(result);
		
		//Update Status Bar and Dice Panel 
		updateDie1Panel();
		updateDie2Panel();
		
		//Update Console
		pcs.firePropertyChange(Events.CONSOLE_CHANGE, 0, output);
		
	}
	
	public void updateHouseResult(DicePair result, String output) {
		this.houseResult = result;
		die1 = result.getDie1();
		die2 = result.getDie2();
		
		//Update Status Bar and Dice Panel 
		updateDie1Panel();
		updateDie2Panel();
		
		pcs.firePropertyChange(Events.CONSOLE_CHANGE, 0, output);
		
	}
	
	/*
	 *  This method add bet for the selected user and update all relevant changes in UI
	 */
	
	public void placeBet(Player player, String output) {
		
		this.playerPlacedBet.add(player);
		this.playerHasntPlaceBet.remove(player);
		pcs.firePropertyChange(Events.CONSOLE_CHANGE, 0, output);
		pcs.firePropertyChange(Events.UPDATE_SUMMARY_PANEL, 0, this.currentSelectedPlayer);
		if(this.playerPlacedBet.size() > 0) {
			pcs.firePropertyChange(Events.REMOVE_BET_ENABLE, 0, true);
		}
		if(this.playerHasntPlaceBet.size()==0) {
			pcs.firePropertyChange(Events.ADD_BET_DISABLE, 0, true);
		}
		
		updateRollButton();
	}
	
	/*
	 *  This method remove bet for the selected user and update all relevant changes in UI
	 */
	
	public void removeBet(Player player, String output) {
		
		this.playerPlacedBet.remove(player);
		this.playerHasntPlaceBet.add(player);
		pcs.firePropertyChange(Events.CONSOLE_CHANGE, 0, output);
		pcs.firePropertyChange(Events.UPDATE_SUMMARY_PANEL, 0, this.currentSelectedPlayer);
		
		if(this.playerPlacedBet.size() == 0) {
			pcs.firePropertyChange(Events.REMOVE_BET_DISABLE, 0, true);
		}
		if(this.playerHasntPlaceBet.size() > 0) {
			pcs.firePropertyChange(Events.ADD_BET_ENABLE, 0, true);
		};
		
		updateRollButton();
		
	}
	
	/*
	 *  This method updates all the relevant view in the entire application when 
	 *  a user being selected from the comboBox 
	 */
	public void UpdateAllInfo(String userItem) {
		String playerID = userItem.split(" ")[0];
		Player player = this.getSpecificPlayer(playerID);
		this.currentSelectedPlayer = player;
		
		updateRollButton();
		
		DicePair dp = player.getResult();
		if(dp!=null) {
			die1 = dp.getDie1();
			die2 = dp.getDie2();
			updateDie1Panel();
			updateDie2Panel();
		}
		
		pcs.firePropertyChange(Events.UPDATE_SUMMARY_PANEL, 0, this.currentSelectedPlayer);
		
	}
	
	/*
	 *  This method add a new player and update all relevant changes in UI
	 */

	public void addPlayer(String playerName, int points) {
		Player player = new SimplePlayer(Integer.toString(this.nextPlayerID), playerName, points);
		this.playerList.add(player);
		this.playerHasntPlaceBet.add(player);
		playerCouldRemove.add(player);
		gameEngine.addPlayer(player);
		
		
		String output = "New Player " + playerName + " with points " + points + " is created";
		
		pcs.firePropertyChange(Events.CONSOLE_CHANGE, 0, output);
		pcs.firePropertyChange(Events.REMOVE_PLAYER_ENABLE, 0, true);
		pcs.firePropertyChange(Events.ADD_BET_ENABLE, 0, false);
		pcs.firePropertyChange(Events.CLEAR_SUMMARY_PANEL, 0, this.currentSelectedPlayer);
		pcs.firePropertyChange(Events.COMBO_BOX_UPDATE, 0, 1);
		this.nextPlayerID += 1;
	}
	
	/*
	 * Below are UI updating Methods and some getters and firePropertyListener Method
	 */
	private void updateRollButton() {

		if(this.playerHasntPlaceBet.contains(this.currentSelectedPlayer)) {
			pcs.firePropertyChange(Events.PLAYER_ROLL_BUTTON_DISABLE, 0, false);
			pcs.firePropertyChange(Events.TRY_ROLL_BUTTON_ENABLE, 0, false);
		}else{
			if(this.playerRolled.contains(this.currentSelectedPlayer)) {
				pcs.firePropertyChange(Events.PLAYER_ROLL_BUTTON_DISABLE, 0, false);
				pcs.firePropertyChange(Events.TRY_ROLL_BUTTON_DISABLE, 0, false);
			}
			else{
				pcs.firePropertyChange(Events.PLAYER_ROLL_BUTTON_ENABLE, 0, true);
				pcs.firePropertyChange(Events.TRY_ROLL_BUTTON_ENABLE, 0, true);
			}
		}
	}

	
	private void updateDie1Panel() {
		pcs.firePropertyChange(Events.DIE_VAL_CHANGE, 0, die1);
		pcs.firePropertyChange(Events.DIE_1_STATUS_CHANGE, 0, die1.getValue());
	}
	
	private void updateDie2Panel() {
		pcs.firePropertyChange(Events.DIE_VAL_CHANGE, 0, die2);
		pcs.firePropertyChange(Events.DIE_2_STATUS_CHANGE, 0, die2.getValue());
	}
	
	public Player getSpecificPlayer(String playerID) {
		 return gameEngine.getPlayer(playerID);
	}
	
	public void addPropertyChangeListener(PropertyChangeListener listener) {
		this.pcs.addPropertyChangeListener(listener);
	}

	
}
