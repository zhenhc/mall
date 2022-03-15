package com.macro.mall.portal.controller;
import java.math.BigDecimal;

import cn.hutool.core.util.RandomUtil;
import com.macro.mall.mapper.OmsCartItemMapper;
import com.macro.mall.mapper.PmsProductMapper;
import com.macro.mall.mapper.UmsMemberMapper;
import com.macro.mall.model.OmsCartItem;
import com.macro.mall.model.PmsProduct;
import com.macro.mall.model.UmsMember;
import com.macro.mall.portal.MallPortalApplicationTests;
import com.macro.mall.portal.service.impl.OmsCartItemServiceImpl;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.lang.reflect.Method;
import java.util.Date;
import java.util.List;

public class OmsCartItemControllerTest extends MallPortalApplicationTests {

    private static final Logger LOGGER = LoggerFactory.getLogger(OmsCartItemControllerTest.class);
    @Autowired
    private UmsMemberMapper memberMapper;

    @Autowired
    private OmsCartItemServiceImpl cartItemServiceImpl;

    @Autowired
    private OmsCartItemMapper cartItemMapper;

    @Autowired
    private PmsProductMapper productMapper;

    /**
     * 添加商品到购物车测试
     */
    @Test
    public void addTest(){
        List<UmsMember> memberList = memberMapper.selectByExample(null);
        List<PmsProduct> productList = productMapper.selectByExample(null);

        UmsMember currentMember = memberList.get(RandomUtil.randomInt(0, memberList.size()));
        PmsProduct product = productList.get(RandomUtil.randomInt(0,productList.size()));

        OmsCartItem cartItem = new OmsCartItem();
        cartItem.setId(0L);
        cartItem.setProductId(product.getId());
        cartItem.setProductSkuId(0L);
        cartItem.setQuantity(0);
        cartItem.setPrice(product.getPrice());
        cartItem.setProductPic(product.getPic());
        cartItem.setProductName(product.getName());
        cartItem.setProductSubTitle(product.getSubTitle());
        cartItem.setProductSkuCode("");
        cartItem.setCreateDate(new Date());
        cartItem.setModifyDate(new Date());
        cartItem.setProductCategoryId(product.getProductCategoryId());
        cartItem.setProductBrand(product.getBrandName());
        cartItem.setProductSn(product.getProductSn());
        cartItem.setProductAttr("");
        cartItem.setMemberId(currentMember.getId());
        cartItem.setMemberNickname(currentMember.getNickname());
        cartItem.setDeleteStatus(0);
        try {
            //通过对象的getClass()方法获得Class,然后getMethod()方法获得私有方法。
            Method method = cartItemServiceImpl.getClass().getMethod("getCartItem", OmsCartItem.class);
            //设置私有方法可进入
            method.setAccessible(true);
            //使用method.invoke()方法反射调用私有方法。
            OmsCartItem existCartItem = (OmsCartItem) method.invoke(cartItemServiceImpl, cartItem);
            if (existCartItem == null){
                cartItem.setCreateDate(new Date());
                cartItemMapper.insert(cartItem);
            }else {
                cartItem.setModifyDate(new Date());
                existCartItem.setQuantity(existCartItem.getQuantity() + cartItem.getQuantity());
                cartItemMapper.updateByPrimaryKey(existCartItem);
            }
        } catch (Exception e) {
            LOGGER.warn("创建产品出错:{}", e.getMessage());
            throw new RuntimeException(e.getMessage());
        }

/*        OmsCartItem existCartItem = cartItemService.getCartItem(cartItem);
        if (existCartItem == null) {
            cartItem.setCreateDate(new Date());
            cartItemMapper.insert(cartItem);
        } else {
            cartItem.setModifyDate(new Date());
            existCartItem.setQuantity(existCartItem.getQuantity() + cartItem.getQuantity());
            cartItemMapper.updateByPrimaryKey(existCartItem);
        }*/
    }
}
