import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

public class Game extends JPanel implements Runnable{

	private static final long serialVersionUID = 1L;
	private static final int TILE_SCALE = 50;
	private static final int PLAYER_STRENGTH = 1;

	private int playerID;
	
	private int xOffset;
	private int yOffset;
	private int xMove;
	private int yMove;

	//Key press definitions
	private boolean wPressed;
	private boolean aPressed;
	private boolean sPressed;
	private boolean dPressed;
	private int currPress;
	
	//Added
	private Thread thread;
	private boolean running;
	private int FPS = 60;
	private long targetTime = 1000/FPS;
	private int numGoals;
		
	private Warehouse wh;
	private Level lvl;
	private Player player;
	private SoundManager sm;
	private String difficulty;
	private char[][] level;
	
	
	public Game(String difficulty, int playerID) {
		this.difficulty = difficulty;
		this.playerID = playerID;
		initGame(difficulty, false, null);
		setBackground(Color.DARK_GRAY);
		System.out.println("New game created");
	}
	
	public Game(String difficulty, int playerID, char[][] alevel){
		this.difficulty = difficulty;
		this.playerID = playerID;
		initGame(difficulty, true, alevel);
		setBackground(Color.DARK_GRAY);
		System.out.println("New game created");
	}
	
	public char[][] getLevel(){
		return level;
	}
	
	public void addNotify () {
		System.out.println("Now in addNotify");
		super.addNotify();
		if (thread == null) {
			thread = new Thread(this);
			thread.start();
		}
		running = true;
	}
	
