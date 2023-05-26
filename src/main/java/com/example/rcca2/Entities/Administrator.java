package com.example.rcca2.Entities;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;


@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "administrators")
@JsonIgnoreProperties(value = {"hibernateLazyInitializer"})
public class Administrator{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long aid = 0L;

    private String name;

    private String password;

    private String security_question;

    private String answer;

    private String role = "ROLE_ADMIN";

    @OneToOne(cascade = CascadeType.ALL)  //fetch = FetchType.LAZY
    private Administrator created_by;

    public Administrator(String name, String password, String security_question, String answer, Administrator created_by) {
        this.name = name;
        this.password = password;
        this.security_question = security_question;
        this.answer = answer;
        this.created_by = created_by;
        this.role = "ADMIN";
    }

}
