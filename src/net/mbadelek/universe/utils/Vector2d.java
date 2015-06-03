package net.mbadelek.universe.utils;

public class Vector2d {// pomocnicza klasa do dzialan na wektorach

	public double x, y;

	public Vector2d(double x, double y) {
		this.x = x;
		this.y = y;
	}

	public void set(Vector2d v) {
		this.x = v.x;
		this.y = v.y;
	}

	public void add(Vector2d v) {
		this.x += v.x;
		this.y += v.y;
	}

	public void subtract(Vector2d v) {
		this.x -= v.x;
		this.y -= v.y;
	}

	public void multiply(double constant) {
		this.x *= constant;
		this.y *= constant;
	}

	public double length() {// dlugosc wektora
		return Math.sqrt(this.x * this.x + this.y * this.y);
	}

	public void normalize() {// normalizacja
		double length = this.length();
		this.x *= (1.0 / length);
		this.y *= (1.0 / length);
	}

	public double scalarProduct(Vector2d v) {// iloczyn skalarny
		return this.x * v.x + this.y * v.y;
	}
	
	@Override
	public String toString() {
		return "[" + x + ", " + y + "]";
	}
}
