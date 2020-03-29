demo-junit
====


## 敏捷开发-TDD总结及其工具介绍

#### 一、TDD总结及其工具介绍

#### 二、PowerMock介绍

    PowerMockPowerMock基于Mockito开发，是一个扩展了其它如EasyMock等mock框架的、功能更加强大的框架。PowerMock使用一个自定义类加载器和字节码操作来模拟静态方法，构造函数，final类和方法，私有方法，去除静态初始化器等等。通过使用自定义的类加载器，简化采用的IDE或持续集成服务器不需要做任何改变。
    1、PowerMock环境配置：
    <!-- 单元测试工具  start-->
    <dependency>
        <groupId>junit</groupId>
        <artifactId>junit</artifactId>
        <version>4.12</version>
        <scope>test</scope>
    </dependency>
    <dependency>
        <groupId>org.mockito</groupId>
        <artifactId>mockito-core</artifactId>
        <version>2.10.0</version>
        <scope>test</scope>
    </dependency>
    <dependency>
        <groupId>org.powermock</groupId>
        <artifactId>powermock-api-mockito2</artifactId>
        <version>2.0.2</version>
        <scope>test</scope>
    </dependency>
    <dependency>
        <groupId>org.powermock</groupId>
        <artifactId>powermock-module-junit4</artifactId>
        <version>2.0.2</version>
        <scope>test</scope>
    </dependency>
    <!-- 单元测试工具  end—>
    Java单元测试类引入注解配置：
          @RunWith(PowerMockRunner.class) // 告诉JUnit使用PowerMockRunner进行测试
        @PrepareForTest({RandomUtil.class}) // 所有需要测试的类列在此处，适用于模拟final类或有final, private, static, native方法的类
        @PowerMockIgnore("javax.management.*") //为了解决使用powermock后，提示classloader错误
    2、最大的优点及时解决了其它mock不能支持的，可以mock接口、私有方法、静态方法、final方法：
    1）调用PowerMockito.mockStatic() 以mock静态类（使用PowerMockito.spy(class) 来mock制定的方法）
    2）final&私有方法：PownerMockClass spy = PowerMockito.spy(new PownerMockClass());
    3）mock：@Mock public GoodsDao goodsDao;
    2、Mock静态方法
    @Test
    @PrepareForTest({RandomUtil.class})
    public void nextInt(){
        PowerMockito.mockStatic(RandomUtil.class);
        PowerMockito.when(RandomUtil.nextInt(10)).thenReturn(1);
        assertEquals(1,RandomUtil.nextInt(10));
    }
    3、Mock私有方法
    @Test
    public void privateMethod() throws Exception {
        // 模拟 private的方法
        PownerMockClass spy = PowerMockito.spy(new PownerMockClass());
        PowerMockito.doReturn(3).when(spy, "privateMethod", 1);
        assertEquals(3, spy.testPrivateMethod(1));
        PowerMockito.verifyPrivate(spy, Mockito.times(1)).invoke("privateMethod", 1);
    }
    4、无返回值方法
    @Test
    public void voidMethod() {
        // 模拟 不执行void的方法
        PownerMockClass spy = PowerMockito.spy(new PownerMockClass());
        PowerMockito.doNothing().when(spy).voidMethod();
        spy.voidMethod();
    }
    5、无参数和返回值的静态方法
    @Test
    public void staticVoidMethod() throws Exception{
        // 模拟 不执行没参数的静态void的方法
        PowerMockito.mockStatic(PownerMockClass.class);
        PowerMockito.doNothing().when(PownerMockClass.class, "staticVoidMethod");
        PownerMockClass.staticVoidMethod();
    }
    6、有参数和返回值的静态方法
    @Test
    public void staticMethod() throws Exception{
        // 模拟 不执行带参数的静态void的方法
        PowerMockito.mockStatic(PownerMockClass.class);
        PowerMockito.doNothing().when(PownerMockClass.class, "staticMethod", "123");
        PownerMockClass.staticMethod("123");
        PowerMockito.doNothing().when(PownerMockClass.class, "staticMethod", Mockito.anyString());
        PownerMockClass.staticMethod("456");
    }
    7、如何为返回值为Void类型的静态方法抛出异常
    不是Private修饰的方法，则：
    PowerMockito.doThrow(new ArrayStoreException("Mock error")).when(StaticService.class);
    StaticService.executeMethod();
    final修饰的 类和方法 执行相同的操作:
    PowerMockito.doThrow(new ArrayStoreException("Mock error")).when(myFinalMock).myFinalMethod();
    8、接口mock方法
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
    public interface GoodsDao {
        String getGoodsBySkuId(RequestVo requestVo);
    }
    public interface GoodsAppService {
        String getGoods(long skuId);
        long getRequestVoId();
    }
    @Component
    public class GoodsAppServiceImpl implements GoodsAppService {
        @Resource
        private GoodsDao goodsDao;
        public String getGoods(long skuId){
            String res = goodsDao.getGoodsBySkuId(new RequestVo(skuId));
            return res;
        }
        public long getRequestVoId(){
            RequestVo requestVo = new RequestVo(1L);
            return requestVo.getId();
        }
    }
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
    }
    
    8、验证行为方法：
    1、验证私有方法调用行为：
        PowerMockito.verifyPrivate(spy, Mockito.times(1)).invoke("privateMethod", 1);
    2、验证静态方法行为：
            1）首先，调用 PowerMockito.verifyStatic(Static.class) 来开始验证行为；
            2）然后调用 Static.class 的静态方法进行验证。
    例如：
    //验证调用
    PowerMockito.verifyStatic(PownerMockClass.class, Mockito.times(1));
    PownerMockClass.staticMethod("456");
    
