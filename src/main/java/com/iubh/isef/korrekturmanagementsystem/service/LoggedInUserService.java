package com.iubh.isef.korrekturmanagementsystem.service;

import com.iubh.isef.korrekturmanagementsystem.entity.User;
import com.iubh.isef.korrekturmanagementsystem.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Service;

@Service
@Configuration
@ComponentScan
public class LoggedInUserService {

    private UserRepository userRepository;

    private String loggedInEmail;

    /**
     * Constructor for LoggedInUserService
     *
     * @param userRepository UserRepository
     */
    @Autowired
    public LoggedInUserService(UserRepository userRepository){
        this.userRepository = userRepository;
    }

    public void setLoggedInUsername(String email) {
        loggedInEmail = email;
    }

    public User getLoggedInUser() {
        if (loggedInEmail == null) {
            return null;
        }
        return userRepository.findByEmail(loggedInEmail);
    }

    public String getLoggedInUserInfo() {
        User loggedInUser = getLoggedInUser();
        if (loggedInUser == null) return "";
        return loggedInUser.getVorname() + " "
                + loggedInUser.getNachname() + " ("
                + loggedInUser.getRolle().getBezeichnung() + ")";
    }

    /**
     * Checks if user has right to create reports
     *
     * @return true if user is Ersteller or Administrator
     */
    public boolean isErsteller() {
        return getLoggedInUser().getRolle().getBezeichnung().equals("Ersteller") ||
                getLoggedInUser().getRolle().getBezeichnung().equals("Administrator");
    }

    /**
     * Checks if user has right to edit reports
     *
     * @return true if user is Bearbeiter or Administrator
     */
    public boolean isBearbeiter() {
        return getLoggedInUser().getRolle().getBezeichnung().equals("Bearbeiter") ||
                getLoggedInUser().getRolle().getBezeichnung().equals("Administrator");
    }

    public void logout(){
        this.loggedInEmail = null;
    }
}
