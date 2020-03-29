package com.gongpb.demo.junit.service.impl;

import com.gongpb.demo.junit.dao.MockDao;
import com.gongpb.demo.junit.vo.MockModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class MockServiceImpl {
    @Autowired
    private MockDao mockDao;

    public int count(MockModel model) {
        return mockDao.count(model);
    }
}
