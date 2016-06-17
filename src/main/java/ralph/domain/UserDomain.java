package ralph.domain;

import org.springframework.stereotype.Component;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

/**
 * Created by ralph on 17-6-2016.
 */
@Component
public class UserDomain {


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

    public User findByUsername(String username) {
        return entityManager.find(User.class, username);
    }

    public User replace(User user) {
        return entityManager.merge(user);
    }
}
