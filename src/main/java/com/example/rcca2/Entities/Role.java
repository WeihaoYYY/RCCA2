package com.example.rcca2.Entities;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
@Table(name = "role")
public class Role {

    @Id
    private Long rid = 0L;

    private String name;

    @ManyToMany(mappedBy = "roles")
    private List<User> users;
}
