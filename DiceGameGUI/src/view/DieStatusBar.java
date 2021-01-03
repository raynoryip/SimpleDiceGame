package view;

import java.awt.Color;
import java.awt.GridLayout;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.Border;

import model.DiceAppModel;

@SuppressWarnings("serial")
public class DieStatusBar extends JPanel implements PropertyChangeListener {
	
	private DiceAppModel model;
	private JLabel die1State;
	private JLabel die2State;
	
	public DieStatusBar(DiceAppModel model) {
		this.model = model;
		setLayout(new GridLayout(0,1));
		Border border = BorderFactory.createLineBorder(Color.BLACK, 1);
		die1State = new JLabel();
		die1State.setBorder(border);
		die2State = new JLabel();
		die2State.setBorder(border);
		add(die1State);
		add(die2State);
		
		this.model.addPropertyChangeListener(this);
		updateDie1Label(model.getDie1().getValue());
		updateDie2Label(model.getDie2().getValue());
		
	}
	
	public void updateDie1Label(int dieNum) {
		die1State.setText("Die 1 = " + dieNum);
	}
	
	public void updateDie2Label(int dieNum) {
		die2State.setText("Die 2 = " + dieNum);
	}
	
	@Override
	public void propertyChange(PropertyChangeEvent evt) {
		
		if(evt.getPropertyName()==DiceAppModel.Events.DIE_1_STATUS_CHANGE) {
			 updateDie1Label((int)evt.getNewValue());
		}
		if(evt.getPropertyName()==DiceAppModel.Events.DIE_2_STATUS_CHANGE) {
			 updateDie2Label((int)evt.getNewValue());
		}
		
	}
	
	
	
}
