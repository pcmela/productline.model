package diploma.productline.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashSet;
import java.util.Properties;



import java.util.Set;

import org.apache.cassandra.cli.CliParser.newColumnFamily_return;

import diploma.productline.DaoUtil;
import diploma.productline.entity.Module;
import diploma.productline.entity.ProductLine;

public class ModuleDAO extends BaseDAO{
	private final String selectModule = "SELECT module_id, name, is_variable, product_line_id FROM module WHERE module_id like ?";
	private final String selectModuleByPL = "SELECT id, name, is_variable, product_line_id FROM module WHERE product_line_id like ?";

	public ModuleDAO(Properties properties) {
		super(properties);
	}
	
	public Module getModule(int id)
			throws ClassNotFoundException, SQLException {
		Connection con = DaoUtil.connect(properties);
		return getModuleFromDB(con, id);

	}
	
	public Set<Module> getModulesWhithChildsByProductLine(ProductLine productLine) throws SQLException, ClassNotFoundException{
		VariabilityDAO variabilityDao = new VariabilityDAO(properties);
		ElementDAO elementDAO = new ElementDAO(properties);
		
		Set<Module> module = new HashSet<Module>();
		Connection con = DaoUtil.connect(properties);
		PreparedStatement prepStatement = con
				.prepareStatement(selectModuleByPL);
		prepStatement.setString(1, productLine.getName());
		ResultSet result = prepStatement.executeQuery();

		while (result.next()) {
			Module m = new Module();
			m.setName(result.getString("name"));
			m.setDescription(result.getString("description"));
			m.setProductLine(productLine);
			m.setVariabilities(variabilityDao.getModulesWhithChildsByModule(m));
			m.setElements(elementDAO.getModulesWhithChildsByModule(m));
			module.add(m);
		}
		return module;
	}
	
	private Module getModuleFromDB(Connection con, int id)
			throws ClassNotFoundException, SQLException {
		Module module = null;
		PreparedStatement prepStatement = con
				.prepareStatement(selectModule);
		prepStatement.setInt(1, id);
		ResultSet result = prepStatement.executeQuery();

		while (result.next()) {
			module = new Module();
			module.setName(result.getString("name"));
			module.setDescription(result.getString("description"));
		}

		return module;
	}

	
}
