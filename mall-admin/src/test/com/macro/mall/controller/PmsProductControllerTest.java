package com.macro.mall.controller;
import java.math.BigDecimal;

import cn.hutool.core.util.RandomUtil;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.google.common.collect.Lists;
import java.util.Date;

import com.macro.mall.MallAdminApplicationTest;
import com.macro.mall.common.api.CommonResult;
import com.macro.mall.dto.PmsProductAttributeCategoryItem;
import com.macro.mall.dto.PmsProductCategoryWithChildrenItem;
import com.macro.mall.dto.PmsProductParam;
import com.macro.mall.model.*;
import com.macro.mall.service.PmsBrandService;
import com.macro.mall.service.PmsProductAttributeCategoryService;
import com.macro.mall.service.PmsProductCategoryService;
import com.macro.mall.service.PmsProductService;
import org.apache.commons.lang3.RandomStringUtils;
import org.checkerframework.checker.signature.qual.BinaryNameForNonArray;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.List;

public class PmsProductControllerTest extends MallAdminApplicationTest {
    @Autowired
    private PmsProductService productService;

    @Autowired
    private PmsBrandService brandService;

    @Autowired
    private PmsProductCategoryService productCategoryService;

    @Autowired
    private PmsProductAttributeCategoryService attributeCategoryService;
    @Autowired
    private PmsProductController productController;
    @Test
    public void updateStatusTest(){
        List<Long> longList = new ArrayList<>();
        for (int i=1;i<=24;i++){
            longList.add(Long.valueOf(i));
        }
        int count = productService.updateDeleteStatus(longList, 0);

    }

    @Test
    public void updatePublishTest(){
        List<Long> list = new ArrayList<>();
        for (int i=1;i<=24;i++){
            list.add(Long.valueOf(i));
        }
        productService.updatePublishStatus(list,1);
    }

    @Test
    public void productCategoryListTest(){
        List<PmsProductCategoryWithChildrenItem> productCategoryList = productCategoryService.listWithChildren();

        JSONArray jsonArray = (JSONArray) JSON.toJSON(productCategoryList);
        System.out.println(jsonArray);
    }

