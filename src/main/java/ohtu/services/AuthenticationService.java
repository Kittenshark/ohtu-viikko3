package ohtu.services;

import ohtu.domain.User;
import java.util.ArrayList;
import java.util.List;
import ohtu.data_access.UserDao;

public class AuthenticationService {

    private UserDao userDao;

    public AuthenticationService(UserDao userDao) {
        this.userDao = userDao;
    }

    public boolean logIn(String username, String password) {
        for (User user : userDao.listAll()) {
            if (checkLogin(user, username, password)) {
                return true;
            }
        }
        return false;
    }
    
    public boolean checkLogin(User user, String username, String password) {
        return user.getUsername().equals(username) && user.getPassword().equals(password);
    }

    public boolean createUser(String username, String password) {
        if (userDao.findByName(username) != null) {
            return false;
        }

        if (invalid(username, password)) {
            return false;
        }

        userDao.add(new User(username, password));

        return true;
    }

    private boolean invalid(String username, String password) {
        // validity check of username and password
	if (password.length() < 8 || username.length() < 3) {
            return true;	
	}
        return !password.matches(".*\\d.*");
    }
}
