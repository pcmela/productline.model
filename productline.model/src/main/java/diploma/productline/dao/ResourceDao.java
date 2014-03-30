package diploma.productline.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashSet;
import java.util.Set;

import diploma.productline.entity.Element;
import diploma.productline.entity.Resource;

public class ResourceDao extends BaseDAO {

	private final String checkIfResourceExist = "SELECT resource_id FROM resource WHERE relative_path = ?";
	private final String insertResource = "INSERT INTO resource (name,relative_path,full_path,element_id) VALUES (?,?,?,?)";
	private final String update = "UPDATE resource SET name = ?, relative_path = ?, full_path = ? WHERE resource_id = ?";
	private final String selectResourceByElement = "SELECT resource_id, name, relative_path, full_path FROM resource WHERE element_id = ?";
	private final String remove = "DELETE FROM resource WHERE resource_id = ?";
	
	public Set<Resource> getResourceWhithChildsByElement(Element element,
			Connection con) throws SQLException {
		Set<Resource> resources = new HashSet<Resource>();
		try (PreparedStatement prepStatement = con
				.prepareStatement(selectResourceByElement)) {
			prepStatement.setInt(1, element.getId());
			try (ResultSet result = prepStatement.executeQuery()) {

				while (result.next()) {
					Resource e = new Resource();
					e.setName(result.getString("name"));
					e.setId(result.getInt("resource_id"));
					e.setRelativePath(result.getString("relative_path"));
					e.setFullPath(result.getString("full_path"));
					e.setElement(element);
					resources.add(e);
				}
			}
		}
		return resources;
	}
	
	public boolean createCollection(Set<Resource> resource, Connection connection)
			throws ClassNotFoundException, SQLException {
		if (resource == null) {
			return true;
		}
		for (Resource m : resource) {
			save(m, connection);
		}

		return true;
	}
	
	public int save(Resource resource, Connection con)
			throws ClassNotFoundException, SQLException {
		try(PreparedStatement prepStatment = con.prepareStatement(checkIfResourceExist)){
			prepStatment.setString(1, resource.getRelativePath());
			try(ResultSet result = prepStatment.executeQuery()){
				boolean exist = false;
				while(result.next()){
					exist = true;
					break;
				}
				if(exist){
					return -1;
				}
			}
			
		}
		
		try (PreparedStatement prepStatement = con
				.prepareStatement(insertResource, Statement.RETURN_GENERATED_KEYS)) {
			prepStatement.setString(1, resource.getName());
			prepStatement.setString(2, resource.getRelativePath());
			prepStatement.setString(3, resource.getFullPath());
			prepStatement.setInt(4, resource.getElement().getId());
			prepStatement.execute();
			
			try (ResultSet rs = prepStatement.getGeneratedKeys()) {
				rs.next();
				return rs.getInt(1);
			}
		}
	}
	
	public int update(Resource resource, Connection con) throws SQLException{
		try(PreparedStatement prepareStmt = con.prepareStatement(update)){
			prepareStmt.setString(1, resource.getName());
			prepareStmt.setString(2, resource.getRelativePath());
			prepareStmt.setString(3, resource.getFullPath());
			prepareStmt.setInt(4, resource.getId());
			return prepareStmt.executeUpdate();
		}
	}
	
	public boolean delete(int id, Connection con) throws SQLException{
		try(PreparedStatement prepStatement = con.prepareStatement(remove)){
			prepStatement.setLong(1, id);
			return prepStatement.execute();
		}
	}

}
