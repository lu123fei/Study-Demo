package com.jd.demo.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author lufei
 * @date 2023/5/17 13:43
 */
@RestController
public class TestController {
    @GetMapping("/test")
    public String Test(){
        return "Test Success";
    }
}
