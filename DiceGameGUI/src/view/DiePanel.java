package view;

import java.awt.GridLayout;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

import javax.swing.BorderFactory;
import javax.swing.JComponent;
import javax.swing.JPanel;

import model.DiceAppModel;

@SuppressWarnings("serial")
public class DiePanel extends JPanel implements PropertyChangeListener  {
	
	private DiceAppModel model;
	private JPanel die1View;
	private JPanel die2View;
	
	public DiePanel(DiceAppModel model) {
		this.model = model;
		setLayout(new GridLayout(1,0));
		setBorder(BorderFactory.createEmptyBorder(50, 50, 50, 50));
		updateDie();
		this.model.addPropertyChangeListener(this);
	}
	
	public void updateDie() {
		//Remove the existing panel and repaints them
		
		revalidate();
		repaint();
		
		if(die1View!=null) {
			this.remove(die1View);
			this.remove(die2View);
		}
		die1View = new SingleDieView(this.model.getDie1().getValue());
		die1View.setAlignmentX(JComponent.CENTER_ALIGNMENT);
		
		die2View = new SingleDieView(this.model.getDie2().getValue());
		die2View.setAlignmentX(JComponent.CENTER_ALIGNMENT);
		
		add(die1View);
		add(die2View);
		
	}
	
	@Override
	public void propertyChange(PropertyChangeEvent evt) {
		if(evt.getPropertyName()==DiceAppModel.Events.DIE_VAL_CHANGE) {
			this.updateDie();
		}
		
	}

}
