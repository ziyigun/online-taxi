package com.zi.apipassenger.request;

import lombok.Data;

@Data
public class VertificationCodeDTO {

    private String passengerPhone;

    private String vertificationCode;

}
