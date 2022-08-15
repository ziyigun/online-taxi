package com.zi.apipassenger.service;

import com.zi.internalcommon.dto.PassengerUser;
import com.zi.internalcommon.dto.ResponseResult;
import com.zi.internalcommon.dto.TokenResult;
import com.zi.internalcommon.util.JwtUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class UserService {

    public ResponseResult getUserByAccessToken(String accessToken){
        log.info("accessToken:" + accessToken);
        //解析accessToken，拿到手机号
        TokenResult tokenResult = JwtUtils.checkToken(accessToken);
        String phone = tokenResult.getPhone();
        log.info("手机号:" + phone);
        //根据手机号查询用户信息
        PassengerUser passengerUser = new PassengerUser();
        passengerUser.setPassengerName("张三");
        passengerUser.setProfilePhoto("头像");
        return ResponseResult.success(passengerUser);
    }
}
