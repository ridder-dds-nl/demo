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

    public static class AttributeNames {
        public static final String USERNAME = "username";
        public static final String PASSWORD = "password";
        public static final String FIRST_NAME = "firstName";
        public static final String LAST_NAME = "lastName";
        public static final String EMAIL_ADDRESS = "emailAddress";
    }

    @Id
    @Column(name = "username")
    private String username;

    @Column(name = "password")
    private String password;

    @Column(name = "first_name")
    private String firstName;

    @Column(name = "last_name")
    private String lastName;

    @Column(name = "email_address")
    private String emailAddress;

    @Column(name = "organisation_shortname")
    private String organisationShortname;

    @Column(name = "organisation_name")
    private String organisationName;

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

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmailAddress() {
        return emailAddress;
    }

    public void setEmailAddress(String emailAddress) {
        this.emailAddress = emailAddress;
    }

    public String getOrganisationShortname() {
        return organisationShortname;
    }

    public void setOrganisationShortname(String organisationShortname) {
        this.organisationShortname = organisationShortname;
    }

    public String getOrganisationName() {
        return organisationName;
    }

    public void setOrganisationName(String organisationName) {
        this.organisationName = organisationName;
    }

    @Override
    public String toString() {
        return "Person{" +
                "username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", emailAddress='" + emailAddress + '\'' +
                ", organisationShortname='" + organisationShortname + '\'' +
                ", organisationName='" + organisationName + '\'' +
                '}';
    }
}
