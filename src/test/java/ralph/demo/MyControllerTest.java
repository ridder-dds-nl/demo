package ralph.demo;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import ralph.domain.PersonDomain;

import static org.junit.Assert.assertEquals;

/**
 * Created by ralph on 17-6-2016.
 */
@RunWith(MockitoJUnitRunner.class)
public class MyControllerTest {

    @Mock
    private PersonDomain personDomain;

    @Test
    public void test() {
        MyController controller = new MyController();
        controller.setPersonDomain(personDomain);
        assertEquals(MyController.HELLO, controller.sayHello());
    }
}
