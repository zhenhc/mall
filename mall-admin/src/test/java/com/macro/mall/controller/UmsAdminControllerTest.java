package com.macro.mall.controller;

import cn.hutool.http.HttpRequest;
import cn.hutool.json.JSONUtil;

import org.junit.Test;


import java.util.*;
import java.util.stream.Collectors;

public class UmsAdminControllerTest {

    @Test
    public void loginTest() {
        Map<String, Object> map = new HashMap<>();
        map.put("username", "admin");
        map.put("password", "macro123");
        String body = HttpRequest.post("http://localhost:8080/admin/login")
                .body(JSONUtil.toJsonStr(map))
                .execute()
                .body();
        System.out.println(body);
    }
}
