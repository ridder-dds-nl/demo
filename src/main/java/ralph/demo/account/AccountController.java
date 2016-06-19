package ralph.demo.account;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import ralph.domain.Person;
import ralph.domain.PersonDomain;

import java.util.List;
import java.util.Optional;

/**
 * Created by ralph on 6/19/16.
 */
@Controller(value = "/account")
//@SessionAttributes({"validationCodes")
public class AccountController {

    private final PersonDomain personDomain;

    @Autowired
    public AccountController(PersonDomain personDomain) {
        this.personDomain = personDomain;
    }

    @RequestMapping(path = "/account/details")
    public String index(Model model) {
        if (!model.containsAttribute("account")) {
            model.addAttribute("account", new AccountForm());
        }
        return "create-account";
    }

    @RequestMapping(path = "/account/write", method = RequestMethod.POST)
    public String details(@ModelAttribute AccountForm accountForm, RedirectAttributes redirectAttributes) {
        Optional<List<String>> validationCodes = accountForm.validateInput();
        if (validationCodes.isPresent()) {
            redirectAttributes.addFlashAttribute("validationCodes", validationCodes.get());
            redirectAttributes.addFlashAttribute("account", accountForm);
            return "redirect:/account/details";
        }
        personDomain.replace(new Person(accountForm.getUsername(), accountForm.getPassword()));
        return "redirect:/account/created";
    }


}
