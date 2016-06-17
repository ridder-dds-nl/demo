package ralph.domain;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.persistence.*;
import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.*;

/**
 * Created by ralph on 17-6-2016.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = JPATestConfiguation.class)
public class UserDomainTest {

    @PersistenceUnit
    private EntityManagerFactory entityManagerFactory;

    @Before
    public void setUp(){
        List<User> userList = Arrays.asList(//
                new User("ralph", "password"),//
                new User("dennie", "secret")
        );
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        EntityTransaction transaction = entityManager.getTransaction();
        transaction.begin();
        userList.parallelStream().forEach(user -> entityManager.merge(user));
        transaction.commit();
    }

    @Test
    public void findByUserName_returnsCorrectInstance() {
        UserDomain userDomain = new UserDomain();
        userDomain.setEntityManager(entityManagerFactory.createEntityManager());
        assertEquals("User{username='ralph', password='password'}", userDomain.findByUsername("ralph").toString());
    }

    @Test
    public void findByUserName_returnsNothing() {
        UserDomain userDomain = new UserDomain();
        userDomain.setEntityManager(entityManagerFactory.createEntityManager());
        assertNull(userDomain.findByUsername("charlie"));
    }

    @Test
    public void authenticate_nonExistingUser() {
        UserDomain userDomain = new UserDomain();
        userDomain.setEntityManager(entityManagerFactory.createEntityManager());
        assertFalse(userDomain.authenticate("charlie", "checkpoint"));
    }

    @Test
    public void authenticate_wrongPassword() {
        UserDomain userDomain = new UserDomain();
        userDomain.setEntityManager(entityManagerFactory.createEntityManager());
        assertFalse(userDomain.authenticate("ralph", "hack01"));
    }

    @Test
    public void authenticate_succeeds() {
        UserDomain userDomain = new UserDomain();
        userDomain.setEntityManager(entityManagerFactory.createEntityManager());
        assertTrue(userDomain.authenticate("ralph", "password"));
    }

}
