package view;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

import javax.swing.JMenuItem;
import controllers.RemoveBetController;
import model.DiceAppModel;

@SuppressWarnings("serial")
public class MenuRemoveBet extends JMenuItem implements PropertyChangeListener {
	
	public MenuRemoveBet(DiceAppModel model) {
		this.setText("Remove Bet");
		this.addActionListener(new RemoveBetController(model));
		this.buttonState(false);
		model.addPropertyChangeListener(this);
	}
	
	public void buttonState(boolean state) {
		this.setEnabled(state);
	}

	@Override
	public void propertyChange(PropertyChangeEvent evt) {
		if(evt.getPropertyName()==DiceAppModel.Events.REMOVE_BET_ENABLE) {
			buttonState(true);
		}
		if(evt.getPropertyName()==DiceAppModel.Events.REMOVE_BET_DISABLE) {
			buttonState(false);
		}
	}
	
}