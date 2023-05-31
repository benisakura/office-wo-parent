package com.office.process.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.office.model.process.ProcessTemplate;
import com.office.model.process.ProcessType;
import com.office.process.mapper.OaProcessTypeMapper;
import com.office.process.service.OaProcessService;
import com.office.process.service.OaProcessTemplateService;
import com.office.process.service.OaProcessTypeService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.xml.ws.Action;
import java.util.List;

/**
 * <p>
 * 审批类型 服务实现类
 * </p>
 *
 * @author chp
 * @since 2023-05-24
 */
@Service
public class OaProcessTypeServiceImpl extends ServiceImpl<OaProcessTypeMapper, ProcessType> implements OaProcessTypeService {


    @Autowired
    private OaProcessTemplateService processTemplateService;
    //查询所有审批分类和每个分类所有的审批模板
    @Override
    public List<ProcessType> findProcessType() {
        //1 查询所有审批分类，返回list集合
        List<ProcessType> processTypeList = baseMapper.selectList(null);
        //2 遍历返回所有审批分类list集合
        for (ProcessType processType:processTypeList) {
            //得到每个审批分类，根据审批分类id查询对应审批模板
            //审批分类id
            Long typeId = processType.getId();
            //根据审批分类id查询对应审批模板
            LambdaQueryWrapper<ProcessTemplate> wrapper = new LambdaQueryWrapper<>();
            wrapper.eq(ProcessTemplate::getProcessTypeId,typeId);
            List<ProcessTemplate> processTemplateLisst =processTemplateService.list(wrapper);
            //4 根据分类id查询对应审批模板数据，封装到每个审批分类对象里面
            processType.setProcessTemplateList(processTemplateLisst);
        }
        return processTypeList;
    }
}
