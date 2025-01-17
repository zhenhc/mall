package com.macro.mall.common.util;

/**
 * Author: zhenhc
 * Description:
 */
public class StringUtils {


    /**
     * 下划线命名法转化为驼峰命名法
     * @param str
     * @return
     */
    public static String toCamelCase(String str){
        String b = "";
        for (int i=0;i<str.length();i++){
            char temp = str.charAt(i);
            //排除第一个字符为“_”的情况
            if (temp == '_' && i!=0){
                //b+=temp;
                char c = str.charAt(i + 1);
                c = (char) (c-32);
                b+=c;
                i++;
            }else {
                b += temp;
            }
        }
        return b;
    }

    /**
     * 驼峰命名法转化为下划线命名法
     * @param str
     * @return
     */
    public static String camelCaseTo_(String str){
        String b="";
        for(int i=0;i<str.length();i++){
            char temp=str.charAt(i);
            if(temp>='A'&&temp<='Z'){
                b+='_';
                temp=(char)(temp+32);
            }
            b+=temp;
        }
        return b;
    }
}
