package uni.pu.fmi.repos;

import uni.pu.fmi.models.User;

import java.util.ArrayList;
import java.util.List;

public class UserRepo {

    public List<User> getUsers(){
        ArrayList<User> users = new ArrayList<>();
        users.add(new User("ivan","Password123щ","test@test.com"));
        return users;
    }

    public void add(User user) {
        getUsers().add(user);
    }
}
