package view;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

import javax.swing.JMenuItem;

import controllers.AddPlayerController;
import model.DiceAppModel;

@SuppressWarnings("serial")
public class MenuAddPlayer extends JMenuItem implements PropertyChangeListener {
	
	public MenuAddPlayer(DiceAppModel model) {
		this.setText("Add a Player");
		this.addActionListener(new AddPlayerController(model));
		model.addPropertyChangeListener(this);
	}
	
	public void buttonState(boolean state) {
		this.setEnabled(state);
	}

	@Override
	public void propertyChange(PropertyChangeEvent evt) {
		if(evt.getPropertyName()==DiceAppModel.Events.ADD_PLAYER_DISABLE) {
			buttonState(false);
		}
		if(evt.getPropertyName()==DiceAppModel.Events.ADD_PLAYER_ENABLE) {
			buttonState(true);
		}	
	}

}
