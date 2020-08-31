package stage3;

import java.sql.*;


/**
 * 
 * @author hejing
 * Connect database
 */

public class DbConnection {

	static final String JDBC_DRIVER = "com.mysql.cj.jdbc.Driver";
	static final String DB_URL = "jdbc:mysql://localhost:3306/Stage3";
	static final String USER = "root";
	static final String PASS = "123123123";

	static {
		try {
			Class.forName(JDBC_DRIVER);
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
			// TODO: handle exception
		}
	}

	public static Connection getConnection() {
		try {
			return DriverManager.getConnection(DB_URL, USER, PASS);
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
			// TODO: handle exception
		}
	}
	
	public static void getClose(PreparedStatement ps,Connection conn) {
		try {
			if (!ps.isClosed()) {
				ps.close();
			}
		} catch (SQLException e) {
			// TODO: handle exception
		}finally {
			try {
				if(!conn.isClosed()) {
					conn.close();
				}
			} catch (Exception e2) {
				// TODO: handle exception
			}
		}
	}
	
}
