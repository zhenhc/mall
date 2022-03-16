package com.macro.mall.portal.dao;

import com.macro.mall.model.OmsCartItem;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface OmsCartItemDao {

    /**
     * @param cartItemList
     * @return 新增的数量
     */
    int insertList(@Param("list") List<OmsCartItem> cartItemList);
}
