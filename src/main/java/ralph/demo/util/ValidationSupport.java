package ralph.demo.util;

import java.util.List;

/**
 * Created by ralph on 6/19/16.
 */
public class ValidationSupport {


    public static boolean validateGiven(String value, String validationMessage, List<String> validationMessages) {
        return validate(value != null && !value.trim().isEmpty(), validationMessage, validationMessages);
    }

    public static boolean validateNotNull(Object value, String message, List<String> validationList) {
        return validate(value != null, message, validationList);
    }

    public static boolean validateNull(Object value, String message, List<String> validationList) {
        return validate(value == null, message, validationList);
    }

    public static boolean validate(boolean expression, String validationMessage, List<String> validationMessages) {
        if (!expression) {
            validationMessages.add(validationMessage);
        }
        return true;
    }
}
