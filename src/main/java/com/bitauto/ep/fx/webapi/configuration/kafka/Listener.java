package com.bitauto.ep.fx.webapi.configuration.kafka;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;

public class Listener {
    protected final Logger logger = LoggerFactory.getLogger(this.getClass());


    //@KafkaListener(topics = {"mykafka"})
    public void listen(ConsumerRecord<?, ?> record) {
        System.out.println(" ------------ listen : "+ record.value().toString());
        /*logger.info("kafka的key: " + record.key());
        logger.info("kafka的value: " + record.value().toString());*/
    }
}
