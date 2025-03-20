package com.gongpb.demo.junit.vo;

import lombok.Data;

@Data
public class RequestVo {
    private long id;
    public RequestVo(long id){
        this.id=id;
    }
}
