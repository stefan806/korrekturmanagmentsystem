package com.iubh.isef.korrekturmanagementsystem.view;

import com.iubh.isef.korrekturmanagementsystem.entity.User;
import com.iubh.isef.korrekturmanagementsystem.repository.UserRepository;
import com.iubh.isef.korrekturmanagementsystem.service.LoggedInUserService;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import javax.annotation.ManagedBean;

@Component
@Scope("view")
@ManagedBean
@NoArgsConstructor
public class LoginBean {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private LoggedInUserService loggedInUserService;

    private String loggedInUserEmail;
    private String loggedInUserPassword;
    private boolean loginGranted = false;

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

    public void checkLogin() {
        User user = userRepository.findByEmail(loggedInUserEmail);
        if (user == null) {
            loginGranted = false;
            return;
        }
        loginGranted = user.getPassword().equals(loggedInUserPassword);
        if (loginGranted) {
            loggedInUserService.setLoggedInUsername(loggedInUserEmail);
        }
    }

    public void logout() {
        System.out.println("User logged out");
        loginGranted = false;
        loggedInUserService.logout();
    }
}
