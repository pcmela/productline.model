package diploma.productline.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashSet;
import java.util.Properties;
import java.util.Set;

import diploma.productline.entity.Module;
import diploma.productline.entity.ProductLine;

public class ModuleDAO extends BaseDAO {
	private final String selectModule = "SELECT module_id, name, description, is_variable, product_line_id FROM module WHERE module_id like ?";
	private final String selectModuleByPL = "SELECT module_id, name, description, is_variable, product_line_id FROM module WHERE product_line_id like ?";
	private final String insertModule = "INSERT INTO module (module_id,name,description,is_variable,product_line_id) VALUES (?,?,?,?,?)";

	public ModuleDAO(Properties properties) {
		super(properties);
	}

	public Module getModule(int id, Connection con)
			throws ClassNotFoundException, SQLException {
		return getModuleFromDB(con, id);

	}

	public Set<Module> getModulesWhithChildsByProductLine(
			ProductLine productLine, Connection con) throws SQLException,
			ClassNotFoundException {
		VariabilityDAO variabilityDao = new VariabilityDAO(properties);
		ElementDAO elementDAO = new ElementDAO(properties);
		PackageDAO packageDao = new PackageDAO(properties);

		Set<Module> module = new HashSet<Module>();
		try (PreparedStatement prepStatement = con
				.prepareStatement(selectModuleByPL)) {
			prepStatement.setString(1, productLine.getName());
			try (ResultSet result = prepStatement.executeQuery()) {

				while (result.next()) {
					Module m = new Module();
					m.setName(result.getString("name"));
					m.setDescription(result.getString("description"));
					m.setProductLine(productLine);
					m.setVariabilities(variabilityDao
							.getModulesWhithChildsByModule(m, con));
					m.setElements(elementDAO.getModulesWhithChildsByModule(m,
							con));
					m.setPackages(packageDao.getModulesWhithChildsByModule(m,
							con));
					module.add(m);
				}
				return module;
			}
		}
	}

	private Module getModuleFromDB(Connection con, int id)
			throws ClassNotFoundException, SQLException {
		Module module = null;
		try (PreparedStatement prepStatement = con
				.prepareStatement(selectModule)) {
			prepStatement.setInt(1, id);
			try (ResultSet result = prepStatement.executeQuery()) {

				while (result.next()) {
					module = new Module();
					module.setName(result.getString("name"));
					module.setDescription(result.getString("description"));
				}

				return module;
			}
		}
	}

	public boolean save(Module module, Connection con)
			throws ClassNotFoundException, SQLException {
		try (PreparedStatement prepStatement = con
				.prepareStatement(insertModule)) {
			prepStatement.setString(1, module.getId());
			prepStatement.setString(2, module.getName());
			prepStatement.setString(3, module.getDescription());
			prepStatement.setBoolean(4, module.isVariable());
			prepStatement.setString(5, module.getProductLine().getName());
			return prepStatement.execute();
		}
	}

	public boolean createAll(Set<Module> modules, Connection connection)
			throws ClassNotFoundException, SQLException {
		VariabilityDAO variabilityDAO = new VariabilityDAO(properties);
		ElementDAO elementDao = new ElementDAO(properties);
		PackageDAO packageDao = new PackageDAO(properties);

		for (Module m : modules) {
			save(m, connection);
			variabilityDAO.createCollection(m.getVariabilities(), connection);
			elementDao.createCollection(m.getElements(), connection);
			packageDao.createCollection(m.getPackages(), connection);
		}

		return true;
	}

	public boolean createCollection(Set<Module> modules, Connection connection)
			throws ClassNotFoundException, SQLException {
		if (modules == null) {
			return true;
		}
		for (Module m : modules) {
			save(m, connection);
		}

		return true;
	}

}
