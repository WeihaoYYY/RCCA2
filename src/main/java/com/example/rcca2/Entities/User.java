package com.example.rcca2.Entities;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Data
@NoArgsConstructor
@Table(name = "user")
@JsonIgnoreProperties(value = {"hibernateLazyInitializer"})
public class User{

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long uid = 0L;

    private String Name;

    private String password;

    private String email;

    private String avatarUrl;

    private String security_question;

    private String answer;

    private String role = "USER";

    private boolean enabled = true;

    public User(Long uid, String name, String password, String email, String avatarUrl, String security_question, String answer) {
        this.uid = uid;
        this.Name = name;
        this.password = password;
        this.email = email;
        this.avatarUrl = avatarUrl;
        this.security_question = security_question;
        this.answer = answer;
    }

    //    @OneToOne(cascade = CascadeType.ALL)  //fetch = FetchType.LAZY
//    private Administrator created_by;


}
