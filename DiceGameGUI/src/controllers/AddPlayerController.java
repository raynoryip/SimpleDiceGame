package controllers;

import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

import model.DiceAppModel;

public class AddPlayerController implements ActionListener {
	
	private DiceAppModel model;

	public AddPlayerController(DiceAppModel model) {
		this.model = model;
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		
		 JTextField playerNameField = new JTextField(5);
	     JTextField playerPointsField = new JTextField(5);

	      JPanel myPanel = new JPanel();
	      myPanel.setLayout(new GridLayout(0,1));
	      myPanel.add(new JLabel("New Player's Name"));
	      myPanel.add(playerNameField);
	      myPanel.add(new JLabel("New Player's Initial Points"));
	      myPanel.add(playerPointsField);

	      int result = JOptionPane.showConfirmDialog(null, myPanel, 
	               "Please Enter Players Name and their initial Point", JOptionPane.OK_CANCEL_OPTION);
	      if (result == JOptionPane.OK_OPTION) {
	    	  String playerName = playerNameField.getText();
	    	  String playerPoints = playerPointsField.getText();
	    	  if(playerName.isEmpty()) {
	    		  JOptionPane.showMessageDialog(null, "Your name shouldn't be empty");
	    	  }else if(!playerPoints.matches("[0-9]+") && playerPoints.length() < 1) {
	    		  JOptionPane.showMessageDialog(null, "You should input a proper Number");
	    	  }else {
	    		  int points = Integer.parseInt(playerPoints);
	    		  if(points < 100) {
	    			  JOptionPane.showMessageDialog(null, "Points should be at least greater than 100");
	    		  }else {
	    			  model.addPlayer(playerName, points);
	    		  }
	    	  }
	      }

	}

}
