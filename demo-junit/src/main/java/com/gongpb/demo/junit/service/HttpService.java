package com.gongpb.demo.junit.service;

import com.gongpb.demo.junit.service.vo.RequestVo;

public interface HttpService {
    String getBodyFromHttp(RequestVo requestVo);
}
