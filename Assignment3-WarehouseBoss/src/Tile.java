import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;

public class Tile {

	String type;
    private int x;
    private int y;
    private BufferedImage image;

    public Tile(String type, int x, int y) {
    	this.type = type;
        this.x = x;
        this.y = y;
        try {
        	if (type.equals("Wall")){
        		image = ImageIO.read(getClass().getResourceAsStream("icon/Wall.png"));
        	}
        	else if (type.equals("Ground") || type.equals("Start")){
        		image = ImageIO.read(getClass().getResourceAsStream("icon/Ground.png"));
        	}
        	else if (type.equals("LeftWall")){
        		image = ImageIO.read(getClass().getResourceAsStream("icon/LeftWall.png"));
        	}
        	else if (type.equals("RightWall")){
        		image = ImageIO.read(getClass().getResourceAsStream("icon/RightWall.png"));
        	}
        	else if (type.equals("BottomWall")){
        		image = ImageIO.read(getClass().getResourceAsStream("icon/BottomWall.png"));
        	}
        	else if (type.equals("TopWall")){
        		image = ImageIO.read(getClass().getResourceAsStream("icon/TopWall.png"));
        	}
        	else if (type.equals("TopLeft")){
        		image = ImageIO.read(getClass().getResourceAsStream("icon/TopLeft.png"));
        	}
        	else if (type.equals("TopRight")){
        		image = ImageIO.read(getClass().getResourceAsStream("icon/TopRight.png"));
        	}
        	else if (type.equals("BottomRight")){
        		image = ImageIO.read(getClass().getResourceAsStream("icon/BottomRight.png"));
        	}
        	else if (type.equals("BottomLeft")){
        		image = ImageIO.read(getClass().getResourceAsStream("icon/BottomLeft.png"));
        	}
        	else if (type.equals("LeftBottomInsideCorner")){
        		image = ImageIO.read(getClass().getResourceAsStream("icon/LeftBottomInsideCorner.png"));
        	}
        	else if (type.equals("RightBottomInsideCorner")){
        		image = ImageIO.read(getClass().getResourceAsStream("icon/RightBottomInsideCorner.png"));
        	}
        	else if (type.equals("RightTopInsideCorner")){
        		image = ImageIO.read(getClass().getResourceAsStream("icon/RightTopInsideCorner.png"));
        	}
        	else if (type.equals("LeftTopInsideCorner")){
        		image = ImageIO.read(getClass().getResourceAsStream("icon/LeftTopInsideCorner.png"));
        	}
        	else if (type.equals("MiddleWallBlock")) {
        		image = ImageIO.read(getClass().getResourceAsStream("icon/MiddleWallBlock.png"));
        	}
        	else if (type.equals("TopMiddleWallBlock")) {
        		image = ImageIO.read(getClass().getResourceAsStream("icon/TopMiddleWallBlock.png"));
        	}
        	else if (type.equals("BottomMiddleWallBlock")) {
        		image = ImageIO.read(getClass().getResourceAsStream("icon/BottomMiddleWallBlock.png"));
        	}
        	else if (type.equals("Cone")) {
        		image = ImageIO.read(getClass().getResourceAsStream("icon/HeavyCrate.png"));
        	}
        	else if (type.equals("Goal")) {
        		image = ImageIO.read(getClass().getResourceAsStream("icon/Goal.png"));
        	}
  
        } catch (IOException e) {
        	e.printStackTrace();
        }
    }
    
    public BufferedImage getImage(){
    	return image;
    }
    
    public boolean isType(String type){
    	return this.type.equals(type);
    }

    public int getX() {
        return this.x;
    }

    public int getY() {
        return this.y;
    }
    
    public String getType () {
    	return type;
    }

    public void setX(int x) {
        this.x = x;
    }

    public void setY(int y) {
        this.y = y;
    }
}