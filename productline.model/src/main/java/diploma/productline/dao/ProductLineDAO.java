package diploma.productline.dao;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Properties;

import diploma.productline.entity.ProductLine;

public class ProductLineDAO extends BaseDAO {

	private final String selectProductLine = "SELECT name, description FROM productline WHERE name = ?";
	private final String insertProductLine = "INSERT INTO productline VALUES (?,?,?)";

	public ProductLineDAO(Properties properties) {
		super(properties);
	}

	public static void createDatabaseStructure(Properties properties, File ddl,
			Connection connection) throws ClassNotFoundException, SQLException,
			FileNotFoundException, IOException {
		ScriptRunner runner = new ScriptRunner(connection, true, true);
		runner.runScript(new BufferedReader(new FileReader(ddl)));
	}

	public ProductLine getProductLine(String name, Connection connection)
			throws ClassNotFoundException, SQLException {
		ProductLine productLine = getProductLineFromDB(connection, name);
		return productLine;
	}

	private ProductLine getProductLineFromDB(Connection con, String name)
			throws ClassNotFoundException, SQLException {

		ModuleDAO moduleDao = new ModuleDAO(this.properties);

		ProductLine productLine = null;
		try (PreparedStatement prepStatement = con
				.prepareStatement(selectProductLine)) {
			prepStatement.setString(1, name);
			try (ResultSet result = prepStatement.executeQuery()) {

				while (result.next()) {
					productLine = new ProductLine();
					productLine.setName(result.getString("name"));
					productLine.setDescription(result.getString("description"));
				}

				productLine.setModules(moduleDao
						.getModulesWhithChildsByProductLine(productLine, con));
			}
		}
		return productLine;
	}

	public ProductLine getProductLineWithChilds(String name, Connection con)
			throws ClassNotFoundException, SQLException {
		ProductLine productLine = null;
		productLine = getProductLineFromDB(con, name);

		return productLine;
	}

	public boolean save(ProductLine productLine, Connection con)
			throws ClassNotFoundException, SQLException {
		try(PreparedStatement prepStatement = con
				.prepareStatement(insertProductLine)){
			prepStatement.setString(1, productLine.getName());
			prepStatement.setString(2, productLine.getDescription());
			prepStatement.setString(3, null);
			return prepStatement.execute();
		}
	}

	public boolean createAll(ProductLine productLine, Connection connection)
			throws ClassNotFoundException, SQLException {
		ModuleDAO moduleDao = new ModuleDAO(properties);

		this.save(productLine, connection);
		moduleDao.createAll(productLine.getModules(), connection);
		return false;
	}
}
