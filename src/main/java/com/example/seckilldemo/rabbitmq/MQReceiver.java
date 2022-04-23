package com.example.seckilldemo.rabbitmq;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.example.seckilldemo.mapper.OrderMapper;
import com.example.seckilldemo.pojo.*;
import com.example.seckilldemo.service.impl.GoodsServiceImpl;
import com.example.seckilldemo.service.impl.OrderServiceImpl;
import com.example.seckilldemo.service.impl.SeckillGoodsServiceImpl;
import com.example.seckilldemo.service.impl.SeckillOrderServiceImpl;
import com.example.seckilldemo.utils.JsonUtil;
import com.example.seckilldemo.vo.GoodsVo;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.Date;

@Service
public class MQReceiver {
    @Autowired
    RedisTemplate redisTemplate;
    @Autowired
    GoodsServiceImpl goodsService;
    @Autowired
    SeckillGoodsServiceImpl seckillGoodsService;
    @Autowired
    OrderMapper orderMapper;
    @Autowired
    OrderServiceImpl orderService;

    @RabbitListener(queues = "seckillQueue")
    public void receive(String message) throws IOException {
        SeckillMessage obj = new SeckillMessage();
        SeckillMessage seckillMessage = (SeckillMessage) JsonUtil.jsonToObj(obj , message);
        Long goodsId = seckillMessage.getGoodsId();
        User user = seckillMessage.getUser();

        GoodsVo goodsVo = goodsService.findGoodsVoByGoodsId(goodsId);

        if(goodsVo.getGoodsStock() < 1){
            return;
        }

        SeckillOrder seckillOrder = (SeckillOrder) redisTemplate.opsForValue().get("order:" + user.getId() + ":" + goodsId);

        if(seckillOrder != null){
            return;
        }

        orderService.seckill(user , goodsVo);







    }
}
