package diploma.productline;

import java.util.Properties;

import org.hibernate.HibernateException;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.hibernate.service.ServiceRegistry;
import org.hibernate.service.ServiceRegistryBuilder;

public class HibernateUtil {

	private static SessionFactory sessionFactory;
	private static ServiceRegistry serviceRegistry;
	private static Configuration configuration;

	public static final String USERNAME = "hibernate.connection.username";
	public static final String PASSWORD = "hibernate.connection.password";
	public static final String URL = "hibernate.connection.url";

	/*
	 * private static SessionFactory buildSessionFactory() { try { configuration
	 * = new Configuration(); configuration.configure(); serviceRegistry = new
	 * ServiceRegistryBuilder
	 * ().applySettings(configuration.getProperties()).buildServiceRegistry();
	 * return configuration.buildSessionFactory(serviceRegistry); } catch
	 * (Throwable ex) { // TODO log
	 * System.err.println("Initial SessionFactory creation failed." + ex); throw
	 * new ExceptionInInitializerError(ex); } }
	 */

	private static SessionFactory buildSessionFactory(Properties properties)
			throws HibernateException {
		try {
			configuration = new Configuration();
			configuration.configure();

			if (properties != null) {
				if (properties.getProperty(USERNAME) != null) {
					configuration.setProperty(USERNAME,
							properties.getProperty(USERNAME));
				}
				if (properties.getProperty(PASSWORD) != null) {
					configuration.setProperty(PASSWORD,
							properties.getProperty(PASSWORD));
				}
				if (properties.getProperty(URL) != null) {
					configuration.setProperty(URL, properties.getProperty(URL));
				}
			}

			serviceRegistry = new ServiceRegistryBuilder().applySettings(
					configuration.getProperties()).buildServiceRegistry();
			return configuration.buildSessionFactory(serviceRegistry);
		} catch (HibernateException ex) {
			// TODO log
			System.err.println("Initial SessionFactory creation failed." + ex);
			throw ex;
		} catch (Exception e){
			e.printStackTrace();
			throw new HibernateException(e.getMessage());
		}
	}

	public static SessionFactory getSessionFactory() {
		return sessionFactory;
	}

	public static void initSessionFactory(Properties properties)
			throws HibernateException {
		sessionFactory = buildSessionFactory(properties);
	}
	
	public static void removeSessionFactory(){
		sessionFactory.getCurrentSession().disconnect();
		sessionFactory.close();
		sessionFactory = null;
	}

}
