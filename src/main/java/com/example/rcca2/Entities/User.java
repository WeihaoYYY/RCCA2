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
@Table(name = "user")
@JsonIgnoreProperties(value = {"hibernateLazyInitializer"})
public class User{

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long aid = 0L;

    private String name;

    private String password;

    private String security_question;

    private String answer;

    private int role = 0;

//    @OneToOne(cascade = CascadeType.ALL)  //fetch = FetchType.LAZY
//    private Administrator created_by;


}
