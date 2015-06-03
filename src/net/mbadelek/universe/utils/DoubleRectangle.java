package net.mbadelek.universe.utils;

public class DoubleRectangle {

	private double x, y, width, height;

	public DoubleRectangle(double x, double y, double w, double h) {
		this.x = x;
		this.y = y;
		this.height = h;
		this.width = w;
	}

	public double getX() {
		return x;
	}

	public double getY() {
		return y;
	}

	public double getWidth() {
		return width;
	}

	public double getHeight() {
		return height;
	}

	public boolean intersects(DoubleRectangle rectangle) {
		boolean intersect = true;
		if (((rectangle.x < x && rectangle.x + rectangle.width < x) || (rectangle.x > x + width && rectangle.x + rectangle.width > x + width))
				|| ((rectangle.y < y && rectangle.y + rectangle.height < y) || (rectangle.y > y + height && rectangle.y + rectangle.height > y + height)))
			intersect = false;
		return intersect;
	}
}
