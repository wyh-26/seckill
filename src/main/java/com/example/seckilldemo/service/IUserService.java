package com.example.seckilldemo.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.example.seckilldemo.pojo.User;
import com.example.seckilldemo.vo.LoginVo;
import com.example.seckilldemo.vo.RespBean;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author zhoubin
 * @since 2022-03-02
 */
public interface IUserService extends IService<User> {
    RespBean doLogin(LoginVo loginVo , HttpServletRequest request , HttpServletResponse response);
    User getUserByCookie(String userTicket);
}
