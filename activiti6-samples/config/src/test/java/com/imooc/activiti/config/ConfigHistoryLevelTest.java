package com.imooc.activiti.config;

import lombok.extern.slf4j.Slf4j;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.task.Task;
import org.activiti.engine.test.ActivitiRule;
import org.activiti.engine.test.Deployment;
import org.junit.Rule;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

/**
 * @Author: HuTingrong
 * @Description: history测试
 * @Date: Created in 11:17 2019/10/11
 * @Modified By:
 */
@Slf4j
public class ConfigHistoryLevelTest {

    //@Rule 用后即毁的。含启动、创建
    @Rule
    public ActivitiRule activitiRule = new ActivitiRule("activiti_history.cfg.xml");

    /**
     * none: 不记录历史流程，性能高，流程结束后不可读取
     * activiti：归档流程实例和活动实例，流程变量不同步（无细节）
     * audit：默认，在activiti基础上 同步变量值（有细节），保存表单属性
     * full：性能较差，全部实例，变量
     */
    //@Deployment部署
    @Test
    @Deployment(resources = {"com/imooc/activiti/my-process.bpmn20.xml"})
    public void test() {
        ProcessInstance processInstance = activitiRule.getRuntimeService().startProcessInstanceByKey("my-process");
        Task task = activitiRule.getTaskService().createTaskQuery().singleResult();
        assertEquals("Activiti is awesome!", task.getName());
        activitiRule.getTaskService().complete(task.getId());

        //启动流程
        //修改变量
        //提交表单 task
        //输出历史内容
        //输出历史活动
        //输出历史表单
        //输出历史详情

    }

}
