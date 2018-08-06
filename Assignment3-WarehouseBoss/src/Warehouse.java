import java.util.ArrayList;

public class Warehouse{
	
	/** horizontal width of warehouse */
	private int x;
	/** vertical width of warehouse */
	private int y;
	/** 2D array of immovable tiles */
	private Tile tiles[][];
	/** List of all crates in warehouse */
	private ArrayList <Crate> crates;
	
	/**
	 * Constructor which creates a new warehouse
	 * with dimensions specified by params
	 * x and y and an arrays to hold the
	 * tiles
	 *
	 * @param x - the width of the warehouse
	 * @param y - the height of the warehouse
	*/
	public Warehouse(int x, int y){
		this.x = x;
		this.y = y;
		tiles = new Tile[x][y];
		crates = new ArrayList <Crate> ();
	}

	
	/**
	 * Return the width of the warehouse
	 *
	 * @return the width of the warehouse
	*/
	public int getWidth(){
		return x;
	}

	/**
	 * Return the height of the warehouse
	 *
	 * @return the height of the warehouse
	*/
	public int getHeight(){
		return y;
	}
	
	/**
	 * Return the height of the warehouse
	 *
	 * @return the height of the warehouse
	*/
	public boolean isCollision(int x, int y, String direction){
		switch(direction){
		case("left"):
			//return tiles[--x][y].isType("LeftWall");
			return isWall (--x, y);
		case("right"):
			//return tiles[++x][y].isType("RightWall");
			return isWall (++x, y);
		case("up"):
			//return tiles[x][--y].isType("TopWall");
			return isWall (x, --y);
		case("down"):
			//return tiles[x][++y].isType("BottomWall");
			return isWall (x, ++y);
		}
		return false;
	}
	
	/**
	 * Return whether a tile is empty
	 *
	 * @param x - the x position to check
	 * @param y - the y position to check
	*/
	public boolean isEmpty(int x, int y){
		return (tiles[x][y].isType("Ground"));
	}	
	
	/**
	 * Set the tile at the specified position
	 * 
	 * @param x - the x position to set
	 * @param y - the y position to set
	 * @param e - the tile to add to the position
	*/
	public void setTile(int x, int y, Tile t){
		tiles[x][y] = t;
	}
	
	/**
	 * Return the tile at the specified position
	 *
	 * @param x - the x position of the element
	 * @param y - the y position of the element
	 * @return the tile at the specified position
	*/
	public Tile getTile(int x, int y){
		return tiles[x][y];
	}
	
	/**
	 * Add a crate at (x,y) position
	 * @param x int representing x position of new crate
	 * @param y int representing y position of new crate
	 */
	public void addCrate (int x, int y) {
		crates.add(new Crate (x, y));
	}
	
	/**
	 * Get the crate at (x,y).
	 * Null if no crate to double as check for crate.
	 * @param x int representing x position of crate
	 * @param y int representing y position of crate
	 * @return Crate || null
	 */
	public Crate getCrate (int x, int y) {
		Crate c = null;
		for (Crate temp: crates) {
			if (temp.getX() == x && temp.getY() == y) {
				c = temp;
				break;
			}
		}
		
		return c;
	}
	
	/**
	 * Get list of all crates
	 * @return ArrayList of all crates
	 */
	public ArrayList <Crate> getAllCrates () {
		return crates;
	}
	
	
	// Update isWall for any new tiles !wall added. 
	/**
	 * Check whether tile at (x,y) is wall. 
	 * @param x int representing x position of tile to check 
	 * @param y int representing y position of tile to check
	 * @return boolean representing whether wall. 
	 */
	public boolean isWall (int x, int y) {
		boolean foundWall = false;
		String type = getTile(x, y).getType();
		// simpler to prove wall. 
		if (!type.equals("Ground") && !type.equals("Goal") && !type.equals("Start")) {
			foundWall = true;
		}
		
		return foundWall;
		
	}
}	
