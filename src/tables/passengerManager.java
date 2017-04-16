package tables;
/*
 * 
 *  Passenger Manager class
 *  Author: Kapil 
 * 
 */
import java.sql.*;
import java.util.ArrayList;

import db.*;
import beans.passenger;

public class passengerManager {
	
public static passenger getRow(String passportNo) throws SQLException {
		
		String sql = "SELECT passengerID, passportNo, firstName, lastName, email FROM passenger WHERE passportNo = ? ";
		ResultSet rs = null;
		
		try(
			Connection conn = DBUtil.getConnection(DBType.MYSQL);
			PreparedStatement stmt = conn.prepareStatement(sql);
			)
			{
			stmt.setString(1, passportNo);
			rs = stmt.executeQuery();
			
			if (rs.next()){
				passenger bean = new passenger();
				bean.setPassengerId(Integer.parseInt(rs.getString("passengerId")));
				bean.setPassportNo(rs.getString("passportNo"));
				bean.setFirstName(rs.getString("firstName"));
				bean.setLastName(rs.getString("lastName"));
				bean.setEmail(rs.getString("email"));
				return bean;
			} else {
				return null;
			}
			
		} catch (SQLException e) {
			System.err.println(e);
			return null;
		}
		finally {
			if (rs != null) rs.close();
		}
		
	}

public static passenger getEmail(String email) throws SQLException {
	
	String sql = "SELECT email FROM passenger WHERE email = ? ";
	ResultSet rs = null;
	
	try(
		Connection conn = DBUtil.getConnection(DBType.MYSQL);
		PreparedStatement stmt = conn.prepareStatement(sql);
		)
		{
		stmt.setString(1, email);
		rs = stmt.executeQuery();
		
		if (rs.next()){
			passenger bean = new passenger();
			bean.setEmail(rs.getString("email"));
			return bean;
		} else {
			return null;
		}
		
	} catch (SQLException e) {
		System.err.println(e);
		return null;
	}
	finally {
		if (rs != null) rs.close();
	}
}

public static passenger getLastName(String lastName) throws SQLException {
	
	String sql = "SELECT passportNo, passengerId, firstName, lastName, email FROM passenger WHERE lastName = ? ";
	ResultSet rs = null;
	
	try(
		Connection conn = DBUtil.getConnection(DBType.MYSQL);
		PreparedStatement stmt = conn.prepareStatement(sql);
		)
		{
		stmt.setString(1, lastName);
		rs = stmt.executeQuery();
		
		if (rs.next()){
			passenger bean = new passenger();
			bean.setPassengerId(Integer.parseInt(rs.getString("passengerId")));
			bean.setPassportNo(rs.getString("passportNo"));
			bean.setFirstName(rs.getString("firstName"));
			bean.setLastName(rs.getString("lastName"));
			bean.setEmail(rs.getString("email"));
			return bean;
		} else {
			return null;
		}
		
	} catch (SQLException e) {
		System.err.println(e);
		return null;
	}
	finally {
		if (rs != null) rs.close();
	}
}

public static boolean insert(passenger bean) throws Exception {
	
	String sql = "INSERT into passenger (passportNo, firstName, lastName, email) " +
	"VALUES (?, ?, ?, ?)";
	ResultSet keys = null;
	try (
			Connection conn = DBUtil.getConnection(DBType.MYSQL);
			PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
			){
		
		stmt.setString(1, bean.getPassportNo());
		stmt.setString(2, bean.getFirstName());
		stmt.setString(3, bean.getLastName());
		stmt.setString(4, bean.getEmail());
		int affected = stmt.executeUpdate();
		
		if (affected ==1 ){
			keys = stmt.getGeneratedKeys();
			keys.next();
			int newKey = keys.getInt(1);
			bean.setPassengerId(newKey);
		} else {
			System.err.println("No rows affected");
			return false;
		}
		
	} catch (SQLException e){
		System.err.println(e);
		return false;
	} finally {
		if (keys!=null) keys.close();
	}
	return true;
}

public static boolean update(passenger bean) throws Exception{
	String sql = 
			"UPDATE passenger SET " +
			"passportNo = ?, firstName = ?, lastName = ?, email = ? " +
			"WHERE passengerID = ?";
	try 
		(Connection conn = DBUtil.getConnection(DBType.MYSQL);
		PreparedStatement stmt = conn.prepareStatement(sql);)
	{
		stmt.setString(1, bean.getPassportNo());
		stmt.setString(2, bean.getFirstName());
		stmt.setString(3, bean.getLastName());
		stmt.setString(4, bean.getEmail());
		stmt.setInt(5, bean.getPassengerId());
		
		int affected = stmt.executeUpdate();
		if (affected == 1){
			return true;
		} else {
			return false;
		}
	}
	catch (SQLException e){
		System.err.println(e);
		return false;
	}
}
}
