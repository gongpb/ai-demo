package com.gongpb.demo.junit.service.impl;

import com.gongpb.demo.junit.base.BaseJunit4;
import com.gongpb.demo.junit.dao.GoodsDao;
import com.gongpb.demo.junit.service.GoodsAppService;
import com.gongpb.demo.junit.vo.RequestVo;
import org.junit.Before;
import org.junit.Test;
import org.mockito.*;

import static org.mockito.Mockito.*;

import org.mockito.invocation.InvocationOnMock;
import java.util.*;
import org.springframework.beans.factory.annotation.Autowired;
import static org.junit.Assert.*;

public class GoodsAppServiceImplMockitoTest {
    /**
     * 单元测试mock接口
     * 两种引入方式：
     * 1、写接口类型，并赋值给实现类值
     *    @InjectMocks
     *    GoodsAppService goodsAppServiceImpl= new GoodsAppServiceImpl();
     * 2、直接声明实现类类型
     *    @InjectMocks
     *    GoodsAppServiceImpl goodsAppServiceImpl;
     *
     * 测试的方法里面依赖接口，mock方法：
     * 1、声明为类局部变量
     *    @Mock
     *    private GoodsDao goodsDao;
     * 2、写期望测试数据，可使用Mockito或者PownerMock
     *    1)PownerMock：需要声明@RunWith(PowerMockRunner.class) // 告诉JUnit使用PowerMockRunner进行测试
     *      PowerMockito.when(goodsDao,"getGoodsBySkuId",Mockito.any()).thenReturn("mockTest");
     *      或者PowerMockito.when(goodsDao.getGoodsBySkuId(Mockito.any())).thenReturn("mockTest");
     *    2)Mockito：import static org.mockito.Mockito.*; 然后
     *    when(goodsDao.getGoodsBySkuId(Mockito.any())).thenAnswer(
     *                 (InvocationOnMock invocationOnMock) -> {
     *                     Object arg = invocationOnMock.getArguments()[0];
     *                     if (null == arg || arg.equals(null)) {
     *                         return "param is null";
     *                     }
     *                     return "requestVo";
     *                 });
     */
    @InjectMocks//创建一个实例，其余用@Mock（或@Spy）注解创建的mock将被注入到用该实例中
    GoodsAppService goodsAppServiceImpl= new GoodsAppServiceImpl();
    @Mock
    private GoodsDao goodsDao;

    @Before
    public void initMock(){
        //重要：初始化Mock对象
        MockitoAnnotations.initMocks(this);
    }

    /**
     * Mockit 接口依赖测试demo
     * @throws Exception
     */
    @Test
    public void getGoods() throws Exception{
        when(goodsDao.getGoodsBySkuId(Mockito.any())).thenAnswer(
                (InvocationOnMock invocationOnMock) -> {
                    Object arg = invocationOnMock.getArguments()[0];
                    if (null == arg || arg.equals(null)) {
                        return "param is null";
                    }
                    return "requestVo";
                });
        String res = goodsAppServiceImpl.getGoods(1L);
        assertEquals("requestVo",res);


    }

    @Test
    public void getGoodsHttp() throws Exception{
        when(goodsDao.getGoodsBySkuId(Mockito.any())).thenAnswer(
                (InvocationOnMock invocationOnMock) -> {
                    Object arg = invocationOnMock.getArguments()[0];
                    if (null == arg || arg.equals(null)) {
                        return "param is null";
                    }
                    return "requestVo";
                });

        String res = goodsAppServiceImpl.getGoods(1L);

        assertEquals("requestVo",res);
        verify(goodsDao,times(1)).getGoodsBySkuId(Mockito.any());
    }

}