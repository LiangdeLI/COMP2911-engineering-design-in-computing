import java.util.*;

/**
 * The depot provides campervans that can be booked
 * @author Liangde Li z5077896
 */
public class Depot {
    private String name;
	private ArrayList<Campervan> vanList; 
	
	/**
	 * Construct a new depot
	 * @param aName the name for this new depot
	 */
	public Depot(String aName)
	{
		this.name = aName;
		vanList = new ArrayList<Campervan>(); 
	}

	/**
	 * Get the name of this depot
	 * @return the name of this depot in String type
	 */
	public String getName() {
		return name;
	}
	
	/**
	 * Add new campervan for this depot
	 * @param aName the name of this new campervan
	 * @param isAuto the type of this new campervan
	 * @pre the name and type of campervan are given valid 
	 * @post the campervan is now in the vanList of this depot
	 */
	public void addCampervan(String aName, String isAuto)
	{
		Iterator<Campervan> iterator = vanList.iterator();
		while(iterator.hasNext())
		{
			Campervan element = iterator.next();
			if (element.getName().equals(aName)) return;
		}
		Campervan newVan = new Campervan(aName, isAuto);
		vanList.add(newVan);
	}
	
	/**
	 * Analyse how many manual campervan is available for certain booking
	 * @param aBooking a booking try to find possible manual campervan
	 * @param ignoreID the ID of the booking to be ignored during this analysis, used when finding for "Change" request
	 * @return the number of manual campervans available for the booking in this depot
	 * @pre the booking has valid time, and ignoreID is valid in "Change" condition
	 * @post the result is a nature number
	 */
	public int manAble(Booking aBooking, int ignoreID)
	{
		int result = 0;
		Iterator<Campervan> iterator = vanList.iterator();
		while (iterator.hasNext())
		{
			Campervan element = iterator.next();
			if (!element.getIsAuto())
			{
				if (element.available(aBooking, ignoreID)) result++;
			}
		}
		return result;
	}
	
	/**
	 * Analyse how many automatic campervan is available for certain booking
	 * @param aBooking a booking try to find possible automatic campervan
	 * @param ignoreID the ID of the booking to be ignored during this analysis, used when finding for "Change" request
	 * @return the number of automatic campervans available for the booking in this depot
	 * @pre the booking has valid time, and ignoreID is valid in "Change" condition
	 * @post the result is a nature number
	 */
	public int autoAble(Booking aBooking, int ignoreID)
	{
		int result = 0;
		Iterator<Campervan> iterator = vanList.iterator();
		while (iterator.hasNext())
		{
			Campervan element = iterator.next();
			if (element.getIsAuto())
			{
				if (element.available(aBooking, ignoreID)) result++;
			}
		}
		return result;
	}
	
	/**
	 * Book certain amount of manual campervans and automatic campervans in this depot
	 * @param newBooking a booking to be added into some campervans in this depot
	 * @param numManBook number of manual campervans in this depot to be needed for the booking
	 * @param numAutoBooknumber of automatic campervans in this depot to be needed for the booking
	 * @pre this depot has at least numManBook amount of manual vans and numAutoBook amount of automatic vans for the booking, start time is before end time
	 * @post the booking has been added to the schedule of numManBook amount of manual vans and numAutoBook amount of automatic vans
	 */
	public void makeBooking(Booking newBooking, int numManBook, int numAutoBook)
	{
		Iterator<Campervan> iterator = vanList.iterator();
		int totalNeed = numManBook + numAutoBook;
		while (iterator.hasNext())
		{
			Campervan element = iterator.next();
			if (element.getIsAuto() && element.available(newBooking, -1) && numAutoBook != 0)
			{
				element.addBooking(newBooking);
			    numAutoBook--;
			}
			if (!element.getIsAuto() && element.available(newBooking, -1) && numManBook != 0)
			{
				element.addBooking(newBooking);
			    numManBook--;
			}
			
			if (numAutoBook + numManBook != 0)
			{
				if (totalNeed != numManBook + numAutoBook) System.out.print(", ");
			}
			else return;
		}
	}
	
	/**
	 * Cancel certain booking
	 * @param id the unique ID of the booking to be canceled
	 * @pre the booking ID is valid
	 * @post the schedule of vans in this depot don't have the booking anymore
	 */
	public void cancelBooking(String id)
	{
		Iterator<Campervan> iterator = vanList.iterator();
		while (iterator.hasNext())
		{
			Campervan element = iterator.next();
		    element.cancelBooking(id);
		}
	}
	
	/**
	 * Print the all bookings of all campervans belong to this depot
	 * @pre this depot is the depot exactly want to be printed
	 * @post all bookings of all vans have been printed, no more or less
	 */
	public void print()
	{
		Iterator<Campervan> iterator = vanList.iterator();
		while (iterator.hasNext())
		{
			Campervan element = iterator.next();
			element.print(name);
		}
	}
}
