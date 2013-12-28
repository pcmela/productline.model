package diploma.productline.validate;

import java.util.ArrayList;
import java.util.Set;

import javax.validation.*;

import diploma.productline.entity.Module;
import diploma.productline.entity.ProductLine;
import diploma.productline.entity.Variability;

public class ProductLineValidator{
	
	private static Validator validator;
	
	private static void init(){
		ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
	}
	
	public static ArrayList<ProductLineErrors> validate(ProductLine productLine){
		init();
		
		Set<ConstraintViolation<ProductLine>> productLineErrors = validator.validate(productLine);
		
		Set<ConstraintViolation<Module>> moduleErrors = null;
		Set<ConstraintViolation<Variability>> variabilityErrors = null;
		
		for(Module m : productLine.getModules()){
			for(Variability v : m.getVariabilities()){
				if(variabilityErrors == null){
					variabilityErrors = validator.validate(v);
				}else{
					variabilityErrors.addAll(validator.validate(v));
				}
			}
			
			if(moduleErrors == null){
				moduleErrors = validator.validate(m);
			}else{
				moduleErrors.addAll(validator.validate(m));
			}
		}
		
		ArrayList<ProductLineErrors> errors = joinCollections(productLineErrors, moduleErrors, variabilityErrors);
		if(errors.size() > 0){
			return errors;
		}else{
			return null;
		}
	}
	
	private static ArrayList<ProductLineErrors> joinCollections(Set<ConstraintViolation<ProductLine>> productLineErrors, 
			Set<ConstraintViolation<Module>> moduleErrors, Set<ConstraintViolation<Variability>> variabilityErrors){
	
		ArrayList<ProductLineErrors> listOfErrors = new ArrayList<ProductLineErrors>();
		
		if(productLineErrors != null){
			for(ConstraintViolation<ProductLine> e : productLineErrors){
				listOfErrors.add(new ProductLineErrors(ProductLine.class, e.getMessage(), e.getPropertyPath()));
			}
		}
		
		if(moduleErrors != null){
			for(ConstraintViolation<Module> e : moduleErrors){
				listOfErrors.add(new ProductLineErrors(Module.class, e.getMessage(), e.getPropertyPath()));
			}
		}
		
		if(variabilityErrors != null){
			for(ConstraintViolation<Variability> e : variabilityErrors){
				listOfErrors.add(new ProductLineErrors(Variability.class, e.getMessage(), e.getPropertyPath()));
			}
		}
		
		return listOfErrors;
	}
	

}
