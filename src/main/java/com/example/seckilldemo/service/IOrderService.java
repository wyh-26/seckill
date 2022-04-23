package com.example.seckilldemo.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.example.seckilldemo.pojo.Order;
import com.example.seckilldemo.pojo.User;
import com.example.seckilldemo.vo.GoodsVo;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author zhoubin
 * @since 2022-03-13
 */
public interface IOrderService extends IService<Order> {

    Order seckill(User user, GoodsVo goodsVo);
}
