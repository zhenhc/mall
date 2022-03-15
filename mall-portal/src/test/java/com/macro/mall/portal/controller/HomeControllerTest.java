package com.macro.mall.portal.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.macro.mall.common.api.CommonResult;
import com.macro.mall.portal.MallPortalApplicationTests;
import com.macro.mall.portal.domain.HomeContentResult;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

public class HomeControllerTest extends MallPortalApplicationTests {

    @Autowired
    private HomeController homeController;

    @Test
    public void contentTest(){
        CommonResult<HomeContentResult> content = homeController.content();
        System.out.println(JSON.toJSONString(content));
    }
}
