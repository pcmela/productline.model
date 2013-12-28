package diploma.productline.validate;

import javax.validation.Path;

public class ProductLineErrors {

	
	public ProductLineErrors(Class<?> className, String message, Path path) {
		super();
		this.className = className;
		this.message = message;
		this.path = path;
		
	}
	
	private Class<?> className;
	private String message;
	private Path path;
	
	
	public Class<?> getClassName() {
		return className;
	}
	public void setClassName(Class<?> className) {
		this.className = className;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	public Path getPath() {
		return path;
	}
	public void setPath(Path path) {
		this.path = path;
	}	
	
}
