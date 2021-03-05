package com.iubh.isef.korrekturmanagementsystem.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.context.annotation.Bean;

import javax.annotation.ManagedBean;
import javax.persistence.*;


@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
@Table(name="user")
public class User {

    @Id
    @Column(name = "email")
    private String email;

    @Column(name = "vorname")
    private String vorname;

    @Column(name = "nachname")
    private String nachname;

    @Column(name = "funktion")
    private String funktion;

    @Column(name = "user_password")
    private String password;

    @ManyToOne
    @JoinColumn(name = "roleid", referencedColumnName = "roleid")
    private Role role;
}
