package com.gongpb.demo.junit.service.impl;

import com.github.kevinsawicki.http.HttpRequest;
import com.gongpb.demo.junit.dao.GoodsDao;
import com.gongpb.demo.junit.service.GoodsAppService;
import com.gongpb.demo.junit.vo.RequestVo;
import org.springframework.stereotype.Component;
import javax.annotation.Resource;

@Component
public class GoodsAppServiceImpl implements GoodsAppService {
    @Resource
    private GoodsDao goodsDao;

    public String getGoods(long skuId){
        String res = goodsDao.getGoodsBySkuId(new RequestVo(skuId));
        return res;
    }
    public long getRequestVoId(){
        RequestVo requestVo = new RequestVo(1L);
        return requestVo.getId();
    }
}
