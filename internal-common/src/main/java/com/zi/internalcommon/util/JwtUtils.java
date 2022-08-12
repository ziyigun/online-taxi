package com.zi.internalcommon.util;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTCreator;
import com.auth0.jwt.algorithms.Algorithm;

import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class JwtUtils {

    private static final String SIGN = "fjao%304208*@#)*";

    //生成token
    public static String generatorToken(Map<String, String> map){
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

    //测试
    public static void main(String[] args) {
        Map<String, String> map = new HashMap<>();
        map.put("name", "lisi");
        map.put("age", "23");
        String s = generatorToken(map);
        System.out.println("生成的token：" + s);
    }
}
