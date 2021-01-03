package view;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

import javax.swing.JComboBox;

import controllers.ComboBoxController;
import model.DiceAppModel;

@SuppressWarnings("serial")
public class PlayerComboBox extends JComboBox<String> implements PropertyChangeListener {

	private DiceAppModel model;
	
	public PlayerComboBox(DiceAppModel model) {
		this.model = model;
		display();
		this.addItemListener(new ComboBoxController(model, this));
		model.addPropertyChangeListener(this);
		
	}
	
	public void display() {
		this.removeAllItems();
		Object[] listPlayers = model.getPlayersName();
		for(Object o: listPlayers) {
			this.addItem((String) o);
		}
	}
	
	@Override
	public void propertyChange(PropertyChangeEvent evt) {
		if(evt.getPropertyName()==DiceAppModel.Events.COMBO_BOX_UPDATE) {
			display();
		}
		
	}

	
	
}
