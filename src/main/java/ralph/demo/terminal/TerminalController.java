package ralph.demo.terminal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import ralph.domain.Terminal;
import ralph.domain.TerminalDomain;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ralph on 6/18/16.
 */
@Controller
public class TerminalController {

    public static final String APPLICATION_VALIDATIONS = "applicationValidations";
    private static final String FORM_SUCCESS = "formSuccess";
    public static final String TERMINAL = "terminal";
    public static final String NEW_OBJECT = "newObject";

    private final TerminalDomain terminalDomain;

    @Autowired
    public TerminalController(TerminalDomain terminalDomain) {
        this.terminalDomain = terminalDomain;
    }

    @RequestMapping(path = "/terminal/new", method = RequestMethod.GET)
    public String newTerminal(ModelMap modelMap) {
        Terminal terminal = (Terminal) modelMap.get(TERMINAL);
        terminal = terminal == null ? new Terminal() : terminal;
        modelMap.addAttribute(NEW_OBJECT, true);
        modelMap.addAttribute(TERMINAL, terminal);
        return "/terminal/terminal";
    }

    @RequestMapping(path = "/terminal", method = RequestMethod.GET)
    public String existingTerminal(@RequestParam(value = "name", required = false) String name, ModelMap modelMap) {
        Terminal terminal = (Terminal) modelMap.get(TERMINAL);
        if (terminal == null) {
            List<String> validationList = new ArrayList<>();
            terminalDomain.validateExists(name, validationList);
            if (!validationList.isEmpty()) {
                throw new RuntimeException("Not found.");
            }
            terminal = terminalDomain.findByName(name);
        }
        modelMap.addAttribute(NEW_OBJECT, false);
        modelMap.addAttribute(TERMINAL, terminal);
        return "/terminal/terminal";
    }

    @RequestMapping(path = "/terminal/new", method = RequestMethod.POST)
    public String create(@ModelAttribute Terminal terminal, RedirectAttributes redirectAttributes) {
        TerminalValidator validator = new TerminalValidator();
        validator.validate(terminalDomain, terminal);
        if (validator.isValid()) {
            redirectAttributes.addFlashAttribute(FORM_SUCCESS, true);
            return "redirect:/terminal?name=" + terminalDomain.save(terminal).getName();
        }
        redirectAttributes.addFlashAttribute(APPLICATION_VALIDATIONS, validator.getValidationList());
        redirectAttributes.addFlashAttribute(TERMINAL, terminal);
        return "redirect:/terminal/new";
    }


    @RequestMapping(path = "/terminal", method = RequestMethod.POST)
    public String replace(@ModelAttribute Terminal terminal, RedirectAttributes redirectAttributes) {
        TerminalValidator validator = new TerminalValidator();
        validator.validateForReplace(terminalDomain, terminal);
        if (validator.isValid()) {
            redirectAttributes.addFlashAttribute(FORM_SUCCESS, true);
            return "redirect:/terminal?name=" + terminalDomain.save(terminal).getName();
        }
        redirectAttributes.addFlashAttribute(APPLICATION_VALIDATIONS, validator.getValidationList());
        redirectAttributes.addFlashAttribute(TERMINAL, terminal);
        return "redirect:/terminal?username=" + terminal.getName();
    }

    @RequestMapping(path = "/terminal/delete", method = RequestMethod.GET)
    String delete(@RequestParam(value = "name") String username) {
        terminalDomain.delete(username);
        return "redirect:/terminals";
    }


}
