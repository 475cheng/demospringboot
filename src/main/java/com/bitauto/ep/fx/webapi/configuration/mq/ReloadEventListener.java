package com.bitauto.ep.fx.webapi.configuration.mq;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Profile;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

//消息消费者
//@ConditionalOnProperty(prefix = "app.reload",havingValue = "true")  //此类配置是否生效
@Component
@Profile("prod")   //配置表示spring.profiles.active=prod时  此类生效
public class ReloadEventListener {
    private final static Logger logger = LoggerFactory.getLogger(ReloadEventListener.class);

    @Value("${app.reload.queue}")
    private String queue;

    @Autowired
    RestTemplate api;
    @Value("${server.port}")
    private String serverPort;

    //监听不同的队列
    @RabbitHandler
    @RabbitListener(queues = "Qu_SCBusinessCard")
    public void process(String content) throws InterruptedException {
        System.out.println("Listener: "+content);
        if(content.startsWith("appevent:reload") ) {
            String url = "http://localhost:" + serverPort + "/refresh";
            System.out.println(url);
            ResponseEntity<String> response = api.postForEntity(url, null, String.class);
            logger.info("成功接收到MQ消息:" + content + queue + response.getBody());
        }
    }
}

