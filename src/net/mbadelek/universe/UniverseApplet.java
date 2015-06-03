package net.mbadelek.universe;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;

import javax.swing.ButtonGroup;
import javax.swing.JApplet;
import javax.swing.JCheckBox;
import javax.swing.JOptionPane;
import javax.swing.JRadioButton;
import javax.swing.JSlider;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import net.mbadelek.universe.utils.Player;

public class UniverseApplet extends JApplet {

	/**
	 * 
	 */
	private static final long serialVersionUID = -6732378041495246101L;
	
	private final int APPLET_WIDTH = 800;
	private final int APPLET_HEIGHT = 600;
	private final int CANVAS_WIDTH = 650;
	private final int CANVAS_HEIGHT = 550;
	private final double SLMAX = 10000;

	private UniverseCanvas universeCanvas;
	private JRadioButton languageButtonPL = new JRadioButton("PL");
	private JRadioButton languageButtonEN = new JRadioButton("EN", true);
	private JCheckBox sound = new JCheckBox("Sound");
	private JSlider slider = new JSlider(JSlider.HORIZONTAL, 0, (int) SLMAX, 1);

	private Player musicPlayer;
	private ObjectManager manager = new ObjectManager();
	
	public void loadResources() throws Exception {
		musicPlayer = new Player("sound.wav");
		manager.load();
	}
	
	public void initGUI() {
		setSize(APPLET_WIDTH, APPLET_HEIGHT);
//		getContentPane().setBackground(Color.BLACK);
		setLayout(new GridBagLayout());

		universeCanvas = new UniverseCanvas(this.CANVAS_WIDTH, this.CANVAS_HEIGHT, manager);
		universeCanvas.addMouseMotionListener(new MouseMotionListener() {
			public void mouseMoved(MouseEvent e) {
				double min = universeCanvas.getExpMin();
				double max = universeCanvas.getExpMax();
				double expval = min + (max - min) * (slider.getValue() / SLMAX);
				manager.setLangSwitch(languageButtonPL.isSelected() ? 1 : 0);
				manager.setMouseCoords(e.getX(), e.getY(), expval);
				universeCanvas.repaint();
			}

			public void mouseDragged(MouseEvent e) {
			}
		});

		sound.addChangeListener(new ChangeListener() {

			@Override
			public void stateChanged(ChangeEvent arg0) {
				musicPlayer.setPlaying(sound.isSelected());
			}
		});
		
		slider.addChangeListener(new ChangeListener() {
			
			@Override
			public void stateChanged(ChangeEvent arg0) {
				double min = universeCanvas.getExpMin();
				double max = universeCanvas.getExpMax();
				double expval = min + (max - min) * (slider.getValue() / SLMAX);
				universeCanvas.setCurrentScale(expval);
				universeCanvas.repaint();
			}
		});
		
		ButtonGroup group = new ButtonGroup();
		group.add(languageButtonPL);
		group.add(languageButtonEN);
		
		slider.setValue((int) (SLMAX * (universeCanvas.getCurrentExpSize() - universeCanvas.getExpMin()) / (universeCanvas.getExpMax() - universeCanvas.getExpMin())));
		
		double canvasAppletWidthRatio = (double) CANVAS_WIDTH / APPLET_WIDTH;
		double canvasAppletHeightRatio = (double) CANVAS_HEIGHT / APPLET_HEIGHT;
		Insets zeroInsets = new Insets(0, 0, 0, 0);
		add(universeCanvas, new GridBagConstraints(0, 0, 1, 3, canvasAppletWidthRatio, canvasAppletHeightRatio,
				GridBagConstraints.CENTER, GridBagConstraints.BOTH, zeroInsets, 0, 0));
		add(languageButtonPL, new GridBagConstraints(1, 0, 1, 1, (1 - canvasAppletWidthRatio) / 2, (1 - canvasAppletWidthRatio) / 3,
				GridBagConstraints.NORTHWEST, GridBagConstraints.HORIZONTAL, zeroInsets, 0, 0));
		add(languageButtonEN, new GridBagConstraints(2, 0, 1, 1, (1 - canvasAppletWidthRatio) / 2, (1 - canvasAppletWidthRatio) / 3,
				GridBagConstraints.NORTHWEST, GridBagConstraints.HORIZONTAL, zeroInsets, 0, 0));
		add(sound, new GridBagConstraints(1, 1, 1, 2, (1 - canvasAppletWidthRatio) / 2, canvasAppletHeightRatio - (1 - canvasAppletWidthRatio) / 3,
				GridBagConstraints.NORTHWEST, GridBagConstraints.HORIZONTAL, zeroInsets, 0, 0));
		add(slider, new GridBagConstraints(0, 3, 3, 1, 1.0, (1 - canvasAppletHeightRatio),
				GridBagConstraints.CENTER, GridBagConstraints.HORIZONTAL, zeroInsets, 0, 0));
		setVisible(true);
	}
	
	public void init() {
		try {
			loadResources();
		} catch (Exception e) {
			e.printStackTrace();
			JOptionPane.showMessageDialog(null, "An error occurred while loading resources.", "Error", JOptionPane.ERROR_MESSAGE);
			System.exit(1);
		}
		initGUI();
	}

}
