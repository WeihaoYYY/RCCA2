package com.example.rcca2.DTO;


import lombok.Data;

@Data
public class UserDetailsDTO {

    private Long uid;

    private String Name;

    private String email;

    private String role;

    private boolean enabled;

    private String avatarUrl;
}
