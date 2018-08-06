import java.io.*;
import java.util.*;

/**
 * A system of Freight, to maintain everything
 * @author Liangde Li z5077896
 * Runtime analysis of heuristic funtion: the idea is based on trying to sum up all inevitable cost 
 * First, add up all traveling cost of the todo jobs, and add up all unloading cost in destination city
 * As the shortest way should be just connect all jobs head to tail, means just string them, but this is not gonna be true in most of cases
 * Then try to connect all jobs using the smallest cost.
 * For every source city of the job, if there is another job's destination city or current city of the state is exactly this source city, try to do them continuely,
 * means this source city has been "connected". Then try to find the shortest path from this source city, add its cost to the heristic cost,
 * assume this part is connected now.
 * Do the previous step for every job
 */
public class FreightSystem {
	private Graph graph;
	private ArrayList<Path> jobList;

	/**
	 * The main function to read input from file, and print out the solution
	 * @param args the command line words, including the filename in String type
	 */
	public static void main(String[] args) {
		FreightSystem system = new FreightSystem();
		Scanner sc = null;
		try
	    {
	        sc = new Scanner(new FileReader(args[0]));
	        while (sc.hasNextLine())
	        {
	        	String line = sc.nextLine();
	            String[] parts = line.split("\\s+");
	        	if(parts[0].equals("Unloading")) system.addCity(parts[2], parts[1]);
	        	else if(parts[0].equals("Cost")) system.addPath(parts[2], parts[3], parts[1]);
	        	else if(parts[0].equals("Job"))  system.addJob(parts[1], parts[2]);
	        }
	    }
	    catch (FileNotFoundException e) {}
	    finally
	    {
	        if (sc != null) sc.close();
	    }

        system.aStarSearch();  
	}
    
	/**
	 * Construct a FreightSystem
	 */
	public FreightSystem()
	{
		graph = new Graph();
		jobList = new ArrayList<Path>();
	}
	
	/**
	 * Add the information of the given city into the system
	 * @param aName the name of given city
	 * @param unloadingCost time cost in the given city to unload
	 */
	public void addCity(String aName, String unloadingCost)
	{
		graph.addCity(aName, unloadingCost);
	}
	
	/**
	 * Add path between two cities
	 * @param aCity one of the cities to be connected
	 * @param bCity one of the cities to be connected
	 * @param cost the time cost to travel between these two cities
	 */
	public void addPath(String aCity, String bCity, String cost)
	{
		graph.addPath(aCity, bCity, cost);
	}
	
	/**
	 * Add a to do job between two cities
	 * @param src the city job begins
	 * @param dest the city job ends
	 */
	public void addJob(String src, String dest)
	{
		Path newJob = new Path(graph.getCity(src), graph.getCity(dest), graph.getCostBetween(src, dest));
		jobList.add(newJob);
	}
	
	/**
	 * A* algorithm to find optimal solution
	 */
	public void aStarSearch()
	{
		graph.aStarSearch(jobList);
	}
}
