package com.zi.internalcommon.util;

public class RedisPrefixUtils {

    //乘客验证码前缀
    public static String vertificationCodePrefix = "passenger-vertification-code-";

    //token存储的前缀
    public static String tokenPrefix = "token-";

    public static String generateKey(String passengerPhone){
        return vertificationCodePrefix + passengerPhone;
    }


    /**
     * 根据手机号和身份标识生成token的key
     * @param phone
     * @param identity
     * @return
     */
    public static String generateToken(String phone, String identity, String tokenType){
        return tokenPrefix + phone + "-" + identity + "-" + tokenType;
    }
}
