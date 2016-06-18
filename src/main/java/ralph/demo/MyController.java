package ralph.demo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import ralph.domain.Person;
import ralph.domain.PersonDomain;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ralph on 17-6-2016.
 */
@RestController
class MyController {

    public static final String HELLO = "Hello!";


    private PersonDomain personDomain;

    @Autowired
    public void setPersonDomain(PersonDomain personDomain) {
        this.personDomain = personDomain;
    }

    /**
     * GET  http://localhost/hello
     *
     * @return
     */
    @RequestMapping(method = RequestMethod.GET, path = "/hello")
    public String sayHello() {
        Person person = personDomain.findByUsername("test");
        return HELLO;
    }

    @RequestMapping(method = RequestMethod.PUT, path = "/hello")
    public Object save(String username) {
        List<String> validations = validate(username);
        if (!validations.isEmpty())
            return validations;
        return personDomain.replace(new Person());
    }

    public List<String> validate(String username) {
        List<String> validations = new ArrayList<>();
        personDomain.validateUserExists(validations, username);
        return validations;
    }

}
