package diploma.productline.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

import javax.validation.constraints.*;

@Entity
public class Type implements BaseProductLineEntity{

	@Id
	@Column(name="type_id")
	@NotNull
	private String id;
	
	@Column(length=50)
	@NotNull
	private String name;
	
	@Column(length=150)
	private String description;
	
	
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
	
	
	
}
