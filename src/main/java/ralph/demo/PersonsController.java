package ralph.demo;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import ralph.domain.Person;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by ralph on 6/18/16.
 */
@Controller
public class PersonsController {

    @RequestMapping(path = "/persons")
    String index(ModelMap modelMap) {
        List<Person> personList = new ArrayList<>();
        personList.addAll(Arrays.asList(new Person("amanda", "please"), new Person("ralph", "secret")));
        modelMap.put("personList", personList);
        modelMap.put("welcome", "Welcome");
        return "index";
    }

    public void getPersons() {

    }
}
