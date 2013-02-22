/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Conference.User;

import Conference.Database.DAOException;
import Conference.Database.UserDao;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.http.HttpServletRequest;

/**
 *
 * @author Quicksort
 */
public final class UserForm {
    private static final String EMAIL = "email";
    private static final String DISPLAY_NAME = "display_name";
    private static final String PASSWORD = "password";
    private static final String PASSWORD_CONFIRMATION = "password_confirmation";
    
    private UserDao userDao;
    private String result;
    private Map<String, String> errors = new HashMap<String, String>();

    UserForm(UserDao userDao) {
        this.userDao = userDao;
    }
    
    public String getResult() {
        return result;
    }
    public Map<String, String> getErrors() {
        return errors;
    }
    public User RegisterUser(HttpServletRequest request) throws SQLException, ClassNotFoundException {
        String email = getFieldValue(request, EMAIL);
        String displayName = getFieldValue(request, DISPLAY_NAME);
        String password = getFieldValue(request, PASSWORD);
        String passwordConfirmation = getFieldValue(request, PASSWORD_CONFIRMATION);
        
        User user = new User();
        
        try {
            validateEmail(email);
        } catch (Exception e) {
            setError(EMAIL, e.getMessage());
        }
        try {
            validatePassword(password, passwordConfirmation);
        } catch (Exception e) {
            setError(PASSWORD, e.getMessage());
        }
        try {
            validateDisplayName(displayName);
        } catch (Exception e) {
            setError(DISPLAY_NAME, e.getMessage());
        }
        
        user.setEmail(email);
        user.setPassword(password);
        user.setDisplayName(displayName);
        
        if (errors.isEmpty()) {
            try {
                userDao.insert(user);
            } catch (DAOException ex) {
                Logger.getLogger(UserForm.class.getName()).log(Level.SEVERE, null, ex);
            }
            result = "Success";
        } else {
            result = "Fail";
        }
        
        
        
        return user;
    }

    private String getFieldValue(HttpServletRequest request, String fieldName) {
        String parameter = request.getParameter(fieldName);
        if (parameter == null || parameter.trim().length() == 0) {
            return null;
        } else {
            return parameter;
        }
    }

    private void validateEmail(String email) throws Exception {
        if (email != null) {
            if (!email.matches("([^.@]+)(\\.[^.@]+)*@([^.@]+\\.)+([^.@]+)")) {
                throw new Exception("Invalid email.");
            }
            if (userDao.findByEmail(email) != null) {
                throw new Exception("This email adress is already used.");
            }
        }
    }
    
    private void validatePassword(String password, String passwordConfirmation) throws Exception {
        if (password != null && passwordConfirmation != null) {
            if (!password.equals(passwordConfirmation)) {
                throw new Exception("Passwords must match.");
            } else if (password.length() < 3) {
                throw new Exception("Passwords must contain at least 3 characters.");
            }
        } else {
            throw new Exception("Password is missing");
        }
    }

    private void validateDisplayName(String displayName) throws Exception {
        if ( displayName == null || displayName.length() < 3 ) {
            throw new Exception("Display name must contain at least 3 characters.");
        }
    }
    
    private void setError(String field, String message) {
        errors.put(field, message);
    }
}
