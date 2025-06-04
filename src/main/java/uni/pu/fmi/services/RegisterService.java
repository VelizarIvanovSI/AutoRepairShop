package uni.pu.fmi.services;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.validator.routines.EmailValidator;
import uni.pu.fmi.models.User;
import uni.pu.fmi.repos.UserRepo;

import java.util.List;

public class RegisterService {
    public String register(String username, String email, String password, String pass2) {
        String errorMessage = validate(username, email, password, pass2);
        if (errorMessage != null) {
            return errorMessage;
        }
        final User user = new User(username, password, email);
        new UserRepo().add(user);
        return "регестрирахте се";
    }

    private static String validate(String username, String email, String password, String pass2) {
        if (username.length() < 3) {
            return "Късо име";
        }
        if (username.length() > 20) {
            return "Дълго име";
        }
        if (!EmailValidator.getInstance().isValid(email)) {
            return "Невалидна поща";
        }
        if (StringUtils.isBlank(password)) {
            return "Въведи парола";
        }
        if (StringUtils.isBlank(pass2)) {
            return "Въведи втора парола";
        }
        if (!password.equals(pass2)) {
            return "различни пароли";
        }
        List<User> users = new UserRepo().getUsers();
        if (users.stream().anyMatch(u -> u.getUsername().equals(username))) {
            return "името е заето";
        }
        return null;
    }
}
