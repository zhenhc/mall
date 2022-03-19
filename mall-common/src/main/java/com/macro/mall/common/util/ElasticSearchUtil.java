package com.macro.mall.common.util;

import cn.hutool.http.HttpRequest;
import cn.hutool.json.JSONArray;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author : zhenhc
 * @date : 2022-03-19 22:52
 **/
public class ElasticSearchUtil {

    /**
     *
     * @param ip es查询地址
     * @param sql sql脚本
     * @return
     */
    public static List<Map<String,Object>> searchBySql(String ip, String sql){
        List<Map<String,Object>> list = new ArrayList<>();
        HashMap<String, Object> params = new HashMap<>();
        params.put("query",sql);
        String body = HttpRequest.get("http://" +ip+":9200/_sql?format=json")
                .body(JSONUtil.toJsonStr(params))
                .execute()
                .body();

        JSONObject result = JSONUtil.parseObj(body);
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
        return list;
    }
}
