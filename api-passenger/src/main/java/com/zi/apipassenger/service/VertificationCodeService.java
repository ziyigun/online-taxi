package com.zi.apipassenger.service;

import com.zi.apipassenger.remote.ServiceVertificationCodeClient;
import com.zi.internalcommon.dto.ResponseResult;
import com.zi.internalcommon.response.NumberCodeResponse;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class VertificationCodeService {

    @Autowired
    private ServiceVertificationCodeClient serviceVertificationCodeClient;

    public String generateCode(String passengerPhone){
        //调用验证码服务，获取验证码
        System.out.println("调用验证码服务，获取验证码");
        ResponseResult<NumberCodeResponse> numberCodeResponse = serviceVertificationCodeClient.getNumberCode();

        int numberCode = numberCodeResponse.getData().getNumberCode();

        System.out.println("remote number code:" + numberCode);
        //存入redis
        System.out.println("存入redis");

        //返回值
        JSONObject result = new JSONObject();
        result.put("code", 1);
        result.put("message", "success");

        return result.toString();
    }
}
