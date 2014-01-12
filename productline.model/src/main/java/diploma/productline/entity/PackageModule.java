package diploma.productline.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Entity
public class PackageModule implements BaseProductLineEntity {

	@Id
	@Column(name="package_id")
	@GeneratedValue(strategy=GenerationType.AUTO)
	private Long id;
	
	@Column(length = 50)
	private String name;

	@ManyToOne
	@JoinColumn(name="module_id")
	private Module module; 
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Module getModule() {
		return module;
	}

	public void setModule(Module module) {
		this.module = module;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getName() {
		return name;
	}

	@Override
	public String toString() {
		return name;
	}
	
	

}
