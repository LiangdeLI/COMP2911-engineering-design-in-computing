import java.util.ArrayList;
import java.util.Iterator;
import java.util.PriorityQueue;

/**
 * A map that contains all cities
 * @author Liangde Li z5077896
 */
public class Graph {
    private ArrayList<City> cityList;

    /**
     * Get a list of all cities
     * @return the ArrayList type of city list
     */
	public ArrayList<City> getCityList() {
		return cityList;
	}

	/**
	 * Construct the graph
	 */
    public Graph ()
    {
    	cityList = new ArrayList<City>();
    }
    
    /**
     * Add a city into the graph
     * @param aName the name of the city to be added
     * @param unloadingCost the cost to unload in the city
     */
    public void addCity(String aName, String unloadingCost)
    {
    	City newCity = new City(aName, Integer.parseInt(unloadingCost));
    	cityList.add(newCity);
    }
    
    /**
     * Add path between two cities
     * @param aCity one of the cities to be connected
     * @param bCity one of the cities to be connected
     * @param cost the cost between traveling between these two cities
     */
    public void addPath(String aCity, String bCity, String cost)
    {
    	getCity(aCity).addPath(getCity(bCity), Integer.parseInt(cost));
    	getCity(bCity).addPath(getCity(aCity), Integer.parseInt(cost));
    }
    
    /**
     * Get the instance of the city with given name
     * @param aName the name of the city to be given
     * @return the city 
     */
    public City getCity(String aName)
    {
    	Iterator<City> iterator = cityList.iterator();
    	while(iterator.hasNext())
    	{
    		City element = iterator.next();
    		if(element.getName().equals(aName)) return element;
    	}
    	return null;
    }
    
    /**
     * Get the cost of traveling between two cities
     * @param src the source city
     * @param dest the destination city 
     * @return the cost to travel between these cities in int type
     */
    public int getCostBetween(String src, String dest)
    {
    	return getCity(src).getCostTo(getCity(dest));
    }
    
    /**
     * A* algorithm to find the optimal path to finish all jobs
     * @param jobList a list of jobs need to be finished
     */
    public void aStarSearch(ArrayList<Path> jobList)
    {
    	
    	PriorityQueue<State> OPEN = new PriorityQueue<State>();
    	PriorityQueue<State> CLOSED = new PriorityQueue<State>();
    	int numNodes = 0;

    	City startCity = getCity("Sydney");
    	State startState = new State(startCity, jobList, null, new ConnectHeuristic(), false);
    	OPEN.add(startState);
    	
    	while(!OPEN.isEmpty())
    	{
    		State element = OPEN.poll();
    		numNodes++;
    		CLOSED.add(element);
    		
    		if(element.getJobList().isEmpty()) //if success, all jobs completed
    		{
    			System.out.println(numNodes + " nodes expanded");
    			System.out.println("cost = " + element.getGCost());
    			element.printResult(false, true);
    			return;
    		}
    		
    		Iterator<Path> iterator = element.getCurr().getPathList().iterator();//get all path can go from current state
    		while (iterator.hasNext())
    		{
    			Path temp = iterator.next();
    			City next = temp.getDest();
    			ArrayList<Path> newJobList = element.getJobList();
    			boolean loading = false;
    			Iterator<Path> iteratorTempJob = newJobList.iterator();
    			while (iteratorTempJob.hasNext())
    			{	
    			    Path tempJob = iteratorTempJob.next();
    				if (tempJob.equals(temp))
    			    {	
    			    	loading = true;
    				    iteratorTempJob.remove();
    			    }
    			}
    			State newState = new State(next, newJobList, element, new ConnectHeuristic(), loading);
    			
    			State state1 = this.hasState(newState, CLOSED);
    			State state2 = this.hasState(newState, OPEN);
    			if (state1 != null)
    			{
    				if(newState.getGCost() <= state1.getGCost())
    				{
    					CLOSED.remove(state1);
    				    OPEN.add(newState);
    				}
    			}
    			else if (state2 != null)
    			{
    				if(newState.getGCost() < state2.getGCost())
    				{
    					OPEN.remove(state2);
    					OPEN.add(newState);
    				}
    			}
    			else if (state2 == null)
    			{
    				OPEN.add(newState);
    			}
    		}
    	}
    	System.out.println(numNodes + " nodes expanded");
    	System.out.println("No solution");
    }
    
    /**
     * Test whether there is a state, in the queue, which has the same current city with the city to be tested,
     * and contains all jobs need to be done for the input state
     * @param aState the state to be tested
     * @param queue the queue to be searched
     * @return the found state in the queue, null if there is none
     */
    public State hasState(State aState, PriorityQueue<State> queue)
    {
    	Iterator<State> iterator = queue.iterator();
    	while (iterator.hasNext())
    	{
    		State prevState = iterator.next();
    		if (prevState.getCurr().equals(aState.getCurr()) 
    				&& aState.getNumJob() == prevState.getNumJob() 
    				&& prevState.hasAllJobOf(aState)) return prevState; 
    	}
    	return null;
    }
}
