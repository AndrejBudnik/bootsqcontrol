package sample.Model;

import javafx.collections.ObservableList;
import sample.DBLogic.EmployeeDB;

import javax.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by kotsy on 18.12.2016.
 */
@Entity
@Table(name = "employee", schema = "public", catalog = "uchet")
public class EmployeeEntity implements Serializable {

    private static final long serialVersionUID = 6012051867584895124L;

    private short id;
    private String name;
    private BigDecimal salary;
    private String post;
    private String telNum;

    /**
     * Entity select method.
     * Redirects straight to DB interface select() method,
     * implementing Indirection pattern.
     * @return list of selected entity items.
     */
    public ObservableList<EmployeeEntity> select(){
        return EmployeeDB.getInstance().select();
    }

    /**
     * Entity insert method.
     * Redirects the entity to the DB interface,
     * implementing Indirection pattern.
     * @param entity is the entity to insert.
     */
    public void insert(EmployeeEntity entity){
        EmployeeDB.getInstance().insertOrUpdate(entity);
    }

    /**
     * Entity delete method.
     * Redirects the entity to the DB interface,
     * implementing Indirection pattern.
     * @param entity is the entity to delete.
     */
    public void delete(EmployeeEntity entity){
        EmployeeDB.getInstance().delete(entity);
    }

    private Set<PlanEntity> plans = new HashSet<PlanEntity>();


    @Id
    @SequenceGenerator(name="identifier", sequenceName="employee_id_seq", allocationSize=1)
    @GeneratedValue(strategy=GenerationType.SEQUENCE, generator="identifier")
    @Column(name = "id", nullable = false)
    public short getId() {
        return id;
    }

    public void setId(short id) {
        this.id = id;
    }

    @Basic
    @Column(name = "name", nullable = false, length = 255)
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Basic
    @Column(name = "salary", nullable = false, precision = 0)
    public BigDecimal getSalary() {
        return salary;
    }

    public void setSalary(BigDecimal salary) {
        this.salary = salary;
    }

    @Basic
    @Column(name = "post", nullable = true, length = 255)
    public String getPost() {
        return post;
    }

    public void setPost(String post) {
        this.post = post;
    }

    @Basic
    @Column(name = "tel_num", nullable = true, length = 20)
    public String getTelNum() {
        return telNum;
    }

    public void setTelNum(String telNum) {
        this.telNum = telNum;
    }


    @OneToMany(mappedBy = "employee", cascade = CascadeType.ALL)
    public Set<PlanEntity> getPlans() {
        return plans;
    }

    public void setPlans(Set<PlanEntity> plans) {
        this.plans = plans;
    }

    public void setPlans(PlanEntity plan) {
        this.plans.add(plan);
    }

    @Override
    public String toString() {
        return  name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        EmployeeEntity that = (EmployeeEntity) o;

        if (id != that.id) return false;
        if (name != null ? !name.equals(that.name) : that.name != null) return false;
        if (salary != null ? !salary.equals(that.salary) : that.salary != null) return false;
        if (post != null ? !post.equals(that.post) : that.post != null) return false;
        if (telNum != null ? !telNum.equals(that.telNum) : that.telNum != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = (int) id;
        result = 31 * result + (name != null ? name.hashCode() : 0);
        result = 31 * result + (salary != null ? salary.hashCode() : 0);
        result = 31 * result + (post != null ? post.hashCode() : 0);
        result = 31 * result + (telNum != null ? telNum.hashCode() : 0);
        return result;
    }
}
