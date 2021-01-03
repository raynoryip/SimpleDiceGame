package view;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

import javax.swing.JMenuItem;
import controllers.RemovePlayerController;
import model.DiceAppModel;

@SuppressWarnings("serial")
public class MenuRemovePlayer extends JMenuItem implements PropertyChangeListener {
	
	public MenuRemovePlayer(DiceAppModel model) {
		this.setText("Remove a Player");
		this.addActionListener(new RemovePlayerController(model));
		this.buttonState(false);
		model.addPropertyChangeListener(this);
	}
	
	public void buttonState(boolean state) {
		this.setEnabled(state);
	}

	@Override
	public void propertyChange(PropertyChangeEvent evt) {
		if(evt.getPropertyName()==DiceAppModel.Events.REMOVE_PLAYER_ENABLE) {
			buttonState(true);
		}
		if(evt.getPropertyName()==DiceAppModel.Events.REMOVE_PLAYER_DISABLE) {
			buttonState(false);
		}
	}

}
