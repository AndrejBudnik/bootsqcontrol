package sample.DBLogic;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import org.hibernate.Session;
import sample.Config;
import sample.Constants;
import sample.Model.DefectMonEntity;

import java.util.List;

/**
 * Created by kotsy on 20.12.2016.
 */
public class DefectMonDB extends DBLogic<DefectMonEntity> {

    private static DefectMonDB instance = null;

    private DefectMonDB() {}

    /**
     * Singleton implementation.
     * @return the only one created class instance.
     */
    public static DefectMonDB getInstance() {
        DefectMonDB localInstance = instance;
        if (localInstance == null) {
            synchronized (DefectMonDB.class) {
                localInstance = instance;
                if (localInstance == null) {
                    instance = localInstance = new DefectMonDB();
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
    public ObservableList<DefectMonEntity> select(){
        Session session = Config.getSession();
        session.beginTransaction();
        List<DefectMonEntity> entities = null;
        try {
            entities = session.createCriteria(DefectMonEntity.class).createAlias("plan","plan").createAlias("defectType", "defect").list();
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
