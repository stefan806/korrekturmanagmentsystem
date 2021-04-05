package com.iubh.isef.korrekturmanagementsystem.service;

import com.iubh.isef.korrekturmanagementsystem.entity.Rolle;
import com.iubh.isef.korrekturmanagementsystem.entity.User;
import com.iubh.isef.korrekturmanagementsystem.repository.UserRepository;
import org.junit.Before;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class LoggedInUserServiceTest {

    public UserRepository userRepository = mock(UserRepository.class);
    public LoggedInUserService loggedInUserService;

    @BeforeAll
    public void setUp(){
        loggedInUserService = new LoggedInUserService(userRepository);
    }

    @Test
    void isErsteller() {
        loggedInUserService.setLoggedInUsername("test@test.de");
        User user = new User();
        Rolle rolle = new Rolle(1, "Bearbeiter");
        user.setRolle(rolle);
        when(userRepository.findByEmail("test@test.de")).thenReturn(user);

        boolean bearbeiter = loggedInUserService.isBearbeiter();

        assertTrue(bearbeiter);
    }

    @Test
    void isBearbeiter() {
        loggedInUserService.setLoggedInUsername("test@test.de");
        User user = new User();
        Rolle rolle = new Rolle(1, "Ersteller");
        user.setRolle(rolle);
        when(userRepository.findByEmail("test@test.de")).thenReturn(user);

        boolean bearbeiter = loggedInUserService.isErsteller();

        assertTrue(bearbeiter);
    }
}