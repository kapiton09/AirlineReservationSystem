package db;
/*
 * 
 *  DBUtil class
 *  Author: Kapil 
 * 
 */
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

//Reusable Class to connect to MySQL Database (or can connect to other database if required)
public class DBUtil {

	private static final String USERNAME = "kapiton9_kiaora";  
	private static final String PASSWORD = "kapil";
	private static final String M_CONN_STRING =
			"jdbc:mysql://mysql1003.mochahost.com/kapiton9_kiaora";    //for remote database use: mysql1003.mochahost.com/kapiton9_kiaora
												//for localhost use: localhost/kiaora

	public static Connection getConnection(DBType dbType) throws SQLException {
		
		switch (dbType) {
		case MYSQL:
			return DriverManager.getConnection(M_CONN_STRING, USERNAME, PASSWORD);
		default:
			return null;
		}
		
	}
	
	public static void processException(SQLException e) {
		System.err.println("Error message: " + e.getMessage());
		System.err.println("Error code: " + e.getErrorCode());
		System.err.println("SQL state: " + e.getSQLState());
	}
	
}
