package com.gongpb.demo.junit.dao;

import com.gongpb.demo.junit.vo.RequestVo;

public interface GoodsDao {
    String getGoodsBySkuId(RequestVo requestVo);
}