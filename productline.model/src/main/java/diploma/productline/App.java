package diploma.productline;

import java.util.ArrayList;

import org.hibernate.Session;
import org.yaml.snakeyaml.error.YAMLException;

import diploma.productline.configuration.YamlExtractor;
import diploma.productline.entity.PackageModule;
import diploma.productline.entity.ProductLine;
import diploma.productline.validate.ProductLineErrors;
import diploma.productline.validate.ProductLineValidator;

/**
 * Hello world!
 *
 */
public class App 
{
	private HibernateUtil hibernateUtil;
	
    public static void main( String[] args )
    {
    	String path = "C:\\Users\\IBM_ADMIN\\Desktop\\Neon.yaml";
    	App app = new App();
    	app.setHibernateUtil(new HibernateUtil());
    	try{
    		ProductLine prod = YamlExtractor.extract(path);
    		
    		ArrayList<ProductLineErrors> errors = ProductLineValidator.validate(prod);
    		
    		
    		if(errors != null){
    			for(ProductLineErrors e : errors){
    				System.out.println(e.getClassName() + " - " + e.getPath() + ": " + e.getMessage());
    			}
    		}else{
	    		HibernateUtil.initSessionFactory(null);
	    		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
	    		session.beginTransaction();
	    		session.save(prod);
	    		session.getTransaction().commit();
	    		System.out.println("Test connection!");
	    		session = HibernateUtil.getSessionFactory().getCurrentSession();
	    		session.beginTransaction();
	    		HibernateUtil.getSessionFactory().getCurrentSession().get(ProductLine.class, "1");
	    		session.getTransaction().commit();
	    		System.out.println("loaded!");
	    		
	    		diploma.productline.entity.PackageModule p = new PackageModule();
	    		p.setName("sdsd");
	    		session = HibernateUtil.getSessionFactory().getCurrentSession();
	    		session.beginTransaction();
	    		session.save(p);
	    		session.getTransaction().commit();
    		}
    	}catch(YAMLException e){
    		// TODO logger
    		e.printStackTrace();
    	}
    }

	public HibernateUtil getHibernateUtil() {
		return hibernateUtil;
	}

	public void setHibernateUtil(HibernateUtil hibernateUtil) {
		this.hibernateUtil = hibernateUtil;
	}
    
    
}
