package com.iubh.isef.korrekturmanagementsystem.entity;

import com.iubh.isef.korrekturmanagementsystem.enumeration.Kategorie;
import com.iubh.isef.korrekturmanagementsystem.enumeration.Lehrmittel;
import com.iubh.isef.korrekturmanagementsystem.enumeration.Prioritaet;
import com.iubh.isef.korrekturmanagementsystem.enumeration.Status;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
@Table(name = "report")
public class Meldung {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private Long id;

    @Column(name = "lehrmittel")
    private Lehrmittel lehrmittel;

    @Column(name = "status")
    private Status status = Status.ANGELEGT;

    @Column(name = "kurzbeschreibung")
    private String kurzbeschreibung;

    @Column(name = "anlagedatum")
    private LocalDateTime anlagedatum = LocalDateTime.now();

    @Column(name = "beschreibung")
    private String beschreibung;

    @Column(name = "prioritaet")
    private Prioritaet prioritaet;

    @Column(name = "kategorie")
    private Kategorie kategorie;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "kursid", referencedColumnName = "kursid")
    private Kurs kurs;

    @OneToMany(fetch = FetchType.EAGER)
    @JoinColumn(name = "reportid", referencedColumnName = "id")
    private List<Kommentar> kommentare;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "creatorid", referencedColumnName = "email")
    private User creator;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "editorid", referencedColumnName = "email")
    private User editor;
}
