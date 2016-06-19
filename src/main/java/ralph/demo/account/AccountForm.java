package ralph.demo.account;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static ralph.demo.ValidationSupport.validate;
import static ralph.demo.ValidationSupport.validateGiven;

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
        validatePassword(validationMessages);
        return validationMessages.isEmpty() ? Optional.empty() : Optional.of(validationMessages);
    }

    void validatePassword(List<String> validationMessages) {
        boolean passwordGiven = validatePasswordGiven(validationMessages);
        boolean passwordCheckGiven = validatePasswordCheckGiven(validationMessages);
        if (passwordCheckGiven && passwordGiven) {
            validatePasswordAndCheckEqual(validationMessages);
        }
    }

    boolean validateUsernameGiven(List<String> validationMessages) {
        return validate(username != null && !username.trim().isEmpty(), "username.missing", validationMessages);
    }

    boolean validatePasswordGiven(List<String> validationMessages) {
        return validateGiven(password, "password.missing", validationMessages);
    }

    boolean validatePasswordCheckGiven(List<String> validationMessages) {
        return validateGiven(passwordCheck, "passwordCheck.missing", validationMessages);
    }

    boolean validatePasswordAndCheckEqual(List<String> validationMessges) {
        return validate(passwordCheck.equals(password), "passwordCheck.doesnotmatchpassword", validationMessges);
    }


}
