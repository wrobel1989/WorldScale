package net.mbadelek.universe;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.text.DecimalFormat;
import java.text.NumberFormat;

import javax.swing.JComponent;

import net.mbadelek.universe.utils.Utils;

public class UniverseCanvas extends JComponent {

	
	private static final long serialVersionUID = 7638330588391745923L;
	
	private int size_X;
	private int size_Y;
	private ObjectManager manager;
	private double currentExpSizeX;

	private double expMin = -35;
	private double expMax = 27;

	public UniverseCanvas(int sizeX, int sizeY, ObjectManager manager) {
		this.manager = manager;
		this.manager.setCanvasInfo(sizeX, sizeY);
		this.size_X = sizeX;// canvas
		this.size_Y = sizeY;
		setPreferredSize(new Dimension(sizeX, sizeY));
		this.currentExpSizeX = 7.5;// sqrt(10) m
	}

	public double getExpMin() {
		return expMin;
	}

	public double getExpMax() {
		return expMax;
	}

	public double getCurrentExpSize() {
		return this.currentExpSizeX;
	}

	public synchronized void setCurrentScale(double size_exponent_of10) {
		this.currentExpSizeX = size_exponent_of10;
	}

	@Override
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		g.setColor(Color.BLACK);

		double intensity = 255.0 - Utils.trim((255.0 * (this.currentExpSizeX - 3.7)), 0.0, 255.0);
		int col = ((int) (intensity)) + ((int) (0.9 * intensity)) * 256 + ((int) (0.8 * intensity)) * 256 * 256;

		g.setColor(new Color(col));
		g.clearRect(0, 0, size_X, size_Y);
		g.fillRect(0, 0, size_X, size_Y);
		g.setColor(Color.BLUE);
		g.setClip(0, 0, size_X, size_Y);
		this.drawTunel(g, this.currentExpSizeX);
		this.manager.drawObjects(g, this.currentExpSizeX);
		g.setColor(new Color(0x007700));
		double fracb = 0.35;
		double frace = 1 - fracb;
		g.drawLine(((int) (fracb * size_X)), size_Y - 35, ((int) (frace * size_X)), size_Y - 35);
		NumberFormat formatter = new DecimalFormat("0.##E0");
		g.drawString(formatter.format(Math.pow(10, this.currentExpSizeX) * (frace - fracb)) + " m", this.size_X / 2 - 20, this.size_Y - 20);
	}

	private void drawTunel(Graphics g, double expx) {
		int exp = (int) expx;
		for (int i = exp - 3; i <= exp + 1; i++) {
			int width = (int) ((double) size_X * (Math.pow(10, i) / Math.pow(10, expx)));
			int height = (int) ((double) size_Y * (Math.pow(10, i) / Math.pow(10, expx)));
			g.drawRect(size_X / 2 - width / 2, size_Y / 2 - height / 2, width, height);

			g.drawLine(size_X / 2 - (int) ((double) size_X * (Math.pow(10, i) / Math.pow(10, expx))) / 2,
					size_Y / 2 - (int) ((double) size_Y * (Math.pow(10, i) / Math.pow(10, expx))) / 2,
					size_X / 2 - (int) ((double) size_X * (Math.pow(10, i + 1) / Math.pow(10, expx))) / 2,
					size_Y / 2 - (int) ((double) size_Y * (Math.pow(10, i + 1) / Math.pow(10, expx))) / 2);
			g.drawLine(size_X / 2 + (int) ((double) size_X * (Math.pow(10, i) / Math.pow(10, expx))) / 2,
					size_Y / 2 - (int) ((double) size_Y * (Math.pow(10, i) / Math.pow(10, expx))) / 2,
					size_X / 2 + (int) ((double) size_X * (Math.pow(10, i + 1) / Math.pow(10, expx))) / 2,
					size_Y / 2 - (int) ((double) size_Y * (Math.pow(10, i + 1) / Math.pow(10, expx))) / 2);
			g.drawLine(size_X / 2 + (int) ((double) size_X * (Math.pow(10, i) / Math.pow(10, expx))) / 2,
					size_Y / 2 + (int) ((double) size_Y * (Math.pow(10, i) / Math.pow(10, expx))) / 2,
					size_X / 2 + (int) ((double) size_X * (Math.pow(10, i + 1) / Math.pow(10, expx))) / 2,
					size_Y / 2 + (int) ((double) size_Y * (Math.pow(10, i + 1) / Math.pow(10, expx))) / 2);
			g.drawLine(size_X / 2 - (int) ((double) size_X * (Math.pow(10, i) / Math.pow(10, expx))) / 2,
					size_Y / 2 + (int) ((double) size_Y * (Math.pow(10, i) / Math.pow(10, expx))) / 2,
					size_X / 2 - (int) ((double) size_X * (Math.pow(10, i + 1) / Math.pow(10, expx))) / 2,
					size_Y / 2 + (int) ((double) size_Y * (Math.pow(10, i + 1) / Math.pow(10, expx))) / 2);
		}
	}
}
