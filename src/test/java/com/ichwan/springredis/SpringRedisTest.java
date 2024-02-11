package com.ichwan.springredis;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.ListOperations;
import org.springframework.data.redis.core.SetOperations;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ValueOperations;

import java.util.Set;
import static org.hamcrest.MatcherAssert.*;
import static org.hamcrest.Matchers.hasItems;

@SpringBootTest
public class SpringRedisTest {

    /**
     * menyediakan operasi string redis
     */
    @Autowired
    private StringRedisTemplate redisTemplate;

    /**
     * melakukan operasi sama seperti di redis (set,get dll)
     * docs in here: <a href="https://docs.spring.io/spring-data/redis/docs/current/api/org/springframework/data/redis/core/ValueOperations.html">...</a>
     */
    @Test
    void stringRedisTest() {
        ValueOperations<String, String> operations = redisTemplate.opsForValue();

        operations.set("ichwan","hello world");

        Assertions.assertEquals("hello world", operations.get("ichwan"));
    }

    /**
     * docs in here: <a href="https://docs.spring.io/spring-data/redis/docs/current/api/org/springframework/data/redis/core/ListOperations.html">...</a>
     */
    @Test
    void listRedisTest() {
        ListOperations<String, String> operations = redisTemplate.opsForList();

        operations.rightPush("lastname","Sholihin");
        operations.leftPush("firstname","Ichwan");

        Assertions.assertEquals("Ichwan", operations.leftPop("firstname"));
        Assertions.assertEquals("Sholihin", operations.leftPop("lastname"));
    }

    /**
     * docs in here: <a href="https://docs.spring.io/spring-data/redis/docs/current/api/org/springframework/data/redis/core/SetOperations.html">...</a>
     */
    @Test
    void setRedisTest() {
        SetOperations<String, String> operations = redisTemplate.opsForSet();
        operations.add("person","Ichwan","Sholihin");

        Set<String> person = operations.members("person");
        Assertions.assertEquals(2, person.size());
        assertThat(person, hasItems("Ichwan", "Sholihin"));
    }
}
