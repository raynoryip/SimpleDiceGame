package controllers;

import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import javax.swing.JComboBox;
import model.DiceAppModel;

public class ComboBoxController implements ItemListener {

	private DiceAppModel model;
	private String selectedItem;
	private JComboBox box;
	
	public ComboBoxController(DiceAppModel model, JComboBox box) {
		this.model = model;
		this.box = box;
	}
	
	@Override
	public void itemStateChanged(ItemEvent e) {
		this.selectedItem = (String)box.getSelectedItem();
		if(this.selectedItem!=null) {
			model.UpdateAllInfo(this.selectedItem);
		}
	}

}
