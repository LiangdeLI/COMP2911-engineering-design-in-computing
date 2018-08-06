import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;

public class Crate extends Sprite{
	private static final int CRATE_VELOCITY = 5;
	private int momentumX;
	private int momentumY;
	
	public Crate(int x, int y) {
		super(x, y);
        BufferedImage img = null;
        try {
        	img = ImageIO.read(getClass().getResourceAsStream("icon/Crate.png"));
        } catch (IOException e) {
        	e.printStackTrace();
        }
        super.setImage(img);
        momentumX = 0;
        momentumY = 0;
	}
	
	/**
	 * Transition crate in X direction
	 * @param momentum Int representing length of transition 
	 */
	public void gainMomentumX (int momentum) {
		this.momentumX = momentum;
	}
	
	/**
	 * Transition crate in Y direction
	 * @param momentum Int representing length of transition
	 */
	public void gainMomentumY (int momentum) {
		this.momentumY = momentum;
	}
	
	/**
	 * Get the X momentum of crate
	 * @return Int representing X momentum
	 */
	public int getMomentumX () {
		return momentumX;
	}
	
	/**
	 * Get the Y momentum of crate
	 * @return Int representing Y momentum
	 */
	public int getMomentumY () {
		return momentumY;
	}
	
	/**
	 * Slow momentum/transition of crate towards zero. 
	 */
	public void reduceMomentum () {
		if (momentumX < 0) {
			momentumX += CRATE_VELOCITY;
		}
		else if (momentumX > 0) {
			momentumX -= CRATE_VELOCITY;
		}
		else if (momentumY < 0) {
			momentumY += CRATE_VELOCITY;
		}
		else if (momentumY > 0) {
			momentumY -= CRATE_VELOCITY;
		}
		
	}
	
	

}
