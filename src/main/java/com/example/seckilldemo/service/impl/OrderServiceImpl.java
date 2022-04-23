package com.example.seckilldemo.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.conditions.query.QueryChainWrapper;
import com.baomidou.mybatisplus.extension.conditions.update.UpdateChainWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.seckilldemo.mapper.OrderMapper;
import com.example.seckilldemo.pojo.Order;
import com.example.seckilldemo.pojo.SeckillGoods;
import com.example.seckilldemo.pojo.SeckillOrder;
import com.example.seckilldemo.pojo.User;
import com.example.seckilldemo.service.IOrderService;
import com.example.seckilldemo.service.ISeckillGoodsService;
import com.example.seckilldemo.service.ISeckillOrderService;
import com.example.seckilldemo.vo.GoodsVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author zhoubin
 * @since 2022-03-13
 */
@Service
public class OrderServiceImpl extends ServiceImpl<OrderMapper, Order> implements IOrderService {
    @Autowired
    ISeckillGoodsService seckillGoodsService;
    @Autowired
    OrderMapper orderMapper;
    @Autowired
    RedisTemplate redisTemplate;
    @Autowired
    ISeckillOrderService seckillOrderService;
    @Transactional
    @Override
    public Order seckill(User user, GoodsVo goodsVo) {

        SeckillGoods seckillGoods = seckillGoodsService.getOne(new QueryWrapper<SeckillGoods>().eq("goods_id" , goodsVo.getId()));
        seckillGoods.setStockCount(seckillGoods.getStockCount()-1);
        boolean result = seckillGoodsService.update(new UpdateWrapper<SeckillGoods>().setSql("stock_count= " + "stock_count-1").eq("id",seckillGoods.getId()).gt("stock_count",0));
        //seckillGoodsService.updateById(seckillGoods);

        if(!result){
            return null;
        }
        //生成订单
        Order order = new Order();
        order.setUserId(user.getId());
        order.setGoodsId(goodsVo.getId());
        order.setDeliveryAddrId(0L);
        order.setGoodsName(goodsVo.getGoodsName());
        order.setGoodsCount(1);
        order.setGoodsPrice(seckillGoods.getSeckillPrice());
        order.setOrderChannel(1);
        order.setStatus(0);
        order.setCreateDate(new Date());
        orderMapper.insert(order);

        //生成秒杀订单
        SeckillOrder seckillOrder = new SeckillOrder();
        seckillOrder.setUserId(user.getId());
        seckillOrder.setOrderId(order.getId());
        seckillOrder.setGoodsId(goodsVo.getId());
        seckillOrderService.save(seckillOrder);
        //将秒杀订单缓存到redis，优化性能
        redisTemplate.opsForValue().set("order:" + user.getId() + ":" + goodsVo.getId(),seckillOrder);

        return order;
    }
}
