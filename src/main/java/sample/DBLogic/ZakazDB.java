package sample.DBLogic;

import com.sun.corba.se.impl.orbutil.closure.Constant;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.internal.CriteriaImpl;
import sample.Config;
import sample.Constants;
import sample.Model.CustomerEntity;
import sample.Model.DefectEntity;
import sample.Model.ZakazEntity;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by kotsy on 20.12.2016.
 */
public class ZakazDB extends DBLogic<ZakazEntity> {

    private static ZakazDB instance = null;

    private ZakazDB() {}

    /**
     * Singleton implementation.
     * @return the only one created class instance.
     */
    public static ZakazDB getInstance() {
        ZakazDB localInstance = instance;
        if (localInstance == null) {
            synchronized (ZakazDB.class) {
                localInstance = instance;
                if (localInstance == null) {
                    instance = localInstance = new ZakazDB();
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
    public ObservableList<ZakazEntity> select(){
        Session session = Config.getSession();
        session.beginTransaction();
        List<ZakazEntity> entities = new ArrayList<>();
        try {
            entities = session.createCriteria(ZakazEntity.class).createAlias("customer", "customer").list();
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
