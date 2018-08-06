import java.awt.Color;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;



public class OptionsMenu extends Menu{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private static final int arrowSide = 20;
	private static final int optionsTitleSize = 40;
	private static final int optionsLabelSize = 20;
	private static final int optionsStatusSize = 20;
	
	private GridBagConstraints gbc;
	private JButton exit;
	private String difficulty;
	private String bgmStatus;
	private JButton lArrowS;
	private JButton rArrowS;

	public OptionsMenu(int menuWidth, int menuHeight, String background) {
		super(menuWidth, menuHeight, background);
		
		setLayout (new GridBagLayout());
		gbc = new GridBagConstraints ();
		difficulty = "Medium";
		bgmStatus = "On";
		
		this.removeAll();
		JLabel optionsTitle = new JLabel ("OPTIONS");
		optionsTitle.setVerticalAlignment(JLabel.NORTH);
		optionsTitle.setFont(new Font(Font.SANS_SERIF, Font.BOLD, optionsTitleSize));
		gbc.gridheight = 1;
		gbc.gridx = 1;
		gbc.gridy = 0;
		gbc.insets.set(0, 0, 60, 0);
		gbc.fill = GridBagConstraints.HORIZONTAL;
		gbc.anchor = GridBagConstraints.NORTH;		
		add (optionsTitle, gbc);
		gbc.anchor = GridBagConstraints.CENTER;
		gbc.gridheight = 1;
		
	
		
						
		JLabel diffL = new JLabel ("GAME DIFFICULTY");
		diffL.setHorizontalAlignment(JLabel.CENTER);
		diffL.setVerticalAlignment(JLabel.CENTER);
		diffL.setFont((new Font (Font.SANS_SERIF, Font.BOLD, optionsLabelSize)));
		gbc.insets.set(0, 0, 10, 0);
		gbc.gridx = 1;
		gbc.gridy = 1;
		add (diffL, gbc);
		
		
		JLabel diffStatus = new JLabel (difficulty);		
		diffStatus.setHorizontalAlignment(JLabel.CENTER);
		diffStatus.setFont((new Font (Font.SANS_SERIF, Font.BOLD, optionsStatusSize)));
		gbc.gridx = 1;
		gbc.gridy = 2;
		add (diffStatus, gbc);
		gbc.insets.set(0, 0, 10, 0);
		
		
		ImageIcon lArrow = getScaledImageIcon("icon/arrowleft.png", arrowSide, arrowSide);	
		JButton leftArrowD = new JButton (lArrow);		
		gbc.gridx = 0;
		gbc.gridy = 2;
		add(leftArrowD, gbc);
		
		ImageIcon rArrow = getScaledImageIcon ("icon/arrowright.png", arrowSide, arrowSide);
		JButton rightArrowD = new JButton (rArrow);
		gbc.gridx = 2;
		gbc.gridy = 2;
		add (rightArrowD, gbc);
		
		JLabel soundL = new JLabel ("Music");
		soundL.setHorizontalAlignment(JLabel.CENTER);
		soundL.setFont((new Font (Font.SANS_SERIF, Font.BOLD, optionsLabelSize)));
		gbc.insets.set(20, 0, 0, 0);
		gbc.gridx = 1;
		gbc.gridy = 3;
		add (soundL, gbc);
		gbc.insets.set(0, 0, 10, 0);
		
		JLabel soundStatus = new JLabel (bgmStatus);
		soundStatus.setHorizontalAlignment(JLabel.CENTER);
		soundStatus.setFont((new Font (Font.SANS_SERIF, Font.BOLD, optionsStatusSize)));
		gbc.gridx = 1;
		gbc.gridy = 4;
		add (soundStatus, gbc);
		
		
		lArrowS = new JButton (lArrow);
		gbc.gridx = 0;
		gbc.gridy = 4;
		add (lArrowS, gbc);
		
		rArrowS = new JButton (rArrow);
		gbc.gridx = 2;
		gbc.gridy = 4;
		rArrowS.setVisible(false);
		add (rArrowS, gbc);
		
		
		gbc.insets.set(40, 0, 0, 0);
		exit = new JButton ("Back to main menu");
		gbc.gridx = 1;
		gbc.gridy = 5;
		add (exit, gbc);
		
		ImageIcon empty = getScaledImageIcon ("icon/transparent.png", lArrow.getIconWidth(), lArrow.getIconHeight());
		System.out.println("Empty is " + empty);
		JButton leftStop = new JButton (empty);
		leftStop.setVisible(true);
		leftStop.setOpaque(false);
		leftStop.setBackground(new Color (0,0,0,0));
		leftStop.setBorderPainted(false);
		leftStop.setFocusPainted(false);
		leftStop.setEnabled(false);
		//clearConstraints();
		gbc.gridx = 0;
		gbc.gridy = 0;
		add (leftStop, gbc);
		JButton rightStop = new JButton (empty);
		rightStop.setBackground(new Color (0,0,0,0));
		rightStop.setBorderPainted(false);
		rightStop.setFocusPainted(false);
		rightStop.setEnabled(false);
		gbc.gridx = 2;
		gbc.gridy = 0;
		add (rightStop, gbc);
		
		
		
		leftArrowD.addActionListener(new ActionListener (){
			@Override
			public void actionPerformed(ActionEvent e) {
				if (difficulty.equals("Medium")) {
					difficulty = "Easy";
					diffStatus.setText(difficulty);
					leftArrowD.setVisible(false);
				}
				else if (difficulty.equals("Hard")) {
					difficulty = "Medium";
					diffStatus.setText(difficulty);
					rightArrowD.setVisible(true);
				}
				
			}			
		});
		
		rightArrowD.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent arg0) {
				if (difficulty.equals("Medium")) {
					difficulty = "Hard";
					diffStatus.setText(difficulty);
					rightArrowD.setVisible(false);
				}
				else if (difficulty.equals("Easy")) {
					difficulty = "Medium";
					diffStatus.setText(difficulty);
					leftArrowD.setVisible(true);
				}
				
			}
			
		});
		
		lArrowS.addActionListener(new ActionListener () {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				if (bgmStatus.equals("On")) {
					bgmStatus = "Off";
					soundStatus.setText(bgmStatus);
					lArrowS.setVisible(false);
					rArrowS.setVisible(true);
				}				
			}			
		});
		
		rArrowS.addActionListener(new ActionListener () {
			@Override
			public void actionPerformed(ActionEvent e) {
				if (bgmStatus.equals("Off")) {
					bgmStatus = "On";
					soundStatus.setText(bgmStatus);
					rArrowS.setVisible(false);
					lArrowS.setVisible(true);
				}
				
			}
			
		});
		
		revalidate();
		repaint ();
		
	}
	
	/*
	 * 			else if (command.equals("Lower difficulty")) {
				System.out.println("Lowering difficulty");
				if (difficulty.equals("Medium")) {
					difficulty = "Easy";
					
				}
				else if (difficulty.equals("Hard")) {
					difficulty = "Medium";
				}
				((JLabel)e.getSource()).setText(difficulty);
				System.out.println("Difficulty is now:" + difficulty);
			}
	 */
	
	public void enableListeners (ActionListener listen) {
		exit.setActionCommand("Back to main");
		exit.addActionListener(listen);
		lArrowS.setActionCommand("Music off");
		lArrowS.addActionListener(listen);
		rArrowS.setActionCommand("Music on");
		rArrowS.addActionListener(listen);
		//exit.setActionCommand("Back to main");
		//exit.addActionListener(((DisplayManager)getParent()).clickListener);
	}

	public String getDifficulty () {
		return difficulty;
	}


	
}
