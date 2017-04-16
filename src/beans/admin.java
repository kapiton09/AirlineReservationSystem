package beans;
/*
 * 
 *  Admin class
 *  Author: Kapil 
 * 
 */
import java.io.Serializable;

public class admin implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 5401598155958888L;
	private int adminId;
	private String username;
	private String email;
	private String password;
	
	public int getAdminId() {
		return adminId;
	}
	public void setAdminId(int adminId) {
		this.adminId = adminId;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}

}
