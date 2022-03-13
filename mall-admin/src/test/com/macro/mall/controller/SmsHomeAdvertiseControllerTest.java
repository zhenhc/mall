package com.macro.mall.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.macro.mall.MallAdminApplicationTest;
import com.macro.mall.common.api.CommonPage;
import com.macro.mall.common.api.CommonResult;
import com.macro.mall.model.SmsHomeAdvertise;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

public class SmsHomeAdvertiseControllerTest extends MallAdminApplicationTest {

    @Autowired
    private SmsHomeAdvertiseController advertiseController;

    @Test
    public void test(){
        CommonResult<CommonPage<SmsHomeAdvertise>> commonResult = advertiseController.list(null,null,null,5,1);
        JSONObject jsonObject = (JSONObject) JSON.toJSON(commonResult);
        System.out.println(jsonObject);
    }
}
