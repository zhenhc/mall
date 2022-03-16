package com.macro.mall.portal.controller;
import java.lang.reflect.Field;
import java.math.BigDecimal;

import cn.hutool.core.util.RandomUtil;
import com.macro.mall.mapper.OmsCartItemMapper;
import com.macro.mall.mapper.PmsProductMapper;
import com.macro.mall.mapper.UmsMemberMapper;
import com.macro.mall.model.OmsCartItem;
import com.macro.mall.model.OmsCartItemExample;
import com.macro.mall.model.PmsProduct;
import com.macro.mall.model.UmsMember;
import com.macro.mall.portal.MallPortalApplicationTests;
import com.macro.mall.portal.dao.OmsCartItemDao;
import com.macro.mall.portal.service.impl.OmsCartItemServiceImpl;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class OmsCartItemControllerTest extends MallPortalApplicationTests {

    private static final Logger LOGGER = LoggerFactory.getLogger(OmsCartItemControllerTest.class);
    @Autowired
    private UmsMemberMapper memberMapper;

    @Autowired
    private OmsCartItemMapper cartItemMapper;

    @Autowired
    private OmsCartItemDao cartItemDao;
    @Autowired
    private PmsProductMapper productMapper;

    /**
     * 添加商品到购物车测试
     */
    @Test
    public void addTest(){
        List<UmsMember> memberList = memberMapper.selectByExample(null);
        List<PmsProduct> productList = productMapper.selectByExample(null);
        List<OmsCartItem> cartItemList = new ArrayList<>();
        for (int i=0;i<=10;i++) {
            UmsMember currentMember = memberList.get(RandomUtil.randomInt(0, memberList.size()));
            PmsProduct product = productList.get(RandomUtil.randomInt(0, productList.size()));

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
            /*//通过对象的getClass()方法获得Class,然后getMethod()方法获得私有方法。
            Class<?> targetClass = Class.forName("com.macro.mall.portal.service.impl.OmsCartItemServiceImpl");
            OmsCartItemServiceImpl targetObject =(OmsCartItemServiceImpl) targetClass.newInstance();
            Method[] methods = targetClass.getDeclaredMethods();
            for (Method method :methods){
                Console.log(method.getName());
            }
            Method method = targetClass.getDeclaredMethod("getCartItem", OmsCartItem.class);
            //设置私有方法可进入
            method.setAccessible(true);
            //使用method.invoke()方法反射调用私有方法。
            OmsCartItem existCartItem = (OmsCartItem) method.invoke(targetObject, cartItem);*/
                OmsCartItem existCartItem = getCartItem(cartItem);
                if (existCartItem == null) {
                    cartItem.setCreateDate(new Date());
                    cartItemList.add(cartItem);
                    //cartItemMapper.insert(cartItem);
                } else {
                    cartItem.setModifyDate(new Date());
                    existCartItem.setQuantity(existCartItem.getQuantity() + cartItem.getQuantity());
                    cartItemMapper.updateByPrimaryKey(existCartItem);
                }
            } catch (Exception e) {
                LOGGER.warn("创建产品出错:{}", e.getMessage());
                throw new RuntimeException(e.getMessage());
            }
        }
        System.out.println(cartItemDao.insertList(cartItemList));
    }
    /**
     * 根据会员id,商品id和规格获取购物车中商品
     */
    private OmsCartItem getCartItem(OmsCartItem cartItem) {
        OmsCartItemExample example = new OmsCartItemExample();
        OmsCartItemExample.Criteria criteria = example.createCriteria().andMemberIdEqualTo(cartItem.getMemberId())
                .andProductIdEqualTo(cartItem.getProductId()).andDeleteStatusEqualTo(0);
        if (!StringUtils.isEmpty(cartItem.getProductSkuId())) {
            criteria.andProductSkuIdEqualTo(cartItem.getProductSkuId());
        }
        List<OmsCartItem> cartItemList = cartItemMapper.selectByExample(example);
        if (!CollectionUtils.isEmpty(cartItemList)) {
            return cartItemList.get(0);
        }
        return null;
    }

    @Test
    public void test(){
        Class<OmsCartItem> cartItemClass = OmsCartItem.class;
        Field[] fields = cartItemClass.getDeclaredFields();
        for (Field field : fields){
            String caseTo_ = com.macro.mall.common.util.StringUtils.camelCaseTo_(field.getName());
            System.out.println(caseTo_);
        }
    }
}
