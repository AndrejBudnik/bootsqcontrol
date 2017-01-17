package sample.Model;

import javafx.collections.ObservableList;
import sample.DBLogic.DefectMonDB;

import javax.persistence.*;
import java.io.Serializable;
import java.sql.Date;

/**
 * Created by kotsy on 18.12.2016.
 */
@Entity
@Table(name = "defect_mon", schema = "public", catalog = "uchet")
public class DefectMonEntity implements Serializable {

    private static final long serialVersionUID = 8523129626463472167L;

    private int id;
    private int defectCount;
    private Date checkDate;
    private DefectEntity defectType;
    private PlanEntity plan;

    /**
     * Entity select method.
     * Redirects straight to DB interface select() method,
     * implementing Indirection pattern.
     * @return list of selected entity items.
     */
    public ObservableList<DefectMonEntity> select(){
        return DefectMonDB.getInstance().select();
    }

    /**
     * Entity insert method.
     * Redirects the entity to the DB interface,
     * implementing Indirection pattern.
     * @param entity is the entity to insert.
     */
    public void insert(DefectMonEntity entity){
        DefectMonDB.getInstance().insertOrUpdate(entity);
    }

    /**
     * Entity delete method.
     * Redirects the entity to the DB interface,
     * implementing Indirection pattern.
     * @param entity is the entity to delete.
     */
    public void delete(DefectMonEntity entity){
        DefectMonDB.getInstance().delete(entity);
    }

    @Id
    @SequenceGenerator(name="identifier", sequenceName="defectmon_id_seq", allocationSize=1)
    @GeneratedValue(strategy=GenerationType.SEQUENCE, generator="identifier")
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @OneToOne(cascade=CascadeType.ALL)
    @JoinColumn(name="plan_id", referencedColumnName = "id")
    public PlanEntity getPlan() {
        return plan;
    }

    public void setPlan(PlanEntity plan) {
        this.plan = plan;
    }

    @Basic
    @Column(name = "defect_count", nullable = true)
    public int getDefectCount() {
        return defectCount;
    }

    public void setDefectCount(int defectCount) {
        this.defectCount = defectCount;
    }



    @Basic
    @Column(name = "check_date", nullable = true)
    public Date getCheckDate() {
        return checkDate;
    }

    public void setCheckDate(Date checkDate) {
        this.checkDate = checkDate;
    }

    @ManyToOne
    @JoinColumn(name="defect_type")
    public DefectEntity getDefectType() {
        return defectType;
    }

    public void setDefectType(DefectEntity defectType) {
        this.defectType = defectType;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        DefectMonEntity that = (DefectMonEntity) o;

        if (defectCount != that.defectCount) return false;
        if (checkDate != null ? !checkDate.equals(that.checkDate) : that.checkDate != null) return false;
        if (defectType != null ? !defectType.equals(that.defectType) : that.defectType != null) return false;

        return true;
    }

    @Override
    public String toString() {
        return "DefectMonEntity{" +
                "id=" + id +
                ", defectCount=" + defectCount +
                ", checkDate=" + checkDate +
                ", defectType=" + defectType +
                ", plan=" + plan +
                '}' + '\n';
    }

    @Override
    public int hashCode() {
        int result = defectCount;
        result = 31 * result + (checkDate != null ? checkDate.hashCode() : 0);
        result = 31 * result + (defectType != null ? defectType.hashCode() : 0);
        return result;
    }
}
