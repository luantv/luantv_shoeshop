package utility;

import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.service.ServiceRegistry;

@SuppressWarnings("all")
public class HibernateUtil{
	private static SessionFactory sessionFactory;

	@SuppressWarnings("deprecation")
	public static SessionFactory getSessionFactory(){
		try{
			Configuration configuration = new Configuration();
			configuration.configure();
			ServiceRegistry serviceRegistry = new StandardServiceRegistryBuilder().applySettings(configuration.getProperties()).build();
			sessionFactory = configuration.buildSessionFactory(serviceRegistry);
			return sessionFactory;
		} catch(Exception e){
			e.printStackTrace();
			return null;
		}
	}
}
