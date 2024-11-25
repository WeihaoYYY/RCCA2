package com.example.rcca2.DTO;


import com.example.rcca2.Entities.Role;
import lombok.Data;

import java.util.List;

@Data
public class UserDetailsDTO {

    private Long uid;

    private String Name;

    private String email;

    private List<Role> roles;

    private boolean enabled;

    private String avatarUrl;
}
