package com.macro.mall.portal.util;

import cn.hutool.core.io.FileUtil;
import cn.hutool.core.io.file.FileReader;
import cn.hutool.core.io.file.FileWriter;
import cn.hutool.core.lang.Console;
import cn.hutool.core.util.ReUtil;
import cn.hutool.extra.qrcode.QrCodeUtil;
import cn.hutool.http.HttpUtil;
import com.alibaba.fastjson.JSON;
import org.junit.Test;

import java.io.File;
import java.util.List;

public class HutoolTest {
    @Test
    public void test(){
        FileReader fileReader = new FileReader("E:\\developer\\IDE\\idea-projects\\mall\\mall\\mall-portal\\src\\main\\resources\\application-dev.yml");
        String string = fileReader.readString();
        System.out.println(string);
    }

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

    @Test
    public void test3(){
        // 生成指定url对应的二维码到文件，宽和高都是300像素
        QrCodeUtil.generate("http://www.macrozheng.com/#/README", 300, 300, FileUtil.file("f:/mall.jpg"));
    }
}
