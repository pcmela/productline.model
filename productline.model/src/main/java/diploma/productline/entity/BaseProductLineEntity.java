package diploma.productline.entity;

public abstract class BaseProductLineEntity {

	protected boolean isDirty = false;
	public abstract String getName();
	
	public boolean isDirty() {
		return isDirty;
	}
	public void setDirty(boolean isDirty) {
		this.isDirty = isDirty;
	}
	
	
}
