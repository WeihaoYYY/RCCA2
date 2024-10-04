package com.example.rcca2.Services.Impl;

import com.example.rcca2.DTO.UserDetailsDTO;
import com.example.rcca2.Entities.User;
import com.example.rcca2.Entities.User;
import com.example.rcca2.Repository.UserRepository;
import com.example.rcca2.Services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class UserServiceImpl implements UserService, UserDetailsService {

    @Autowired
    private UserRepository userRepo;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User userEntity = userRepo.findByName(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found: " + username));  //TODO 全局异常

        return org.springframework.security.core.userdetails.User.builder()
                .username(userEntity.getName())
                .password(userEntity.getPassword()) // 假设数据库中已保存加密后的密码
                .roles(userEntity.getRole()) // 用户的角色，如 "USER" 或 "ADMIN"
                .build();
    }


    public void save(User admin) {
        userRepo.save(admin);
    }

    public User findById(Long id) {
        return userRepo.findById(id).orElse(null);
    }

    @Override
    public UserDetailsDTO findDTOById(Long id) {
        Optional<User> user = userRepo.findById(id);
        if (user.isPresent()) {
            UserDetailsDTO userDetailsDTO = new UserDetailsDTO();
            userDetailsDTO.setUid(user.get().getUid());
            userDetailsDTO.setName(user.get().getName());
            userDetailsDTO.setEmail(user.get().getEmail());
            userDetailsDTO.setRole(user.get().getRole());
            userDetailsDTO.setEnabled(user.get().isEnabled());
            userDetailsDTO.setAvatarUrl(user.get().getAvatarUrl());
            return userDetailsDTO;
        }

        return null;
    }

    public User findByName(String name) {
        return userRepo.findByName(name)
                //.orElse(new User()); // 返回一个默认的 User 实例
                .orElseThrow(() -> new UsernameNotFoundException("User not found: " + name));
    }


    public String findRoleByName(String name) {
        return userRepo.findRoleByName(name);
    }

    public List<User> search (String type, String keyword) {
        //return userRepo.search(type, keyword);
        return null;
    }


    public void delete(Long id) {
        userRepo.deleteById(id);
    }

    public Boolean ifExist(Long id){
        return findById(id) != null;
    }

    public List<User> pagination(Integer pageNum,Integer pageSize) {

        PageRequest pageRequest = PageRequest.of(pageNum, pageSize);

        Page<User> admin = userRepo.findAll(pageRequest);

        return admin.getContent();
    }

}