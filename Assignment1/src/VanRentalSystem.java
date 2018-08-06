import java.io.*;
import java.util.*;

/**
 * A system of all bookings and depots, for users to maintain
 * @author Liangde Li z5077896
 */
public class VanRentalSystem {
	private ArrayList<Booking> bookingList;
    private ArrayList<Depot> depotList;
	
	/**
	 * The main function to read input from file, and print out the information requested
	 * @param args the command line words, including the filename in String type
	 */
	public static void main(String[] args) {
		Scanner sc = null;
		VanRentalSystem system = new VanRentalSystem();
	        try
	        {
	            sc = new Scanner(new FileReader(args[0])); //  
	            
	            while (sc.hasNextLine())
	            {
	        	    String line = sc.nextLine();
	        	    String[] parts = line.split("\\s+");
                    if(parts[0].equals("Location"))
                    {
                	    system.addCampervan(parts[1], parts[2], parts[3]);
                    }
                    else if(parts[0].equals("Request"))
                    {
                	    system.makeBooking(parts, false);
                    }
                    else if(parts[0].equals("Cancel"))
                    {
                    	system.cancelBooking(parts[1], false);
                    }
                    else if(parts[0].equals("Change"))
                    {
                	    system.changeBooking(parts);
                    }
                    else if(parts[0].equals("Print"))
                    {
                	    system.print(parts[1]);
                    }
                    else if(parts[0].equals("#"))
                    {
                	    continue;
                    }
	            }
	        }
	        catch (FileNotFoundException e) { } //System.out.println("runninghere");}
	        finally
	        {
	            if (sc != null) sc.close();
	        }

	}
    
	/**
	 * Constructs a VanRentalSystem
	 */
	public VanRentalSystem()
	{
		bookingList = new ArrayList<Booking>();
		depotList = new ArrayList<Depot>();
	}
	
	/**
	 * Add the information of a given campervan into system
	 * @param aDepot the name of the depot that campervan belongs to 
	 * @param aVan the name of the campervan
	 * @param isAuto type of the campervan
	 * @pre the given names of depot and van are not null
	 * @post the campervan exist now
	 */
	public void addCampervan(String aDepot, String aVan, String isAuto)
	{
		Iterator<Depot> iterator = depotList.iterator();
		while(iterator.hasNext())
		{
			Depot element = iterator.next();
			if(element.getName().equals(aDepot))
			{
				element.addCampervan(aVan, isAuto);
				return;
			}
		}
		depotList.add(new Depot(aDepot));
		depotList.get(depotList.size()-1).addCampervan(aVan, isAuto);
	}
	
	/**
	 * Make a new booking
	 * @param parts all the information read from related line in input file
	 * @param isChanging whether this is booking for "Change", or a new "Request"
	 * @pre the given information of booking is valid, valid time i.e. start time is before end time 
	 *      and the amount of campervans needed are not negative
	 * @post the booking exist in bookingList and corresponding campervans' schedule
	 */
	public void makeBooking(String[] parts, Boolean isChanging)
	{
		int length = parts.length; //get the length of request to distinguish number of types of vans needed
		int numMan = 0; 
		int numAuto = 0;
		
		//get numMan and numAuto
		if (length == 12) //request two types of campervan
		{
			if (parts[9].equals("Manual")) numMan = Integer.parseInt(parts[8]);
			else if (parts[9].equals("Automatic")) numAuto = Integer.parseInt(parts[8]);
			
			if (parts[11].equals("Manual")) numMan = Integer.parseInt(parts[10]);
			else if (parts[11].equals("Automatic")) numAuto = Integer.parseInt(parts[10]);
		}
		else if (length == 10) //request one type of campervan
		{
			if (parts[9].equals("Manual")) numMan = Integer.parseInt(parts[8]);
			else if (parts[9].equals("Automatic")) numAuto = Integer.parseInt(parts[8]);
		}
		
		Booking newBooking = new Booking(parts[1], parts[2], parts[3], parts[4], parts[5], parts[6], parts[7], numMan, numAuto);
		
		if(!this.bookAble(newBooking, -1))
		{
			System.out.println("Booking rejected");
			return;
		}
		else
		{
			bookingList.add(newBooking);
			if(!isChanging) System.out.print("Booking " + parts[1] + " ");
		}
		
		int manNeed = newBooking.getNumManual(); // Manual vans needed
		int autoNeed = newBooking.getNumAutomatic(); // Automatic vans needed
		
		//go through each depot to find campervans satisfy request
		Iterator<Depot> iterator = depotList.iterator();
		while(iterator.hasNext())
		{
			Depot element = iterator.next();
			int manAble = element.manAble(newBooking, -1);
			int autoAble = element.autoAble(newBooking, -1);
			if((manNeed > 0 && manAble > 0) || (autoNeed > 0 && autoAble > 0))
			{
				System.out.print(element.getName() + " ");
				
				int manBook = 0;
				int autoBook = 0;
				if (manNeed <= manAble) manBook = manNeed;
				else manBook = manAble;
				
				if (autoNeed <= autoAble) autoBook = autoNeed;
				else autoBook = autoAble;
			    
				element.makeBooking(newBooking, manBook, autoBook);
			    manNeed = manNeed - manBook;
			    autoNeed = autoNeed - autoBook;
			    if (manNeed + autoNeed == 0) System.out.println();
			    else System.out.print("; ");
			}
		}
	}
	
