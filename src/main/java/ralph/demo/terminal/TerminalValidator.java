package ralph.demo.terminal;

import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import ralph.domain.Terminal;
import ralph.domain.TerminalDomain;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Created by ralph on 6/29/16.
 */
public class TerminalValidator {

    private final List<String> validationList = new ArrayList<>();

    public void validate(TerminalDomain terminalDomain, Terminal terminal) {
        terminalDomain.validateNameGiven(terminal.getName(), validationList);
        if (validationList.isEmpty()) {
            terminalDomain.validateDoesNotExist(terminal.getName(), validationList);
        }
    }

    public void validateForReplace(TerminalDomain terminalDomain, Terminal terminal) {
        List<String> validationList = new ArrayList<>();
        if (validationList.isEmpty()) {
            terminalDomain.validateExists(terminal.getName(), validationList);
        }
    }

    public boolean isValid() {
        return validationList.isEmpty();
    }

    public List<String> getValidationList() {
        return Collections.unmodifiableList(validationList);
    }
}
