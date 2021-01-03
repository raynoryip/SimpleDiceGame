package view;

import java.awt.Color;
import java.awt.Dimension;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

import model.DiceAppModel;
import model.DiceAppModel.Events;
import model.interfaces.Player;

@SuppressWarnings("serial")
public class SummaryBox extends JPanel implements PropertyChangeListener {
	
	private JLabel label;
	private JLabel nameLabel;
	private JLabel idLabel;
	private JLabel pointLabel;
	private JLabel betLabel;
	
	
	public SummaryBox(DiceAppModel model) {
		setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		this.setPreferredSize(new Dimension(200, 200));
		setBackground(Color.WHITE);
		model.addPropertyChangeListener(this);
		defaultPanel();
	}
	
	public void defaultPanel() {
		this.removeAll();
		this.revalidate();
		this.repaint();
		label = new JLabel("Players Summary",SwingConstants. CENTER);
		JLabel space = new JLabel(" ");
		add(label);
		add(space);
	}
	
	public void updatePanel(Player player) {
		defaultPanel();
		nameLabel = new JLabel("Player Name->" + player.getPlayerName());
		idLabel = new JLabel("Player ID->" + player.getPlayerId());
		pointLabel = new JLabel("Player Point->" + player.getPoints());
		betLabel = new JLabel("Placed Bet->" + player.getBet());
		this.add(nameLabel);
		this.add(idLabel);
		this.add(pointLabel);
		this.add(betLabel);
	}
	
	@Override
	public void propertyChange(PropertyChangeEvent evt) {
		if(evt.getPropertyName()==Events.UPDATE_SUMMARY_PANEL) {
			updatePanel((Player)evt.getNewValue());
		}
		if(evt.getPropertyName()==Events.CLEAR_SUMMARY_PANEL) {
			defaultPanel();
		}
	}
}
