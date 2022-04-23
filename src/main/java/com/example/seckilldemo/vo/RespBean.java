package com.example.seckilldemo.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data

public class RespBean {
    public int code;
    public String message;
    public Object obj;

    public static RespBean success(){
        return new RespBean(RespBeanEnum.SUCCESS.getCode() , RespBeanEnum.SUCCESS.getMessage(),null);
    }
    public static RespBean success(Object obj){
        return new RespBean(RespBeanEnum.SUCCESS.getCode() , RespBeanEnum.SUCCESS.getMessage(),obj);
    }

    public static RespBean error(){
        return new RespBean(RespBeanEnum.ERROR.getCode(), RespBeanEnum.ERROR.getMessage(), null);
    }
}
