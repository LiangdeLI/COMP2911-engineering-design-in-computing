/**
 * Path, the connection between two cities 
 * @author Liangde Li z5077896
 */
public class Path implements Cloneable{
    private City Src;
    private City Dest;
    private int cost;
	
    /**
     * Construct a new path between two cities
     * @param src the source city of the path
     * @param dest the destination city of the path
     * @param cost the cost of traveling between these two cities
     */
    public Path(City src, City dest, int cost)
    {
    	this.Src = src;
    	this.Dest = dest;
    	this.cost = cost;
    }
    
    /**
     * Get the source city of the path
     * @return the source city of the path
     */
    public City getSrc() {
		return Src;
	}

    /**
     * Get the destination city of the path
     * @return the destination city of the path
     */
	public City getDest() {
		return Dest;
	}

	/**
	 * Get the cost of the traveling in the path
	 * @return the cost of the path in int
	 */
	public int getCost() {
		return cost;
	}
	
	/**
	 * Check whether two paths are the same
	 * @param other
	 * @return
	 */
    public boolean equals(Path other)
    {
    	if(Src.getName().equals(other.getSrc().getName()) && Dest.getName().equals(other.getDest().getName()))
    	    return true;
    	else return false;
    }
    
    /**
     * Clone this path and return it
     * @return a clone path 
     */
    public Path clone()
    {
    	Path temp = new Path(Src, Dest, cost);
    	return temp;
    }
}
