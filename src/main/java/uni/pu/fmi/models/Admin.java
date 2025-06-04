package uni.pu.fmi.models;

import lombok.Getter;
import lombok.Setter;

import java.util.Objects;

@Getter
@Setter
public class Admin {
    private User user;
    private String username;
    private String password;
    private String email;

    private boolean admin;

    public Admin(String admin, String adminpass, String mail, boolean b) {
    }

    public Admin(String ivan, String password123щ, String mail) {
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass())
            return false;

        User user = (User)o;
        return Objects.equals(username, user.username) && Objects.equals(email, user.email);
    }

    @Override
    public int hashCode() {
        int result = Objects.hashCode(username);
        result = 31 * result + Objects.hashCode(email);
        return result;
    }

    public Admin(User user) {
        this.user = user;
    }

    public String getUsername() {
        return user.getUsername();
    }

    public boolean canApproveRequests() {
        return true;
    }
}