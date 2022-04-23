package com.example.seckilldemo.pojo;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;
import java.util.Date;

/**
 * <p>
 * 
 * </p>
 *
 * @author zhoubin
 * @since 2022-03-02
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("t_user")
public class User implements Serializable {

    private static final long serialVersionUID = 1L;

    private String nickname;

    private Long id;

    private String password;

    private String slat;

    private String head;

    private Date registerDate;

    private Date lastLoginDate;

    private Integer loginCount;


}
