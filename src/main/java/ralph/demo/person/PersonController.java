package ralph.demo.person;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import ralph.domain.Person;
import ralph.domain.PersonDomain;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ralph on 6/18/16.
 */
@Controller
public class PersonController {

    PersonDomain personDomain;

    @Autowired
    public PersonController(PersonDomain personDomain) {
        this.personDomain = personDomain;
    }

    @RequestMapping(path = "/person/details", method = RequestMethod.GET)
    String index(@RequestParam(value = "username", required = false) String username, ModelMap modelMap) {
        boolean newUser = username == null;
        Person personUI = (Person) modelMap.get("person");
        Person person = null;
        if (newUser) {
            person = personUI == null ? new Person() : personUI;
        } else {
            if (personUI == null) {
                List<String> validationList = new ArrayList<>();
                personDomain.validateUserExists(username, validationList);
                if (!validationList.isEmpty()) {
                    throw new RuntimeException("Not found.");
                }
                person = personDomain.findByUsername(username);
            }
        }
        modelMap.addAttribute("newUser", newUser);
        modelMap.addAttribute("person", person);
        return "edit-person";
    }

    @RequestMapping(path = "/person/create", method = RequestMethod.POST)
    public String createPerson(@ModelAttribute Person person, RedirectAttributes redirectAttributes) {
        return validateForCreatePerson(person, redirectAttributes) ?//
                "redirect:/person/details?username=" + personDomain.save(person).getUsername()//
                : "redirect:/person/details";
    }


    @RequestMapping(path = "/person/replace", method = RequestMethod.POST)
    public String replacePerson(@ModelAttribute Person person, RedirectAttributes redirectAttributes) {
        return validateForReplacePerson(person, redirectAttributes) ?//
                "redirect:/person/details?username=" + personDomain.save(person).getUsername()//
                : "redirect:/person/details?username=" + person.getUsername();
    }

    @RequestMapping(path = "/person/delete", method = RequestMethod.GET)
    String delete(@RequestParam(value = "username") String username) {
        personDomain.delete(username);
        return "redirect:/persons";
    }


    private boolean validateForCreatePerson(Person person, RedirectAttributes redirectAttributes) {
        List<String> validationList = new ArrayList<>();
        personDomain.validateUsernameGiven(person, validationList);
        personDomain.validateFirstNameGiven(person, validationList);
        personDomain.validateLastNameGiven(person, validationList);
        if (validationList.isEmpty()) {
            personDomain.validateUserDoesNotExist(person.getUsername(), validationList);
        }
        if (updateFlashAttributes(person, redirectAttributes, validationList)) return false;
        return true;
    }

    private boolean validateForReplacePerson(Person person, RedirectAttributes redirectAttributes) {
        List<String> validationList = new ArrayList<>();
        personDomain.validateUsernameGiven(person, validationList);
        personDomain.validateFirstNameGiven(person, validationList);
        personDomain.validateLastNameGiven(person, validationList);
        if (validationList.isEmpty()) {
            personDomain.validateUserExists(person.getUsername(), validationList);
        }
        if (updateFlashAttributes(person, redirectAttributes, validationList)) return false;
        return true;
    }

    private boolean updateFlashAttributes(Person person, RedirectAttributes redirectAttributes, List<String> validationList) {
        if (!validationList.isEmpty()) {
            redirectAttributes.addFlashAttribute("validations", validationList);
            redirectAttributes.addFlashAttribute("person", person);
            return true;
        }
        return false;
    }

}
