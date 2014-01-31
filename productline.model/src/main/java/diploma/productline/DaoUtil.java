package diploma.productline;

import java.sql.Connection;
import java.sql.DriverManager;
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
}
