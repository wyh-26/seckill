package com.example.seckilldemo.rabbitmq;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MQSender {
    @Autowired
    RabbitTemplate rabbitTemplate;
    public void sendSeckillMessage(String message){
        rabbitTemplate.convertAndSend("seckillExchange" , "seckill.message" , message);
    }
}
