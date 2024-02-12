package com.ichwan.springredis;

import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.connection.stream.ObjectRecord;
import org.springframework.data.redis.stream.StreamListener;
import org.springframework.stereotype.Component;

/**
 * class ini akan menerima data dari stream publisher
 */
@Component
@Slf4j
public class OrderListener implements StreamListener<String, ObjectRecord<String, Order>> {
    @Override
    public void onMessage(ObjectRecord<String, Order> message) {
        log.info("received data: {}",message.getValue());
    }
}
