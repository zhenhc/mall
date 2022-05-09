package com.macro.mall.portal.util;

import cn.hutool.core.io.FileUtil;
import cn.hutool.core.io.file.FileReader;
import cn.hutool.core.io.file.FileWriter;
import cn.hutool.core.lang.Console;
import cn.hutool.core.util.ReUtil;
import cn.hutool.db.nosql.mongo.MongoFactory;
import cn.hutool.extra.qrcode.QrCodeUtil;
import cn.hutool.http.HttpUtil;
import com.alibaba.fastjson.JSON;
import com.mongodb.Block;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.MongoIterable;
import junit.framework.TestCase;
import org.bson.Document;
import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.BeforeEach;

import java.io.File;
import java.util.List;
import java.util.function.Consumer;

public class HutoolTest extends TestCase {

    public static final String dbName = "mall-port";
    private MongoDatabase mongoDatabase = MongoFactory.getDS("master").getDb(dbName);;
    public void test5(){
        MongoCollection<Document> memberReadHistory =
                mongoDatabase.getCollection("memberReadHistory");
        long l = memberReadHistory.countDocuments();
        System.out.println(l+" 条数据");
        for (Document document : memberReadHistory.find()) {
            String s = document.toJson();
            System.out.println(s);
        }

    }
    public void test4(){
        MongoDatabase mongoDatabase = MongoFactory.getDS("127.0.0.1",27017).getDb(dbName);
        MongoIterable<String> strings = mongoDatabase.listCollectionNames();

        strings.forEach((Consumer<String>) s -> System.out.println(s));
    }
    /**
     * 读入数据
     */
    @Test
    public void test(){
        FileReader fileReader = new FileReader("E:\\developer\\IDE\\idea-projects\\mall\\mall\\mall-portal\\src\\main\\resources\\application-dev.yml");
        String string = fileReader.readString();
        System.out.println(string);
    }

    /**
     * 写出文件
     */
    @Test
    public void test1(){
        FileReader fileReader = new FileReader("E:\\developer\\IDE\\idea-projects\\mall\\mall\\mall-portal\\src\\main\\resources\\application-dev.yml");
        List<String> stringList = fileReader.readLines();
        //System.out.println(stringList);
        FileWriter fileWriter = new FileWriter("C:\\Users\\甄豪闯\\Desktop\\新建文件夹\\test.properties");
        stringList.forEach(s -> {
            System.out.println(s);
            fileWriter.write(s,true);
        });
    }

    /**
     * 爬虫
     */
    @Test
    public void test2(){
        //请求列表页
        String listContent = HttpUtil.get("https://www.oschina.net/action/ajax/get_more_news_list?newsType=&p=2");
        //使用正则获取所有标题
        List<String> titles = ReUtil.findAll("<span class=\"text-ellipsis\">(.*?)</span>", listContent, 1);
        for (String title : titles) {
            //打印标题
            Console.log(title);
            //Console.error(title);
        }

    }

    /**
     * 测试生成二维码
     */
    @Test
    public void test3(){
        // 生成指定url对应的二维码到文件，宽和高都是300像素
        QrCodeUtil.generate("http://www.macrozheng.com/#/README", 300, 300, FileUtil.file("f:/mall.jpg"));
    }
}
