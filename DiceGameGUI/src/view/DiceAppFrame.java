package view;

import java.awt.BorderLayout;
import java.awt.Dimension;

import javax.swing.JFrame;
import model.DiceAppModel;

@SuppressWarnings("serial")
public class DiceAppFrame extends JFrame {
	
	public DiceAppFrame(DiceAppModel model) {
		
		super("Dice Game App");
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setLayout(new BorderLayout());
		setPreferredSize(new Dimension(900, 600));
		
		add(new DieToolBar(model), BorderLayout.NORTH);
		add(new DiePanel(model), BorderLayout.CENTER);
		add(new SummaryBox(model), BorderLayout.EAST);
		add(new DieStatusBar(model), BorderLayout.WEST);
		add(new DieConsoleBox(model), BorderLayout.SOUTH);
		
		pack();
		setVisible(true);
		
	}
}
