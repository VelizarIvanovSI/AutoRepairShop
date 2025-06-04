package uni.pu.fmi.services;

import org.apache.commons.lang3.StringUtils;
import uni.pu.fmi.models.User;
import uni.pu.fmi.repos.UserRepo;

import java.util.List;

public class LoginService {
    public String login(String username, String password) {
        if (StringUtils.isBlank(password)) {
            return "Паролата не може да бъде празна";
        }
        if (password.length() > 20) {
            return "Пролата е дълга";
        }
        if (username.length() > 20) {
            return "Името е дълго";
        }
        List<User> admins = new UserRepo().getUsers();
        List<User> users = new UserRepo().getUsers();
        boolean isUserExist = users.stream()
                .anyMatch(u -> u.getUsername().equals(username)
                        && u.getPassword().equals(password));

        return isUserExist ? "Влязохте в системата" : "Грешно име или парола";
    }


}
