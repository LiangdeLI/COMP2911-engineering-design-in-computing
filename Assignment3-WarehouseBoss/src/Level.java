import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;

public class Level {
	
	private int width;
	private int height;

	public static final int PRESET = 0;
	public static final int EASY = 1;
	public static final int MEDIUM = 2;
	public static final int HARD = 3;
	
	//Pregen level definitions
	private String preGen =
			 "~~~~~~~~___;"
			+"~ *    ~___;"
			+"~ C C  ~~~~;"
			+"~         ~;"
			+"~  G      ~;"
			+"~      G  ~;"
			+"~  ~~~ ~~~~;"
			+"~~~~_~~~___;";
	
	//Easy level definitions
	private static final int EASY_WIDTH = 8;
	private static final int EASY_HEIGHT = 8;
	private static final int EASY_NUMGOALS = 2;
	
	//Medium level definitions
	private static final int NUM_DIRECTIONS = 4;
	private static final int MAX_PATH = 4;
	private static final int X_PATH = 0;
	private static final int Y_PATH = 1;
	private static final int RAND_DIR = 2;
	private static final int NOTHING = 999;

	//Hard level definitions
	private static final int HARD_MAX_PATH = 6;
	private static final int HARD_MIN_PATH = 3;



	public Level(){
		//TODO if necessary
	}
	
	/**
	 * Return the width of the level last generated
	 * 
	 * @return the width of the last level
	 */
	public int getWidth(){
		return width;
	}
	
	/**
	 * Return the height of the level last generated
	 * 
	 * @return the height of the last level
	 */
	public int getHeight(){
		return height;
	}
	
	/**
	 * Generate a level of the desired difficulty
	 * 
	 * @param difficulty - the difficulty of the level to be generated
	 * @return the generated level
	 */
	public char[][] genLevel(String difficulty){

		if(difficulty.equals("Easy")){
			char grid[][] = genEasy();
			return fixWalls(grid);
		}
		else if(difficulty.equals("Medium")){
			char grid[][] = genMedium();
			return fixWalls(grid);
		}
		else if(difficulty.equals("Hard")){
			char grid[][] = genHard(4);
			return fixWalls(grid);
		}
		else { //Assume preset
			char grid[][] = stringToGrid(preGen);
			return fixWalls(grid);
		}

	}
	
	/**
	 * Convert a string to a grid representation of the string
	 * 
	 * @param level - the string to convert to a grid
	 * @return the grid representation of the level string
	 */
	private char[][] stringToGrid(String level){
		
		String[] rows = level.split(";");
	    width = rows[0].length();
	    height = rows.length;
	    char[][] matrix = new char[height][width]; 
	    int r = 0;
	    for (String row : rows) {
	        matrix[r++] = row.toCharArray();
	    }
	    
	    //Mirror the matrix so that it draws correctly
	    char[][] grid = new char[width][height];
	    for(int y = 0; y < matrix.length; y++){
	    	for(int x = 0; x < matrix[y].length; x++){
		    	grid[x][y] = matrix[y][x];
		    }
	    }
	    return grid;
	}

	/**
	 * Generate an easy level which is guaranteed to be solvable
	 *
	 * @return the grid representation of the level
	 */
	private char[][] genEasy(){
		
		Random rn = new Random();

		width = rn.nextInt(4) + 6;
		height = rn.nextInt(4) + 6;
		char[][] grid = new char[width][height];
		
		//Generate square borders
		for(int x = 0; x < width; x++){
			for(int y = 0; y < height; y++){
				if(x == 0 || x == width - 1){
					grid[x][y] = '~';
				}
				else if(y == 0 || y == height - 1){
					grid[x][y] = '~';
				}
				else{
					grid[x][y] = ' ';
				}
			}
		}
		
		//Generate some potential grid positions
		//Each time a position is taken by a tile, it's removed from the list
		ArrayList<GridPosition> gridPositions = new ArrayList<GridPosition>();
		for(int x = 2; x < width - 2; x++){
			for(int y = 2; y < height - 2; y++){
				gridPositions.add(new GridPosition(x,y));
			}
		}
		
		//Get some crate positions on the grid
		//Can't be placed next to a wall (guarantees solvable)
		//Assume that there are an equal number of crates and goals
		for(int numCrates = 0; numCrates < EASY_NUMGOALS; numCrates++){
			int index = rn.nextInt(gridPositions.size()-1);
			GridPosition gp = gridPositions.remove(index);
			grid[gp.getX()][gp.getY()] = 'C';
		}
		
		//Goal tiles can be next to walls
		//So include wall positions in the array
		for(int x = 1; x < width - 2; x++){
			for(int y = 1; y < height - 2; y++){
				if(x == 1 || x == width - 2 || y == 1 || y == height - 2){
					gridPositions.add(new GridPosition(x,y));
				}
			}
		}
		
		//Get some goal positions on the grid
		for(int numGoals = 0; numGoals < EASY_NUMGOALS; numGoals++){
			int index = rn.nextInt(gridPositions.size()-1);
			GridPosition gp = gridPositions.remove(index);
			grid[gp.getX()][gp.getY()] = 'G';
		}
		
		//Get the player start position
		int index = rn.nextInt(gridPositions.size()-1);
		GridPosition gp = gridPositions.remove(index);
		grid[gp.getX()][gp.getY()] = '*';

		return grid;
	}
	
