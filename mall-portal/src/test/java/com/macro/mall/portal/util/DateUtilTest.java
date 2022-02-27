package com.macro.mall.portal.util;

import cn.hutool.core.date.DateUtil;
import org.junit.Test;

public class DateUtilTest {

    @Test
    public void getDate(){
        System.out.println(DateUtil.date());
        System.out.println(DateUtil.now());
        System.out.println(com.macro.mall.portal.util.DateUtil.getDate(DateUtil.date()));
        System.out.println(com.macro.mall.portal.util.DateUtil.getTime(DateUtil.date()));
    }
}
