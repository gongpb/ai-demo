package com.gongpb.demo.junit.pownerMock;

import com.gongpb.demo.junit.util.RandomUtil;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PowerMockIgnore;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import static org.mockito.Mockito.*;
import static org.junit.Assert.*;
import static org.powermock.api.mockito.PowerMockito.verifyStatic;

@RunWith(PowerMockRunner.class) // 告诉JUnit使用PowerMockRunner进行测试
@PrepareForTest({PownerMockClass.class}) // 所有需要测试的类列在此处，适用于模拟final类或有final, private, static, native方法的类
@PowerMockIgnore("javax.management.*") //为了解决使用powermock后，提示classloader错误
public class PownerMockClassTest {

    @Test
    public void privateMethod() throws Exception {
        //mock private的方法
        PownerMockClass spy = PowerMockito.spy(new PownerMockClass());
        PowerMockito.doReturn(3).when(spy, "privateMethod", 1);
        assertEquals(3, spy.testPrivateMethod(1));
        PowerMockito.verifyPrivate(spy, Mockito.times(1)).invoke("privateMethod", 1);
    }

    @Test
    @PrepareForTest({RandomUtil.class})
    public void nextInt(){
        PowerMockito.mockStatic(RandomUtil.class);
        PowerMockito.when(RandomUtil.nextInt(10)).thenReturn(1);
        assertEquals(1,RandomUtil.nextInt(10));
    }

    @Test
    public void voidMethod() {
        // 模拟 不执行void的方法
        PownerMockClass spy = PowerMockito.spy(new PownerMockClass());
        PowerMockito.doNothing().when(spy).voidMethod();
        spy.voidMethod();
    }

    @Test
    public void staticVoidMethod() throws Exception{
        // 模拟 不执行没参数的静态void的方法
        PowerMockito.mockStatic(PownerMockClass.class);
        PowerMockito.doNothing().when(PownerMockClass.class, "staticVoidMethod");
        PownerMockClass.staticVoidMethod();
    }

    @Test
    public void staticMethod() throws Exception{
        // 模拟 不执行带参数的静态void的方法
        PowerMockito.mockStatic(PownerMockClass.class);
        PowerMockito.doNothing().when(PownerMockClass.class, "staticMethod", "123");
        PownerMockClass.staticMethod("123");

        PowerMockito.doNothing().when(PownerMockClass.class, "staticMethod", Mockito.anyString());
        PownerMockClass.staticMethod("456");

        //验证调用
        PowerMockito.verifyStatic(PownerMockClass.class, Mockito.times(1));
        PownerMockClass.staticMethod("456");
    }
}