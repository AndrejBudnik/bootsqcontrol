package sample.DBLogic;

import org.hibernate.Session;
import sample.Config;

/**
 * Created by kotsy on 25.12.2016.
 */
public abstract class DBLogic<T> {

    /**
     * Deletes an entity from the database if the one exists.
     * @param entity is the entity to delete.
     */
    public void delete(T entity){
        Session session = Config.getSession();
        session.beginTransaction();
        try{
            session.delete(entity);
            session.getTransaction().commit();
        } catch (Exception e){
            session.getTransaction().rollback();
            e.printStackTrace();
        } finally {
            session.close();
        }
    }

    /**
     * Inserts the entity into the database if such a primary key does not exist.
     * Updates the entity otherwise.
     * @param entity is the entity to insert/update
     */
    public void insertOrUpdate(T entity){
        Session session = Config.getSession();
        session.beginTransaction();
        try{
            session.saveOrUpdate(entity);
            session.getTransaction().commit();
        } catch (Exception e){
            session.getTransaction().rollback();
            e.printStackTrace();
        } finally {
            session.close();
        }
    }

}
