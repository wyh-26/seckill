package com.example.seckilldemo.vo;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
@AllArgsConstructor
public enum RespBeanEnum {
    SUCCESS(200 , "SUCCESS"),
    ERROR(500 , "ERROR");

    private final Integer code;
    private final String message;

}
