package com.bitauto.ep.fx.webapi.task;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
@ConditionalOnProperty(name = "task.token.reload",havingValue = "true")  //当 配置文件中的 app.task.reload 与havingValue值相同时 此类配置生效
public class MyScheduledTask {
    private Logger logger=LoggerFactory.getLogger(MyScheduledTask.class);

    @Scheduled(initialDelayString ="${task.token.init}", fixedDelayString  = "${task.token.refresh}")
    public void reportCurrentTimes() throws Exception {
        System.out.println("1");
    }
}
