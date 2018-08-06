import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.SwingConstants;

public class MainMenu extends Menu {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private static final int bannerX = 400;
	private static final int bannerY = 200;
	private static final int buttonWidth = 120;
	private static final int buttonHeight = 30;
	
	private GridBagConstraints gbc;
	private SoundManager sm;
	private boolean playBGM;
	
	private JButton single;
	private JButton multi;
	private JButton options;
	private JButton custom;
	private JButton exit;
	
	public MainMenu(int menuWidth, int menuHeight, String resource) {
		
		super(menuWidth, menuHeight, resource);
		gbc = new GridBagConstraints ();
		sm = new SoundManager ();
		
		setLayout(new GridBagLayout ());
		ImageIcon bannerImg = null;
		bannerImg = getScaledImageIcon ("Breaking_Bad_logo.png", bannerX, bannerY);
		gbc.insets = new Insets (0, 0, 80, 0); 
		JLabel banner = new JLabel ();
		banner.setPreferredSize(new Dimension (bannerX, bannerY));
		banner.setIcon(bannerImg);
		banner.setOpaque(false);
		banner.setBackground(new Color (0,0,0,0));
		add(banner, gbc);
		
		
		
		
		gbc.insets = new Insets (0, 0, 10, 0);
		single = new JButton ("Singleplayer");
		multi= new JButton ("Multiplayer");
		options = new JButton ("Options");
		custom = new JButton ("Custom Game");
		exit = new JButton ("Exit");
		Dimension buttonSize = new Dimension (buttonWidth, buttonHeight);
		single.setPreferredSize(buttonSize);
		multi.setPreferredSize(buttonSize);
		options.setPreferredSize(buttonSize);
		custom.setPreferredSize(buttonSize);
		exit.setPreferredSize(buttonSize);
		
		single.setRolloverEnabled(true);
		
		
		
		gbc.gridy = 1;
		add(single, gbc);
		gbc.gridy = 2;
		add (multi, gbc);
		gbc.gridy = 3;
		add(options, gbc);
		gbc.gridy = 4;
		add(custom, gbc);
		gbc.gridy = 5;
		add(exit, gbc);
		
		
		
		JLabel tooltip = new JLabel (" ");
		tooltip.setHorizontalAlignment(SwingConstants.CENTER);
		tooltip.setVerticalAlignment(SwingConstants.BOTTOM);
		tooltip.setFont(new Font (Font.SANS_SERIF, Font.BOLD, 20));
		gbc.gridy = 6;

	
		gbc.insets = new Insets (0, 0, 30, 0);
		add (tooltip, gbc);
		
		
		addMouseMotionListener (new MouseMotionAdapter () {
			public void mouseMoved (MouseEvent e) {
				tooltip.setText(" ");
			}
		});
		
		
		single.addMouseMotionListener(new MouseMotionAdapter () {
			public void mouseMoved (MouseEvent e) {
				tooltip.setText("Play a singleplayer game!");
			}
		});
		
		single.addMouseListener(new MouseAdapter () {
			public void mouseEntered (MouseEvent e) {
				sm.playButton();
			}
		});
		
		multi.addMouseMotionListener(new MouseMotionAdapter () {
			public void mouseMoved (MouseEvent e) {
				tooltip.setText("Play a multiplayer game!");
			}
		});
		
		multi.addMouseListener(new MouseAdapter () {
			public void mouseEntered (MouseEvent e) {
				sm.playButton();
			}
		});
		
		options.addMouseMotionListener(new MouseMotionAdapter () {
			public void mouseMoved (MouseEvent e) {
				tooltip.setText("Change your settings!");
			}
		});
		
		options.addMouseListener(new MouseAdapter () {
			public void mouseEntered (MouseEvent e) {
				sm.playButton();
			}
		});
		
		custom.addMouseMotionListener(new MouseMotionAdapter () {
			public void mouseMoved (MouseEvent e) {
				tooltip.setText("Create a custom game!");
			}
		});
		
		custom.addMouseListener(new MouseAdapter () {
			public void mouseEntered (MouseEvent e) {
				sm.playButton();
			}
		});
		
		exit.addMouseMotionListener(new MouseMotionAdapter () {
			public void mouseMoved (MouseEvent e) {
				tooltip.setText("Exit the game!");
			}
		});
		
		exit.addMouseListener(new MouseAdapter () {
			public void mouseEntered (MouseEvent e) {
				sm.playButton();
			}
		});
		
		
		playBGM = false;
		
		banner.setVisible(true);
		beginSoundtrack();
		
		
		/*System.out.println("Parent: " +((super.getParent())));
		System.out.println("Click listener: " + ((DisplayManager)getParent()).clickListener);
		
		sm.playMainMenu();*/
		//super.addBackground(Menu.class.getResource("giphy.gif"));
		//addBackground ("giphy.gif");
		
	}
	
	public void enableListeners (ActionListener listen) {
		
		single.setActionCommand("Start single game");
		single.addActionListener(listen);
		multi.setActionCommand("Start multi game");
		multi.addActionListener(listen);
		options.setActionCommand("Options");
		options.addActionListener(listen);
		custom.setActionCommand("Custom menu");
		custom.addActionListener(listen);
		exit.setActionCommand("Exit game");
		exit.addActionListener(listen);
	}
	
	public void beginSoundtrack () {
		if (!playBGM) {
			sm.playMainMenu();
			playBGM = true;
		}		
	}
	
	public void endSoundtrack () {
		if (playBGM) {
			sm.stopMainMenu();
			playBGM = false;
		}
	}
	
	public boolean getBGMStatus () {
		return playBGM;
	}

	
}
