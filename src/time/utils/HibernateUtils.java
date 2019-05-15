package time.utils;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

public class HibernateUtils {
	private static final Configuration configuration;
	private static final SessionFactory sessionFactory;
	static {
		configuration = new Configuration().configure();
		sessionFactory = configuration.buildSessionFactory();
	}
	public static Session getSession() {
		return sessionFactory.openSession();
	}
}
