package diploma.productline.entity;

public class Resource extends BaseProductLineEntity {

	private int id;
	private String name;
	private String relativePath;
	private String fullPath;
	private Element element;
	
	
	@Override
	public String getName() {
		return name;
	}


	public int getId() {
		return id;
	}


	public void setId(int id) {
		this.id = id;
	}


	public String getRelativePath() {
		return relativePath;
	}


	public void setRelativePath(String relativePath) {
		this.relativePath = relativePath;
	}


	public String getFullPath() {
		return fullPath;
	}


	public void setFullPath(String fullPath) {
		this.fullPath = fullPath;
	}


	public Element getElement() {
		return element;
	}


	public void setElement(Element element) {
		this.element = element;
	}


	public void setName(String name) {
		this.name = name;
	}

	
}
