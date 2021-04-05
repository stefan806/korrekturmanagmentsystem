package com.iubh.isef.korrekturmanagementsystem.view;

import com.iubh.isef.korrekturmanagementsystem.entity.Kurs;
import com.iubh.isef.korrekturmanagementsystem.entity.Rolle;
import com.iubh.isef.korrekturmanagementsystem.entity.User;
import com.iubh.isef.korrekturmanagementsystem.repository.KurseRepository;
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
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Component
@ApplicationScoped
@NoArgsConstructor
@Named
@ManagedBean
@Configurable
@ComponentScan
public class KurseBean {

    private KurseRepository kurseRepository;

    private UserRepository userRepository;

    private boolean createMode = false;

    private Kurs kursToCreate = new Kurs();

    Set<User> bearbeiter = new HashSet<>();

    /**
     * Constructor for KurseBean
     *
     * @param kurseRepository KurseRepository
     * @param userRepository UserRepository
     */
    @Autowired
    public KurseBean(KurseRepository kurseRepository, UserRepository userRepository){
        this.kurseRepository = kurseRepository;
        this.userRepository = userRepository;
    }

    public boolean isCreateMode() {
        return createMode;
    }

    public Set<User> getBearbeiter() {
        return bearbeiter;
    }

    public void setBearbeiter(Set<User> bearbeiter) {
        this.bearbeiter = bearbeiter;
    }

    public void setCreateMode(boolean createMode) {
        kursToCreate = new Kurs();
        this.createMode = createMode;
    }

    public Kurs getKursToCreate() {
        return kursToCreate;
    }

    public void setKursToCreate(Kurs kursToCreate) {
        this.kursToCreate = kursToCreate;
    }

    @Transactional
    public List<Kurs> getAllKurse() {
        List<Kurs> kurse = StreamSupport.stream(this.kurseRepository.findAll().spliterator(), false).collect(Collectors.toList());
        return kurse;
    }

    @Transactional
    public void editKurs(Kurs kurs) {
        setCreateMode(true);
        kursToCreate = kurs;
    }

    public void deleteKurs(Kurs kurs) {
        this.kurseRepository.delete(kurs);
    }

    @Transactional
    public void addKurs() {
        createMode = false;
        kursToCreate.setUser(bearbeiter);
        this.kurseRepository.save(kursToCreate);
        this.kursToCreate = new Kurs();
    }

    public List<User> getAllAdminsAndBearbeiter() {
        List<Rolle> roles = Arrays.asList(new Rolle(2, "Bearbeiter"), new Rolle(3, "Administrator"));
        return userRepository.findAllByRolleIsIn(roles);
    }

}
