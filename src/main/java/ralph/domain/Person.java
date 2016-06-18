package ralph.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * Created by ralph on 17-6-2016.
 */
@Entity
@Table(name = "user_t")
public class Person {

    @Id
    @Column(name = "username")
    private String username;

    @Column(name = "password")
    private String password;

    public Person() {
        //for jps
    }

    /**
     * Construct with required fields.
     *
     * @param username
     * @param password
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

    @Override
    public String toString() {
        return "Person{" +
                "username='" + username + '\'' +
                ", password='" + password + '\'' +
                '}';
    }
}
