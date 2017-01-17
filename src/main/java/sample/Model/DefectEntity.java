package sample.Model;

import javafx.collections.ObservableList;
import sample.DBLogic.DefectDB;

import javax.persistence.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by kotsy on 18.12.2016.
 */
@Entity
@Table(name = "defect", schema = "public", catalog = "uchet")
public class DefectEntity implements Serializable {

    private static final long serialVersionUID = 2466378290724383516L;

    private short id;
    private String type;

    /**
     * Entity select method.
     * Redirects straight to DB interface select() method,
     * implementing Indirection pattern.
     * @return list of selected entity items.
     */
    public ObservableList<DefectEntity> select(){
        return DefectDB.getInstance().select();
    }

    /**
     * Entity insert method.
     * Redirects the entity to the DB interface,
     * implementing Indirection pattern.
     * @param entity is the entity to insert.
     */
    public void insert(DefectEntity entity){
        DefectDB.getInstance().insertOrUpdate(entity);
    }

    /**
     * Entity delete method.
     * Redirects the entity to the DB interface,
     * implementing Indirection pattern.
     * @param entity is the entity to delete.
     */
    public void delete(DefectEntity entity){
        DefectDB.getInstance().delete(entity);
    }

    @Id
    @SequenceGenerator(name="identifier", sequenceName="defect_id_seq", allocationSize=1)
    @GeneratedValue(strategy=GenerationType.SEQUENCE, generator="identifier")
    @Column(name = "id", nullable = false)
    public short getId() {
        return id;
    }

    public void setId(short id) {
        this.id = id;
    }


    @OneToMany(mappedBy = "defectType", cascade = CascadeType.REMOVE, orphanRemoval = true)
    private Set<DefectMonEntity> defects = new HashSet<DefectMonEntity>();


    @Basic
    @Column(name = "type", nullable = true, length = 250)
    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    @Override
    public String toString() {
        return "DefectEntity{" +
                "id=" + id +
                ", type='" + type + '\'' +
                ", defects=" + defects +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        DefectEntity that = (DefectEntity) o;
        if (id != that.id) return false;
        return type != null ? type.equals(that.type) : that.type == null;
    }

    @Override
    public int hashCode() {
        int result = (int) id;
        result = 31 * result + (type != null ? type.hashCode() : 0);
        return result;
    }
}
