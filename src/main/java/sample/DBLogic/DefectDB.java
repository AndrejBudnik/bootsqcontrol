package sample.DBLogic;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import org.hibernate.Query;
import org.hibernate.Session;
import sample.Config;
import sample.Model.DefectEntity;
import sample.Model.DefectMonEntity;

import java.util.List;

/**
 * Created by kotsy on 20.12.2016.
 */
public class DefectDB extends DBLogic<DefectEntity> {

    private static DefectDB instance = null;

    private DefectDB() {}

    /**
     * Singleton implementation.
     * @return the only one created class instance.
     */
    public static DefectDB getInstance() {
        DefectDB localInstance = instance;
        if (localInstance == null) {
            synchronized (DefectDB.class) {
                localInstance = instance;
                if (localInstance == null) {
                    instance = localInstance = new DefectDB();
                }
            }
        }
        return localInstance;
    }

    /**
     * Selects the list of entities from the database.
     * @return observableList of entities.
     */
    @SuppressWarnings("unchecked")
    public ObservableList<DefectEntity> select(){
        Session session = Config.getSession();
        session.beginTransaction();
        List<DefectEntity> entities = null;
        try {
            entities = session.createCriteria(DefectEntity.class).list();
            session.getTransaction().commit();
        } catch (Exception e) {
            session.getTransaction().rollback();
            e.printStackTrace();
        }finally {
            session.close();
        }

        return FXCollections.observableArrayList(entities);
    }
}
