package diploma.productline.dao;

import java.util.Properties;

public abstract class BaseDAO {

	protected Properties properties;
	
	public BaseDAO(Properties properties) {
		this.properties = properties;
	}
	
}
