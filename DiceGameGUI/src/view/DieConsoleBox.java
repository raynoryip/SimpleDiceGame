package view;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.TextArea;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

import javax.swing.JScrollPane;

import model.DiceAppModel;

@SuppressWarnings("serial")
public class DieConsoleBox extends TextArea implements PropertyChangeListener {
	
	public DiceAppModel model;
	
	public DieConsoleBox(DiceAppModel model) {
		this.model = model;
		this.setSize(0, 50);
		this.setBackground(Color.WHITE);
		JScrollPane areaScrollPane = new JScrollPane(this);
		this.setEditable(false);
		 areaScrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
	     areaScrollPane.setPreferredSize(new Dimension(5, 5));
	     
	     this.model.addPropertyChangeListener(this);
	}
	
	public void addText(String text) {
		this.append(text+"\n");
	}

	@Override
	public void propertyChange(PropertyChangeEvent evt) {
		if(evt.getPropertyName()==DiceAppModel.Events.DIE_CONSOLE_UPDATE || 
				evt.getPropertyName()==DiceAppModel.Events.CONSOLE_CHANGE) {
			addText((String)evt.getNewValue());
		}	
	}

}
