package com.macro.mall.controller;

import cn.hutool.http.HttpRequest;
import cn.hutool.json.JSONUtil;
import com.macro.mall.MallAdminApplicationTest;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Value;

public class UmsMenuControllerTest {
    @Value("baseUil")
    private String baseUil = "http://localhost:8080/";

    @Test
    public void treeListTest(){
        System.out.println(baseUil);
        try {
            String body = HttpRequest.get(baseUil + "menu/treeList")
                    .header("Authorization","Bearer eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJhZG1pbiIsImNyZWF0ZWQiOjE2NDc2ODY3MzYwMDYsImV4cCI6MTY0ODI5MTUzNn0.PjZ91HXdVNL-XNpueccLmGHOULYRzAR6Ieq_bXRG_BIPvytACmOgXFbsFgtrHh3_mn3JMVWsillGD9bRtFmmYQ")
                    .execute()
                    .body();
            System.out.println(JSONUtil.parseObj(body));
        }catch (Exception e){
            Throwable cause = e.getCause();
            System.out.println(cause.getMessage());
        }
    }
}
