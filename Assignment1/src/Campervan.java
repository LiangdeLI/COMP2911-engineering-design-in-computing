import java.util.*;

/**
 * The campervan that can be booked
 * @author Liangde Li z5077896
 */
public class Campervan {
    private String name;
    private Boolean isAuto;
    private ArrayList<Booking> schedule;
    
    /**
     * Construct a new campervan
     * @param name the name of the campervan in String type
     * @param isAuto the type of the campervan in String type
     * @pre the name and type is valid 
     * @post the campervan do exist after construction
     */
    public Campervan(String name, String isAuto)
    {
    	this.name = name;
    	if (isAuto.equals("Automatic")) this.isAuto = true;
    	else this.isAuto = false;
    	schedule = new ArrayList<Booking>();
    }
    
    /**
     * Analyse whether this campervan is available for certain booking
     * @param aBooking the booking want to match with this campervan
     * @param ignoreID the ID of the booking to be ignored during analysis, used for "Change" request 
     * @return whether this campervan is available for the booking
     * @pre the booking has valid time contain, ignoreID are exactly the booking want to be changed
     * @post specific result of true/false will get
     */
    public Boolean available(Booking aBooking, int ignoreID)
    {
    	Iterator<Booking> iterator = schedule.iterator();
    	while (iterator.hasNext())
    	{
    		Booking element = iterator.next();
    		if (element.getID() == ignoreID) continue;
    		if (element.clash(aBooking)) return false;
    	}
    	return true;
    }
    
    /**
     * Add the new booking to this campervan's schedule
     * @param newBooking the new booking to be added
     * @pre the campervan doesn't have the booking before
     * @post the added booking in schedule is sorted by start time 
     */
    public void addBooking(Booking newBooking)
    {
    	int length = schedule.size();
    	int it = 0;
    	while (it < length)
    	{
    		Booking element = schedule.get(it);
    		if (newBooking.endTimeMagic() < element.startTimeMagic()) break;
    		it++;
    	}
    	schedule.add(it, newBooking);
    	System.out.print(name);
    }
    
    /**
     * Cancel certain booking for this campervan
     * @param id the ID of the booking to be canceled in String type
     * @pre the booking do exist in the schedule
     * @post the booking is not in the schedule anymore
     */
    public void  cancelBooking(String id)
    {
    	Iterator<Booking> iterator = schedule.iterator();
    	while (iterator.hasNext())
    	{
    		Booking element = iterator.next();
    	    if (element.getID() == Integer.parseInt(id))
    	    {
    	    	iterator.remove();
    	    	break;
    	    }
    	}
    }
    
    /**
     * Print all bookings of this campervan
     * @param depotName the name of the depot that this campervan belongs to
     * @pre the campervan belongs to the depot want to be printed
     * @post all bookings in schedule are printed
     */
    public void print(String depotName)
    {
    	if (schedule.isEmpty()) return;
    	Iterator<Booking> iterator = schedule.iterator();
    	while (iterator.hasNext())
    	{
    		Booking element = iterator.next();
    		
    		System.out.print(depotName + " " + name + " "); 
    		if(element.getStartHour() >= 10)
    		{		
    			System.out.print(element.getStartHour() + ":00 " + element.getStartMonth() + " " + element.getStartDay() + " ");
    		}
    		else
    		{
    			System.out.print("0" + element.getStartHour() + ":00 " + element.getStartMonth() + " " + element.getStartDay() + " ");
    		}
    		if(element.getEndHour() >= 10)
    		{
    			System.out.println(element.getEndHour() + ":00 " + element.getEndMonth() + " " + element.getEndDay());
    		}
    		else
    		{
    			System.out.println("0" + element.getEndHour() + ":00 " + element.getEndMonth() + " " + element.getEndDay());	
    		}
    	}
    }
    
    /**
     * Get the name of this campervan
     * @return the name of this campervan in String type
     */
    public String getName()
    {
    	return name;
    }
    
    /**
     * Get the type of this campervan, whether it is automatic
     * @return whether this campervan is automatic
     */
    public Boolean getIsAuto()
    {
    	return isAuto;
    }

}
