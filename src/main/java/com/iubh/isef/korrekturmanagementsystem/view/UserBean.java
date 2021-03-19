package com.iubh.isef.korrekturmanagementsystem.view;

import com.iubh.isef.korrekturmanagementsystem.entity.Rolle;
import com.iubh.isef.korrekturmanagementsystem.entity.User;
import com.iubh.isef.korrekturmanagementsystem.repository.RoleRepository;
import com.iubh.isef.korrekturmanagementsystem.repository.UserRepository;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import javax.annotation.ManagedBean;
import javax.transaction.Transactional;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Component
@Scope("view")
@ManagedBean
@NoArgsConstructor
public class UserBean {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;

    private boolean createMode = false;

    private User userToCreate = new User();

    private String userToCreateRole;

    public boolean isCreateMode() {
        return createMode;
    }

    public void setCreateMode(boolean createMode) {
        userToCreate = new User();
        this.createMode = createMode;
    }

    public User getUserToCreate() {
        return userToCreate;
    }

    public void setUserToCreate(User userToCreate) {
        this.userToCreate = userToCreate;
    }

    public String getUserToCreateRole() {
        return userToCreateRole;
    }

    public void setUserToCreateRole(String userToCreateRole) {
        this.userToCreateRole = userToCreateRole;
    }

    @Transactional
    public List<User> getAllUser() {
        List<User> users = StreamSupport.stream(userRepository.findAll().spliterator(), false).collect(Collectors.toList());
        users.sort(Comparator.comparing(User::getNachname));
        return users;
    }

    public void editUser(User user) {
        setCreateMode(true);
        userToCreate = userRepository.findByEmail(user.getEmail());
        userToCreateRole = userToCreate.getRolle().getBezeichnung();
    }

    public void deleteUser(User user) {
        userRepository.deleteById(user.getEmail());
    }

    public User addUser() {
        Rolle rolle = roleRepository.findByBezeichnung(userToCreateRole);
        userToCreate.setRolle(rolle);
        if (userToCreate.getPassword() == null || userToCreate.getPassword().equals("")) {
            userToCreate.setPassword("passwort");
        }
        createMode = false;
        return userRepository.save(userToCreate);
    }
}
