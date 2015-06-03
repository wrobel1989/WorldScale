package net.mbadelek.universe;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FilenameFilter;
import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.imageio.ImageIO;

import net.mbadelek.universe.utils.Vector2d;
import net.mbadelek.universe.utils.DoubleRectangle;

public class ObjectManager {

	private PhysicalObject[] objects;
	// private File[] imglist;

	private int canvasX;
	private int canvasY;

	private String drawDescr;
	private int descrX = -666, descrY = -666;
	private int langSwitch = 0; // 0-eng, 1-pl

	public int load() {
		File folder = new File(".");
		File[] images = folder.listFiles(new FilenameFilter() {

			public boolean accept(File dir, String name) {
				return name.toLowerCase().endsWith(".png");
			}
		});

		this.objects = new PhysicalObject[images.length];
		
		for (int i = 0; i < getObjectsCount(); i++) {
			Pattern pattern = Pattern.compile("(?<=RSX).*(?=#D)");
			Matcher matcher = pattern.matcher(images[i].getName());

			String out_phsize = (matcher.find() ? matcher.group(0) : "").replaceAll("#p", ".");
			double phsizeX = Double.parseDouble(out_phsize);

			Pattern pattern2 = Pattern.compile("(?<=#D).*(?=\\.png)");
			Matcher matcher2 = pattern2.matcher(images[i].getName());

			String out_decs = matcher2.find() ? matcher2.group() : "";

			BufferedImage img = null;
			try {
				img = ImageIO.read(images[i]);
			} catch (IOException e) {
				e.printStackTrace();
			}
			Vector2d pos = new Vector2d(0, 0);
			this.objects[i] = new PhysicalObject(img, phsizeX, out_decs, pos);
			pos = this.findNextSuitedPosition(phsizeX, i);
			this.objects[i].setRealPos(pos);
		}

		return 0;
	}
	
	public int getObjectsCount() {
		return objects == null ? 0 : objects.length;
	}

	public void setMouseCoords(int xm, int ym, double expval) {
		double realx = (((double) xm - canvasX / 2.0) / ((double) 0.5 * canvasX)) * 0.5 * Math.pow(10, expval);
		double realy = (((double) ym - canvasY / 2.0) / ((double) 0.5 * canvasY)) * 0.5 * (((double) canvasY) / ((double) canvasX)) * Math.pow(10, expval);
		for (int i = 0; i < getObjectsCount(); i++) {
			if (this.objects[i].isXYWithinObject(realx, realy)) {
				this.descrX = xm;
				this.descrY = ym;
				this.drawDescr = this.objects[i].getName();
				break;
			}
		}
	}

	public void setLangSwitch(int lswitch) {
		this.langSwitch = lswitch;
	}

	private Vector2d findNextSuitedPosition(double pxh, int count) {
		int Nangle = 6;
		double r = 0.52 * pxh * Math.sqrt(1.0 + (this.objects[count].getYXratio()) * (this.objects[count].getYXratio()));
		double angle = (2.0 * Math.PI / Nangle) * (count % Nangle);
		Vector2d pos = new Vector2d(r * Math.cos(angle), r * Math.sin(angle));

		int Nangl = 24;
		double ratio = this.objects[count].getYXratio();
		double R = 0.52 * pxh * Math.sqrt(1.0 + (ratio) * (ratio));
		boolean foundpos = false;
		long counter = 0;
		long totalcounter = 0;
		while (!foundpos) {
			angle = (2.0 * Math.PI / Nangl) * (counter % Nangl);
			DoubleRectangle trect = new DoubleRectangle(R * Math.cos(angle) - pxh / 2.0, R * Math.sin(angle) - 0.5 * pxh * this.objects[count].getYXratio(),
					pxh, pxh * this.objects[count].getYXratio());
			boolean notintersect = true;
			for (int i = 0; i < count; i++) {
				DoubleRectangle el = this.objects[i].getRealPos();
				if (trect.intersects(el))
					notintersect = false;
			}
			if (notintersect) {
				foundpos = true;
				pos = new Vector2d(R * Math.cos(angle), R * Math.sin(angle));
				System.out.println("Found pos " + this.objects[count].getName() + " angle : " + angle + " posx " + pos.x + " posy " + pos.y + " ratio "
						+ ratio);
				break;
			}
			counter++;
			totalcounter++;
			if (counter == Nangl) {
				counter = 0;
				R = R * 1.01;
			}
			if (totalcounter > 9000000) {
				System.out.println("Too many iterations BREAK, pos " + this.objects[count].getName() + " angle : " + angle + " posx " + pos.x + " posy "
						+ pos.y + " ratio " + ratio);
				System.out.println("REct rx " + trect.getX() + " ry " + trect.getY() + " w " + trect.getWidth() + " h " + trect.getHeight() + " R: " + R
						+ " counter " + counter);
				break;
			}
		}
		System.out.println("Number of objects: " + getObjectsCount());
		return pos;
	}

	public void drawObjects(Graphics g, double expscale) {
		for (int i = 0; i < getObjectsCount(); i++) {
			this.objects[i].draw(g, expscale);
		}
		if (this.descrX >= 0 && this.descrY >= 0) {
			g.setColor(new Color(0xff0000));
			String out = this.drawDescr;
			if (this.langSwitch == 0) {
				Pattern pattern = Pattern.compile(".*?(?=QQ)");
				Matcher matcher = pattern.matcher(this.drawDescr);
				String out_phsize = (matcher.find() ? matcher.group(0) : "");
				if (out_phsize != "")
					out = out_phsize;
			} else {
				Pattern pattern = Pattern.compile("(?<=QQ).*");
				Matcher matcher = pattern.matcher(this.drawDescr);
				String out_phsize = (matcher.find() ? matcher.group(0) : "");
				if (out_phsize != "")
					out = out_phsize;
			}
			g.drawString(out, this.descrX, this.descrY);
		}
	}

	public void setCanvasInfo(int canvasX, int canvasY) {
		this.canvasX = canvasX;
		this.canvasY = canvasY;
		for (int i = 0; i < getObjectsCount(); i++)
			this.objects[i].setCanvasInfo(this.canvasX, this.canvasY);
	}

}