	private void initGame(String difficulty, boolean retry, char[][] aLevel){
		
		System.out.println("Initializing game");
		
		sm = new SoundManager();
		
		lvl = new Level();
		if (!retry){
			level = lvl.genLevel(difficulty);
		}
		else
		{
			level = aLevel;
		}
		

		wh = new Warehouse(level.length, level[0].length); 
		numGoals = 0;
		
		
		xOffset = (Application.WIDTH - (wh.getWidth()*TILE_SCALE))/2;
		yOffset = (Application.HEIGHT - (wh.getHeight()*TILE_SCALE))/2;
		xMove = 0;
		yMove = 0;
		

		for(int y = 0; y < wh.getHeight(); y++){
			for(int x = 0; x < wh.getWidth(); x++){
				Tile t = null;
				if (level[x][y] == '#'){
					t = new Tile("Wall", x, y);
				}
				else if (level[x][y] == ' '){
					t = new Tile("Ground", x, y);
				}
				else if (level[x][y] == '*'){
					t = new Tile("Start", x, y);
					player = new Player(x, y);
				}
				else if (level[x][y] == 'C') {
					t = new Tile("Ground", x, y);
					wh.addCrate(x, y);
				}
				else if (level[x][y] == '_'){
					t = new Tile("Void", x, y);
				}
				else if (level[x][y] == 'L'){
					t = new Tile("TopLeft", x, y);
				}
				else if (level[x][y] == 'R'){
					t = new Tile("TopRight", x, y);
				}
				else if (level[x][y] == 'l'){
					t = new Tile("BottomLeft", x, y);
				}
				else if (level[x][y] == 'r'){
					t = new Tile("BottomRight", x, y);
				}
				else if (level[x][y] == '>'){
					t = new Tile("RightWall", x, y);
				}
				else if (level[x][y] == '<'){
					t = new Tile("LeftWall", x, y);
				}
				else if (level[x][y] == '~'){
					t = new Tile("TopWall", x, y);
				}
				else if (level[x][y] == '-'){
					t = new Tile("BottomWall", x, y);
				}
				else if (level[x][y] == '('){
					t = new Tile("LeftBottomInsideCorner", x, y);
				}
				else if (level[x][y] == ')'){
					t = new Tile("RightBottomInsideCorner", x, y);
				}
				else if (level[x][y] == '['){
					t = new Tile("RightTopInsideCorner", x, y);
				}
				else if (level[x][y] == ']'){
					t = new Tile("LeftTopInsideCorner", x, y);
				}
				else if (level[x][y] == 'm'){
					t = new Tile("MiddleWallBlock", x, y);
				}
				else if (level[x][y] == 'u'){
					t = new Tile("TopMiddleWallBlock", x, y);
				}
				else if (level[x][y] == 'd'){
					t = new Tile("BottomMiddleWallBlock", x, y);
				}
				else if (level[x][y] == 'O') {
					t = new Tile ("Cone", x, y);
				}
				else if (level[x][y] == 'G') {
					t = new Tile ("Goal", x, y);
					numGoals++;
					//System.out.println("Detect one goal");
				}
				else{
					System.out.println("Null tile at " + x + " " + y);
				}
				//System.out.println("In the loop");
				wh.setTile(x, y, t);
			}
			running = false;

			
		}
		
		if (playerID == 1){				
		    addKeyListener(new KeyAdapter() {
			
			    public void keyReleased(KeyEvent e){
				    if(e.getKeyCode() == KeyEvent.VK_W){
					    wPressed = false;
					    if(currPress == KeyEvent.VK_W){
						    currPress = 0;
					    }
				    }
				    else if(e.getKeyCode() == KeyEvent.VK_A){
					    aPressed = false;
					    if(currPress == KeyEvent.VK_A){
						    currPress = 0;    
					    }
				    }
				    else if(e.getKeyCode() == KeyEvent.VK_S){
					    sPressed = false;
					    if(currPress == KeyEvent.VK_S){
						    currPress = 0;
					    }
				    }
				    else if(e.getKeyCode() == KeyEvent.VK_D){
					    dPressed = false;
					    if(currPress == KeyEvent.VK_D){
						    currPress = 0;
					    }
				    }
			    }
			
			    public void keyPressed(KeyEvent e){
				    if(e.getKeyCode() == KeyEvent.VK_W){
					    currPress = KeyEvent.VK_W;
					    wPressed = true;
				    }
				    else if(e.getKeyCode() == KeyEvent.VK_A){
					    currPress = KeyEvent.VK_A;
					    aPressed = true;
				    }
				    else if(e.getKeyCode() == KeyEvent.VK_S){
					    currPress = KeyEvent.VK_S;
					    sPressed = true;
				    }
				    else if(e.getKeyCode() == KeyEvent.VK_D){
					    currPress = KeyEvent.VK_D;
					    dPressed = true;
				    }
			    }
		    });
		}else{
			addKeyListener(new KeyAdapter() {
				
			    public void keyReleased(KeyEvent e){
				    if(e.getKeyCode() == KeyEvent.VK_UP){
					    wPressed = false;
					    if(currPress == KeyEvent.VK_UP){
						    currPress = 0;
					    }
				    }
				    else if(e.getKeyCode() == KeyEvent.VK_LEFT){
					    aPressed = false;
					    if(currPress == KeyEvent.VK_LEFT){
						    currPress = 0;    
					    }
				    }
				    else if(e.getKeyCode() == KeyEvent.VK_DOWN){
					    sPressed = false;
					    if(currPress == KeyEvent.VK_DOWN){
						    currPress = 0;
					    }
				    }
				    else if(e.getKeyCode() == KeyEvent.VK_RIGHT){
					    dPressed = false;
					    if(currPress == KeyEvent.VK_RIGHT){
						    currPress = 0;
					    }
				    }
			    }
			
			    public void keyPressed(KeyEvent e){
				    if(e.getKeyCode() == KeyEvent.VK_UP){
					    currPress = KeyEvent.VK_UP;
					    wPressed = true;
				    }
				    else if(e.getKeyCode() == KeyEvent.VK_LEFT){
					    currPress = KeyEvent.VK_LEFT;
					    aPressed = true;
				    }
				    else if(e.getKeyCode() == KeyEvent.VK_DOWN){
					    currPress = KeyEvent.VK_DOWN;
					    sPressed = true;
				    }
				    else if(e.getKeyCode() == KeyEvent.VK_RIGHT){
					    currPress = KeyEvent.VK_RIGHT;
					    dPressed = true;
				    }
			    }
		    });
		}
        setFocusable(true);
	}
	

