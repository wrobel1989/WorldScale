package testjavafx;



import java.awt.Button;
import java.awt.Color;

import javax.swing.*;


public class GUI extends JApplet {

	static int APP_X = 1100;
	static int APP_Y = 600;
	
	static int SIZE_X = 950;//rozmiar canvasu
	static int SIZE_Y = 550;
	static int OFFSET= 25;
	
	private JButton pl;
	private JButton eng;
	private JButton sound;
	private JSlider s;
	
	
    private UniverseCanvas Drect;
	
	public void init(){
		this.setSize(APP_X, APP_Y);
		Drect = new UniverseCanvas(this.SIZE_X,this.SIZE_Y);
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
		
		s = new JSlider(JSlider.HORIZONTAL );
	    s.setPaintLabels(true);
	    s.setBounds(0,SIZE_Y+10,APP_X,OFFSET);
	    this.add(s);
	    

		                                                     
	}
	
}
