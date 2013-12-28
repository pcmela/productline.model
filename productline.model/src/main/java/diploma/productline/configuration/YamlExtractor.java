package diploma.productline.configuration;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;

import org.yaml.snakeyaml.JavaBeanLoader;
import org.yaml.snakeyaml.TypeDescription;
import org.yaml.snakeyaml.error.YAMLException;

import diploma.productline.entity.Module;
import diploma.productline.entity.ProductLine;

public class YamlExtractor {
	
	
	public static ProductLine extract(String path){
		InputStream ins;
		try {
			ins = new FileInputStream(new File(path));
			
			TypeDescription typeDescription = new TypeDescription(ProductLine.class, "!product_line");
			typeDescription.putMapPropertyType("module", Module.class, String.class);
			JavaBeanLoader<ProductLine> loader = 
				new JavaBeanLoader<ProductLine>(typeDescription);
			
			return loader.load(ins);
			
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return null;
	}
		
}