	public void paintComponent(Graphics g){
		
		super.paintComponent(g);
		
		BufferedImage img = null;
			
		//Draw the floor tiles
		for(int y = 0; y < wh.getHeight(); y++){
			for(int x = 0; x < wh.getWidth(); x++){
				Tile t = wh.getTile(x, y);
				img = t.getImage();
				if (img != null){
					g.drawImage(img, x*TILE_SCALE + xOffset, y*TILE_SCALE + yOffset, null);
				}
			}
		}
		
		// Crate painting. 
		ArrayList <Crate> allCrates = wh.getAllCrates();
		for (Crate c: allCrates) {
			img = c.getImage();
			int crateX = c.getX() * TILE_SCALE + xOffset + c.getMomentumX();
			int crateY = c.getY() * TILE_SCALE + yOffset + c.getMomentumY();
			g.drawImage(img, crateX, crateY, null);
		}
		
		
		//Draw the player
		img = player.getImage();
		int x = player.getX()*TILE_SCALE + xOffset + xMove;
		int y = player.getY()*TILE_SCALE + yOffset + yMove - 15;
		player.updateFrame(xMove, yMove);
		g.drawImage(img, x, y, null);
	}

	public void update() {
		
		//Check if keys have been pressed
		checkKeys();
		
		//Increment the SoundManager timer
		sm.tick();
		
		if(xMove < 0){
			xMove+= 2.5;
		}
		else if (xMove > 0){
			xMove-= 2.5;
		}
		
		if(yMove < 0){
			yMove+= 2.5;
		}
		else if (yMove > 0){
			yMove-= 2.5;
		}
		ArrayList <Crate> allCrates = wh.getAllCrates();
		for (Crate c: allCrates) {
			c.reduceMomentum();
		}
		
		int cratesOnGoal = 0;
		for (Crate c: allCrates) {
			int crateX = c.getX();
			int crateY = c.getY();			
			if (wh.getTile(c.getX(), c.getY()).isType("Goal") && c.getMomentumX() == 0 && c.getMomentumY() == 0) {
				cratesOnGoal++;
			}
		}
		
		
		if (cratesOnGoal == numGoals) {
			System.out.println("Player won.");
			sm.playVictory();
			JOptionPane.showMessageDialog(null, "You have won. Congratulations!");
			running = false;
			initGame(difficulty, false, null);
			yMove = 0;
			xMove = 0;
			currPress = 0;
			repaint();
			running = true;
			
		}
		
		
	}
	
	public void run() {
		System.out.println("Now in run");
		long start;
		long elapsed;
		long wait;
		
		while (running) {
			
			update();
			
			start = System.nanoTime();
			elapsed = System.nanoTime() - start;
			wait = targetTime - elapsed / 1000000;
			if (wait < 0) {
				wait = 5;
			}
			try {
				Thread.sleep(wait);
			}
			catch (Exception e)	{
				e.printStackTrace();
			}
			repaint ();
		}
		
	}
	
