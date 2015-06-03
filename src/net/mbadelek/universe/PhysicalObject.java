//repo https://github.com/wrobel1989/WorldScale.git

package net.mbadelek.universe;

import java.awt.Graphics;
import java.awt.image.BufferedImage;

import net.mbadelek.universe.utils.Vector2d;
import net.mbadelek.universe.utils.DoubleRectangle;

public class PhysicalObject {

	private int canvasWidth, canvasHeight;// ov drawing canvas in pixels

	private BufferedImage image;

	private double realSizeX;// of image in meters
	private double realSizeY;
	private String name;
	private Vector2d realCenterPosition;

	public PhysicalObject(BufferedImage img, double rs, String dsc, Vector2d rpc) {
		this.image = img;
		this.realSizeX = rs;
		this.realSizeY = rs * (((double) img.getHeight()) / ((double) img.getWidth()));
		this.realCenterPosition = new Vector2d(rpc.x, rpc.y);
		this.name = dsc;
	}

	public String getName() {
		return this.name;
	}

	public DoubleRectangle getRealPos() {
		DoubleRectangle p = new DoubleRectangle(this.realCenterPosition.x - this.realSizeX / 2, this.realCenterPosition.y - this.realSizeY / 2,
				this.realSizeX, this.realSizeY);
		return p;
	}

	public void setRealPos(Vector2d pos) {
		this.realCenterPosition = new Vector2d(pos.x, pos.y);
	}

	// canvas center is at physical (0,0) - focal point
	public void draw(Graphics g, double expscale) {// /10^expscale = canvas_x in meters
		if (this.shouldTryToDrawThisImage(expscale)) {
			long imageCenterX = (long) ((this.canvasWidth / 2) * (1.0 + (this.realCenterPosition.x) / (0.5 * Math.pow(10, expscale))));
			long imageCenterY = (long) ((this.canvasHeight / 2) * (1.0 + (this.realCenterPosition.y) / (((((double) canvasHeight) / ((double) canvasWidth))) * 0.5 * Math.pow(10, expscale))));

			int imx, imy, imwidth, imheight;// parmeters of the image on the
											// screen after rescaling
			imwidth = (int) ((this.realSizeX / Math.pow(10, expscale)) * (canvasWidth));
			imheight = (int) (imwidth * (((double) image.getHeight(null)) / ((double) (image.getWidth(null)))));
			imx = (int) (imageCenterX - imwidth / 2.0);
			imy = (int) (imageCenterY - imheight / 2.0);
			
			g.setClip(0, 0, canvasWidth, canvasHeight);
			g.drawImage(image, imx, imy, imwidth, imheight, null);
		}
	}

	public void setCanvasInfo(int CanX, int CanY) {
		this.canvasWidth = CanX;
		this.canvasHeight = CanY;
	}

	private boolean shouldTryToDrawThisImage(double expscale) {
		return (Math.pow(10, expscale) / this.realCenterPosition.length() < 1e3 && Math.pow(10, expscale) / this.realCenterPosition.length() > 1e-3);
	}

	public boolean isXYWithinObject(double XX, double YY) {
		boolean is = false;
		if (XX > this.realCenterPosition.x - this.realSizeX / 2.0 && XX < this.realCenterPosition.x + this.realSizeX / 2.0
				&& YY > this.realCenterPosition.y - this.realSizeY / 2.0 && YY < this.realCenterPosition.y + this.realSizeY / 2.0)
			is = true;
		return is;
	}

	public double getYXratio() {
		return ((double) this.image.getHeight()) / ((double) this.image.getWidth());
	}
}