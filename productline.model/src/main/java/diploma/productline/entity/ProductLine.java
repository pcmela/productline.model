package diploma.productline.entity;

import java.util.HashSet;
import java.util.Set;


/**
 * 
 * @author pcmela
 * 
 */
public class ProductLine extends BaseProductLineEntity{

	private Integer id;	
	private String name;
	private String description;
	private Set<Module> modules;
	private ProductLine parent;
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public ProductLine getParent() {
		return parent;
	}
	public void setParent(ProductLine productLine) {
		this.parent = productLine;
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
	public Set<Module> getModules() {
		return modules;
	}
	public void setModules(Set<Module> modules) {
		this.modules = modules;
	}
	
	private Set<Module> getModulesObject(){
		if(this.modules == null) return modules = new HashSet<Module>();
		else return modules;
		
	}
	
	public void addModule(Module module){
		getModulesObject().add(module);
	}
	@Override
	public String toString() {
		return this.name;
	}
	
	
	
}
