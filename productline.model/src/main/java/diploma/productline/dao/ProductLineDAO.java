package diploma.productline.dao;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Properties;

import diploma.productline.DaoUtil;
import diploma.productline.entity.ProductLine;

public class ProductLineDAO extends BaseDAO {

	private final String selectProductLine = "SELECT name, description FROM productline WHERE name = ?";
	private final String insertProductLine = "INSERT INTO productline VALUES (?,?,?)";
	/*private final String selectVariability = "SELECT id, name, module_id FROM variability WHERE module_id = ?";
	private final String selectElement = "SELECT element_id, name, description, module_id FROM element WHERE module_id = ?";*/
	// private final String selectType =
	// "SELECT type_id, name, description FROM type WHERE";
	//private final String selectPackage = "SELECT package_id, name, module_id FROM element WHERE module_id = ?";

	public ProductLineDAO(Properties properties) {
		super(properties);
	}

	public static void createDatabaseStructure(Properties properties)
			throws ClassNotFoundException, SQLException, FileNotFoundException,
			IOException {
		Connection con = DaoUtil.connect(properties);
		ScriptRunner runner = new ScriptRunner(con, true, true);
		ClassLoader cl = ProductLineDAO.class.getClassLoader();
		
		runner.runScript(new BufferedReader(new FileReader(cl.getResource("DDL.sql").getFile())));
	}

	public ProductLine getProductLine(String name)
			throws ClassNotFoundException, SQLException {
		Connection con = DaoUtil.connect(properties);
		return getProductLineFromDB(con, name);

	}

	private ProductLine getProductLineFromDB(Connection con, String name)
			throws ClassNotFoundException, SQLException {
		
		ModuleDAO moduleDao = new ModuleDAO(this.properties);
		
		ProductLine productLine = null;
		PreparedStatement prepStatement = con
				.prepareStatement(selectProductLine);
		prepStatement.setString(1, name);
		ResultSet result = prepStatement.executeQuery();

		while (result.next()) {
			productLine = new ProductLine();
			productLine.setName(result.getString("name"));
			productLine.setDescription(result.getString("description"));
		}

		productLine.setModules(moduleDao.getModulesWhithChildsByProductLine(productLine));		
		return productLine;
	}

	public ProductLine getProductLineWithChilds(String name)
			throws ClassNotFoundException, SQLException {
		Connection con = DaoUtil.connect(properties);
		ProductLine productLine = getProductLineFromDB(con, name);
		if (productLine == null) {
			return null;
		}

		return productLine;
	}
	
	public boolean save(ProductLine productLine, Connection connection) throws ClassNotFoundException, SQLException{
		Connection con = null;
		if(connection == null){
			con = DaoUtil.connect(properties);
		}else{
			con = connection;
		}
		PreparedStatement prepStatement = con.prepareStatement(insertProductLine);
		prepStatement.setString(1, productLine.getName());
		prepStatement.setString(2, productLine.getDescription());
		prepStatement.setString(3, null);
		return prepStatement.execute();
	}
	
	public boolean createAll(ProductLine productLine) throws ClassNotFoundException, SQLException{
		Connection connection = DaoUtil.connect(properties);
		ModuleDAO moduleDao = new ModuleDAO(properties);
		
		this.save(productLine, connection);
		moduleDao.createAll(productLine.getModules(), connection);
		return false;
	}
}
