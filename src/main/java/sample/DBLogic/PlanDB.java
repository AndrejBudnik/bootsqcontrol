package sample.DBLogic;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import org.hibernate.Session;
import sample.Config;
import sample.Constants;
import sample.Model.PlanEntity;

import java.util.List;

/**
 * Created by kotsy on 20.12.2016.
 */
public class PlanDB extends DBLogic<PlanEntity> {

    private static PlanDB instance = null;

    private PlanDB() {}

    /**
     * Singleton implementation.
     * @return the only one created class instance.
     */
    public static PlanDB getInstance() {
        PlanDB localInstance = instance;
        if (localInstance == null) {
            synchronized (PlanDB.class) {
                localInstance = instance;
                if (localInstance == null) {
                    instance = localInstance = new PlanDB();
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
    public ObservableList<PlanEntity> select(){
        Session session = Config.getSession();
        session.beginTransaction();
        List<PlanEntity> entities = null;
        try {
            entities = session.createCriteria(PlanEntity.class).createAlias("employee", "employee").createAlias("order","order").list();
        } catch (Exception e) {
            session.getTransaction().rollback();
            e.printStackTrace();
        } finally {
            session.close();
        }


        return FXCollections.observableArrayList(entities);
    }
}
