package diploma.productline.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashSet;
import java.util.Set;

import diploma.productline.entity.Module;
import diploma.productline.entity.Variability;

public class VariabilityDAO extends BaseDAO {

	private final String selectVariability = "SELECT variability_id, name FROM variability WHERE id like ?";
	private final String selectVariabilityByPL = "SELECT variability_id, name FROM variability WHERE module_id like ?";
	private final String insertVariability = "INSERT INTO variability (name,description,module_id) VALUES (?,?,?)";
	private final String update = "UPDATE variability SET name = ?, description = ? WHERE variability_id = ?";


	public Variability getVariability(String id, Connection con)
			throws ClassNotFoundException, SQLException {
		return getVariabilityFromDB(con, id);
	}

	private Variability getVariabilityFromDB(Connection con, String id)
			throws ClassNotFoundException, SQLException {
		Variability variability = null;
		try (PreparedStatement prepStatement = con
				.prepareStatement(selectVariability)) {
			prepStatement.setString(1, id);
			try (ResultSet result = prepStatement.executeQuery()) {

				while (result.next()) {
					variability = new Variability();
					variability.setName(result.getString("name"));
					variability.setId(result.getInt("variability_id"));
				}
			}
		}
		return variability;
	}

	public Set<Variability> getVariabilitiesWhithChildsByModule(Module module,
			Connection con) throws SQLException {
		Set<Variability> variabilities = new HashSet<Variability>();
		try (PreparedStatement prepStatement = con
				.prepareStatement(selectVariabilityByPL)) {
			prepStatement.setInt(1, module.getId());
			try (ResultSet result = prepStatement.executeQuery()) {

				while (result.next()) {
					Variability v = new Variability();
					v.setName(result.getString("name"));
					v.setId(result.getInt("variability_id"));
					v.setModule(module);
					variabilities.add(v);
				}
			}
		}
		return variabilities;
	}
	
	public Set<Variability> getVariabilitiesByModuleId(String moduleId,
			Connection con) throws SQLException, ClassNotFoundException {
		Set<Variability> variabilities = new HashSet<Variability>();
		try (PreparedStatement prepStatement = con
				.prepareStatement(selectVariabilityByPL)) {
			prepStatement.setString(1, moduleId);
			try (ResultSet result = prepStatement.executeQuery()) {

				while (result.next()) {
					Variability v = new Variability();
					v.setName(result.getString("name"));
					v.setId(result.getInt("variability_id"));
					variabilities.add(v);
				}
			}
		}
		return variabilities;
	}

	public int save(Variability variability, Connection con)
			throws ClassNotFoundException, SQLException {
		try (PreparedStatement prepStatement = con
				.prepareStatement(insertVariability, Statement.RETURN_GENERATED_KEYS)) {
			prepStatement.setString(1, variability.getName());
			prepStatement.setString(2, variability.getDescription());
			prepStatement.setInt(3, variability.getModule().getId());
			prepStatement.execute();
			try (ResultSet rs = prepStatement.getGeneratedKeys()) {
				rs.next();
				return rs.getInt(1);
			}
		}
	}

	public boolean createCollection(Set<Variability> variability,
			Connection connection) throws ClassNotFoundException, SQLException {
		if (variability == null)
			return true;
		for (Variability m : variability) {
			save(m, connection);
		}

		return true;
	}
	
	public int update(Variability variability, Connection con) throws SQLException{
		try(PreparedStatement prepareStmt = con.prepareStatement(update)){
			prepareStmt.setString(1, variability.getName());
			prepareStmt.setString(2, variability.getDescription());
			prepareStmt.setInt(3, variability.getId());
			return prepareStmt.executeUpdate();
		}
	}
}
