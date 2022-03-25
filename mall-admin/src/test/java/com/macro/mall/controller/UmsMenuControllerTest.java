package com.macro.mall.controller;

import cn.hutool.http.HttpRequest;

import cn.hutool.json.JSONUtil;
import com.macro.mall.common.constant.RedisConstants;
import org.junit.Test;
import org.springframework.http.HttpHeaders;
import redis.clients.jedis.Jedis;


public class UmsMenuControllerTest {

    private String baseUil = "http://localhost:8080/";
    private Jedis jedis;
    private String token;
    private void init(){
        jedis = new Jedis(RedisConstants.IP);
        token ="Bearer "+jedis.get(RedisConstants.REDIS_TOKEN_KEY).replace("\"","");
    }

    /**
     * 树形结构返回所有菜单列表
     */
    @Test
    public void treeListTest(){
        init();
        try {
            String body = HttpRequest.get(baseUil + "menu/treeList")
                    .header(HttpHeaders.AUTHORIZATION,token)
                    .execute()
                    .body();
            System.out.println(body);
        }catch (Exception e){
            Throwable cause = e.getCause();
            System.out.println(cause.getMessage());
        }
    }
}
