package client;

import javax.swing.SwingUtilities;

import model.DiceAppModel;
import view.DiceAppFrame;

public class DiceAppClient {

	public static void main(String[] args) {
		DiceAppModel model = new DiceAppModel();
		
		SwingUtilities.invokeLater(new Runnable()
		{
			@Override
			public void run()
			{
				new DiceAppFrame(model);
			}
		});
		

	}

}
