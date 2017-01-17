package sample.DBLogic;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import org.hibernate.Session;
import sample.Config;
import sample.Constants;
import sample.Model.CustomerEntity;

import java.util.List;

/**
 * Created by kotsy on 20.12.2016.
 */
public class CustomerDB extends  DBLogic<CustomerEntity> {

    private static CustomerDB instance = null;

    private CustomerDB() {}

    /**
     * Singleton implementation.
     * @return the only one created class instance.
     */
    public static CustomerDB getInstance() {
        CustomerDB localInstance = instance;
        if (localInstance == null) {
            synchronized (CustomerDB.class) {
                localInstance = instance;
                if (localInstance == null) {
                    instance = localInstance = new CustomerDB();
                }
            }
        }
        return localInstance;
    }

    /**
     * Selects the list of entities from the database.
     * @return observableList of entities.
     */
    @SuppressWarnings(Constants.UNCHECKED)
    public ObservableList<CustomerEntity> select(){
        Session session = Config.getSession();
        session.beginTransaction();
        List<CustomerEntity> entities = null;
        try {
            entities = session.createCriteria(CustomerEntity.class).list();
            session.getTransaction().commit();
        } catch (Exception e) {
            session.getTransaction().rollback();
            e.printStackTrace();
        } finally {
            session.close();
        }
        return FXCollections.observableList(entities);
    }
}
