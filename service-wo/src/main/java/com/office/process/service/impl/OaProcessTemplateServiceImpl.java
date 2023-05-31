package com.office.process.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.office.model.process.ProcessTemplate;
import com.office.model.process.ProcessType;
import com.office.process.mapper.OaProcessTemplateMapper;
import com.office.process.service.OaProcessService;
import com.office.process.service.OaProcessTemplateService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.office.process.service.OaProcessTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.List;

/**
 * <p>
 * 审批模板 服务实现类
 * </p>
 *
 * @author chp
 * @since 2023-05-24
 */
@Service
public class OaProcessTemplateServiceImpl extends ServiceImpl<OaProcessTemplateMapper, ProcessTemplate> implements OaProcessTemplateService {

    @Autowired
    private OaProcessTypeService processTypeService;

    @Autowired
    private OaProcessService processService;
    @Override
    public IPage<ProcessTemplate> selectPageProcessTempate(Page<ProcessTemplate> pageParam) {
        //1 调用mapper的方法实现分页查询
            Page<ProcessTemplate> processTemplatePage = baseMapper.selectPage(pageParam, null);
        //2 第一步分页查询返回分页输一局，从分页数据获取列表集合
            List<ProcessTemplate> processTemplateList = processTemplatePage.getRecords();
        //3 遍历list集合，得到每个对象的审批类型id
            for (ProcessTemplate processTemplate:processTemplateList){
                //得到每个对象的审批类型id
                Long processTypeId = processTemplate.getProcessTypeId();
                //4 根据审批类型id，查询获取对应名称
                LambdaQueryWrapper<ProcessType> wrapper = new LambdaQueryWrapper<>();
                wrapper.eq(ProcessType::getId,processTypeId);
                ProcessType processType = processTypeService.getOne(wrapper);
                if (processType==null){
                    continue;
                }
                //5 完成最终封装，返回processTypeName
                processTemplate.setProcessTypeName(processType.getName());

            }





        return processTemplatePage;
    }

    @Override
    public void publish(Long id) {
        //修改莫汉发布状态 1 代表已经发布
        ProcessTemplate processTemplate = baseMapper.selectById(id);
        processTemplate.setStatus(1);
        baseMapper.updateById(processTemplate);
        //流程定义部署完善
        if(StringUtils.isEmpty(processTemplate.getProcessDefinitionPath())){
            processService.deployByZip(processTemplate.getProcessDefinitionPath());
        }

    }
}