	/**
	 * Check if a crate can be pushed in a direction given player strength.
	 * Can't push if not enough strength or blocked by wall.
	 * @param target Crate to be checked
	 * @param playerStrength Int representing how many crates can be moved in one push
	 * @param direction String representing direction
	 * @return boolean representing whether moveable or not. 
	 */
	public boolean canPush (Crate target, int playerStrength, String direction) {
		boolean pushable = false;
		
		if (!wh.isWall(target.getX(), target.getY()) && playerStrength > 0) {
			Crate nextAdjCrate = null;
			switch (direction) {
			case "up":
				nextAdjCrate = wh.getCrate(target.getX(), target.getY()-1);
				// adjacent tile contains crate. check if that crate is pushable
				if (nextAdjCrate != null) {
					pushable = canPush (nextAdjCrate, playerStrength -1, "up");
				}
				// adjacent tile doesn't contain crate but contains wall.
				else if (nextAdjCrate == null && wh.isWall(target.getX(), target.getY() - 1)) {
					pushable = false;
				}
				// adjacent tile doesn't contain crate and doesn't contain wall. 
				else {
					pushable = true;
				}
				break;
			case "left":
				nextAdjCrate = wh.getCrate(target.getX() - 1, target.getY());
				
				if (nextAdjCrate != null) {
					pushable = canPush (nextAdjCrate, playerStrength - 1, "left");
				}
				else if (nextAdjCrate == null && wh.isWall(target.getX() -1, target.getY())) {
					pushable = false;
				}
				else {
					pushable = true;
				}
				
				break;
			case "down":
				nextAdjCrate = wh.getCrate(target.getX(), target.getY() + 1);
				
				if (nextAdjCrate != null) {
					pushable = canPush (nextAdjCrate, playerStrength - 1, "down");
				}
				else if (nextAdjCrate == null && wh.isWall(target.getX(), target.getY() + 1)){
					pushable = false;
				}
				else {
					pushable = true;
				}
				break;
			case "right":
				nextAdjCrate = wh.getCrate(target.getX() + 1, target.getY());
				if (nextAdjCrate != null) {
					pushable = canPush (nextAdjCrate, playerStrength - 1, "right");
				}
				else if (nextAdjCrate == null && wh.isWall(target.getX() + 1, target.getY())){
					pushable = false;
				}
				else {
					pushable = true;
				}
				break;
			
			}
		}
		return pushable;
	}
	
	/**
	 * Move a crate in a particular direction. 
	 * All crates in line also move. Caller responsibility to account for this.  
	 * @param target Crate to be moved
	 * @param direction String representing desired direction
	 */
	public void pushCrate (Crate target, String direction) {
	
		//Play the crate push sound
		sm.playPush();
		
		Crate nextAdjCrate = null;
		switch (direction) {
		case "up":
			nextAdjCrate = wh.getCrate(target.getX(), target.getY() - 1);
			target.move("up");
			target.gainMomentumY(TILE_SCALE);
			if (nextAdjCrate != null) {
				pushCrate (nextAdjCrate, "up");
			}
			break;
		case "left":
			nextAdjCrate = wh.getCrate(target.getX() - 1, target.getY());
			target.move("left");
			target.gainMomentumX(TILE_SCALE);
			if (nextAdjCrate != null) {
				pushCrate (nextAdjCrate, "left");
			}
			break;
		case "down":
			nextAdjCrate = wh.getCrate(target.getX(), target.getY() + 1);
			target.move("down");
			target.gainMomentumY(-TILE_SCALE);
			if (nextAdjCrate != null) {
				pushCrate (nextAdjCrate, "down");
			}
			break;
		case "right":
			nextAdjCrate = wh.getCrate(target.getX() + 1, target.getY());
			target.move("right");
			target.gainMomentumX(-TILE_SCALE);
			if (nextAdjCrate != null) {
				pushCrate (nextAdjCrate, "right");
			}
			break;
		}
	}
	
	/**
	 * See if a crate can be moved in any direction. 
	 * @param target Crate to check 
	 * @return boolean indicating whether crate is stuck
	 */
	public boolean isStuck (Crate target) {
		boolean stuckStatus = false;
		//can push cannot know if source direction is wall tile. must check this for each direction 
		
		boolean up = true;
		boolean down = true;
		boolean left = true;
		boolean right = true;
		
		//to do: crate in way of pushing but can manipulate that crate == !stuck
		
		// from above to below
		if (wh.isWall (target.getX(), target.getY() -1) || !canPush (target, PLAYER_STRENGTH, "down")) {
			down = false;
		}
		// from left to right
		if (wh.isWall(target.getX() -1, target.getY()) || !canPush (target, PLAYER_STRENGTH, "right")) {
			right = false;
		}
		// from right to left
		if (wh.isWall (target.getX() + 1, target.getY()) || !canPush (target, PLAYER_STRENGTH, "left")) {
			left = false;
		}
		// from below to above
		if (wh.isWall(target.getX(), target.getY() + 1) || !canPush(target, PLAYER_STRENGTH, "up")) {
			up = false;
		}
		
		if (!up && !down && !left && !right) {
			stuckStatus = true;
		}
		
		return stuckStatus;
	}
	
