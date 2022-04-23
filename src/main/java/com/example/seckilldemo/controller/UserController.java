package com.example.seckilldemo.controller;


import com.example.seckilldemo.pojo.User;
import com.example.seckilldemo.vo.RespBean;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author zhoubin
 * @since 2022-03-02
 */
@RestController
@RequestMapping("/user")
public class UserController {
    @RequestMapping("info")
    RespBean info(User user){
        return RespBean.success();
    }
}
