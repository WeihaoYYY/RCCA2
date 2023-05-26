package com.example.rcca2;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@SpringBootApplication
@Slf4j
@EnableJpaRepositories
@EnableTransactionManagement
public class Rcca2Application {

    public static void main(String[] args) {
        SpringApplication.run(Rcca2Application.class, args);
        System.out.println("RCCA2 application started");
    }

}
