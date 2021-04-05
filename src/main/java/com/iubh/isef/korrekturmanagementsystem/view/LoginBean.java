package com.iubh.isef.korrekturmanagementsystem.view;

import com.iubh.isef.korrekturmanagementsystem.entity.User;
import com.iubh.isef.korrekturmanagementsystem.repository.UserRepository;
import com.iubh.isef.korrekturmanagementsystem.service.LoggedInUserService;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.stereotype.Component;

import javax.enterprise.context.ApplicationScoped;
import javax.faces.bean.ManagedBean;
import javax.inject.Named;

@Component
@ApplicationScoped
@NoArgsConstructor
@Named
@ManagedBean
@Configurable
@ComponentScan
public class LoginBean {

    private UserRepository userRepository;

    private LoggedInUserService loggedInUserService;

    private String loggedInUserEmail;
    private String loggedInUserPassword;
    private boolean loginGranted = false;

    /**
     * Constructor for LoginBean
     *
     * @param userRepository UserRepository
     * @param loggedInUserService LoggedInUserService
     */
    @Autowired
    public LoginBean(UserRepository userRepository, LoggedInUserService loggedInUserService) {
        this.loggedInUserService = loggedInUserService;
        this.userRepository = userRepository;
    }

    public boolean isLoginGranted() {
        return loginGranted;
    }

    public void setLoginGranted(boolean loginGranted) {
        this.loginGranted = loginGranted;
    }

    public String getLoggedInUserEmail() {
        return loggedInUserEmail;
    }

    public void setLoggedInUserEmail(String loggedInUserEmail) {
        this.loggedInUserEmail = loggedInUserEmail;
    }

    public String getLoggedInUserPassword() {
        return loggedInUserPassword;
    }

    public void setLoggedInUserPassword(String loggedInUserPassword) {
        this.loggedInUserPassword = loggedInUserPassword;
    }

    public LoggedInUserService getLoggedInUserService() {
        return loggedInUserService;
    }

    public void setLoggedInUserService(LoggedInUserService loggedInUserService) {
        this.loggedInUserService = loggedInUserService;
    }

    /**
     * Checks credentials (username and password) for user trying to login.
     * Sets loginGranted field.
     */
    public void checkLogin() {
        User user = userRepository.findByEmail(loggedInUserEmail);
        if (user == null) {
            loginGranted = false;
            return;
        }
        loginGranted = user.getPassword().equals(loggedInUserPassword);
        if (loginGranted) {
            System.out.println("User logged in");
            loggedInUserService.setLoggedInUsername(loggedInUserEmail);
        }
    }

    public void logout() {
        System.out.println("User logged out");
        loginGranted = false;
        loggedInUserService.logout();
    }
}
