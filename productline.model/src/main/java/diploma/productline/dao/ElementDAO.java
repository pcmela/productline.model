package diploma.productline.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashSet;
import java.util.Properties;
import java.util.Set;

import diploma.productline.DaoUtil;
import diploma.productline.entity.Element;
import diploma.productline.entity.Module;

public class ElementDAO extends BaseDAO{

	private final String selectElement = "SELECT id, name, description FROM element WHERE id = ?";
	private final String selectElementByModule = "SELECT id, name, description FROM element WHERE module_id = ?";

	public ElementDAO(Properties properties) {
		super(properties);
	}
	
	public Element getElement(String id) throws ClassNotFoundException, SQLException {
		Connection con = DaoUtil.connect(properties);
		return getElementFromDB(con, id);
	}
	
	private Element getElementFromDB(Connection con, String id)
			throws ClassNotFoundException, SQLException {
		Element element = null;
		PreparedStatement prepStatement = con
				.prepareStatement(selectElement);
		prepStatement.setString(1, id);
		ResultSet result = prepStatement.executeQuery();

		while (result.next()) {
			element = new Element();
			element.setName(result.getString("name"));
			element.setId(result.getString("id"));
			element.setDescription(result.getString("description"));
		}

		return element;
	}
	
	public Set<Element> getModulesWhithChildsByModule(Module module) throws SQLException, ClassNotFoundException{
		Set<Element> elements = new HashSet<Element>();
		Connection con = DaoUtil.connect(properties);
		PreparedStatement prepStatement = con
				.prepareStatement(selectElementByModule);
		prepStatement.setString(1, module.getId());
		ResultSet result = prepStatement.executeQuery();

		while (result.next()) {
			Element e = new Element();
			e.setName(result.getString("name"));
			e.setId(result.getString("id"));
			e.setDescription(result.getString("description"));
			e.setModule(module);
			elements.add(e);
		}
		
		return elements;
	}
}
