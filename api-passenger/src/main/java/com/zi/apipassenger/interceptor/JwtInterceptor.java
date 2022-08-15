package com.zi.apipassenger.interceptor;

import com.auth0.jwt.exceptions.AlgorithmMismatchException;
import com.auth0.jwt.exceptions.SignatureVerificationException;
import com.auth0.jwt.exceptions.TokenExpiredException;
import com.zi.internalcommon.constant.TokenConstants;
import com.zi.internalcommon.dto.ResponseResult;
import com.zi.internalcommon.dto.TokenResult;
import com.zi.internalcommon.util.JwtUtils;
import com.zi.internalcommon.util.RedisPrefixUtils;
import io.netty.util.internal.StringUtil;
import net.sf.json.JSONObject;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.PrintWriter;
import java.security.SignatureException;

public class JwtInterceptor implements HandlerInterceptor {

    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        boolean result = true;
        String resultString = "";

        String token = request.getHeader("Authorization");
        TokenResult tokenResult = null;
        try{
            tokenResult = JwtUtils.parseToken(token);
        } catch (SignatureVerificationException e){
            resultString = "token sign error";
            result = false;
        } catch (TokenExpiredException e){
            //过期
            resultString = "token time out";
            result = false;
        } catch (AlgorithmMismatchException e){
            resultString = "token AlgorithmMismatchException";
            result = false;
        } catch (Exception e){
            resultString = "token invalid";
        }

        if(tokenResult == null){
            resultString = "token invalid";
            result = false;
        } else {
            //拼接key
            String phone = tokenResult.getPhone();
            String identity = tokenResult.getIdentity();
            String tokenKey = RedisPrefixUtils.generateToken(phone, identity, TokenConstants.ACCESS_TOKEN_TYPE);
            //从redis中获取token
            String tokenRedis = stringRedisTemplate.opsForValue().get(tokenKey);
            if(StringUtils.isBlank(tokenRedis)){
                resultString = "token invalid";
                result = false;
            } else {
                if(!token.trim().equals(tokenRedis.trim())){
                    resultString = "token valid";
                    result = false;
                }
            }
        }


        //如果token错误
        if(!result){
            PrintWriter out = response.getWriter();
            out.print(JSONObject.fromObject(ResponseResult.fail(resultString)).toString());
        }
        return result;
    }
}
