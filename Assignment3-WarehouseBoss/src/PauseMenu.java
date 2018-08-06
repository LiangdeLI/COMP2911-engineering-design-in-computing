import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.SwingConstants;

public class PauseMenu extends Menu{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private static final int buttonWidth = 180;
	private static final int buttonHeight = 60;
	
    private JButton backMain;
    private JButton retry;
    private JButton resume;
    private GridBagConstraints gbc;
    private SoundManager sm;
	
	public PauseMenu(int menuWidth, int menuHeight, String resource) 
	{
		super(menuWidth, menuHeight, resource);
		gbc = new GridBagConstraints ();
		sm = new SoundManager ();
		
		gbc.gridx = 0;
		gbc.gridy = 0;

		setOpaque(false);
		
		setLayout(new GridBagLayout ());
		
		gbc.insets = new Insets (0, 0, 30, 0);
		backMain = new JButton ("MainMenu");
		retry= new JButton ("Retry");
		resume = new JButton ("Resume");
		Dimension buttonSize = new Dimension (buttonWidth, buttonHeight);
		backMain.setPreferredSize(buttonSize);
		retry.setPreferredSize(buttonSize);
		resume.setPreferredSize(buttonSize);
		
		backMain.setFont(new Font("Arial", Font.BOLD, 30));
		retry.setFont(new Font("Arial", Font.BOLD, 30));
		resume.setFont(new Font("Arial", Font.BOLD, 30));
		
		gbc.gridy = 1;
		add(backMain, gbc);
		gbc.gridy = 2;
		add (retry, gbc);
		gbc.gridy = 3;
		add(resume, gbc);
		
		JLabel tooltip = new JLabel (" ");
		tooltip.setHorizontalAlignment(SwingConstants.CENTER);
		tooltip.setVerticalAlignment(SwingConstants.BOTTOM);
		tooltip.setFont(new Font (Font.SANS_SERIF, Font.BOLD, 20));
		gbc.gridy = 4;

        gbc.insets = new Insets (0, 0, 30, 0);
		add (tooltip, gbc);
		
		
		addMouseMotionListener (new MouseMotionAdapter () {
			public void mouseMoved (MouseEvent e) {
				tooltip.setText(" ");
			}
		});
		
		
		backMain.addMouseMotionListener(new MouseMotionAdapter () {
			public void mouseMoved (MouseEvent e) {
				tooltip.setText("Go back to MainMenu");
			}
		});
		
	    backMain.addMouseListener(new MouseAdapter () {
			public void mouseEntered (MouseEvent e) {
				sm.playButton();
			}
		});
		
		retry.addMouseMotionListener(new MouseMotionAdapter () {
			public void mouseMoved (MouseEvent e) {
				tooltip.setText("Retry this level");
			}
		});
		
		retry.addMouseListener(new MouseAdapter () {
			public void mouseEntered (MouseEvent e) {
				sm.playButton();
			}
		});
		
		resume.addMouseMotionListener(new MouseMotionAdapter () {
			public void mouseMoved (MouseEvent e) {
				tooltip.setText("Resume the game");
			}
		});
		
		resume.addMouseListener(new MouseAdapter () {
			public void mouseEntered (MouseEvent e) {
				sm.playButton();
			}
		});
		

	}
	
	public void addListener(ActionListener listener)
	{
        backMain.setActionCommand("Go back to MainMenu");
        backMain.addActionListener(listener);
		retry.setActionCommand("Retry this level");
		retry.addActionListener(listener);
		resume.setActionCommand("Resume the game");
		resume.addActionListener(listener);
	}

}
