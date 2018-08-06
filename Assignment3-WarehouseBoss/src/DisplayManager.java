import java.awt.CardLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

import javax.swing.JPanel;

public class DisplayManager extends JPanel{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public static final int WIDTH = 1280;
	public static final int HEIGHT = 800;
	
	public static final int PLAYER1 = 1;
	public static final int PLAYER2 = 2;
	
	private CardLayout displaySlot;
	private MainMenu mainMenu;
	private OptionsMenu optionsMenu;
	private Game game1;
	private Game game2;
	private LevelBuilder customMenu;
	private PauseMenu pauseMenu;
	

	
	public DisplayManager () {
		setSize (WIDTH, HEIGHT);
		optionsMenu = new OptionsMenu (WIDTH, HEIGHT, null);
		customMenu = new LevelBuilder (WIDTH, HEIGHT, null);
		mainMenu = new MainMenu (WIDTH, HEIGHT, "giphy.gif");
		pauseMenu = new PauseMenu(WIDTH, HEIGHT, null);
		displaySlot = new CardLayout ();
		setLayout(displaySlot);
		add (mainMenu, "MAINMENU");
		add (optionsMenu, "OPTIONS");
		add (customMenu, "CUSTOM");
		add (pauseMenu, "PAUSEMENU");
		changeDisplay("MAINMENU");
		mainMenu.enableListeners(new ActionListener (){
		@Override
		public void actionPerformed(ActionEvent e) {
				if (e.getActionCommand().equals("Options")) {
					changeDisplay ("OPTIONS");
				}
				else if (e.getActionCommand().equals("Start single game")) {
					//System.out.println("Here");
					game1 = new Game (optionsMenu.getDifficulty(), PLAYER1);
					game1.addKeyListener(new KeyAdapter() {
					    public void keyPressed(KeyEvent e){
					    	if(e.getKeyCode() == KeyEvent.VK_ESCAPE){
					    		changeDisplay ("PAUSEMENU");
					    	}
					    }
					});
					add (game1, "game");
					changeDisplay ("game");
					game1.requestFocus();
				}
				else if(e.getActionCommand().equals("Start multi game")){
//					game1 = new Game(optionsMenu.getDifficulty(), PLAYER1);
//					game2 = new Game(optionsMenu.getDifficulty(), PLAYER2);
//					game1.addKeyListener(new KeyAdapter() {
//					    public void keyPressed(KeyEvent e){
//					    	if(e.getKeyCode() == KeyEvent.VK_ESCAPE){
//					    		changeDisplay ("PAUSEMENU");
//					    	}
//					    }
//					});
//					JPanel game = new JPanel();
//					game.setLayout(new GridLayout(1,2));
//					game.add (game1);
//					game.add (game2);
//					add(game, "game");
//					changeDisplay ("game");
//					game.setVisible(true);
//					game.requestFocus();
//					game1.requestFocus();
//					game2.requestFocus();
				}
				else if (e.getActionCommand().equals("Exit game")) {
					System.exit(0);
				}
				else if (e.getActionCommand().equals("Custom menu")) {
					changeDisplay ("CUSTOM");
				}
				
			}
			
		});
		optionsMenu.enableListeners( new ActionListener (){

			@Override
			public void actionPerformed(ActionEvent e) {
				if (e.getActionCommand().equals("Back to main")) {
					changeDisplay ("MAINMENU");
				}
				else if (e.getActionCommand().equals("Music off")) {
					mainMenu.endSoundtrack();
				}
				else if (e.getActionCommand().equals("Music on")) {
					mainMenu.beginSoundtrack();
				}
				
			}
			
		});
		
		customMenu.enableListeners(new ActionListener () {
			@Override
			public void actionPerformed (ActionEvent e) {
				if (e.getActionCommand().equals("Return main")) {
					changeDisplay ("MAINMENU");
				}
			}
		});
		
		
		
		pauseMenu.addListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if(e.getActionCommand().equals("Go back to MainMenu")){
					changeDisplay ("MAINMENU");
				}
				else if(e.getActionCommand().equals("Retry this level")){
					remove(game1);
					Game newgame1 = new Game(optionsMenu.getDifficulty(), PLAYER1, game1.getLevel());
					
					game1 = newgame1;
					game1.addKeyListener(new KeyAdapter() {
					    public void keyPressed(KeyEvent e){
					    	if(e.getKeyCode() == KeyEvent.VK_ESCAPE){
					    		changeDisplay ("PAUSEMENU");
					    	}
					    }
					});
					add (game1, "game");
					changeDisplay ("game");
					game1.requestFocus();
				}
				else if(e.getActionCommand().equals("Resume the game")){
					changeDisplay ("game");
					game1.requestFocus();
				}
			}
			
		});
	}
	
	public void changeDisplay (String displayName) {
		displaySlot.show(this, displayName);
		
	}

	
}
