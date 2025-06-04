package uni.pu.fmi.services;

import org.apache.commons.lang3.StringUtils;
import uni.pu.fmi.models.RepairRequest;
import uni.pu.fmi.models.User;
import uni.pu.fmi.repos.RepairRequestRepo;

import java.util.List;

public class RepairRequestService {

    private boolean isLoggedIn = false;
    private User currentUser;

    public void login(User user) {
        this.currentUser = user;
        this.isLoggedIn = true;
    }

    public void logout() {
        this.isLoggedIn = false;
        this.currentUser = null;
    }

    public String submitRequest(String model, String issueDescription) {
        if (!isLoggedIn) {
            return "Моля, влезте в системата, за да подадете заявка";
        }

        String validationError = validate(model, issueDescription);
        if (validationError != null) {
            return validationError;
        }

        if (isDuplicate(model, issueDescription)) {
            return "Вече съществува подобна заявка";
        }

        RepairRequest request = new RepairRequest(currentUser.getUsername(), model, issueDescription);
        new RepairRequestRepo().add(request);

        return "Заявката е подадена успешно!";
    }

    private String validate(String model, String issueDescription) {
        if (StringUtils.isBlank(model)) {
            return "Не е въведен модел на колата";
        }

        if (!model.matches("[A-Za-zА-Яа-я0-9\\s-]{2,50}")) {
            return "Моделът на колата съдържа невалидни символи";
        }

        if (StringUtils.isBlank(issueDescription)) {
            return "Моля, опишете проблема";
        }

        if (issueDescription.length() > 500) {
            return "Описанието е твърде дълго";
        }

        return null;
    }

    private boolean isDuplicate(String model, String issueDescription) {
        List<RepairRequest> requests = new RepairRequestRepo().getAll();
        return requests.stream()
                .anyMatch(r -> r.getUsername().equals(currentUser.getUsername())
                        && r.getModel().equals(model)
                        && r.getIssueDescription().equals(issueDescription));
    }
}