	/**
	 * Analyse whether this booing is available, or need to be rejected
	 * @param aBooking the booking to be analysed
	 * @param ignoreID original booking ID, when analyse "Change" booking, ignore the influence of original booking
	 * @return whether this booking is available
	 * @pre analysed booking has valid time, start time is before end time
	 * @post just analysis, no physical action been taken
	 */
	public Boolean bookAble(Booking aBooking, int ignoreID)
	{
		Iterator<Depot> iterator = depotList.iterator();
		int manNeed = aBooking.getNumManual(); // Manual vans needed
		int autoNeed = aBooking.getNumAutomatic(); // Automatic vans needed
		while(iterator.hasNext())
		{
			Depot element = iterator.next();
			manNeed = manNeed - element.manAble(aBooking, ignoreID);
			autoNeed = autoNeed - element.autoAble(aBooking, ignoreID);
			if (manNeed <= 0 && autoNeed <= 0) return true;
		}
		
		return false;
	}
	
	/**
	 * Cancel the booking
	 * @param id the ID of the booking to be canceled
	 * @param isChanging whether is canceling the original booking for "Change" request
	 * @post the booking been canceled not exist anywhere in system
	 */
	public void cancelBooking(String id, Boolean isChanging)
	{
		Boolean found = false;
		Iterator<Booking> iteratorB1 = bookingList.iterator();
		while(iteratorB1.hasNext())
		{
			Booking elementB1 = iteratorB1.next();
			if (elementB1.getID() == Integer.parseInt(id)) found = true;
		}
		if (!found) 
		{
			System.out.println("Cancel rejected");
			return;
		}
		Iterator<Depot> iteratorD = depotList.iterator();
		while(iteratorD.hasNext())
		{
			Depot elementD = iteratorD.next();
		    elementD.cancelBooking(id);
		}
		
		Iterator<Booking> iteratorB2 = bookingList.iterator();
		while(iteratorB2.hasNext())
		{
			Booking elementB = iteratorB2.next();
			if (elementB.getID() == Integer.parseInt(id))
    	    {
    	    	iteratorB2.remove();
    	    	break;
    	    }
		}
		
		if(!isChanging) System.out.println("Cancel " + id);
	}
	
	/**
	 * Try to change one original booking
	 * @param parts the detail of booking hoped to be finally modified to
	 * @pre the booking to be changed do exist before, start time is before end time
	 * @post the modified booking has the exactly same ID as before
	 */
	public void changeBooking(String[] parts)
	{
		int length = parts.length; //get the length of request to distinguish number of types of vans needed
		int numMan = 0; 
		int numAuto = 0;
		
		//get numMan and numAuto
		if (length == 12) //request two types of campervan
		{
			if (parts[9].equals("Manual")) numMan = Integer.parseInt(parts[8]);
			else if (parts[9].equals("Automatic")) numAuto = Integer.parseInt(parts[8]);
			
			if (parts[11].equals("Manual")) numMan = Integer.parseInt(parts[10]);
			else if (parts[11].equals("Automatic")) numAuto = Integer.parseInt(parts[10]);
		}
		else if (length == 10) //request one type of campervan
		{
			if (parts[9].equals("Manual")) numMan = Integer.parseInt(parts[8]);
			else if (parts[9].equals("Automatic")) numAuto = Integer.parseInt(parts[8]);
		}
		
		Booking newBooking = new Booking(parts[1], parts[2], parts[3], parts[4], parts[5], parts[6], parts[7], numMan, numAuto);
		
		if(!this.bookAble(newBooking, Integer.parseInt(parts[1])))
		{
			System.out.println("Change rejected");
			return;
		}
		else
		{
			this.cancelBooking(parts[1], true); 
			bookingList.add(newBooking);
			System.out.print("Change " + parts[1] + " ");
		}
		this.makeBooking(parts, true);
	}
	
	/**
	 * Print all booking of all campervans in that depot
	 * @param depotName the name of depot wanted to be printed
	 * @pre the depot to be printed do exist
	 * @post the information for the depot not changed after printing
	 */
	public void print(String depotName)
	{
		Iterator<Depot> iterator = depotList.iterator();
		while(iterator.hasNext())
		{
			Depot element = iterator.next();
			if (element.getName().equals(depotName)) 
			{
				element.print();
				return;
			}
		}
	}
}
