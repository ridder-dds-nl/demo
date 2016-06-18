package ralph.domain;

import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

/**
 * Created by ralph on 17-6-2016.
 */
public class PersonTest {

    @Test
    public void emptyContructor_covered() {
        assertNotNull(new Person());
    }

    @Test
    public void toString_returnsAllValues() {
        assertEquals("Person{username='ralph', password='malph'}", new Person("ralph", "malph").toString());
    }

    @Test
    public void getters_returnCorrectValues() {
        Person person = new Person("diana", "ross");
        assertEquals("diana", person.getUsername());
        assertEquals("ross", person.getPassword());
    }
}
