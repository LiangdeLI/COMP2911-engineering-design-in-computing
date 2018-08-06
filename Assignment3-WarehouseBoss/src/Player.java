import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;

public class Player extends Sprite {
	
	private BufferedImage standing;
	private BufferedImage Left1;
	private BufferedImage Left2;
	private BufferedImage Left3;
	private BufferedImage Left4;
	private BufferedImage Left5;
	private BufferedImage Right1;
	private BufferedImage Right2;
	private BufferedImage Right3;
	private BufferedImage Right4;
	private BufferedImage Right5;
	private BufferedImage PushLeft1;
	private BufferedImage PushLeft2;
	private BufferedImage PushLeft3;
	private BufferedImage PushLeft4;
	private BufferedImage PushLeft5;
	private BufferedImage PushLeft6;
	private BufferedImage PushRight1;
	private BufferedImage PushRight2;
	private BufferedImage PushRight3;
	private BufferedImage PushRight4;
	private BufferedImage PushRight5;
	private BufferedImage PushRight6;
	private BufferedImage MoveUp1;
	private BufferedImage MoveUp2;
	private BufferedImage PushUp1;
	private BufferedImage PushUp2;
	private BufferedImage MoveDown1;
	private BufferedImage MoveDown2;
	private BufferedImage PushDown1;
	private BufferedImage PushDown2;
	
	private boolean isPushing;
	
	
	public Player(int x, int y) {
        super(x,y);
        setPushing(false);
        //Set player icon
        BufferedImage img = null;
        try {
        	img = ImageIO.read(getClass().getResourceAsStream("icon/Player.png"));
        } catch (IOException e) {
        	e.printStackTrace();
        }
        super.setImage(img);
       
        try {
        	standing = ImageIO.read(getClass().getResourceAsStream("icon/Player.png"));
        	
          	Left1 = ImageIO.read(getClass().getResourceAsStream("icon/Left1.png"));
          	Left2 = ImageIO.read(getClass().getResourceAsStream("icon/Left2.png"));
          	Left3 = ImageIO.read(getClass().getResourceAsStream("icon/Left3.png"));
          	Left4 = ImageIO.read(getClass().getResourceAsStream("icon/Left4.png"));
            Left5 = ImageIO.read(getClass().getResourceAsStream("icon/Left5.png"));
          	 
          	Right1 = ImageIO.read(getClass().getResourceAsStream("icon/Right1.png"));
          	Right2 = ImageIO.read(getClass().getResourceAsStream("icon/Right2.png"));
          	Right3 = ImageIO.read(getClass().getResourceAsStream("icon/Right3.png"));
          	Right4 = ImageIO.read(getClass().getResourceAsStream("icon/Right4.png"));
          	Right5 = ImageIO.read(getClass().getResourceAsStream("icon/Right5.png"));
          	
          	PushRight1 = ImageIO.read(getClass().getResourceAsStream("icon/PushRight1.png"));
          	PushRight2 = ImageIO.read(getClass().getResourceAsStream("icon/PushRight2.png"));
          	PushRight3 = ImageIO.read(getClass().getResourceAsStream("icon/PushRight3.png"));
          	PushRight4 = ImageIO.read(getClass().getResourceAsStream("icon/PushRight4.png"));
          	PushRight5 = ImageIO.read(getClass().getResourceAsStream("icon/PushRight5.png"));
          	PushRight6 = ImageIO.read(getClass().getResourceAsStream("icon/PushRight6.png"));
          	
          	PushLeft1 = ImageIO.read(getClass().getResourceAsStream("icon/PushLeft1.png"));
          	PushLeft2 = ImageIO.read(getClass().getResourceAsStream("icon/PushLeft2.png"));
          	PushLeft3 = ImageIO.read(getClass().getResourceAsStream("icon/PushLeft3.png"));
          	PushLeft4 = ImageIO.read(getClass().getResourceAsStream("icon/PushLeft4.png"));
          	PushLeft5 = ImageIO.read(getClass().getResourceAsStream("icon/PushLeft5.png"));
          	PushLeft6 = ImageIO.read(getClass().getResourceAsStream("icon/PushLeft5.png"));
          	
          	MoveUp1 = ImageIO.read(getClass().getResourceAsStream("icon/MoveUp1.png"));
          	MoveUp2 = ImageIO.read(getClass().getResourceAsStream("icon/MoveUp2.png"));
          	PushUp1 = ImageIO.read(getClass().getResourceAsStream("icon/PushUp1.png"));
          	PushUp2 = ImageIO.read(getClass().getResourceAsStream("icon/PushUp2.png"));
          	
          	MoveDown1 = ImageIO.read(getClass().getResourceAsStream("icon/MoveDown1.png"));
          	MoveDown2 = ImageIO.read(getClass().getResourceAsStream("icon/MoveDown2.png"));
          	PushDown1 = ImageIO.read(getClass().getResourceAsStream("icon/PushDown1.png"));
          	PushDown2 = ImageIO.read(getClass().getResourceAsStream("icon/PushDown2.png"));
          	
          } catch (IOException e) {
          	e.printStackTrace();
          }
     
     
    }
	
