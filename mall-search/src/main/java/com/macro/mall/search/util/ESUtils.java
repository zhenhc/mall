package com.macro.mall.search.util;

import cn.hutool.core.util.IdUtil;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.http.HttpHost;
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

public class ESUtils {
    public static final String HOSTNAME = "10.3.24.133";
    public static final String SCHEME = "http";
    public static final int PORT = 9200;
    public static RestHighLevelClient client = new RestHighLevelClient(
            RestClient.builder(new HttpHost(HOSTNAME, PORT, SCHEME))
    );

    /**
     * 获取索引信息
     * @param indices 索引名称
     * @return
     */
    public static GetIndexResponse getIndex(String... indices){
        // 查询索引
        GetIndexRequest request = new GetIndexRequest(indices);
        try {
            GetIndexResponse getIndexResponse =
                    client.indices().get(request, RequestOptions.DEFAULT);
            return getIndexResponse;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 搜索查询
     * @param queryBuilder 查询条件builder
     * @param indices 索引名称
     * @return
     */
    public static SearchResponse search(QueryBuilder queryBuilder, String... indices){
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
        }
        return response;
    }

    /**
     * 插入一条数据
     * @param index 索引名称
     * @param o 数据对象
     * @return
     */
    public static IndexResponse insertOne(String index,Object o){
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
        }
    }
}