	private char[][] genMedium(){
		
		int x = 0;
		int y = 0;
		int numPoints = 0;
		
		//Move x or y first
		Random rn = new Random();
		int nextPath = rn.nextInt(RAND_DIR);
		
		//Randomize which x direction we want to move in
		int xDir;
		if(rn.nextInt(RAND_DIR) == 1){
			xDir = 1;
		}
		else {
			xDir = -1;
		}
		
		ArrayList<GridPosition> gridList = new ArrayList<GridPosition>();
		
		gridList.add(new GridPosition(0, 0));
		
		ArrayList<PushPoint> pushPoints = new ArrayList<PushPoint>();
		
		while(numPoints < NUM_DIRECTIONS){
			if(nextPath == X_PATH){
				
				//Set next path to y path
				nextPath = Y_PATH;
				//Increment the number of push points
				numPoints++;
				
				//Add a push point to the gridList
				gridList.add(new GridPosition(x-xDir, y));
				pushPoints.add(new PushPoint(x-xDir, y, PushPoint.VERTICAL));

				//Create a path of length between 1 and MAX_PATH
				int pathLength = rn.nextInt(MAX_PATH) + 1;
				for(int currLength = 0; currLength < pathLength; currLength++){
					x += xDir;
					gridList.add(new GridPosition(x, y));
					currLength++;
				}
			}
			else {
				
				//Set next path to x path
				nextPath = X_PATH;
				//Increment the number of push points
				numPoints++;
				
				//Randomize y path to be either up or down
				int mul;
				if(rn.nextInt(RAND_DIR) == 1){
					mul = 1;
				}
				else {
					mul = -1;
				}
				
				//Add a push point to the gridList
				gridList.add(new GridPosition(x, y - mul));
				pushPoints.add(new PushPoint(x, y - mul, PushPoint.HORIZONTAL));

				//Create a path of length between 1 and MAX_PATH
				int pathLength = rn.nextInt(MAX_PATH) + 1;
				for(int currLength = 0; currLength < pathLength; currLength++){
					y += mul;
					gridList.add(new GridPosition(x, y));
				}
			}
		}

		PushPoint playerStart = pushPoints.get(0);
		GridPosition crateStart = gridList.get(0);
		GridPosition crateEnd = gridList.get(gridList.size()-1);
		
		//Add new GridPositions to allow PushPoints to be reached
		for(PushPoint p: pushPoints){
			int pX = p.getX();
			int pY = p.getY();
			boolean canReach = false;
			for(GridPosition g: gridList){
				int gX = g.getX();
				int gY = g.getY();
				
				if(p.isType(PushPoint.HORIZONTAL)){
					if((pX-1 == gX && pY == gY) || (pX + 1 == gX && pY == gY) ){
						canReach = true;
						break;
					}
				}
				else {
					if((pX == gX && pY-1 == gY) || (pX == gX && pY + 1 == gY)){
						canReach = true;
						break;
					}
				}
			}
			if(!canReach){
				if(p.isType(PushPoint.HORIZONTAL)){
					gridList.add(new GridPosition(pX - 1, pY));
					gridList.add(new GridPosition(pX + 1, pY));
				}
				else{
					gridList.add(new GridPosition(pX, pY - 1));
					gridList.add(new GridPosition(pX, pY + 1));
				}
			}
		}
		

		//Calculate the max and min values
		int xMax = NOTHING;
		int xMin = NOTHING;
		int yMax = NOTHING;
		int yMin = NOTHING;

		for(GridPosition gp: gridList){
			
			int currX = gp.getX();
			int currY = gp.getY();
			
			if(xMax == NOTHING){
				xMax = currX;
				xMin = currX;
			}
			if(yMax == NOTHING){
				yMax = currY;
				yMin = currY;
			}
			if(yMax < currY){
				yMax = currY;
			}
			if(xMax < currX){
				xMax = currX;
			}
			if(yMin > currY){
				yMin = currY;
			}
			if(xMin > currX){
				xMin = currX;
			}
		}
		
		//Calculate grid width and height (with one space since counting from 1 and two extra spaces for walls)
		width = xMax - xMin + 1 + 2;
		height = yMax - yMin + 1 + 2;
		
		//Create a new grid and fill it with empty spaces
		char grid[][] = new char[width][height];
		for (char[] row: grid){
			Arrays.fill(row, '_');
		}
		 		
		for(GridPosition gp: gridList){
			
			//Normalize the grid positions
			int gX = gp.getX() - xMin + 1;
			int gY = gp.getY() - yMin + 1;
			
			grid[gX][gY] = ' ';
			
			//Generate walls around the grid tile
			if(grid[gX+1][gY] != ' '){
				grid[gX+1][gY] = '~';
			}
			if(grid[gX-1][gY] != ' '){
				grid[gX-1][gY] = '~';
			}
			if(grid[gX][gY+1] != ' '){
				grid[gX][gY+1] = '~';
			}
			if(grid[gX][gY-1] != ' '){
				grid[gX][gY-1] = '~';
			}
			
			if(grid[gX+1][gY+1] != ' '){
				grid[gX+1][gY+1] = '~';
			}
			if(grid[gX-1][gY+1] != ' '){
				grid[gX-1][gY+1] = '~';
			}
			if(grid[gX+1][gY-1] != ' '){
				grid[gX+1][gY-1] = '~';
			}
			if(grid[gX-1][gY-1] != ' '){
				grid[gX-1][gY-1] = '~';
			}
		}
		
		grid[playerStart.getX() - xMin + 1][playerStart.getY() - yMin + 1] = '*';
		grid[crateStart.getX() - xMin + 1][crateStart.getY() - yMin + 1] = 'C';
		grid[crateEnd.getX() - xMin + 1][crateEnd.getY() - yMin + 1] = 'G';	


		return grid;
	}
	
