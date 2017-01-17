package sample.Model;

import javafx.collections.ObservableList;
import sample.DBLogic.ZakazDB;

import javax.persistence.*;
import java.io.Serializable;
import java.sql.Date;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by kotsy on 18.12.2016.
 */
@Entity
@Table(name = "zakaz", schema = "public", catalog = "uchet")
public class ZakazEntity implements Serializable {

    private static final long serialVersionUID = -6927593970040080837L;

    private short id;
    private String fason;
    private Date orderDate;
    private int size;
    private int count;

    /**
     * Entity select method.
     * Redirects straight to DB interface select() method,
     * implementing Indirection pattern.
     * @return list of selected entity items.
     */
    public ObservableList<ZakazEntity> select(){
        return ZakazDB.getInstance().select();
    }

    /**
     * Entity insert method.
     * Redirects the entity to the DB interface,
     * implementing Indirection pattern.
     * @param entity is the entity to insert.
     */
    public void insert(ZakazEntity entity){
        ZakazDB.getInstance().insertOrUpdate(entity);
    }

    /**
     * Entity delete method.
     * Redirects the entity to the DB interface,
     * implementing Indirection pattern.
     * @param entity is the entity to delete.
     */
    public void delete(ZakazEntity entity){
        ZakazDB.getInstance().delete(entity);
    }

    private CustomerEntity customer;

    public void setCustomer(CustomerEntity customer) {
        this.customer = customer;
    }

    @OneToMany(mappedBy = "order", cascade = CascadeType.REMOVE, orphanRemoval = true)
    private Set<PlanEntity> plans = new HashSet<PlanEntity>();

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name="customer_id")
    public CustomerEntity getCustomer() {
        return customer;
    }

    @Id
    @SequenceGenerator(name="identifier", sequenceName="zakaz_id_seq", allocationSize=1)
    @GeneratedValue(strategy=GenerationType.SEQUENCE, generator="identifier")
    @Column(name = "id", nullable = false)
    public short getId() {
        return id;
    }

    public void setId(short id) {
        this.id = id;
    }

    @Basic
    @Column(name = "count", nullable = false)
    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    @Basic
    @Column(name = "fason", nullable = false, length = 50)
    public String getFason() {
        return fason;
    }

    public void setFason(String fason) {
        this.fason = fason;
    }

    @Basic
    @Column(name = "order_date", nullable = false)
    public Date getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(Date orderDate) {
        this.orderDate = orderDate;
    }

    @Basic
    @Column(name = "size", nullable = true)
    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }

    @Override
    public String toString() {
        return "Заказ № " + id;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ZakazEntity that = (ZakazEntity) o;

        if (id != that.id) return false;
        if (size != that.size) return false;
        if (count != that.count) return false;
        if (!fason.equals(that.fason)) return false;
        if (!orderDate.equals(that.orderDate)) return false;
        return plans != null ? plans.equals(that.plans) : that.plans == null;

    }

    @Override
    public int hashCode() {
        int result = (int) id;
        result = 31 * result + fason.hashCode();
        result = 31 * result + orderDate.hashCode();
        result = 31 * result + size;
        result = 31 * result + count;
        result = 31 * result + (plans != null ? plans.hashCode() : 0);
        return result;
    }
}
