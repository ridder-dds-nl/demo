package ralph.domain;

import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.util.List;

import static ralph.demo.util.ValidationSupport.*;

/**
 * Created by ralph on 17-6-2016.
 */
@Component
public class TerminalDomain {


    public static final String DOMAIN_TERMINAL_NOTFOUND = "domain.terminal.notfound";
    public static final String DOMAIN_TERMINAL_EXISTS = "domain.terminal.exists";
    public static final String DOMAIN_TERMINAL_NAME_REQUIRED = "domain.terminal.name.required";

    private EntityManager entityManager;

    @PersistenceContext
    public void setEntityManager(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    public Terminal findByName(String name) {
        return entityManager.find(Terminal.class, name);
    }

    @Transactional
    public Terminal save(Terminal terminal) {
        return entityManager.merge(terminal);
    }

    public List<Terminal> all() {
        Query query = entityManager.createQuery("from Terminal t");
        return query.getResultList();
    }

    @Transactional
    public void delete(String name) {
        entityManager.remove(findByName(name));
    }

    public boolean validateExists(String name, List<String> validationList) {
        return validateNotNull(findByName(name), DOMAIN_TERMINAL_NOTFOUND, validationList);
    }

    public boolean validateDoesNotExist(String name, List<String> validationList) {
        return validateNull(findByName(name), DOMAIN_TERMINAL_EXISTS, validationList);
    }

    public void validateNameGiven(String name, List<String> validationList) {
        validateGiven(name, DOMAIN_TERMINAL_NAME_REQUIRED, validationList);
    }
}
