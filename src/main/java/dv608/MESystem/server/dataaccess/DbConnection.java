package dv608.MESystem.server.dataaccess;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class DbConnection {
	private static String DB_URI = "jdbc:mysql://localhost:3306/mesystem";
	private static String DB_USER = "MESystem";
	private static String DB_PASS = "MESystem";
	private Connection connection = null;
	
	public Connection connect() {
		try {
			Class.forName("com.mysql.jdbc.Driver");
			connection = DriverManager.getConnection(DB_URI, DB_USER, DB_PASS);
		} catch (Exception e) {
			System.err.println(e);
		}
		return connection;
	}
	
	public void close(){
		try {
			connection.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public Integer create(String sql) {
		Integer generatedKey = null;
		try {
			PreparedStatement ps = connection.prepareStatement(sql,
			        Statement.RETURN_GENERATED_KEYS);
			ps.execute();
			 
			ResultSet rs = ps.getGeneratedKeys();
			
			if (rs.next()) {
			    generatedKey = rs.getInt(1);
			}
		} catch (Exception e) {
			System.err.println(e);
		}
		return generatedKey;
	}
	
	public ResultSet get(String sql) {
		ResultSet rs = null;
		try {
			Statement stmt = connection.createStatement();
			rs = stmt.executeQuery(sql);
		} catch (Exception e) {
			System.err.println(e);
		}
		return rs;
	}
	
	public boolean update(String sql){
		Integer updateResult = null;
		try {
			PreparedStatement ps = connection.prepareStatement(sql);
			updateResult = ps.executeUpdate();
			if(updateResult == 0)
				return true;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return false;
	}
}
