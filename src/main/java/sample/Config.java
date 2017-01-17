package sample;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.hibernate.metadata.ClassMetadata;
import org.hibernate.service.ServiceRegistry;
import org.hibernate.service.ServiceRegistryBuilder;

/**
 * Created by kotsy on 19.12.2016.
 */
public abstract class Config {

    private static final SessionFactory ourSessionFactory;
    private static final ServiceRegistry serviceRegistry;

    static {
        try {
            Configuration configuration = new Configuration();
            configuration.configure();

            serviceRegistry = new ServiceRegistryBuilder().applySettings(configuration.getProperties()).buildServiceRegistry();
            ourSessionFactory = configuration.buildSessionFactory(serviceRegistry);
        } catch (Throwable ex) {
            throw new ExceptionInInitializerError(ex);
        }
    }

    /**
     * Singleton implementation for a session opener.
     * @return opened session.
     * @throws HibernateException if the session cannot be opened for some reason.
     */
    public static Session getSession() throws HibernateException {
        return ourSessionFactory.openSession();
    }

    private Config() {}

    /**
     * Connects to the database (DB address is mentioned in resources/hibernate.cfg.xml)
     * and querying all the entities.
     */
    static void connect(){
        final Session session = getSession();
        try {
            final java.util.Map metadataMap = session.getSessionFactory().getAllClassMetadata();
            for (Object key : metadataMap.keySet()) {
                final ClassMetadata classMetadata = (ClassMetadata) metadataMap.get(key);
                final String entityName = classMetadata.getEntityName();
                final org.hibernate.Query query = session.createQuery("from " + entityName);
                System.out.println(Constants.EXECUTING + query.getQueryString());
                for (Object o : query.list()) {
                    System.out.println(Constants.TAB + o);
                }
            }

        } catch(Exception e){
            e.printStackTrace();
        } finally {
            session.close();
        }
    }

}
