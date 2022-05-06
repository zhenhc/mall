package com.macro.mall.search.util;

import cn.hutool.core.util.RandomUtil;
import cn.hutool.core.util.StrUtil;
import junit.framework.TestCase;
import org.apache.commons.lang3.RandomStringUtils;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.indices.GetIndexResponse;
import org.elasticsearch.index.query.MatchAllQueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.SearchHits;

public class UtilTest extends TestCase {

    public void test3(){
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
    public void test2(){
        //使用QueryBuilders构建查询条件
        SearchResponse user = ESUtils.search(QueryBuilders.rangeQuery("age").from(40).to(90),"user");
        SearchHits hits = user.getHits();
        System.out.println(user.getTook());
        System.out.println(hits.getTotalHits());
        for (SearchHit hit : hits.getHits()) {
            System.out.println(hit.getSourceAsString());
        }
    }
    public void test1(){
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
