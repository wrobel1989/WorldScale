package testjavafx;


import java.awt.Button;
import java.awt.Color;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;


public class GUI extends JApplet{

	static int APP_X = 1100;
	static int APP_Y = 600;
	
	static int SIZE_X = 950;//rozmiar canvasu
	static int SIZE_Y = 550;
	static int OFFSET= 25;
	
	private JButton pl;
	private JButton eng;
	private JButton sound;
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
		pl = new JButton("PL");// przycisk
		pl.setBounds(SIZE_X,0,OFFSET*3,OFFSET*2);
		this.add(pl);
		eng = new JButton("ENG");// przycisk
		eng.setBounds(SIZE_X+3*OFFSET,0,OFFSET*3,OFFSET*2);
		this.add(eng);
		
		sound= new JButton("Sound");// przycisk                 
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
