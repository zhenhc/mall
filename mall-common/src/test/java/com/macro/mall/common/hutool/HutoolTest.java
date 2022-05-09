package com.macro.mall.common.hutool;

import cn.hutool.http.HttpRequest;
import cn.hutool.http.HttpResponse;
import cn.hutool.json.JSONArray;
import cn.hutool.json.JSONUtil;
import com.alibaba.fastjson.JSONObject;
import com.macro.mall.common.domain.Account;
import com.macro.mall.common.util.StringUtils;
import org.junit.Test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
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


    @Test
    public void esTest1(){
        List<Map<String,Object>> list = new ArrayList<>();
        HashMap<String, Object> params = new HashMap<>();
        params.put("query","SELECT * FROM account LIMIT 10");
        String body = HttpRequest.get("http://192.168.31.233:9200/_sql?format=json")
                .body(JSONUtil.toJsonStr(params))
                .execute()
                .body();
        cn.hutool.json.JSONObject result = JSONUtil.parseObj(body);
        JSONArray columns = result.getJSONArray("columns");
        JSONArray rows = result.getJSONArray("rows");
        for (int i=0;i<rows.size();i++){
            JSONArray row = rows.getJSONArray(i);
            HashMap<String, Object> map = new HashMap<>();
            for (int j=0;j<columns.size();j++){
                map.put(StringUtils.toCamelCase(columns.getJSONObject(j).getStr("name")),row.get(j));
            }
            list.add(map);
        }
        System.out.println(JSONUtil.parseArray(list));
    }



    //curl -XPOST "http://192.168.31.233:9200/_sql?format=txt" -H 'Content-Type: application/json' -d'{  "query": "SELECT account_number,address,age,balance FROM account LIMIT 10"}'
    @Test
    public void esTest(){
        HashMap<String, Object> map = new HashMap<>();
        map.put("query","SELECT account_number,address,age,balance FROM account");
        String body = HttpRequest.get("http://192.168.31.233:9200/_sql?format=json")
                .body(JSONUtil.toJsonStr(map))
                .execute()
                .body();

        //System.out.println(body);
        JSONArray rows = JSONUtil.parseObj(body).getJSONArray("rows");

        List<Account> accountList = new ArrayList<>();
        for (int i=0;i<rows.size();i++){
            JSONArray row = rows.getJSONArray(i);
            Account account = new Account();
            account.setAccountNumber(row.getLong(0));
            account.setAddress(row.getStr(1));
            account.setAge(row.getLong(2));
            account.setBalance(row.getLong(3));
            accountList.add(account);
        }
        System.out.println(JSONUtil.parseArray(accountList));
    }



}
