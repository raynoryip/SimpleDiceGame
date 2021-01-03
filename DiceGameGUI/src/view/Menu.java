package view;

import javax.swing.JMenu;
import javax.swing.JMenuBar;

import model.DiceAppModel;

@SuppressWarnings("serial")
public class Menu extends JMenuBar{

	private JMenu menu;
	
	public Menu(DiceAppModel model) {
		menu = new JMenu("Menu");
		add(menu);
		menu.add(new MenuAddPlayer(model));
		menu.add(new MenuRemovePlayer(model));
		menu.add(new MenuAddBet(model));
		menu.add(new MenuRemoveBet(model));
	}
	
}
