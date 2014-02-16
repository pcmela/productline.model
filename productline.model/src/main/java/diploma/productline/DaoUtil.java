package diploma.productline;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Properties;

public class DaoUtil {

	public static Connection connect(Properties properties)
			throws ClassNotFoundException, SQLException {
		Class.forName("org.h2.Driver");
		Connection con = DriverManager.getConnection(
				properties.getProperty("connection_url"),
				properties.getProperty("username"),
				properties.getProperty("password"));

		return con;
	}
	
	public static Connection connect(String username, String password, String connectionString)
			throws ClassNotFoundException, SQLException {
		Class.forName("org.h2.Driver");
		Connection con = DriverManager.getConnection(
				connectionString,
				username,
				password);

		return con;
	}
	
	public static void cloaseConnection(Connection con){
		try {
			con.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public static void closeResultSet(ResultSet rs){
		try {
			rs.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public static void closePrepareStatement(PreparedStatement ps){
		try {
			ps.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
}
