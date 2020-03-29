package com.gongpb.demo.junit.service.impl;


import com.gongpb.demo.junit.service.HttpService;
import com.gongpb.demo.junit.service.vo.RequestVo;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

@Component
public class ApiMockServiceImpl {

    @Resource
    private HttpService httpServiceImpl;

    public String getBody(){
        String res = httpServiceImpl.getBodyFromHttp(new RequestVo());
        return res;
    }
}
