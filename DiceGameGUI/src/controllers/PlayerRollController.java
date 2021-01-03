package controllers;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import model.DiceAppModel;

public class PlayerRollController implements ActionListener {

	private DiceAppModel model;
	
	public PlayerRollController(DiceAppModel model) {
		this.model = model;
	}
	
	@Override
	public void actionPerformed(ActionEvent arg0) {
			model.rollDie();

	}

}
