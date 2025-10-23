package allynn.alvarico.repository;

import allynn.alvarico.model.User;

public class UserRepository {

    public User createUser(String name, String password){
        return new User(name, password);
    }
}