#### 三、Mockito使用方法:
   
    1、环境配置:
     <dependency>
              <groupId>org.mockito</groupId>
              <artifactId>mockito-all</artifactId>
              <version>2.10.0</version>
              <scope>test</scope>
      </dependency>
     <dependency>
            <groupId>junit</groupId>
            <artifactId>junit</artifactId>
            <version>4.11</version>
            <scope>test</scope>
     </dependency>
    代码引用class:
    import static org.mockito.Mockito.*;
    import static org.junit.Assert.*;
    简单例子说明mock方法:
    2、概念解析:
        1）Stub和Mock异同
             Stub和Mock都是模拟外部依赖、Stub是完全模拟一个外部依赖， 而Mock还可以用来判断测试通过还是失败 
                Mock不是真实的对象，它只是用类型的class创建了一个虚拟对象，并可以设置对象行为
                Spy是一个真实的对象，但它可以设置对象行为
                  InjectMocks创建这个类的对象并自动将标记@Mock、@Spy等注解的属性值注入到这个中
               使用方法
    @Test
    public void mockTest(){
        //mock一个List对象
        List mock = mock(List.class);
        //使用mock的对象
        mock.add(1);
        mock.clear();
        //验证add(1)和clear()行为是否发生
        verify(mock).add(1);
        verify(mock).clear();
    }
    @Test
    public void spyTest(){
        List list = new LinkedList();
        List spy = spy(list);
        //optionally, you can stub out some methods:
        when(spy.size()).thenReturn(100);
        //using the spy calls *real* methods
        spy.add("one");
        spy.add("two");
        //prints "one" - the first element of a list
        System.out.println(spy.get(0));
        //size() method was stubbed - 100 is printed
        System.out.println(spy.size());
        //optionally, you can verify
        verify(spy).add("one");
        verify(spy).add("two");
    }
    //初始化mock的代码
    MockitoAnnotations.initMocks(this);
    预设结果:
    1、when(mockList.get(anyInt())).thenAnswer(new CustomAnswer());
    2、//使用Answer来生成我们我们期望的返回
    when(mockList.get(anyInt())).thenAnswer(new Answer<Object>() {
                @Override
                public Object answer(InvocationOnMock invocation) throws Throwable {
                    Object[] args = invocation.getArguments();
                    return "hello world:"+args[0];
                }
    });
    3、mock对象使用Answer来对未预设的调用返回默认期望值
    @Test
    public void unstubbed_invocations(){
            //mock对象使用Answer来对未预设的调用返回默认期望值
            List mock = mock(List.class,new Answer() {
                @Override
                public Object answer(InvocationOnMock invocation) throws Throwable {
                    return 999;
                }
            });
            //下面的get(1)没有预设，通常情况下会返回NULL，但是使用了Answer改变了默认期望值
            assertEquals(999, mock.get(1));
            //下面的size()没有预设，通常情况下会返回0，但是使用了Answer改变了默认期望值
            assertEquals(999,mock.size());
        }
    断言验证:
    1、//预设当流关闭时抛出异常
    doThrow(new IOException()).when(outputStream).close();
    2、验证调用次数:
    @Test
    public void verifying_number_of_invocations(){
        List list = mock(List.class);
        list.add(1);
        list.add(2);
        list.add(2);
        list.add(3);
        list.add(3);
        //验证是否被调用一次，等效于下面的times(1)
        verify(list).add(1);
        verify(list,times(1)).add(1);
        //验证是否被调用2次
        verify(list,times(2)).add(2);
        //验证是否被调用3次
        verify(list,times(3)).add(3);
        //验证是否从未被调用过
        verify(list,never()).add(4);
        //验证至少调用一次
        verify(list,atLeastOnce()).add(1);
        //验证至少调用2次
        verify(list,atLeast(2)).add(2);
        //验证至多调用3次
        verify(list,atMost(2)).add(3);
    }
    3、验证代码执行顺序
    @Test
    public void verification_in_order(){
        List list = mock(List.class);
        List list2 = mock(List.class);
        list.add(1);
        list2.add("hello");
        list.add(2);
        list2.add("world");
        //将需要排序的mock对象放入InOrder
        InOrder inOrder = inOrder(list,list2);
        //下面的代码不能颠倒顺序，验证执行顺序
        inOrder.verify(list).add(1);
        inOrder.verify(list2).add("hello");
        inOrder.verify(list).add(2);
        inOrder.verify(list2).add("world");
    }
    4、验证零互动行为
    @Test
    public void verify_interaction(){
        List list = mock(List.class);
        List list2 = mock(List.class);
        List list3 = mock(List.class);
        list.add(1);
        verify(list).add(1);
        verify(list,never()).add(2);
        //验证零互动行为
        verifyZeroInteractions(list2,list3);
    }
    5、验证代码是否都已经验证完成
    @Test(expected = NoInteractionsWanted.class)
    public void find_redundant_interaction(){
        List list = mock(List.class);
        list.add(1);
        list.add(2);
        verify(list,times(2)).add(anyInt());
        //检查是否有未被验证的互动行为，因为add(1)和add(2)都会被上面的anyInt()验证到，所以下面的代码会通过
        verifyNoMoreInteractions(list);
        List list2 = mock(List.class);
        list2.add(1);
        list2.add(2);
        verify(list2).add(1);
        //检查是否有未被验证的互动行为，因为add(2)没有被验证，所以下面的代码会失败抛出异常
        verifyNoMoreInteractions(list2);
    }
    6、重置mock
    @Test
    public void reset_mock(){
        List list = mock(List.class);
        when(list.size()).thenReturn(10);
        list.add(1);
        assertEquals(10,list.size());
        //重置mock，清除所有的互动和预设
        reset(list);
        assertEquals(0,list.size());
    }
    
