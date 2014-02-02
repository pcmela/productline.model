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
import diploma.productline.entity.PackageModule;

public class PackageDAO extends BaseDAO{

	private final String selectPackage = "SELECT package_id, name FROM package WHERE id like ?";
	private final String selectPackageByModule = "SELECT package_id, name FROM package WHERE module_id like ?";
	private final String insertElement = "INSERT INTO package VALUES (?,?,?)";

	public PackageDAO(Properties properties) {
		super(properties);
	}
	
	public PackageModule getVariability(long id) throws ClassNotFoundException, SQLException {
		Connection con = DaoUtil.connect(properties);
		return getPackageFromDB(con, id);
	}
	
	private PackageModule getPackageFromDB(Connection con, long id)
			throws ClassNotFoundException, SQLException {
		PackageModule pkg = null;
		PreparedStatement prepStatement = con
				.prepareStatement(selectPackage);
		prepStatement.setLong(1, id);
		ResultSet result = prepStatement.executeQuery();

		while (result.next()) {
			pkg = new PackageModule();
			pkg.setName(result.getString("name"));
			pkg.setId(result.getLong("id"));
		}

		return pkg;
	}
	
	public Set<PackageModule> getModulesWhithChildsByModule(Module module) throws SQLException, ClassNotFoundException{
		Set<PackageModule> pkgs = new HashSet<PackageModule>();
		Connection con = DaoUtil.connect(properties);
		PreparedStatement prepStatement = con
				.prepareStatement(selectPackageByModule);
		prepStatement.setString(1, module.getId());
		ResultSet result = prepStatement.executeQuery();

		while (result.next()) {
			PackageModule pkg = new PackageModule();
			pkg.setName(result.getString("name"));
			pkg.setId(result.getLong("id"));
			pkg.setModule(module);
			pkgs.add(pkg);
		}
		
		return pkgs;
	}
	
	
	public boolean save(PackageModule pkg, Connection connection) throws ClassNotFoundException, SQLException{
		Connection con = null;
		if(connection == null){
			con = DaoUtil.connect(properties);
		}else{
			con = connection;
		}
		PreparedStatement prepStatement = con.prepareStatement(insertElement);
		prepStatement.setLong(1, pkg.getId());
		prepStatement.setString(2, pkg.getName());
		prepStatement.setString(3, pkg.getModule().getId());
		return prepStatement.execute();
	}
	
	public boolean createCollection(Set<PackageModule> pkg, Connection connection)
			throws ClassNotFoundException, SQLException {
		if(pkg == null){
			return true;
		}
		for (PackageModule m : pkg) {
			save(m, connection);
		}

		return true;
	}
}
