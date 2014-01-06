package diploma.productline;

import java.util.Properties;

import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.hibernate.service.ServiceRegistry;
import org.hibernate.service.ServiceRegistryBuilder;

public class HibernateUtil {

    private static SessionFactory sessionFactory;
    private static ServiceRegistry serviceRegistry;
    private static Configuration configuration;

    private static SessionFactory buildSessionFactory() {
        try {
        	configuration = new Configuration();
            configuration.configure();
            serviceRegistry = new ServiceRegistryBuilder().applySettings(configuration.getProperties()).buildServiceRegistry();        
            return configuration.buildSessionFactory(serviceRegistry);
        }
        catch (Throwable ex) {
            // TODO log
            System.err.println("Initial SessionFactory creation failed." + ex);
            throw new ExceptionInInitializerError(ex);
        }
    }
    
    private static SessionFactory rebuildSessionFactory(Properties properties, boolean resetConfiguration) throws NullPointerException {
        try {
        	if(resetConfiguration == true){
	        	configuration = new Configuration();
	            configuration.configure();
        	}else{
        		if(configuration == null){
        			throw new NullPointerException("Cinfiguration object is null!");
        		}
        	}
        	configuration.setProperties(properties);
            
            serviceRegistry = new ServiceRegistryBuilder().applySettings(configuration.getProperties()).buildServiceRegistry();        
            return configuration.buildSessionFactory(serviceRegistry);
        }
        catch (Throwable ex) {
            // TODO log
            System.err.println("Initial SessionFactory creation failed." + ex);
            throw new ExceptionInInitializerError(ex);
        }
    }

    public static SessionFactory getSessionFactory() {
        if(sessionFactory == null) sessionFactory = buildSessionFactory();
    	return sessionFactory;
    }
    
    /*public static void updateSessionFactory(Properties properties, boolean resetConfiguration){
    	sessionFactory = rebuildSessionFactory(properties, resetConfiguration);
    }*/

}
