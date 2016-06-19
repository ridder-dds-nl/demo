package ralph.demo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import ralph.domain.Person;
import ralph.domain.PersonDomain;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by ralph on 6/18/16.
 */
@Controller
public class PersonsController {

    @Autowired
    PersonDomain personDomain;

    @RequestMapping(path = "/persons")
    String index(ModelMap modelMap) {
        modelMap.put("personList", personDomain.all());
        return "person-overview";
    }
}
