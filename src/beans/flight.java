package beans;

import java.sql.Date;
import java.sql.Time;

/*
 * 
 *  Flight class
 *  Author: Kapil 
 * 
 */
public class flight {
	
	private int flightId;
	private String origin;
	private String destination;
	private Date flightDate;
	private Time departTime;
	private int capacity;
	private double fare;
	
	public flight ()
	{
		
	}
	
	public flight (int flightID, String origin, String destination, Date flightDate, Time departTime, int capacity, double fare){
		this.flightId = flightID;
		this.origin = origin;
		this.destination = destination;
		this.flightDate = flightDate;
		this.departTime = departTime;
		this.capacity = capacity;
		this.fare = fare;
	}
	
	public int getFlightId() {
		return flightId;
	}
	
	public void setFlightId(int flightId) {
		this.flightId = flightId;
	}
	
	public String getOrigin() {
		return origin;
	}
	
	public void setOrigin(String origin) {
		this.origin = origin;
	}
	
	public String getDestination() {
		return destination;
	}
	
	public void setDestination(String destination) {
		this.destination = destination;
	}
	
	public Date getFlightDate() {
		return flightDate;
	}
	
	public void setFlightDate(Date flightDate) {
		this.flightDate = flightDate;
	}
	
	public Time getDepartTime() {
		return departTime;
	}
	
	public void setDepartTime(Time departTime) {
		this.departTime = departTime;
	}
	
	public int getCapacity() {
		return capacity;
	}
	
	public void setCapacity(int capacity) {
		this.capacity = capacity;
	}
	
	public double getFare() {
		return fare;
	}
	
	public void setFare(double fare) {
		this.fare = fare;
	}
	
	
	
}
