package ralph.demo;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import ralph.domain.Person;
import ralph.domain.PersonDomain;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 * Created by ralph on 6/22/16.
 */
@Component
public class ApplicationStartedEventListener implements ApplicationListener<ContextRefreshedEvent> {

    Logger LOGGER = LoggerFactory.getLogger(ApplicationStartedEventListener.class);

    private PersonDomain personDomain;

    @Autowired
    public ApplicationStartedEventListener(PersonDomain personDomain) {
        this.personDomain = personDomain;
    }

    @Override
    @Transactional
    public void onApplicationEvent(ContextRefreshedEvent applicationStartedEvent) {
        if (personDomain.findByUsername("admin") == null) {
            LOGGER.info("Creating admin user.");
            createAdminUser();
        }
        else {
            LOGGER.info("Admin user present.");
        }
    }

    private void createAdminUser() {
        Person admin;
        admin = new Person("bigboss", "*VenomSnake?");
        admin.setFirstName("Administrator");
        admin.setLastName("Administrator");
        admin.setEmailAddress("ralph.portbase@gmail.com");
        admin.setOrganisationShortname("PORTBASE");
        admin.setOrganisationName("Portbase B.V.");
        personDomain.save(admin);
    }
}
