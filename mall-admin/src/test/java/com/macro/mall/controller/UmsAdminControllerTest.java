package com.macro.mall.controller;

import cn.hutool.http.HttpRequest;
import cn.hutool.json.JSONUtil;
import com.alibaba.fastjson.JSON;
import com.macro.mall.MallAdminApplicationTest;
import com.macro.mall.common.api.CommonResult;
import com.macro.mall.dto.UmsAdminLoginParam;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.*;
import java.util.stream.Collectors;

public class UmsAdminControllerTest {

    @Test
    public void loginTest() {
        Map<String, Object> map = new HashMap<>();
        map.put("username", "admin");
        map.put("password", "macro123");
        List<String> list = new ArrayList<>();
        for (int i=0;i<=10;i++) {
            String body = HttpRequest.post("http://localhost:8080/admin/login")
                    .body(JSONUtil.toJsonStr(map))
                    .execute()
                    .body();
            String str = JSONUtil.parseObj(body).getJSONObject("data").getStr("token");
            list.add(str);
        }
        System.out.println(list.size());
        Set<String> set = new HashSet<>();
        set.addAll(list);
        System.out.println(JSONUtil.toJsonStr(set));
    }
}
