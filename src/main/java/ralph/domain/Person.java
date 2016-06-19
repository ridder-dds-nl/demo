package ralph.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * Created by ralph on 17-6-2016.
 */
@Entity
@Table(name = "person")
public class Person {

    @Id
    @Column(name = "username")
    private String username;

    @Column(name = "password")
    private String password;

    public Person() {
        //for jpa
    }

    /**
     * Construct with required fields.
     *
     * @param username required
     * @param password required
     */
    public Person(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public String toString() {
        return "Person{" +
                "username='" + username + '\'' +
                ", password='" + password + '\'' +
                '}';
    }
}
