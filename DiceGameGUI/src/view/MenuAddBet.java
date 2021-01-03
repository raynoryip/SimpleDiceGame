package view;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

import javax.swing.JMenuItem;

import controllers.AddBetController;
import model.DiceAppModel;

@SuppressWarnings("serial")
public class MenuAddBet extends JMenuItem implements PropertyChangeListener {
	
	public MenuAddBet(DiceAppModel model) {
		this.setText("Place Bet");
		this.addActionListener(new AddBetController(model));
		this.buttonState(false);
		model.addPropertyChangeListener(this);
	}
	
	public void buttonState(boolean state) {
		this.setEnabled(state);
	}

	@Override
	public void propertyChange(PropertyChangeEvent evt) {
		if(evt.getPropertyName()==DiceAppModel.Events.ADD_BET_ENABLE) {
			buttonState(true);
		}
		if(evt.getPropertyName()==DiceAppModel.Events.ADD_BET_DISABLE) {
			buttonState(false);
		}
	}
	
}
