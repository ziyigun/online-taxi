package com.zi.servicevertificationcode.controller;

import com.zi.internalcommon.dto.ResponseResult;
import com.zi.internalcommon.response.NumberCodeResponse;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class NumberCodeController {

    @GetMapping("/numberCode/{size}")
    public ResponseResult numberCode(@PathVariable("size") int size){
//        System.out.println("size= " + size);


        //随机生成验证码
        int numCode = (int)((Math.random() * 9 + 1) * Math.pow(10.0, size - 1));

        System.out.println("generator src code :" + numCode);
        //定义返回值
        NumberCodeResponse response = new NumberCodeResponse();
        response.setNumberCode(numCode);

        return ResponseResult.success(response);
    }
}
