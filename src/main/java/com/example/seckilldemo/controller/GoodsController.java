package com.example.seckilldemo.controller;


import com.example.seckilldemo.pojo.User;
import com.example.seckilldemo.service.IGoodsService;
import com.example.seckilldemo.service.impl.UserServiceImpl;
import com.example.seckilldemo.vo.GoodsVo;
import com.example.seckilldemo.vo.RespBean;
//import org.springframework.boot.web.servlet.server.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
//import java.sql.Date;
import java.util.Date;

@Controller
@RequestMapping("/goods")
public class GoodsController {

    @Autowired
    UserServiceImpl userServiceImpl;

    @Autowired
    IGoodsService goodsService;

    @RequestMapping("/toList")
    public String toList(HttpSession session , Model model  , @CookieValue("userTicket") String ticket){
        if(ticket.isEmpty()){
            return "login";
        }

        //User user = (User)session.getAttribute(ticket);
        User user = userServiceImpl.getUserByCookie(ticket);
        if(user == null){
            System.out.println("跳转商品页失败");
            return "login";
        }

        model.addAttribute("user" , user);
        model.addAttribute("goodsList" , goodsService.findGoodsVo());

        return "goodsList";
    }

    @RequestMapping("/toDetail/{goodsId}")
    public String toDetail(Model model , User user , @PathVariable long goodsId){
        System.out.println("helloooooo");
        GoodsVo goodsVo = goodsService.findGoodsVoByGoodsId(goodsId);
        Date startDate = goodsVo.getStartDate();
        Date endDate = goodsVo.getEndDate();
        Date nowDate = new Date();
        //秒杀状态
        int secKillStatus = 0;
        //秒杀倒计时
        int remainSeconds = 0;
        //秒杀还未开始
        if (nowDate.before(startDate)) {
            remainSeconds = ((int) ((startDate.getTime() - nowDate.getTime()) / 1000));
        } else if (nowDate.after(endDate)) {
            //	秒杀已结束
            secKillStatus = 2;
            remainSeconds = -1;
        } else {
            //秒杀中
            secKillStatus = 1;
            remainSeconds = 0;
        }
        model.addAttribute("remainSeconds", remainSeconds);

        model.addAttribute("secKillStatus", secKillStatus);
        model.addAttribute("user" , user);
        model.addAttribute("goods" , goodsService.findGoodsVoByGoodsId(goodsId));

        return "goodsDetail";
    }
}
