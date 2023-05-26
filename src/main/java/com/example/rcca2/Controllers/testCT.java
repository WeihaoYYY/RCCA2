package com.example.rcca2.Controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class testCT {

    @RequestMapping("/b")
    public String ct() {
        return "hello";
    }
}
