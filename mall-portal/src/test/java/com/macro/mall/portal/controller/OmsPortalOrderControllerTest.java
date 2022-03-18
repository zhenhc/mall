package com.macro.mall.portal.controller;

import com.alibaba.fastjson.JSON;
import com.macro.mall.common.api.CommonResult;
import com.macro.mall.portal.MallPortalApplicationTests;
import com.macro.mall.portal.domain.OrderParam;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

public class OmsPortalOrderControllerTest extends MallPortalApplicationTests {

    @Autowired
    private OmsPortalOrderController portalOrderController;
    @Test
    public void generateOrderTest(){
        OrderParam orderParam = new OrderParam();
        CommonResult commonResult = portalOrderController.generateOrder(orderParam);

        System.out.println(JSON.toJSON(commonResult));
    }
}
