package com.example.rcca2.config;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;


@EnableWebSecurity
@Configuration
@EnableGlobalMethodSecurity(prePostEnabled = true, securedEnabled = true, jsr250Enabled = true)
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    //private AdminService loginService;

    //定义用户信息服务（查询数据库用户信息）
//    @Bean
//    public UserDetailsService userDetailsService() {
//        return username -> {
//            Administrator users = loginService.findByName(username);
//            if (users == null) {
//                throw new UsernameNotFoundException("Account Not Found");
//            }
//            String password = users.getPassword();
//            PasswordEncoder passwordEncoder = PasswordEncoderFactories.createDelegatingPasswordEncoder();
//            String passwordAfterEncoder = passwordEncoder.encode(password);
//            //TODO 待测试role
//            return User.withUsername(username).password(passwordAfterEncoder).roles(loginService.findRoleByName(username)).build();
//        };
//    }

    @Bean
    public UserDetailsService userDetailsService() {
        PasswordEncoder passwordEncoder = PasswordEncoderFactories.createDelegatingPasswordEncoder();
        InMemoryUserDetailsManager manager = new InMemoryUserDetailsManager();
        manager.createUser(User.withUsername("user")
                .password(passwordEncoder.encode("user"))
                .roles("USER")
                .build());
        manager.createUser(User.withUsername("admin")
                .password(passwordEncoder.encode("admin"))
                .roles("ADMIN")
                .build());
        return manager;
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests()
                .antMatchers("/admin/**").hasRole("ADMIN")
                .anyRequest().permitAll()//.anyRequest().permitAll()//除了/r/**，其它的请求可以访问
                //.antMatchers("/**").authenticated()//所有/r/**的请求必须认证通过
                .and().csrf().disable()
                .formLogin();//允许表单登录
                //.successForwardUrl("/");//自定义登录成功的页面地址
    }



//    @Bean  //此方法适用于Spring3以后
//    public SecurityFilterChain apiFilterChain(HttpSecurity http) throws Exception {
//        http.authorizeHttpRequests()
//                // 用户具有system:user权限时允许访问/role
//                .requestMatchers("/role").hasRole("ADMIN")
//                .anyRequest().permitAll()
//                .and()
//                .formLogin()
//                .and()
//                .csrf().disable();
//        return http.build();
//    }



}
