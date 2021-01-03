package view;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;

import javax.swing.JPanel;

@SuppressWarnings("serial")
public class SingleDieView extends JPanel {
	
	  private static final int FIX_ASPECT = 250;
	  private static final int SIZE = 35;
	  private Color color;
	  private int value;
	  
	  SingleDieView(int value) {
		  this.value = value;
		  color = Color.BLACK;
	       repaint();
	  }
	  @Override
	  public Dimension getPreferredSize() {
	    return new Dimension(FIX_ASPECT, FIX_ASPECT);
	  }
	  
	  @Override
	  public void paintComponent(Graphics g) {
	    super.paintComponent(g);
	    g.setColor(color);
	    switch (value) {
	    case 1:
	      g.fillRect(0, 0, FIX_ASPECT, FIX_ASPECT); 
		  g.setColor(Color.WHITE);
	      g.fillRect(3 * SIZE, 3 * SIZE, SIZE, SIZE);
	      break;
	    case 2:
	      g.fillRect(0, 0, FIX_ASPECT, FIX_ASPECT); 
		  g.setColor(Color.WHITE);
	      g.fillRect(5 * SIZE, SIZE, SIZE, SIZE);
	      g.fillRect(SIZE, 5 * SIZE, SIZE, SIZE);
	      break;
	    case 3:
	      g.fillRect(0, 0, FIX_ASPECT, FIX_ASPECT); 
	      g.setColor(Color.WHITE);
	      g.fillRect(5*SIZE, SIZE, SIZE, SIZE);
	      g.fillRect(SIZE, 5 * SIZE, SIZE, SIZE);
	      g.fillRect(3 * SIZE, 3 * SIZE, SIZE, SIZE);
	      break;
	    case 4:
	      g.fillRect(0, 0, FIX_ASPECT, FIX_ASPECT); 
		  g.setColor(Color.WHITE);
	      g.fillRect(SIZE, SIZE, SIZE, SIZE);
	      g.fillRect(5 * SIZE, 5 * SIZE, SIZE, SIZE);
	      g.fillRect(5 * SIZE, SIZE, SIZE, SIZE);
	      g.fillRect(SIZE, 5 * SIZE, SIZE, SIZE);
	      break;
	    case 5:
	      g.fillRect(0, 0, FIX_ASPECT, FIX_ASPECT); 
		  g.setColor(Color.WHITE);
	      g.fillRect(SIZE, SIZE, SIZE, SIZE);
	      g.fillRect(5 * SIZE, 5 * SIZE, SIZE, SIZE);
	      g.fillRect(5 * SIZE, SIZE, SIZE, SIZE);
	      g.fillRect(SIZE, 5 * SIZE, SIZE, SIZE);
	      g.fillRect(3 * SIZE, 3 * SIZE, SIZE, SIZE);
	      break;
	    case 6:
	      g.fillRect(0, 0, FIX_ASPECT, FIX_ASPECT); 
		  g.setColor(Color.WHITE);
	      g.fillRect(SIZE, SIZE, SIZE, SIZE);
	      g.fillRect(5 * SIZE, 5 * SIZE, SIZE, SIZE);
	      g.fillRect(5 * SIZE, SIZE, SIZE, SIZE);
	      g.fillRect(SIZE, 5 * SIZE, SIZE, SIZE);
	      g.fillRect(SIZE, 3 * SIZE, SIZE, SIZE);
	      g.fillRect(5 * SIZE, 3 * SIZE, SIZE, SIZE);
	      break;
	    }
	  }
	}