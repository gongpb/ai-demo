package com.gongpb.demo.junit.service.impl;

import com.gongpb.demo.junit.base.BaseJunit4;
import com.gongpb.demo.junit.dao.GoodsDao;
import com.gongpb.demo.junit.util.RandomUtil;
import com.gongpb.demo.junit.vo.RequestVo;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.internal.util.MockUtil;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PowerMockIgnore;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.util.ReflectionTestUtils;

import java.lang.reflect.Method;
import static org.junit.Assert.*;

@RunWith(PowerMockRunner.class) // 告诉JUnit使用PowerMockRunner进行测试
@PrepareForTest({RandomUtil.class}) // 所有需要测试的类列在此处，适用于模拟final类或有final, private, static, native方法的类
@PowerMockIgnore("javax.management.*") //为了解决使用powermock后，提示classloader错误
public class GoodsAppServiceImplTest{
    @InjectMocks
    GoodsAppServiceImpl goodsAppServiceImpl;
    @Mock
    public GoodsDao goodsDao;

    /**
     * PownerMock 接口依赖测试demo
     * @throws Exception
     */
    @Test
    public void interfaceTest() throws Exception{

//        PowerMockito.when(goodsDao,"getGoodsBySkuId",Mockito.any()).thenReturn("mockTest");
        PowerMockito.when(goodsDao.getGoodsBySkuId(Mockito.any())).thenReturn("mockTest");

        //使用反射的方式调用
        Method method = PowerMockito.method(GoodsAppServiceImpl.class, "getGoods");
        String res1 = (String) method.invoke(goodsAppServiceImpl,1L);
        assertEquals("mockTest", res1);

        //直接调用
        String res2 = goodsAppServiceImpl.getGoods(1L);
        assertEquals("mockTest", res2);
    }

    @Test
    @PrepareForTest({RequestVo.class})
    public void getRequestVoId()throws Exception{
        RequestVo requestVo = new RequestVo(2L);
        PowerMockito.whenNew(RequestVo.class).withArguments(1L).thenReturn(requestVo);
        assertEquals(1L,goodsAppServiceImpl.getRequestVoId());
    }
}