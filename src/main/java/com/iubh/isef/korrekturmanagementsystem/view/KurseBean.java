package com.iubh.isef.korrekturmanagementsystem.view;

import com.iubh.isef.korrekturmanagementsystem.entity.Kurs;
import com.iubh.isef.korrekturmanagementsystem.entity.Rolle;
import com.iubh.isef.korrekturmanagementsystem.entity.User;
import com.iubh.isef.korrekturmanagementsystem.repository.KurseRepository;
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
public class KurseBean {

    @Autowired
    public KurseRepository kurseRepository;

    @Autowired
    public UserRepository userRepository;

    private boolean createMode = false;

    private Kurs kursToCreate = new Kurs();

    public boolean isCreateMode() {
        return createMode;
    }

    Set<User> bearbeiter = new HashSet<>();

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
