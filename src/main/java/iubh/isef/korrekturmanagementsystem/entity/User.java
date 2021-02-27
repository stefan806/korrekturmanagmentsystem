package iubh.isef.korrekturmanagementsystem.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;


@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
@Table(name="user")
public class User {

    @Id
    @Column(name = "userid")
    @GeneratedValue(strategy= GenerationType.AUTO)
    private Long userid;

    @Column(name = "username")
    private String username;
}
