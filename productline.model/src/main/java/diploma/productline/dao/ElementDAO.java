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
import diploma.productline.entity.Resource;

public class ElementDAO extends BaseDAO {

	private final String selectElement = "SELECT element_id, name, description, type_id FROM element WHERE id = ?";
	private final String selectElementByModule = "SELECT element_id, name, description, type_id FROM element WHERE module_id = ?";
	private final String insertElement = "INSERT INTO element (name,description,type_id,module_id) VALUES (?,?,?,?)";
	private final String update = "UPDATE element SET name = ?, description = ?, type_id = ? WHERE element_id = ?";
	private final String remove = "DELETE FROM element WHERE element_id = ?";


	public Element getElement(String id, Connection con)
			throws ClassNotFoundException, SQLException {
		return getElementFromDB(con, id);
	}

	private Element getElementFromDB(Connection con, String id)
			throws ClassNotFoundException, SQLException {
		ElementTypeDAO etDao = new ElementTypeDAO();
		
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
					element.setType(etDao.getElementType(con, result.getInt("type_id")));
				}
			}
		}
		return element;
	}

	public Set<Element> getElementsWhithChildsByModule(Module module,
			Connection con) throws SQLException {
		ResourceDao rDao = new ResourceDao();
		ElementTypeDAO etDao = new ElementTypeDAO();
		
		
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
					e.setType(etDao.getElementType(con, result.getInt("type_id")));
					e.setModule(module);
					e.setResources(rDao.getResourceWhithChildsByElement(e, con));
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
			if(element.getType() == null){
				prepStatement.setNull(3, Types.NULL);
			}else{
				prepStatement.setInt(3, element.getType().getId());
			}
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
	
	public int update(Element element, Connection con) throws SQLException{
		try(PreparedStatement prepareStmt = con.prepareStatement(update)){
			prepareStmt.setString(1, element.getName());
			prepareStmt.setString(2, element.getDescription());
			prepareStmt.setInt(3, element.getType().getId());
			prepareStmt.setInt(4, element.getId());
			return prepareStmt.executeUpdate();
		}
	}
	
	public boolean delete(Element element, Connection con) throws SQLException{
		ResourceDao rDao = new ResourceDao();
		for(Resource r : element.getResources()){
			rDao.delete(r.getId(), con);
		}
		try(PreparedStatement prepareStmt = con.prepareStatement(remove)){
			prepareStmt.setInt(1, element.getId());
			return prepareStmt.execute();
		}
	}
}
