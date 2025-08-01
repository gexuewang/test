package com.gexw;

import com.gexw.context.CachePrefix;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.test.context.junit4.SpringRunner;

@SpringBootTest
@RunWith(SpringRunner.class)  //2xx必须加
public class SpringbootT1 {

    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    @Autowired
    private CachePrefix cachePrefix;

    @Test
    public void contextLoads() {
        String address = cachePrefix.getAddress();
        System.out.println(address);
//        System.out.println(cachePrefix.getPrefix());
//        System.out.println(cachePrefix.getYewu());

    }
}
