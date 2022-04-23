package com.example.seckilldemo.vo;

import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
public class LoginVo {
    @NotNull
    private String mobile;
    @NotNull
    private String password;
}
