package diploma.productline;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Properties;

import diploma.productline.configuration.YamlExtractor;
import diploma.productline.dao.ProductLineDAO;
import diploma.productline.entity.Element;
import diploma.productline.entity.Module;
import diploma.productline.entity.PackageModule;
import diploma.productline.entity.ProductLine;
import diploma.productline.entity.Variability;

/**
 * Hello world!
 *
 */
public class App 
{
	private HibernateUtil hibernateUtil;
	
    public static void main( String[] args )
    {
    	//String path = "C:\\Users\\IBM_ADMIN\\Desktop\\Neon.yaml";
    	App app = new App();
    	app.setHibernateUtil(new HibernateUtil());
    	//ProductLine prod = YamlExtractor.extract(path);
    	
    	Properties p = new Properties();
    	p.setProperty("connection_url", "jdbc:h2:~/test2");
    	p.setProperty("username", "sa");
    	p.setProperty("password", "");
    	
    	try {
			Class.forName("org.h2.Driver");
		
	        Connection conn;
			try {
				try {
					ProductLineDAO dao = new ProductLineDAO(p);
					ProductLineDAO.createDatabaseStructure(p);
					/*System.out.println("created structure");
					dao.createAll(getProductLine());
					System.out.println("inserted");*/
					ProductLine prd = dao.getProductLineWithChilds("p1");
					System.out.println("ssadasdsad" + prd.getName());
					for(Module m : prd.getModules()){
						System.out.println(m.getName());
					}
				} catch (FileNotFoundException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
    	} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	
    	/*String path = "C:\\Users\\IBM_ADMIN\\Desktop\\Neon.yaml";
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
    	}*/
    }

    public static ProductLine getProductLine(){
    	ProductLine prod = new ProductLine();
    	prod.setName("p1");
    	
    	Module m1 = new Module();
    	m1.setName("m1");
    	m1.setProductLine(prod);
    	Variability v1 = new Variability();
    	v1.setName("v1");
    	v1.setModule(m1); 
    	Variability v2 = new Variability();
    	v2.setName("v2");
    	v2.setModule(m1);
    	Element e1 = new Element();
    	e1.setName("e1");
    	e1.setModule(m1);
    	Element e2 = new Element();
    	e2.setName("e2");
    	e2.setModule(m1);
    	PackageModule p = new PackageModule();
    	p.setId(2l);
    	p.setName("test");
    	p.setModule(m1);
    	
    	m1.addVariability(v1);
    	m1.addVariability(v2);
    	m1.addElement(e1);
    	m1.addElement(e2);
    	
    	Module m2 = new Module();
    	m2.setName("m2");
    	m2.setProductLine(prod);
    	
    	prod.addModule(m1);
    	prod.addModule(m2);
    	
    	return prod;
    	
    }
    
	public HibernateUtil getHibernateUtil() {
		return hibernateUtil;
	}

	public void setHibernateUtil(HibernateUtil hibernateUtil) {
		this.hibernateUtil = hibernateUtil;
	}
    
    
}
