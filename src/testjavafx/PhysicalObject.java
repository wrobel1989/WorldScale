package testjavafx;

import java.awt.Graphics;
import java.awt.Image;
import java.awt.image.BufferedImage;

public class PhysicalObject {

	private int CanX,CanY;
	
	private Image img;
	private double realSizeX;
	private double realSizeY;
	private String Descr;
	private vec2d realPositionofTheCenter;
	
	public PhysicalObject(BufferedImage img, double rs, String dsc, vec2d rpc){
		this.img = img;
		this.realSizeX = rs;
		this.realSizeY = rs * (((double)img.getHeight())/((double)img.getWidth()));
		this.realPositionofTheCenter = new vec2d(rpc.vx,rpc.vy);
		this.Descr = dsc;
	}

	//canvas center is at physical (0,0) - focal point
	public void drawMe(Graphics g, double expscale){
		//BufferedImage dest = src.getSubimage(0, 0, rect.width, rect.height);
		if (this.shouldTryToDrawThisImage(expscale)){
			long imgcenterX = (long)((this.CanX / 2) * (1.0 + (this.realPositionofTheCenter.vx)/(0.5*Math.pow(10, expscale)))); 
			long imgcenterY = (long)((this.CanY / 2) * (1.0 + (this.realPositionofTheCenter.vy)/(((((double)CanY)/((double)CanX)))*0.5*Math.pow(10, expscale))));
			
			int imx,imy,imwidth,imheight;//parmeters of the image on the screen after rescaling
			imwidth = (int) ((this.realSizeX/Math.pow(10, expscale))*img.getWidth(null)) ;
			imheight = (int) ((this.realSizeX/Math.pow(10, expscale))*img.getHeight(null)) ;
			imx = (int) (imgcenterX-imwidth/2.0);
			imy = (int) (imgcenterY-imheight/2.0);
			
			g.drawImage(this.img, imx, imy, imwidth ,imheight , null);
		}
	}
	
	public void setCanvasinfo(int CanX,int CanY){
		this.CanX = CanX;
		this.CanY = CanY;
	}
	
	private boolean shouldTryToDrawThisImage(double expscale){
		return (Math.pow(10, expscale)/this.realPositionofTheCenter.len() < 1e5 &&
				Math.pow(10, expscale)/this.realPositionofTheCenter.len() > 1e-5);
	}
	
}










class vec2d {//pomocnicza klasa do dzialan na wektorach
	public double vx, vy;

	vec2d(double x, double y) {
		this.vx = x;
		this.vy = y;
	}

	void print() {
		System.out.println("X: " + vx + " Y: " + vy);
	}

	void set(vec2d v) {
		this.vx = v.vx;
		this.vy = v.vy;
	}

	void add(vec2d v) {
		this.vx += v.vx;
		this.vy += v.vy;
	}

	void sub(vec2d v) {
		this.vx -= v.vx;
		this.vy -= v.vy;
	}

	void mult(double constant) {
		this.vx *= constant;
		this.vy *= constant;
	}

	double len() {//dlugosc wektora
		return Math.sqrt(this.vx * this.vx + this.vy * this.vy);
	}

	void normalize() {//normalizacja
		double length = this.len();
		this.vx *= (1.0 / length);
		this.vy *= (1.0 / length);
	}

	double scalarproduct(vec2d v) {//iloczyn skalarny
		return this.vx * v.vx + this.vy * v.vy;
	}
}
