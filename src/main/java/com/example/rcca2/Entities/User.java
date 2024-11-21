package com.example.rcca2.Entities;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.*;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
@Table(name = "user")
@JsonIgnoreProperties(value = {"hibernateLazyInitializer"})
public class User{

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long uid = 0L;

    private String name;

    private String password;

    private String email;

    private String avatarUrl;

    private String security_question;

    private String answer;

    @ManyToMany(fetch = FetchType.EAGER) // 使用 EAGER 加载角色信息
    @JoinTable(
            name = "user_roles", // 中间表的名称
            joinColumns = @JoinColumn(name = "uid"), // User 对应的列
            inverseJoinColumns = @JoinColumn(name = "rid") // Role 对应的列
    )
    @ToString.Exclude // 避免在 toString() 中输出实体类
    private List<Role> roles;

    private boolean enabled = true;

    public User(Long uid, String name, String password, String email, String avatarUrl, String security_question, String answer) {
        this.uid = uid;
        this.name = name;
        this.password = password;
        this.email = email;
        this.avatarUrl = avatarUrl;
        this.security_question = security_question;
        this.answer = answer;
    }

    //    @OneToOne(cascade = CascadeType.ALL)  //fetch = FetchType.LAZY
//    private Administrator created_by;


}
