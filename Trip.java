
public class Trip implements Comparable{
	//trip_id,arrival_time,departure_time,stop_id,stop_sequence,stop_headsign,pickup_type,drop_off_type,shape_dist_traveled
	public int  tripId;
	public String arrivalTime;
	public String departureTime;
	public int stopID;
	public int stopSequence;
	public int stopHeadsign;
	public int pickupType;
	public int dropOffType;
	public double shapeDistTraveled;
	
	@Override
	public int compareTo(Object o) {
		//Secondary sort if tripId's match
		if(Integer.compare(tripId,((Trip) o).tripId)==0) {
			return Integer.compare(stopID,((Trip)o).stopID);
		}
		
		return Integer.compare(tripId,((Trip) o).tripId);
		
	}
	 @Override
	 public String toString() {
	     return String.format("|%8d|%9s|%9s|%6d|%3d|%2d|%2d|%2d|%7.4f|",tripId,arrivalTime,departureTime,stopID,stopSequence,
	    		 stopHeadsign,pickupType,dropOffType,shapeDistTraveled);
	 }
	
	
}
