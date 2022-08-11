package com.zi.apipassenger.controller;

import com.zi.apipassenger.request.VertificationCodeDTO;
import com.zi.apipassenger.service.VertificationCodeService;
import com.zi.internalcommon.dto.ResponseResult;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class VertificationCodeController {

    @Autowired
    private VertificationCodeService vertificationCodeService;

    @GetMapping("/vertification-code")
    public ResponseResult vertificationCode(@RequestBody VertificationCodeDTO vertificationCodeDTO){
        String passengerPhone = vertificationCodeDTO.getPassengerPhone();
        System.out.println("接收到的手机号参数：" + passengerPhone);
        return vertificationCodeService.generateCode(passengerPhone);
    }
}
