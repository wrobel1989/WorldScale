package testjavafx;


import java.awt.Color;
import java.awt.Graphics;

import javax.swing.JPanel;


public class UniverseCanvas extends JPanel implements Runnable{

	
	private int size_X;
	private int size_Y;
	private boolean running = true;
	private ObjectManager objMng;
	private double currentexp_sizeX;
	
	private double expMin = -35;
	private double expMax = 27;
	
	public double getexpMin(){return expMin;}
	public double getexpMax(){return expMax;}
	public double getCurrentexpSize(){return this.currentexp_sizeX;}
	public synchronized void setCurrentScale(double size_exponent_of10){
		this.currentexp_sizeX = size_exponent_of10;
	}
	
	public UniverseCanvas(int sizeX, int sizeY){
		this.size_X = sizeX;
		this.size_Y = sizeY;
		super.setBounds(0,0,size_X,size_Y);
		//this.setBackground(Color.black);
		this.objMng = new ObjectManager();
		this.objMng.setCanvasinfo(sizeX,sizeY);
		this.currentexp_sizeX = 0.5;//sqrt(10) m
		Thread myThr = new Thread(this);
		myThr.start();
	}
	
    @Override
    public void paintComponent(Graphics g) {
    	super.paintComponent(g);
    	g.setColor(Color.BLACK);
    	g.clearRect(0, 0, size_X, size_Y);
    	g.fillRect(0, 0, size_X, size_Y);
    	g.setColor(Color.RED);
        g.drawOval(0, 0, this.size_X, this.size_Y);
        //g.setClip(0, 0, 950, 550);
        this.objMng.drawScene(g,this.currentexp_sizeX);
        //g.setClip(0, 0, 950, 550);
        //g.drawString("BLAH", 20, 20);
        //g.drawRect(200, 200, 200, 200);
    }

    private synchronized void makerun(){
    	this.repaint();
    }
    
    
	@Override
	public void run() { //thread entry
		while(this.running){
			this.makerun();
			try {
				System.out.println(this.currentexp_sizeX);
				Thread.sleep(5);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		
	}
	
}
