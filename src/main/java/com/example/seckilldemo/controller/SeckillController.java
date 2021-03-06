package com.example.seckilldemo.controller;



import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.conditions.query.QueryChainWrapper;
import com.example.seckilldemo.pojo.Order;
import com.example.seckilldemo.pojo.SeckillMessage;
import com.example.seckilldemo.pojo.SeckillOrder;
import com.example.seckilldemo.pojo.User;
import com.example.seckilldemo.rabbitmq.MQSender;
import com.example.seckilldemo.service.IGoodsService;
import com.example.seckilldemo.service.IOrderService;
import com.example.seckilldemo.service.impl.SeckillGoodsServiceImpl;
import com.example.seckilldemo.service.impl.SeckillOrderServiceImpl;
import com.example.seckilldemo.utils.JsonUtil;
import com.example.seckilldemo.vo.GoodsVo;
import com.example.seckilldemo.vo.RespBean;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;

@Controller
@RequestMapping("/seckill")
public class SeckillController implements InitializingBean {
    @Autowired
    IGoodsService goodsService;
    @Autowired
    RedisTemplate redisTemplate;

    @Autowired
    MQSender mqSender;
    @Autowired
    SeckillOrderServiceImpl seckillOrderService;

    @Autowired
    IOrderService orderService;

    private HashMap<Long , Boolean> stockIsEmpty = new HashMap<>();

    @RequestMapping("/doSeckill")
    String doSeckill(Model model , User user , long goodsId) throws JsonProcessingException {
        //System.out.println(user.getNickname());
        if(user == null){
            return "login";
        }

//        model.addAttribute("user" , user);
//        GoodsVo goodsVo = goodsService.findGoodsVoByGoodsId(goodsId);
//
//        if(goodsVo.getGoodsStock() < 1){
//            model.addAttribute("errmsg" , "?????????");
//            return "seckillFail";
//        }
////        //????????????????????????
////        //??????????????????????????????????????????????????????????????????????????????????????????,???????????????id????????????id??????????????????
////        //??????????????????redis????????????????????????
////        SeckillOrder seckillOrder = seckillOrderService.getOne(new QueryWrapper<SeckillOrder>().eq("user_id" , user.getId()).eq("goods_id",goodsId));
////
////        if(seckillOrder != null){
////            model.addAttribute("errmsg" , "?????????????????????");
////            return "seckillFail";
////        }
//
//
//        SeckillOrder seckillOrder = (SeckillOrder) redisTemplate.opsForValue().get("order:" + user.getId() + ":" + goodsId);
//
//
//        if(seckillOrder != null){
//            return "seckillFail";
//        }
//
//        //?????????????????????????????????????????????????????????
//        Order order = orderService.seckill(user , goodsVo);
//        model.addAttribute("order" , order);
//        model.addAttribute("goods" , goodsVo);
//        return "success.html";
        /*redis????????????*/
        SeckillOrder seckillOrder = (SeckillOrder) redisTemplate.opsForValue().get("order:" + user.getId() + ":" + goodsId);
        if(seckillOrder != null){
            return "seckillFail";
        }

        if(stockIsEmpty.get(goodsId)){
            return "seckillFail";
        }
        Long stock = redisTemplate.opsForValue().decrement("seckillGoods:" + goodsId);
        if( stock < 0){
            //redisTemplate.opsForValue().increment("seckillGoods:" + goodsId);
            stockIsEmpty.put(goodsId,true);
            return "seckillFail";
        }

        SeckillMessage seckillMessage = new SeckillMessage(user , goodsId);
        mqSender.sendSeckillMessage(JsonUtil.objToJson(seckillMessage));







        return "success";
    }

    @Override
    public void afterPropertiesSet() throws Exception{
        List<GoodsVo> list = goodsService.findGoodsVo();
        if(CollectionUtils.isEmpty(list)){
            return;
        }
        list.forEach(goodsVo -> {
            redisTemplate.opsForValue().set("seckillGoods:" + goodsVo.getId(),goodsVo.getStockCount());
            stockIsEmpty.put(goodsVo.getId() , false);
        });

    }
}
