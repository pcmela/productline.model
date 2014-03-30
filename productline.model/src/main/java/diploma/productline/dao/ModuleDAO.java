package diploma.productline.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashSet;
import java.util.Properties;
import java.util.Set;

import diploma.productline.entity.Element;
import diploma.productline.entity.Module;
import diploma.productline.entity.ProductLine;
import diploma.productline.entity.Variability;

public class ModuleDAO extends BaseDAO {
	private final String selectModule = "SELECT module_id, name, description, is_variable, product_line_id FROM module WHERE module_id = ?";
	private final String selectModuleByPL = "SELECT module_id, name, description, is_variable, product_line_id FROM module WHERE product_line_id = ?";
	private final String insertModule = "INSERT INTO module (name,description,is_variable,product_line_id) VALUES (?,?,?,?)";
	private final String update = "UPDATE module SET name = ?, description = ?, is_variable = ? WHERE module_id = ?";
	private final String remove = "DELETE FROM module WHERE module_id = ?";


	public Module getModule(int id, Connection con)
			throws ClassNotFoundException, SQLException {
		return getModuleFromDB(con, id);

	}
	
	public Set<Module> getModuleByProductLine(Connection con, int productline_id) throws SQLException{
		Set<Module> module = new HashSet<Module>();
		try (PreparedStatement prepStatement = con
				.prepareStatement(selectModuleByPL)) {
			prepStatement.setInt(1, productline_id);
			try (ResultSet result = prepStatement.executeQuery()) {

				while (result.next()) {
					Module m = new Module();
					m.setId(result.getInt("module_id")); 
					m.setName(result.getString("name"));
					m.setDescription(result.getString("description"));
					m.setVariable(result.getBoolean("is_variable"));
					module.add(m);
				}
				return module;
			}
		}
	}

	public Set<Module> getModulesWhithChildsByProductLine(
			ProductLine productLine, Connection con) throws SQLException{
		VariabilityDAO variabilityDao = new VariabilityDAO();
		ElementDAO elementDAO = new ElementDAO();
		PackageDAO packageDao = new PackageDAO();

		Set<Module> module = new HashSet<Module>();
		try (PreparedStatement prepStatement = con
				.prepareStatement(selectModuleByPL)) {
			prepStatement.setInt(1, productLine.getId());
			try (ResultSet result = prepStatement.executeQuery()) {

				while (result.next()) {
					Module m = new Module();
					m.setId(result.getInt("module_id")); 
					m.setName(result.getString("name"));
					m.setDescription(result.getString("description"));
					m.setVariable(result.getBoolean("is_variable"));
					m.setProductLine(productLine);
					m.setVariabilities(variabilityDao
							.getVariabilitiesWhithChildsByModule(m, con));
					m.setElements(elementDAO.getElementsWhithChildsByModule(m,
							con));
					m.setPackages(packageDao.getPackagesWhithChildsByModule(m,
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
					module.setId(result.getInt("module_id"));
					module.setName(result.getString("name"));
					module.setDescription(result.getString("description"));
					module.setVariable(result.getBoolean("is_variable"));
				}

				return module;
			}
		}
	}

	public int save(Module module, Connection con)
			throws ClassNotFoundException, SQLException {
		try (PreparedStatement prepStatement = con
				.prepareStatement(insertModule, Statement.RETURN_GENERATED_KEYS)) {
			prepStatement.setString(1, module.getName());
			prepStatement.setString(2, module.getDescription());
			prepStatement.setBoolean(3, module.isVariable());
			prepStatement.setInt(4, module.getProductLine().getId());
			prepStatement.execute();
			
			try (ResultSet rs = prepStatement.getGeneratedKeys()) {
				rs.next();
				return rs.getInt(1);
			}
		}
	}

	public boolean createAll(Set<Module> modules, Connection connection)
			throws ClassNotFoundException, SQLException {
		VariabilityDAO variabilityDAO = new VariabilityDAO();
		ElementDAO elementDao = new ElementDAO();
		PackageDAO packageDao = new PackageDAO();

		for (Module m : modules) {
			int id = save(m, connection);
			m.setId(id);
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
	
	public int update(Module module, Connection con) throws SQLException{
		try(PreparedStatement prepareStmt = con.prepareStatement(update)){
			prepareStmt.setString(1, module.getName());
			prepareStmt.setString(2, module.getDescription());
			prepareStmt.setBoolean(3, module.isVariable());
			prepareStmt.setInt(4, module.getId());
			return prepareStmt.executeUpdate();
		}
	}

	public boolean delete(Module m, Connection con) throws SQLException{
		VariabilityDAO vDao = new VariabilityDAO();
		ElementDAO eDao = new ElementDAO();
		
		for(Variability v : m.getVariabilities()){
			vDao.delete(v.getId(), con);
		}
		
		for(Element e : m.getElements()){
			eDao.delete(e, con);
		}
		
		try(PreparedStatement prepStatement = con.prepareStatement(remove)){
			prepStatement.setLong(1, m.getId());
			return prepStatement.execute();
		}
	}
}
