package diploma.productline;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Properties;

import diploma.productline.dao.ProductLineDAO;

/**
 * Hello world!
 *
 */
public class App 
{
	private HibernateUtil hibernateUtil;
	
    public static void main( String[] args )
    {
    	Properties p = new Properties();
    	p.setProperty("connection_url", "jdbc:h2:~/test2");
    	p.setProperty("username", "sa");
    	p.setProperty("password", "");
    	
    	try {
			Class.forName("org.h2.Driver");
		
	        Connection conn;
			try {
				try {
					ProductLineDAO.createDatabaseStructure(p);
					System.out.println("done");
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

	public HibernateUtil getHibernateUtil() {
		return hibernateUtil;
	}

	public void setHibernateUtil(HibernateUtil hibernateUtil) {
		this.hibernateUtil = hibernateUtil;
	}
    
    
}
