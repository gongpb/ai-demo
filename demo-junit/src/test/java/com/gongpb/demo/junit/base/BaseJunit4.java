package com.gongpb.demo.junit.base;

import org.junit.runner.RunWith;
import org.powermock.core.classloader.annotations.PowerMockIgnore;
import org.powermock.modules.junit4.PowerMockRunner;
import org.powermock.modules.junit4.PowerMockRunnerDelegate;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.transaction.annotation.Transactional;

@PowerMockRunnerDelegate(SpringJUnit4ClassRunner.class)  //委派给SpringJUnit4ClassRunner
//@ContextConfiguration(locations={"classpath:spring/*.xml"}) //加载配置文件
@PowerMockIgnore({"javax.management.*"}) //忽略一些mock异常
public class BaseJunit4 {
}
