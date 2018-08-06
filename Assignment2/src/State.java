import java.util.ArrayList;

/**
 * A state store the information of the current city, to do job, G-Cost, H-Cost, previous state, the strategy
 * to be use to get H-Cost and whether the last path was loading
 * @author Liangde Li
 */
public class State implements Comparable<State>{
    private City curr;
    private ArrayList<Path> jobList;
    private int gCost;
    private int hCost;
    private State prev;
    private Strategy strategy;
    private boolean needUnloading;
    
    /**
     * Construct a new state
     * @param curr the current city of the state
     * @param jobList the job to be done in the state
     * @param prev the previous state
     * @param strategy the strategy to be applied to get H-Cost
     * @param needUnloading whether need unloading in current city
     */
    public State(City curr, ArrayList<Path> jobList, State prev, Strategy strategy, boolean needUnloading)
    {
    	this.curr = curr;
    	this.jobList = jobList;
    	if (prev == null) gCost = 0;
    	else 
    	{
    		this.gCost = prev.getGCost() + prev.getCurr().getCostTo(curr);
    	    if(needUnloading) gCost = gCost + curr.getUnloadingCost();
    	}
    	this.hCost = strategy.calcHeuritic(curr, jobList);
    	this.prev = prev;
    	this.strategy = strategy;
    	this.needUnloading = needUnloading;
    }
	
    /**
     * Get the current city of the state
     * @return the current city
     */
    public City getCurr() {
		return curr;
	}

    /**
     * Get a copy of the list of the jobs to be done
     * @return the copy of the todo jobs
     */
    public ArrayList<Path> getJobList()
    {
    	ArrayList<Path> temp = new ArrayList<Path>();
    	for(int i = 0; i < jobList.size(); i++)
    	{
    		temp.add(jobList.get(i).clone());
    	}
    	return temp;
    }
    
    /**
     * Get the G-Cost of the state
     * @return the G-Cost of the state
     */
	public int getGCost() {
		return gCost;
	}

	/**
	 * Get the H-Cost of the state
	 * @return the H-Cost of the state
	 */
	public int getHCost() {
		return hCost;
	}

	/**
	 * Get the F-Cost of this state, which is the sum of the G-Cost and H-Cost
	 * @return the F-Cost in int type
	 */
	public int getFCost(){
		return gCost + hCost;
	}
	
	@Override
	/**
	 * Make the state comparable 
	 * @param the other state to be compared
	 * @return whose subtraction of the F-Cost
	 */
	public int compareTo(State other) {
		return gCost+hCost-other.getFCost();
	}
    
	/**
	 * Print the result of the optimal solution
	 * @param loading whether is loading to next state
	 * @param last whether this state is the last state
	 */
	public void printResult(boolean loading, boolean last)
	{
		if(prev != null)
		{
//			System.out.print(prev.getCurr().getName() + " -> " + curr.getName());
//			if(needUnloading) System.out.println(" loading");
//			else System.out.println(" no loading");
			
			prev.printResult(needUnloading, false);
			System.out.println(curr.getName());
			if(last) return;
		}
		if(loading)
		{
			System.out.print("Job " + curr.getName() + " to ");
		}
		else
		{
			System.out.print("Empty " + curr.getName() + " to ");
		}
	}
	
	/**
	 * Check if all todo jobs in other state are in this state
	 * @param other the other state to be checked
	 * @return whether this state contains all jobs in other state
	 */
	public boolean hasAllJobOf(State other)
	{
		for(Path o:other.getJobList())
		{
			boolean found = false;
			for(Path p:jobList)
			{
				if(o.equals(p)) found = true;
			}
			if (!found) return false;
		}
		return true;
	}
	
	/**
	 * Get the number of the todo jobs
	 * @return the number of the todo jobs
	 */
	public int getNumJob()
	{
		return jobList.size();
	}
}
