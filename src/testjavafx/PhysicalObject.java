package testjavafx;

import java.awt.Image;
import java.awt.image.BufferedImage;



public class PhysicalObject {

	private Image img;
	private double realSizeX;
	private String Descr;
	private vec2d realPositionofTheCenter;
	
	public PhysicalObject(BufferedImage img, double rs, String dsc, vec2d rpc){
		this.img = img;
		this.realSizeX = rs;
		this.realPositionofTheCenter = new vec2d(rpc.vx,rpc.vy);
		this.Descr = dsc;
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
