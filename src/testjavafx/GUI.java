package testjavafx;


import java.awt.Button;
import java.awt.Color;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;


public class GUI extends JApplet{

	static int APP_X = 800;
	static int APP_Y = 600;
	
	static int SIZE_X = 650;//rozmiar canvasu
	static int SIZE_Y = 550;
	static int OFFSET= 25;
	
	private JRadioButton pl;
	private JRadioButton eng;
	private JCheckBox sound;
	private JSlider s;
	
	private static double SLMAX = 10000;
	
	
    private UniverseCanvas Drect;
	
	public void init(){
		this.setSize(APP_X, APP_Y);
		Drect = new UniverseCanvas(this.SIZE_X,this.SIZE_Y,this.APP_X,this.APP_Y);
		this.setBackground(Color.WHITE);
		System.out.println("QQQqs");
		//this.Drect.setBounds(0, 0, SIZE_Y, SIZE_Y);
		this.Drect.setVisible(true);
		this.setContentPane(this.Drect);
		this.Drect.repaint();
		this.setVisible(true);
		this.setLayout(null);
		pl = new JRadioButton("PL");// przycisk
		pl.setBounds(SIZE_X,0,OFFSET*3,OFFSET*2);
		this.add(pl);
		eng = new JRadioButton("ENG");// przycisk
		eng.setBounds(SIZE_X+3*OFFSET,0,OFFSET*3,OFFSET*2);
		this.add(eng);
		eng.setSelected(true);
	
		ButtonGroup group = new ButtonGroup();
	    group.add(pl);
	    group.add(eng);
	    
	    Drect.addMouseMotionListener(new MouseMotionListener(){
	    	   public void mouseMoved(MouseEvent e) {
	    	     int xm = e.getX();
	    	     int ym = e.getY();
	    	     double min = Drect.getexpMin();
				 double max = Drect.getexpMax();
	    	     double expval = min + (max-min)*(s.getValue()/SLMAX) ;
	    	     Drect.setLangSwitch(pl.isSelected() ? 1 : 0);
	    	     Drect.setMousecoords(xm, ym, expval);
	    	   }

	    	    public void mouseDragged(MouseEvent e) {
	    	       
	    	    }
	    });
		
		sound= new JCheckBox("Sound");// przycisk                 
		sound.setBounds(SIZE_X,OFFSET*2,OFFSET*6,OFFSET*2);  
		this.add(sound);   
		
		 
		s = new JSlider(JSlider.HORIZONTAL, 0, (int)SLMAX,1);
	    s.setPaintLabels(true);
	    s.setBounds(0,SIZE_Y+10,APP_X,OFFSET);
	    s.setValue((int)(SLMAX*(Drect.getCurrentexpSize()-Drect.getexpMin())/(Drect.getexpMax()-Drect.getexpMin())));
	    s.addChangeListener(new ChangeListener(){
			@Override
			public synchronized void stateChanged(ChangeEvent arg0) {
				double min = Drect.getexpMin();
				double max = Drect.getexpMax();
				double expval = min + (max-min)*(s.getValue()/SLMAX) ;
				Drect.setCurrentScale(expval);
				//System.out.println(expval);
			}	
	    });
	    this.add(s);
	    

		                                                     
	}
	
}
