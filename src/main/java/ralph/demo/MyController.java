package ralph.demo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import ralph.domain.User;
import ralph.domain.UserDomain;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ralph on 17-6-2016.
 */
@RestController
class MyController {

    public static final String HELLO = "Hello!";


    private UserDomain userDomain;

    @Autowired
    public void setUserDomain(UserDomain userDomain) {
        this.userDomain = userDomain;
    }

    /**
     * GET  http://localhost/hello
     *
     * @return
     */
    @RequestMapping(method = RequestMethod.GET, path = "/hello")
    public String sayHello() {
        User user = userDomain.findByUsername("test");
        return HELLO;
    }

    @RequestMapping(method = RequestMethod.PUT, path = "/hello")
    public Object save(String username) {
        List<String> validations = validate(username);
        if (!validations.isEmpty())
            return validations;
        return userDomain.replace(new User());
    }

    public List<String> validate(String username) {
        List<String> validations = new ArrayList<>();
        userDomain.validateUserExists(validations, username);
        return validations;
    }

}
