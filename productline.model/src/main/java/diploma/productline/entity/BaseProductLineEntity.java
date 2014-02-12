package diploma.productline.entity;

import java.io.Serializable;
import java.util.Properties;

public abstract class BaseProductLineEntity implements Serializable{

	protected boolean isDirty = false;
	protected Properties databaseProperties;
	public abstract String getName();
	
	public boolean isDirty() {
		return isDirty;
	}
	public void setDirty(boolean isDirty) {
		this.isDirty = isDirty;
	}

	public Properties getDatabaseProperties() {
		return databaseProperties;
	}

	public void setDatabaseProperties(Properties databaseProperties) {
		this.databaseProperties = databaseProperties;
	}
	
	
}
