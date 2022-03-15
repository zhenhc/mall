package com.macro.mall.portal.controller;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import cn.hutool.core.util.RandomUtil;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.macro.mall.mapper.PmsProductMapper;
import com.macro.mall.mapper.UmsMemberMapper;
import com.macro.mall.model.PmsProduct;
import com.macro.mall.model.UmsMember;
import com.macro.mall.portal.MallPortalApplicationTests;
import com.macro.mall.portal.domain.MemberReadHistory;
import com.macro.mall.portal.repository.MemberReadHistoryRepository;
import com.macro.mall.portal.service.UmsMemberService;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

public class MemberReadHistoryControllerTest extends MallPortalApplicationTests {

    @Autowired
    private MemberReadHistoryRepository readHistoryRepository;

    @Autowired
    private UmsMemberMapper umsMemberMapper;

    @Autowired
    private PmsProductMapper productMapper;
    @Test
    public void createReadHistoryBatchTest(){
        List<UmsMember> memberList= umsMemberMapper.selectByExample(null);
        List<PmsProduct> productList = productMapper.selectByExample(null);
        List<Long> memberIdList = memberList.stream().map(umsMember -> umsMember.getId()).collect(Collectors.toList());
        JSONArray jsonArray = (JSONArray) JSON.toJSON(memberList);
        JSONArray jsonArray1 = (JSONArray) JSON.toJSON(memberIdList);
        System.out.println(jsonArray);
        System.out.println(jsonArray1);

        List<MemberReadHistory> readHistoryList = new ArrayList<>();
        long startTime = System.currentTimeMillis();
        System.out.println("startTime："+startTime);
        for (int i=0;i<=100000;i++) {
            MemberReadHistory readHistory = new MemberReadHistory();

            UmsMember umsMember = memberList.get(RandomUtil.randomInt(0,memberList.size()));
            PmsProduct pmsProduct = productList.get(RandomUtil.randomInt(0,productList.size()));
            readHistory.setId(null);
            readHistory.setMemberId(umsMember.getId());
            readHistory.setMemberNickname(umsMember.getNickname());
            readHistory.setMemberIcon(umsMember.getIcon());
            readHistory.setProductId(pmsProduct.getId());
            readHistory.setProductName(pmsProduct.getName());
            readHistory.setProductPic(pmsProduct.getPic());
            readHistory.setProductSubTitle(pmsProduct.getSubTitle());
            readHistory.setProductPrice(pmsProduct.getPrice().toString());
            readHistory.setCreateTime(new Date());
            readHistoryList.add(readHistory);
            if (readHistoryList.size()==10000){
                System.err.println("浏览计数总数："+readHistoryList.size());
                readHistoryRepository.saveAll(readHistoryList);
                readHistoryList.clear();
            }
            //readHistoryRepository.save(readHistory);
        }
        //readHistoryRepository.saveAll(readHistoryList);
        long endTime = System.currentTimeMillis();
        System.out.println("endTime："+ endTime);
        float seconds = (endTime - startTime) / 1000F;
        System.out.println(seconds +" seconds.");
/*
        JSONArray jsonArray2 = (JSONArray) JSON.toJSON(readHistoryList);
        System.out.println(jsonArray2);
        readHistoryRepository.saveAll(readHistoryList);*/
    }
}
