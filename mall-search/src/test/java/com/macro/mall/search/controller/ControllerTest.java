package com.macro.mall.search.controller;

import cn.hutool.http.HttpRequest;
import com.macro.mall.common.util.ElasticSearchUtil;
import org.junit.Test;

import java.util.List;
import java.util.Map;

public class ControllerTest {

    @Test
    public void test1(){
        List<Map<String, Object>> mapList = ElasticSearchUtil.searchBySql("10.3.24.133", "select * from library");
        System.out.println(mapList);
    }
    @Test
    public void test(){
        String body = HttpRequest.get("http://10.3.24.133:9200/_cat/indices?v")
                .execute()
                .body();
        System.out.println(body);
    }
}
