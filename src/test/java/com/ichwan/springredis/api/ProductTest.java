package com.ichwan.springredis.api;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.StringRedisTemplate;

import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class ProductTest {

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private ProductService productService;

    @Autowired
    private StringRedisTemplate redisTemplate;

    @Test
    void productRepoTest() {
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

    @Test
    void cacheableTest() {
        Product product = productService.getProductById("P001");
        assertNotNull(product);
        assertEquals("Sample",product.getName());

        Product product1 = productService.getProductById("P001");
        assertEquals(product, product1);
    }

    @Test
    void cachePutTest() {
        Product product = Product.builder().id("P002").name("asal").price(100L).build();
        productService.create(product);

        Product product1 = productService.getProductById("P002");
        assertEquals(product, product1);
    }

    @Test
    void cacheEvictTest() {
        Product product = productService.getProductById("P003");
        assertNotNull(product);

        productService.remove("P003");

        Product product1 = productService.getProductById("P003");
        assertEquals(product, product1);
    }
}