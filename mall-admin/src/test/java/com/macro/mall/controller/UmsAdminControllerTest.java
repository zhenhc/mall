package com.macro.mall.controller;

import cn.hutool.http.HttpRequest;
import cn.hutool.http.HttpUtil;
import cn.hutool.json.JSONUtil;
import com.macro.mall.MallAdminApplicationTest;
import com.macro.mall.common.api.CommonResult;
import com.macro.mall.dto.UmsAdminLoginParam;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.HashMap;
import java.util.Map;

public class UmsAdminControllerTest extends MallAdminApplicationTest {

    @Autowired
    private UmsAdminController adminController;

    @Test
    public void loginTest(){
        UmsAdminLoginParam adminLoginParam = new UmsAdminLoginParam();
        adminLoginParam.setUsername("admin");
        adminLoginParam.setPassword("macro123");
        CommonResult login = adminController.login(adminLoginParam);
        System.out.println(JSONUtil.parseObj(login));
    }

}
