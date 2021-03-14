package com.iubh.isef.korrekturmanagementsystem.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
@Table(name="role")
public class Rolle {

    @Id
    @Column(name = "roleid")
    private int roleid;

    @Column(name = "bezeichnung")
    private String bezeichnung;
}
