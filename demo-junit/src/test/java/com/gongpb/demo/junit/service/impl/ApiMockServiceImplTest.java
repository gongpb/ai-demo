package com.gongpb.demo.junit.service.impl;

import com.gongpb.demo.junit.base.BaseJunit4;
import com.gongpb.demo.junit.service.HttpService;
import com.gongpb.demo.junit.service.vo.RequestVo;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.util.ReflectionTestUtils;

import javax.annotation.Resource;
import java.lang.reflect.Method;
import static org.junit.Assert.*;

@RunWith(PowerMockRunner.class)
//@PrepareForTest({HttpService.class})
public class ApiMockServiceImplTest extends BaseJunit4 {
    @InjectMocks @Autowired
    ApiMockServiceImpl apiMockServiceImpl;
    @Resource
    private HttpServiceImpl httpServiceImpl;

    @Mock
    public HttpService httpService;

    @Before
    public void initMock(){
        //依赖注入相应的action
        httpService = PowerMockito.mock(HttpServiceImpl.class);
        ReflectionTestUtils.setField(apiMockServiceImpl, "httpServiceImpl", httpService);
    }

    @Test
    public void getBody() throws Exception{

        RequestVo requestVo = new RequestVo();
        requestVo.setId(100L);

        PowerMockito.whenNew(RequestVo.class).withAnyArguments().thenReturn(requestVo);
        PowerMockito.when(httpService,"getBodyFromHttp", Mockito.any()).thenReturn("mockTest");

        String res = httpService.getBodyFromHttp(requestVo);
        assertEquals("mockTest", res);

        Method method = PowerMockito.method(ApiMockServiceImpl.class, "getBody");
        String res1 = (String) method.invoke(apiMockServiceImpl);
        assertEquals("mockTest", res1);

        String res2 = apiMockServiceImpl.getBody();
        assertEquals("mockTest", res2);
    }
}