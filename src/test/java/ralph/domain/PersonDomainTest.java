package ralph.domain;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.PersistenceUnit;
import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.*;

/**
 * Created by ralph on 17-6-2016.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = JPATestConfiguation.class)
public class PersonDomainTest {

    @PersistenceUnit
    private EntityManagerFactory entityManagerFactory;

    @Before
    public void setUp() {
        List<Person> personList = Arrays.asList(//
                new Person("ralph", "password"),//
                new Person("dennie", "secret")
        );
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        EntityTransaction transaction = entityManager.getTransaction();
        transaction.begin();
        personList.parallelStream().forEach(entityManager::merge);
        transaction.commit();
    }

    @Test
    public void findByUserName_returnsCorrectInstance() {
        PersonDomain personDomain = new PersonDomain();
        personDomain.setEntityManager(entityManagerFactory.createEntityManager());
        assertEquals("Person{username='ralph', password='password'}", personDomain.findByUsername("ralph").toString());
    }

    @Test
    public void findByUserName_returnsNothing() {
        PersonDomain personDomain = new PersonDomain();
        personDomain.setEntityManager(entityManagerFactory.createEntityManager());
        assertNull(personDomain.findByUsername("charlie"));
    }

    @Test
    public void authenticate_nonExistingUser() {
        PersonDomain personDomain = new PersonDomain();
        personDomain.setEntityManager(entityManagerFactory.createEntityManager());
        assertFalse(personDomain.authenticate("charlie", "checkpoint"));
    }

    @Test
    public void authenticate_wrongPassword() {
        PersonDomain personDomain = new PersonDomain();
        personDomain.setEntityManager(entityManagerFactory.createEntityManager());
        assertFalse(personDomain.authenticate("ralph", "hack01"));
    }

    @Test
    public void authenticate_succeeds() {
        PersonDomain personDomain = new PersonDomain();
        personDomain.setEntityManager(entityManagerFactory.createEntityManager());
        assertTrue(personDomain.authenticate("ralph", "password"));
    }

}
