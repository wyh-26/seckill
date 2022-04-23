package com.example.seckilldemo.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.rabbitmq.tools.json.JSONUtil;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;
import java.util.Collections;
import java.util.Comparator;

/**
 * <p>
 * 
 * </p>
 *
 * @author zhoubin
 * @since 2022-03-13
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("t_seckill_order")
public class SeckillOrder implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    private Long userId;

    private Long goodsId;

    private Long orderId;




}
