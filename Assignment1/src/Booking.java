
/**
 * The booking contains all details that can be checked and changed
 * @author Liangde Li
 */
public class Booking {
    private int ID;
    private int startHour;
    private int startMonth;
    private int startDay;
    private int endHour;
    private int endMonth;
    private int endDay;
    private int numManual;
    private int numAutomatic;
    
    /**
     * Construct a new booking
     * @param id the unique ID of this new booking
     * @param sh the start hour of this new booking
     * @param sm the start month of this new booking
     * @param sd the start day of this new booking
     * @param eh the end hour of this new booking
     * @param em the end month of this new booking
     * @param ed the end day of this new booking
     * @param nm the number of manual campervans been requested for this new booking
     * @param na the number of automatic campervans been requested for this new booking
     * @pre all information input are valid and type right, start time is before end time
     * @post the booking has right information with correct type
     */
    public Booking(String id, String sh, String sm, String sd, String eh, String em, String ed, int nm, int na)
    {
    	this.ID = Integer.parseInt(id);
    	this.startHour = Integer.parseInt(sh);
    	this.startMonth = toMonth(sm);
    	this.startDay = Integer.parseInt(sd);
    	this.endHour = Integer.parseInt(eh);
    	this.endMonth = toMonth(em);
    	this.endDay = Integer.parseInt(ed);
    	this.numManual = nm;
    	this.numAutomatic = na;
    }
    
    /**
     * Convert an abbreviation of month to int type 
     * @param aMonth the abbreviation of a certain month
     * @return the number that represents the month
     * @pre month input is abbreviation with first three letters and in String type
     * @post month return is int type, varies from 1 to 12
     */
    private int toMonth(String aMonth)
    {
    	if (aMonth.equals("Jan")) return 1;
    	else if (aMonth.equals("Feb")) return 2;
    	else if (aMonth.equals("Mar")) return 3;
    	else if (aMonth.equals("Apr")) return 4;
    	else if (aMonth.equals("May")) return 5;
    	else if (aMonth.equals("Jun")) return 6;
    	else if (aMonth.equals("Jul")) return 7;
    	else if (aMonth.equals("Aug")) return 8;
    	else if (aMonth.equals("Sep")) return 9;
    	else if (aMonth.equals("Oct")) return 10;
    	else if (aMonth.equals("Nov")) return 11;
    	else  return 12;
    }
    
    /**
     * Convert int type of a month to abbreviation
     * @param aMonth the number that represents the month
     * @return the abbreviation of the month in string type
     * @pre month input is int type varies from 1 to 12
     * @post month return is abbreviation with first three letter of the month in String type 
     */
    private String toMonth(int aMonth)
    {
    	if (aMonth == 1) return "Jan";
    	else if (aMonth == 2) return "Feb";
    	else if (aMonth == 3) return "Mar";
    	else if (aMonth == 4) return "Apr";
    	else if (aMonth == 5) return "May";
    	else if (aMonth == 6) return "Jun";
    	else if (aMonth == 7) return "Jul";
    	else if (aMonth == 8) return "Aug";
    	else if (aMonth == 9) return "Sep";
    	else if (aMonth == 10) return "Oct";
    	else if (aMonth == 11) return "Nov";
    	else  return "Dec";
    }
    
    /**
     * Test whether two booking clash on time
     * @param aBooking the booking to be tested for clashing with this booking
     * @return whether they clash
     * @pre two bookings have valid start and end time
     * @post specific result of true/false will be given
     */
    public Boolean clash(Booking aBooking) // test whether two bookings clash on time
    {
    	if (this.endTimeMagic() + 1 <= aBooking.startTimeMagic()
    		|| aBooking.endTimeMagic() + 1	<= this.startTimeMagic()) return false;
    	return true;
    }
    
    /**
     * Get the value of the start time, in MMDDHH format, int type
     * @return the value of start time
     * @pre this booking has valid start time
     * @post result is between 10100 and 123122
     */
    public int startTimeMagic() // convert start time to integer with format MMDDHH
    {
    	return (startMonth * 10000 + startDay * 100 + startHour);
    }
    
    /**
     * Get the value of the end time, in MMDDHH format, int type
     * @return the value of end time
     * @pre this booking has valid end time
     * @post result is between 10101 and 123123
     */
    public int endTimeMagic() // convert end time to integer with format MMDDHH
    {
    	return (endMonth * 10000 + endDay * 100 + endHour);
    }
    
    /**
     * Get this booking's unique ID
     * @return the ID of this booking in int type
     */
    public int getID() {
		return ID;
	}
	
    /**
     * Get this booking's startHour
     * @return the startHour of this booking in int type
     */
	public int getStartHour() {
		return startHour;
	}
	
	/**
	 * Get the booking's startMonth in abbreviation String type
	 * @return the abbreviation of startMonth in String type
	 */
	public String getStartMonth() {
		return this.toMonth(startMonth);
	}
	
	/**
	 * Get the booking's startDay 
	 * @return the startDay of booking in int type
	 */
	public int getStartDay() {
		return startDay;
	}
	
    /**
     * Get this booking's endHour
     * @return the endHour of this booking in int type
     */
	public int getEndHour() {
		return endHour;
	}
	
	/**
	 * Get the booking's endMonth in abbreviation String type
	 * @return the abbreviation of endMonth in String type
	 */
	public String getEndMonth() {
		return this.toMonth(endMonth);
	}

	/**
	 * Get the booking's endDay 
	 * @return the endDay of booking in int type
	 */
	public int getEndDay() {
		return endDay;
	}

	/**
	 * Get the number of manual campervans needed for this booking
	 * @return number of manual campervans needed
	 */
	public int getNumManual() {
		return numManual;
	}

	/**
	 * Get the number of automatic campervans needed for this booking
	 * @return number of automatic campervans needed
	 */
	public int getNumAutomatic() {
		return numAutomatic;
	}
   
}
