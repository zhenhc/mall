package com.macro.mall.common.hutool;

import cn.hutool.http.HttpRequest;
import cn.hutool.http.HttpResponse;
import cn.hutool.json.JSONUtil;
import com.alibaba.fastjson.JSONObject;
import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

public class HutoolTest {

    @Test
    public void getAdminInfoTest(){
        Map<String,Object> map = new HashMap<>();
        map.put("username","admin");
        map.put("password","macro123");
        HttpRequest httpRequest = HttpRequest.post("http://localhost:8080/admin/login")
                .body(JSONUtil.toJsonStr(map));
        System.out.println(httpRequest);
        HttpResponse httpResponse = httpRequest.execute();
        System.out.println(httpResponse);
        System.out.println(httpResponse.body());
        JSONObject jsonObject = JSONObject.parseObject(httpResponse.body()).getJSONObject("data");
        System.out.println(jsonObject);
    }

}
