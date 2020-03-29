package com.gongpb.demo.junit.service.impl;


import com.github.kevinsawicki.http.HttpRequest;
import com.gongpb.demo.junit.service.HttpService;
import com.gongpb.demo.junit.service.vo.RequestVo;
import org.springframework.stereotype.Component;

@Component
public class HttpServiceImpl implements HttpService {

    public String getBodyFromHttp(RequestVo requestVo){
        HttpRequest request = HttpRequest.get("https://item.jd.com/26206639017.html");
        String src = request.body();
        return src;
    }
}