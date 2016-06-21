package ralph.demo.util;

import java.util.List;

/**
 * Created by ralph on 6/19/16.
 */
public class ValidationSupport {


    public static final boolean validateGiven(String value, String validationMessage, List<String> validationMessages) {
        return validate(value != null && !value.trim().isEmpty(), validationMessage, validationMessages);
    }

    public static final boolean validate(boolean expression, String validationMessage, List<String> validationMessages) {
        if (!expression) {
            validationMessages.add(validationMessage);
        }
        return true;
    }
}