    public void updateFrame(int xMove, int yMove) {
    	
    	if (isPushing) {
    		if (xMove == -50) {
    			super.setImage(PushRight1);
    		} else if (xMove == -40) {
    			super.setImage(PushRight2);
    		} else if (xMove == -30) {
    			super.setImage(PushRight3);
    		} else if (xMove == -20) {
    			super.setImage(PushRight4);
    		} else if (xMove == -10) {
    			super.setImage(PushRight5);
    		}  if (xMove == 50) {
    			super.setImage(PushLeft1);
    		} else if (xMove == 40) {
    			super.setImage(PushLeft2);
    		} else if (xMove == 30) {
    			super.setImage(PushLeft3);
    		} else if (xMove == 20) {
    			super.setImage(PushLeft4);
    		} else if (xMove == 10) {
    			super.setImage(PushLeft5);
    		}
    		
    		if (yMove == 50 || yMove == 30 || yMove == 10) {
				super.setImage(PushUp1);
			} else if (yMove == 40 || yMove == 20) {
				super.setImage(PushUp2);
			}
			else if (yMove == -50 || yMove == -30 || yMove == -10) {
				super.setImage(PushDown1);
			} else if (yMove == -40 || yMove == -20) {
				super.setImage(PushDown2);
			}
    		
    				
    		if (xMove == 0 && yMove == 0) {
    			super.setImage(standing);
    			this.setPushing(false);
    		}
    	
    	} 	else  {
    		if (xMove == -50) {
    			super.setImage(Right2);
			} else if (xMove == -45) {
				super.setImage(Right3);
			} else if (xMove == -40) {
				super.setImage(Right4);
			} else if (xMove == -35) {
				super.setImage(Right5);
			} else if (xMove == -30) {
				super.setImage(Right2);
			}else if (xMove == -25) {
    			super.setImage(Right3);
			} else if (xMove == -20) {
				super.setImage(Right4);
			} else if (xMove == -15) {
				super.setImage(Right5);
			} else if (xMove == -10) {
				super.setImage(Right2);
			} else if (xMove == -5) {
				super.setImage(Right3);
			} 
			else if (xMove == 50) {
    			super.setImage(Left2);
			} else if (xMove == 45) {
				super.setImage(Left3);
			} else if (xMove == 40) {
				super.setImage(Left4);
			} else if (xMove == 35) {
				super.setImage(Left5);
			} else if (xMove == 30) {
				super.setImage(Left2);
			}else if (xMove == 25) {
    			super.setImage(Left3);
			} else if (xMove == 20) {
				super.setImage(Left4);
			} else if (xMove == 15) {
				super.setImage(Left5);
			} else if (xMove == 10) {
				super.setImage(Left2);
			} else if (xMove == 5) {
				super.setImage(Left3);
			}
			
			if (yMove == 50 || yMove == 30 || yMove == 10) {
				super.setImage(MoveUp1);
			} else if (yMove == 40 || yMove == 20) {
				super.setImage(MoveUp2);
			}
			else if (yMove == -50 || yMove == -30 || yMove == -10) {
				super.setImage(MoveDown1);
			} else if (yMove == -40 || yMove == -20) {
				super.setImage(MoveDown2);
			}
			
			if (xMove == 0 && yMove == 0) {
    			super.setImage(standing);
    			this.setPushing(false);
    		}
    	}
    	
    	
    	
    }

	public boolean isPushing() {
		return isPushing;
	}

	public void setPushing(boolean isPushing) {
		this.isPushing = isPushing;
	}
   }

