package com.iubh.isef.korrekturmanagementsystem.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
@Table(name = "comments")
public class Kommentar {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator="comments_id_seq")
    @SequenceGenerator(name="comments_id_seq", sequenceName="comments_id_seq", allocationSize=1)
    @Column(name = "id")
    private Long kommentarId;

    @Column(name = "inhalt")
    private String inhalt;
}
