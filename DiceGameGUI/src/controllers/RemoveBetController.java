package controllers;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JOptionPane;

import model.DiceAppModel;
import model.interfaces.Player;

public class RemoveBetController implements ActionListener {

	private DiceAppModel model;
	
	public RemoveBetController(DiceAppModel model) {
		this.model = model;
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		
		Object[] possibilities = this.model.getPlayersPlacedBet();
		
		if(possibilities.length>0) {
			String option = (String) JOptionPane.showInputDialog(null,"Choose a player to remove Bet", 
					"remove bet", JOptionPane.PLAIN_MESSAGE, null, possibilities,possibilities[0]);
			
			if (option!=null) {
				String playerID = option.split(" ")[0];
				Player selectedPlayer = model.getSpecificPlayer(playerID);
				selectedPlayer.resetBet();
				
				String output = "Player " + selectedPlayer.getPlayerName() + "'s Bet has reset to 0";
				model.removeBet(selectedPlayer, output);
			}
		}
	}

}
