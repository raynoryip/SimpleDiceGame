package controllers;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JOptionPane;

import model.DiceAppModel;
import model.interfaces.Player;

public class AddBetController implements ActionListener {
	
	private DiceAppModel model;
	
	public AddBetController(DiceAppModel model) {
		this.model = model;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		
		Object[] possibilities = this.model.getPlayersHasntPlaceBet();
		
		String option = (String) JOptionPane.showInputDialog(null,"Choose a player to place Bet", 
				"Add bet", JOptionPane.PLAIN_MESSAGE, null, possibilities,possibilities[0]);
		
		String playerID = option.split(" ")[0];
		if(playerID!=null) {
			Player selectedPlayer = model.getSpecificPlayer(playerID);
			
			String input = (String)JOptionPane.showInputDialog("You can place a bet between " 
										+ selectedPlayer.getPoints() + " and 1");
			
			if(input.isEmpty() || !input.matches("[0-9]+")) {
				JOptionPane.showMessageDialog(null, "You should input a proper Number");
			}else {
				int betAmount = Integer.parseInt(input);
				String output = "Player " + selectedPlayer.getPlayerName() + " has successfully placed " + betAmount + " Bet.";
				
				if(selectedPlayer.setBet(betAmount)) {
					
					model.placeBet(selectedPlayer, output);
					JOptionPane.showMessageDialog(null, output);
					
				}else {
					JOptionPane.showMessageDialog(null, "Your bet amount is invalid");
				}
			}
		}
		
	}

}
