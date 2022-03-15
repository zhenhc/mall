package com.macro.mall.portal.controller;

import cn.hutool.core.io.file.FileWriter;
import cn.hutool.core.util.RandomUtil;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.macro.mall.mapper.PmsBrandMapper;
import com.macro.mall.mapper.UmsMemberMapper;
import com.macro.mall.model.PmsBrand;
import com.macro.mall.model.SmsHomeBrand;
import com.macro.mall.model.UmsMember;
import com.macro.mall.portal.MallPortalApplicationTests;
import com.macro.mall.portal.domain.MemberBrandAttention;
import com.macro.mall.portal.repository.MemberBrandAttentionRepository;
import io.swagger.models.auth.In;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.util.*;

public class MemberAttentionControllerTest extends MallPortalApplicationTests {

    @Autowired
    private UmsMemberMapper memberMapper;

    @Autowired
    private PmsBrandMapper brandMapper;
    @Autowired
    private MemberBrandAttentionRepository memberBrandAttentionRepository;

    /**
     * 批量添加品牌关注
     */
    @Test
    public void addTest(){
        List<UmsMember> memberList = memberMapper.selectByExample(null);
        List<PmsBrand> brandList = brandMapper.selectByExample(null);
        List<MemberBrandAttention> brandAttentionList = new ArrayList<>();
        for (int i=0;i<=95;i++){
            UmsMember member = memberList.get(RandomUtil.randomInt(0,memberList.size()));
            PmsBrand brand = brandList.get(RandomUtil.randomInt(0, brandList.size()));
            MemberBrandAttention memberBrandAttention = new MemberBrandAttention();
            memberBrandAttention.setId("");
            memberBrandAttention.setBrandId(brand.getId());
            memberBrandAttention.setBrandName(brand.getName());
            memberBrandAttention.setBrandLogo(brand.getLogo());
            memberBrandAttention.setBrandCity(null);

            memberBrandAttention.setMemberId(member.getId());
            memberBrandAttention.setMemberNickname(member.getNickname());
            memberBrandAttention.setMemberIcon(member.getIcon());
            memberBrandAttention.setCreateTime(new Date());
            MemberBrandAttention findAttention = memberBrandAttentionRepository.findByMemberIdAndBrandId(memberBrandAttention.getMemberId(), memberBrandAttention.getBrandId());
            if (findAttention == null) {
                brandAttentionList.add(memberBrandAttention);
                if (brandAttentionList.size()==10){
                    System.err.println(brandAttentionList.size());
                    memberBrandAttentionRepository.saveAll(brandAttentionList);
                    brandAttentionList.clear();
                }
                //memberBrandAttentionRepository.save(memberBrandAttention);
            }
        }
        System.err.println(brandAttentionList.size());
        memberBrandAttentionRepository.saveAll(brandAttentionList);
    }

    /**
     * 显示当前用户品牌关注列表
     */
    @Test
    public void listTest(){
        List<UmsMember> memberList = memberMapper.selectByExample(null);

        List<Map<String,Object>> mapList = new ArrayList<>();
        memberList.forEach(umsMember -> {
            Map<String,Object> map = new HashMap<>();
            List<MemberBrandAttention> brandAttentionList = memberBrandAttentionRepository.findByMemberId(umsMember.getId());
            map.put("memberId",umsMember.getId());
            map.put("brandAttentionList",brandAttentionList);
            mapList.add(map);
        });
        JSONArray jsonArray = (JSONArray) JSON.toJSON(mapList);
        System.out.println(JSON.toJSONString(mapList));
        FileWriter writer = new FileWriter("C:\\Users\\甄豪闯\\Desktop\\新建文件夹\\test.properties");
        //writer.writeLines(jsonArray);
        writer.write(JSON.toJSONString(mapList));
        //return memberBrandAttentionRepository.findByMemberId(member.getId(),pageable);
    }
}
