package com.zi.apipassenger.service;

import com.zi.apipassenger.remote.ServicePassengerUserClient;
import com.zi.apipassenger.remote.ServiceVertificationCodeClient;
import com.zi.internalcommon.constant.CommonStatusEnum;
import com.zi.internalcommon.constant.IdentityConstants;
import com.zi.internalcommon.constant.TokenConstants;
import com.zi.internalcommon.dto.ResponseResult;
import com.zi.internalcommon.request.VertificationCodeDTO;
import com.zi.internalcommon.response.NumberCodeResponse;
import com.zi.internalcommon.response.TokenResponse;
import com.zi.internalcommon.util.JwtUtils;
import com.zi.internalcommon.util.RedisPrefixUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;

@Service
public class VertificationCodeService {

    @Autowired
    private ServiceVertificationCodeClient serviceVertificationCodeClient;

    @Autowired
    private StringRedisTemplate stringRedisTemplate;


    /**
     * 生成验证码
     * @param passengerPhone
     * @return
     */
    public ResponseResult generateCode(String passengerPhone){
        //调用验证码服务，获取验证码
        ResponseResult<NumberCodeResponse> numberCodeResponse = serviceVertificationCodeClient.getNumberCode(6);

        int numberCode = numberCodeResponse.getData().getNumberCode();

        System.out.println("remote number code:" + numberCode);
        //存入redis
        System.out.println("存入redis");
        //key、value及过期时间
        String key = RedisPrefixUtils.generateKey(passengerPhone);
        //存入redis[过期时间两分钟]
        stringRedisTemplate.opsForValue().set(key, numberCode + "", 2, TimeUnit.MINUTES);


        return ResponseResult.success("");
    }




    @Autowired
    private ServicePassengerUserClient servicePassengerUserClient;
    /**
     * 校验验证码
     * @param passengerPhone
     * @param vertificationCode
     * @return
     */
    public ResponseResult checkCode(String passengerPhone, String vertificationCode){
        //根据手机号，去redis获取验证码
        System.out.println("根据手机号，去redis获取验证码");
        String key = RedisPrefixUtils.generateKey(passengerPhone);

        String codeRedis = stringRedisTemplate.opsForValue().get(key);
        System.out.println("redis中的验证码:" + codeRedis);

        //校验验证码
        if(StringUtils.isBlank(codeRedis)){
            return ResponseResult.fail(CommonStatusEnum.VERTIFICATION_CODE_ERROR.getCode(), CommonStatusEnum.VERTIFICATION_CODE_ERROR.getValue());
        }
        //用户输入验证码与redis中缓存的不一致【trim去掉前后空格】
        if(!vertificationCode.trim().equals(codeRedis.trim())){
            return ResponseResult.fail(CommonStatusEnum.VERTIFICATION_CODE_ERROR.getCode(), CommonStatusEnum.VERTIFICATION_CODE_ERROR.getValue());
        }


        //判断原来是否有用户，并进项相应的处理
        VertificationCodeDTO vertificationCodeDTO = new VertificationCodeDTO();
        vertificationCodeDTO.setPassengerPhone(passengerPhone);
        servicePassengerUserClient.loginOrRegister(vertificationCodeDTO);

        //颁发令牌
        System.out.println("颁发令牌");
        String accessToken = JwtUtils.generatorToken(passengerPhone, IdentityConstants.PASSENGER_IDENTITY, TokenConstants.ACCESS_TOKEN_TYPE);
        String refreshToken = JwtUtils.generatorToken(passengerPhone, IdentityConstants.PASSENGER_IDENTITY, TokenConstants.REFRESH_TOKEN_TYPE);

        //将accessToken存入redis
        String accessTokenKey = RedisPrefixUtils.generateToken(passengerPhone, IdentityConstants.PASSENGER_IDENTITY, TokenConstants.ACCESS_TOKEN_TYPE);
        stringRedisTemplate.opsForValue().set(accessTokenKey, accessToken, 30, TimeUnit.DAYS);

        //将refreshToken存入redis
        String refreshTokenKey = RedisPrefixUtils.generateToken(passengerPhone, IdentityConstants.PASSENGER_IDENTITY, TokenConstants.REFRESH_TOKEN_TYPE);
        stringRedisTemplate.opsForValue().set(refreshTokenKey, refreshToken, 31, TimeUnit.DAYS);


        TokenResponse tokenResponse = new TokenResponse();
        tokenResponse.setAccessToken(accessToken);
        tokenResponse.setRefreshToken(refreshToken);
        return ResponseResult.success(tokenResponse);
    }
}
