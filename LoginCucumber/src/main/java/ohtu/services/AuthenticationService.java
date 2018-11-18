package ohtu.services;

import ohtu.domain.User;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import ohtu.data_access.UserDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class AuthenticationService {

    private UserDao userDao;
    
    @Autowired
    public AuthenticationService(UserDao userDao) {
        this.userDao = userDao;
    }

    public boolean logIn(String username, String password) {
        for (User user : userDao.listAll()) {
            if (user.getUsername().equals(username)
                    && user.getPassword().equals(password)) {
                return true;
            }
        }

        return false;
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
        if (password.length() < 8 || username.length() < 3 ) {
            return true;
        }
        
        String testPass = password.replaceAll("[*a-zA-Z]", "");
        Pattern usernamePattern = Pattern.compile("^[a-z]*");
        Matcher usernameMatcher = usernamePattern.matcher(username);
            
        if (!usernameMatcher.find()) {
            return true;
        }
        
        if(testPass.length()==0) {
            return true;
        }
        

        return false;
    }
}