	public char[][] genHard(int numCrates){
		int x = 0;
		int y = 0;
		int numPaths = 0;
		
		//Move x or y first
		Random rn = new Random();
		int currPath = rn.nextInt(RAND_DIR);
		
		ArrayList<GridPosition> gridList = new ArrayList<GridPosition>();
		ArrayList<GridPosition> goalList = new ArrayList<GridPosition>();
		ArrayList<GridPosition> crateList = new ArrayList<GridPosition>();
		ArrayList<GridPosition> pushPositions = new ArrayList<GridPosition>();
		
		gridList.add(new GridPosition(x, y));
		
		while(numPaths < numCrates){
			
			//Add a new goal tile
			goalList.add(new GridPosition(x, y));

			//Randomize path direction
			int mul;
			if(rn.nextInt(RAND_DIR) == 1){
				mul = 1;
			}
			else {
				mul = -1;
			}
			
			//Fix path conflict if one exists
			boolean conflict = true;
			for(int i = 1; i <= HARD_MAX_PATH; i++){
				for(GridPosition gp: gridList){
					if(currPath == X_PATH && gp.getX() == x + i*mul && gp.getY() == y){
						conflict = true;
						break;
					}
					else if(currPath == Y_PATH && gp.getX() == x && gp.getY() == y + i*mul){
						conflict = true;
						break;
					}
				}
			}
			//If there's a conflict in one direction, there won't be a conflict in the other
			//This property is guaranteed to hold for up to 4 paths
			//TODO - change max path lengths to ensure this property holds for up to 5 paths
			if(conflict){
				mul *= -1;
			}
			
			//List of gridpositions for this current path
			ArrayList<GridPosition> thisPath = new ArrayList<GridPosition>();

			//Create a path of length between HARD_MIN_PATH and HARD_MAX_PATH
			int pathLength = rn.nextInt(HARD_MAX_PATH - HARD_MIN_PATH) + HARD_MIN_PATH;
			for(int currLength = 0; currLength < pathLength; currLength++){
				if(currPath == X_PATH){
					x += mul;
				}
				else if(currPath == Y_PATH){
					y += mul;
				}
				thisPath.add(new GridPosition(x, y));
			}
			//The last position becomes a pushPosition
			GridPosition pushPosition = thisPath.remove(thisPath.size()-1);
			gridList.add(pushPosition);
			pushPositions.add(pushPosition);
			
			//The second last (but now last) position becomes a crate position
			GridPosition cratePosition = thisPath.remove(thisPath.size()-1);
			gridList.add(cratePosition);
			crateList.add(cratePosition);
			
			//Get a random x and y position from the remaining grid positions
			int randIndex = rn.nextInt(thisPath.size());
			GridPosition nextPosition = thisPath.remove(randIndex);
			gridList.add(nextPosition);
			x = nextPosition.getX();
			y = nextPosition.getY();
			
			//Add the remaining positions to gridList
			gridList.addAll(thisPath);

			//Swap path axis
			if(currPath == X_PATH){
				currPath = Y_PATH;
			}
			else if(currPath == Y_PATH){
				currPath = X_PATH;
			}
			
			numPaths++;
		}
		
		//Calculate the max and min values
		int xMax = NOTHING;
		int xMin = NOTHING;
		int yMax = NOTHING;
		int yMin = NOTHING;

		for(GridPosition gp: gridList){
			
			int currX = gp.getX();
			int currY = gp.getY();
			
			if(xMax == NOTHING){
				xMax = currX;
				xMin = currX;
			}
			if(yMax == NOTHING){
				yMax = currY;
				yMin = currY;
			}
			if(yMax < currY){
				yMax = currY;
			}
			if(xMax < currX){
				xMax = currX;
			}
			if(yMin > currY){
				yMin = currY;
			}
			if(xMin > currX){
				xMin = currX;
			}
		}	
		
		//Calculate grid width and height (with one space since counting from 1 and two extra spaces for walls)
		width = xMax - xMin + 1 + 2;
		height = yMax - yMin + 1 + 2;
		
		//Create a new grid and fill it with empty spaces
		char grid[][] = new char[width][height];
		for (char[] row: grid){
			Arrays.fill(row, '_');
		}
		
		//Fill the grid with movable positions
		for(GridPosition gp: gridList){
			//Normalize the grid positions
			int gX = gp.getX() - xMin + 1;
			int gY = gp.getY() - yMin + 1;
			grid[gX][gY] = ' ';
		}
		
		//Fill the grid with crates
		for(GridPosition crate: crateList){
			
			//Normalize the c positions
			int gX = crate.getX() - xMin + 1;
			int gY = crate.getY() - yMin + 1;
			
			grid[gX][gY] = 'C';
		}
		
		//Fill the grid with goals
		for(GridPosition goal: goalList){
			
			//Normalize the c positions
			int gX = goal.getX() - xMin + 1;
			int gY = goal.getY() - yMin + 1;
			
			grid[gX][gY] = 'G';
		}
		
		//Generate the player start position
		GridPosition player = pushPositions.get(pushPositions.size()-1);
		int gX = player.getX() - xMin + 1;
		int gY = player.getY() - yMin + 1;
		grid[gX][gY] = '*';

		//Generate a square to ensure reachability
		//Also generate walls around the square
		for(x = 0; x < width; x++){
			for(y = 0; y < height; y++){
				if (x == 0 || x == width-1 || y == 0 || y == height-1){
					grid[x][y] = '~';
				}
				else if ((x == 1 || x == width - 2 || y == 1 || y == height-2) && (grid[x][y] == '_' || grid[x][y] == '~')){
					grid[x][y] = ' ';
				}
				else if (grid[x][y] == '_'){
					grid[x][y] = '~';
				}
			}
		}
		
		//Search for disconnected push positions
		for(GridPosition pp: pushPositions){
			
			//Normalize the c positions
			int xPos = pp.getX() - xMin + 1;
			int yPos = pp.getY() - yMin + 1;
			
			if(grid[xPos+1][yPos] == 'C' && grid[xPos-1][yPos] == '~'){
				while(--xPos > 0){
					if(grid[xPos][yPos] == '_' || grid[xPos][yPos] == '~'){
						grid[xPos][yPos] = ' ';
					}
				}
			}
			else if(grid[xPos-1][yPos] == 'C' && grid[xPos+1][yPos] == '~'){
				while(++xPos < width - 1){
					if(grid[xPos][yPos] == '_' || grid[xPos][yPos] == '~'){
						grid[xPos][yPos] = ' ';
					}
				}
			}
			else if(grid[xPos][yPos+1] == 'C' && grid[xPos][yPos-1] == '~'){
				while(--yPos > 0){
					if(grid[xPos][yPos] == '_' || grid[xPos][yPos] == '~'){
						grid[xPos][yPos] = ' ';
					}
				}
			}
			else if(grid[xPos][yPos-1] == 'C' && grid[xPos][yPos+1] == '~'){
				while(++yPos < height - 1){
					if(grid[xPos][yPos] == '_' || grid[xPos][yPos] == '~'){
						grid[xPos][yPos] = ' ';
					}
				}
			}
		}
				
		/*
		//Search for disconnected push positions
		for(GridPosition pp: pushPositions){
			
			//Normalize the c positions
			int xPos = pp.getX() - xMin + 1;
			int yPos = pp.getY() - yMin + 1;
			
			if(grid[xPos+1][yPos] == 'C' && grid[xPos-1][yPos] == '_'){
				while(--xPos > 0){
					if(grid[xPos][yPos] == '_' || grid[xPos][yPos] == '~'){
						grid[xPos][yPos] = ' ';
					}
				}
			}
			else if(grid[xPos-1][yPos] == 'C' && grid[xPos+1][yPos] == '_'){
				while(++xPos < width - 1){
					if(grid[xPos][yPos] == '_' || grid[xPos][yPos] == '~'){
						grid[xPos][yPos] = ' ';
					}
				}
			}
			else if(grid[xPos][yPos+1] == 'C' && grid[xPos][yPos-1] == '_'){
				while(--yPos > 0){
					if(grid[xPos][yPos] == '_' || grid[xPos][yPos] == '~'){
						grid[xPos][yPos] = ' ';
					}
				}
			}
			else if(grid[xPos][yPos-1] == 'C' && grid[xPos][yPos+1] == '_'){
				while(++yPos < height - 1){
					if(grid[xPos][yPos] == '_' || grid[xPos][yPos] == '~'){
						grid[xPos][yPos] = ' ';
					}
				}
			}
		}
		*/	
				
		return grid;
	}
	
	
	/**
	 * Change wall tiles so that they represent the correct sprites
	 * 
	 * @param grid - the grid whose walls should be fixed
	 * @return the grid with walls changed to match their corresponding sprite
	 */
	public char[][] fixWalls(char[][] grid){
		
		for(int x = 0; x < width; x++){
			for(int y = 0; y < height; y++){
				if(grid[x][y] == '~') {
					if (x > 0 && x < width-1 && y > 0 && y < height-1) {
					    if ((grid[x-1][y] == '~' || grid[x-1][y] == '[' || grid[x-1][y] == 'L')
					    		&& (grid[x+1][y] == '~')
					    		&& grid[x][y-1] == '_') {
					    	//grid[x][y] grid remains top wall
						} else if ((grid[x+1][y] == ' ' || grid[x+1][y] == 'G' || grid[x+1][y] == 'C' || grid[x+1][y] == '*')
							&& (grid[x-1][y] == ' ' || grid[x-1][y] == 'G' || grid[x-1][y] == 'C' || grid[x-1][y] == '*')
							&& (grid[x][y-1] == 'u' || grid[x][y-1] == '~'|| grid[x][y-1] == 'm')  
							&& grid[x][y+1] == '~' ) {
							//grid[x][y] is inside middle block
							grid[x][y] = 'm';
						} else if ((grid[x+1][y] == ' ' || grid[x+1][y] == 'G' || grid[x+1][y] == 'C' || grid[x+1][y] == '*')
								&& (grid[x-1][y] == ' ' || grid[x-1][y] == 'G' || grid[x-1][y] == 'C' || grid[x-1][y] == '*')
								&& (grid[x][y-1] == ' ' || grid[x][y-1] == 'G' || grid[x][y-1] == 'C' || grid[x][y-1] == '*')  
								&& grid[x][y+1] == '~' 
								&& (grid[x+1][y+1] == ' ' || grid[x+1][y+1] == 'G' || grid[x+1][y+1] == 'C' || grid[x+1][y+1] == '*')
								&& (grid[x-1][y+1] == ' ' || grid[x-1][y+1] == 'G' || grid[x-1][y+1] == 'C' || grid[x-1][y+1] == '*')) {
							//grid[x][y] is inside top middle block
							grid[x][y] = 'u';
						} else if ((grid[x+1][y] == ' ' || grid[x+1][y] == 'G' || grid[x+1][y] == 'C' || grid[x+1][y] == '*')
								&& (grid[x-1][y] == ' ' || grid[x-1][y] == 'G' || grid[x-1][y] == 'C' || grid[x-1][y] == '*')
								&& (grid[x][y-1] == 'm'  || grid[x][y-1] == 'u')
								&& (grid[x][y+1] == ' ' || grid[x][y+1] == 'C' || grid[x][y+1] == 'G' || grid[x][y+1] == '*')) {
							//grid[x][y] is inside middle block
							grid[x][y] = 'd';
						} else if (grid[x+1][y] == '_' 
								&& (grid[x][y-1] == '~' || grid[x][y-1] == '>' || grid[x][y-1] == '(' || grid[x][y-1] == 'R') 
								&& (grid[x][y+1] == '~' || grid[x][y+1] == 'r')) {
							//grid[x][y] is right wall
							grid[x][y] = '>';
						} else if (grid[x-1][y] == '_' 
								&& (grid[x][y+1] == '~') 
								&& (grid[x][y-1] == '~' || grid[x][y-1] == 'L' || grid[x][y-1] == ']' || grid[x][y-1] == ')' || grid[x][y-1] == '<')) {
							//grid[x][y] is left wall
							grid[x][y] = '<';
						} else if (grid[x-1][y] == '_' 
								&& grid[x+1][y] == '~'
								&& grid[x][y+1] == '~') {
							//grid[x][y] is top left corner
							grid[x][y] = 'L';
						} else if (grid[x+1][y] == '_'
								&& grid[x][y+1] == '~') {
							//grid[x][y] is top right corner
							grid[x][y] = 'R';
						} else if ((grid[x+1][y] == '~')
								&& (grid[x][y-1] == '<' || grid[x][y-1] == 'R')
								&& grid[x-1][y] == '_') {
							//grid[x][y] is bottom right corner
							grid[x][y] = 'l';
						} else if ((grid[x-1][y] == '-' || grid[x-1][y] == '(')
								&& (grid[x][y-1] == '>' || grid[x][y-1] == 'L')) {
							//grid[x][y] is bottom right corner
							grid[x][y] = 'r';
						} else if ((grid[x-1][y] == ' ' || grid[x-1][y] == 'G' || grid[x-1][y] == 'C' || grid[x-1][y] == '*' || grid[x-1][y] == 'O')
								&& (grid[x+1][y] == '~')
								&& (grid[x][y+1] == '~')
								&& (grid[x][y-1] != '>')
								&& (grid[x+1][y+1] != '~')) {
							//grid[x][y] is inside right bottom corner
							if ((grid[x+1][y-1] == '~' && grid [x+1][y] == '~' && grid[x+1][y+1] != '_')
									|| (grid[x-1][y+1] == '-' && grid[x+1][y+1] != '_' || grid[x-1][y+1] == 'l' && grid[x+1][y+1] != '_')) {
								grid[x][y] = 'O';
							} else {
								grid[x][y] = '(';
							}
						} else if ((grid[x-1][y] == '~' || grid[x-1][y] == '(' || grid[x-1][y] == 'l' || grid[x-1][y] == '-')
								&& (grid[x+1][y] == '~')
								&& (grid[x][y+1] == '_')) {
							//grid[x][y] is bottom wall
							grid[x][y] = '-';
						} else if ((grid[x-1][y] == '~' || grid[x-1][y] == 'L')
								&& (grid[x][y-1] == '<' || grid[x][y-1] == 'L')) {
							//grid[x][y] is top left inside corner
							grid[x][y] = ']';
						} else if ((grid[x-1][y] == '~' || grid[x-1][y] == '-' || grid[x-1][y] == 'l')
								&& (grid[x][y+1] == '~')) {
							//grid[x][y] is bottom left inside corner
							grid[x][y] = ')';
						} else if ((grid[x+1][y] == '~')
								&& (grid[x-1][y] == ' ' || grid[x-1][y] == 'O' || grid[x-1][y] == 'G' || grid[x-1][y] == 'C' || grid[x-1][y] == '*')
								&& (grid[x][y-1] == '>' || grid[x][y-1] == 'R')) {
							//grid[x][y] is top  right inside corner
							grid[x][y] = '[';
						} else if ((grid[x-1][y] == 'L' || grid[x-1][y] == '~' || grid[x-1][y] == '[')
								&& (grid[x+1][y] == '~')) {
							//grid[x][y] is top wall
							grid[x][y] = '~';
						} else if ((grid[x-1][y] == ']' || grid[x-1][y] == '<') 
								|| (grid[x+1][y] == '~')) {
							//grid[x][y] is obstacle
							grid[x][y] = 'O';
						} else {
							//grid[x][y] is obstacle
							grid[x][y] = 'O';
						}
					}
					else if (x == 0 || grid[x-1][y] == '_'){  //grid[x][y] is a type of left wall
						//grid[x][y] is a top left wall
						if(y == 0 || grid[x][y-1] == '_'){
							grid[x][y] = 'L';
						}
						//grid[x][y] is a bottom left wall
						else if(y == height - 1 || grid[x][y+1] == '_'){
							grid[x][y] = 'l';
						}
						//grid[x][y] is a regular left wall
						else{
							grid[x][y] = '<';
						}
					}
					//grid[x][y] is a type of right wall
					else if(x == width-1 || grid[x+1][y] == '_'){
						//grid[x][y] is a top right wall
						if(y == 0 || grid[x][y-1] == '_'){
							grid[x][y] = 'R';
						}
						//grid[x][y] is a bottom right wall
						else if(y == height - 1 || grid[x][y+1] == '_'){
							grid[x][y] = 'r';
						}
						//grid[x][y] is a regular right wall
						else{
							grid[x][y] = '>';
						}
					}
					//grid[x][y] is a bottom wall
					else if(y == height-1 || grid[x][y+1] == '_'){
						grid[x][y] = '-';
					}
					//grid[x][y] is a top wall
					else if(y == 0 || grid[x][y-1] == '_'){
						grid[x][y] = '~';
					}
					//grid[x][y] is a type of corner
					else {
						//grid[x][y] is a top corner
						if(grid[x][y-1] == '*' || grid[x][y-1] == 'C' || grid[x][y-1] == 'G' || grid[x][y-1] == ' '){
							//grid[x][y] is a left corner
							if(grid[x-1][y] == '*' || grid[x-1][y] == 'C' || grid[x-1][y] == 'G' || grid[x-1][y] == ' '){
								grid[x][y] = '(';
							}
							//grid[x][y] is a right corner
							else{
								grid[x][y] = ')';
							}
						}
						//grid[x][y] is a bottom corner
						else {
							//grid[x][y] is a left corner
							if(grid[x-1][y] == '*' || grid[x-1][y] == 'C' || grid[x-1][y] == 'G' || grid[x-1][y] == ' '){
								grid[x][y] = '[';
							}
							//grid[x][y] is a right corner
							else{
								grid[x][y] = ']';
							}
						}
					}
				}
			}
		}
		
		return grid;
	}
	
	/**
	 * Simple class used for managing grid positions in an ArrayList
	 */
	public class GridPosition {
		
	    int x;
	    int y;

	    GridPosition(int x, int y){
	        this.x = x;
	        this.y = y;
	    }
	    
	    public int getX(){
	    	return x;
	    }
	    
	    public int getY(){
	    	return y;
	    }
	}
	
	public class PushPoint extends GridPosition {

		public static final int VERTICAL = 0;
		public static final int HORIZONTAL = 1;
		private int type;

		PushPoint(int x, int y, int type){
			super(x,y);
			this.type = type;
	    }
	    
	    public int getX(){
	    	return super.getX();
	    }
	    
	    public int getY(){
	    	return super.getY();
	    }
	    
	    public boolean isType(int type){
	    	return this.type == type;
	    }
	}
}
