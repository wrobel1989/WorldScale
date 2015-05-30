//repo https://github.com/wrobel1989/WorldScale.git

package testjavafx;

import java.awt.Graphics;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;

public class PhysicalObject {

	private int CanX,CanY;//ov drawing canvas in pixels
	
	private BufferedImage img;
	
	private double realSizeX;//of image in meters
	private double realSizeY;
	private String Descr;
	private vec2d realPositionofTheCenter;
	
	public String getDescr(){
		return this.Descr;
	}
	
	public doubleRect getRealPos(){
		doubleRect p = new doubleRect(this.realPositionofTheCenter.vx-this.realSizeX/2,this.realPositionofTheCenter.vy-this.realSizeY/2,
				this.realSizeX,this.realSizeY);
		return p;
	}
	
	public PhysicalObject(BufferedImage img, double rs, String dsc, vec2d rpc){
		this.img = img;
		this.realSizeX = rs;
		this.realSizeY = rs * (((double)img.getHeight())/((double)img.getWidth()));
		this.realPositionofTheCenter = new vec2d(rpc.vx,rpc.vy);
		this.Descr = dsc;
	}

	public void setRealPos(vec2d pos){
		this.realPositionofTheCenter = new vec2d(pos.vx,pos.vy);
	}
	
	//canvas center is at physical (0,0) - focal point
	public void drawMe(Graphics g, double expscale){///10^expscale = canvas_x in meters
		if (this.shouldTryToDrawThisImage(expscale)){
			long imgcenterX = (long)((this.CanX / 2) * (1.0 + (this.realPositionofTheCenter.vx)/(0.5*Math.pow(10, expscale)))); 
			long imgcenterY = (long)((this.CanY / 2) * (1.0 + (this.realPositionofTheCenter.vy)/(((((double)CanY)/((double)CanX)))*0.5*Math.pow(10, expscale))));
			
			int imx,imy,imwidth,imheight;//parmeters of the image on the screen after rescaling
			imwidth = (int) ((this.realSizeX/Math.pow(10, expscale))*(CanX)) ;
			imheight = (int)(imwidth * (((double)img.getHeight(null))/((double)(img.getWidth(null)))));
			imx = (int) (imgcenterX-imwidth/2.0);
			imy = (int) (imgcenterY-imheight/2.0);
			
			//Rectangle canv = new Rectangle(0,0,CanX,CanY);
			//Rectangle rawImgBounds = new Rectangle(imx, imy, imwidth, imheight);
			//Rectangle croppedImgBounds = canv.intersection(rawImgBounds);
			//int crX = (int)croppedImgBounds.getX();
			//int crY = (int)croppedImgBounds.getY();
			//int crWidth = (int)croppedImgBounds.getWidth();
			//int crHeight = (int)croppedImgBounds.getHeight();
			//int croppedx = (int)clamp(croppedImgBounds.getX(),);
			//target = img.getSubimage(crX,crY,crWidth,crHeight);
			g.setClip(0, 0, CanX, CanY);
			g.drawImage(img, imx, imy, imwidth ,imheight , null);
			//g.setClip(0, 0, 2222, 2222);
		}
	}
	
	public void setCanvasinfo(int CanX,int CanY){
		this.CanX = CanX;
		this.CanY = CanY;
	}
	
	private boolean shouldTryToDrawThisImage(double expscale){
		return (Math.pow(10, expscale)/this.realPositionofTheCenter.len() < 1e3 &&
				Math.pow(10, expscale)/this.realPositionofTheCenter.len() > 1e-3);
	}


	
	
	public double getYXratio(){
		return ((double)this.img.getHeight()) / ((double)this.img.getWidth());
	}
}




class doubleRect{
	
	public double x,y,w,h;
	doubleRect(double x, double y, double w, double h){
		this.x=x;
		this.y=y;
		this.h=h;
		this.w=w;
	}
	
	public boolean doesintersect(doubleRect nre){
		boolean intersect = true;
		if (((nre.x<x && nre.x+nre.w < x) || (nre.x>x+w && nre.x+nre.w > x+w)) || 
				((nre.y<y && nre.y+nre.h < y) || (nre.y>y+h && nre.y+nre.h > y+h)) )
			intersect = false;
		return intersect;
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
