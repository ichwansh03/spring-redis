package com.ichwan.springredis;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;

@SpringBootTest
public class CacheRedisTest {

    @Autowired
    private CacheManager cacheManager;

    @Test
    void cacheTest() {
        Cache scores = cacheManager.getCache("scores");
        scores.put("test1",90);
        scores.put("test3",50);

        Assertions.assertEquals(90, scores.get("test1", Integer.class));

        scores.evict("test3");
        Assertions.assertNull(scores.get("test3"));
    }
}
