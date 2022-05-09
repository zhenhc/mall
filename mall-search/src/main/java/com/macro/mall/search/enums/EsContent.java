package com.macro.mall.search.enums;

/**
 * 查询es内容类型
 */
public enum EsContent {
    INDICES("/_cat/indices"),
    HEALTH("/_cat/health"),
    NODES("/_cat/nodes");

    private final String info;

    EsContent(String info){
        this.info = info;
    }

    public String getInfo(){
        return info;
    }
}
