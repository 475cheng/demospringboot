package com.bitauto.ep.fx.webapi.configuration.redis;

import org.springframework.data.redis.connection.Message;
import org.springframework.data.redis.listener.KeyExpirationEventMessageListener;
import org.springframework.data.redis.listener.RedisMessageListenerContainer;

//监听Redis
public class RedisMessageListener extends KeyExpirationEventMessageListener {
    public RedisMessageListener(RedisMessageListenerContainer listenerContainer) {
        super(listenerContainer);
    }

    @Override
    public void onMessage(Message message, byte[] bytes) {
        String key = message.toString();
        System.out.println("redis listener" + key);

    }
}
