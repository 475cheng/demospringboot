package com.bitauto.ep.fx.webapi.configuration.mq;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.rabbit.support.CorrelationData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;

import java.util.UUID;

/**
 * 消息生产者
 *
 */
@Component
@ConditionalOnProperty(name = "app.reload",havingValue = "true")  //当 配置文件中的 app.reload 与havingValue值相同时 此类配置生效
public class Sender implements RabbitTemplate.ConfirmCallback {

    private RabbitTemplate rabbitTemplate;

    private final static Logger logger = LoggerFactory.getLogger(Sender.class);

    /**
     * 构造方法注入
     */
    @Autowired
    public Sender(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
        rabbitTemplate.setConfirmCallback(this); //rabbitTemplate如果为单例的话，那回调就是最后设置的内容
    }



    public void sendMsg(String content) {
        CorrelationData correlationId = new CorrelationData(UUID.randomUUID().toString());
        rabbitTemplate.convertAndSend("Ex_CZK", "Rk_SCBusinessCard", content, correlationId);
        logger.info("MQ发送成功，内容："+ content);
    }

    /**
     * 发送成功的回调
     */
    @Override
    public void confirm(CorrelationData correlationData, boolean ack, String cause) {
        logger.info("回调id:" + correlationData);
        if (ack) {
            logger.info("消息成功发送");
        } else {
            logger.info("消息消费失败:" + cause);
        }
    }

}
