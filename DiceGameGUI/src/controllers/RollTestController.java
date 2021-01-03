package controllers;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import model.DiceAppModel;

public class RollTestController implements ActionListener {

	private DiceAppModel model;
	
	public RollTestController(DiceAppModel model) {
		super();
		this.model = model;
	}
	
	@Override
	public void actionPerformed(ActionEvent arg0) {
		model.rollDieTest();
	}

}
