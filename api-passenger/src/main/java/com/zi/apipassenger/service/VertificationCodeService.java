package com.zi.apipassenger.service;

import com.zi.apipassenger.remote.ServiceVertificationCodeClient;
import com.zi.internalcommon.dto.ResponseResult;
import com.zi.internalcommon.response.NumberCodeResponse;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;

@Service
public class VertificationCodeService {

    @Autowired
    private ServiceVertificationCodeClient serviceVertificationCodeClient;

    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    //乘客验证码前缀
    private String vertificationCodePrefix = "passenger-vertification-code-";

    public ResponseResult generateCode(String passengerPhone){
        //调用验证码服务，获取验证码
        System.out.println("调用验证码服务，获取验证码");
        ResponseResult<NumberCodeResponse> numberCodeResponse = serviceVertificationCodeClient.getNumberCode(6);

        int numberCode = numberCodeResponse.getData().getNumberCode();

        System.out.println("remote number code:" + numberCode);
        //存入redis
        System.out.println("存入redis");
        //key、value及过期时间
        String key = vertificationCodePrefix + passengerPhone;
        //存入redis[过期时间两分钟]
        stringRedisTemplate.opsForValue().set(key, numberCode + "", 2, TimeUnit.MINUTES);


        return ResponseResult.success("");
    }
}
