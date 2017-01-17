package sample.Model;


import javafx.collections.ObservableList;
import sample.DBLogic.CustomerDB;
import javax.persistence.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by kotsy on 18.12.2016.
 */
@Entity
@Table(name = "customer", schema = "public", catalog = "uchet")
public class CustomerEntity implements Serializable {

    private static final long serialVersionUID = 5261169252874006047L;

    private short id;
    private String telNum;
    private String name;
    private String email;

    private Set<ZakazEntity> orders = new HashSet<ZakazEntity>();

    /**
     * Entity select method.
     * Redirects straight to DB interface select() method,
     * implementing Indirection pattern.
     * @return list of selected entity items.
     */
    public ObservableList<CustomerEntity> select(){
       return CustomerDB.getInstance().select();
    }

    /**
     * Entity insert method.
     * Redirects the entity to the DB interface,
     * implementing Indirection pattern.
     * @param entity is the entity to insert.
     */
    public void insert(CustomerEntity entity){
        CustomerDB.getInstance().insertOrUpdate(entity);
    }

    /**
     * Entity delete method.
     * Redirects the entity to the DB interface,
     * implementing Indirection pattern.
     * @param entity is the entity to delete.
     */
    public void delete(CustomerEntity entity){
        CustomerDB.getInstance().delete(entity);
    }

    @Id
    @SequenceGenerator(name="identifier", sequenceName="customer_id_seq", allocationSize=1)
    @GeneratedValue(strategy=GenerationType.SEQUENCE, generator="identifier")
    @Column(name = "id", nullable = false)
    public short getId() {
        return id;
    }

    public void setId(short id) {
        this.id = id;
    }

    @Basic
    @Column(name = "tel_num", nullable = false)
    public String getTelNum() {
        return telNum;
    }

    public void setTelNum(String telNum) {
        this.telNum = telNum;
    }

    @Basic
    @Column(name = "name", nullable = false, length = 15)
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @OneToMany(mappedBy = "customer", cascade = CascadeType.ALL)
    public Set<ZakazEntity> getOrders() {
        return orders;
    }

    public void setOrders(Set<ZakazEntity> orders) {
        this.orders = orders;
    }

    public void setOrders(ZakazEntity order) {
        this.orders.add(order);
    }

    @Basic
    @Column(name = "email", nullable = true, length = 255)
    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Override
    public String toString() {
        return name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        CustomerEntity that = (CustomerEntity) o;

        if (id != that.id) return false;
        if (telNum != that.telNum) return false;
        if (name != null ? !name.equals(that.name) : that.name != null) return false;
        if (email != null ? !email.equals(that.email) : that.email != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + (telNum != null ? telNum.hashCode() : 0);
        result = 31 * result + (name != null ? name.hashCode() : 0);
        result = 31 * result + (email != null ? email.hashCode() : 0);
        result = 31 * result + (orders != null ? orders.hashCode() : 0);
        return result;
    }
}
