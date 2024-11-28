package com.example.rcca2;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.beans.factory.annotation.Value;

import javax.annotation.PostConstruct;


@SpringBootApplication
@Slf4j
@EnableJpaRepositories
@EnableTransactionManagement
@RequestMapping("/api")
public class Rcca2Application {

    @Value("${spring.redis.host}")
    private String redisHost;

    public static void main(String[] args) {
        SpringApplication.run(Rcca2Application.class, args);
        System.out.println("RCCA2 application started");
    }


    @PostConstruct
    public void postConstruct() {
        log.info("++++++Redis host: " + redisHost + " ++++++");
    }

}
