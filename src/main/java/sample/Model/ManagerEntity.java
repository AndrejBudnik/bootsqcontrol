package sample.Model;

import javax.persistence.*;
import java.io.Serializable;

/**
 * Created by kotsy on 20.12.2016.
 */
@Entity
@Table(name = "manager", schema = "public", catalog = "uchet")
public class ManagerEntity implements Serializable {

    private static final long serialVersionUID = -8699779129093072804L;

    private String login;
    private String passHash;
    private String passhash;
    private short id;

    @Override
    public String toString() {
        return "Manager{" +
                "login='" + login + '\'' +
                ", passHash='" + passHash + '\'' +
                '}';
    }

    @Basic
    @Column(name = "login", nullable = true, length = 100)
    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPassHash() {
        return passHash;
    }

    public void setPassHash(String passHash) {
        this.passHash = passHash;
    }

    @Basic
    @Column(name = "passhash", nullable = true, length = 100)
    public String getPasshash() {
        return passhash;
    }

    public void setPasshash(String passhash) {
        this.passhash = passhash;
    }

    @Id
    @Column(name = "id", nullable = false)
    public short getId() {
        return id;
    }

    public void setId(short id) {
        this.id = id;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ManagerEntity that = (ManagerEntity) o;

        if (id != that.id) return false;
        if (login != null ? !login.equals(that.login) : that.login != null) return false;
        if (passhash != null ? !passhash.equals(that.passhash) : that.passhash != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = login != null ? login.hashCode() : 0;
        result = 31 * result + (passhash != null ? passhash.hashCode() : 0);
        result = 31 * result + (int) id;
        return result;
    }
}
