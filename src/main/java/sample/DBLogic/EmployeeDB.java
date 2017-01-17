package sample.DBLogic;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import org.hibernate.Session;
import sample.Config;
import sample.Constants;
import sample.Model.EmployeeEntity;

import java.util.List;

/**
 * Created by kotsy on 20.12.2016.
 */
public class EmployeeDB extends DBLogic<EmployeeEntity> {

    private static EmployeeDB instance = null;

    private EmployeeDB() {}

    /**
     * Singleton implementation.
     * @return the only one created class instance.
     */
    public static EmployeeDB getInstance() {
        EmployeeDB localInstance = instance;
        if (localInstance == null) {
            synchronized (EmployeeDB.class) {
                localInstance = instance;
                if (localInstance == null) {
                    instance = localInstance = new EmployeeDB();
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
    public ObservableList<EmployeeEntity> select(){
        Session session = Config.getSession();
        session.beginTransaction();
        List<EmployeeEntity> entities = null;
        try {
            entities = session.createCriteria(EmployeeEntity.class).list();
        } catch (Exception e) {
            session.getTransaction().rollback();
            e.printStackTrace();
        }
        finally {
            session.close();
        }

        return FXCollections.observableArrayList(entities);
    }
}
