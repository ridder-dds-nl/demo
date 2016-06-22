package ralph.domain;

import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.util.List;

import static ralph.demo.util.ValidationSupport.validate;
import static ralph.demo.util.ValidationSupport.validateGiven;
import static ralph.demo.util.ValidationSupport.validateNotNull;

/**
 * Created by ralph on 17-6-2016.
 */
@Component
public class PersonDomain {


    public static final String DOMAIN_PERSON_NOTFOUND = "domain.personnotfound";
    private EntityManager entityManager;

    @PersistenceContext
    public void setEntityManager(EntityManager entityManager) {
        this.entityManager = entityManager;
    }


    public boolean validateUsernameGiven(Person person, List<String> validationList) {
        return validateGiven(person.getUsername(), "domain.person.username.required", validationList);
    }

    public boolean validateFirstNameGiven(Person person, List<String> validationList) {
        return validateGiven(person.getFirstName(), "domain.person.firstname.required", validationList);
    }

    public boolean validateLastNameGiven(Person person, List<String> validationList) {
        return validateGiven(person.getLastName(), "domain.person.lastname.required", validationList);
    }

    public boolean validateEmailAddressGiven(Person person, List<String> validationList) {
        return validateGiven(person.getEmailAddress(), "domain.person.emailaddress.required", validationList);
    }

    public boolean validateOrganisationShortnameGiven(Person person, List<String> validationList) {
        return validateGiven(person.getOrganisationShortname(), "domain.person.organisationshortname.required", validationList);
    }

    public boolean validateNameGiven(Person person, List<String> validationList) {
        return validateGiven(person.getOrganisationName(), "domain.person.organisationname.required", validationList);
    }

    public boolean validateUserExists(String username, List<String> validations) {
        return validate(findByUsername(username) != null, DOMAIN_PERSON_NOTFOUND, validations);
    }

    public boolean validateUserDoesNotExist(String username, List<String> validationList) {
        return validate(findByUsername(username) == null, "domains.user.alreadyexists", validationList);
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

    @Transactional
    public Person save(Person person) {
        return entityManager.merge(person);
    }

    public List<Person> all() {
        Query query = entityManager.createQuery("from Person p");
        return query.getResultList();
    }

    @Transactional
    public void delete(String username) {
        entityManager.remove(findByUsername(username));
    }

    public Person findByEmailAddress(String emailAddress) {
        Query query = entityManager.createQuery("from Person p where p.emailAddress= :emailAddress");
        query.setParameter("emailAddress", emailAddress);
        List<Person> resultList = query.getResultList();
        if (resultList.isEmpty()) {
            return null;
        }
        return resultList.get(0);
    }

    public boolean validatePersonExists(Person person, List<String> validationList) {
        return validateNotNull(person, DOMAIN_PERSON_NOTFOUND, validationList);
    }
}
