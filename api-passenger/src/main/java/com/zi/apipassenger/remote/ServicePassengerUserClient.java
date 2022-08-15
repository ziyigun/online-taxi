package com.zi.apipassenger.remote;

import com.zi.internalcommon.dto.PassengerUser;
import com.zi.internalcommon.dto.ResponseResult;
import com.zi.internalcommon.request.VertificationCodeDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient("service-passenger-user")
public interface ServicePassengerUserClient {

    @PostMapping("/user")
    public ResponseResult loginOrRegister(@RequestBody VertificationCodeDTO vertificationCodeDTO);


    @GetMapping("/user/{phone}")
    public ResponseResult<PassengerUser> getUserByPhone(@PathVariable("phone") String passengerPhone);
}
