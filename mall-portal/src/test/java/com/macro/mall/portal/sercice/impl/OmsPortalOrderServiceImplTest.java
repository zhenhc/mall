package com.macro.mall.portal.sercice.impl;

import cn.hutool.core.util.RandomUtil;
import com.macro.mall.common.service.RedisService;
import com.macro.mall.model.OmsOrder;
import com.macro.mall.portal.MallPortalApplicationTests;
import com.macro.mall.portal.service.impl.OmsPortalOrderServiceImpl;
import com.macro.mall.security.util.SpringUtil;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationContext;

import javax.annotation.Resource;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.text.SimpleDateFormat;
import java.util.Date;

public class OmsPortalOrderServiceImplTest extends MallPortalApplicationTests {

    @Autowired
    private RedisService redisService;
    @Value("${redis.key.orderId}")
    private String REDIS_KEY_ORDER_ID;
    @Value("${redis.database}")
    private String REDIS_DATABASE;

    @Test
    public void test(){
        OmsOrder omsOrder = new OmsOrder();
        omsOrder.setSourceType(1);
        omsOrder.setPayType(2);
        try {
            Class<?> forName = Class.forName("com.macro.mall.portal.service.impl.OmsPortalOrderServiceImpl");

            ApplicationContext applicationContext = SpringUtil.getApplicationContext();
            Method[] methods = forName.getDeclaredMethods();
            for (Method method: methods){
                System.out.println(method.getName());
            }
            Method method = forName.getDeclaredMethod("generateOrderSn", OmsOrder.class);
            method.setAccessible(true);
            String str =(String) method.invoke(applicationContext.getBean(forName), omsOrder);
            System.out.println(str);
        }catch (Exception e) {
            Throwable cause = e.getCause();
            cause.printStackTrace();
        }
    }

    @Test
    public void generateOrderSnTest(){
        OmsOrder omsOrder = new OmsOrder();
        for (int i=0;i<10;i++) {
            //订单来源：0->PC订单；1->app订单
            omsOrder.setSourceType(RandomUtil.randomInt(0,2));
            //支付方式：0->未支付；1->支付宝；2->微信
            omsOrder.setPayType(RandomUtil.randomInt(0,3));
            String s = this.generateOrderSn(omsOrder);
            System.out.println(s);
        }
    }
    /**
     * 生成18位订单编号:8位日期+2位平台号码+2位支付方式+6位以上自增id
     */
    private String generateOrderSn(OmsOrder order) {
        StringBuilder sb = new StringBuilder();
        String date = new SimpleDateFormat("yyyyMMdd").format(new Date());
        String key = REDIS_DATABASE+":"+ REDIS_KEY_ORDER_ID + date;
        Long increment = redisService.incr(key, 1);
        sb.append(date);
        sb.append(String.format("%02d", order.getSourceType()));
        sb.append(String.format("%02d", order.getPayType()));
        String incrementStr = increment.toString();
        if (incrementStr.length() <= 6) {
            sb.append(String.format("%06d", increment));
        } else {
            sb.append(incrementStr);
        }
        return sb.toString();
    }

    public void test1(){
        /*try {
            //从ApplicationContext中取出已创建好的的对象
            //不可直接反射创建serviceimpi对象，因为反射创建出来的对象无法实例化dao接口
            ApplicationContext applicationContext = SpringUtil.getApplicationContext();
            //反射创建serviceimpi实体对象，和实体类
            Class<?> ServiceImplType = Class.forName(GlobalParams.REF_SERVICE+className+"ServiceImpl");
            Class<?> entityType = Class.forName(GlobalParams.REF_ENTITY+className);
            //反射设置方法参数。
            Method method = ServiceImplType.getDeclaredMethod("Insert",entityType);
            //在ApplicationContext中根据class取出已实例化的bean
            method.invoke(applicationContext.getBean(ServiceImplType),className);

        } catch (ClassNotFoundException | IllegalAccessException | InvocationTargetException | NoSuchMethodException e) {
            e.printStackTrace();
            return GlobalResult.resOk("个性化表单数据插入失败");
        }*/
    }
}