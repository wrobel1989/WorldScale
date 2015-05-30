package testjavafx;


import java.awt.Color;
import java.awt.Graphics;
import java.text.DecimalFormat;
import java.text.NumberFormat;

import javax.swing.JPanel;


public class UniverseCanvas extends JPanel implements Runnable{

	
	private int size_X;
	private int size_Y;
	private int APPX, APPY;
	private boolean running = true;
	private ObjectManager objMng;
	private double currentexp_sizeX;
	
	//private String []descScale = {"Skala XX me",""};
	
	private double expMin = -35;
	private double expMax = 27;
	
	public double getexpMin(){return expMin;}
	public double getexpMax(){return expMax;}
	public double getCurrentexpSize(){return this.currentexp_sizeX;}
	public synchronized void setCurrentScale(double size_exponent_of10){
		this.currentexp_sizeX = size_exponent_of10;
	}
	
	public UniverseCanvas(int sizeX, int sizeY, int APPX, int APPY){
		this.size_X = sizeX;
		this.size_Y = sizeY;
		this.APPX = APPX;
		this.APPY = APPY;
		super.setBounds(0,0,size_X,size_Y);
		//this.setBackground(Color.black);
		this.objMng = new ObjectManager();
		this.objMng.setCanvasinfo(sizeX,sizeY);
		this.currentexp_sizeX = 7.5;//sqrt(10) m
		Thread myThr = new Thread(this);
		myThr.start();
	}
	
    @Override
    public void paintComponent(Graphics g) {
    	super.paintComponent(g);
    	g.setColor(Color.BLACK);
    	
    	double intensity = 255.0 - clamp((255.0*(this.currentexp_sizeX - 5.0)),0.0,255.0);
    	System.out.println(intensity);
    	int col = ((int) (intensity)) + ((int)(0.9*intensity)) *256 + ((int)(0.8*intensity)) *256 * 256;
    	
    	g.setColor(new Color(col));
    	g.clearRect(0, 0, size_X, size_Y);
    	g.fillRect(0, 0, size_X, size_Y);
    	g.setColor(Color.BLUE);
        //g.drawOval(0, 0, this.size_X, this.size_Y);
    	g.setClip(0, 0, size_X, size_Y);
        this.drawTunel(g,this.currentexp_sizeX);
        //g.setClip(0, 0, 950, 550);
        this.objMng.drawScene(g,this.currentexp_sizeX);
        //g.setClip(0, 0, 950, 550);
        //g.drawString("BLAH", 20, 20);
        //g.drawRect(200, 200, 200, 200);
        g.setColor(new Color(0x007700));
        //g.drawLine(0,0, 500,500);
        double fracb = 0.35;
        double frace = 1-fracb;
        g.drawLine(((int)(fracb*size_X)),size_Y-35,((int)(frace*size_X)),size_Y-35);
        NumberFormat formatter = new DecimalFormat("0.##E0");
        g.drawString(formatter.format(Math.pow(10, this.currentexp_sizeX)*(frace-fracb))+" m", this.size_X / 2 - 20, this.size_Y - 20);
        g.setClip(0,0,this.APPX,this.APPY);
    }

    private synchronized void makerun(){
    	this.repaint();
    }
    
    
	@Override
	public void run() { //thread entry
		while(this.running){
			this.makerun();
			try {
				//System.out.println(this.currentexp_sizeX);
				Thread.sleep(5);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		
	}
	
	private void drawTunel(Graphics g, double expx){
		int Nexp = (int) expx;
		for (int i=Nexp-3; i<= Nexp+1;i++){
			int width  =(int) ((double)size_X * (Math.pow(10, i)/Math.pow(10, expx)));
			int height =(int) ((double)size_Y * (Math.pow(10, i)/Math.pow(10, expx)));
			g.drawRect(size_X/2 - width/2, size_Y/2 - height/2, width, height);
			
			g.drawLine(size_X/2 -(int) ((double)size_X * (Math.pow(10, i)/Math.pow(10, expx)))/2,
					size_Y/2 -(int) ((double)size_Y * (Math.pow(10, i)/Math.pow(10, expx)))/2,
					size_X/2 -(int) ((double)size_X * (Math.pow(10, i+1)/Math.pow(10, expx)))/2,
					size_Y/2 -(int) ((double)size_Y * (Math.pow(10, i+1)/Math.pow(10, expx)))/2);
			g.drawLine(size_X/2 +(int) ((double)size_X * (Math.pow(10, i)/Math.pow(10, expx)))/2,
					size_Y/2 -(int) ((double)size_Y * (Math.pow(10, i)/Math.pow(10, expx)))/2,
					size_X/2 +(int) ((double)size_X * (Math.pow(10, i+1)/Math.pow(10, expx)))/2,
					size_Y/2 -(int) ((double)size_Y * (Math.pow(10, i+1)/Math.pow(10, expx)))/2);
			g.drawLine(size_X/2 +(int) ((double)size_X * (Math.pow(10, i)/Math.pow(10, expx)))/2,
					size_Y/2 +(int) ((double)size_Y * (Math.pow(10, i)/Math.pow(10, expx)))/2,
					size_X/2 +(int) ((double)size_X * (Math.pow(10, i+1)/Math.pow(10, expx)))/2,
					size_Y/2 +(int) ((double)size_Y * (Math.pow(10, i+1)/Math.pow(10, expx)))/2);
			g.drawLine(size_X/2 -(int) ((double)size_X * (Math.pow(10, i)/Math.pow(10, expx)))/2,
					size_Y/2 +(int) ((double)size_Y * (Math.pow(10, i)/Math.pow(10, expx)))/2,
					size_X/2 -(int) ((double)size_X * (Math.pow(10, i+1)/Math.pow(10, expx)))/2,
					size_Y/2 +(int) ((double)size_Y * (Math.pow(10, i+1)/Math.pow(10, expx)))/2);
		}
	}
	
	private final double clamp(double value, double BeginRange, double EndRange){//if value outside range, then ends up at some of the range value
		if(value > EndRange)
			return EndRange;
		if(value < BeginRange)
			return BeginRange;
		return value;
	}
	
}
