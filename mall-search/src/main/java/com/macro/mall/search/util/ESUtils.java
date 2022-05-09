package com.macro.mall.search.util;

import cn.hutool.core.util.IdUtil;
import cn.hutool.http.HttpRequest;
import cn.hutool.json.JSONArray;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.macro.mall.common.util.StringUtils;
import com.macro.mall.search.enums.EsContent;
import com.macro.mall.search.enums.FormatType;
import org.apache.http.HttpHost;
import org.elasticsearch.action.bulk.BulkRequest;
import org.elasticsearch.action.bulk.BulkResponse;
import org.elasticsearch.action.delete.DeleteRequest;
import org.elasticsearch.action.delete.DeleteResponse;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.client.indices.GetIndexRequest;
import org.elasticsearch.client.indices.GetIndexResponse;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.xcontent.XContentType;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.builder.SearchSourceBuilder;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * elasticsearch增删改查工具类
 */
public class ESUtils {
    public static final String HOSTNAME = "10.3.24.133";
    public static final String SCHEME = "http";
    public static final String URL = "http://10.3.24.133:9200";
    public static final int PORT = 9200;
    public static RestHighLevelClient client = new RestHighLevelClient(
            RestClient.builder(new HttpHost(HOSTNAME, PORT, SCHEME))
    );

    /**
     * 创建索引
     * @param indicName 索引名称
     * @return
     */
    public static String createIndic(String indicName){
        String body = HttpRequest.put(URL + "/" + indicName)
                .execute()
                .body();
        return body;
    }
    /**
     * 获取es信息
     * @return
     */
    public static String get(EsContent esContent, FormatType formatType){
        String formatTypeInfo = formatType.getInfo();
        String esContentInfo = esContent.getInfo();
        if ("json".equals(formatTypeInfo))
            formatTypeInfo = "format="+formatTypeInfo;
        String body = HttpRequest.get(URL + esContentInfo + "?"+formatTypeInfo)
                .execute()
                .body();
        return body;
    }
    /**
     * @param sql sql脚本
     * @return
     */
    public static List<Map<String,Object>> searchBySql(String sql){
        List<Map<String,Object>> list = new ArrayList<>();
        HashMap<String, Object> params = new HashMap<>();
        params.put("query",sql);
        String body = HttpRequest.get(URL+"/_sql?format=json")
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

    public static String searchBySql(String sql,FormatType formatType){
        String formatTypeInfo = formatType.getInfo();
        if ("json".equals(formatTypeInfo))
            formatTypeInfo = "format="+formatTypeInfo;
        List<Map<String,Object>> list = new ArrayList<>();
        HashMap<String, Object> params = new HashMap<>();
        params.put("query",sql);
        String body = HttpRequest.get(URL+"/_sql?"+formatTypeInfo)
                .body(JSONUtil.toJsonStr(params))
                .execute()
                .body();
        return body;
    }
    /**
     * 获取索引信息
     * @param indices 索引名称
     * @return
     */
    public static GetIndexResponse getIndex(String... indices) throws IOException {
        // 查询索引
        GetIndexRequest request = new GetIndexRequest(indices);
        try {
            GetIndexResponse getIndexResponse =
                    client.indices().get(request, RequestOptions.DEFAULT);
            return getIndexResponse;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }finally {
            client.close();
        }
    }

    /**
     * 搜索查询
     * @param queryBuilder 查询条件builder
     * @param indices 索引名称
     * @return
     */
    public static SearchResponse search(QueryBuilder queryBuilder, String... indices) throws IOException {
        // 1. 查询索引的所有数据
        SearchRequest request = new SearchRequest();
        request.indices(indices);

        // 构造查询条件
        SearchSourceBuilder builder = new SearchSourceBuilder();
        builder.query(queryBuilder);
        request.source(builder);

        SearchResponse response = null;
        try {
            response = client.search(request, RequestOptions.DEFAULT);
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            client.close();
        }
        return response;
    }

    /**
     * 插入一条数据
     * @param index 索引名称
     * @param o 数据对象
     * @return
     */
    public static IndexResponse insertOne(String index,Object o) throws IOException {
        // 插入数据
        IndexRequest request = new IndexRequest();
        request.index(index).id(IdUtil.fastSimpleUUID());

        // 向ES插入数据，必须将数据转换位JSON格式
        ObjectMapper mapper = new ObjectMapper();
        String objectJson = null;
        try {
            objectJson = mapper.writeValueAsString(o);
            request.source(objectJson,XContentType.JSON);
            IndexResponse response = client.index(request, RequestOptions.DEFAULT);
            return response;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }finally {
            client.close();
        }
    }

    /**
     * 根据主键id删除数据
     * @param index 索引名称
     * @param id 主键id
     * @return
     * @throws IOException
     */
    public static DeleteResponse deleteById(String index,String id) throws IOException {
        DeleteRequest request = new DeleteRequest();
        request.index(index).id(id);
        DeleteResponse response = client.delete(request, RequestOptions.DEFAULT);
        client.close();
        return response;
    }

    public static BulkResponse deleteBatch(String index,List<String> ids) throws IOException {
        // 批量删除数据
        BulkRequest request = new BulkRequest();
        ids.forEach(id ->{
            request.add(new DeleteRequest().index(index).id(id));
        });
        BulkResponse response = client.bulk(request, RequestOptions.DEFAULT);
        client.close();
        return response;
    }
}
