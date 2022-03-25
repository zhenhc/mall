package com.macro.mall.service;

import cn.hutool.core.util.RandomUtil;
import com.macro.mall.common.constant.RedisConstants;
import org.junit.Test;
import redis.clients.jedis.Jedis;

public class RedisTest {

    @Test
    public void test(){
        Jedis jedis = new Jedis(RedisConstants.IP);
        for (int i=1;i<=10000;i++) {
            jedis.del("test:"+i);
        }
    }
}
