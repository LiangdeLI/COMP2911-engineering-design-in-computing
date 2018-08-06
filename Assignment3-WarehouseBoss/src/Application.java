import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;

import javax.swing.JFrame;

public class Application extends JFrame {
    
	private static final long serialVersionUID = 1L;
	
	public static final int WIDTH = 1280;
	public static final int HEIGHT = 800;

	private DisplayManager dManager;

	
	public Application() {
		System.out.println();
		initUI();
		dManager = new DisplayManager();
		add (dManager);
        setVisible(true);

        
    }

    private void initUI() {
        setSize(new Dimension(WIDTH, HEIGHT));
        setResizable(false);
        setTitle("Warehouse Boss");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);    	
    	

    }    
    
    
    public static void main(String[] args) {
        
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                Application app = new Application();
            }
        });
    }
}