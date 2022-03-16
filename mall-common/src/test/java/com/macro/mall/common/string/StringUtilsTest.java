package com.macro.mall.common.string;

import com.macro.mall.common.util.StringUtils;
import org.junit.Test;

public class StringUtilsTest {

    @Test
    public void test1(){
        String s = "product_id";
        String s1 = s.replace("_", "");
        System.out.println(s1);
    }

    /**
     * 数据库下划线字段名 转为 驼峰命名法
     */
    @Test
    public void test(){
        String s = "id, product_id, product_sku_id, member_id, quantity, price, product_pic, product_name, \n" +
                "    product_sub_title, product_sku_code, member_nickname, create_date, modify_date, delete_status, \n" +
                "    product_category_id, product_brand, product_sn, product_attr";
        String[] strings = s.split(",");

        for (String str : strings){
            //System.out.println(str.trim());
/*            int i = str.indexOf("_");
            int j = str.lastIndexOf("_");
            if (i>=0) {
                str =str.substring(0,i+1) + str.substring(i+1, i+2).toUpperCase() + str.substring(i+2);
            }
            if (j>=0 && j!=i){
                str= str.substring(0,j+1) + str.substring(j+1,j+2).toUpperCase()+ str.substring(j+2);
            }
            String replace = str.replace("_", "");*/
            String camelCase = StringUtils.toCamelCase(str);
            System.out.println("#{item."+camelCase.trim()+"},");
            //str.replace("_","");
        }
    }



}
