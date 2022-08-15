package com.zi.internalcommon.constant;

import lombok.Getter;


public enum CommonStatusEnum {

    /**
     * 验证码不正确 1000 - 1099
     */
    VERTIFICATION_CODE_ERROR(1099,"验证码不正确"),

    /**
     * token错误 1100 - 1199
     */
    TOKEN_ERROR(1199, "token错误"),

    /**
     * 用户提示：1200 - 1299
     */
    USER_NOT_EXIST(1200, "当前用户不存在"),

    /**
     * 成功
     */
    SUCCESS(1, "success"),

    /**
     * 失败
     */
    FAIL(0, "fail");

    @Getter
    private int code;

    @Getter
    private String value;

    CommonStatusEnum(int code, String value){
        this.code = code;
        this.value = value;
    }
}
