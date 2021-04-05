package com.iubh.isef.korrekturmanagementsystem.view;

import com.iubh.isef.korrekturmanagementsystem.entity.Rolle;
import com.iubh.isef.korrekturmanagementsystem.entity.User;
import com.iubh.isef.korrekturmanagementsystem.repository.RoleRepository;
import com.iubh.isef.korrekturmanagementsystem.repository.UserRepository;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.stereotype.Component;

import javax.enterprise.context.ApplicationScoped;
import javax.faces.bean.ManagedBean;
import javax.inject.Named;
import javax.transaction.Transactional;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@ApplicationScoped
@Component
@NoArgsConstructor
@Named
@ManagedBean
@Configurable
@ComponentScan
public class UserBean {

    private static final String DEFAULT_PASSWORD = "passwort";
    private UserRepository userRepository;

    private RoleRepository roleRepository;

    private boolean createMode = false;

    private User userToCreate = new User();

    private String userToCreateRole;

    /**
     * Constructor for UserBean
     *
     * @param userRepository UserRepository
     * @param roleRepository RoleRepository
     */
    @Autowired
    public UserBean(UserRepository userRepository, RoleRepository roleRepository) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
    }

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

    /**
     * Adds new user with given roles and sets default password.
     *
     * @return user User
     */
    public User addUser() {
        Rolle rolle = roleRepository.findByBezeichnung(userToCreateRole);
        userToCreate.setRolle(rolle);
        if (userToCreate.getPassword() == null || userToCreate.getPassword().equals("")) {
            userToCreate.setPassword(DEFAULT_PASSWORD);
        }
        createMode = false;
        return userRepository.save(userToCreate);
    }
}