	public void checkKeys(){
		if(playerID == 1){
		    if(currPress == KeyEvent.VK_W){
			    if(wh.isCollision(player.getX(), player.getY(), "up")){
				    //Can't move up
				    //Play collision sfx if player is at a standstill
				    if(xMove == 0 && yMove == 0){
					    sm.playCollision();
				    }
			    }
			    //Ignore if in the process of moving
			    else if(xMove == 0 && yMove == 0){
				    Crate adjCrate = wh.getCrate(player.getX(), player.getY() - 1);
				    int playerStrength = PLAYER_STRENGTH;
				    if (adjCrate == null) {
					    player.move("up");
					    yMove = TILE_SCALE;
					    repaint();
				    }
				    else if (canPush (adjCrate, playerStrength, "up")) {
					    pushCrate (adjCrate, "up");
					    player.setPushing(true);
					    player.move("up");
					    yMove = TILE_SCALE;
					    repaint();
				    }
				    else{
					    //Can't push crate, play collision sfx
					    sm.playCollision();
				    }
			    }
		    }
		    else if(currPress == KeyEvent.VK_A){
			    if(wh.isCollision(player.getX(), player.getY(), "left")){
				    //Can't move left
				    //Play collision sfx if player is at a standstill
				    if(xMove == 0 && yMove == 0){
					    sm.playCollision();
				    }
			    }
			    //Ignore if in the process of moving
			    else if(xMove == 0 && yMove == 0){
				    Crate adjCrate = wh.getCrate(player.getX() -1, player.getY());
				    int playerStrength = PLAYER_STRENGTH;
				    if (adjCrate == null) {
					    player.move("left");
					    xMove = TILE_SCALE;
					    repaint();
				    }
				    else if (canPush (adjCrate, playerStrength, "left")) {
					    pushCrate (adjCrate, "left");
					    player.setPushing(true);
					    player.move("left");
					    xMove = TILE_SCALE;
					    repaint();
				    }
				    else{
					    //Can't push crate, play collision sfx
					    sm.playCollision();
				    }							
			    }
		    }
		    else if(currPress == KeyEvent.VK_S){
			    if(wh.isCollision(player.getX(), player.getY(), "down")){
				    //Can't move down
				    //Play collision sfx if player is at a standstill
				    if(xMove == 0 && yMove == 0){
					    sm.playCollision();
				    }
			    }
			    //Ignore if in the process of moving
			    else if(xMove == 0 && yMove == 0){
				    Crate adjCrate = wh.getCrate (player.getX(), player.getY() + 1);
				    int playerStrength = PLAYER_STRENGTH;
				    if (adjCrate == null) {
					    player.move("down");
					    yMove = -TILE_SCALE;
					    repaint();
				    }
				    else if (canPush (adjCrate, playerStrength, "down")) {
					    pushCrate (adjCrate, "down");
					    player.setPushing(true);
					    player.move("down");
					    yMove = -TILE_SCALE;
					    repaint();
				    }
				    else{
					    //Can't push crate, play collision sfx
					    sm.playCollision();
				    }							
			    };
		    }
		    else if(currPress == KeyEvent.VK_D){
			    if(wh.isCollision(player.getX(), player.getY(), "right")){
				    //Can't move right
				    //Play collision sfx if player is at a standstill
				    if(xMove == 0 && yMove == 0){
					    sm.playCollision();
				    }
			    }
			    //Ignore if in the process of moving
			    else if(xMove == 0 && yMove == 0){
				    Crate adjCrate = wh.getCrate(player.getX() + 1, player.getY());
				    int playerStrength = PLAYER_STRENGTH;
				    if (adjCrate == null) {
					    player.move("right");
					    xMove = -TILE_SCALE;
					    repaint();
				    }
				    else if (canPush (adjCrate, playerStrength, "right")) {
					    pushCrate (adjCrate, "right");
					    player.setPushing(true);
					    player.move("right");
					    xMove = -TILE_SCALE;
					    repaint();
				    }
				    else{
					    //Can't push crate, play collision sfx
					    sm.playCollision();
				    }
			    }
		    }
		}else{
			if(currPress == KeyEvent.VK_UP){
			    if(wh.isCollision(player.getX(), player.getY(), "up")){
				    //Can't move up
				    //Play collision sfx if player is at a standstill
				    if(xMove == 0 && yMove == 0){
					    sm.playCollision();
				    }
			    }
			    //Ignore if in the process of moving
			    else if(xMove == 0 && yMove == 0){
				    Crate adjCrate = wh.getCrate(player.getX(), player.getY() - 1);
				    int playerStrength = PLAYER_STRENGTH;
				    if (adjCrate == null) {
					    player.move("up");
					    yMove = TILE_SCALE;
					    repaint();
				    }
				    else if (canPush (adjCrate, playerStrength, "up")) {
					    pushCrate (adjCrate, "up");
					    player.setPushing(true);
					    player.move("up");
					    yMove = TILE_SCALE;
					    repaint();
				    }
				    else{
					    //Can't push crate, play collision sfx
					    sm.playCollision();
				    }
			    }
		    }
		    else if(currPress == KeyEvent.VK_LEFT){
			    if(wh.isCollision(player.getX(), player.getY(), "left")){
				    //Can't move left
				    //Play collision sfx if player is at a standstill
				    if(xMove == 0 && yMove == 0){
					    sm.playCollision();
				    }
			    }
			    //Ignore if in the process of moving
			    else if(xMove == 0 && yMove == 0){
				    Crate adjCrate = wh.getCrate(player.getX() -1, player.getY());
				    int playerStrength = PLAYER_STRENGTH;
				    if (adjCrate == null) {
					    player.move("left");
					    xMove = TILE_SCALE;
					    repaint();
				    }
				    else if (canPush (adjCrate, playerStrength, "left")) {
					    pushCrate (adjCrate, "left");
					    player.setPushing(true);
					    player.move("left");
					    xMove = TILE_SCALE;
					    repaint();
				    }
				    else{
					    //Can't push crate, play collision sfx
					    sm.playCollision();
				    }							
			    }
		    }
		    else if(currPress == KeyEvent.VK_DOWN){
			    if(wh.isCollision(player.getX(), player.getY(), "down")){
				    //Can't move down
				    //Play collision sfx if player is at a standstill
				    if(xMove == 0 && yMove == 0){
					    sm.playCollision();
				    }
			    }
			    //Ignore if in the process of moving
			    else if(xMove == 0 && yMove == 0){
				    Crate adjCrate = wh.getCrate (player.getX(), player.getY() + 1);
				    int playerStrength = PLAYER_STRENGTH;
				    if (adjCrate == null) {
					    player.move("down");
					    yMove = -TILE_SCALE;
					    repaint();
				    }
				    else if (canPush (adjCrate, playerStrength, "down")) {
					    pushCrate (adjCrate, "down");
					    player.setPushing(true);
					    player.move("down");
					    yMove = -TILE_SCALE;
					    repaint();
				    }
				    else{
					    //Can't push crate, play collision sfx
					    sm.playCollision();
				    }							
			    };
		    }
		    else if(currPress == KeyEvent.VK_RIGHT){
			    if(wh.isCollision(player.getX(), player.getY(), "right")){
				    //Can't move right
				    //Play collision sfx if player is at a standstill
				    if(xMove == 0 && yMove == 0){
					    sm.playCollision();
				    }
			    }
			    //Ignore if in the process of moving
			    else if(xMove == 0 && yMove == 0){
				    Crate adjCrate = wh.getCrate(player.getX() + 1, player.getY());
				    int playerStrength = PLAYER_STRENGTH;
				    if (adjCrate == null) {
					    player.move("right");
					    xMove = -TILE_SCALE;
					    repaint();
				    }
				    else if (canPush (adjCrate, playerStrength, "right")) {
					    pushCrate (adjCrate, "right");
					    player.setPushing(true);
					    player.move("right");
					    xMove = -TILE_SCALE;
					    repaint();
				    }
				    else{
					    //Can't push crate, play collision sfx
					    sm.playCollision();
				    }
			    }
		    }
		}
	}
	

}
