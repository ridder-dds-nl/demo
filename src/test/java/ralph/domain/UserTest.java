package ralph.domain;

import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

/**
 * Created by ralph on 17-6-2016.
 */
public class UserTest {

    @Test
    public void emptyContructor_covered() {
        assertNotNull(new User());
    }

    @Test
    public void toString_returnsAllValues() {
       assertEquals("User{username='ralph', password='malph'}", new User("ralph", "malph").toString());
    }

    @Test
    public void getters_returnCorrectValues() {
        User user = new User("diana", "ross");
        assertEquals("diana", user.getUsername());
        assertEquals("ross", user.getPassword());
    }
}
