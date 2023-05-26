package com.example.rcca2;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class ct {

    @RequestMapping("/ct")
    public String ct() {
        return "hello";
    }
}
