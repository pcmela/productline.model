package diploma.productline.entity;

import java.util.Set;




public class Element extends BaseProductLineEntity{


	private Integer id;
	private String name;	
	private String description;	
	private Type type;
	private Module module; 
	private Set<Resource> resources;
	private String textOfType;
	
	public Set<Resource> getResources() {
		return resources;
	}
	public void setResources(Set<Resource> resources) {
		this.resources = resources;
	}
	public Module getModule() {
		return module;
	}
	public void setModule(Module module) {
		this.module = module;
	}
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public Type getType() {
		return type;
	}
	public void setType(Type type) {
		textOfType = type.getName();
		this.type = type;
	}
	
	public String getTextOfType() {
		return textOfType;
	}
	public void setTextOfType(String textOfType) {
		Type t = new Type();
		ElementType et = ElementType.get(textOfType);
		t.setId(et.getId());
		t.setName(et.toString());
		type = t;
		this.textOfType = textOfType;
	}
	@Override
	public String toString() {
		return this.name;
	}
		
	
	
}
