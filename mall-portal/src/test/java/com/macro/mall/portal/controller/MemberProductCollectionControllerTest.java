package com.macro.mall.portal.controller;
import java.util.Date;

import cn.hutool.core.util.RandomUtil;
import com.macro.mall.mapper.PmsProductMapper;
import com.macro.mall.mapper.UmsMemberMapper;
import com.macro.mall.model.PmsProduct;
import com.macro.mall.model.UmsMember;
import com.macro.mall.portal.MallPortalApplicationTests;
import com.macro.mall.portal.domain.MemberProductCollection;
import com.macro.mall.portal.repository.MemberProductCollectionRepository;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.Random;

public class MemberProductCollectionControllerTest extends MallPortalApplicationTests {

    @Autowired
    private MemberProductCollectionRepository productCollectionRepository;

    @Autowired
    private UmsMemberMapper memberMapper;

    @Autowired
    private PmsProductMapper productMapper;

    @Test
    public void addTest(){
        List<UmsMember> memberList = memberMapper.selectByExample(null);
        List<PmsProduct> productList = productMapper.selectByExample(null);
        for (int i=0;i<=100;i++) {
            MemberProductCollection productCollection = new MemberProductCollection();

            UmsMember member = memberList.get(RandomUtil.randomInt(0, memberList.size()));
            PmsProduct product = productList.get(RandomUtil.randomInt(0, productList.size()));
            productCollection.setId("");
            productCollection.setProductId(product.getId());
            productCollection.setProductName(product.getName());
            productCollection.setProductPic(product.getPic());
            productCollection.setProductSubTitle(product.getSubTitle());
            productCollection.setProductPrice(product.getPrice().toString());
            productCollection.setCreateTime(new Date());
            productCollection.setMemberId(member.getId());
            productCollection.setMemberNickname(member.getNickname());
            productCollection.setMemberIcon(member.getIcon());
            MemberProductCollection findCollection = productCollectionRepository.findByMemberIdAndProductId(productCollection.getMemberId(), productCollection.getProductId());
            if (findCollection == null) {
                productCollectionRepository.save(productCollection);
            }
        }
    }
}
