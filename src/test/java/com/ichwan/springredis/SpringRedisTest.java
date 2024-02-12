package com.ichwan.springredis;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.DataAccessException;
import org.springframework.data.geo.Circle;
import org.springframework.data.geo.Distance;
import org.springframework.data.geo.GeoResults;
import org.springframework.data.geo.Point;
import org.springframework.data.redis.connection.RedisGeoCommands;
import org.springframework.data.redis.core.*;
import org.springframework.data.redis.domain.geo.Metrics;

import java.util.List;
import java.util.Set;
import static org.hamcrest.MatcherAssert.*;
import static org.hamcrest.Matchers.hasItems;
import static org.hamcrest.Matchers.hasSize;

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

    /**
     * mendapatkan jarak antar dua point dan mencari key berdasarkan point
     * docs in here: <a href="https://docs.spring.io/spring-data/redis/docs/current/api/org/springframework/data/redis/core/GeoOperations.html">...</a>
     */
    @Test
    void geoRedisTest() {
        GeoOperations<String, String> operations = redisTemplate.opsForGeo();
        operations.add("branch",new Point(105.191658,-5.266823), "Home");
        operations.add("branch",new Point(105.190548,-5.266466), "Toko");

        Distance distance = operations.distance("branch", "Home", "Toko", Metrics.METERS);
        Assertions.assertEquals(129.2528,distance.getValue());

        GeoResults<RedisGeoCommands.GeoLocation<String>> searched = operations.search("branch", new Circle(
                new Point(105.191658, -5.266823),
                new Distance(5, Metrics.KILOMETERS)
        ));

        Assertions.assertEquals(2, searched.getContent().size());
    }

    /**
     * melakukan database transaction, semua perubahan akan dicommit di akhir
     */
    @Test
    void execute() {
        redisTemplate.execute(new SessionCallback<>() {
            @Override
            public Object execute(RedisOperations operations) throws DataAccessException {
                operations.multi();

                operations.opsForValue().set("ichwan","Test01");
                operations.opsForValue().set("sholihin","Test03");

                operations.exec();
                return null;
            }
        });

        Assertions.assertEquals("Test01", redisTemplate.opsForValue().get("ichwan"));
    }

    /**
     * melakukan input data sekaligus tanpa menunggu balasan satu persatu
     */
    @Test
    void executePipeline() {
        List<Object> list = redisTemplate.executePipelined(new SessionCallback<>() {
            @Override
            public Object execute(RedisOperations operations) throws DataAccessException {

                operations.opsForValue().set("test1", "Hello world");
                operations.opsForValue().set("test2", "Halo dunia");
                return null;
            }
        });

        assertThat(list, hasSize(2));
        assertThat(list, hasItems(true));
    }
}
