package com.office.process.controller;


import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.office.common.result.Result;
import com.office.model.process.ProcessTemplate;
import com.office.process.service.OaProcessTemplateService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.ResourceUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * <p>
 * 审批模板 前端控制器
 * </p>
 *
 * @author chp
 * @since 2023-05-24
 */
@RestController
@RequestMapping("/admin/process/processTemplate")
public class OaProcessTemplateController {

    @Autowired
    private OaProcessTemplateService processTemplateService;
    //分页查询审批模板
    @ApiOperation("获取分页审批模板数据")
    @GetMapping("{page}/{limit}")
    public Result index(@PathVariable Long page,
                        @PathVariable Long limit){
        Page<ProcessTemplate> pageParam = new Page<>(page, limit);
        IPage<ProcessTemplate> pageModel=processTemplateService.selectPageProcessTempate(pageParam);
        return Result.ok(pageModel);

    }
    //@PreAuthorize("hasAuthority('bnt.processTemplate.list')")
    @ApiOperation(value = "获取")
    @GetMapping("get/{id}")
    public Result get(@PathVariable Long id) {
        ProcessTemplate processTemplate = processTemplateService.getById(id);
        return Result.ok(processTemplate);
    }

    //@PreAuthorize("hasAuthority('bnt.processTemplate.templateSet')")
    @ApiOperation(value = "新增")
    @PostMapping("save")
    public Result save(@RequestBody ProcessTemplate processTemplate) {
        processTemplateService.save(processTemplate);
        return Result.ok();
    }

    //@PreAuthorize("hasAuthority('bnt.processTemplate.templateSet')")
    @ApiOperation(value = "修改")
    @PutMapping("update")
    public Result updateById(@RequestBody ProcessTemplate processTemplate) {
        processTemplateService.updateById(processTemplate);
        return Result.ok();
    }

    //@PreAuthorize("hasAuthority('bnt.processTemplate.remove')")
    @ApiOperation(value = "删除")
    @DeleteMapping("remove/{id}")
    public Result remove(@PathVariable Long id) {
        processTemplateService.removeById(id);
        return Result.ok();
    }
//    @PreAuthorize("hasAuthority('bnt.processTemplate.templateSet')")
    @ApiOperation(value = "上传流程定义")
    @PostMapping("/uploadProcessDefinition")
    public Result uploadProcessDefinition(MultipartFile file) throws FileNotFoundException {
        //获取classes目录位置
        String path = new File(ResourceUtils.getURL("classpath:").getPath()).getAbsolutePath();
        //设置上传文件夹
        File temFile = new File(path + "/processes/");
        if (!temFile.exists()){
            temFile.mkdirs();
        }
        //创建空文件，实现文件写入
        String fileName = file.getOriginalFilename();
        File zipFile = new File(path + "/processes/" + fileName);
        //保存文件
        try {
            file.transferTo(zipFile);
        } catch (IOException e) {
            return Result.fail();

        }
        Map<String, Object> map = new HashMap<>();
        //根据上传地址后续部署流程定义，文件名称为流程定义的默认key
        map.put("processDefinitionPath", "processes/" + fileName);
        map.put("processDefinitionKey", fileName.substring(0, fileName.lastIndexOf(".")));
        return Result.ok(map);

    }

//    public static void main(String[] args) {
//        try {
//            String classpath = new File(ResourceUtils.getURL("classpath:").getPath()).getAbsolutePath();
//            System.out.println(classpath);
//        } catch (FileNotFoundException e) {
//            throw new RuntimeException(e);
//        }
//    }
        //流程部署定义
        //@PreAuthorize("hasAuthority('bnt.processTemplate.publish')")
        @ApiOperation(value = "发布")
        @GetMapping("/publish/{id}")
        public Result publish(@PathVariable Long id){
        //修改模板状态 1代表已经发布
            //流程定义部署
            processTemplateService.publish(id);
            return Result.ok();
        }

}

