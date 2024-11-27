package com.example.rcca2.Services.Impl;

import com.example.rcca2.DTO.UserDetailsDTO;
import com.example.rcca2.Entities.LoginUser;
import com.example.rcca2.Entities.User;
import com.example.rcca2.Entities.User;
import com.example.rcca2.Repository.UserRepository;
import com.example.rcca2.Services.UserService;
import com.example.rcca2.Utils.JwtUtil;
import com.example.rcca2.Utils.RedisCache;
import com.example.rcca2.common.R;
import lombok.extern.java.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.logging.Logger;

@Service
@Transactional
public class UserServiceImpl implements UserService, UserDetailsService {

    @Autowired
    private UserRepository userRepo;

    @Autowired
    private AuthenticationManager manager;

    @Autowired
    private RedisCache redisCache;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        User user = userRepo.getByName(username)
                .orElse(null);
        if (user == null) {
            throw new UsernameNotFoundException("UserServiceImpl - User not found: " + username);
        }

        System.out.println(user);
        //如果查询不到数据就通过抛出异常来给出提示
        if(Objects.isNull(user)){
            throw new RuntimeException("用户名或者密码错误123 - UserServiceImpl.loadUserByUsername");
        }

        //TODO 根据用户查询权限信息 添加到LoginUser中


        //如果找到对应用户，封装成UserDetails对象返回
        return new LoginUser(user);
    }


    @Override
    public R login(User user) {
        //1. AuthenticationManager
        // 这个UserNamePasswordAuthenticationToken是集成了AbstractAuthenticationToken的AuthenticationManager的实现类
        // 用把用户名和密码封装成 UsernamePasswordAuthenticationToken对象
        UsernamePasswordAuthenticationToken token =
                new UsernamePasswordAuthenticationToken(user.getName(), user.getPassword());

        // 这个manager.authenticate(token)会调用UserDetailsServiceImpl的loadUserByUsername方法
        Authentication authenticate = manager.authenticate(token);

        //1.1 如果认证没有通过，返回错误信息
        if(Objects.isNull(authenticate)){
            //@ControllerAdvice全局捕获的注解是捕获springmvc层的，这里是过滤层，还没到controller层就已经被过滤器拦截了，所以直接抛异常
            throw new RuntimeException("User Not Found - UserServiceImpl.login");
        }

        LoginUser loginUser = (LoginUser) authenticate.getPrincipal();

        //1.2 如果认证通过，用userId生成jwt，并用ResponseResult返回
        String id = loginUser.getUser().getUid().toString();
        String jwt = JwtUtil.createJWT(id);

        Map<String, String > map = new HashMap<>();
        map.put("token", jwt);


        //2. 把完整的用户信息存入redis中，userId作为key
        redisCache.setCacheObject("login:"+id, loginUser);

        return R.ok("Login In Successful - UserServiceImpl", map);

    }

    @Override
    public R logout() {
        //1. 从SecurityContextHolder中获取用户信息
        UsernamePasswordAuthenticationToken token =
                (UsernamePasswordAuthenticationToken) SecurityContextHolder.getContext().getAuthentication();
        LoginUser user = (LoginUser) token.getPrincipal();

        Long id = user.getUser().getUid();

        //2. 删除redis中的用户信息
        redisCache.deleteObject("login:" + id);

        return R.ok("Logout Successful");
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
            userDetailsDTO.setEnabled(user.get().isEnabled());
            userDetailsDTO.setAvatarUrl(user.get().getAvatarUrl());
            userDetailsDTO.setRoles(user.get().getRoles());
            return userDetailsDTO;
        }

        return null;
    }

    public User findByName(String name) {
        return userRepo.getByName(name)
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