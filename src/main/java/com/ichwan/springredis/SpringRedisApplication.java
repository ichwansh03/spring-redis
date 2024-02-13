package com.ichwan.springredis;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.stream.Consumer;
import org.springframework.data.redis.connection.stream.ObjectRecord;
import org.springframework.data.redis.connection.stream.ReadOffset;
import org.springframework.data.redis.connection.stream.StreamOffset;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.repository.configuration.EnableRedisRepositories;
import org.springframework.data.redis.stream.StreamMessageListenerContainer;
import org.springframework.data.redis.stream.Subscription;
import org.springframework.scheduling.annotation.EnableScheduling;

import java.time.Duration;

@SpringBootApplication
@Slf4j
@EnableScheduling
@EnableRedisRepositories
@EnableCaching
public class SpringRedisApplication {

	@Autowired
	private StringRedisTemplate redisTemplate;

	@Bean
	public Subscription orderSubscription(StreamMessageListenerContainer<String, ObjectRecord<String, Order>> orderContainer,
										  OrderListener orderListener) {
		try {
			redisTemplate.opsForStream().createGroup("orders", "my-group");
		} catch (Throwable t){

		}

		var readRequest = StreamMessageListenerContainer.StreamReadRequest
				.builder(StreamOffset.create("orders", ReadOffset.lastConsumed()))
				.consumer(Consumer.from("my-group","consumer-1"))
				.autoAcknowledge(true)
				.cancelOnError(t -> false)
				.errorHandler(t -> log.warn(t.getMessage())).build();

		return orderContainer.register(readRequest, orderListener);
	}

	@Bean(destroyMethod = "stop", initMethod = "start")
	public StreamMessageListenerContainer<String, ObjectRecord<String, Order>> orderContainer(RedisConnectionFactory connectionFactory) {
		var options = StreamMessageListenerContainer.StreamMessageListenerContainerOptions
				.builder()
				.pollTimeout(Duration.ofSeconds(5))
				.targetType(Order.class)
				.build();

		return StreamMessageListenerContainer.create(connectionFactory, options);
	}

	public static void main(String[] args) {
		SpringApplication.run(SpringRedisApplication.class, args);
	}

}
