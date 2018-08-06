import java.util.*;

/**
 * The city with connection to other cities
 * @author Liangde Li z5077896
 */
public class City {
    private String name;
    private int unloadingCost;
    private ArrayList<Path> pathList;
    
    /**
     * Construct a city
     * @param aName the name of the city to be constructed
     * @param unloadingCost the time cost to unload in the city
     */
    public City (String aName, int unloadingCost)
    {
    	this.name = aName;
    	this.unloadingCost = unloadingCost;
    	this.pathList = new ArrayList<Path>();
    }
    
    /**
     * The cost to go to another city from this city
     * @param dest the destination city to be measured
     * @return the cost to destination city
     */
    public int getCostTo(City dest)
    {
    	for(Path element:pathList)
    	    if(element.getDest().equals(dest)) return element.getCost();
    	return -1;
    }
    
    /**
     * Check whether two cities are the same one
     * @param other The city to be compared with this
     * @return whether they are same
     */
    public boolean equals(City other)
    {
    	if(other.getName().equals(this.name)) return true;
    	else return false;
    }
    
    /**
     * Get the name of this city
     * @return the name of this city
     */
	public String getName() {
		return name;
	}

    /**
     * Get the list of path that the city has
     * @return an arraylist of the paths the city has
     */
	public ArrayList<Path> getPathList() {
		return pathList;
	}

	/**
	 * Get the cost of unloading in that city
	 * @return the integer of unloading time in that city
	 */
	public int getUnloadingCost() {
		return unloadingCost;
	}
	
	/**
	 * Add a path to the destination city with given cost
	 * @param dest the city will be connected to
	 * @param cost the cost to destination city
	 */
    public void addPath(City dest, int cost)
    {
    	Path newPath = new Path(this, dest, cost);
    	pathList.add(newPath);
    }

    /**
     * Get the cost of the shortest path of the city
     * @return the integer of the cost of the shortest path of the city
     */
    public int getShortestPath()
    {
    	int result = Integer.MAX_VALUE;
    	for(Path temp : pathList)
    	{
    		if (temp.getCost() < result) result = temp.getCost();
    	}
    	return result;
    }
}
