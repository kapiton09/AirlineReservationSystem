package beans;

import java.io.Serializable;
/*
 * 
 *  Passenger class
 *  Author: Kapil 
 * 
 */
public class passenger implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * 
	 */
	private int passengerId;
	private String passportNo;
	private String firstName;
	private String lastName;
	private String email;
	
	public passenger (){
		
	}
	
	public passenger (int passengerId, String passportNo,String firstName, String lastName, String email)
	{
		this.passengerId = passengerId;
		this.passportNo = passportNo;
		this.firstName = firstName;
		this.lastName = lastName;
		this.email = email;
	}
	
	public int getPassengerId() {
		return passengerId;
	}
	public void setPassengerId(int passengerId) {
		this.passengerId = passengerId;
	}
	public String getPassportNo() {
		return passportNo;
	}
	public void setPassportNo(String passportNo) {
		this.passportNo = passportNo;
	}
	public String getFirstName() {
		return firstName;
	}
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}
	public String getLastName() {
		return lastName;
	}
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	

}
