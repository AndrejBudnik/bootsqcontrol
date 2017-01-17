package sample.Model;

import javafx.collections.ObservableList;
import sample.DBLogic.PlanDB;

import javax.persistence.*;
import java.io.Serializable;
import java.sql.Date;

/**
 * Created by kotsy on 18.12.2016.
 */
@Entity
@Table(name = "plan", schema = "public", catalog = "uchet")
public class PlanEntity implements Serializable {

    private static final long serialVersionUID = 6555087016559733720L;

    private Date planDate;
    private String fason;
    private int size;
    private int actualCnt;
    private int expectedCnt;
    private int id;

    /**
     * Entity select method.
     * Redirects straight to DB interface select() method,
     * implementing Indirection pattern.
     * @return list of selected entity items.
     */
    public ObservableList<PlanEntity> select(){
        return PlanDB.getInstance().select();
    }

    /**
     * Entity insert method.
     * Redirects the entity to the DB interface,
     * implementing Indirection pattern.
     * @param entity is the entity to insert.
     */
    public void insert(PlanEntity entity){
        PlanDB.getInstance().insertOrUpdate(entity);
    }

    /**
     * Entity delete method.
     * Redirects the entity to the DB interface,
     * implementing Indirection pattern.
     * @param entity is the entity to delete.
     */
    public void delete(PlanEntity entity){
        PlanDB.getInstance().delete(entity);
    }

    private EmployeeEntity employee;

    private ZakazEntity order;

    @ManyToOne
    @JoinColumn(name="order_id")
    public ZakazEntity getOrder() {
        return order;
    }

    public void setOrder(ZakazEntity order) {
        this.order = order;
    }

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name="employee_id")
    public EmployeeEntity getEmployee() {
        return employee;
    }

    public void setEmployee(EmployeeEntity employee) {
        this.employee = employee;
    }

    @Basic
    @Column(name = "plan_date", nullable = true)
    public Date getPlanDate() {
        return planDate;
    }

    public void setPlanDate(Date planDate) {
        this.planDate = planDate;
    }

    @Basic
    @Column(name = "fason", nullable = true, length = 255)
    public String getFason() {
        return fason;
    }

    public void setFason(String fason) {
        this.fason = fason;
    }

    @Basic
    @Column(name = "size", nullable = false)
    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }

    @Basic
    @Column(name = "actual_cnt", nullable = true)
    public int getActualCnt() {
        return actualCnt;
    }

    public void setActualCnt(int actualCnt) {
        this.actualCnt = actualCnt;
    }

    @Basic
    @Column(name = "expected_cnt", nullable = false)
    public int getExpectedCnt() {
        return expectedCnt;
    }

    public void setExpectedCnt(int expectedCnt) {
        this.expectedCnt = expectedCnt;
    }

    @Id
    @SequenceGenerator(name="identifier", sequenceName="plan_id_seq", allocationSize=1)
    @GeneratedValue(strategy=GenerationType.SEQUENCE, generator="identifier")
    @Column(name = "id", nullable = false)
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return "План № " + id;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        PlanEntity that = (PlanEntity) o;

        if (size != that.size) return false;
        if (actualCnt != that.actualCnt) return false;
        if (expectedCnt != that.expectedCnt) return false;
        if (id != that.id) return false;
        if (planDate != null ? !planDate.equals(that.planDate) : that.planDate != null) return false;
        if (fason != null ? !fason.equals(that.fason) : that.fason != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = planDate != null ? planDate.hashCode() : 0;
        result = 31 * result + (fason != null ? fason.hashCode() : 0);
        result = 31 * result + size;
        result = 31 * result + actualCnt;
        result = 31 * result + expectedCnt;
        return result;
    }
}
