package diploma.productline.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import javax.validation.constraints.*;

@Entity
public class Variability implements BaseProductLineEntity{

	@Id
	@Column(name="variability_id")
	@NotNull
	private String id;
	
	@Column(length=50)
	@NotNull
	private String name;
	
	@ManyToOne
	@JoinColumn(name="module_id")
	private Module module; 
		
	
	
	
	public Module getModule() {
		return module;
	}
	public void setModule(Module module) {
		this.module = module;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	
	
}
