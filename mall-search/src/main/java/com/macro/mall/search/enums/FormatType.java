package com.macro.mall.search.enums;

public enum FormatType {
    JSON("json"),
    TABLE("v");
    //私有属性
    private final String info;

    //有参构造方法
    FormatType(String info) {
        this.info = info;
    }
    //getter方法
    public String getInfo(){
        return info;
    }
}
