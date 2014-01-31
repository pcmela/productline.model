package diploma.productline.entity;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;


/**
 * 
 * @author pcmela
 * 
 */
@Entity
@Table(name="product_line")
public class ProductLine implements BaseProductLineEntity{

	/*@Id
	@Column(name="product_line_id")
	@GeneratedValue(strategy=GenerationType.AUTO)
	private Long id; */
	
	@Id
	@Column(length=50)
	private String name;
	
	@Column(length=150)
	private String description;
	
	@OneToMany(mappedBy="productLine",fetch=FetchType.EAGER)
	@Cascade(CascadeType.ALL)
	private Set<Module> modules;
	
	@ManyToOne
	@JoinColumn(name="product_line_id")
	private ProductLine productLine;
	
	/*public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}*/
	public String getName() {
		return name;
	}
	public ProductLine getProductLine() {
		return productLine;
	}
	public void setProductLine(ProductLine productLine) {
		this.productLine = productLine;
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
