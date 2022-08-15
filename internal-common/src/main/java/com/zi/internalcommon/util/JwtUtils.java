package com.zi.internalcommon.util;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTCreator;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.Claim;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.zi.internalcommon.dto.TokenResult;
import com.zi.internalcommon.response.TokenResponse;
import jdk.nashorn.internal.parser.Token;

import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class JwtUtils {

    private static final String SIGN = "fao%208*@#)*";

    private static final String JWT_KEY_PHONE = "phone";

    //乘客 1      司机 2
    private static final String JWT_IDENTITY_KEY = "identity";

    private static final String TOKEN_TYPE = "tokenType";

    private static final String JWT_TOKEN_TIME = "tokenTime";

    //生成token
    public static String generatorToken(String phone, String identity, String tokenType){
        Map<String, String> map = new HashMap<>();
        map.put(JWT_KEY_PHONE, phone);
        map.put(JWT_IDENTITY_KEY, identity);
        map.put(TOKEN_TYPE, tokenType);

        //为了使得每次生成的token不一样
        map.put(JWT_TOKEN_TIME, Calendar.getInstance().getTime().toString());

        JWTCreator.Builder builder = JWT.create();
        //整合map
        map.forEach((K, V) -> {
            builder.withClaim(K, V);
        });

        //整合过期时间
//        builder.withExpiresAt(date);

        //生成token
        String sign = builder.sign(Algorithm.HMAC256(SIGN));

        return sign;
    }

    //解析token
    public static TokenResult parseToken(String token){
        DecodedJWT verify = JWT.require(Algorithm.HMAC256(SIGN)).build().verify(token);
        String phone = verify.getClaim("phone").asString();//注意：此处是asString,使用toString会多引号
        String identity = verify.getClaim("identity").asString();
        TokenResult tokenResult = new TokenResult();
        tokenResult.setPhone(phone);
        tokenResult.setIdentity(identity);
        return tokenResult;
    }


    /**
     * 校验token，主要判断token是否异常
     * @param token
     * @return
     */
    public static TokenResult checkToken(String token){
        TokenResult tokenResult = null;
        try{
            tokenResult = JwtUtils.parseToken(token);
        } catch (Exception e){

        }
        return tokenResult;
    }

    //测试
    public static void main(String[] args) {
        String s = generatorToken("17372804728", "1", "accessToken");
        System.out.println("生成的token：" + s);

        TokenResult tokenResult = parseToken(s);
        System.out.println("解析后的phone：" + tokenResult.getPhone());
        System.out.println("解析后的identity：" + tokenResult.getIdentity());

    }
}
