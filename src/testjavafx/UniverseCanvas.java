package testjavafx;


import java.awt.Color;
import java.awt.Graphics;

import javax.swing.JPanel;


public class UniverseCanvas extends JPanel implements Runnable{

	
	private int size_X;
	private int size_Y;
	private boolean running = true;
	private ObjectManager objMng;
	
	public UniverseCanvas(int sizeX, int sizeY){
		this.size_X = sizeX;
		this.size_Y = sizeY;
		super.setBounds(0,0,size_X,size_Y);
		//this.setBackground(Color.black);
		this.objMng = new ObjectManager();
	}
	
    @Override
    public void paintComponent(Graphics g) {
    	super.paintComponent(g);
    	g.setColor(Color.BLACK);
    	g.clearRect(0, 0, size_X, size_Y);
    	g.fillRect(0, 0, size_X, size_Y);
    	g.setColor(Color.RED);
        g.drawOval(0, 0, this.size_X, this.size_Y);

        //g.drawString("BLAH", 20, 20);
        //g.drawRect(200, 200, 200, 200);
    }

	@Override
	public synchronized void run() { //wejscie do watku
		while(this.running){
			try {
				Thread.sleep(3);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		
	}
	
}
