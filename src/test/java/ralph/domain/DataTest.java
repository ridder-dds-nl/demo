package ralph.domain;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.yaml.snakeyaml.Yaml;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.PersistenceUnit;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Map;

/**
 * Created by ralph on 7/2/16.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = JPATestConfiguation.class)
public class DataTest {

    private final static Logger _logger = LoggerFactory.getLogger(DataTest.class);

    @PersistenceUnit
    private EntityManagerFactory entityManagerFactory;

    @Test
    public void test() throws FileNotFoundException {
        importToDatabase();
    }

    private void importToDatabase() throws FileNotFoundException {
        InputStream input = new FileInputStream(new File("src/test/resources/persons.yaml"));
        Yaml yaml = new Yaml();
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        EntityTransaction transaction = entityManager.getTransaction();
        transaction.begin();
        _logger.info("Persisting persons...");
        for (Object data : yaml.loadAll(input)) {
            save(entityManager, mapPerson((Map<String, Object>) data));
        }
        transaction.commit();
    }

    private Person mapPerson(Map<String, Object> objectMap) {
        Person person = new Person();
        person.setUsername((String) objectMap.get(Person.AttributeNames.USERNAME));
        person.setFirstName((String) objectMap.get(Person.AttributeNames.FIRST_NAME));
        person.setLastName((String) objectMap.get(Person.AttributeNames.LAST_NAME));
        person.setEmailAddress((String) objectMap.get(Person.AttributeNames.EMAIL_ADDRESS));
        return person;
    }

    private Person save(EntityManager entityManager, Person person) {
        return entityManager.merge(person);
    }

    @Test
    public void t() throws NoSuchAlgorithmException {
        System.out.println(cryptWithMD5("Hello!"));
        System.out.println(cryptWithMD5("Hello!"));
        System.out.println(cryptWithMD5("Seriertje"));

    }

    public static String cryptWithMD5(String pass) throws NoSuchAlgorithmException {
        MessageDigest md = MessageDigest.getInstance("MD5");
        byte[] passBytes = pass.getBytes();
        md.reset();
        byte[] digested = md.digest(passBytes);
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < digested.length; i++) {
            sb.append(Integer.toHexString(0xff & digested[i]));
        }
        return sb.toString();
    }
}
