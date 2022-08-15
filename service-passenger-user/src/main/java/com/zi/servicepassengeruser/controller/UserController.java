package com.zi.servicepassengeruser.controller;

import com.zi.internalcommon.dto.ResponseResult;
import com.zi.internalcommon.request.VertificationCodeDTO;
import com.zi.servicepassengeruser.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@RestController
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping("/user")
    public ResponseResult loginOrRegister(@RequestBody VertificationCodeDTO vertificationCodeDTO){
        String passengerPhone = vertificationCodeDTO.getPassengerPhone();

        System.out.println("手机号：" + passengerPhone);
        return userService.loginOrRegister(passengerPhone);
    }

    @GetMapping("/user/{phone}")
    public ResponseResult getUser(@PathVariable("phone") String passengerPhone){
        System.out.println("service-passenger-user:phone :" + passengerPhone);
        return userService.getUserByPhone(passengerPhone);
    }
}
