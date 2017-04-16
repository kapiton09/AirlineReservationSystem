package beans;
/*
 * 
 *  Ticket class
 *  Author: Kapil 
 * 
 */

import java.sql.Date;

public class ticket {

	private int ticketID;
	private Date bookingDate;
	private Date flightDate;
	private int passengerID;
	private int flightID;
	private double cost;
	private int seatRow;
	private String seatLabel;
	
	public ticket() {
		
	}
	
	public ticket(int ticketID,int passengerID, int flightID,  Date flightDate,  double cost, Date bookingDate, int seatRow, String seatLabel){
		this.ticketID = ticketID;
		this.passengerID = passengerID;
		this.flightID = flightID;
		this.flightDate = flightDate;
		this.cost = cost;
		this.bookingDate = bookingDate;
		this.seatRow = seatRow;
		this.seatLabel = seatLabel;
	}

	public int getTicketID() {
		return ticketID;
	}

	public void setTicketID(int ticketID) {
		this.ticketID = ticketID;
	}

	public Date getBookingDate() {
		return bookingDate;
	}

	public void setBookingDate(Date bookingDate) {
		this.bookingDate = bookingDate;
	}

	public Date getFlightDate() {
		return flightDate;
	}

	public void setFlightDate(Date flightDate) {
		this.flightDate = flightDate;
	}

	public int getPassengerID() {
		return passengerID;
	}

	public void setPassengerID(int passengerID) {
		this.passengerID = passengerID;
	}

	public int getFlightID() {
		return flightID;
	}

	public void setFlightID(int flightID) {
		this.flightID = flightID;
	}

	public double getCost() {
		return cost;
	}

	public void setCost(double cost) {
		this.cost = cost;
	}

	public int getSeatRow() {
		return seatRow;
	}

	public void setSeatRow(int seatRow) {
		this.seatRow = seatRow;
	}

	public String getSeatLabel() {
		return seatLabel;
	}

	public void setSeatLabel(String seatLabel) {
		this.seatLabel = seatLabel;
	}
	
	
	
}
