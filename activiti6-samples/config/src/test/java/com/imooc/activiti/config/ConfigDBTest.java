package com.imooc.activiti.config;

import lombok.extern.slf4j.Slf4j;
import org.activiti.engine.ProcessEngine;
import org.activiti.engine.ProcessEngineConfiguration;
import org.junit.Test;

/**
 * @Author: HuTingrong
 * @Description:数据库配置
 * @Date: Created in 18:05 2019/10/10
 * @Modified By:
 */
@Slf4j
public class ConfigDBTest {
    @Test
    public void testConfig1(){
        ProcessEngineConfiguration cfg = ProcessEngineConfiguration
                .createProcessEngineConfigurationFromResourceDefault();
        log.info("configuration={}",cfg);
        ProcessEngine processEngine = cfg.buildProcessEngine();
        log.info("获取流程引擎{}",processEngine.getName());
        processEngine.close();
    }
    @Test
    public void testConfig2(){
        ProcessEngineConfiguration cfg = ProcessEngineConfiguration
                .createProcessEngineConfigurationFromResource("activiti_druid.cfg.xml");
        log.info("configuration={}",cfg);
        ProcessEngine processEngine = cfg.buildProcessEngine();
        log.info("获取流程引擎{}",processEngine.getName());
        processEngine.close();
    }
}
