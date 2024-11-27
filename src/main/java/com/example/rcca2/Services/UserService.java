package com.example.rcca2.Services;



import com.example.rcca2.DTO.UserDetailsDTO;
import com.example.rcca2.Entities.User;
import com.example.rcca2.common.R;
import org.springframework.security.core.userdetails.UserDetailsService;

import java.util.List;


public interface UserService extends UserDetailsService {

    public R login(User user);

    public R logout();

    public void save(User admin);

    public User findById(Long id);


    public UserDetailsDTO findDTOById(Long id);

    public User findByName(String name);

    public String findRoleByName(String name);

    public List<User> search (String type, String keyword);


    public void delete(Long id);

    public Boolean ifExist(Long id);

    public List<User> pagination(Integer pageNum,Integer pageSize);

}
