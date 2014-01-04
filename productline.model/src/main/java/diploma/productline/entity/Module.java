package diploma.productline.entity;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.validation.constraints.*;

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
	
	
	@ManyToOne
	@JoinColumn(name="product_line_id")
	private ProductLine productLine;
	
	@OneToMany(mappedBy="module")
	private List<Variability> variabilities;
	
	@OneToMany(mappedBy="module")
	private List<Element> elements;
	
	
	
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
	public List<Variability> getVariabilities() {
		return variabilities;
	}
	public void setVariabilities(List<Variability> variabilities) {
		this.variabilities = variabilities;
	}
	public List<Element> getElements() {
		return elements;
	}
	public void setElements(List<Element> elements) {
		this.elements = elements;
	}
	
	private List<Variability> getVariabilitiesObject(){
		if(this.variabilities == null) return variabilities = new ArrayList<Variability>();
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
