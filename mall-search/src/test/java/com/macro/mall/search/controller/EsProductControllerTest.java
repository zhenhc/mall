package com.macro.mall.search.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.macro.mall.common.api.CommonPage;
import com.macro.mall.common.api.CommonResult;
import com.macro.mall.search.MallSearchApplicationTests;
import com.macro.mall.search.domain.EsProduct;
import com.macro.mall.search.repository.EsProductRepository;
import com.macro.mall.search.service.EsProductService;
import io.swagger.models.auth.In;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;

import java.util.ArrayList;
import java.util.List;

public class EsProductControllerTest extends MallSearchApplicationTests {

    @Autowired
    private EsProductRepository esProductRepository;
    @Autowired
    private EsProductService esProductService;
    @Test
    public void searchTest(){
        String keyword = "小米";
        Integer pageNum = 1;
        Integer pageSize = 5;
        Page<EsProduct> esProductPage = esProductService.search(keyword, pageNum, pageSize);

        CommonResult<CommonPage<EsProduct>> commonResult = CommonResult.success(CommonPage.restPage(esProductPage));

        JSONObject jsonObject = (JSONObject) JSON.toJSON(commonResult);
        System.out.println(jsonObject);
    }

    @Test
    public void test(){
        Iterable<EsProduct> iterable = esProductRepository.findAll();
        CommonResult commonResult = CommonResult.success(iterable);
        JSONObject jsonObject = (JSONObject) JSON.toJSON(commonResult);
        System.out.println(jsonObject);
    }

    @Test
    public void importTest(){
        int count = esProductService.importAll();
        CommonResult commonResult = CommonResult.success(count);

        JSONObject jsonObject = (JSONObject) JSON.toJSON(commonResult);
        System.out.println(jsonObject);
    }

    @Test
    public void deleteIds(){
        List<Long> list = new ArrayList<>();
        for (int i=26;i<=36;i++){
            list.add(Long.valueOf(i));
        }
        esProductService.delete(list);
        //return CommonResult.success(null);
    }
}
