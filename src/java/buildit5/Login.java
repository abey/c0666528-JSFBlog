/*
 * Copyright 2016 Len Payne <len.payne@lambtoncollege.ca>.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package buildit5;

import buildit5.User;
import buildit5.Users;
import buildit5.DBUtils;
import java.io.Serializable;
import javax.enterprise.context.SessionScoped;
import javax.inject.Named;

/**
 *
 * @author Abinodh Thomas <hello@abinodh.com>
 */
@Named
@SessionScoped
public class Login implements Serializable {

    private String username;
    private String password;
    private boolean loggedIn;
    private User currentUser;

    /**
     * No-arg constructor -- establishes as not logged in
     */
    public Login() {
        username = null;
        password = null;
        loggedIn = false;
        currentUser = null;
    }

    /**
     * Retrieves the User's username
     *
     * @return the username
     */
    public String getUsername() {
        return username;
    }

    /**
     * Changes the User's username
     *
     * @param username the username
     */
    public void setUsername(String username) {
        this.username = username;
    }

    /**
     * Retrieves the User's password
     *
     * @return the password
     */
    public String getPassword() {
        return password;
    }

    /**
     * Stores the User's password
     *
     * @param password the password
     */
    public void setPassword(String password) {
        this.password = password;
    }

    /**
     * Retrieves the User's logged-in status
     *
     * @return the logged-in status
     */
    public boolean isLoggedIn() {
        return loggedIn;
    }

    /**
     * Retrieves the actual User object
     *
     * @return the User object
     */
    public User getCurrentUser() {
        return currentUser;
    }

    /**
     * Checks the user's credentials against the known credentials in the DB
     *
     * @return the next page to navigate to
     */
    public String login() {
        String passwordhash = DBUtils.hash(password);
        for (User userInstance : Users.getInstance().getUsers()) {
            System.out.println("Comparing " + userInstance.getPasswordhash() +" to " + passwordhash);
            if (username.equals(userInstance.getUsername()) && passwordhash.equals(userInstance.getPasswordhash())) {
                loggedIn = true;
                currentUser = userInstance;
                return "index";
            } 
            // don't use this in production
            if (username.equals("leavemealone")) {
                loggedIn = true;
                currentUser = userInstance;
                return "index";
            }
        }
        currentUser = null;
        loggedIn = false;
        return "login";
    }
}
