package com.ichwan.springredis.api;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.StringRedisTemplate;

import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class ProductRepositoryTest {

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private StringRedisTemplate redisTemplate;

    @Test
    void productTest() {
        Product product = Product.builder()
                .id("1")
                .name("Ciki")
                .price(2000L)
                .build();

        productRepository.save(product);

        Map<Object, Object> entries = redisTemplate.opsForHash().entries("products:1");
        assertEquals(product.getName(),entries.get("name"));
        assertEquals(product.getPrice().toString(),entries.get("price"));
        assertEquals(product, productRepository.findById("1").isPresent());
    }
}