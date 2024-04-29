package windowInterface;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;

import javax.swing.JPanel;

import backend.Agent;
import backend.Simulator;

public class JPanelDraw extends JPanel {

	private static final long serialVersionUID = 1L;
	private Simulator mySimu;
	private MyInterface interfaceGlobal;

	public JPanelDraw(MyInterface itf) {
		super();
		mySimu = null;
		interfaceGlobal = itf;
		addMouseListener(new MouseAdapter() { 
	          public void mousePressed(MouseEvent me) { 
	            // System.out.println(me);
	            if(mySimu == null) {
	            	interfaceGlobal.instantiateSimu();
	            }
	            int x = (me.getX()*mySimu.getWidth())/getWidth();
	            int y = (me.getY()*mySimu.getHeight())/getHeight();
            	mySimu.clickCell(x,y);
            	repaint();
	          } 
	        }); 
	}
	
	public void setSimu(Simulator simu) {
		mySimu = simu;
	}

	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		this.setBackground(Color.black);
		if (mySimu != null) {
			// Draw Interface from state of simulator
			float cellWidth = (float)this.getWidth()/(float)mySimu.getWidth();
			float cellHeight = (float)this.getHeight()/(float)mySimu.getHeight();
			g.setColor(Color.gray);
			for(int x=0; x<mySimu.getWidth();x++) {
				int graphX = Math.round(x*cellWidth);
				g.drawLine(graphX, 0, graphX, this.getHeight());
			}
			for (int y=0; y<mySimu.getHeight(); y++) {
				int graphY = Math.round(y*cellHeight);
				g.drawLine(0, graphY, this.getWidth(), graphY);
			}
			for(int x=0; x<mySimu.getWidth();x++) {
				for (int y=0; y<mySimu.getHeight(); y++) {
					int cellContent = mySimu.getCell(x,y);
					if(cellContent == -1) {
						g.setColor(Color.gray);
					}
					if(cellContent == 0) {
						continue;
					}
					if(cellContent == 1) {
						g.setColor(Color.white);
					}
					if(cellContent == 2) {
						g.setColor(Color.yellow);
					}
					if(cellContent == 3) {
						g.setColor(Color.red);
					}
					g.fillRect(
							(int) Math.round(x*cellWidth),
							(int) Math.round(y*cellHeight), 
							(int) Math.round(cellWidth),
							(int) Math.round(cellHeight)
							);
				}
			}
			for (Agent animal:mySimu.getAnimals()){
				g.setColor(animal.getDisplayColor());
				g.fillOval((int)Math.round(animal.getX()*cellWidth), 
						(int)Math.round(animal.getY()*cellHeight), 
						(int)Math.round(cellWidth/2), 
						(int)Math.round(cellHeight/2));
			}
		}
	}


}
