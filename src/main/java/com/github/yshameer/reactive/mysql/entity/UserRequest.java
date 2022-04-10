package com.github.yshameer.reactive.mysql.entity;

import lombok.Data;

import java.util.List;

@Data
public class UserRequest {
    private String mobileNumber;
    private String billDate;
    private String effectiveDate;
    private String billCode;
    String cposition;
    String maxrows;
}