package com.lm.springbootstandardproject.controllers;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/test")
public class testController {

    @GetMapping("/test")
    public static void main(String[] args) {
        System.out.println("Hello World!");
    }
}