package com.macro.mall.common.string;

public class T0114 {
    public static void main(String[] args){
        String a="AdEFgHi";
        String b = "product_sku_id";
        String c = "productSkuId";
        System.out.println("原字符串："+a);
        System.out.println("转换为小写字母："+ToLowerCase(a));
        System.out.println("转换为大写字母："+ToUpperCase(a));
        System.out.println(toCamelCase(b));
        System.out.println(camelCaseTo_(c));
    }

    /**
     * 下划线命名法转化为驼峰命名法
     * @param str
     * @return
     */
    public static String toCamelCase(String str){
        String b = "";
        for (int i=0;i<str.length();i++){
            char temp = str.charAt(i);
            if (temp == '_'){
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
    //将字符串中的小写字母转换为大写字母
    public static String ToUpperCase(String str) {
        String b="";
        for(int i=0;i<str.length();i++){
            char temp=str.charAt(i);
            if(temp>='a'&&temp<='z'){
                temp=(char)(temp-32);
            }
            b+=temp;
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
    //将字符串中的大写字母转换为小写字母
    public static String ToLowerCase(String str) {
        String b="";
        for(int i=0;i<str.length();i++){
            char temp=str.charAt(i);
            if(temp>='A'&&temp<='Z'){
                temp=(char)(temp+32);
            }
            b+=temp;
        }
        return b;
    }
}