    @Test
    public void productListTest(){
        List<PmsProduct> productList = productService.list(null);

        JSONArray jsonArray = (JSONArray) JSON.toJSON(productList);
        System.out.println(jsonArray);
    }
    @Test
    public void serviceIdsTest(){
        String[] serviceIds = {"1","2","3","1,2","2,3","1,3","1,2,3"};
        for (int i=0;i<=10;i++){
            System.out.println(serviceIds[RandomUtil.randomInt(0,serviceIds.length)]);
        }
    }
    @Test
    public void createTest(){
        List<PmsProductParam> productParamList = new ArrayList<>();
        List<PmsBrand> brandList = brandService.listAllBrand();
        List<PmsProductCategoryWithChildrenItem> withChildrenItemList = productCategoryService.listWithChildren();
        List<PmsProduct> productList = productService.list(null);
        List<PmsProductAttributeCategoryItem> categoryItemList = attributeCategoryService.getListWithAttr();
        String[] unit = {"件","个","副","包","袋","盒"};
        String[] serviceIds = {"1","2","3","1,2","2,3","1,3","1,2,3"};
        for (int i=0;i<=100;i++) {
            PmsBrand pmsBrand = brandList.get(RandomUtil.randomInt(0,brandList.size()));
            List<PmsProductCategory> productCategoryList = withChildrenItemList.get(RandomUtil.randomInt(0,withChildrenItemList.size())).getChildren();
            PmsProductCategory productCategory = productCategoryList.get(RandomUtil.randomInt(0,productCategoryList.size()));
            PmsProduct product = productList.get(RandomUtil.randomInt(0,productList.size()));
            PmsProductAttributeCategoryItem attributeCategoryItem = categoryItemList.get(RandomUtil.randomInt(0,categoryItemList.size()));
            BigDecimal price = new BigDecimal(RandomUtil.randomInt(10,10000));
            BigDecimal originPrice = price.add(new BigDecimal(RandomUtil.randomInt(10,500)));
            List<PmsProductLadder> productLadderList = new ArrayList<>();
            //最多3条
            for (int j=0;j<=RandomUtil.randomInt(0,3);j++){
                BigDecimal discount = RandomUtil.randomBigDecimal(new BigDecimal(10),new BigDecimal(100));
                BigDecimal postPrice = product.getPrice().subtract(discount);
                PmsProductLadder productLadder = new PmsProductLadder();
                productLadder.setCount(RandomUtil.randomInt(5,10));
                productLadder.setPrice(postPrice);
                productLadder.setDiscount(discount);
                productLadderList.add(productLadder);
            }
            List<PmsProductFullReduction> fullReductionList = new ArrayList<>();
            for (int j=0;j<=RandomUtil.randomInt(0,3);j++){
                PmsProductFullReduction fullReduction = new PmsProductFullReduction();
                BigDecimal fullPrice = RandomUtil.randomBigDecimal(new BigDecimal(100),new BigDecimal(1000));
                BigDecimal reducePrice = RandomUtil.randomBigDecimal(new BigDecimal(10),new BigDecimal(100));
                fullReduction.setFullPrice(fullPrice);
                fullReduction.setReducePrice(reducePrice);
                fullReductionList.add(fullReduction);
            }

            PmsProductParam productParam = new PmsProductParam();
            productParam.setProductLadderList(productLadderList);
            productParam.setProductFullReductionList(fullReductionList);
            productParam.setMemberPriceList(Lists.newArrayList());
            productParam.setSkuStockList(Lists.newArrayList());
            productParam.setProductAttributeValueList(Lists.newArrayList());
            productParam.setSubjectProductRelationList(Lists.newArrayList());
            productParam.setPrefrenceAreaProductRelationList(Lists.newArrayList());
            productParam.setId(0L);
            //品牌id
            productParam.setBrandId(pmsBrand.getId());

            productParam.setProductCategoryId(productCategory.getId());
            //feight_template_id默认为0
            productParam.setFeightTemplateId(0L);
            productParam.setProductAttributeCategoryId(attributeCategoryItem.getId());
            //商品名称
            productParam.setName(RandomStringUtils.randomAlphabetic(5,10));

            productParam.setPic(product.getPic());
            //商品货号
            productParam.setProductSn("No"+RandomStringUtils.randomNumeric(5));
            productParam.setDeleteStatus(0);
            //发布状态：0-下架，1-上架
            productParam.setPublishStatus(RandomUtil.randomInt(0,2));
            //新品状态:0->不是新品；1->新品
            productParam.setNewStatus(RandomUtil.randomInt(0,2));
            //推荐状态；0->不推荐；1->推荐
            productParam.setRecommandStatus(RandomUtil.randomInt(0,2));
            //审核状态：0->未审核；1->审核通过
            productParam.setVerifyStatus(RandomUtil.randomInt(0,2));
            //排序：0~100
            productParam.setSort(RandomUtil.randomInt(0,100));
            //销量当前都是0
            productParam.setSale(0);
            //商品售价
            productParam.setPrice(price);
            //促销价格为null
            productParam.setPromotionPrice(null);
            //赠送的成长值：20~100
            productParam.setGiftGrowth(RandomUtil.randomInt(20,100));
            //赠送的积分：20~100
            productParam.setGiftPoint(RandomUtil.randomInt(20,100));
            productParam.setUsePointLimit(0);
            //副标题
            productParam.setSubTitle(RandomStringUtils.randomAlphabetic(5,8));
            productParam.setOriginalPrice(originPrice);
            //库存
            productParam.setStock(RandomUtil.randomInt(100,1000));
            //库存预警值
            productParam.setLowStock(RandomUtil.randomInt(10,100));
            //单位
            productParam.setUnit(unit[RandomUtil.randomInt(0,unit.length)]);
            //商品重量
            productParam.setWeight(new BigDecimal(RandomUtil.randomInt(10,1000)));
            //是否为预告商品：0->不是；1->是
            productParam.setPreviewStatus(RandomUtil.randomInt(0,2));
            //以逗号分割的产品服务：1->无忧退货；2->快速退款；3->免费包邮
            productParam.setServiceIds(serviceIds[RandomUtil.randomInt(0,serviceIds.length)]);
            productParam.setKeywords(RandomStringUtils.randomAlphabetic(5,8));
            productParam.setNote(RandomStringUtils.randomAlphabetic(10,20));
            productParam.setAlbumPics(null);
            productParam.setDetailTitle(RandomStringUtils.randomAlphabetic(10,20));
            productParam.setPromotionStartTime(new Date());
            productParam.setPromotionEndTime(new Date());
            //活动限购数量
            productParam.setPromotionPerLimit(RandomUtil.randomInt(2,5));
            //促销类型：0->没有促销使用原价;1->使用促销价；2->使用会员价；3->使用阶梯价格；4->使用满减价格；5->限时购
            productParam.setPromotionType(RandomUtil.randomInt(0,6));

            productParam.setBrandName(pmsBrand.getName());
            productParam.setProductCategoryName(productCategory.getName());
            productParam.setDescription(RandomStringUtils.randomAlphabetic(20,50));
            productParam.setDetailDesc(RandomStringUtils.randomAlphabetic(50,100));
            productParam.setDetailHtml(null);
            productParam.setDetailMobileHtml(null);
            productParamList.add(productParam);
            int count = productService.create(productParam);
        }
        JSONArray jsonArray = (JSONArray) JSON.toJSON(productParamList);
        System.out.println(jsonArray);
    }
}
