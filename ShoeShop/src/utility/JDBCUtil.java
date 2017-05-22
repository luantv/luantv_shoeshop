package utility;

import java.sql.Connection;
import java.sql.DriverManager;

public class JDBCUtil{
	private static Connection conn;
	private static final String	URL		= "jdbc:mysql://localhost:3306/ShoeShop";
	private static final String	USER	= "root";
	private static final String	PASS	= "root";

	public static Connection getConnection(){
		try{
			Class.forName("com.mysql.jdbc.Driver");
			conn = DriverManager.getConnection(URL, USER, PASS);
			return conn;
		} catch(Exception e){
			e.printStackTrace();
			return null;
		}
	}

	public static void close(Connection conn){
		try{
			if(!conn.isClosed())
				conn.close();
		} catch(Exception e){
			e.printStackTrace();
		}
	}
}
