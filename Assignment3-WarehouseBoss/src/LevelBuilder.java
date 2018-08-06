import java.awt.event.ActionListener;

import javax.swing.JButton;

public class LevelBuilder extends Menu {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JButton exit;
	
	public LevelBuilder(int menuWidth, int menuHeight, String resource) {
		super(menuWidth, menuHeight, resource);
		
		exit = new JButton ("Exit to main menu");
		add (exit);
	}
	
	
	public void enableListeners (ActionListener listen) {
		exit.setActionCommand ("Return main");
		exit.addActionListener(listen);
	}

}
