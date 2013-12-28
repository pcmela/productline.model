package diploma.productline.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import javax.validation.constraints.*;

@Entity
public class Element implements BaseProductLineEntity{

	@Id
	@Column(name="element_id")
	@NotNull
	private String id;
	
	@Column(length=50)
	@NotNull
	private String name;
	
	@Column(length=150)
	private String description;
	
	@ManyToOne
	@JoinColumn(name="type_id")
	private Type type;
	
	@ManyToOne
	@JoinColumn(name="module_id")
	private Module module; 
	
	
	
	
	public Module getModule() {
		return module;
	}
	public void setModule(Module module) {
		this.module = module;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
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
		this.type = type;
	}
		
}
