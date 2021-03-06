package diploma.productline.dao;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.Reader;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Types;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Properties;
import java.util.Set;

import diploma.productline.entity.Element;
import diploma.productline.entity.Module;
import diploma.productline.entity.ProductLine;
import diploma.productline.entity.Variability;

public class ProductLineDAO extends BaseDAO {

	private final String selectProductLine = "SELECT productline_id, name, description, parent_productline FROM productline WHERE productline_id = ?";
	private final String insertProductLine = "INSERT INTO productline (name, description, parent_productline) VALUES (?,?,?)";
	private final String updateProductLine = "UPDATE productline SET name = ?, description = ? WHERE productline_id = ?";
	private final String selectProductLinesByParent = "SELECT productline_id, name, description, parent_productline FROM productline WHERE parent_productline = ?";
	private final String selectAllProductLine = "SELECT productline_id, name, description, parent_productline FROM productline";
	private static final String selectNamOfChild = "select name from productline where parent_productline = ?";
	private final String remove = "DELETE FROM productline WHERE productline_id = ?";

	public static void createDatabaseStructure(Reader ddl, Connection connection)
			throws ClassNotFoundException, SQLException, FileNotFoundException,
			IOException {		
		ScriptRunner runner = new ScriptRunner(connection, true, true);
		runner.runScript(ddl);
	}

	public static Set<String> getNamesOfChild(Connection con, int id)
			throws SQLException {
		Set<String> set = new HashSet<>();
		try (PreparedStatement prepStatement = con
				.prepareStatement(selectNamOfChild)) {
			prepStatement.setInt(1, id);
			try (ResultSet result = prepStatement.executeQuery()) {

				while (result.next()) {
					set.add(result.getString("name"));
				}
			}
		}
		return set;
	}

	public ProductLine getProductLine(int id, Connection connection)
			throws SQLException {
		ProductLine productLine = getProductLineFromDBWithChild(connection, id);
		return productLine;
	}

	private ProductLine getProductLineFromDBWithChild(Connection con, int id)
			throws SQLException {

		ModuleDAO moduleDao = new ModuleDAO();

		ProductLine productLine = null;
		try (PreparedStatement prepStatement = con
				.prepareStatement(selectProductLine)) {
			prepStatement.setInt(1, id);
			try (ResultSet result = prepStatement.executeQuery()) {

				while (result.next()) {
					productLine = new ProductLine();
					productLine.setId(result.getInt("productline_id"));
					productLine.setName(result.getString("name"));
					productLine.setDescription(result.getString("description"));
					Integer parent = result.getInt("parent_productline");
					if (result.wasNull()) {
						productLine.setParent(null);
					} else {
						productLine.setParent(getProductLine(parent, con));
					}

				}
				if (productLine != null) {
					productLine.setModules(moduleDao
							.getModulesWhithChildsByProductLine(productLine,
									con));
				}
			}
		}
		return productLine;
	}

	private ProductLine getProductLineFromDB(Connection con, int id)
			throws SQLException {

		ProductLine productLine = null;
		try (PreparedStatement prepStatement = con
				.prepareStatement(selectProductLine)) {
			prepStatement.setInt(1, id);
			try (ResultSet result = prepStatement.executeQuery()) {

				while (result.next()) {
					productLine = new ProductLine();
					productLine.setId(result.getInt("productline_id"));
					productLine.setName(result.getString("name"));
					productLine.setDescription(result.getString("description"));
					Integer parent = result.getInt("parent_productline");
					if (result.wasNull()) {
						productLine.setParent(null);
					} else {
						productLine.setParent(getProductLine(parent, con));
					}

				}
			}
		}
		return productLine;
	}

