import java.awt.image.BufferedImage;

public class Sprite {
	String type;
    private int x;
    private int y;
    private BufferedImage image;

    public Sprite(int x, int y) {
        this.x = x;
        this.y = y;
    }
    
    public BufferedImage getImage(){
    	return image;
    }
        
    public void setImage(BufferedImage img){
    	image = img;
    }
    
    public int getX(){
    	return x;
    }
    
    public int getY(){
    	return y;
    }
    
    public void setX(int x){
    	this.x = x;
    }
    
    public void setY(int y){
    	this.y = y;
    }
    
	public void move(String direction){
		switch(direction){
			case("left"):
				x--;
				break;
			case("right"):
				x++;
				break;
			case("up"):
				y--;
				break;
			case("down"):
				y++;
				break;
		}
	}
}
