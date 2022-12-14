package com.zi.servicepassengeruser.service;

import com.zi.internalcommon.constant.CommonStatusEnum;
import com.zi.internalcommon.dto.PassengerUser;
import com.zi.internalcommon.dto.ResponseResult;
import com.zi.servicepassengeruser.mapper.PassengerUserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class UserService {

    @Autowired
    private PassengerUserMapper passengerUserMapper;

    public ResponseResult loginOrRegister(String passengerPhone){
        System.out.println("user service 被调用， 手机号：" + passengerPhone);
        //根据手机号查询用户信息
        Map<String, Object> map = new HashMap<>();
        map.put("passenger_phone", passengerPhone);
        List<PassengerUser> passengerUsers = passengerUserMapper.selectByMap(map);
        System.out.println(passengerUsers.size() == 0 ? "无记录" : (passengerUsers.get(0).getPassengerName() + "," + passengerUsers.get(0).getPassengerPhone()));

        //判断用户信息是否存在
        if(passengerUsers.size() == 0){
            //如果不存在，插入用户信息
            PassengerUser passengerUser = new PassengerUser();
            LocalDateTime now = LocalDateTime.now();
            passengerUser.setGmtCreate(now);
            passengerUser.setGmtModified(now);
            passengerUser.setPassengerName("张三");
            passengerUser.setPassengerPhone(passengerPhone);
            passengerUser.setPassengerGender((byte)0);
            passengerUser.setState((byte)0);

            passengerUserMapper.insert(passengerUser);
        }


        return ResponseResult.success();
    }


    public ResponseResult getUserByPhone(String passengerPhone){
        //根据手机号查询用户信息
        Map<String, Object> map = new HashMap<>();
        map.put("passenger_phone", passengerPhone);
        List<PassengerUser> passengerUsers = passengerUserMapper.selectByMap(map);
        if(passengerUsers.size() == 0){
            return ResponseResult.fail(CommonStatusEnum.USER_NOT_EXIST.getCode(), CommonStatusEnum.USER_NOT_EXIST.getValue());
        } else {
            PassengerUser passengerUser = passengerUsers.get(0);
            return ResponseResult.success(passengerUser);
        }
    }
}
