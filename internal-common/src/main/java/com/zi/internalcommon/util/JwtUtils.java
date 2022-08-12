package com.zi.internalcommon.util;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTCreator;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.Claim;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.zi.internalcommon.dto.TokenResult;
import jdk.nashorn.internal.parser.Token;

import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class JwtUtils {

    private static final String SIGN = "fao%208*@#)*";

    private static final String JWT_KEY_PHONE = "phone";

    private static final String JWT_IDENTITY_KEY = "identity";

    //生成token
    public static String generatorToken(String phone, String identity){
        Map<String, String> map = new HashMap<>();
        map.put(JWT_KEY_PHONE, phone);
        map.put(JWT_IDENTITY_KEY, identity);
        //token的过期时间
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.DATE, 1);
        Date date = calendar.getTime();

        JWTCreator.Builder builder = JWT.create();
        //整合map
        map.forEach((K, V) -> {
            builder.withClaim(K, V);
        });

        //整合过期时间
        builder.withExpiresAt(date);

        //生成token
        String sign = builder.sign(Algorithm.HMAC256(SIGN));

        return sign;
    }

    //解析token
    public static TokenResult parseToken(String token){
        DecodedJWT verify = JWT.require(Algorithm.HMAC256(SIGN)).build().verify(token);
        String phone = verify.getClaim("phone").toString();
        String identity = verify.getClaim("identity").toString();
        TokenResult tokenResult = new TokenResult();
        tokenResult.setPhone(phone);
        tokenResult.setIdentity(identity);
        return tokenResult;
    }

    //测试
    public static void main(String[] args) {
        String s = generatorToken("17372804728", "1");
        System.out.println("生成的token：" + s);

        TokenResult tokenResult = parseToken(s);
        System.out.println("解析后的phone：" + tokenResult.getPhone());
        System.out.println("解析后的identity：" + tokenResult.getIdentity());

    }
}
