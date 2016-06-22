package ralph.demo.account;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailSender;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import ralph.domain.Person;
import ralph.domain.PersonDomain;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static java.util.Optional.of;

/**
 * Created by ralph on 6/19/16.
 */
@Controller
public class ForgotPasswordController {

    private final PersonDomain personDomain;

    private final MailSender mailSender;
    private final NewPasswordEmailMessageCreator newPasswordEmailMessageCreator;

    @Autowired
    public ForgotPasswordController(PersonDomain personDomain, MailSender mailSender, NewPasswordEmailMessageCreator newPasswordEmailMessageCreator) {
        this.personDomain = personDomain;
        this.mailSender = mailSender;
        this.newPasswordEmailMessageCreator = newPasswordEmailMessageCreator;
    }

    @RequestMapping(path = "/account/request-password", method = RequestMethod.GET)
    public String showRequestPassword(Model model) {
        if (!model.containsAttribute("account")) {
            model.addAttribute("account", new AccountForm());
        }
        return "account/request-password";
    }

    @RequestMapping(path = "/account/password-sent", method = RequestMethod.GET)
    public String passwordSent() {
        return "account/password-sent";
    }

    @RequestMapping(path = "/account/request-password", method = RequestMethod.POST)
    public String submitRequestPassword(@ModelAttribute AccountForm accountForm, RedirectAttributes redirectAttributes) {
        List<String> validationList = validate(accountForm);
        if (!validationList.isEmpty()) {
            redirectAttributes.addFlashAttribute("applicationValidations", validationList);
            redirectAttributes.addFlashAttribute("account", accountForm);
            return "redirect:/account/request-password";
        }
        createAndSendEmail(accountForm.getUsername());
        return "redirect:/account/password-sent";
    }

    private void createAndSendEmail(String emailAddress) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(emailAddress);
        message.setSubject(newPasswordEmailMessageCreator.getSubject());
        message.setText(newPasswordEmailMessageCreator.create());
        mailSender.send(message);
    }

    List<String> validate(AccountForm accountForm) {
        List<String> validationList = new ArrayList<>();
        accountForm.validateUsernameGiven(validationList);
        if (validationList.isEmpty()) {
            Person person = personDomain.findByEmailAddress(accountForm.getUsername());
            personDomain.validatePersonExists(person, validationList);
        }
        return validationList;
    }

}
