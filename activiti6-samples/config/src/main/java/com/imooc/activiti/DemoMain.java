package com.imooc.activiti;

import com.google.common.collect.Maps;
import lombok.extern.slf4j.Slf4j;
import org.activiti.engine.*;
import org.activiti.engine.form.FormProperty;
import org.activiti.engine.form.TaskFormData;
import org.activiti.engine.impl.form.DateFormType;
import org.activiti.engine.impl.form.StringFormType;
import org.activiti.engine.repository.Deployment;
import org.activiti.engine.repository.DeploymentBuilder;
import org.activiti.engine.repository.ProcessDefinition;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.task.Task;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

@SpringBootApplication
@Slf4j
public class DemoMain {
    public static void main(String[] args) throws ParseException {
        log.info("启动程序");
        //创建流程引擎
        ProcessEngine processEngine = getProcessEngine();
        //部署流程定义文件
        ProcessDefinition processDefinition = getProcessDefinition(processEngine);
        //启动运行流程
        ProcessInstance processInstance = getProcessInstance(processEngine, processDefinition);
        //处理流程任务
        processTask(processEngine, processInstance);
        log.info("结束程序");
    }

    private static void processTask(ProcessEngine processEngine, ProcessInstance processInstance) throws ParseException {
        Scanner scanner = new Scanner(System.in);
        while (processInstance != null && !processInstance.isEnded()){
            TaskService taskService = processEngine.getTaskService();
            List<Task> list = taskService.createTaskQuery().list();
            log.info("待处理任务数量【{}】",list.size());
            for (Task task : list) {
                Map<String, Object> variables = getStringObjectMap(processEngine, scanner, task);
                taskService.complete(task.getId(),variables);
                processInstance = processEngine
                        .getRuntimeService()
                        .createProcessInstanceQuery().processInstanceId(processInstance.getId()).singleResult();
                if (processInstance!=null){
                    log.info("启动流程 【{}】,流程id【{}】",processInstance.getProcessDefinitionKey(),processInstance.getId());
                }
            }
        }
        scanner.close();
    }

    private static Map<String, Object> getStringObjectMap(ProcessEngine processEngine, Scanner scanner, Task task) throws ParseException {
        log.info("待处理任务【{}】待处理ID【{}】",task.getName(),task.getId());
        FormService formService = processEngine.getFormService();
        TaskFormData taskFormData = formService.getTaskFormData(task.getId());
        List<FormProperty> formProperties = taskFormData.getFormProperties();
        Map<String,Object> variables = Maps.newHashMap();
        for (FormProperty property: formProperties) {
            String line = null;
            if (StringFormType.class.isInstance(property.getType())){
                log.info("请输入【{}】 ?",property.getName());
                line = scanner.nextLine();
                variables.put(property.getId(),line);
            }else if (DateFormType.class.isInstance(property.getType())){
                log.info("请输入【{}】 ? 格式（yyyy-MM-dd）",property.getName());
                line = scanner.nextLine();
                SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
                Date parse = dateFormat.parse(line);
                variables.put(property.getId(),parse);
            }else {
                log.info("暂不支持【{}】",property.getType());
            }
            log.info("您输入的内容是【{}】",line);
        }
        return variables;
    }

    private static ProcessInstance getProcessInstance(ProcessEngine processEngine, ProcessDefinition processDefinition) {
        RuntimeService runtimeService = processEngine.getRuntimeService();
        ProcessInstance processInstance = runtimeService.startProcessInstanceById(processDefinition.getId());
        log.info("启动流程 【{}】,流程部署id【{}】",processInstance.getProcessDefinitionKey(),processInstance.getDeploymentId());
        return processInstance;
    }

    private static ProcessDefinition getProcessDefinition(ProcessEngine processEngine) {
        RepositoryService repositoryService = processEngine.getRepositoryService();
        DeploymentBuilder builder = repositoryService.createDeployment();
        builder.addClasspathResource("second_approve.bpmn20.xml");
        builder.name("二级审批");
        Deployment deployment = builder.deploy();
        String deploymentId = deployment.getId();
        deployment.getName();
        RuntimeService runtimeService = processEngine.getRuntimeService();
        ProcessDefinition processDefinition = repositoryService.createProcessDefinitionQuery().deploymentId(deploymentId).singleResult();
        log.info("流程定义文件【{}】,流程ID【{}】",processDefinition.getName(),processDefinition.getId());
        return processDefinition;
    }

    private static ProcessEngine getProcessEngine() {
        ProcessEngineConfiguration cfg = ProcessEngineConfiguration.createProcessEngineConfigurationFromResourceDefault();
        ProcessEngine processEngine = cfg.buildProcessEngine();
        String name = processEngine.getName();
        String version = processEngine.VERSION;
        log.info("流程引擎名称【{}】,版本【{}】",name,version);
        return processEngine;
    }
}