	private ProductLine[] getProductLinesByParentFromDB(Connection con,
			int parent_id) throws ClassNotFoundException, SQLException {

		ModuleDAO moduleDao = new ModuleDAO();

		ProductLine productLine = null;
		try (PreparedStatement prepStatement = con
				.prepareStatement(selectProductLinesByParent)) {
			prepStatement.setInt(1, parent_id);
			Set<ProductLine> resultSet = new HashSet<>();
			try (ResultSet result = prepStatement.executeQuery()) {

				while (result.next()) {
					productLine = new ProductLine();
					productLine.setId(result.getInt("productline_id"));
					productLine.setName(result.getString("name"));
					productLine.setDescription(result.getString("description"));
					Integer parent = result.getInt("parent_productline");
					if (result.wasNull()) {
						productLine.setParent(null);
					} else {
						productLine.setParent(getProductLine(parent, con));
					}
					productLine.setModules(moduleDao
							.getModulesWhithChildsByProductLine(productLine,
									con));
					resultSet.add(productLine);
				}
			}
			return resultSet.toArray(new ProductLine[resultSet.size()]);
		}
	}

	public ProductLine getProductLineWithChilds(int id, Connection con)
			throws ClassNotFoundException, SQLException {
		ProductLine productLine = null;
		productLine = getProductLineFromDBWithChild(con, id);

		return productLine;
	}

	public ProductLine[] getProductLineByParent(int parent_id, Connection con)
			throws ClassNotFoundException, SQLException {
		return getProductLinesByParentFromDB(con, parent_id);
	}

	public int save(ProductLine productLine, Connection con)
			throws ClassNotFoundException, SQLException {
		try (PreparedStatement prepStatement = con.prepareStatement(
				insertProductLine, Statement.RETURN_GENERATED_KEYS)) {
			prepStatement.setString(1, productLine.getName());
			prepStatement.setString(2, productLine.getDescription());
			if (productLine.getParent() == null) {
				prepStatement.setNull(3, Types.NULL);
			} else {
				prepStatement.setInt(3, productLine.getParent().getId());
			}
			boolean a = prepStatement.execute();

			try (ResultSet rs = prepStatement.getGeneratedKeys()) {
				rs.next();
				return rs.getInt(1);
			}
		}
	}

	public int createAll(ProductLine productLine, Connection connection)
			throws ClassNotFoundException, SQLException {
		ModuleDAO moduleDao = new ModuleDAO();

		int plId = this.save(productLine, connection);
		productLine.setId(plId);
		moduleDao.createAll(productLine.getModules(), connection);
		return plId;
	}

	public int update(ProductLine productLine, Connection con)
			throws SQLException {
		try (PreparedStatement prepareStmt = con
				.prepareStatement(updateProductLine)) {
			prepareStmt.setString(1, productLine.getName());
			prepareStmt.setString(2, productLine.getDescription());
			prepareStmt.setInt(3, productLine.getId());
			return prepareStmt.executeUpdate();
		}
	}

	public Set<ProductLine> getProductLine(Connection con) throws SQLException {
		Set<ProductLine> set = new HashSet<ProductLine>();
		try (PreparedStatement prepareStmt = con
				.prepareStatement(selectAllProductLine)) {
			ResultSet result = prepareStmt.executeQuery();

			while (result.next()) {
				ProductLine p = new ProductLine();
				p.setId(result.getInt("productline_id"));
				p.setName(result.getString("name"));
				p.setDescription(result.getString("description"));
				Integer parent = result.getInt("parent_productline");
				if (result.wasNull()) {
					p.setParent(null);
				} else {
					p.setParent(getProductLineFromDB(con, parent));
				}
				set.add(p);
			}
		}
		return set;
	}

	public boolean delete(ProductLine p, Connection con) throws SQLException {
		ModuleDAO mDao = new ModuleDAO();

		for (Module m : p.getModules()) {
			mDao.delete(m, con);
		}

		try (PreparedStatement prepStatement = con.prepareStatement(remove)) {
			prepStatement.setLong(1, p.getId());
			return prepStatement.execute();
		}
	}
}
