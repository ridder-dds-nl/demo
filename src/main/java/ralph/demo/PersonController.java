package ralph.demo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import ralph.domain.Person;
import ralph.domain.PersonDomain;

/**
 * Created by ralph on 6/18/16.
 */
@Controller
@Transactional
public class PersonController {

    @Autowired
    PersonDomain personDomain;

    @RequestMapping(path = "/person", method = RequestMethod.GET)
    String index(@RequestParam(value = "username", required = false) String username, ModelMap modelMap) {
        Person person = username == null ? null : personDomain.findByUsername(username);
        person = person == null ? new Person() : person;
        modelMap.addAttribute("person", person);
        return "edit-person";
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    @RequestMapping(path = "/person", method = RequestMethod.POST)
    String write(@ModelAttribute Person person, Model model) {
        person = personDomain.replace(person);
        model.addAttribute("person", person);
        return "redirect:/person?username=" + person.getUsername();
    }

}
