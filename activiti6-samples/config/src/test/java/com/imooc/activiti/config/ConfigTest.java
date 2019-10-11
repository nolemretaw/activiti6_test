package com.imooc.activiti.config;

import lombok.extern.slf4j.Slf4j;
import org.activiti.engine.ProcessEngineConfiguration;
import org.junit.Test;

/**
 * @Author: HuTingrong
 * @Description:
 * @Date: Created in 16:21 2019/10/10
 * @Modified By:
 */
@Slf4j
public class ConfigTest {
    @Test
    public void testConfig1(){
        //依赖spring
        ProcessEngineConfiguration cfg = ProcessEngineConfiguration.createProcessEngineConfigurationFromResourceDefault();
        log.info("configuration={}",cfg);

    }

    @Test
    public void testConfig2(){
        //不依赖spring
        ProcessEngineConfiguration cfg = ProcessEngineConfiguration.createStandaloneProcessEngineConfiguration();
        log.info("configuration={}",cfg);
    }
}
