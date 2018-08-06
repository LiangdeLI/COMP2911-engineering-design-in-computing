import java.util.ArrayList;

/**
 * One concrete heuristic strategy
 * @author Liangde Li z5077896
 */
public class ConnectHeuristic implements Strategy{
    /**
     * Construct the ConnectHeuristic strategy
     */
	public ConnectHeuristic()
    {
	   
    }
	
	@Override
	/**
	 * Calculate the particular H-Cost for a state
	 * @param curr the current city of the state
	 * @param jobList the jobs to be down for the state
	 */
	public int calcHeuritic(City curr, ArrayList<Path> jobList) {
		int hCost = 0;
		for(Path tempPath1 : jobList)
		{
			boolean connectSrc = false;
			hCost += tempPath1.getCost();
			hCost += tempPath1.getDest().getUnloadingCost();
			for(Path tempPath2 : jobList)
			{
				if (tempPath1.getSrc().equals(tempPath2.getDest())) connectSrc = true;
			}
			if(tempPath1.getSrc().equals(curr)) connectSrc = true;
			if(!connectSrc) hCost += tempPath1.getSrc().getShortestPath();
		}
		return hCost;
	}

}
