package view;

import java.awt.GridLayout;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

import javax.swing.AbstractButton;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JToolBar;

import controllers.PlayerRollController;
import controllers.RollTestController;
import model.DiceAppModel;

@SuppressWarnings("serial")
public class DieToolBar extends JToolBar implements PropertyChangeListener {
	
	private AbstractButton rollButton;
	private AbstractButton playerRollButton;
	private Menu diceMenu;
	private JComboBox<String> comboBox;
	private DiceAppModel model;
	
	public DieToolBar(DiceAppModel model) {
		
		this.model = model;
		
		setLayout(new GridLayout(1,0));
		
		diceMenu = new Menu(model);
		add(diceMenu);
		
		rollButton = new JButton("Roll Test");
		rollButton.addActionListener(new RollTestController(model));
		
		playerRollButton = new JButton("Player Roll");
		playerRollButton.addActionListener(new PlayerRollController(model));
		
		add(rollButton);
		add(playerRollButton);
		
		comboBox = new PlayerComboBox(model);
		add(comboBox);
		
		playerButtonState(false);
		
		this.model.addPropertyChangeListener(this);
	
	}

	public void playerButtonState(boolean state) {
		playerRollButton.setEnabled(state);
	}
	
	public void rollButtonState(boolean state) {
		rollButton.setEnabled(state);
	}
	
	@Override
	public void propertyChange(PropertyChangeEvent evt) {
		if(evt.getPropertyName()==DiceAppModel.Events.PLAYER_ROLL_BUTTON_ENABLE) {
			playerButtonState(true);
		}
		if(evt.getPropertyName()==DiceAppModel.Events.PLAYER_ROLL_BUTTON_DISABLE) {
			playerButtonState(false);
		}
		
		if(evt.getPropertyName()==DiceAppModel.Events.TRY_ROLL_BUTTON_DISABLE) {
			rollButtonState(false);
		}
		if(evt.getPropertyName()==DiceAppModel.Events.TRY_ROLL_BUTTON_ENABLE) {
			rollButtonState(true);
		}
		
		
	}
	

}
