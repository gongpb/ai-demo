package com.gongpb.demo.junit.service.impl;

import com.gongpb.demo.junit.base.BaseJunit4;
import com.gongpb.demo.junit.dao.MockDao;
import com.gongpb.demo.junit.vo.MockModel;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.internal.util.MockUtil;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PowerMockIgnore;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import static org.junit.Assert.*;

@RunWith(PowerMockRunner.class) // 告诉JUnit使用PowerMockRunner进行测试
public class MockServiceImplTest{

    @InjectMocks
    private MockServiceImpl mockService;
    @Mock
    private MockDao mockDao;
    @Test
    public void count() {
        MockModel model = new MockModel();
        PowerMockito.when(mockDao.count(model)).thenReturn(2);

        assertEquals(2, mockService.count(model));
    }
}