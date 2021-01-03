package controllers;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JOptionPane;
import model.DiceAppModel;

public class RemovePlayerController implements ActionListener {

	private DiceAppModel model;
	
	public RemovePlayerController(DiceAppModel model) {
		this.model = model;
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		
		Object[] possibilities = this.model.getPlayersRemoveName();
		
		String option = (String) JOptionPane.showInputDialog(null,"Please choose a player to remove", 
				"Remove Player", JOptionPane.PLAIN_MESSAGE, null, possibilities,possibilities[0]);
		if(option!=null) {
			String playerID = option.split(" ")[0];
			model.removePlayer(playerID);
		}
	}

}
