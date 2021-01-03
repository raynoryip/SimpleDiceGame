package view;

import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;

import model.DiceAppModel;
import model.interfaces.DicePair;
import model.interfaces.Die;
import model.interfaces.GameEngine;
import model.interfaces.Player;
import view.interfaces.GameEngineCallback;

public class GameEngineCallbackGUI implements GameEngineCallback {

	private DiceAppModel model;
	
	public GameEngineCallbackGUI(DiceAppModel model) {
		this.model = model;
	}
	
	@Override
	public void playerDieUpdate(Player player, Die die, GameEngine gameEngine) {
		SwingUtilities.invokeLater(new Runnable() {
			@Override
			public void run() {
				model.updateDicePanel(die);
			}
		});
		
	}

	@Override
	public void houseDieUpdate(Die die, GameEngine gameEngine) {
		SwingUtilities.invokeLater(new Runnable() {
			@Override
			public void run() {
				model.updateDicePanel(die);
			}
		});
		
	}

	@Override
	public void playerResult(Player player, DicePair result, GameEngine gameEngine) {
		String output = "Player " + player.getPlayerName() + " has rolled a DicePair " + 
								result.getDie1().getValue() + " and " + result.getDie2().getValue();
		SwingUtilities.invokeLater(new Runnable() {
			@Override
			public void run() {
				model.updatePlayerResult(player, result, output);
				JOptionPane.showMessageDialog(null, output);
			}
		});
		
	}

	@Override
	public void houseResult(DicePair result, GameEngine gameEngine) {
		String output = "House has rolled a DicePair " + 
				result.getDie1().getValue() + " and " + result.getDie2().getValue();
		SwingUtilities.invokeLater(new Runnable() {
		@Override
		public void run() {
			model.updateHouseResult(result, output);
			JOptionPane.showMessageDialog(null, output);
			}
		});		
	}

}
