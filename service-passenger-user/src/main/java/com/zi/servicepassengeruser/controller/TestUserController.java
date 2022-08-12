package com.zi.servicepassengeruser.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TestUserController {

    @GetMapping("/test")
    public String test(){
        return "service-passenger-user api";
    }
}
