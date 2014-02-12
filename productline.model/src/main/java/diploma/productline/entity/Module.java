package diploma.productline.entity;

import java.util.HashSet;
import java.util.Set;

public class Module extends BaseProductLineEntity{

	private Integer id;
	private String name;
	private String description;
	private boolean variable;
	private ProductLine productLine;
	private Set<Variability> variabilities;
	private Set<Element> elements;
	private Set<PackageModule> packages;
	
	
	
	public boolean isVariable() {
		return variable;
	}
	public void setVariable(boolean variable) {
		this.variable = variable;
	}
	public ProductLine getProductLine() {
		return productLine;
	}
	public void setProductLine(ProductLine productLine) {
		this.productLine = productLine;
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
	private Set<Element> getElementsObject(){
		if(this.elements == null) return elements = new HashSet<Element>();
		else return elements;		
	}
	
	public void addVariability(Variability variability){
		getVariabilitiesObject().add(variability);
	}
	
	public void addElement(Element element){
		getElementsObject().add(element);
	}
	@Override
	public String toString() {
	
		return this.name;
	}
	
	
	
	
}
