package diploma.productline.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashSet;
import java.util.Properties;
import java.util.Set;

import diploma.productline.DaoUtil;
import diploma.productline.entity.Module;
import diploma.productline.entity.ProductLine;
import diploma.productline.entity.Variability;

public class VariabilityDAO extends BaseDAO{
	
	private final String selectVariability = "SELECT id, name FROM variability WHERE id like ?";
	private final String selectVariabilityByPL = "SELECT id, name FROM variability WHERE module_id like ?";

	public VariabilityDAO(Properties properties) {
		super(properties);
	}
	
	public Variability getVariability(String id) throws ClassNotFoundException, SQLException {
		Connection con = DaoUtil.connect(properties);
		return getVariabilityFromDB(con, id);
	}
	
	private Variability getVariabilityFromDB(Connection con, String id)
			throws ClassNotFoundException, SQLException {
		Variability variability = null;
		PreparedStatement prepStatement = con
				.prepareStatement(selectVariability);
		prepStatement.setString(1, id);
		ResultSet result = prepStatement.executeQuery();

		while (result.next()) {
			variability = new Variability();
			variability.setName(result.getString("name"));
			variability.setId(result.getString("id"));
		}

		return variability;
	}
	
	public Set<Variability> getModulesWhithChildsByModule(Module module) throws SQLException, ClassNotFoundException{
		Set<Variability> variabilities = new HashSet<Variability>();
		Connection con = DaoUtil.connect(properties);
		PreparedStatement prepStatement = con
				.prepareStatement(selectVariabilityByPL);
		prepStatement.setString(1, module.getId());
		ResultSet result = prepStatement.executeQuery();

		while (result.next()) {
			Variability v = new Variability();
			v.setName(result.getString("name"));
			v.setId(result.getString("id"));
			v.setModule(module);
			variabilities.add(v);
		}
		
		return variabilities;
	}

}
