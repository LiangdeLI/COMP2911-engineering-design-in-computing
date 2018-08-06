import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GridBagConstraints;
import java.awt.Image;
import java.awt.Insets;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;

import javax.swing.ImageIcon;
import javax.swing.JPanel;




public class Menu extends JPanel{
	private static final long serialVersionUID = 1L;

	
	
	private ImageIcon background;
	private GridBagConstraints gbc;
	
	
	protected String soundLevel;
	
	/**
	 * Constructor for the game menus
	 * 
	 * @param backgroundRes Path to desired background to render
	 * @param menuWidth Desired width for menu
	 * @param menuHeight Desired height for menu
	 */
	public Menu (int menuWidth, int menuHeight, String resource) {
		setSize (new Dimension (menuWidth, menuHeight));
		gbc = new GridBagConstraints ();
		//ClassLoader c1 = Thread.currentThread().getContextClassLoader();
		background = null;
		if (resource != null) {
			addBackground (resource);
		}
		soundLevel = "Medium"; // default
		
	}
	
	


	private void addBackground (String resource) {
		background = new ImageIcon (Menu.class.getResource(resource));
	}
	
	public void paintComponent (Graphics g) {
		super.paintComponent(g);
		if (background != null) {
			Image bg = background.getImage();
			g.drawImage(bg, 0, 0, this.getWidth(), this.getHeight(), this);
		}
		
		
	}
	
	
	protected void clearConstraints () {
		gbc.gridx = 0;
		gbc.gridy = 0;
		gbc.insets.set(0, 0, 0, 0);
		gbc.gridheight = 1;
		gbc.gridwidth = 1;
	}
	
	
	protected ImageIcon getScaledImageIcon (String resource, int width, int height) {
		ImageIcon converted = new ImageIcon (Menu.class.getResource(resource));
		Image temp = converted.getImage();
		Image convertedImage = getScaledImage (temp, width, height);
		converted = new ImageIcon (convertedImage);
		return converted;
	}
	
	//WARNING: ATTRIBUTE THIS CODE!!!!!!!!!!!!!!!!
	//Copy pasta code: http://stackoverflow.com/questions/6714045/how-to-resize-jlabel-imageicon
	private Image getScaledImage(Image srcImg, int w, int h){
	    BufferedImage resizedImg = new BufferedImage(w, h, BufferedImage.TYPE_INT_ARGB);
	    Graphics2D g2 = resizedImg.createGraphics();

	    g2.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
	    g2.drawImage(srcImg, 0, 0, w, h, null);
	    g2.dispose();

	    return resizedImg;
	}

}
