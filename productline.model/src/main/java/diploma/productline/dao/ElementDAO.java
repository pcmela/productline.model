package diploma.productline.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.HashSet;
import java.util.Properties;
import java.util.Set;

import diploma.productline.entity.Element;
import diploma.productline.entity.Module;

public class ElementDAO extends BaseDAO {

	private final String selectElement = "SELECT element_id, name, description FROM element WHERE id = ?";
	private final String selectElementByModule = "SELECT element_id, name, description FROM element WHERE module_id = ?";
	private final String insertElement = "INSERT INTO element (name,description,type_id,module_id) VALUES (?,?,?,?)";

	public ElementDAO(Properties properties) {
		super(properties);
	}

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
					element.setId(result.getString("element_id"));
					element.setDescription(result.getString("description"));
				}
			}
		}
		return element;
	}

	public Set<Element> getModulesWhithChildsByModule(Module module,
			Connection con) throws SQLException, ClassNotFoundException {
		Set<Element> elements = new HashSet<Element>();
		try (PreparedStatement prepStatement = con
				.prepareStatement(selectElementByModule)) {
			prepStatement.setString(1, module.getId());
			try (ResultSet result = prepStatement.executeQuery()) {

				while (result.next()) {
					Element e = new Element();
					e.setName(result.getString("name"));
					e.setId(result.getString("element_id"));
					e.setDescription(result.getString("description"));
					e.setModule(module);
					elements.add(e);
				}
			}
		}
		return elements;
	}

	public boolean save(Element element, Connection con)
			throws ClassNotFoundException, SQLException {
		try (PreparedStatement prepStatement = con
				.prepareStatement(insertElement)) {
			prepStatement.setString(1, element.getName());
			prepStatement.setString(2, element.getDescription());
			prepStatement.setNull(3, Types.NULL);
			prepStatement.setString(4, element.getModule().getId());
			return prepStatement.execute();
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
}
