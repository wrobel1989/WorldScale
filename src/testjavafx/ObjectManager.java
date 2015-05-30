package testjavafx;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FilenameFilter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.imageio.ImageIO;

public class ObjectManager {

	private int Nobj;
	//private File[] imglist;
	private PhysicalObject[] objs;
	
	private int canvasX;
	private int canvasY;
	
	public ObjectManager(){
		this.Nobj = 0;
		
		this.initObj();
	}
	
	
	private int initObj(){
		File folder = new File(".");
		File[] imglist = folder.listFiles(new FilenameFilter() {
		    public boolean accept(File dir, String name) {
		        return name.toLowerCase().endsWith(".png");
		    }
		});
		this.Nobj = imglist.length;
		this.objs = new PhysicalObject[this.Nobj];
		for(int i=0;i<this.Nobj;i++){
			String name_raw = imglist[i].getName();
			String name_raw2 = name_raw;
		    Pattern pattern = Pattern.compile("(?<=RSX).*(?=#D)");
		    Matcher matcher = pattern.matcher(name_raw);

		    String out_phsize = (matcher.find() ? matcher.group(0) : "").replaceAll("#p", ".");
		    double phsizeX = Double.parseDouble(out_phsize);

		    Pattern pattern2 = Pattern.compile("(?<=#D).*(?=\\.png)");
		    Matcher matcher2 = pattern2.matcher(name_raw2);

		    String out_decs = matcher2.find() ? matcher2.group() : "";
		        
		    BufferedImage img = null;
			try {
				img = ImageIO.read(imglist[i]);
			} catch (IOException e) {
				e.printStackTrace();
			}
			vec2d pos = new vec2d(0,0);
			this.objs[i] = new PhysicalObject(img ,phsizeX , out_decs ,pos);
			pos = this.findNextSuitedPosition(phsizeX,i);
			this.objs[i].setRealPos(pos);
		    
		}
		return 0;
	}

	
	private vec2d findNextSuitedPosition(double pxh, int count){
		int Nangle = 6;
		double r = 0.56 * pxh*Math.sqrt(1.0 + (this.objs[count].getYXratio())*(this.objs[count].getYXratio()));
		double angle = (2.0*Math.PI/Nangle)*(count%Nangle);
		vec2d pos = new vec2d(r*Math.sin(angle), r*Math.cos(angle));
		return pos;
	}
	
	public void drawScene(Graphics g, double expscale){
	    for(int i=0 ; i< this.Nobj;i++){
	    	this.objs[i].drawMe(g, expscale);
	    }
	 }
	
	public void setCanvasinfo(int CX,int CY){
		this.canvasX = CX;
		this.canvasY = CY;
		for(int i=0;i<this.Nobj;i++)
			this.objs[i].setCanvasinfo(this.canvasX,this.canvasY);
	}
	
}



