package tables;
/*
 * 
 *  Admin Manager class
 *  Author: Kapil 
 * 
 */
import java.sql.*;
import db.*;
import beans.admin;

public class adminManager {
	
	public static admin getRow(String username) throws SQLException {
		
		String sql = "SELECT username, password FROM admin WHERE username = ? ";
		ResultSet rs = null;
		
		try(
			Connection conn = DBUtil.getConnection(DBType.MYSQL);
			PreparedStatement stmt = conn.prepareStatement(sql);
			)
			{
			stmt.setString(1, username);
			rs = stmt.executeQuery();
			
			if (rs.next()){
				admin bean = new admin();
				bean.setUsername(rs.getString("username"));
				bean.setPassword(rs.getString("password"));
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
	
public static admin getEmail(String email) throws SQLException {
		
		String sql = "SELECT email FROM admin WHERE email = ? ";
		ResultSet rs = null;
		
		try(
			Connection conn = DBUtil.getConnection(DBType.MYSQL);
			PreparedStatement stmt = conn.prepareStatement(sql);
			)
			{
			stmt.setString(1, email);
			rs = stmt.executeQuery();
			
			if (rs.next()){
				admin bean = new admin();
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
	
	public static boolean insert(admin bean) throws Exception {
		
		String sql = "INSERT into admin (username, email, password) " +
		"VALUES (?, ?, ?)";
		ResultSet keys = null;
		try (
				Connection conn = DBUtil.getConnection(DBType.MYSQL);
				PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
				){
			
			stmt.setString(1, bean.getUsername());
			stmt.setString(2, bean.getEmail());
			stmt.setString(3, bean.getPassword());
			int affected = stmt.executeUpdate();
			
			if (affected ==1 ){
				keys = stmt.getGeneratedKeys();
				keys.next();
				int newKey = keys.getInt(1);
				bean.setAdminId(newKey);
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

}
