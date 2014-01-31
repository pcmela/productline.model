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
import javax.validation.constraints.NotNull;

import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;

@Entity
public class Module implements BaseProductLineEntity{

	@Id
	@Column(name="module_id")
	@NotNull
	private String id;
	
	@Column(length=50)
	@NotNull
	private String name;
	
	@Column(length=150)
	private String description;
	
	@Column(nullable=false)
	private boolean isVariable;
	
	@ManyToOne
	@JoinColumn(name="product_line_id")
	private ProductLine productLine;
	
	@OneToMany(mappedBy="module",fetch=FetchType.EAGER)
	@Cascade(CascadeType.ALL)
	private Set<Variability> variabilities;
	
	@OneToMany(mappedBy="module",fetch=FetchType.EAGER)
	@Cascade(CascadeType.ALL)
	private Set<Element> elements;
	
	@OneToMany(mappedBy="module",fetch=FetchType.EAGER)
	@Cascade(CascadeType.ALL)
	private Set<PackageModule> packages;
	
	
	public boolean isVariable() {
		return isVariable;
	}
	public void setVariable(boolean isVariable) {
		this.isVariable = isVariable;
	}
	public ProductLine getProductLine() {
		return productLine;
	}
	public void setProductLine(ProductLine productLine) {
		this.productLine = productLine;
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
		if(name != null && id == null){
			id = name.replaceAll("( )*", "_");
		}
		this.name = name;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public Set<Variability> getVariabilities() {
		return variabilities;
	}
	public void setVariabilities(Set<Variability> variabilities) {
		this.variabilities = variabilities;
	}
	public Set<Element> getElements() {
		return elements;
	}
	public void setElements(Set<Element> elements) {
		this.elements = elements;
	}
	
	public Set<PackageModule> getPackages() {
		return packages;
	}
	public void setPackages(Set<PackageModule> packages) {
		this.packages = packages;
	}
	private Set<Variability> getVariabilitiesObject(){
		if(this.variabilities == null) return variabilities = new HashSet<Variability>();
		else return variabilities;
		
	}
	
	public void addVariability(Variability variability){
		getVariabilitiesObject().add(variability);
	}
	@Override
	public String toString() {
	
		return this.name;
	}
	
	
	
	
}
