package diploma.productline.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Types;
import java.util.HashSet;
import java.util.Set;

import diploma.productline.entity.Element;
import diploma.productline.entity.Module;

public class ElementDAO extends BaseDAO {

	private final String selectElement = "SELECT element_id, name, description FROM element WHERE id = ?";
	private final String selectElementByModule = "SELECT element_id, name, description FROM element WHERE module_id = ?";
	private final String insertElement = "INSERT INTO element (name,description,type_id,module_id) VALUES (?,?,?,?)";
	private final String update = "UPDATE element SET name = ?, description = ? WHERE element_id = ?";


	public Element getElement(String id, Connection con)
			throws ClassNotFoundException, SQLException {
		return getElementFromDB(con, id);
	}

	private Element getElementFromDB(Connection con, String id)
			throws ClassNotFoundException, SQLException {
		Element element = null;
		try (PreparedStatement prepStatement = con
				.prepareStatement(selectElement)) {
			prepStatement.setString(1, id);
			try (ResultSet result = prepStatement.executeQuery()) {

				while (result.next()) {
					element = new Element();
					element.setName(result.getString("name"));
					element.setId(result.getInt("element_id"));
					element.setDescription(result.getString("description"));
				}
			}
		}
		return element;
	}

	public Set<Element> getModulesWhithChildsByModule(Module module,
			Connection con) throws SQLException {
		Set<Element> elements = new HashSet<Element>();
		try (PreparedStatement prepStatement = con
				.prepareStatement(selectElementByModule)) {
			prepStatement.setInt(1, module.getId());
			try (ResultSet result = prepStatement.executeQuery()) {

				while (result.next()) {
					Element e = new Element();
					e.setName(result.getString("name"));
					e.setId(result.getInt("element_id"));
					e.setDescription(result.getString("description"));
					e.setModule(module);
					elements.add(e);
				}
			}
		}
		return elements;
	}

	public int save(Element element, Connection con)
			throws ClassNotFoundException, SQLException {
		try (PreparedStatement prepStatement = con
				.prepareStatement(insertElement, Statement.RETURN_GENERATED_KEYS)) {
			prepStatement.setString(1, element.getName());
			prepStatement.setString(2, element.getDescription());
			prepStatement.setNull(3, Types.NULL);
			prepStatement.setInt(4, element.getModule().getId());
			prepStatement.execute();

			try (ResultSet rs = prepStatement.getGeneratedKeys()) {
				rs.next();
				return rs.getInt(1);
			}
		}
	}

	public boolean createCollection(Set<Element> element, Connection connection)
			throws ClassNotFoundException, SQLException {
		if (element == null) {
			return true;
		}
		for (Element m : element) {
			save(m, connection);
		}

		return true;
	}
	
	public int update(Element module, Connection con) throws SQLException{
		try(PreparedStatement prepareStmt = con.prepareStatement(update)){
			prepareStmt.setString(1, module.getName());
			prepareStmt.setString(2, module.getDescription());
			prepareStmt.setInt(3, module.getId());
			return prepareStmt.executeUpdate();
		}
	}
}
