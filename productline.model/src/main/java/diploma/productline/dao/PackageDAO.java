package diploma.productline.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashSet;
import java.util.Set;

import diploma.productline.entity.Module;
import diploma.productline.entity.PackageModule;

public class PackageDAO extends BaseDAO {

	private final String selectPackage = "SELECT package_id, name, module_id FROM package WHERE id like ?";
	private final String selectModuleIdByPackageName = "SELECT m.module_id FROM module AS m JOIN package AS p ON (m.module_id  = p.module_id) AND (m.product_line_id = ?) AND (p.name = ?)";
	private final String selectPackageByModule = "SELECT package_id, name FROM package WHERE module_id like ?";
	private final String insertElement = "INSERT INTO package (name, module_id) VALUES (?,?)";
	private final String removePackage = "DELETE FROM package WHERE package_id = ?";


	public PackageModule getPackage(long id, Connection con)
			throws ClassNotFoundException, SQLException {
		return getPackageFromDB(con, id);
	}

	public String getModuleIdByPackageName(String productLine, String packageName, Connection con)
			throws ClassNotFoundException, SQLException {
		String m = null;
		try (PreparedStatement prepStatement = con
				.prepareStatement(selectModuleIdByPackageName)) {
			prepStatement.setString(1, productLine);
			prepStatement.setString(2, packageName);
			try (ResultSet result = prepStatement.executeQuery()) {

				while (result.next()) {
					m = result.getString("module_id");
				}
			}
		}

		return m;
	}
	
	private PackageModule getPackageFromDB(Connection con, long id)
			throws ClassNotFoundException, SQLException {
		PackageModule pkg = null;
		try (PreparedStatement prepStatement = con
				.prepareStatement(selectPackage)) {
			prepStatement.setLong(1, id);
			try (ResultSet result = prepStatement.executeQuery()) {

				while (result.next()) {
					pkg = new PackageModule();
					pkg.setName(result.getString("name"));
					pkg.setId(result.getLong("package_id"));
				}
			}
		}

		return pkg;
	}
	
/*	private String getModuleId(Connection con, String name)
			throws ClassNotFoundException, SQLException {
		PackageModule pkg = null;
		try (PreparedStatement prepStatement = con
				.prepareStatement(selectModuleIdByPackageName)) {
			prepStatement.setString(1, name);
			try (ResultSet result = prepStatement.executeQuery()) {

				while (result.next()) {
					pkg = new PackageModule();
					pkg.setName(result.getString("name"));
					pkg.setId(result.getLong("package_id"));
				}
			}
		}

		return pkg;
	}*/

	public Set<PackageModule> getPackagesWhithChildsByModule(Module module,
			Connection con) throws SQLException {
		Set<PackageModule> pkgs = new HashSet<PackageModule>();
		try (PreparedStatement prepStatement = con
				.prepareStatement(selectPackageByModule)) {
			prepStatement.setInt(1, module.getId());
			try (ResultSet result = prepStatement.executeQuery()) {

				while (result.next()) {
					PackageModule pkg = new PackageModule();
					pkg.setName(result.getString("name"));
					pkg.setId(result.getLong("package_id"));
					pkg.setModule(module);
					pkgs.add(pkg);
				}
			}
		}

		return pkgs;
	}

	public int save(PackageModule pkg, Connection con)
			throws ClassNotFoundException, SQLException {
		try (PreparedStatement prepStatement = con
				.prepareStatement(insertElement, Statement.RETURN_GENERATED_KEYS)) {
			prepStatement.setString(1, pkg.getName());
			prepStatement.setInt(2, pkg.getModule().getId());

			try (ResultSet rs = prepStatement.getGeneratedKeys()) {
				rs.next();
				return rs.getInt(1);
			}
		}
	}

	public boolean createCollection(Set<PackageModule> pkg,
			Connection connection) throws ClassNotFoundException, SQLException {
		if (pkg == null) {
			return true;
		}
		for (PackageModule m : pkg) {
			save(m, connection);
		}

		return true;
	}
	
	public boolean delete(Long id, Connection con) throws SQLException{
		try(PreparedStatement prepStatement = con.prepareStatement(removePackage)){
			prepStatement.setLong(1, id);
			return prepStatement.execute();
		}
	}
}
