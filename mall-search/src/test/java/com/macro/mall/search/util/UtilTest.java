package com.macro.mall.search.util;

import cn.hutool.core.util.RandomUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.json.JSONUtil;
import com.macro.mall.search.enums.EsContent;
import com.macro.mall.search.enums.FormatType;
import junit.framework.TestCase;
import org.apache.commons.lang3.RandomStringUtils;
import org.elasticsearch.action.bulk.BulkResponse;
import org.elasticsearch.action.delete.DeleteResponse;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.indices.GetIndexResponse;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.SearchHits;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class UtilTest extends TestCase {

    public void test6() throws IOException {
        String id = "fedfd47bf27441b6a307e85a096826a2";
        DeleteResponse deleteResponse = ESUtils.deleteById("user", id);
        System.out.println(deleteResponse);
    }
    //删除
    public void test5() throws IOException {
        SearchResponse user = ESUtils.search(QueryBuilders.matchAllQuery(), "user");
        SearchHits hits = user.getHits();
        List<String> ids = new ArrayList<>();
        hits.forEach(hit -> {
            String id = hit.getId();
            try {
                Thread.sleep(1000);
                DeleteResponse deleteResponse = ESUtils.deleteById("user", id);
                System.out.println(deleteResponse);
            } catch (IOException | InterruptedException e) {
                e.printStackTrace();
            }
        });
    }
    public void test4(){
        String s = ESUtils.get(EsContent.INDICES, FormatType.TABLE);
        //System.out.println(s);
        String s1 = ESUtils.searchBySql("select * from user limit 10", FormatType.JSON);
        System.out.println(s1);
    }
    public void test3() throws IOException {
        String[] sex = {"男","女"};
        User user = new User();
        for (int i=0;i<100;i++) {
            user.setName(RandomStringUtils.randomAlphabetic(5, 10));
            user.setAge(RandomUtil.randomInt(1, 150));
            user.setSex(sex[RandomUtil.randomInt(0, 2)]);
            IndexResponse response = ESUtils.insertOne("user", user);
            System.out.println(response.getResult());
        }
    }
    public void test2() throws IOException {
        //使用QueryBuilders构建查询条件
        SearchResponse user = ESUtils.search(QueryBuilders.rangeQuery("age").from(40).to(90),"user");
        SearchHits hits = user.getHits();
        System.out.println(user.getTook());
        System.out.println(hits.getTotalHits());
        for (SearchHit hit : hits.getHits()) {
            Map<String, Object> sourceAsMap = hit.getSourceAsMap();
            sourceAsMap.put("_id",hit.getId());
            System.out.println(JSONUtil.toJsonStr(sourceAsMap));
        }
    }
    public void test1() throws IOException {
        GetIndexResponse index = ESUtils.getIndex("user");
        System.err.println(index.getMappings());
    }
    class User{
        private String name;
        private String sex;
        private Integer age;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getSex() {
            return sex;
        }

        public void setSex(String sex) {
            this.sex = sex;
        }

        public Integer getAge() {
            return age;
        }

        public void setAge(Integer age) {
            this.age = age;
        }
    }
}
