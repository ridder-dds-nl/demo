package ralph.demo.account;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * Created by ralph on 6/19/16.
 */
public class AccountForm {

    private String username;

    private String password;

    private String passwordCheck;

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPasswordCheck() {
        return passwordCheck;
    }

    public void setPasswordCheck(String passwordCheck) {
        this.passwordCheck = passwordCheck;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }


    public Optional<List<String>> validateInput() {
        List<String> validationMessages = new ArrayList<>();
        validateUsernameGiven(validationMessages);
        boolean passwordGiven = validatePasswordGiven(validationMessages);
        boolean passwordCheckGiven = validatePasswordCheckGiven(validationMessages);
        if (passwordCheckGiven && passwordGiven) {
            validatePasswordAndCheckEqual(validationMessages);
        }
        return validationMessages.isEmpty() ? Optional.empty() : Optional.of(validationMessages);
    }

    private boolean validateUsernameGiven(List<String> validationMessages) {
        String userName = getUsername();
        return validate(userName != null && !userName.trim().isEmpty(), "username.missing", validationMessages);
    }

    private boolean validatePasswordGiven(List<String> validationMessages) {
        String password = getPassword();
        return validateGiven(password, "password.missing", validationMessages);
    }

    private boolean validatePasswordCheckGiven(List<String> validationMessages) {
        String password = getPasswordCheck();
        return validateGiven(password, "passwordCheck.missing", validationMessages);
    }

    private boolean validatePasswordAndCheckEqual(List<String> validationMessges) {
        String password = getPassword();
        String passwordCheck = getPasswordCheck();
        return validate(passwordCheck.equals(password), "passwordCheck.doesnotmatchpassword", validationMessges);
    }

    private boolean validateGiven(String value, String validationMessage, List<String> validationMessages) {
        return validate(value != null && !value.trim().isEmpty(), validationMessage, validationMessages);
    }

    private boolean validate(boolean expression, String validationMessage, List<String> validationMessages) {
        if (!expression) {
            validationMessages.add(validationMessage);
        }
        return true;
    }


}
