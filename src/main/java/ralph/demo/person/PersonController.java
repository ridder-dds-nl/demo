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

    public static final String APPLICATION_VALIDATIONS = "applicationValidations";
    private static final String FORM_SUCCESS = "formSuccess";
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
            else {
                person = personUI;
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
        List<String> validationList = validateForReplacePerson(person);
        if (validationList.isEmpty()) {
            redirectAttributes.addFlashAttribute(FORM_SUCCESS, validationList);
            return "redirect:/person/details?username=" + personDomain.save(person).getUsername();
        }
        else {
            redirectAttributes.addFlashAttribute(APPLICATION_VALIDATIONS, validationList);
            redirectAttributes.addFlashAttribute("person", person);
            return "redirect:/person/details?username=" + person.getUsername();
        }
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
        personDomain.validateEmailAddressGiven(person, validationList);
        personDomain.validateOrganisationShortnameGiven(person, validationList);
        if (validationList.isEmpty()) {
            personDomain.validateUserDoesNotExist(person.getUsername(), validationList);
        }
        if (updateFlashAttributes(person, redirectAttributes, validationList)) return false;
        return true;
    }

    private List<String> validateForReplacePerson(Person person) {
        List<String> validationList = new ArrayList<>();
        personDomain.validateUsernameGiven(person, validationList);
        personDomain.validateFirstNameGiven(person, validationList);
        personDomain.validateLastNameGiven(person, validationList);
        personDomain.validateEmailAddressGiven(person, validationList);
        personDomain.validateOrganisationShortnameGiven(person, validationList);
        if (validationList.isEmpty()) {
            personDomain.validateUserExists(person.getUsername(), validationList);
        }
        return validationList;
    }

    private boolean updateFlashAttributes(Person person, RedirectAttributes redirectAttributes, List<String> validationList) {
        if (!validationList.isEmpty()) {
            redirectAttributes.addFlashAttribute(APPLICATION_VALIDATIONS, validationList);
            redirectAttributes.addFlashAttribute("person", person);
            return true;
        }
        redirectAttributes.addFlashAttribute(FORM_SUCCESS, validationList);
        return false;
    }

}
