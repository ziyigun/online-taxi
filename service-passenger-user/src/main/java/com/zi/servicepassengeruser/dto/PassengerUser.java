package com.zi.servicepassengeruser.dto;

import lombok.Data;

import java.sql.Date;

@Data
public class PassengerUser {

    private long id;

    private Date gmtCreate;

    private Date gmtModified;

    private String passengerPhone;

    private String passengerName;
    private byte passengerGender;

    private byte state;
}
