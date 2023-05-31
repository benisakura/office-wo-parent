package com.office.auth.activiti;

import org.activiti.engine.HistoryService;
import org.activiti.engine.RepositoryService;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.TaskService;
import org.activiti.engine.history.HistoricTaskInstance;
import org.activiti.engine.repository.Deployment;
import org.activiti.engine.repository.ProcessDefinition;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.task.Task;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;


@SpringBootTest
public class ProcessTest {
    //注入RepositoryService
    @Autowired
    private RepositoryService repositoryService;
    //注入RuntimeService
    @Autowired
    private RuntimeService runtimeService;
    @Autowired
    private TaskService taskService;
    @Autowired
    private HistoryService historyService;
    //单个文件部署
    @Test
    public void deployProcess(){
        //流程部署
        Deployment deploy = repositoryService.createDeployment()
                .addClasspathResource("process/qingjia.bpmn20.xml")
                .addClasspathResource("process/qingjia.png")
                .name("请假申请流程")
                .deploy();
        System.out.println(deploy.getId());
        System.out.println(deploy.getName());
    }
    //启动流程实例
    @Test
    public void startProcess(){
        ProcessInstance processInstance = runtimeService.startProcessInstanceByKey("qingjia");
        System.out.println("流程定义id："+processInstance.getProcessDefinitionId());
        System.out.println("流程实例id："+processInstance.getId());
        System.out.println("流程活动id："+processInstance.getActivityId());
    }
    //查询个人待办任务
    @Test
    public void findTaskList(){
        List<Task> tasks = taskService.createTaskQuery().taskAssignee("zhangsan").list();
        for (Task task:tasks){
            System.out.println("流程实例id："+task.getProcessInstanceId());
            System.out.println("任务id："+task.getId());
            System.out.println("任务负责人："+task.getAssignee());
            System.out.println("任务名称："+task.getName());
        }

    }
    //处理当前任务
    @Test
    public void completeTask(){
        //查询负责人需要处理任务，返回一条
        Task task = taskService.createTaskQuery().taskAssignee("zhangsan").singleResult();
        //完成任务,参数是任务id
        taskService.complete(task.getId());;

    }
    //查询已处理任务
    @Test
    public void findcompleteTaskList(){
        List<HistoricTaskInstance> list = historyService.createHistoricTaskInstanceQuery().taskAssignee("zhangsan").finished().list();
        for (HistoricTaskInstance historicTaskInstance:list){
            System.out.println("流程实例id："+historicTaskInstance.getProcessInstanceId());
            System.out.println("任务id："+historicTaskInstance.getId());
            System.out.println("任务负责人："+historicTaskInstance.getAssignee());
            System.out.println("任务名称："+historicTaskInstance.getName());
        }
    }
    @Test
    public void startUpProcessAddBusinessKey(){
        // 启动流程实例，指定业务标识businessKey，也就是请假申请单id
        ProcessInstance processInstance = runtimeService.
                startProcessInstanceByKey("qingjia","11111");
        // 输出
        System.out.println("业务id:"+processInstance.getBusinessKey());
        System.out.println(processInstance.getId());
    }
    //全部流程实例挂起
    @Test
    public void suspendProcessInstance() {
        //1 获取流程定义对象
        List<ProcessDefinition> list = repositoryService.createProcessDefinitionQuery().processDefinitionKey("qingjia").list();
        for (ProcessDefinition qingjia : list) {
            //2 调用流程定义对象的方法判断当前状态：挂起 激活
            boolean suspended = qingjia.isSuspended();
            //3 判断如果挂起，实现激活
            if (suspended) {
                //第一个参数 流程定义id
                //第二个参数 是否激活
                //第三个参数 时间点
                repositoryService.activateProcessDefinitionById(qingjia.getId(), true, null);
                System.out.println(qingjia.getId() + "激活");
            } else {
                //如果激活，实现挂起
                repositoryService.suspendProcessDefinitionById(qingjia.getId(), true, null);
                System.out.println(qingjia.getId() + "挂起");
            }
        }
    }
    //单个流程挂起
    @Test
    public void SingleSuspendProcessInstance(){
    String processInstanceId="7385121c-f93f-11ed-80de-005056c00008";
        ProcessInstance processInstance = runtimeService.createProcessInstanceQuery().processInstanceId(processInstanceId).singleResult();
        boolean suspended = processInstance.isSuspended();
        if(suspended){
            //激活
            runtimeService.activateProcessInstanceById(processInstanceId);
            System.out.println(processInstanceId+"：激活");
        }else {
            //挂起
            runtimeService.suspendProcessInstanceById(processInstanceId);
            System.out.println(processInstanceId+"：挂起");
        }
    }


}
