package diploma.productline.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import diploma.productline.entity.Type;

public class ElementTypeDAO extends BaseDAO{
	private final String ELEMENT_BY_ID = "SELECT type_id, name FROM type WHERE type_id = ?";
	
	public Type getElementType(Connection con, int id)
			throws SQLException {
		Type type = null;
		try (PreparedStatement prepStatement = con
				.prepareStatement(ELEMENT_BY_ID)) {
			prepStatement.setInt(1, id);
			try (ResultSet result = prepStatement.executeQuery()) {

				while (result.next()) {
					type = new Type();
					type.setId(result.getInt("type_id"));
					type.setName(result.getString("name"));
				}

				return type;
			}
		}
	}
}
