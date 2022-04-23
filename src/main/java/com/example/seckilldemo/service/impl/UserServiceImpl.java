package com.example.seckilldemo.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.seckilldemo.mapper.UserMapper;
import com.example.seckilldemo.pojo.User;
import com.example.seckilldemo.service.IUserService;
import com.example.seckilldemo.utils.CookieUtil;
import com.example.seckilldemo.utils.MD5Util;
import com.example.seckilldemo.utils.UUIDUtil;
import com.example.seckilldemo.vo.LoginVo;
import com.example.seckilldemo.vo.RespBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author zhoubin
 * @since 2022-03-02
 */


@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements IUserService {

    @Autowired
    private  UserMapper userMapper;
    @Autowired
    RedisTemplate redisTemplate;


    @Override
    public RespBean doLogin(LoginVo loginVo , HttpServletRequest request , HttpServletResponse response){
        String mobile = loginVo.getMobile();
        String password = loginVo.getPassword();
        User user  = userMapper.selectById(mobile);

        if(MD5Util.fromPassToDBPass(user.getSlat() , password).equals(user.getPassword())){
//            System.out.println(MD5Util.fromPassToDBPass(user.getSlat() , password));
//            System.out.println(user.getPassword());
            //生成Cookie
            String ticket = UUIDUtil.uuid();
            //request.getSession().setAttribute(ticket , user);
            redisTemplate.opsForValue().set("user:" + ticket , user);
            CookieUtil.setCookie(request , response , "userTicket" , ticket);

            return RespBean.success(ticket);
        }else{

            //System.out.println(MD5Util.fromPassToDBPass(user.getSlat() , password));
            //System.out.println(user.getPassword());
            return RespBean.error();
        }
    }

    @Override
    public User getUserByCookie(String userTicket) {
        return (User) redisTemplate.opsForValue().get("user:"+userTicket);
    }
}
