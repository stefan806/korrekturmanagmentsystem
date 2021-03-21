package com.iubh.isef.korrekturmanagementsystem.service;

import com.iubh.isef.korrekturmanagementsystem.entity.User;
import com.iubh.isef.korrekturmanagementsystem.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class LoggedInUserService {

    @Autowired
    private UserRepository userRepository;

    private String loggedInEmail;

    public void setLoggedInUsername(String email) {
        loggedInEmail = email;
    }

    public User getLoggedInUser() {
        if (loggedInEmail == null) {
            return null;
        }
        return userRepository.findByEmail(loggedInEmail);
    }
}
