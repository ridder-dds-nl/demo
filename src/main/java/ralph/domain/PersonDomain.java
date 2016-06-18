package ralph.domain;

import org.springframework.stereotype.Component;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.util.List;

/**
 * Created by ralph on 17-6-2016.
 */
@Component
public class PersonDomain {


    private EntityManager entityManager;

    @PersistenceContext
    public void setEntityManager(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    public boolean validateUserExists(List<String> validations, String username) {
        return validate(validations, findByUsername(username) != null, "domains.users.notfound");
    }

    public boolean validate(List<String> validations, boolean expression, String validationCode) {
        if (!expression) {
            validations.add(validationCode);
            return false;
        }
        return true;
    }

    public Person findByUsername(String username) {
        return entityManager.find(Person.class, username);
    }

    public boolean authenticate(String username, String password) {
        Query query = entityManager.createQuery("from Person u where u.username = :username and u.password = :password");
        query.setParameter("username", username);
        query.setParameter("password", password);
        return !query.getResultList().isEmpty();
    }

    public Person replace(Person person) {
        return entityManager.merge(person);
    }
}