#### 四、采用Jacoco插件进行单元测试覆盖率统计
    
    Idea环境搭建问题:
    <!--单元测试覆盖率统计插件-->
    <dependency>
        <groupId>org.jacoco</groupId>
        <artifactId>jacoco-maven-plugin</artifactId>
        <version>0.8.2</version>
    </dependency>
    和plugins中增加插件:
    <!--单元测试覆盖率统计插件 start-->
    <plugin>
        <groupId>org.jacoco</groupId>
        <artifactId>jacoco-maven-plugin</artifactId>
        <version>0.8.2</version>
        <configuration>
            <destFile>target/coverage-reports/jacoco-unit.exec</destFile>
            <dataFile>target/coverage-reports/jacoco-unit.exec</dataFile>
            <includes>
                <include>**/service/**</include>
                <include>**/controller/**</include>
                <!--<include>**/service/impl/*.class</include>-->
            </includes>
            <!-- rules里面指定覆盖规则 -->
            <rules>
                <rule implementation="org.jacoco.maven.RuleConfiguration">
                    <element>BUNDLE</element>
                    <limits>　　
                        <!-- 指定方法覆盖到50% -->
                        <limit implementation="org.jacoco.report.check.Limit">
                            <counter>METHOD</counter>
                            <value>COVEREDRATIO</value>
                            <minimum>0.0</minimum>
                        </limit>
                        <!-- 指定分支覆盖到50% -->
                        <limit implementation="org.jacoco.report.check.Limit">
                            <counter>BRANCH</counter>
                            <value>COVEREDRATIO</value>
                            <minimum>0.0</minimum>
                        </limit>
                        <!-- 指定类覆盖到100%，不能遗失任何类 -->
                        <limit implementation="org.jacoco.report.check.Limit">
                            <counter>CLASS</counter>
                            <value>MISSEDCOUNT</value>
                            <maximum>0</maximum>
                        </limit>
                    </limits>
                </rule>
            </rules>
        </configuration>
        <executions>
            <execution>
                <id>jacoco-initialize</id>
                <goals>
                    <goal>prepare-agent</goal>
                </goals>
            </execution>
            <!--这个check:对代码进行检测，控制项目构建成功还是失败-->
            <execution>
                <id>check</id>
                <goals>
                    <goal>check</goal>
                </goals>
            </execution>
            <!--这个report:对代码进行检测，然后生成index.html在 target/site/index.html中可以查看检测的详细结果-->
            <execution>
                <id>jacoco-site</id>
                <phase>package</phase>
                <goals>
                    <goal>report</goal>
                </goals>
            </execution>
        </executions>
    </plugin>
    <!--单元测试覆盖率统计插件 end-->
    