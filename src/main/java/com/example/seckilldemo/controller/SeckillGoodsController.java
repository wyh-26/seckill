package com.example.seckilldemo.controller;


import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author zhoubin
 * @since 2022-03-13
 */
@Controller
@RequestMapping("/sec89kill")
public class SeckillGoodsController {
    @RequestMapping("/doSeck89ill")
    public String doSeckill(){
        return "hello";
    }

}
